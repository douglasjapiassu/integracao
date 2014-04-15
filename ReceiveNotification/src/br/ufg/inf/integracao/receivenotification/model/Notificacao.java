package br.ufg.inf.integracao.receivenotification.model;

import java.io.Serializable;

public class Notificacao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int identificador;
	private String mensagem;
	
	public Notificacao() {
		
	}
	
	public Notificacao(int identificador, String mensagem) {
		super();
		this.identificador = identificador;
		this.mensagem = mensagem;
	}

	public int getIdentificador() {
		return identificador;
	}
	
	public void setIdentificador(int identificador) {
		this.identificador = identificador;
	}
	
	public String getMensagem() {
		return mensagem;
	}
	
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}
