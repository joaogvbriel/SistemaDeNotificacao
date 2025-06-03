package com.joaogvbriel.sdn.service;

import com.joaogvbriel.sdn.auth.JwtServiceGenerator;
import com.joaogvbriel.sdn.dto.UsuarioDTO;
import com.joaogvbriel.sdn.entity.Usuario;
import com.joaogvbriel.sdn.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private JwtServiceGenerator jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;


    public String logar(UsuarioDTO usuario) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            usuario.getUsername(),
                            usuario.getPassword()
                    )
            );
        } catch (Exception e) {
            System.out.println("Erro ao autenticar: " + e.getMessage());
            throw e;
        }
        Usuario user = usuarioRepository.findByUsername(usuario.getUsername()).get();
        String jwtToken = jwtService.generateToken(user);
        return jwtToken;
    }
}
