package com.example.demo.pedido;

import com.example.demo.roupa.Roupa;
import jakarta.persistence.*;

@Entity
@Table(name = "pedido_roupa")
public class PedidoRoupa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "roupa_id", nullable = false)
    private Roupa roupa;

    @Column(nullable = false)
    private String quantidade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Roupa getRoupa() {
        return roupa;
    }

    public void setRoupa(Roupa roupa) {
        this.roupa = roupa;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "PedidoRoupa{" +
                "id=" + id +
                ", pedido=" + pedido +
                ", roupa=" + roupa +
                ", quantidade=" + quantidade +
                '}';
    }
}