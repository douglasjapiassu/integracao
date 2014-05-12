package br.ufg.inf.integracao.sendnotification4android.util;

import java.io.UnsupportedEncodingException;

/**
 * Classe contendo métodos utilitários
 * 
 * @author douglas.japiassu
 */
public class Util {
	public final static String GCM_URL = "https://android.googleapis.com/gcm/send";
	public final static String API_KEY = "AIzaSyCOhgqMOv3HJ4aoBMbQwzScMZtzQT0vKbM";
	
	public static boolean isTamanhoDaMensagemInvalido(String mensagem) {
		byte[] bytesDaMensagem = null;
		try {
			bytesDaMensagem = mensagem.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.err.println("Ocorreu o seguinte erro: " + e.getMessage());
		}
		
		if (bytesDaMensagem == null)
			return true;
		
		if (bytesDaMensagem.length > 2048)
			return true;
		
		return false;
	}
}
