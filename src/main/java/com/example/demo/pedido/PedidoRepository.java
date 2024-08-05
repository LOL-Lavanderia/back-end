package com.example.demo.pedido;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
    List<Pedido> findByStatus(String status);
    List<Pedido> findByClienteId(Long clienteId);

    @Query("SELECT p FROM Pedido p WHERE p.openDate BETWEEN :startDate AND :endDate AND p.status NOT IN ('Em Aberto', 'Cancelado','Rejeitado','Recolhido','Aguardando pagamento')")
    List<Pedido> findByOpenDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query(value = "SELECT c.id as id, c.name as nome, COUNT(p.id) as quantidadePedidos, SUM(p.value) as receitaTotal " +
            "FROM Pedido p " +
            "JOIN Usuario c ON p.cliente_id = c.id " +
            "WHERE p.status NOT IN ('Em Aberto', 'Cancelado','Rejeitado','Recolhido','Aguardando pagamento') " +
            "GROUP BY c.id, c.name " +
            "ORDER BY COUNT(p.id) DESC, SUM(p.value) DESC " +
            "LIMIT 3",
            nativeQuery = true)
    List<Object[]> findTopClientes();

    @Query("SELECT p FROM Pedido p WHERE p.status NOT IN ('Em Aberto', 'Cancelado','Rejeitado','Recolhido','Aguardando pagamento')")
    List<Pedido> findByOpenDate();
}

