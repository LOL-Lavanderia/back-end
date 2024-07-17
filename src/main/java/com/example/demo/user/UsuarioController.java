package com.example.demo.user;

import com.example.demo.cliente.ClienteDTO;
import com.example.demo.funcionario.FuncionarioDTO;
import com.example.demo.user.UsuarioDTO;
import com.example.demo.user.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping
    public ResponseEntity<UsuarioDTO>createUsuario(@RequestBody String usuarioJson) {
        try {
            UsuarioDTO usuarioDTO = parseUsuarioDTO(usuarioJson);
            UsuarioDTO savedUsuario = usuarioService.saveUsuario(usuarioDTO);
            return ResponseEntity.ok(savedUsuario);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private UsuarioDTO parseUsuarioDTO(String usuarioJson) throws IOException {
        // Primeiro, mapear para um UsuarioDTO genérico para checar o role
        UsuarioDTO tempUsuarioDTO = objectMapper.readValue(usuarioJson, UsuarioDTO.class);

        // Agora, mapear para o tipo específico com base no role
        if ("client".equals(tempUsuarioDTO.getRole())) {
            return objectMapper.readValue(usuarioJson, ClienteDTO.class);
        } else if ("employee".equals(tempUsuarioDTO.getRole())) {
            return objectMapper.readValue(usuarioJson, FuncionarioDTO.class);
        } else {
            throw new IllegalArgumentException("Invalid role: " + tempUsuarioDTO.getRole());
        }
    }
}