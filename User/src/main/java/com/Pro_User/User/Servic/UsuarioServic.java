package com.Pro_User.User.Servic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.Pro_User.User.model.Perfil;
import com.Pro_User.User.model.Usuario;
import com.Pro_User.User.repository.PerfilRepository;
import com.Pro_User.User.repository.UsuarioRepository;

@Service
public class UsuarioServic {

    private final UsuarioRepository usuarioRepository;

    private final PerfilRepository perfilRepository;

    public UsuarioServic(UsuarioRepository usuarioRepository, PerfilRepository perfilRepository) {
    	
        this.usuarioRepository = usuarioRepository;
        this.perfilRepository = perfilRepository;
    }

    public Usuario salvar(Usuario usuario, List<Long> idsPerfis) {
        System.out.println("Perfis recebidos: " + idsPerfis); // bom para debug
        Set<Perfil> perfis = new HashSet<>(perfilRepository.findAllById(idsPerfis));
        usuario.setPerfis(perfis);
        return usuarioRepository.save(usuario);
    }

    public boolean redefinirSenha(String email, String novaSenha) {
        Optional<Usuario> userOpt = usuarioRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            Usuario usuario = userOpt.get();
            usuario.setSenha(novaSenha); // Considere criptografar!
            usuarioRepository.save(usuario);
            return true;
        }
        return false;
    }


    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }

	public Optional<Usuario> buscarPorEmail(String email) {

		return usuarioRepository.findByEmail(email);
	}
    public Map<String, Long> contarUsuariosPorPerfil() {
    	Map<String, Long> resultado = new HashMap<>();
    	List<Usuario> usuarios = usuarioRepository.findAll();
    	for (Usuario usuario: usuarios) {
    		// cada usuário pode ter vários perfil!
    		usuario.getPerfis().forEach(perfil -> {
    			String nomePerfil = perfil.getNome();
    			resultado.put(nomePerfil, resultado.getOrDefault(nomePerfil, 0L) +1);
    		});
    	}
    	return resultado;
    }
	
}
