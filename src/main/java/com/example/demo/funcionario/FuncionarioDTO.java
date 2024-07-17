package com.example.demo.funcionario;

import com.example.demo.user.UsuarioDTO;

public class FuncionarioDTO extends UsuarioDTO {
    private String dataNascimento;

    public FuncionarioDTO(Long id, String email, String senha, String salt, String role, String nome) {
        super(id, email, senha, salt, role, nome);
    }

    // Getters e Setters
    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}