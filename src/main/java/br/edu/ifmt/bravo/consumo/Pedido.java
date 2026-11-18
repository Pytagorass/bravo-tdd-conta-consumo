package br.edu.ifmt.bravo.consumo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Pedido {

    private final Map<String, ItemPedido> itens = new HashMap<>();
    private boolean confirmado;
    private final String id;

    public Pedido(String id) {
        this.id = id;
    }
    
    public String getId() {
        return id;
    }

    public void adicionarItem(String idItem, Produto produto, int quantidade) {
        validarPedidoEmEdicao();
        if (!produto.isAtivo()) {
            throw new IllegalArgumentException("O produto está desativado.");
        }

        ItemPedido item = new ItemPedido(produto.getPreco(), quantidade);
            if (itens.putIfAbsent(idItem, item) != null) {
                throw new IllegalArgumentException("Já existe um item com esse identificador.");
        }
    }

    public BigDecimal calcularTotal() {
        BigDecimal total = BigDecimal.ZERO;

        for (ItemPedido item : itens.values()) {
            total = total.add(item.calcularSubtotal());
        }

        return total;
    }

    public void removerItem(String idItem) {
        validarPedidoEmEdicao();
        if (itens.remove(idItem) == null) {
            throw new IllegalArgumentException("Item não encontrado no pedido.");
        }
    }

    public void alterarQuantidadeDoItem(String idItem, int quantidade) {
        validarPedidoEmEdicao();
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

    private void validarPedidoEmEdicao() {
        if (confirmado) {
            throw new IllegalStateException("Um pedido confirmado não pode ser alterado.");
        }
    }
}