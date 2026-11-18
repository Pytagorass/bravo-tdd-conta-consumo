package br.edu.ifmt.bravo.consumo;

import java.math.BigDecimal;

public class Produto {

    public Produto(String nome, BigDecimal preco) {
    }

    public void alterarPreco(BigDecimal novoPreco) {
    if (novoPreco.signum() < 0) {
        throw new IllegalArgumentException("O preço não pode ser negativo.");
    }
    }
}