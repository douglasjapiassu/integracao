package br.ufg.inf.integracao.sendnotification4android.server.dao;

import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import br.ufg.inf.integracao.sendnotification4android.server.model.MercadoriaEntity;

/**
 * Implementa o contrato de persistência da entidade <code>Mercadoria</code>.
 *  
 * <p>Utiliza o mecanismo de persistência <code>JDO</code> para realizar as operações de cadastro de mercadorias.</p>
 * 
 * @see br.com.yaw.ggc.dao.MercadoriaDAO
 * 
 * @author YaW Tecnologia
 */
public class MercadoriaDAOJDO implements MercadoriaDAO {

	/**
	 * Factory de conexoes do JDO, veja o mapeamento no arquivo <code>jdoconfig.xml</code>.
	 */
	private static final PersistenceManagerFactory factory = JDOHelper.getPersistenceManagerFactory("ggc-pm");
	
	@Override
	public MercadoriaEntity save(MercadoriaEntity me) {
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
			MercadoriaEntity me = pm.getObjectById(MercadoriaEntity.class, id);
			pm.deletePersistent(me);	
		} catch (Exception e){
			throw new RuntimeException("Nao foi possivel remover mercadoria: "+e.getMessage());
		} finally {
			pm.close();
		}
	}

	@Override
	public List<MercadoriaEntity> getAll() {
		PersistenceManager pm = getPersistenceManager();
	    try {
	    	Query q = pm.newQuery(MercadoriaEntity.class);
	    	List<MercadoriaEntity> lista = (List<MercadoriaEntity>) q.execute();
	    	lista.size();
	    	return lista;
	    } catch (Exception e){
	    	throw new RuntimeException("Nao foi possivel consultar mercadoria(s): "+e.getMessage());
		} finally {
	    	pm.close();
	    }
	}

	@Override
	public MercadoriaEntity findById(Long id) {
		PersistenceManager pm = getPersistenceManager();
		try {
			return pm.getObjectById(MercadoriaEntity.class, id);
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
