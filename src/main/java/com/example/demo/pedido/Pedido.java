package com.example.demo.pedido;

import com.example.demo.cliente.Cliente;
import com.example.demo.roupa.Roupa;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private long time;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private double value;

    @Column(nullable = false)
    private LocalDateTime closeDate;

    @Column(nullable = false)
    private LocalDateTime openDate;

    @ManyToMany
    @JoinTable(
            name = "pedido_roupa",
            joinColumns = @JoinColumn(name = "pedido_id"),
            inverseJoinColumns = @JoinColumn(name = "roupa_id"))
    private List<Roupa> roupas = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido")
    private List<PedidoRoupa> pedidoRoupas = new ArrayList<>();

    public List<PedidoRoupa> getPedidoRoupas() {
        return pedidoRoupas;
    }

    public void setPedidoRoupas(List<PedidoRoupa> pedidoRoupas) {
        this.pedidoRoupas = pedidoRoupas;
    }

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

    public List<Roupa> getRoupas() {
        return roupas;
    }

    public void setRoupas(List<Roupa> roupas) {
        this.roupas = roupas;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}