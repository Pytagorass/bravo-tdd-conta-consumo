package br.edu.ifmt.bravo.consumo;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class ItemPedidoTest {

    @Test
    void deveCalcularSubtotalQuandoHouverMaisDeUmaUnidade() {
        ItemPedido item = new ItemPedido(new BigDecimal("8.00"), 2);

        BigDecimal subtotal = item.calcularSubtotal();

        assertEquals(new BigDecimal("16.00"), subtotal);
    }
    @Test
    void deveRecalcularSubtotalQuandoQuantidadeForAlterada() {
        ItemPedido item = new ItemPedido(new BigDecimal("8.00"), 2);

        item.alterarQuantidade(3);

        assertEquals(new BigDecimal("24.00"), item.calcularSubtotal());
    }
        @Test
    void deveRejeitarAlteracaoQuandoNovaQuantidadeForZero() {
        ItemPedido item = new ItemPedido(new BigDecimal("8.00"), 2);

        assertThrows(IllegalArgumentException.class,
                () -> item.alterarQuantidade(0));

        assertEquals(new BigDecimal("16.00"), item.calcularSubtotal());
    }
        @Test
    void deveRejeitarCriacaoQuandoQuantidadeInicialForZero() {
        assertThrows(IllegalArgumentException.class,
                () -> new ItemPedido(new BigDecimal("8.00"), 0));
    }
}