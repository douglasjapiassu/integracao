package br.ufg.inf.integracao.sendnotification4android.client.controller;

import java.util.Date;
import java.util.Iterator;

import br.ufg.inf.integracao.sendnotification4android.client.UsuarioServiceAsync;
import br.ufg.inf.integracao.sendnotification4android.client.callback.DefaultCallback;
import br.ufg.inf.integracao.sendnotification4android.client.model.Usuario;
import br.ufg.inf.integracao.sendnotification4android.client.ui.EnviaNotificacaoDialog;
import br.ufg.inf.integracao.sendnotification4android.client.ui.UsuarioTable;
import br.ufg.inf.integracao.sendnotification4android.server.HTTPRequestGCM;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Define o controlador para operações com o cadastro <code>Mercadoria</code>.
 * 
 * <p>Esse componente é responsável por instanciar objetos GUI, além de vincular as operações com o serviço web.</code>
 * 
 * <p>As operações com a camada servidor são acessadas pela interface <code>MercadoriaServiceAsync</code>.</p>
 */
public class UsuarioController extends AbstractController {

	private UsuarioServiceAsync service;
	private HTTPRequestGCM requisicaoGCM = new HTTPRequestGCM();
	private VerticalPanel mainPanel = new VerticalPanel();
	private UsuarioTable usuariosTable = new UsuarioTable();
	private HorizontalPanel buttonPanel = new HorizontalPanel();
	private Label ultimaAtualizacaoLabel = new Label();
	
	public UsuarioController(UsuarioServiceAsync s) {
		this.service = s;
		
		final EnviaNotificacaoDialog enviaNotificacaoDialog = new EnviaNotificacaoDialog();
		registerHandler(enviaNotificacaoDialog.getBtnEnviar(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final String mensagem = enviaNotificacaoDialog.getMensagem();
				if (mensagem == null) return;
				
				requisicaoGCM.enviaNotificacaoGCM(mensagem);
				enviaNotificacaoDialog.hide();
			}
		});
		registerHandler(enviaNotificacaoDialog.getBtnCancelar(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				enviaNotificacaoDialog.hide();
			}
		});
		
		Button btnEnviarNotificacao = new Button("Enviar Notificação");
		registerHandler(btnEnviarNotificacao, new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				enviaNotificacaoDialog.center();
				enviaNotificacaoDialog.show();
			}
		});
		
		Button btnExcluirUsuario = new Button("Excluir");
		registerHandler(btnExcluirUsuario, new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final Usuario m = usuariosTable.getUsuarioSelecionado();
				if (m == null) return;
				
				service.remover(m, new DefaultCallback<Boolean>() {
					@Override
					public void onSuccess(Boolean result) {
						if (result) {
							usuariosTable.remover(m);
						}
					}
				});
			}
		});
		
		Button btnAtualizarLista = new Button("Atualizar");
		registerHandler(btnAtualizarLista, new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				preencherTabela();
			}
		});
		
		buttonPanel.addStyleName("buttonPanel");
		buttonPanel.add(btnEnviarNotificacao);
		buttonPanel.add(btnExcluirUsuario);
		buttonPanel.add(btnAtualizarLista);
		
		mainPanel.add(usuariosTable);
		mainPanel.add(buttonPanel);
		mainPanel.add(ultimaAtualizacaoLabel);

		RootPanel.get("usuariosList").add(mainPanel);
		
		preencherTabela();
	}
	
	private void atualizarEstadoBotoes(final boolean isAtivo) {
		Iterator<Widget> it = buttonPanel.iterator();
		while (it.hasNext()) {
			Widget w = it.next();
			if (w instanceof Button) {
				((Button) w).setEnabled(isAtivo);
			}
		}
	}
	
	private void preencherTabela() {
		atualizarEstadoBotoes(false);
		service.getTodosUsuarios(new DefaultCallback<Usuario[]>() {
			
			@Override
			public void onSuccess(Usuario[] mercadorias) {
				usuariosTable.clear();
				preencherTabela(mercadorias);
				atualizarEstadoBotoes(true);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				atualizarEstadoBotoes(true);
				super.onFailure(caught);
			}
		});
	}
	
	private void preencherTabela(Usuario[] mercadorias) {
		usuariosTable.fillTable(mercadorias);
	    ultimaAtualizacaoLabel.setText("Última consulta: "+ DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_SHORT).format(new Date()));
	}
	
}
