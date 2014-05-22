package br.inf.ufg.integracao.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;

/**
 * Classe de modelo que representa uma mercadoria. A mercadoria é um objeto persistido, por isso utilizamos o nome entidade.
 * 
 * <p>As funcionalidades desse sistema demonstração são concentradas no cadastro (CRUD) de mercadorias.</p>
 * 
 * <p>Essa entidade é mapeada com anotações do <code>Objectify</code>, um framework para persistência alto-nível no datastore (mecanismo de persistência do <code>App Engine</code>).</p>
 * 
 * @author YaW Tecnologia
 */
@Entity
@SuppressWarnings("serial")
public class Notificacao implements Serializable {

	@Id
	private Long id;
	private String mensagem;
	private Date dataEnvio;
	@Ignore private List<Usuario> usuarios;
	
	public Notificacao() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Date getDataEnvio() {
		return dataEnvio;
	}

	public void setDataEnvio(Date dataEnvio) {
		this.dataEnvio = dataEnvio;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
}
