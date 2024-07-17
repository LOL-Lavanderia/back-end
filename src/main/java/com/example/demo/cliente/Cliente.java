package com.example.demo.cliente;

import com.example.demo.endereco.Endereco;
import com.example.demo.telefone.Telefone;
import com.example.demo.user.Usuario;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "clientes")
public class Cliente extends Usuario {
    private String cpf;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Endereco> enderecos;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Telefone> telefones;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<Endereco> getEndereco() {
        return enderecos;
    }

    public void setEndereco(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    public List<Telefone> getTelefone() {
        return telefones;
    }

    public void setTelefone(List<Telefone> telefones) {
        this.telefones = telefones;
    }
}