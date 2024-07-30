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
    public UsuarioDTO createMockCliente() {
        RoleDTO clienteDTO = new RoleDTO();
        UsuarioDTO usuarioClienteDTO = new UsuarioDTO();
        usuarioClienteDTO.setEmail("mockcliente@example.com");
        usuarioClienteDTO.setNome("Mock Cliente");
        usuarioClienteDTO.setSenha("mockPassword");
        clienteDTO.setRole("client");


        clienteDTO.setCpf("12345678900");
        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setLogradouro("Rua Mock");
        enderecoDTO.setNumero("123");
        enderecoDTO.setBairro("Bairro Mock");
        enderecoDTO.setLocalidade("Cidade Mock");
        enderecoDTO.setCep("12345678");

        clienteDTO.setEnderecos(Collections.singletonList(enderecoDTO));

        TelefoneDTO telefoneDTO = new TelefoneDTO();
        telefoneDTO.setNumero("123456789");
        clienteDTO.setTelefones(Collections.singletonList(telefoneDTO));
        usuarioClienteDTO.setRole(clienteDTO);
        return saveUsuario(usuarioClienteDTO);
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
        switch (usuarioDTO.getRole().getRole()) {
            case "client":
                usuario = new Cliente();
                break;
            case "employee":
                usuario = new Funcionario();
                break;
            default:
                throw new IllegalArgumentException("Invalid role");
        }

        // Configuração do usuário com base no DTO
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setName(usuarioDTO.getNome());
        String salt = passwordService.generateSalt();
        String senhaCriptografada = passwordService.hashPasswordWithSalt(usuarioDTO.getSenha(), salt);
        usuario.setPassword(senhaCriptografada);
        usuario.setSalt(salt);

        // Configuração específica para Cliente
        if (usuario instanceof Cliente) {
            Cliente cliente = (Cliente) usuario;
            cliente.setCpf(usuarioDTO.getRole().getCpf());
            // Outras configurações específicas do cliente...
        } else if (usuario instanceof Funcionario) {
            Funcionario funcionario = (Funcionario) usuario;
            funcionario.setDataNascimento(usuarioDTO.getRole().getBirthDate());
            // Outras configurações específicas do funcionário...
        }

        // Salva o usuário
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
}