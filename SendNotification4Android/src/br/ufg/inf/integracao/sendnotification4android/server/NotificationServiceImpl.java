package br.ufg.inf.integracao.sendnotification4android.server;

import java.util.List;
import java.util.Map;

import br.ufg.inf.integracao.sendnotification4android.client.NotificationService;
import br.ufg.inf.integracao.sendnotification4android.client.model.Usuario;
import br.ufg.inf.integracao.sendnotification4android.server.dao.MercadoriaDAO;
import br.ufg.inf.integracao.sendnotification4android.server.dao.MercadoriaDAOJDO;
import br.ufg.inf.integracao.sendnotification4android.server.model.MercadoriaEntity;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Classe que implementa as operações definidas pelo serviço web (<i>camada servidor</i>).
 * 
 * @author YaW Tecnologia
 */
public class NotificationServiceImpl extends RemoteServiceServlet implements NotificationService {
	
	//private static Logger log = Logger.getLogNotificationServiceImplImpl.class);
	
	private static final long serialVersionUID = 1L;
	private MercadoriaDAO dao = new MercadoriaDAOJDO();
	
	@Override
	public Long add(Usuario m) {
		try {
			MercadoriaEntity me = dao.save(new MercadoriaEntity(m));
			return me.getId();
		} catch (RuntimeException e){
			throw e;
		}
	}
	
	@Override
	public Boolean remove(Usuario m) {
		try {
			dao.remove(m.getId());
			return true;
		} catch (RuntimeException e){
			throw e;
		}
	}
	
	@Override
	public Usuario[] getAll() {
		try {
	    	List<MercadoriaEntity> mercadorias = dao.getAll();
	    	return MercadoriaEntity.toMercadoriaArray(mercadorias);
	    } catch (RuntimeException e){
	    	throw e;
		}
	}
}
