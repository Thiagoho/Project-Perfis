package com.Pro_User.User.model;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
@Entity
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUsuario;
	
	@Column(nullable = false, length= 50)
	private String nome;
		
	@Column(nullable = false, length = 50, unique = true)
	private String email;
	
	
	private String Senha;
	
	private LocalDateTime dataCadastro = LocalDateTime.now();
		
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
	    name = "usuario_perfil",
	    joinColumns = @JoinColumn(name = "id_usuario"),
	    inverseJoinColumns = @JoinColumn(name = "id_perfil")
	)
	@JsonManagedReference
	private Set<Perfil> perfis = new HashSet<>();




	// Construtor padrão (obrigatório para JPA
	public Usuario() {}

	//Get & Set
	public Long getIdUsuario() {
		return idUsuario;
	}


	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getSenha() {
		return Senha;
	}


	public void setSenha(String senha) {
		this.Senha = senha;
	}

	public Set<Perfil> getPerfis() {
		return perfis;
	}

	public void setPerfis(Set<Perfil> perfis) {
	    this.perfis = perfis;
	}


	
}
