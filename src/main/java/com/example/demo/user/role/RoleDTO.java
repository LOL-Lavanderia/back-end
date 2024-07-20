package com.example.demo.user.role;

import com.example.demo.endereco.EnderecoDTO;
import com.example.demo.telefone.TelefoneDTO;

import java.util.List;

public class RoleDTO {
    private String role; // "client" ou "employee"
    private String cpf; // Para "client"
    private List<EnderecoDTO> enderecos; // Para "client"
    private List<TelefoneDTO> telefones; // Para "client"
    private String dataNascimento; // Para "employee"

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<EnderecoDTO> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<EnderecoDTO> enderecos) {
        this.enderecos = enderecos;
    }

    public List<TelefoneDTO> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<TelefoneDTO> telefones) {
        this.telefones = telefones;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    @Override
    public String toString() {
        return "RoleDTO{" +
                "role='" + role + '\'' +
                ", cpf='" + cpf + '\'' +
                ", enderecos=" + enderecos +
                ", telefones=" + telefones +
                ", dataNascimento='" + dataNascimento + '\'' +
                '}';
    }
}