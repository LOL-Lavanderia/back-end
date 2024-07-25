package com.example.demo.roupa;

import com.example.demo.pedido.Pedido;
import jakarta.persistence.*;

import java.math.BigInteger;
import java.util.List;

@Entity
@Table
public class Roupa {

    @Id
    @SequenceGenerator(
            name="roupa_sequence",
            sequenceName="roupa_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "cliente_sequence"
    )

    private Long id;
    private String name;
    private String price;

    private String time;

    private String quantity;

    @ManyToMany(mappedBy = "roupas")
    private List<Pedido> pedidos;

    @Column(nullable = false)
    private boolean active = true;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }




}
