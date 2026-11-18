package br.edu.ifmt.bravo.consumo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Pedido {

    private final Map<String, ItemPedido> itens = new HashMap<>();
    private boolean confirmado;

    public Pedido(String id) {
    }

    public void adicionarItem(String idItem, Produto produto, int quantidade) {
        if (!produto.isAtivo()) {
            throw new IllegalArgumentException("O produto está desativado.");
        }

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

    public void removerItem(String idItem) {
        if (itens.remove(idItem) == null) {
            throw new IllegalArgumentException("Item não encontrado no pedido.");
        }
    }

    public void alterarQuantidadeDoItem(String idItem, int quantidade) {
        ItemPedido item = itens.get(idItem);

        if (item == null) {
            throw new IllegalArgumentException("Item não encontrado no pedido.");
        }

        item.alterarQuantidade(quantidade);
    }

    public void confirmar() {
        if (itens.isEmpty()) {
            throw new IllegalStateException("O pedido precisa ter pelo menos um item.");
        }

        confirmado = true;
    }

    public boolean isConfirmado() {
        return confirmado;
    }
}