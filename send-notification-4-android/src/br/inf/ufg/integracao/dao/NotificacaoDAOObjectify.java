package br.inf.ufg.integracao.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.Serializable;
import java.util.List;

import com.googlecode.objectify.Key;

import br.inf.ufg.integracao.model.Notificacao;

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
public class NotificacaoDAOObjectify implements Serializable, NotificacaoDAO {

	private static final long serialVersionUID = -2808007719579763893L;

	@Override
	public Long save(Notificacao notificacao) {
		ofy().save().entity(notificacao).now();
		return notificacao.getId();
	}
	
	@Override
	public List<Notificacao> getAll() {
		return ofy().load().type(Notificacao.class).list();
	}
	
	@Override
	public Boolean remove(Notificacao notificacao) {
		ofy().delete().entity(notificacao).now();
		return true;
	}
	
	@Override
	public Notificacao findById(Long id) {
		Key<Notificacao> k = Key.create(Notificacao.class, id);
		return ofy().load().key(k).get();
	}
	
}
