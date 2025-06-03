package com.joaogvbriel.sdn.service;

import com.joaogvbriel.sdn.entity.Usuario;
import com.joaogvbriel.sdn.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

     public Optional<Usuario> findById(UUID id){
        return usuarioRepository.findById(id);
    }

    public ResponseEntity<String> novoUsuario(Usuario novoUsuario){
        try {
            usuarioRepository.save(novoUsuario);
            return new ResponseEntity<>("Cadastrado com sucesso!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro!" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
