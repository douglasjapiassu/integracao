package br.ufg.inf.integracao.sendnotification4android.server;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import br.ufg.inf.integracao.sendnotification4android.client.model.Product;
import br.ufg.inf.integracao.sendnotification4android.client.model.Usuario;
import br.ufg.inf.integracao.sendnotification4android.util.Util;

public class HTTPRequestGCM {
	
	public void enviar(String mensagem) {
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST,
				URL.encode(Util.GCM_URL));
		
		try {
			builder.setHeader("Content-Type", "application/json");
			//builder.setHeader("Content-Type", "text/plain");
			builder.setHeader("Authorization", "key=" + Util.API_KEY);
			builder.setHeader("Access-Control-Request-Method", "POST");
			builder.setHeader("Access-Control-Request-Headers", "Authorization");
			
			String registrationId1 = "APA91bEhqcMRi_8-GkKhGtfQT7MjGUUmcuyX68g1mqjlWJZ2wcYeAd1C2WBBfp8ReLVFWhs1j0GsQZ1rKbb_0IGfMYfCh9-YHy6VGRFG3FSupLPbCOocyYj78oMe8hzyf60wtfgg6KnZ9E3W70SyF7LEFPamQmuO_w";
			String[] ids = new String[] { registrationId1};
			

			
			JSONObject objeto = new JSONObject();
			JSONObject data = new JSONObject();
			data.put("mensagem", new JSONString(mensagem));
			objeto.put("data", data);
			
			JSONArray array = new JSONArray();
			
			for (int i = 0; i < ids.length; i++) {
				array.set(i, new JSONString(ids[i]));
			}
			
			objeto.put("registration_ids", array);
			Request response = builder.sendRequest(objeto.toString(),
					new RequestCallback() {

						@Override
						public void onResponseReceived(Request request,
								Response response) {
							System.out.println(response.getStatusCode());
							System.out.println(response.getText());
							System.out.println(response.getStatusText());
						}

						@Override
						public void onError(Request request, Throwable exception) {
							System.out.println(request.toString());

						}
					});
			System.out.println(builder);
			System.out.println(response.toString());
		} catch (RequestException e) {
			Window.alert("Failed to send the request: " + e.getMessage());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

/*	public void enviaNotificacaoGCM(String mensagem) {
		try {
//			URL url = new URL(Util.GCM_URL);
			
			HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
			
			conexao.setRequestMethod("POST");
			conexao.setRequestProperty("Content-Type", "application/json");
			conexao.setRequestProperty("Authorization", "key=" + Util.API_KEY);
			conexao.setDoOutput(true);
			
			JSONObject objeto = new JSONObject();
			JSONObject data = new JSONObject();
			data.put("mensagem", mensagem);
			objeto.put("data", data);
			String regId1 = "";
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
		} catch (JSONException e) {
			System.err.println("Ocorreu o seguinte erro:\n" + e.getMessage());
		}
	}
	
	public static void enviaNotificacaoGCMSender(String mensagem){
		Sender sender = new Sender(Util.API_KEY);
		
		Message message = new Message.Builder()
		   .collapseKey("1")
		   .timeToLive(3)
		   .delayWhileIdle(true)
		   .addData("mensagem",mensagem)
		   .build();
		
		Result result = null;
		
		//metodo send envia a mensagem e retorna a resposta da requisicao
		try {
			result = sender.send(message,"", 1);
		} catch (IOException e) {
			System.err.println("Ocorreu o seguinte erro:\n"+e.getMessage());
			e.printStackTrace();
		}
		
		//Resposta da requisi��o
		if (result != null)
			System.out.println(result.toString());
	}*/

}
