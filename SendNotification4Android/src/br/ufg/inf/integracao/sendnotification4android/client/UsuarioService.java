package br.ufg.inf.integracao.sendnotification4android.client;


import br.ufg.inf.integracao.sendnotification4android.client.model.Usuario;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Interface que expõe os serviços na camada web (servidor).
 * 
 * <p>Os métodos definidos nessa interface lançam <code>RuntimeException</code> para manter o <i>tipo</i> da exceção original na camada cliente.</p>
 * 
 * @author douglas.japiassu
 */
@RemoteServiceRelativePath("nserv")
public interface UsuarioService extends RemoteService {
	
	/**
	 * Faz a inserção ou atualização do usuário.
	 * @param m
	 * @return o <code>ID</code> do usuário.
	 * @throws RuntimeException
	 */
	Long adicionar(Usuario m) throws RuntimeException;

	/**
	 * Exclui o registro do usuário.
	 * @param usuario
	 * @return <code>true</code>
	 * @throws RuntimeException
	 */
	Boolean remover(Usuario usuario) throws RuntimeException;

	/**
	 * @return Lista com todos os usuários cadastrados.
	 * @throws RuntimeException
	 */
	Usuario[] getTodosUsuarios() throws RuntimeException;
}
 