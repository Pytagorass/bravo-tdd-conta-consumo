package br.edu.ifmt.bravo.consumo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Pedido {

    private final Map<String, ItemPedido> itens = new HashMap<>();

    public Pedido(String id) {
    }

    public void adicionarItem(String idItem, Produto produto, int quantidade) {
        ItemPedido item = new ItemPedido(produto.getPreco(), quantidade);
        itens.put(idItem, item);
    }

    public BigDecimal calcularTotal() {
        BigDecimal total = BigDecimal.ZERO;

        for (ItemPedido item : itens.values()) {
            total = total.add(item.calcularSubtotal());
        }

        return total;
    }
}