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

import br.inf.ufg.integracao.dao.DadosSistemaDAO;
import br.inf.ufg.integracao.dao.DadosSistemaDAOObjectify;
import br.inf.ufg.integracao.model.DadosSistema;
import br.inf.ufg.integracao.model.Notificacao;
import br.inf.ufg.integracao.model.Usuario;

public class EnviaNotificacaoGCM {
	
	private DadosSistemaDAO dadosSistemaDAO = new DadosSistemaDAOObjectify();
	
	/**
	 * Responsavel por enviar uma notificacao para os dispositivos cadastrados,
	 * atraves de uma requisicao HTTP.
	 * 
	 * @param notificacao
	 * @param listaUsuarios
	 * @return resposta da requisição
	 */
	public String enviaNotificacaoGCM(Notificacao notificacao, List<Usuario> listaUsuarios) {
		String retorno = "";
		String apiKey = "";
		
		if (listaUsuarios.isEmpty()) {
			retorno = "Não há nenhum dispositivo cadastrado para receber as notificações.";
			return retorno;
		}
		
		dadosSistemaDAO = new DadosSistemaDAOObjectify();
    	List<DadosSistema> dadosSistema = dadosSistemaDAO.getAll();
    	if (dadosSistema.isEmpty()) {
    		retorno = "É necessário cadastrar os dados do sistema no menu Configuração.";
    		return retorno;
    	} else {
    		apiKey = dadosSistema.get(0).getApiKey();
    		if ("".equalsIgnoreCase(apiKey.trim())) {
    			retorno = "Api Key não preenchida. Impossível enviar a notificação.";
    			return retorno;
    		}
    			
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
			conexao.setRequestProperty("Authorization", "key=" + apiKey);
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
            
            if(responseCode == 401) {
            	retorno = "Não autorizado. Verifique se a API Key está correta.";
            } else {
            	JSONObject retornoJson = new JSONObject(response.toString());
                int sucesso = Integer.parseInt(retornoJson.get("success").toString());
                if(sucesso > 0) {
                	retorno = "Mensagem enviada com sucesso para " + sucesso + " dispositivo(s).";
                } else {
                	retorno = "Ocorreu um erro e a mensagem não foi enviada para nenhum dispositivo.";
                }
            }
            
			
		} catch (MalformedURLException e) {
			System.err.println("Ocorreu o seguinte erro:\n" + e.getMessage());
		} catch (IOException e) {
			System.err.println("Ocorreu o seguinte erro:\n" + e.getMessage());
		}
		
		return retorno;
	}
	
}
