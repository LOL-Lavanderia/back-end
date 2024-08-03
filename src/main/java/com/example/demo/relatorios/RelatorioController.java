package com.example.demo.relatorios;

import com.example.demo.cliente.ClienteDTO;
import com.example.demo.pedido.PedidoService;
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

    @GetMapping("/receita")
    public ResponseEntity<RelatorioReceitaResponse> relatorioReceita(
            @RequestParam("dataInicio") LocalDateTime dataInicio,
            @RequestParam("dataFim") LocalDateTime dataFim) {
        RelatorioReceitaResponse response = pedidoService.calcularReceitaEntreDatas(dataInicio, dataFim);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @GetMapping("/clientes")
//    public ResponseEntity<List<ClienteDTO>> relatorioClientes() {
//        List<ClienteDTO> clientes = pedidoService.listarTodosOsClientes();
//        return new ResponseEntity<>(clientes, HttpStatus.OK);
//    }
//
//    @GetMapping("/clientes-fais")
//    public ResponseEntity<List<ClienteDTO>> relatorioClientesFais() {
//        List<ClienteDTO> clientesFais = pedidoService.listarTresClientesComMaiorReceita();
//        return new ResponseEntity<>(clientesFais, HttpStatus.OK);
//    }
}