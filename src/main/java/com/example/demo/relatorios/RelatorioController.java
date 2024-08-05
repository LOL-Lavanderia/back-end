package com.example.demo.relatorios;

import com.example.demo.cliente.ClienteFielDTO;
import com.example.demo.pedido.PedidoService;
import com.example.demo.user.UsuarioDTO;
import com.example.demo.user.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/relatorios")
public class RelatorioController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private UsuarioService usuarioService;
    @GetMapping("/receita")
    public ResponseEntity<RelatorioReceitaResponse> relatorioReceita(
            @RequestParam("dataInicio") LocalDateTime dataInicio,
            @RequestParam("dataFim") LocalDateTime dataFim) {
        RelatorioReceitaResponse response = pedidoService.calcularReceitaEntreDatas(dataInicio, dataFim);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/clientes-fieis")
    public List<ClienteFielDTO> getClientesFieis() {
        return pedidoService.gerarRelatorioClientesFieis();
    }
}