package com.example.demo.user;

import com.example.demo.user.auth.AuthDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping
    public ResponseEntity<UsuarioDTO> createUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        // Salva o usuário usando o serviço e retorna a resposta
        UsuarioDTO savedUsuario = usuarioService.saveUsuario(usuarioDTO);
        return ResponseEntity.ok(savedUsuario);
    }
    @PostMapping("/login")
    public ResponseEntity<UsuarioDTO> login(@RequestBody AuthDTO authDTO) {
        UsuarioDTO usuarioDTO = usuarioService.authenticate(authDTO);
        if (usuarioDTO != null) {
            return ResponseEntity.ok(usuarioDTO);
        } else {
            return ResponseEntity.status(401).build();
        }
    }

}
