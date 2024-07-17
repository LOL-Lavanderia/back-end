package com.example.demo.telefone;

public class TelefoneDTO {
    private Long id;
    private String numero;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "TelefoneDTO{" +
                "id=" + id +
                ", numero='" + numero + '\'' +
                '}';
    }
}