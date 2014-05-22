package br.ufg.inf.integracao.receivenotification.views;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import br.ufg.inf.integracao.receivenotification.R;
import br.ufg.inf.integracao.receivenotification.util.Consulta;
import br.ufg.inf.integracao.receivenotification.util.UtilGCM;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ViewRegistrar extends Activity {
	
	private Button btnRegistrar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_registrar);
		btnRegistrar = (Button) findViewById(R.id.btnRegistrar2);
		boolean isGCMAtivo = UtilGCM.isRegistrado(getApplicationContext());
		
		btnRegistrar.setOnClickListener(new OnClickListener(){
        	public void onClick(View v) {
        		//ativaDesativaGCM(v);
        		(new Consulta()).execute(null);
        	}});
		
		//setComportamentoBtnRegistrar(isGCMAtivo);
	}
	
	public void ativaDesativaGCM(View view) {
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
		String registrationId = UtilGCM.registrar(getApplicationContext());
		
		try {
			URL url = new URL("http://1.send-notification-4-android.appspot.com/sendID");
			HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
			
			conexao.setRequestMethod("GET");
			conexao.setRequestProperty("Content-Type", "application/json");
			conexao.setDoOutput(true);
			
			/*JSONObject objeto = new JSONObject();
			JSONObject dadosDispositivo = new JSONObject();
			dadosDispositivo.put("usuario", registrationId);
			dadosDispositivo.put("email", registrationId);
			dadosDispositivo.put("id", registrationId);
			objeto.put("data", dadosDispositivo);
			
			DataOutputStream writer = new DataOutputStream(conexao.getOutputStream());
			writer.writeBytes(objeto.toString());
			writer.flush();
			writer.close();*/
			
			
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
			btnRegistrar.setClickable(false);
	}

}
