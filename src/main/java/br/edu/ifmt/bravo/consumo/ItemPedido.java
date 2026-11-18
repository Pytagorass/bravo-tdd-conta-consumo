package br.edu.ifmt.bravo.consumo;

import java.math.BigDecimal;

public class ItemPedido {

    private final BigDecimal precoUnitario;
    private int quantidade;

    public ItemPedido(BigDecimal precoUnitario, int quantidade) {
        validarQuantidade(quantidade);
        this.precoUnitario = precoUnitario;
        this.quantidade = quantidade;
    }

    private static void validarQuantidade(int quantidade) {
        if (quantidade < 1) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }
    }
    public void alterarQuantidade(int novaQuantidade) {
        validarQuantidade(novaQuantidade);
        this.quantidade = novaQuantidade;
    }

    public BigDecimal calcularSubtotal() {
        return precoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }
    
}