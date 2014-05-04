package br.ufg.inf.integracao.sendnotification4android.server.dao;

import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import br.ufg.inf.integracao.sendnotification4android.server.model.UsuarioEntity;

/**
 * Implementa o contrato de persistência da entidade <code>Mercadoria</code>.
 *  
 * <p>Utiliza o mecanismo de persistência <code>JDO</code> para realizar as operações de cadastro de mercadorias.</p>
 * 
 * @see br.UsuarioDAO.yaw.ggc.dao.MercadoriaDAO
 * 
 * @author YaW Tecnologia
 */
public class UsuarioDAOJDO implements UsuarioDAO {

	/**
	 * Factory de conexoes do JDO, veja o mapeamento no arquivo <code>jdoconfig.xml</code>.
	 */
	private static final PersistenceManagerFactory factory = JDOHelper.getPersistenceManagerFactory("ggc-pm");
	
	@Override
	public UsuarioEntity save(UsuarioEntity me) {
		PersistenceManager pm = getPersistenceManager();
		try {
			return pm.makePersistent(me);
		} catch (Exception e){
			throw new RuntimeException("Nao foi possivel persistir mercadoria: "+e.getMessage());
		} finally {
			pm.close();
		}
	}

	@Override
	public void remove(Long id) {
		PersistenceManager pm = getPersistenceManager();
		try {
			UsuarioEntity me = pm.getObjectById(UsuarioEntity.class, id);
			pm.deletePersistent(me);	
		} catch (Exception e){
			throw new RuntimeException("Nao foi possivel remover mercadoria: "+e.getMessage());
		} finally {
			pm.close();
		}
	}

	@Override
	public List<UsuarioEntity> getAll() {
		PersistenceManager pm = getPersistenceManager();
	    try {
	    	Query q = pm.newQuery(UsuarioEntity.class);
	    	List<UsuarioEntity> lista = (List<UsuarioEntity>) q.execute();
	    	lista.size();
	    	return lista;
	    } catch (Exception e){
	    	throw new RuntimeException("Nao foi possivel consultar mercadoria(s): "+e.getMessage());
		} finally {
	    	pm.close();
	    }
	}

	@Override
	public UsuarioEntity findById(Long id) {
		PersistenceManager pm = getPersistenceManager();
		try {
			return pm.getObjectById(UsuarioEntity.class, id);
		} catch (JDOObjectNotFoundException nex){
			return null;
		} catch (Exception e){
			throw new RuntimeException("Nao foi possivel consultar mercadoria c/ id: "+e.getMessage());
		} finally {
	    	pm.close();
	    }
	}
	
	/**
	 * @return <code>PersistenceManager</code> componente <code>JDO</code> responsável por executar as operações de persistência.
	 */
	private PersistenceManager getPersistenceManager() {
	    return factory.getPersistenceManager();
	}

}
