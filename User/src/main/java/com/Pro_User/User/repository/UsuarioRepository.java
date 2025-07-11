package com.Pro_User.User.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Pro_User.User.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	Optional<Usuario> findByEmail(String email); //Correto
	boolean existsByEmail(String email); //Para evitar cadastro Duplicado

}
