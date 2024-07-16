package com.example.demo.roupa;

import jakarta.persistence.*;

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
    private String nome;
    private String valor;

    private String prazo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getPrazo() {
        return prazo;
    }

    public void setPrazo(String prazo) {
        this.prazo = prazo;
    }

    @Override
    public String toString() {
        return "Roupa{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", valor='" + valor + '\'' +
                ", prazo='" + prazo + '\'' +
                '}';
    }
}
