package br.ufg.inf.integracao.receivenotification.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Notificacao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int identificador;
	private String mensagem;
	private Date data_recebimento;
	
	public Notificacao() {
		
	}
	
	@Override
	public String toString() {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
		String data = formato.format(this.data_recebimento);
		
		return 
			"Identificador: "+ this.identificador + "\n" + 
    		"Mensagem: "+ this.mensagem + "\n"+ 
    		"Data: "+ data;
	}

	public Notificacao(int identificador, String mensagem, Date data_recebimento) {
		super();
		this.identificador = identificador;
		this.mensagem = mensagem;
		this.setData_recebimento(data_recebimento);
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

	public Date getData_recebimento() {
		return data_recebimento;
	}

	public void setData_recebimento(Date data_recebimento) {
		this.data_recebimento = data_recebimento;
	}
}
