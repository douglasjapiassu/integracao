package br.inf.ufg.integracao.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.Serializable;
import java.util.List;

import com.googlecode.objectify.Key;

import br.inf.ufg.integracao.model.DadosSistema;

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
public class DadosSistemaDAOObjectify implements Serializable, DadosSistemaDAO {

	private static final long serialVersionUID = -2808007719579763893L;

	@Override
	public Long save(DadosSistema dadosSistema) {
		ofy().save().entity(dadosSistema).now();
		return dadosSistema.getId();
	}
	
	@Override
	public List<DadosSistema> getAll() {
		return ofy().load().type(DadosSistema.class).list();
	}
	
	@Override
	public Boolean remove(DadosSistema dadosSistema) {
		ofy().delete().entity(dadosSistema).now();
		return true;
	}
	
	@Override
	public DadosSistema findById(Long id) {
		Key<DadosSistema> k = Key.create(DadosSistema.class, id);
		return ofy().load().key(k).get();
	}
	
}
