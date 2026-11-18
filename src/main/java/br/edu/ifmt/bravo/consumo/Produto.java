package br.edu.ifmt.bravo.consumo;

import java.math.BigDecimal;

public class Produto {

    private BigDecimal preco;

    private boolean ativo = true;

    public Produto(String nome, BigDecimal preco) {
        this.preco = preco;
    }

    public void alterarPreco(BigDecimal novoPreco) {
        if (novoPreco.signum() < 0) {
            throw new IllegalArgumentException("O preço não pode ser negativo.");
        }

        this.preco = novoPreco;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void desativar() {
        ativo = false;
    }
}