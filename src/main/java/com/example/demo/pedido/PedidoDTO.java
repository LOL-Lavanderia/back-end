package com.example.demo.pedido;

import com.example.demo.roupa.RoupaDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoDTO {
    private Long id;
    private long time;
    private String status;
    private double value;
    private LocalDateTime closeDate;
    private LocalDateTime openDate;
    private List<RoupaDTO> roupas;
    private Long clienteId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public LocalDateTime getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(LocalDateTime closeDate) {
        this.closeDate = closeDate;
    }

    public LocalDateTime getOpenDate() {
        return openDate;
    }

    public void setOpenDate(LocalDateTime openDate) {
        this.openDate = openDate;
    }

    public List<RoupaDTO> getRoupas() {
        return roupas;
    }

    public void setRoupas(List<RoupaDTO> roupas) {
        this.roupas = roupas;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    @Override
    public String toString() {
        return "PedidoDTO{" +
                "id=" + id +
                ", time=" + time +
                ", status='" + status + '\'' +
                ", value=" + value +
                ", closeDate=" + closeDate +
                ", openDate=" + openDate +
                ", roupas=" + roupas +
                ", clienteId=" + clienteId +
                '}';
    }
}
