package com.example.demo.roupa;

public class RoupaDTO {
    private String nome;
    private String valor;

    private String prazo;

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
        return "RoupaDTO{" +
                "nome='" + nome + '\'' +
                ", valor='" + valor + '\'' +
                ", prazo='" + prazo + '\'' +
                '}';
    }
}
