package br.ufg.inf.integracao.sendnotification4android.server;

import java.util.List;
import java.util.Map;

import br.ufg.inf.integracao.sendnotification4android.client.UsuarioService;
import br.ufg.inf.integracao.sendnotification4android.client.model.Usuario;
import br.ufg.inf.integracao.sendnotification4android.server.dao.UsuarioDAO;
import br.ufg.inf.integracao.sendnotification4android.server.dao.UsuarioDAOJDO;
import br.ufg.inf.integracao.sendnotification4android.server.model.UsuarioEntity;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Classe que implementa as operações definidas pelo serviço web (<i>camada servidor</i>).
 * 
 * @author douglas.japiassu
 */
public class UsuarioServiceImpl extends RemoteServiceServlet implements UsuarioService {
	
	private static final long serialVersionUID = 1L;
	private UsuarioDAO dao = new UsuarioDAOJDO();
	
	@Override
	public Long adicionar(Usuario m) {
		try {
			UsuarioEntity me = dao.save(new UsuarioEntity(m));
			return me.getId();
		} catch (RuntimeException e){
			throw e;
		}
	}
	
	@Override
	public Boolean remover(Usuario m) {
		try {
			dao.remove(m.getId());
			return true;
		} catch (RuntimeException e){
			throw e;
		}
	}
	
	@Override
	public Usuario[] getTodosUsuarios() {
		try {
	    	List<UsuarioEntity> mercadorias = dao.getAll();
	    	return UsuarioEntity.toMercadoriaArray(mercadorias);
	    } catch (RuntimeException e){
	    	throw e;
		}
	}
}
