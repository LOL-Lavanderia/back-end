package com.example.demo.cliente;

public class ClienteFielDTO {
    private Long clienteId;
    private String nomeCliente;
    private Integer quantidadePedidos;
    private Double receitaTotal;

    public ClienteFielDTO(Long clienteId, String nomeCliente, Integer quantidadePedidos, Double receitaTotal) {
        this.clienteId = clienteId;
        this.nomeCliente = nomeCliente;
        this.quantidadePedidos = quantidadePedidos;
        this.receitaTotal = receitaTotal;
    }

    // Getters e Setters
    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public Integer getQuantidadePedidos() {
        return quantidadePedidos;
    }

    public void setQuantidadePedidos(Integer quantidadePedidos) {
        this.quantidadePedidos = quantidadePedidos;
    }

    public Double getReceitaTotal() {
        return receitaTotal;
    }

    public void setReceitaTotal(Double receitaTotal) {
        this.receitaTotal = receitaTotal;
    }

    @Override
    public String toString() {
        return "ClienteFielDTO{" +
                "clienteId=" + clienteId +
                ", nomeCliente='" + nomeCliente + '\'' +
                ", quantidadePedidos=" + quantidadePedidos +
                ", receitaTotal=" + receitaTotal +
                '}';
    }
}
