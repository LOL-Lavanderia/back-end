package com.example.demo.user;

import com.example.demo.cliente.Cliente;
import com.example.demo.endereco.Endereco;
import com.example.demo.funcionario.Funcionario;
import com.example.demo.telefone.Telefone;
import com.example.demo.user.UsuarioRepository;
import com.example.demo.user.role.RoleDTO;
import com.example.demo.utils.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final JavaMailSender javaMailSender;
    private final PasswordService passwordService;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, JavaMailSender javaMailSender, PasswordService passwordService) {
        this.usuarioRepository = usuarioRepository;
        this.javaMailSender = javaMailSender;
        this.passwordService = passwordService;
    }

    private void enviarEmail(String email, String senhaGerada) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Sua Nova Senha");
        message.setText("Sua nova senha é: " + senhaGerada);
        javaMailSender.send(message);
    }

    private String gerarSenhaAleatoria() {
        return String.format("%04d", (int) (Math.random() * 10000));
    }

    private String hashSenha(String senha, String salt) {
        return passwordService.hashPasswordWithSalt(senha, salt);
    }

    public UsuarioDTO saveUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario;

        switch (usuarioDTO.getRole().getRole()) {
            case "client":
                usuario = new Cliente();
                Cliente cliente = (Cliente) usuario;
                RoleDTO clienteDTO = usuarioDTO.getRole();

                String senhaGerada = gerarSenhaAleatoria();
                String salt = passwordService.generateSalt();
                String senhaCriptografada = hashSenha(senhaGerada, salt);

                cliente.setPassword(senhaCriptografada);
                cliente.setSalt(salt);

                cliente.setCpf(clienteDTO.getCpf());
                cliente.setEndereco(clienteDTO.getEnderecos().stream().map(enderecoDTO -> {
                    Endereco endereco = new Endereco();
                    endereco.setLogradouro(enderecoDTO.getLogradouro());
                    endereco.setNumero(enderecoDTO.getNumero());
                    endereco.setBairro(enderecoDTO.getBairro());
                    endereco.setCidade(enderecoDTO.getCidade());
                    endereco.setCep(enderecoDTO.getCep());
                    endereco.setTipo(enderecoDTO.getTipo());
                    return endereco;
                }).collect(Collectors.toList()));
                cliente.setTelefone(clienteDTO.getTelefones().stream().map(telefoneDTO -> {
                    Telefone telefone = new Telefone();
                    telefone.setNumero(telefoneDTO.getNumero());
                    return telefone;
                }).collect(Collectors.toList()));

                enviarEmail(usuarioDTO.getEmail(), senhaGerada);
                break;

            case "employee":
                usuario = new Funcionario();
                Funcionario funcionario = (Funcionario) usuario;
                RoleDTO funcionarioDTO = usuarioDTO.getRole();

                salt = passwordService.generateSalt();
                senhaCriptografada = hashSenha(usuarioDTO.getSenha(), salt);

                funcionario.setPassword(senhaCriptografada);
                funcionario.setSalt(salt);

                funcionario.setDataNascimento(LocalDate.parse(funcionarioDTO.getDataNascimento()));
                break;

            default:
                throw new IllegalArgumentException("Invalid role");
        }

        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setName(usuarioDTO.getNome());

        usuario = usuarioRepository.save(usuario);
        usuarioDTO.setId(usuario.getId());
        return usuarioDTO;
    }
}