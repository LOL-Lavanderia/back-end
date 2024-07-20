package com.example.demo.funcionario;

import com.example.demo.user.UsuarioDTO;
import com.example.demo.user.role.RoleDTO;

public class FuncionarioDTO extends UsuarioDTO {
    private String dataNascimento;

    public FuncionarioDTO(Long id, String email, String senha,String nome, RoleDTO role) {
        super(id, email, senha,nome, role) ;
    }

    // Getters e Setters
    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}