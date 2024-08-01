package com.example.demo.user;

import com.example.demo.cliente.Cliente;
import com.example.demo.endereco.Endereco;
import com.example.demo.endereco.EnderecoDTO;
import com.example.demo.funcionario.Funcionario;
import com.example.demo.telefone.Telefone;
import com.example.demo.telefone.TelefoneDTO;
import com.example.demo.user.auth.AuthDTO;
import com.example.demo.user.role.RoleDTO;
import com.example.demo.utils.PasswordService;
import com.example.demo.utils.exceptions.CpfJaCadastradoException;
import com.example.demo.utils.exceptions.EmailJaCadastradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
        // Verifica se o e-mail já existe
        if (usuarioRepository.existsByCpf(usuarioDTO.getRole().getCpf())) {
            throw new CpfJaCadastradoException("CPF já cadastrado");
        }
        if (usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
            throw new EmailJaCadastradoException("E-mail já cadastrado");
        }
        Usuario usuario;
        String senhaCriptografada;
        String salt;
        switch (usuarioDTO.getRole().getRole()) {
            case "client":
                usuario = new Cliente();
                Cliente cliente = (Cliente) usuario;
                RoleDTO clienteDTO = usuarioDTO.getRole();

                String senhaGerada = gerarSenhaAleatoria();
                salt = passwordService.generateSalt();
                senhaCriptografada= hashSenha(senhaGerada, salt);

                cliente.setPassword(senhaCriptografada);
                cliente.setSalt(salt);

                cliente.setCpf(clienteDTO.getCpf());
                cliente.setEndereco(clienteDTO.getEnderecos().stream().map(enderecoDTO -> {
                    Endereco endereco = new Endereco();
                    endereco.setLogradouro(enderecoDTO.getLogradouro());
                    endereco.setNumero(enderecoDTO.getNumero());
                    endereco.setBairro(enderecoDTO.getBairro());
                    endereco.setLocalidade(enderecoDTO.getLocalidade());
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

                funcionario.setDataNascimento(funcionarioDTO.getBirthDate());
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

    public UsuarioDTO authenticate(AuthDTO authDTO) {
        Usuario usuario = usuarioRepository.findByEmail(authDTO.getEmail());

        if (usuario == null) {
            throw new RuntimeException("Usuário não encontrado");
        }

        // Verifica a senha usando PasswordEncoder
        if (!passwordService.matches(authDTO.getPassword(), usuario.getSalt(), usuario.getPassword())) {
            throw new RuntimeException("Senha incorreta");
        }
        return convertToDTO(usuario);
    }

    private UsuarioDTO convertToDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setNome(usuario.getName());
        RoleDTO roleDTO = new RoleDTO();

        // Verifica se o usuário é um funcionário ou cliente
        if (usuario instanceof Funcionario) {
            // Define a role como "employee"
            roleDTO.setRole("employee");
            roleDTO.setBirthDate(((Funcionario) usuario).getDataNascimento());
            usuarioDTO.setRole(roleDTO);
        } else if (usuario instanceof Cliente) {
            // Define a role como "client"
            roleDTO.setRole("client");
            usuarioDTO.setRole(roleDTO);
        }

        // Adicionar outros campos conforme necessário
        return usuarioDTO;
    }

    public List<UsuarioDTO> getAllUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public UsuarioDTO updateUsuario(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Atualiza os campos básicos do usuário
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setName(usuarioDTO.getNome());

        // Verifica se a senha foi fornecida para atualização
        if (usuarioDTO.getSenha() != null && !usuarioDTO.getSenha().isEmpty()) {
            String salt = passwordService.generateSalt();
            String hashedPassword = passwordService.hashPasswordWithSalt(usuarioDTO.getSenha(), salt);
            usuario.setPassword(hashedPassword);
            usuario.setSalt(salt);
        }

        // Verifica se o usuário é um Funcionario para atualizar campos específicos
        if (usuario instanceof Funcionario) {
            ((Funcionario) usuario).setDataNascimento(usuarioDTO.getRole().getBirthDate());
        }

        // Salva o usuário atualizado
        Usuario updatedUsuario = usuarioRepository.save(usuario);

        // Retorna o usuário atualizado como DTO
        return convertToDTO(updatedUsuario);
    }

    public void deleteUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado");
        }
        usuarioRepository.deleteById(id);
    }

}