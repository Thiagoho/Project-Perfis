package com.Pro_User.User.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Pro_User.User.model.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
	Perfil findByNome(String nome);

}
