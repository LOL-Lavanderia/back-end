package com.example.demo.cliente;
import com.example.demo.endereco.Endereco;
import jakarta.persistence.*;

@Entity
@Table
public class Cliente {
    @Id
    @SequenceGenerator(
            name="cliente_sequence",
            sequenceName="cliente_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "cliente_sequence"
    )
    private Long id;
    String cpf;
    String nome;
    String email;
    String telefone;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    String senha;
    String salt;


    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString(){
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email=" + email + '\'' +
                ", endereco=" + endereco + '\'' +
                ", senha=" + senha + '\'' +
                ", salt=" + salt +
                '}';
    }


}
