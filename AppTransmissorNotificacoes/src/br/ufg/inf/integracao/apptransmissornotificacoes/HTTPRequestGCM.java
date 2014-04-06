package br.ufg.inf.integracao.apptransmissornotificacoes;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONObject;

public class HTTPRequestGCM {
	
	private final static String GCM_URL = "https://android.googleapis.com/gcm/send";
	private final static String API_KEY = "AIzaSyAaxTEF85KgN7mLoIUSqbtoCX5jRalOQLk";
	
	public static void main(String[] args) {
		System.out.println("Teste requisição");
		enviaNotificacaoGCM();
	}
	
	public static void enviaNotificacaoGCM() {
		try {
			URL url = new URL(GCM_URL);
			HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
			
			conexao.setRequestMethod("POST");
			conexao.setRequestProperty("Content-Type", "application/json");
			conexao.setRequestProperty("Authorization", "key=" + API_KEY);
			conexao.setDoOutput(true);
			
			JSONObject objeto = new JSONObject();
			objeto.put("data", "mensagem");
			
			DataOutputStream writer = new DataOutputStream(conexao.getOutputStream());
			writer.writeBytes(objeto.toString());
			writer.flush();
			writer.close();
			
			
			int responseCode = conexao.getResponseCode();
            System.out.println("Código Resposta: " + responseCode);
 
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conexao.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
 
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
 
            System.out.println(response.toString());
		} catch (MalformedURLException e) {
			System.err.println("Ocorreu o seguinte erro:\n" + e.getMessage());
		} catch (IOException e) {
			System.err.println("Ocorreu o seguinte erro:\n" + e.getMessage());
		}
	}

}