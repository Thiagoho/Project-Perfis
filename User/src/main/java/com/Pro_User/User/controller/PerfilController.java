package com.Pro_User.User.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Pro_User.User.Servic.PerfilServic;
import com.Pro_User.User.model.Perfil;

@RestController
@RequestMapping("/perfis")
public class PerfilController {
	private final PerfilServic perfilServic;
	
	public PerfilController(PerfilServic perfilServic) {
		this.perfilServic = perfilServic;
	}
	@PostMapping
	public ResponseEntity<Perfil> criarPerfil(@RequestBody Perfil perfil) {
		Perfil perfilSalvo = perfilServic.salvar(perfil);
		return ResponseEntity.ok(perfilSalvo);
	}
	@GetMapping
	public List<Perfil> listarPerfil() {
		return perfilServic.listarTodos();
	}
	@GetMapping("/{id}")
	public ResponseEntity<Perfil> buscarPorId(@PathVariable Long id) {
		return perfilServic.buscarPorId(id)
		.map(ResponseEntity::ok)
		.orElse(ResponseEntity.notFound().build());
		
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		perfilServic.deletar(id);
		return ResponseEntity.noContent().build();
	}
	
	
}
