package br.edu.ifmt.bravo;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import br.edu.ifmt.bravo.consumo.Produto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


class ProdutoTest {

    @Test
    void deveRejeitarAlteracaoQuandoNovoPrecoForNegativo() {
        Produto produto = new Produto("Suco", new BigDecimal("8.00"));

        assertThrows(IllegalArgumentException.class,
                () -> produto.alterarPreco(new BigDecimal("-1.00")));
    }

    @Test
    void deveAtualizarPrecoQuandoNovoPrecoForValido() {
        Produto produto = new Produto("Suco", new BigDecimal("8.00"));

        produto.alterarPreco(new BigDecimal("10.00"));

        assertEquals(new BigDecimal("10.00"), produto.getPreco());
    }
    @Test
    void deveFicarInativoQuandoForDesativado() {
        Produto produto = new Produto("Suco", new BigDecimal("8.00"));
        assertTrue(produto.isAtivo());

        produto.desativar();

        assertFalse(produto.isAtivo());
    }
}