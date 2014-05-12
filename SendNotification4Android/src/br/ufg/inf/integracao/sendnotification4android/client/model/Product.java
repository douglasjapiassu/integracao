package br.ufg.inf.integracao.sendnotification4android.client.model;

import java.io.Serializable;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.i18n.client.NumberFormat;

/**
 * Classe que representa um Usuario cadastrado.
 * 
 * 
 * @author douglasjapiassu
 */
public class Product extends JavaScriptObject {

	private Long id;
	
	private String registrationId;
	
	private String nome;
	
	private String email;
	
	public Product(){}
	
	public Product(Long id, String nome, String email) {
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
}
