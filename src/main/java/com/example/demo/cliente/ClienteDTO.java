package com.example.demo.cliente;

import com.example.demo.endereco.EnderecoDTO;
import com.example.demo.telefone.TelefoneDTO;
import com.example.demo.user.UsuarioDTO;

import java.util.List;

public class ClienteDTO extends UsuarioDTO {
    private String cpf;
    private List<EnderecoDTO> enderecos;
    private List<TelefoneDTO> telefones;

    public ClienteDTO(Long id, String email, String senha, String salt, String role,String nome) {
        super(id, email, senha, salt, role, nome);
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

    @Override
    public String toString() {
        return "ClienteDTO{" +
                "cpf='" + cpf + '\'' +
                ", enderecos=" + enderecos +
                ", telefones=" + telefones +
                '}';
    }
}