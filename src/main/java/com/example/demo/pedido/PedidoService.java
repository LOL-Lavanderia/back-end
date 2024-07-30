package com.example.demo.pedido;

import com.example.demo.cliente.Cliente;
import com.example.demo.roupa.Roupa;
import com.example.demo.roupa.RoupaDTO;
import com.example.demo.roupa.RoupaRepository;
import com.example.demo.user.Usuario;
import com.example.demo.user.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private RoupaRepository roupaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<PedidoDTO> getAllPedidos() {
        return pedidoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public List<PedidoDTO> getOpenPedidos() {
        return pedidoRepository.findByStatus("Em Aberto").stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public List<PedidoDTO> getPedidosByClienteId(Long clienteId) {
    return pedidoRepository.findByClienteId(clienteId).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public PedidoDTO getPedidoById(Long id) {
        return pedidoRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public PedidoDTO getPedidoByClienteAndPedidoId(Long clienteId, Long pedidoId) {
    // Busca o pedido pelo ID do pedido
    Pedido pedido = pedidoRepository.findById(pedidoId)
            .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    
    // Verifica se o pedido pertence ao cliente especificado
    if (!pedido.getCliente().getId().equals(clienteId)) {
        throw new RuntimeException("Pedido não pertence ao cliente especificado");
    }
    
    // Converte o pedido para DTO e retorna
    return convertToDTO(pedido);
}

    public PedidoDTO savePedido(PedidoDTO pedidoDTO) {
        Pedido pedido = new Pedido();
        pedido.setTime(pedidoDTO.getTime());
        pedido.setStatus(pedidoDTO.getStatus());
        pedido.setValue(pedidoDTO.getValue());
        pedido.setCloseDate(pedidoDTO.getCloseDate());
        pedido.setOpenDate(pedidoDTO.getOpenDate());

        Usuario usuario = usuarioRepository.findById(pedidoDTO.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        if (!(usuario instanceof Cliente)) {
            throw new RuntimeException("O usuário com ID " + pedidoDTO.getClienteId() + " não é um cliente.");
        }

        pedido.setCliente((Cliente) usuario);

        List<Roupa> roupas = pedidoDTO.getRoupas().stream()
                .map(roupaDTO -> roupaRepository.findById(roupaDTO.getId())
                        .orElseThrow(() -> new RuntimeException("Roupa não encontrada")))
                .collect(Collectors.toList());

        pedido.setRoupas(roupas);
        pedido = pedidoRepository.save(pedido);
        return convertToDTO(pedido);
    }

    public PedidoDTO updatePedido(Long id, PedidoDTO pedidoDTO) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        pedido.setTime(pedidoDTO.getTime());
        pedido.setStatus(pedidoDTO.getStatus());
        pedido.setValue(pedidoDTO.getValue());
        pedido.setCloseDate(pedidoDTO.getCloseDate());
        pedido.setOpenDate(pedidoDTO.getOpenDate());

        Usuario usuario = usuarioRepository.findById(pedidoDTO.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        if (!(usuario instanceof Cliente)) {
            throw new RuntimeException("O usuário com ID " + pedidoDTO.getClienteId() + " não é um cliente.");
        }

        pedido.setCliente((Cliente) usuario);

        List<Roupa> roupas = pedidoDTO.getRoupas().stream()
                .map(roupaDTO -> roupaRepository.findById(roupaDTO.getId())
                        .orElseThrow(() -> new RuntimeException("Roupa não encontrada")))
                .collect(Collectors.toList());

        pedido.setRoupas(roupas);
        pedido = pedidoRepository.save(pedido);
        return convertToDTO(pedido);
    }

    public boolean deletePedido(Long id) {
        if (pedidoRepository.existsById(id)) {
            pedidoRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    private RoupaDTO convertToDTO(Roupa roupa) {
        RoupaDTO roupaDTO = new RoupaDTO();
        roupaDTO.setId(roupa.getId());
        roupaDTO.setName(roupa.getName());
        roupaDTO.setPrice(roupa.getPrice());
        roupaDTO.setQuantity(roupa.getQuantity());
        return roupaDTO;
    }

    private PedidoDTO convertToDTO(Pedido pedido) {
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setId(pedido.getId());
        pedidoDTO.setTime(pedido.getTime());
        pedidoDTO.setStatus(pedido.getStatus());
        pedidoDTO.setValue(pedido.getValue());
        pedidoDTO.setCloseDate(pedido.getCloseDate());
        pedidoDTO.setOpenDate(pedido.getOpenDate());
        pedidoDTO.setClienteId(pedido.getCliente().getId());
        pedidoDTO.setRoupas(pedido.getRoupas().stream().map(this::convertToDTO).collect(Collectors.toList()));
        return pedidoDTO;
    }
}