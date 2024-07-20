package com.example.demo.pedido;

import com.example.demo.cliente.Cliente;
import com.example.demo.roupa.Roupa;
import com.example.demo.roupa.RoupaDTO;
import com.example.demo.user.Usuario;
import com.example.demo.user.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

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
        pedido.setRoupas(pedidoDTO.getRoupas().stream().map(this::convertToEntity).collect(Collectors.toList()));

        pedido = pedidoRepository.save(pedido);
        return convertToDTO(pedido);
    }

    private Roupa convertToEntity(RoupaDTO roupaDTO) {
        Roupa roupa = new Roupa();
        roupa.setName(roupaDTO.getName());
        roupa.setPrice(roupaDTO.getPrice());
        roupa.setQuantity(roupaDTO.getQuantity());
        return roupa;
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
