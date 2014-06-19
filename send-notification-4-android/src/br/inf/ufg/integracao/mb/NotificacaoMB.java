package br.inf.ufg.integracao.mb;

import static javax.faces.context.FacesContext.getCurrentInstance;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.apache.log4j.Logger;

import br.inf.ufg.integracao.dao.NotificacaoDAO;
import br.inf.ufg.integracao.dao.NotificacaoDAOObjectify;
import br.inf.ufg.integracao.dao.UsuarioDAOObjectify;
import br.inf.ufg.integracao.model.Notificacao;
import br.inf.ufg.integracao.model.Usuario;
import br.inf.ufg.integracao.server.EnviaNotificacaoGCM;
import br.inf.ufg.integracao.server.Util;

/**
 * Componente atua como um intermediário das telas do cadastro e os componentes de negócio (<code>DAO</code>) da entidade <code>Usuario</code>.
 * 
 * <p>Trata-se de um <code>Managed Bean</code>, ou seja, as instância dessa classe são controladas pelo <code>JSF</code>. Para cada sessão de usuário será criado um objeto <code>UsuarioMB</code>.</p>
 * 
 * <p>Esse componente atua com um papel parecido com o <code>Controller</code> de outros frameworks <code>MVC</code>, ele resolve o fluxo de navegação e liga os componentes visuais com os dados.</p>
 */
@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
public class NotificacaoMB implements Serializable {
	
	private static Logger log = Logger.getLogger(NotificacaoMB.class);
	
	/**
	 * Referência do componente de persistência.
	 */
	private NotificacaoDAO dao;
	
	/**
	 * Referência para o usuario utilizado na inclusão (nova) ou edição.
	 */
	private Notificacao notificacao;
	
	/**
	 * Informação é utilizada na edição da mercadoria, quando a seleção de um registro na listagem ocorrer.
	 */
	private Long idSelecionado;
	
	private String abaAtiva;
	
	/**
	 * Mantém os usuários apresentados na listagem indexadas pelo id.
	 * <strong>Importante:</strong> a consulta (query) no DataStore do App Engine pode retornar <i>dados antigos</i>, 
	 * que já foram removidos ou que ainda não foram incluidos, devido a replicação dos dados.
	 * 
	 * Dessa forma esse hashmap mantém um espelho do datastore para minizar o impacto desse modelo do App Engine.
	 */
	private Map<Long, Notificacao> notificacoes;
	
	public NotificacaoMB() {
		dao = new NotificacaoDAOObjectify();
		preencherNotificacoes();
	}
	
	public Notificacao getNotificacao() {
		return notificacao;
	}
	
	public void setNotificacao(Notificacao notificacao) {
		this.notificacao = notificacao;
	}
	
	public Long getIdSelecionado() {
		return idSelecionado;
	}

	public void setIdSelecionado(Long idSelecionado) {
		this.idSelecionado = idSelecionado;
	}

	public String getAbaAtiva() {
		return abaAtiva;
	}

	public void setAbaAtiva(String abaAtiva) {
		this.abaAtiva = abaAtiva;
	}

	/**
	 * @return <code>DataModel</code> para carregar a lista de usuarios.
	 */
	public DataModel<Notificacao> getDmNotificacoes() {
		return new ListDataModel<Notificacao>(new ArrayList<Notificacao>(notificacoes.values()));
	}
	
	private void preencherNotificacoes() {
		try {
			List<Notificacao> listaNotificacoes = new ArrayList<Notificacao>(dao.getAll());
			notificacoes = new HashMap<Long, Notificacao>();
			for (Notificacao m: listaNotificacoes) {
				notificacoes.put(m.getId(), m);
			}
			
			log.debug("Carregou a lista de notificacoes ("+notificacoes.size()+")");
		} catch(Exception ex) {
			log.error("Erro ao carregar a lista de notificacoes.", ex);
			addMessage(getMessageFromI18N("msg.erro.listar.usuario"), ex.getMessage());
		}
		
	}
	
	/**
	 * Ação executada quando a página de envio de notificacoes for carregada.
	 */
	public void enviar(){
		notificacao = new Notificacao();
		Util.setAbaAtiva("enviarNotificacao");
		log.debug("Pronto pra enviar notificacoes");
	}
	
	/**
	 * Operação acionada pela tela de inclusão ou edição, através do <code>commandButton</code> <strong>Salvar</strong>.
	 * @return Se a inclusão/edição foi realizada vai para listagem, senão permanece na mesma tela.
	 */
	public String salvar() {
		try {
			notificacao.setDataEnvio(new Date());
			dao.save(notificacao);
			notificacoes.put(notificacao.getId(), notificacao);
		} catch(Exception ex) {
			log.error("Erro ao salvar usuario.", ex);
			addMessage(getMessageFromI18N("msg.erro.salvar.usuario"), ex.getMessage());
			return "";
		}
		log.debug("Salvou notificacao "+notificacao.getId());
		
		return "";
	}
	
	/**
	 * Opera��o acionada pela tela de listagem, atrav�s do <code>commandButton</code> <strong>Atualizar</strong>. 
	 */
	public void atualizar() {
		preencherNotificacoes();
	}
	
	/**
	 * Opera��o acionada pela tela de listagem, atrav�s do <code>commandButton</code> <strong>Enviar</strong>. 
	 */
	public void enviarNotificacao() {
		if (Util.isTamanhoDaMensagemInvalido(notificacao.getMensagem())) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aten��o", "Mensagem deve ter entre 5 e 2048 caracteres."));
		} else {
			EnviaNotificacaoGCM en = new EnviaNotificacaoGCM();
			List<Usuario> listaDeUsuarios = new UsuarioDAOObjectify().getAll();
			String retorno = en.enviaNotificacaoGCM(notificacao, listaDeUsuarios);
			addMessage("Aten��o", retorno);
			salvar();
		}
	}
	
	/**
	 * Operação acionada toda a vez que a  tela de listagem for carregada.
	 */
	public void reset() {
		idSelecionado = null;
		notificacao = null;
		Util.setAbaAtiva("listaNotificacoes");
	}
	
	public void visualizar() {
		if (idSelecionado == null) {
			return;
		}
		notificacao = notificacoes.get(idSelecionado);
		log.debug("Pronto pra visualizar");
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
