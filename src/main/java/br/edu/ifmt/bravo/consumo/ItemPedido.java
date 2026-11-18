package br.edu.ifmt.bravo.consumo;

import java.math.BigDecimal;

public class ItemPedido {

    private final BigDecimal precoUnitario;
    private int quantidade;

    public ItemPedido(BigDecimal precoUnitario, int quantidade) {
        this.precoUnitario = precoUnitario;
        this.quantidade = quantidade;
    }

    public BigDecimal calcularSubtotal() {
        return precoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }
        public void alterarQuantidade(int novaQuantidade) {
    }
}