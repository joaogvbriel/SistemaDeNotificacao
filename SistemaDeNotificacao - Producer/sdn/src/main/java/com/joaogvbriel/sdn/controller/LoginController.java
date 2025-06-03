package com.joaogvbriel.sdn.controller;

import com.joaogvbriel.sdn.dto.UsuarioDTO;
import com.joaogvbriel.sdn.entity.Usuario;
import com.joaogvbriel.sdn.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public ResponseEntity<String> logar(@RequestBody UsuarioDTO usuario){
        try{
            return ResponseEntity.ok(loginService.logar(usuario));
        } catch (AuthenticationException ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }
}
