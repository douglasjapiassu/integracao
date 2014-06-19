package br.inf.ufg.integracao.mb;

import static javax.faces.context.FacesContext.getCurrentInstance;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;

import br.inf.ufg.integracao.dao.DadosSistemaDAO;
import br.inf.ufg.integracao.dao.DadosSistemaDAOObjectify;
import br.inf.ufg.integracao.model.DadosSistema;
import br.inf.ufg.integracao.server.Util;

/**
 * Componente atua como um intermediário das telas do cadastro e os componentes de negócio (<code>DAO</code>) da entidade <code>DadosSistema</code>.
 * 
 * <p>Trata-se de um <code>Managed Bean</code>, ou seja, as instância dessa classe são controladas pelo <code>JSF</code>. Para cada sessão de usuário será criado um objeto <code>UsuarioMB</code>.</p>
 * 
 * <p>Esse componente atua com um papel parecido com o <code>Controller</code> de outros frameworks <code>MVC</code>, ele resolve o fluxo de navegação e liga os componentes visuais com os dados.</p>
 */
@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
public class DadosSistemaMB implements Serializable {
	
	private static Logger log = Logger.getLogger(DadosSistemaMB.class);
	
	/**
	 * Referência do componente de persistência.
	 */
	private DadosSistemaDAO dao;
	
	/**
	 * Referência para o DadosSistema utilizado na inclusão (nova) ou edição.
	 */
	private DadosSistema dadosSistema;
	
	private String abaAtiva;
	
	public DadosSistemaMB() {
		dao = new DadosSistemaDAOObjectify();
	}
	
	public DadosSistema getDadosSistema() {
		return dadosSistema;
	}
	
	public void setDadosSistema(DadosSistema dadosSistema) {
		this.dadosSistema = dadosSistema;
	}
	
	public String getAbaAtiva() {
		return abaAtiva;
	}

	public void setAbaAtiva(String abaAtiva) {
		this.abaAtiva = abaAtiva;
	}

	/**
	 * Operação acionada pela tela de inclusão ou edição, através do <code>commandButton</code> <strong>Salvar</strong>.
	 * @return Se a inclusão/edição foi realizada vai para listagem, senão permanece na mesma tela.
	 */
	public void salvar() {
		try {
			dao.save(dadosSistema);
		} catch(Exception ex) {
			log.error("Erro ao salvar usuario.", ex);
			addMessage(getMessageFromI18N("msg.erro.salvar.usuario"), ex.getMessage());
			//return "";
		}
		log.debug("Salvou dadosSistema "+dadosSistema.getId());
		
		iniciarTela();
	}
	
	public void iniciarTela() {
		List<DadosSistema> dadosDoSistema = new ArrayList<DadosSistema>(dao.getAll());
		if (!dadosDoSistema.isEmpty())
			dadosSistema = dadosDoSistema.get(0);
		else
			dadosSistema = new DadosSistema();
		
		Util.setAbaAtiva("dadosSistema");
	}
	
	public String remover() {
		try {
			dao.remove(dadosSistema);
			dadosSistema = new DadosSistema();
		} catch(Exception ex) {
			log.error("Erro ao remover dadosSistema.", ex);
			return "";
		}
		return "editarConfiguracao";
	}
	
	/**
	 * @param key
	 * @return Recupera a mensagem do arquivo properties <code>ResourceBundle</code>.
	 */
	private String getMessageFromI18N(String key) {
		ResourceBundle bundle = ResourceBundle.getBundle("messages_labels", getCurrentInstance().getViewRoot().getLocale());
		return bundle.getString(key);
	}
	
	/**
	 * Adiciona um mensagem no contexto do Faces (<code>FacesContext</code>).
	 * @param summary
	 * @param detail
	 */
	private void addMessage(String summary, String detail) {
		getCurrentInstance().addMessage(null, new FacesMessage(summary, ("<br/>").concat(detail)));
	}
}
