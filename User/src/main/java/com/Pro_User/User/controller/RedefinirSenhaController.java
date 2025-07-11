package com.Pro_User.User.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Pro_User.User.DTO.RedefinirSenhaDTO;
import com.Pro_User.User.Servic.UsuarioServic;

@RestController
public class RedefinirSenhaController {
	
	@Autowired
	private UsuarioServic usuarioServic;
	
	@PostMapping("redefinir-senha")
	public ResponseEntity<String> redefinirSenha(@RequestBody RedefinirSenhaDTO dto) {
		boolean resultado = usuarioServic.redefinirSenha(dto.getEmail(),dto.getNovaSenha());
		if(resultado) {
			return ResponseEntity.ok("Senha redefinida com sucesso.");
		} else { 
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Usuário não cadastrado.");
		}
	}
}
