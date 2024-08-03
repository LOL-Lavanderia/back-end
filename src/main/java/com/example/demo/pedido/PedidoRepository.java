package com.example.demo.pedido;

import com.example.demo.cliente.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
    List<Pedido> findByStatus(String status);
    List<Pedido> findByClienteId(Long clienteId);

    @Query("SELECT p.id AS id, p.openDate AS openDate, p.closeDate AS closeDate, p.value AS value FROM Pedido p WHERE p.closeDate BETWEEN :startDate AND :endDate")
    List<PedidoReceita> findByOpenDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

}
