package br.ufg.inf.integracao.apptransmissornotificacoes;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONObject;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class HTTPRequestGCM {
	
	private final static String GCM_URL = "https://android.googleapis.com/gcm/send";
	private final static String API_KEY = "";
	private final static String REG_ID = "";
	

	public static void main(String[] args) {
		System.out.println("Teste requisi��o");
		//enviaNotificacaoGCM();
		enviaNotificacaoGCMSender();
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
			JSONObject data = new JSONObject();
			data.put("mensagem", "mensagem");
			objeto.put("data", data);
			String regId1 = "";
			String regId2 = "";
			//objeto.put("registration_ids", (new String[]{"a","b"}));
			objeto.put("registration_ids", (new String[]{regId1}));
			
			DataOutputStream writer = new DataOutputStream(conexao.getOutputStream());
			writer.writeBytes(objeto.toString());
			writer.flush();
			writer.close();
			
			
			int responseCode = conexao.getResponseCode();
            System.out.println("C�digo Resposta: " + responseCode);
 
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
	
	public static void enviaNotificacaoGCMSender(){
		Sender sender = new Sender(API_KEY);
		
		Message message = new Message.Builder()
		   .collapseKey("1")
		   .timeToLive(3)
		   .delayWhileIdle(true)
		   .addData("mensagem","Mensagem enviada pelo GCM.")
		   .build();
		
		Result result = null;
		
		//metodo send envia a mensagem e retorna a resposta da requisicao
		try {
			result = sender.send(message,REG_ID, 1);
		} catch (IOException e) {
			System.err.println("Ocorreu o seguinte erro:\n"+e.getMessage());
			e.printStackTrace();
		}
		
		//Resposta da requisi��o
		if (result != null)
			System.out.println(result.toString());
	}

}
