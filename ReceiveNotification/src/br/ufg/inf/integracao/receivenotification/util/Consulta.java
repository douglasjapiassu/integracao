package br.ufg.inf.integracao.receivenotification.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
 
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
 
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;
 
public class Consulta extends AsyncTask<String, Void, Void> {
	
	private final String URL = "http://1.send-notification-4-android.appspot.com/sendID";
	private HttpClient httpClient; 
	private HttpResponse httpResponse;
 
    @Override
    protected Void doInBackground(String... params) {
    	if (params.length == 0)
    		return null;
    	else if (params[0] == "recuperarDados")
    		recuperarDados();
    	else if (params[0] == "registrar")
    		sendID(params);
        
        return null;
    }
    
    private void recuperarDados() {
    	String linha = "";
    	
    	try {
    		 
            httpClient = new DefaultHttpClient();
           
            HttpGet requisicao = new HttpGet();
            requisicao.setHeader("Content-Type",
                    "text/plain; charset=utf-8");
            requisicao.setURI(new URI(URL));
            
            httpResponse = httpClient.execute(requisicao);
            
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    httpResponse.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");

            while ((linha = br.readLine()) != null) {
                sb.append(linha);
            }

            br.close();
            
            linha = sb.toString();
            System.out.println(linha);

        } catch (Exception e) {
            System.out.println(e);
            System.out.println(e.getMessage());
        }
    }
    
    protected String addLocationToUrl(String url){
        if(!url.endsWith("?"))
            url += "?";

        List<NameValuePair> params = new LinkedList<NameValuePair>();

        params.add(new BasicNameValuePair("lat", "teste"));

        String paramString = URLEncodedUtils.format(params, "utf-8");

        url += paramString;
        return url;
    }
    
    private void sendID(String... params) {
	    httpClient = new DefaultHttpClient();
	    HttpPost httpPost = new HttpPost(URL);
	    String regId = params[1];
	    String name = params[2];
	    String email = params[3];

	    try {
	        List<NameValuePair> parametros = new ArrayList<NameValuePair>(3);
	        
	        parametros.add(new BasicNameValuePair("regId", regId));
	        parametros.add(new BasicNameValuePair("name", name));
	        parametros.add(new BasicNameValuePair("email", email));
	        
	        httpPost.setEntity(new UrlEncodedFormEntity(parametros));

	        // Execute HTTP Post Request
	        httpResponse = httpClient.execute(httpPost);
	        
	    } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    } catch (Exception e) {
			System.err.println(e.getMessage());
		}
	} 
    
    public void ativaDesativaGCM(View view) {
		boolean isGCMAtivo = false;
		if (UtilGCM.isRegistrado(view.getContext())) {
			UtilGCM.desativa(view.getContext());
			isGCMAtivo = false;
			Toast.makeText(view.getContext(), "GCM desativado!", Toast.LENGTH_LONG).show();
		} else {
			isGCMAtivo = registrarEnviarId(view);
		}
	}
	
	private boolean registrarEnviarId(View view) {
		UtilGCM.desativa(view.getContext());
		String registrationId = UtilGCM.registrar(view.getContext());
		
		try {
			URL url = new URL("http://1.send-notification-4-android.appspot.com/sendID");
			HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
			
			conexao.setRequestMethod("GET");
			conexao.setRequestProperty("Content-Type", "application/json");
			conexao.setDoOutput(true);
			
			int responseCode = conexao.getResponseCode();
            System.out.println("Codigo Resposta: " + responseCode);
 
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
		
		Toast.makeText(view.getContext(), "GCM ativado!", Toast.LENGTH_LONG).show();
		return true;
	}
	
	/*private void setComportamentoBtnRegistrar(boolean isGCMAtivo) {
		if (isGCMAtivo)
			btnRegistrar.setClickable(false);
	}*/
}