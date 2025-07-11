package com.Pro_User.User.DTO;

import java.util.List;

public class UsuarioDTO {
	private String nome;
	private String email;
	private String senha;
	private List<Long> perfis; // IDS dos perfis selecionados
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
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public List<Long> getPerfis() {
		return perfis;
	}
	public void setPerfis(List<Long> perfis) {
		this.perfis = perfis;
	}
	
	

}
