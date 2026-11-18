package br.edu.ifmt.bravo.consumo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ContaConsumo {

    private final String idReserva;
    private final List<Pedido> pedidos = new ArrayList<>();

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

        pedidos.add(pedido);
    }

    public BigDecimal calcularTotal() {
        BigDecimal total = BigDecimal.ZERO;

        for (Pedido pedido : pedidos) {
            total = total.add(pedido.calcularTotal());
        }

    return total;
    }
}