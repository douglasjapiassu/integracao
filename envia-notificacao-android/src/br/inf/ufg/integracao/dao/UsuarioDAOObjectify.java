package br.inf.ufg.integracao.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.Serializable;
import java.util.List;

import com.googlecode.objectify.Key;

import br.inf.ufg.integracao.model.Usuario;

/**
 * Implementa o contrato de persistência da entidade <code>Usuario</code>.
 * 
 * <p>
 *   Nessa aplicação resolvemos a persistência utilizando o Objectify, um framework de persistência para o App Engine.<br/>
 *   A proposta do Objetify é denifir uma API mais alto-nível para manipular dados no <code>DataStore</code> do App Engine.
 * </p>
 * 
 * @see br.inf.ufg.integracao.dao.UsuarioDAO
 * @see com.googlecode.objectify.ObjectifyService.
 * 
 */
public class UsuarioDAOObjectify implements Serializable, UsuarioDAO {

	private static final long serialVersionUID = -2808007719579763893L;

	@Override
	public Long save(Usuario usuario) {
		ofy().save().entity(usuario).now();
		return usuario.getId();
	}
	
	@Override
	public List<Usuario> getAll() {
		return ofy().load().type(Usuario.class).list();
	}
	
	@Override
	public Boolean remove(Usuario mercadoria) {
		ofy().delete().entity(mercadoria).now();
		return true;
	}
	
	@Override
	public Usuario findById(Long id) {
		Key<Usuario> k = Key.create(Usuario.class, id);
		return ofy().load().key(k).get();
	}
	
}
