package com.example.demo.pedido;

import java.time.LocalDateTime;

public interface PedidoReceita {
    Long getId();
    LocalDateTime getOpenDate();
    LocalDateTime getCloseDate();
    Double getValue();
}

