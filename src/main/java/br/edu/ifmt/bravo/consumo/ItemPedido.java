package br.edu.ifmt.bravo.consumo;

import java.math.BigDecimal;

public class ItemPedido {

    private final BigDecimal precoUnitario;
    private int quantidade;

    public ItemPedido(BigDecimal precoUnitario, int quantidade) {
    if (quantidade < 1) {
        throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
    }

    this.precoUnitario = precoUnitario;
    this.quantidade = quantidade;
}

    public BigDecimal calcularSubtotal() {
        return precoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }
    public void alterarQuantidade(int novaQuantidade) {
        if (novaQuantidade < 1) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }
        this.quantidade = novaQuantidade;
    }
    
}