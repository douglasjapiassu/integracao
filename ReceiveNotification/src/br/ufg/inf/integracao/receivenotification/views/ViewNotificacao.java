package br.ufg.inf.integracao.receivenotification.views;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Activity responsável por mostrar a mensagem 
 * recebida do GCM na tela.
 */
public class ViewNotificacao extends Activity {

	private String message;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Definimos uma TextView para mostrar a mensagem na tela
		
		// Define como texto da TextView a mensagem recebida do GCM
		message = getIntent().getStringExtra("mensagem_recebida");
		// Ajusta tamanho e cor da fonte
		
	}
	
	protected void onResume(){
		super.onResume();
		TextView texto = new TextView(getApplicationContext());
		texto.setTextSize(20.0F);
		texto.setTextColor(Color.BLACK);
		texto.setText(message);
		/*
		 * Para tornar as coisas mais simples, mostraremos apenas uma TextView
		 * na tela com o conteúdo da mensagem recebida da Nuvem através do GCM.
		 */
		setContentView(texto);
	}
	
	
	

}
