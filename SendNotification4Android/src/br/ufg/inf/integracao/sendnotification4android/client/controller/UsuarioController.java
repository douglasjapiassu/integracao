package br.ufg.inf.integracao.sendnotification4android.client.controller;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import br.ufg.inf.integracao.sendnotification4android.client.NotificationServiceAsync;
import br.ufg.inf.integracao.sendnotification4android.client.callback.DefaultCallback;
import br.ufg.inf.integracao.sendnotification4android.client.model.Usuario;
import br.ufg.inf.integracao.sendnotification4android.client.ui.EnviaNotificacaoDialog;
import br.ufg.inf.integracao.sendnotification4android.client.ui.UsuarioTable;

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
 * 
 * @author YaW Tecnologia
 */
public class UsuarioController extends AbstractController {

	private NotificationServiceAsync service;
	private VerticalPanel mainPanel = new VerticalPanel();
	private UsuarioTable usuariosTable = new UsuarioTable();
	private HorizontalPanel buttonPanel = new HorizontalPanel();
	private Label lastUpdatedLabel = new Label();
	
	public UsuarioController(NotificationServiceAsync s) {
		this.service = s;
		
		final EnviaNotificacaoDialog enviaNotificacaoDialog = new EnviaNotificacaoDialog();
		registerHandler(enviaNotificacaoDialog.getbSalvar(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final Usuario usuario = enviaNotificacaoDialog.getUsuario();
				if (usuario == null) return;
				
				service.add(usuario, new DefaultCallback<Long>() {
					@Override
					public void onSuccess(Long result) {
						usuario.setId(result);
						
						usuariosTable.adicionar(usuario);
						enviaNotificacaoDialog.hide();
					}
					
				});
			}
		});
		registerHandler(enviaNotificacaoDialog.getbCancelar(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				enviaNotificacaoDialog.hide();
			}
		});
		
		Button bNova = new Button("Nova");
		registerHandler(bNova, new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				enviaNotificacaoDialog.center();
				enviaNotificacaoDialog.show();
			}
		});
		
		Button bEditar = new Button("Editar");
		registerHandler(bEditar, new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Usuario m = usuariosTable.getUsuarioSelecionado();
				if (m == null) return;
				
				enviaNotificacaoDialog.setMercadoria(m);
				enviaNotificacaoDialog.center();
				enviaNotificacaoDialog.show();
			}
		});
		
		Button bExcluir = new Button("Excluir");
		registerHandler(bExcluir, new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final Usuario m = usuariosTable.getUsuarioSelecionado();
				if (m == null) return;
				
				service.remove(m, new DefaultCallback<Boolean>() {
					@Override
					public void onSuccess(Boolean result) {
						if (result) {
							usuariosTable.remover(m);
						}
					}
				});
			}
		});
		
		Button bAtualizar = new Button("Atualizar");
		registerHandler(bAtualizar, new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				fillTable();
			}
		});
		
		buttonPanel.addStyleName("buttonPanel");
		buttonPanel.add(bNova);
		buttonPanel.add(bEditar);
		buttonPanel.add(bExcluir);
		buttonPanel.add(bAtualizar);
		
		mainPanel.add(usuariosTable);
		mainPanel.add(buttonPanel);
		mainPanel.add(lastUpdatedLabel);

		RootPanel.get("notificacaoList").add(mainPanel);
		
		fillTable();
	}
	
	private void updateStateButtons(final boolean enabled) {
		Iterator<Widget> it = buttonPanel.iterator();
		while (it.hasNext()) {
			Widget w = it.next();
			if (w instanceof Button) {
				((Button) w).setEnabled(enabled);
			}
		}
	}
	
	private void fillTable() {
		updateStateButtons(false);
		service.getAll(new DefaultCallback<Usuario[]>() {
			
			@Override
			public void onSuccess(Usuario[] mercadorias) {
				usuariosTable.clear();
				fillTable(mercadorias);
				updateStateButtons(true);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				updateStateButtons(true);
				super.onFailure(caught);
			}
		});
	}
	
	private void fillTable(Usuario[] mercadorias) {
		usuariosTable.fillTable(mercadorias);
	    lastUpdatedLabel.setText("Última consulta: "+ DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_MEDIUM).format(new Date()));
	}
	
}
