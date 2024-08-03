package com.example.demo.relatorios;

import com.example.demo.pedido.PedidoDTO;

import java.util.List;

public class RelatorioReceitaResponse {

    private List<PedidoDTO> pedidos;
    private Double totalReceita;

    public List<PedidoDTO> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<PedidoDTO> pedidos) {
        this.pedidos = pedidos;
    }

    public Double getTotalReceita() {
        return totalReceita;
    }

    public void setTotalReceita(Double totalReceita) {
        this.totalReceita = totalReceita;
    }
}
