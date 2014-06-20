package br.ufg.inf.integracao.receivenotification;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import br.ufg.inf.integracao.receivenotification.model.Usuario;
import br.ufg.inf.integracao.receivenotification.persistencia.DBAdapter;
import br.ufg.inf.integracao.receivenotification.util.ExibeNotificacao;
import br.ufg.inf.integracao.receivenotification.util.HTTPRequestServer;
import br.ufg.inf.integracao.receivenotification.util.UtilGCM;

import com.google.android.gcm.GCMBaseIntentService;

/**
 * Service responsável por tratar os eventos do GCM.
 * 
 * @author Douglas Japiassu
 *
 */
public class GCMIntentService extends GCMBaseIntentService {
	
	/**
	 * Método executado quando o aplicativo se registra no GCM para 
	 * o recebimento de mensagens da Nuvem.
	 */
	@Override
	protected void onRegistered(Context context, String regId) {
		Log.i(UtilGCM.TAG, "GCM ativado.");
		String mensagem = "ID de registro no GCM: " + regId;
		Log.i(UtilGCM.TAG, mensagem);
		HTTPRequestServer requestServer = new HTTPRequestServer();
		DBAdapter dbAdapter = new DBAdapter(context);
		Usuario usuario = dbAdapter.getUsuario();
		requestServer.execute(regId, usuario.getNome(), usuario.getEmail());
	}

	/**
	 * Método executado quando algum erro ocorre na comunicação
	 * com o GCM. 
	 */
	@Override
	protected void onError(Context context, String errorMessage) {
		Log.e(UtilGCM.TAG, "Erro: " + errorMessage);
	}

	/**
	 * Método executado quando uma nova mensagem é recebida 
	 * da Nuvem através do GCM.
	 */
	@Override
	protected void onMessage(Context context, Intent intent) {
		String mensagem = intent.getExtras().getString("mensagem");
		Log.i(UtilGCM.TAG, "Mensagem recebida: " + mensagem);
		
		if (mensagem != null && !"".equals(mensagem))
			ExibeNotificacao.mostraNotificacao("GCM: Nova mensagem recebida", mensagem, context);
		
	}

	/**
	 * Método executado quando o aplicativo se desregistra do GCM.
	 */
	@Override
	protected void onUnregistered(Context context, String regId) {
		Log.i(UtilGCM.TAG, "GCM Desativado.");
	}
	
}