package br.ufg.inf.integracao.receivenotification.model;

import java.io.Serializable;

public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int identificador;
	private String nome;
	private String email;
	
	public Usuario() {
		
	}
	
	public Usuario(int identificador, String nome, String email) {
		super();
		this.identificador = identificador;
		this.nome = nome;
		this.email = email;
	}

	public int getIdentificador() {
		return identificador;
	}
	
	public void setIdentificador(int identificador) {
		this.identificador = identificador;
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
	
}
