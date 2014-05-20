package br.inf.ufg.integracao.mb;

import static javax.faces.context.FacesContext.getCurrentInstance;

import java.io.Serializable;
import java.util.ArrayList;
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

import br.inf.ufg.integracao.dao.UsuarioDAO;
import br.inf.ufg.integracao.dao.UsuarioDAOObjectify;
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
public class UsuarioMB implements Serializable {
	
	private static Logger log = Logger.getLogger(UsuarioMB.class);
	
	private String mensagem;
	
	/**
	 * Referência do componente de persistência.
	 */
	private UsuarioDAO dao;
	
	/**
	 * Referência para o usuario utilizado na inclusão (nova) ou edição.
	 */
	private Usuario usuario;
	
	/**
	 * Informação é utilizada na edição da mercadoria, quando a seleção de um registro na listagem ocorrer.
	 */
	private Long idSelecionado;
	
	/**
	 * Mantém os usuários apresentados na listagem indexadas pelo id.
	 * <strong>Importante:</strong> a consulta (query) no DataStore do App Engine pode retornar <i>dados antigos</i>, 
	 * que já foram removidos ou que ainda não foram incluidos, devido a replicação dos dados.
	 * 
	 * Dessa forma esse hashmap mantém um espelho do datastore para minizar o impacto desse modelo do App Engine.
	 */
	private Map<Long, Usuario> usuarios;
	
	public UsuarioMB() {
		dao = new UsuarioDAOObjectify();
		preencherUsuarios();
	}
	
	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public void setIdSelecionado(Long idSelecionado) {
		this.idSelecionado = idSelecionado;
	}
	
	public Long getIdSelecionado() {
		return idSelecionado;
	}
	
	/**
	 * @return <code>DataModel</code> para carregar a lista de usuarios.
	 */
	public DataModel<Usuario> getDmUsuarios() {
		return new ListDataModel<Usuario>(new ArrayList<Usuario>(usuarios.values()));
	}
	
	private void preencherUsuarios() {
		try {
			List<Usuario> listaUsuarios = new ArrayList<Usuario>(dao.getAll());
			usuarios = new HashMap<Long, Usuario>();
			for (Usuario m: listaUsuarios) {
				usuarios.put(m.getId(), m);
			}
			
			log.debug("Carregou a lista de usuarios ("+usuarios.size()+")");
		} catch(Exception ex) {
			log.error("Erro ao carregar a lista de usuarios.", ex);
			addMessage(getMessageFromI18N("msg.erro.listar.usuario"), ex.getMessage());
		}
		
	}
	
	/**
	 * Ação executada quando a página de inclusão de usuarios for carregada.
	 */
	public void incluir(){
		usuario = new Usuario();
		log.debug("Pronto pra incluir");
	}
	
	/**
	 * Ação executada quando a página de envio de notificacoes for carregada.
	 */
	public void enviar(){
		usuario = new Usuario();
		log.debug("Pronto pra enviar notificacoes");
	}
	
	/**
	 * Ação executada quando a página de edição de mercadorias for carregada.
	 */
	public void editar() {
		if (idSelecionado == null) {
			return;
		}
		usuario = usuarios.get(idSelecionado);
		log.debug("Pronto pra editar");
	}

	/**
	 * Operação acionada pela tela de inclusão ou edição, através do <code>commandButton</code> <strong>Salvar</strong>.
	 * @return Se a inclusão/edição foi realizada vai para listagem, senão permanece na mesma tela.
	 */
	public String salvar() {
		try {
			dao.save(usuario);
			usuarios.put(usuario.getId(), usuario);
		} catch(Exception ex) {
			log.error("Erro ao salvar usuario.", ex);
			addMessage(getMessageFromI18N("msg.erro.salvar.usuario"), ex.getMessage());
			return "";
		}
		log.debug("Salvour usuario "+usuario.getId());
		
		return "listaUsuarios";
	}
	
	/**
	 * Operação acionada pela tela de listagem, através do <code>commandButton</code> <strong>Atualizar</strong>. 
	 */
	public void atualizar() {
		preencherUsuarios();
	}
	
	/**
	 * Operação acionada pela tela de listagem, através do <code>commandButton</code> <strong>Atualizar</strong>. 
	 */
	public void enviarNotificacao() {
		if (Util.isTamanhoDaMensagemInvalido(mensagem)) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aten��o", "Mensagem deve ter entre 5 e 2048 caracteres."));
			return;
		}
			
		EnviaNotificacaoGCM en = new EnviaNotificacaoGCM();
		String retorno = en.enviaNotificacaoGCM(new ArrayList<Usuario>(usuarios.values()), mensagem);
        addMessage("Aten��o", retorno);
	}
	
	/**
	 * Operação acionada toda a vez que a  tela de listagem for carregada.
	 */
	public void reset() {
		usuario = null;
		idSelecionado = null;
	}
	
	/**
	 * Operação acionada pela tela de edição, através do <code>commandButton</code> <strong>Excluir</strong>.
	 * @return Se a exclusão for realizada vai para a listagem, senão permanece na mesma tela.
	 */
	public String remover() {
		try {
			dao.remove(usuario);
			usuarios.remove(usuario.getId());
		} catch(Exception ex) {
			log.error("Erro ao remover mercadoria.", ex);
			addMessage(getMessageFromI18N("msg.erro.remover.usuario"), ex.getMessage());
			return "";
		}
		log.debug("Removeu usuario "+usuario.getId());
		return "listaUsuarios";
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
