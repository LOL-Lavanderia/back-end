package com.example.demo.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping(path= "api/v1/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable("id")Long id){
        Cliente cliente = clienteService.getCliente(id);
        return ResponseEntity.ok(cliente);

    }
    @GetMapping
    public List<Cliente> hello(){
        return  clienteService.getClientes();
    }

    @PostMapping
    public void registerNewCliente(@RequestBody Cliente cliente){
        clienteService.addNewCliente(cliente);
    }

    @DeleteMapping(path = "{clienteId}")
    public void deleteCliente(@PathVariable("clienteId") Long id){
        clienteService.deleteCliente(id);
    }

    @PutMapping(path = "{clienteId}")
    public void updateStudant(@PathVariable("clienteId") Long id,
                              @RequestParam(required = false) String nome,
                              @RequestParam(required = false) String email,
                              @RequestParam(required = false) String endereco,
                              @RequestParam(required = false) String telefone

    ){
        clienteService.updateCliente(id, nome, email, endereco, telefone);

    }




}

