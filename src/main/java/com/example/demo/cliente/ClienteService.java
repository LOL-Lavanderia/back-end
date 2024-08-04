package com.example.demo.cliente;

import com.example.demo.endereco.Endereco;
import com.example.demo.endereco.EnderecoDTO;
import com.example.demo.pedido.PedidoRepository;
import com.example.demo.user.UsuarioDTO;
import com.example.demo.user.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ClienteService {


    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    public List<ClienteDTO> listarTodosOsClientes() {
        // Obtém a lista de clientes do repositório
        List<Cliente> clientes = usuarioRepository.findAllClientes();

        // Converte a lista de clientes para a lista de ClienteDTO

        return clientes.stream()
                .map(this::convertToDTO)  // Corrigido para referência ao método
                .collect(Collectors.toList());
    }

//    public List<ClienteDTO> listarTresClientesComMaiorReceita() {
//        // Exemplo de implementação
//        // Esta função deve ser ajustada para refletir a estrutura real do seu banco de dados e modelo de negócios
//        List<Cliente> clientes = pedidoRepository.findAllClientes();
//        return clientes.stream()
//                .sorted((c1, c2) -> c2.getReceita().compareTo(c1.getReceita()))
//                .limit(3)
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//    }


    // Método para converter Cliente para ClienteDTO
    private ClienteDTO convertToDTO(Cliente cliente) {
        // Cria o ClienteDTO usando o construtor completo
        return new ClienteDTO(
                new UsuarioDTO(cliente.getId(), cliente.getName(), cliente.getEmail()));
    }


}
