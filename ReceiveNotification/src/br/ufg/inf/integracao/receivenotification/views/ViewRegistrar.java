package br.ufg.inf.integracao.receivenotification.views;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import br.ufg.inf.integracao.receivenotification.R;
import br.ufg.inf.integracao.receivenotification.util.HTTPRequestServer;
import br.ufg.inf.integracao.receivenotification.util.UtilGCM;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ViewRegistrar extends Activity {
	
	private Button btnCadastrar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_registrar);
		btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
		
		btnCadastrar.setOnClickListener(new OnClickListener(){
        	public void onClick(View v) {
        		recuperarSenderID();
        		ativaDesativaGCM();
        	}});
	}
	
	public void recuperarSenderID() {
		new HTTPRequestServer().execute((String) "recuperarSenderID");
	}
	
	public void ativaDesativaGCM() {
		boolean isGCMAtivo;
		if (UtilGCM.isRegistrado(getApplicationContext())) {
			UtilGCM.desativa(getApplicationContext());
			isGCMAtivo = false;
			Toast.makeText(getApplicationContext(), "GCM desativado!", Toast.LENGTH_LONG).show();
		} else {
			isGCMAtivo = registrarEnviarId();
		}
		
		setComportamentoBtnRegistrar(isGCMAtivo);
	}
	
	private boolean registrarEnviarId() {
		UtilGCM.desativa(getApplicationContext());
		//String registrationId = UtilGCM.registrar(getApplicationContext());
		
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
		/*catch (JSONException e) {
			System.err.println("Ocorreu o seguinte erro:\n" + e.getMessage());
		}*/
		
		Toast.makeText(getApplicationContext(), "GCM ativado!", Toast.LENGTH_LONG).show();
		return true;
	}
	
	private void setComportamentoBtnRegistrar(boolean isGCMAtivo) {
		if (isGCMAtivo)
			btnCadastrar.setClickable(false);
	}

}
