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

import br.ufg.inf.integracao.receivenotification.ReceiveNotification;
 
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;
 
@SuppressWarnings("unused")
public class HTTPRequestServer extends AsyncTask<String, Void, Void> {
	
	private final String URL = "http://1.send-notification-4-android.appspot.com/sendID";
	private HttpClient httpClient; 
	private HttpResponse httpResponse;
 
    @Override
    protected Void doInBackground(String... params) {
		sendRegistrationID(params);
        
        return null;
    }
    
    private void sendRegistrationID(String... params) {
	    httpClient = new DefaultHttpClient();
	    HttpPost httpPost = new HttpPost(URL);
	    String regId = params[0];
	    String name = params[1];
	    String email = params[2];

	    try {
	        List<NameValuePair> parametros = new ArrayList<NameValuePair>(3);
	        
	        parametros.add(new BasicNameValuePair("regId", regId));
	        parametros.add(new BasicNameValuePair("name", name));
	        parametros.add(new BasicNameValuePair("email", email));
	        
	        httpPost.setEntity(new UrlEncodedFormEntity(parametros));

	        // Execute HTTP Post Request
	        httpResponse = httpClient.execute(httpPost);
	        
	    } catch (ClientProtocolException e) {
	        System.err.println(e.getMessage());
	    } catch (IOException e) {
	    	System.err.println(e.getMessage());
	    } catch (Exception e) {
			System.err.println(e.getMessage());
		}
	} 
}