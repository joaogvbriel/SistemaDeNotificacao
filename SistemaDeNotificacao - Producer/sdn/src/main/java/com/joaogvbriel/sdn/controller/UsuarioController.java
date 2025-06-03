package com.joaogvbriel.sdn.controller;

import com.joaogvbriel.sdn.entity.Usuario;
import com.joaogvbriel.sdn.repository.UsuarioRepository;
import com.joaogvbriel.sdn.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{id}")
    public Optional<Usuario> findByid(@PathVariable UUID id){
        return usuarioService.findById(id);
    }

    @PostMapping("/new")
    public ResponseEntity<String> novoUsuario(@RequestBody Usuario novoUsuario){
        return usuarioService.novoUsuario(novoUsuario);
    }
}
