package com.example.demo.endereco;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/v1/endereco")
public class EnderecoController {

    private final EnderecoService enderecoService;

    @Autowired
    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @GetMapping
    public List<Endereco> getAllEnderecos() {
        return enderecoService.getAllEnderecos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Endereco> getEnderecoById(@PathVariable Long id) {
        Endereco endereco = enderecoService.getEnderecoById(id);
        return ResponseEntity.ok(endereco);
    }

    @PostMapping
    public Endereco createEndereco(@RequestBody Endereco endereco) {
        return enderecoService.saveEndereco(endereco);
    }

    @PutMapping("/{id}")
    public Endereco updateEndereco(@PathVariable Long id, @RequestBody Endereco enderecoDetails) {
        Endereco endereco = enderecoService.getEnderecoById(id);
        if (endereco == null) {
            return null;
        }
//        endereco.setRua(enderecoDetails.getRua());
//        endereco.setNumero(enderecoDetails.getNumero());
//        endereco.setCidade(enderecoDetails.getCidade());
//        endereco.setEstado(enderecoDetails.getEstado());
//        endereco.setCep(enderecoDetails.getCep());
        return enderecoService.saveEndereco(endereco);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEndereco(@PathVariable Long id) {
        enderecoService.deleteEndereco(id);
        return ResponseEntity.noContent().build();
    }

//    @GetMapping("/cep/{cep}")
//    public ResponseEntity<Endereco> buscarEnderecoPorCep(@PathVariable String cep) {
//        Endereco endereco = enderecoService.buscarEnderecoPorCep(cep);
//        return ResponseEntity.ok(endereco);
//    }

    @GetMapping("/cep/{cep}")
    public ResponseEntity<Endereco> getCep(@PathVariable String cep) {
        RestTemplate restTemplate = new RestTemplate();
        Endereco newCep = restTemplate.getForObject("https://viacep.com.br/ws/" + cep + "/json/", Endereco.class);
        return ResponseEntity.ok(newCep);
    }

}