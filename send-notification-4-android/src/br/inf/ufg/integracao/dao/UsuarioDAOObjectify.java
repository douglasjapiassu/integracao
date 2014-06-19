package br.inf.ufg.integracao.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.Serializable;
import java.util.List;

import com.googlecode.objectify.Key;

import br.inf.ufg.integracao.model.Usuario;

/**
 * Implementa o contrato de persistência da entidade <code>Usuario</code>.
 * 
 * @see br.inf.ufg.integracao.dao.UsuarioDAO
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
	public Boolean remove(Usuario usuario) {
		ofy().delete().entity(usuario).now();
		return true;
	}
	
	@Override
	public Usuario findById(Long id) {
		Key<Usuario> k = Key.create(Usuario.class, id);
		return ofy().load().key(k).get();
	}
	
}
