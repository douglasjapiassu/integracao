package br.inf.ufg.integracao.server;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.json.JSONObject;

import br.inf.ufg.integracao.model.Notificacao;
import br.inf.ufg.integracao.model.Usuario;

public class EnviaNotificacaoGCM {
	
	/**
	 * Responsavel por enviar uma notificacao para os dispositivos cadastrados,
	 * atraves de uma requisicao HTTP.
	 * 
	 * @param notificacao
	 * @param listaUsuarios
	 * @return
	 */
	public String enviaNotificacaoGCM(Notificacao notificacao, List<Usuario> listaUsuarios) {
		String retorno = "";
		
		if (listaUsuarios.isEmpty()) {
			retorno = "Não há nenhum dispositivo cadastrado para receber as notificaççoes.";
			return retorno;
		}
		
		try {
			String[] ids = new String[listaUsuarios.size()];
			for (int i = 0; i < listaUsuarios.size(); i++) {
				ids[i] = listaUsuarios.get(i).getRegistrationId();
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
			objeto.put("delay_while_idle", true);
			objeto.put("time_to_live", 86400);
			
			DataOutputStream writer = new DataOutputStream(conexao.getOutputStream());
			writer.writeBytes(objeto.toString());
			writer.flush();
			writer.close();
			
			
			int responseCode = conexao.getResponseCode();
            System.out.println("Codigo Resposta: " + responseCode);
 
            BufferedReader in = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
 
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            
            JSONObject retornoJson = new JSONObject(response.toString());
            int sucesso = Integer.parseInt(retornoJson.get("success").toString());
            
			if(sucesso > 0) {
            	retorno = "Mensagem enviada com sucesso para " + sucesso + " dispositivos.";
            } else {
            	retorno = "Ocorreu um erro e a mensagem não foi enviada para nenhum dispositivo.";
            }
		} catch (MalformedURLException e) {
			System.err.println("Ocorreu o seguinte erro:\n" + e.getMessage());
		} catch (IOException e) {
			System.err.println("Ocorreu o seguinte erro:\n" + e.getMessage());
		}
		
		return retorno;
	}
	
}
