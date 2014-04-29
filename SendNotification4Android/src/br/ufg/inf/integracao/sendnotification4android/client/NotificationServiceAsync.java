package br.ufg.inf.integracao.sendnotification4android.client;


import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

import br.ufg.inf.integracao.sendnotification4android.client.model.Usuario;


/**
 * Interface que define as operações assíncronas na camada de serviço web.
 * 
 * <p>Todas as operações da camada cliente com servidor são executadas de forma assíncrona.</p>
 * 
 * @see br.com.yaw.ggc.client.NotificationService
 * @author YaW Tecnologia
 */
public interface NotificationServiceAsync {

	void add(Usuario m, AsyncCallback<Long> callback);
	
	void remove(Usuario m, AsyncCallback<Boolean> callback);
	
	void getAll(AsyncCallback<Usuario[]> callback);
}
