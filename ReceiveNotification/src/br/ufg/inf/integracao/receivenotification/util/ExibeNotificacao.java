package br.ufg.inf.integracao.receivenotification.util;

import java.util.Date;
import java.util.Random;

import br.ufg.inf.integracao.receivenotification.R;
import br.ufg.inf.integracao.receivenotification.persistencia.DBAdapter;
import br.ufg.inf.integracao.receivenotification.views.ViewNotificacao;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * Classe utilitária para exibir notificações.
 * 
 */
@SuppressWarnings("deprecation")
public class ExibeNotificacao {

	public static void mostraNotificacao(String titulo, String mensagem,
			Context context) {

		// Tempo em que a Notificação será disparada
		long tempoDefinido = System.currentTimeMillis();
		Random rdn = new Random();
		int id = rdn.nextInt();
		

		// Objeto Notification
		Notification notification = new Notification(R.drawable.ic_launcher,
				titulo, tempoDefinido);

		// Intent que será disparada quando o usuário clicar sobre a Notificação
		Intent intent = new Intent(context, ViewNotificacao.class);
		intent.putExtra("mensagem_recebida", mensagem);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		notification.setLatestEventInfo(context, titulo, mensagem, pendingIntent);
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.defaults = Notification.DEFAULT_ALL;
		notification.vibrate = new long[] { 100, 250, 100, 500 };
		DBAdapter dbAdapter = new DBAdapter(context);
		dbAdapter.salvarNotificacao(mensagem, new Date());

		// Agenda a Notificação
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		
		notificationManager.notify(0, notification);
	}

}
