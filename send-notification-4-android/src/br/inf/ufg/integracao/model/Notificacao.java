package br.inf.ufg.integracao.model;

import java.io.Serializable;
import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
@SuppressWarnings("serial")
public class Notificacao implements Serializable {

	@Id
	private Long id;
	private String mensagem;
	private Date dataEnvio;
	
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
	
	public String getMensagemReduzida() {
		if(mensagem.getBytes().length > 60)
			return mensagem.substring(0, 60);
		
		return mensagem;
	}

}
