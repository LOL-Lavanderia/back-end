package com.example.demo.cliente;

import com.example.demo.utils.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final JavaMailSender javaMailSender;

    private final PasswordService passwordService;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository, JavaMailSender javaMailSender, PasswordService passwordService) {

        this.clienteRepository = clienteRepository;
        this.javaMailSender = javaMailSender;
        this.passwordService = passwordService;
    }

    public List<Cliente> getClientes() {
        return clienteRepository.findAll();
    }

    @Transactional
    public void addNewCliente(Cliente cliente) {

        String senhaGerada = gerarSenha(cliente);
        enviarEmail(cliente.getEmail(), senhaGerada);

        clienteRepository.save(cliente);
    }

    private void enviarEmail(String email, String senhaGerada) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Sua Nova Senha");
        message.setText("Sua nova senha é: " + senhaGerada);

        MailSender javaMailSender;
        this.javaMailSender.send(message);
    }

    public String gerarSenha(Cliente cliente) {
        String senha = String.format("%04d", (int) (Math.random() * 10000));
        String salt = passwordService.generateSalt();
        String senhaEncriptografada = passwordService.hashPasswordWithSalt(senha,salt);
        cliente.setSalt(salt);
        cliente.setSenha(senhaEncriptografada);

        return senha;
    }


    public void deleteCliente(Long id) {
        clienteRepository.deleteById(id);
    }

    public Cliente getCliente(Long id) {
        System.out.println("Tentando buscar Cliente com ID:{" + id + "}");

        Optional<Cliente> clienteOptional = clienteRepository.findById(id);

        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            System.out.println("Cliente encontrado: {}" + cliente);
            return cliente;
        } else {
            System.out.println("Cliente não encontrado com ID: {}" + id);
            return null;
        }
    }
    @Transactional
    public void updateCliente(Long id, String name, String email, String endereco, String telefone) {
        Cliente clienteUpdate = clienteRepository.findById(id).orElseThrow(() -> new IllegalStateException("Cliente with id" + id + "not found."));
        if(clienteUpdate.getNome().equals(name)){
            System.out.println("O nome já está atualizado");
        }
        else{
            clienteUpdate.setNome(name);
        }

        if(clienteUpdate.getEmail().equals(email)){
            System.out.println("E-mail já está atualizado.");

        }
        else{
            clienteUpdate.setEmail(email);
        }

        if(clienteUpdate.getEndereco().equals(endereco)){
            System.out.println("Endereço já está atualizado.");
        }
        else{
            clienteUpdate.setEndereco(endereco);
        }

        if(clienteUpdate.getTelefone().equals(telefone)){
            System.out.println("Telefone já está atualizado.");
        }
        else{
            clienteUpdate.setEndereco(telefone);
        }

    }
}