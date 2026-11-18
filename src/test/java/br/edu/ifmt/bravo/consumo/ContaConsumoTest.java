package br.edu.ifmt.bravo.consumo;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class ContaConsumoTest {

    @Test
    void deveSomarAoTotalQuandoRegistrarPedidoConfirmado() {
        ContaConsumo conta = new ContaConsumo("RES-001");
        Pedido pedido = criarPedidoComItens();
        pedido.confirmar();

        conta.registrarPedido(pedido);

        assertEquals(new BigDecimal("16.00"), conta.calcularTotal());
        assertEquals("RES-001", conta.getIdReserva());
    }

    @Test
    void deveRejeitarRegistroQuandoPedidoEstiverEmEdicao() {
        ContaConsumo conta = new ContaConsumo("RES-001");
        Pedido pedido = criarPedidoComItens();

        assertThrows(IllegalStateException.class,
                () -> conta.registrarPedido(pedido));

        assertEquals(BigDecimal.ZERO, conta.calcularTotal());
    }

    private Pedido criarPedidoComItens() {
        Pedido pedido = new Pedido("PED-001");
        Produto suco = new Produto("Suco", new BigDecimal("8.00"));
        pedido.adicionarItem("ITEM-001", suco, 2);
        return pedido;
    }

    @Test
    void devePreservarTotalQuandoRegistrarPedidoComIdentificadorDuplicado() {
        ContaConsumo conta = new ContaConsumo("RES-001");
        Pedido original = criarPedidoComItens();
        original.confirmar();
        conta.registrarPedido(original);

        Pedido duplicado = criarPedidoComItens();
        duplicado.alterarQuantidadeDoItem("ITEM-001", 3);
        duplicado.confirmar();

        assertThrows(IllegalArgumentException.class,
                () -> conta.registrarPedido(duplicado));

        assertEquals(new BigDecimal("16.00"), conta.calcularTotal());
    }
}