package br.ufg.inf.integracao.sendnotification4android.client.model;

import java.io.Serializable;

/**
 * Classe que representa um Usuario cadastrado.
 * 
 * 
 * @author douglasjapiassu
 */
public class Usuario implements Serializable {

	private static final long serialVersionUID = 5439132308575576962L;

	private Long id;
	
	private String registrationId;
	
	private String nome;
	
	private String email;
	
	public Usuario(){}
	
	public Usuario(Long id, String nome, String email) {
		this.id = id;
		this.nome = nome;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
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

	@Override
	public String toString() {
		return id + " - " + nome + " - " + email;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		
		if ((obj == null) || (obj.getClass() != this.getClass())) {
			return false;
		}
		
		Usuario outro = (Usuario) obj;
		boolean equal = (id != null && id.equals(outro.id)) 
				|| (nome != null && nome.equals(outro.nome))
				|| (email != null && email.equals(outro.email));
		return equal;
	}
	
	@Override
	public int hashCode() {
		int hash = 17;
		
		hash = (31 * hash) + (id == null ? 0 : id.intValue());
		hash = (31 * hash) + (nome == null ? 0 : nome.hashCode());
		hash = (31 * hash) + (email == null ? 0 : email.hashCode());
		
		return hash;
	}
}
