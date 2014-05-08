package br.ufg.inf.integracao.sendnotification4android.client;


import com.google.gwt.user.client.rpc.AsyncCallback;

import br.ufg.inf.integracao.sendnotification4android.client.model.Usuario;


/**
 * Interface que define as operações assíncronas na camada de serviço web.
 * 
 * <p>Todas as operações da camada cliente com servidor são executadas de forma assíncrona.</p>
 * 
 * @see br.ufg.inf.integracao.sendnotification4android.client.UsuarioService
 * @author douglas.japiassu
 */
public interface UsuarioServiceAsync {
	void adicionar(Usuario m, AsyncCallback<Long> callback);
	
	void remover(Usuario m, AsyncCallback<Boolean> callback);
	
	void getTodosUsuarios(AsyncCallback<Usuario[]> callback);
}
