package br.inf.ufg.integracao.server;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONObject;

import br.inf.ufg.integracao.model.Notificacao;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class EnviaNotificacaoGCM {
	
	public String enviaNotificacaoGCM(Notificacao notificacao) {
		String retorno = "";
		try {
			String[] ids = new String[notificacao.getUsuarios().size()];
			for (int i = 0; i < notificacao.getUsuarios().size(); i++) {
				ids[i] = notificacao.getUsuarios().get(i).getRegistrationId();
			}
			
			URL url = new URL(Util.GCM_URL);
			HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
			
			conexao.setRequestMethod("POST");
			conexao.setRequestProperty("Content-Type", "application/json");
			conexao.setRequestProperty("Authorization", "key=" + Util.API_KEY);
			conexao.setDoOutput(true);
			
			JSONObject objeto = new JSONObject();
			JSONObject data = new JSONObject();
			data.put("mensagem", notificacao.getMensagem());
			objeto.put("data", data);
			objeto.put("registration_ids", ids);
			
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
 
            retorno = response.toString();
            System.out.println(retorno);
		} catch (MalformedURLException e) {
			System.err.println("Ocorreu o seguinte erro:\n" + e.getMessage());
		} catch (IOException e) {
			System.err.println("Ocorreu o seguinte erro:\n" + e.getMessage());
		}
		
		return retorno;
	}
	
	public static void enviaNotificacaoGCMSender(){
		Sender sender = new Sender(Util.API_KEY);
		
		Message message = new Message.Builder()
		   .collapseKey("1")
		   .timeToLive(3)
		   .delayWhileIdle(true)
		   .addData("mensagem","Mensagem enviada pelo GCM.")
		   .build();
		
		Result result = null;
		
		//metodo send envia a mensagem e retorna a resposta da requisicao
		try {
			result = sender.send(message,Util.REG_ID, 1);
		} catch (IOException e) {
			System.err.println("Ocorreu o seguinte erro:\n"+e.getMessage());
			e.printStackTrace();
		}
		
		//Resposta da requisi��o
		if (result != null)
			System.out.println(result.toString());
	}

}
