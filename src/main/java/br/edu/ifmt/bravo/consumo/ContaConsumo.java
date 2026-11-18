package br.edu.ifmt.bravo.consumo;

import java.math.BigDecimal;

public class ContaConsumo {

    private final String idReserva;

    public ContaConsumo(String idReserva) {
        this.idReserva = idReserva;
    }

    public String getIdReserva() {
        return idReserva;
    }

    public void registrarPedido(Pedido pedido) {
    }

    public BigDecimal calcularTotal() {
        return BigDecimal.ZERO;
    }
}