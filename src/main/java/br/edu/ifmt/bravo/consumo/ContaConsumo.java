package br.edu.ifmt.bravo.consumo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ContaConsumo {

    private final String idReserva;
    private final Map<String, Pedido> pedidos = new HashMap<>();

    public ContaConsumo(String idReserva) {
        this.idReserva = idReserva;
    }

    public String getIdReserva() {
        return idReserva;
    }
    public void registrarPedido(Pedido pedido) {
        if (!pedido.isConfirmado()) {
            throw new IllegalStateException("O pedido precisa estar confirmado.");
        }

        if (pedidos.putIfAbsent(pedido.getId(), pedido) != null) {
            throw new IllegalArgumentException("Já existe um pedido com esse identificador na conta.");
        }
    }

    public BigDecimal calcularTotal() {
        BigDecimal total = BigDecimal.ZERO;

        for (Pedido pedido : pedidos.values()) {
            total = total.add(pedido.calcularTotal());
        }

    return total;
    }
}