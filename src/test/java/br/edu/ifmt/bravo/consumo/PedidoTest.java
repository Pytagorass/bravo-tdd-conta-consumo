package br.edu.ifmt.bravo.consumo;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class PedidoTest {

    @Test
    void deveRetornarZeroQuandoPedidoEstiverVazio() {
        Pedido pedido = new Pedido("PED-001");

        assertEquals(BigDecimal.ZERO, pedido.calcularTotal());
    }
    @Test
    void deveIncluirSubtotalNoTotalQuandoAdicionarItem() {
        Pedido pedido = new Pedido("PED-001");
        Produto produto = new Produto("Suco", new BigDecimal("8.00"));

        pedido.adicionarItem("ITEM-001", produto, 2);

        assertEquals(new BigDecimal("16.00"), pedido.calcularTotal());
    }

    @Test
    void deveRejeitarInclusaoQuandoProdutoEstiverDesativado() {
        Pedido pedido = new Pedido("PED-001");
        Produto produto = new Produto("Suco", new BigDecimal("8.00"));
        produto.desativar();

        assertThrows(IllegalArgumentException.class,
                () -> pedido.adicionarItem("ITEM-001", produto, 2));

        assertEquals(BigDecimal.ZERO, pedido.calcularTotal());
    }

        @Test
    void deveRecalcularTotalQuandoRemoverItemExistente() {
        Pedido pedido = new Pedido("PED-001");
        pedido.adicionarItem("ITEM-001", new Produto("Suco", new BigDecimal("8.00")), 2);
        pedido.adicionarItem("ITEM-002", new Produto("Água", new BigDecimal("5.00")), 1);

        pedido.removerItem("ITEM-001");

        assertEquals(new BigDecimal("5.00"), pedido.calcularTotal());
    }

    @Test
    void deveRejeitarRemocaoQuandoItemNaoExistir() {
        Pedido pedido = new Pedido("PED-001");
        pedido.adicionarItem("ITEM-001", new Produto("Suco", new BigDecimal("8.00")), 2);

        assertThrows(IllegalArgumentException.class,
                () -> pedido.removerItem("ITEM-999"));

        assertEquals(new BigDecimal("16.00"), pedido.calcularTotal());
    }

    @Test
    void deveRecalcularTotalComPrecoOriginalQuandoAlterarQuantidadeDoItem() {
        Pedido pedido = new Pedido("PED-001");
        Produto suco = new Produto("Suco", new BigDecimal("8.00"));
        pedido.adicionarItem("ITEM-001", suco, 2);
        suco.alterarPreco(new BigDecimal("10.00"));

        pedido.alterarQuantidadeDoItem("ITEM-001", 3);

        assertEquals(new BigDecimal("24.00"), pedido.calcularTotal());
    }

    @Test
    void deveRejeitarAlteracaoDeQuantidadeQuandoItemNaoExistir() {
        Pedido pedido = new Pedido("PED-001");

        assertThrows(IllegalArgumentException.class,
                () -> pedido.alterarQuantidadeDoItem("ITEM-999", 3));
    }

    @Test
    void devePreservarTotalQuandoNovaQuantidadeForZero() {
        Pedido pedido = new Pedido("PED-001");
        Produto suco = new Produto("Suco", new BigDecimal("8.00"));
        pedido.adicionarItem("ITEM-001", suco, 2);

        assertThrows(IllegalArgumentException.class,
                () -> pedido.alterarQuantidadeDoItem("ITEM-001", 0));

        assertEquals(new BigDecimal("16.00"), pedido.calcularTotal());
    }

    @Test
    void deveConfirmarPedidoQuandoHouverItens() {
        Pedido pedido = new Pedido("PED-001");
        Produto suco = new Produto("Suco", new BigDecimal("8.00"));
        pedido.adicionarItem("ITEM-001", suco, 2);

        pedido.confirmar();

        assertTrue(pedido.isConfirmado());
    }

    @Test
    void deveRejeitarConfirmacaoQuandoPedidoEstiverVazio() {
        Pedido pedido = new Pedido("PED-001");

        assertThrows(IllegalStateException.class, pedido::confirmar);
    }

    @Test
    void deveRejeitarInclusaoQuandoPedidoEstiverConfirmado() {
        Pedido pedido = criarPedidoConfirmado();
        Produto agua = new Produto("Água", new BigDecimal("5.00"));

        assertThrows(IllegalStateException.class,
                () -> pedido.adicionarItem("ITEM-002", agua, 1));

        assertEquals(new BigDecimal("16.00"), pedido.calcularTotal());
    }

    @Test
    void deveRejeitarRemocaoQuandoPedidoEstiverConfirmado() {
        Pedido pedido = criarPedidoConfirmado();

        assertThrows(IllegalStateException.class,
                () -> pedido.removerItem("ITEM-001"));

        assertEquals(new BigDecimal("16.00"), pedido.calcularTotal());
    }

    @Test
    void deveRejeitarAlteracaoDeQuantidadeQuandoPedidoEstiverConfirmado() {
        Pedido pedido = criarPedidoConfirmado();

        assertThrows(IllegalStateException.class,
                () -> pedido.alterarQuantidadeDoItem("ITEM-001", 3));

        assertEquals(new BigDecimal("16.00"), pedido.calcularTotal());
    }

    private Pedido criarPedidoConfirmado() {
        Pedido pedido = new Pedido("PED-001");
        Produto suco = new Produto("Suco", new BigDecimal("8.00"));
        pedido.adicionarItem("ITEM-001", suco, 2);
        pedido.confirmar();
        return pedido;
    }
    
}