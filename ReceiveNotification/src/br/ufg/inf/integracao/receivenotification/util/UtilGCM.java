package br.ufg.inf.integracao.receivenotification.util;

import android.content.Context;
import android.util.Log;

import com.google.android.gcm.GCMRegistrar;

public class UtilGCM {
	
	public static final String TAG = "gcm";
	public static final String SENDER_ID = "820609605026";

	/**
	 * Registra o dispositivo.
	 * @param context
	 * @return registrationId do dispositivo
	 */
	public static void registrar(Context context, String senderID) {
		GCMRegistrar.checkDevice(context);
		GCMRegistrar.checkManifest(context);
		final String registrationId = GCMRegistrar.getRegistrationId(context);
		if (registrationId.equals("")) {
			GCMRegistrar.register(context, senderID);
			Log.i(TAG, "Serviço Registrado.");
		} else {
			Log.i(TAG, "Serviço ativo, id: " + registrationId);
		}
	}
	
	/**
	 * Método responsável por desativar o uso do GCM.
	 * @param context
	 */
	public static void desativa(Context context) {
			GCMRegistrar.unregister(context);
			Log.i(TAG, "Serviço GCM desativado.");
	}
	
	/**
	 * Método responsável por verificar se o aplicativo 
	 * está ou não registrado para uso do GCM.
	 * @param context
	 * @return
	 */
	public static boolean isRegistrado(Context context) {
		return GCMRegistrar.isRegistered(context);
	}
}
