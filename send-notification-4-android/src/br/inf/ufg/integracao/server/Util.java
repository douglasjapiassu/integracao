package br.inf.ufg.integracao.server;

import java.io.UnsupportedEncodingException;

import br.inf.ufg.integracao.mb.UsuarioMB;

/**
 * Classe contendo métodos utilitários
 * 
 * @author douglas.japiassu
 */
public class Util {
	
	public final static String GCM_URL = "https://android.googleapis.com/gcm/send";
	public final static String API_KEY = "AIzaSyCOhgqMOv3HJ4aoBMbQwzScMZtzQT0vKbM";
	public final static String REG_ID = "APA91bEhqcMRi_8-GkKhGtfQT7MjGUUmcuyX68g1mqjlWJZ2wcYeAd1C2WBBfp8ReLVFWhs1j0GsQZ1rKbb_0IGfMYfCh9-YHy6VGRFG3FSupLPbCOocyYj78oMe8hzyf60wtfgg6KnZ9E3W70SyF7LEFPamQmuO_w";
	
	public static boolean isTamanhoDaMensagemInvalido(String mensagem) {
		byte[] bytesDaMensagem = null;
		try {
			bytesDaMensagem = mensagem.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.err.println("Ocorreu o seguinte erro: " + e.getMessage());
		}
		
		if (bytesDaMensagem == null)
			return true;
		
		if (bytesDaMensagem.length < 5)
			return true;
		
		if (bytesDaMensagem.length > 2048)
			return true;
		
		return false;
	}
	
	public static void setAbaAtiva(String abaAtiva) {
		UsuarioMB.setAbaAtiva(abaAtiva);
	}
}
