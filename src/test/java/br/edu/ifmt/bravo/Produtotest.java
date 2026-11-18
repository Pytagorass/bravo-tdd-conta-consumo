package br.edu.ifmt.bravo;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import br.edu.ifmt.bravo.consumo.Produto;


class ProdutoTest {

    @Test
    void deveRejeitarAlteracaoQuandoNovoPrecoForNegativo() {
        Produto produto = new Produto("Suco", new BigDecimal("8.00"));

        assertThrows(IllegalArgumentException.class,
                () -> produto.alterarPreco(new BigDecimal("-1.00")));
    }
}