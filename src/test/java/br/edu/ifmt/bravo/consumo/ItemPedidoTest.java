package br.edu.ifmt.bravo.consumo;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import br.edu.ifmt.bravo.ItemPedido;

class ItemPedidoTest {

    @Test
    void deveCalcularSubtotalQuandoHouverMaisDeUmaUnidade() {
        ItemPedido item = new ItemPedido(new BigDecimal("8.00"), 2);

        BigDecimal subtotal = item.calcularSubtotal();

        assertEquals(new BigDecimal("16.00"), subtotal);
    }
}