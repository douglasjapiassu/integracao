package br.ufg.inf.integracao.receivenotification.service;

import java.util.Date;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import br.ufg.inf.integracao.receivenotification.R;
import br.ufg.inf.integracao.receivenotification.persistencia.DBAdapter;
import br.ufg.inf.integracao.receivenotification.util.UtilGCM;
import br.ufg.inf.integracao.receivenotification.views.ViewNotificacao;

import com.google.android.gcm.GCMBaseIntentService;

/**
 * Service responsável por tratar os eventos do GCM.
 */
@SuppressWarnings("deprecation")
public class GCMIntentService extends GCMBaseIntentService {
	
	/**
	 * Método executado quando o aplicativo se registra no GCM para 
	 * o recebimento de mensagens da Nuvem.
	 */
	@Override
	protected void onRegistered(Context context, String regId) {
		Log.i(UtilGCM.TAG, "GCM ativado.");
		
		/*
		 * Mostramos no console o ID de registro no GCM para usá-lo 
		 * posteriormente, no aplicativo cliente, para o envio de mensagens
		 * da Nuvem para o dispositivo Android.
		 */
		 
		String mensagem = "ID de registro no GCM: " + regId;
		Log.i(UtilGCM.TAG, mensagem);
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
		/*
		 * Recuperamos a mensagem recebida através do Extras
		 * da Intent do GCM que invocou este Service.
		 */
		String mensagem = intent.getExtras().getString("mensagem");
		Log.i(UtilGCM.TAG, "Mensagem recebida: " + mensagem);
		
		/*
		 * Disparamos uma Notificação para avisar o usuário sobre a 
		 * nova mensagem recebida da Nuvem.
		 */
		if (mensagem != null && !"".equals(mensagem)) {
			mostraNotificacao("GCM: Nova mensagem recebida", mensagem, context);
			salvarNotificacao(mensagem);
		}
			
		
	}

	/**
	 * Método executado quando o aplicativo se desregistra do GCM.
	 */
	@Override
	protected void onUnregistered(Context context, String regId) {
		Log.i(UtilGCM.TAG, "GCM Desativado.");
	}
	
	private void mostraNotificacao(String titulo, String mensagem,
			Context context) {

		// Tempo em que a Notificação será disparada
		long tempoDefinido = System.currentTimeMillis();

		// Objeto Notification
		Notification notification = new Notification(R.drawable.ic_launcher,
				titulo, tempoDefinido);

		// Intent que será disparada quando o usuário clicar sobre a Notificação
		Intent intent = new Intent(context, ViewNotificacao.class);
		intent.putExtra("mensagem_recebida", mensagem);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		// Configurando os dados da Notificação
		notification.setLatestEventInfo(context, titulo, mensagem, pendingIntent);

		// Oculta a notificação após o usuário clicar sobre ela
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		
		// Define alertas de acordo com o padrão definido no dispositivo
		notification.defaults = Notification.DEFAULT_ALL;

		// Agenda a Notificação
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(0, notification);
	}
	
	public void salvarNotificacao(String notificacao) {
    	DBAdapter dbAdapter = new DBAdapter(this);
    	
    	dbAdapter.salvarNotificacao(notificacao, new Date());
    }
	
}
