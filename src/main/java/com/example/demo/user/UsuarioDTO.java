package com.example.demo.user;

import com.example.demo.user.role.RoleDTO;

public class UsuarioDTO {

    private Long id;
    private String email;
    private String nome;
    private String senha;
    private RoleDTO role;
    // Construtor
    public UsuarioDTO(Long id, String email, String senha, String nome, RoleDTO role) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.role = role;
        this.nome = nome;
    }

    public UsuarioDTO() {

    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }



    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "UsuarioDTO{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nome='" + nome + '\'' +
                ", senha='" + senha + '\'' +
                ", role=" + role +
                '}';
    }
}