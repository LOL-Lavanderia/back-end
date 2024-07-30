package com.example.demo.pedido;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRoupaRepository extends JpaRepository<PedidoRoupa, Long> {

    // Método para deletar todas as entradas de PedidoRoupa associadas a um pedido específico
    void deleteByPedidoId(Long pedidoId);

    // Método para encontrar todas as roupas associadas a um pedido
    List<PedidoRoupa> findByPedidoId(Long pedidoId);

}