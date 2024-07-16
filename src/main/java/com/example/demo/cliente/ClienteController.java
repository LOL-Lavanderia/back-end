package com.example.demo.cliente;

import com.example.demo.endereco.Endereco;
import com.example.demo.endereco.EnderecoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path= "api/v1/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;
    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);


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
    public void registerNewCliente(@RequestBody ClienteComEnderecoDTO clienteComEnderecoDTO){
        logger.info("ClienteComEnderecoDTO recebido: {}", clienteComEnderecoDTO);
        ClienteDTO clienteDTO = (clienteComEnderecoDTO.getClienteDTO());
        EnderecoDTO enderecoDTO = (clienteComEnderecoDTO.getEnderecoDTO());
        clienteService.addNewCliente(clienteDTO, enderecoDTO);
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
        clienteService.updateCliente(id, nome, email, telefone);

    }

}

