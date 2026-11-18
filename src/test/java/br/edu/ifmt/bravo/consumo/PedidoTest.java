package br.edu.ifmt.bravo.consumo;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
}