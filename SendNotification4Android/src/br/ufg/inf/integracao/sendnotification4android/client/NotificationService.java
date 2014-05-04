package br.ufg.inf.integracao.sendnotification4android.client;


import br.ufg.inf.integracao.sendnotification4android.client.model.Usuario;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Interface que expõe os serviços na camada web (servidor).
 * 
 * <p>Os métodos definidos nessa interface lançam <code>RuntimeException</code> para manter o <i>tipo</i> da exceção original na camada cliente.</p>
 * 
 * @author YaW Tecnologia
 */
@RemoteServiceRelativePath("nserv")
public interface NotificationService extends RemoteService {

	/**
	 * Faz a inserção ou atualização da mercadoria no mecanismo de persistencia.
	 * @param m
	 * @return o <code>ID</code> o da mercadoria persistida.
	 * @throws RuntimeException caso algum erro durante a operação aconteça.
	 */
	Long add(Usuario m) throws RuntimeException;
	
	/**
	 * Exclui o registro da mercadoria no mecanismo de persistencia.
	 * @param m
	 * @return <code>true</code>
	 * @throws RuntimeException caso algum erro durante a operação aconteça.
	 */
	Boolean remove(Usuario m) throws RuntimeException;

	/**
	 * @return Lista com todas as mercadorias cadastradas no mecanismo de persistencia.
	 * @throws RuntimeException
	 */
	Usuario[] getAll() throws RuntimeException;
}
 