package com.Pro_User.User.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Pro_User.User.model.Usuario;
import com.Pro_User.User.repository.UsuarioRepository;
import com.Pro_User.User.DTO.LoginDTO; // Crie esse DTO com email/senha (ou use Map<String, String>)

@RestController
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO dto) {
        Optional<Usuario> userOpt = usuarioRepository.findByEmail(dto.getEmail());

        // Aqui entra seu código:
        if (!userOpt.isPresent()) {
            // Retorna mensagem personalizada
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
        Usuario usuario = userOpt.get();
        if (!usuario.getSenha().equals(dto.getSenha())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha incorreta");
        }
        // (opcional) Limpe a senha antes de enviar para o frontend
        usuario.setSenha(""); 
        return ResponseEntity.ok(usuario);
    }
}
