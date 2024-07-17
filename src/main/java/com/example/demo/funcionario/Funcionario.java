package com.example.demo.funcionario;

import com.example.demo.user.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class Funcionario extends Usuario {
    @Column(nullable = false)
    private LocalDate dataNascimento;

    // Getters e Setters
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}
