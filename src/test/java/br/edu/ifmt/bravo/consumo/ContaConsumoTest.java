package br.edu.ifmt.bravo.consumo;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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

    @Test
    void deveReduzirSaldoQuandoRegistrarPagamentoParcial() {
        ContaConsumo conta = criarContaComConsumo();

        conta.registrarPagamento(new BigDecimal("10.00"));

        assertEquals(new BigDecimal("6.00"), conta.calcularSaldo());
        assertEquals(new BigDecimal("16.00"), conta.calcularTotal());
    }

    @Test
    void deveZerarSaldoQuandoRegistrarPagamentoIntegral() {
        ContaConsumo conta = criarContaComConsumo();

        conta.registrarPagamento(new BigDecimal("16.00"));

        assertEquals(new BigDecimal("0.00"), conta.calcularSaldo());
    }

    private ContaConsumo criarContaComConsumo() {
        ContaConsumo conta = new ContaConsumo("RES-001");
        Pedido pedido = criarPedidoComItens();
        pedido.confirmar();
        conta.registrarPedido(pedido);
        return conta;
    }
        @ParameterizedTest
    @ValueSource(strings = {"0.00", "-1.00", "6.01"})
    void devePreservarSaldoQuandoPagamentoForInvalido(String valor) {
        ContaConsumo conta = criarContaComConsumo();
        conta.registrarPagamento(new BigDecimal("10.00"));

        assertThrows(IllegalArgumentException.class,
                () -> conta.registrarPagamento(new BigDecimal(valor)));

        assertEquals(new BigDecimal("6.00"), conta.calcularSaldo());
    }
}