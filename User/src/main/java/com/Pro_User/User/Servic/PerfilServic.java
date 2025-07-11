package com.Pro_User.User.Servic;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.Pro_User.User.model.Perfil;
import com.Pro_User.User.repository.PerfilRepository;

@Service	
public class PerfilServic {
	private final PerfilRepository perfilRepository;
	
	public PerfilServic(PerfilRepository perfilRepository) {
		this.perfilRepository =perfilRepository;
		
	}
	public Perfil salvar(Perfil perfil) {
		return perfilRepository.save(perfil);
		
	}
	public List<Perfil> listarTodos() {
		return perfilRepository.findAll();
	}
	public Optional<Perfil> buscarPorId(Long id) {
		return perfilRepository.findById(id);
	}
	
	public void deletar(Long id) {
		perfilRepository.deleteById(id);
	}
}
