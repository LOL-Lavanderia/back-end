package com.example.demo.pedido;

import com.example.demo.cliente.Cliente;
import com.example.demo.cliente.ClienteDTO;
import com.example.demo.cliente.ClienteService;
import com.example.demo.relatorios.RelatorioReceitaResponse;
import com.example.demo.roupa.Roupa;
import com.example.demo.roupa.RoupaDTO;
import com.example.demo.roupa.RoupaRepository;
import com.example.demo.user.Usuario;
import com.example.demo.user.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private RoupaRepository roupaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PedidoRoupaRepository pedidoRoupaRepository; // Novo repositório para PedidoRoupa


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
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        if (!pedido.getCliente().getId().equals(clienteId)) {
            throw new RuntimeException("Pedido não pertence ao cliente especificado");
        }

        return convertToDTO(pedido);
    }

    @Transactional
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

        // Salvar o pedido primeiro para obter o ID gerado
        pedido = pedidoRepository.save(pedido);

        // Adicionar roupas ao pedido
        for (RoupaDTO roupaDTO : pedidoDTO.getRoupas()) {
            Roupa roupa = roupaRepository.findById(roupaDTO.getId())
                    .orElseThrow(() -> new RuntimeException("Roupa não encontrada"));

            PedidoRoupa pedidoRoupa = new PedidoRoupa();
            pedidoRoupa.setPedido(pedido);
            pedidoRoupa.setRoupa(roupa);
            pedidoRoupa.setQuantidade(roupaDTO.getQuantity());

            pedidoRoupaRepository.save(pedidoRoupa);
        }

        return convertToDTO(pedido);
    }

    @Transactional
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

        // Remover todas as roupas antigas associadas ao pedido
        pedidoRoupaRepository.deleteByPedidoId(id);

        // Adicionar roupas atualizadas ao pedido
        for (RoupaDTO roupaDTO : pedidoDTO.getRoupas()) {
            Roupa roupa = roupaRepository.findById(roupaDTO.getId())
                    .orElseThrow(() -> new RuntimeException("Roupa não encontrada"));

            PedidoRoupa pedidoRoupa = new PedidoRoupa();
            pedidoRoupa.setPedido(pedido);
            pedidoRoupa.setRoupa(roupa);
            pedidoRoupa.setQuantidade(roupaDTO.getQuantity());

            pedidoRoupaRepository.save(pedidoRoupa);
        }

        return convertToDTO(pedido);
    }

    @Transactional
    public boolean deletePedido(Long id) {
        if (pedidoRepository.existsById(id)) {
            pedidoRoupaRepository.deleteByPedidoId(id); // Remover relações de PedidoRoupa
            pedidoRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
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

        List<RoupaDTO> roupasDTO = pedido.getPedidoRoupas().stream()
                .map(pr -> {
                    RoupaDTO roupaDTO = new RoupaDTO();
                    roupaDTO.setId(pr.getRoupa().getId());
                    roupaDTO.setName(pr.getRoupa().getName());
                    roupaDTO.setPrice(pr.getRoupa().getPrice());
                    roupaDTO.setTime(pr.getRoupa().getTime());
                    roupaDTO.setQuantity(pr.getQuantidade());
                    return roupaDTO;
                })
                .collect(Collectors.toList());

        pedidoDTO.setRoupas(roupasDTO);
        return pedidoDTO;
    }
    public RelatorioReceitaResponse calcularReceitaEntreDatas(LocalDateTime dataInicio, LocalDateTime dataFim) {
        List<PedidoReceita> pedidosResumidos = pedidoRepository.findByOpenDateBetween(dataInicio, dataFim);
        Double totalReceita = pedidosResumidos.stream()
                .mapToDouble(PedidoReceita::getValue)
                .sum();

        List<PedidoDTO> pedidosDTO = pedidosResumidos.stream()
                .map(projecao -> new PedidoDTO(projecao.getId(), projecao.getOpenDate(), projecao.getCloseDate(), projecao.getValue()))
                .collect(Collectors.toList());

        RelatorioReceitaResponse response = new RelatorioReceitaResponse();
        response.setPedidos(pedidosDTO);
        response.setTotalReceita(totalReceita);

        return response;
    }

}