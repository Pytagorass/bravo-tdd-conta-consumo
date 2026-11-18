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
}