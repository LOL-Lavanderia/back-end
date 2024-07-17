package com.example.demo.roupa;

public class RoupaDTO {
    private long id;
    private String nome;
    private String valor;

    private String prazo;

    private Long quantidade;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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

    public Long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "RoupaDTO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", valor='" + valor + '\'' +
                ", prazo='" + prazo + '\'' +
                ", quantidade=" + quantidade +
                '}';
    }
}
