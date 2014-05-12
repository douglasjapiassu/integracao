package br.ufg.inf.integracao.sendnotification4android.client.ui;

import br.ufg.inf.integracao.sendnotification4android.util.Util;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Define um dialog para o envio de notificações para os usuários cadastrados.
 * 
 * @author douglas.japiassu
 */
public class EnviaNotificacaoDialog extends DialogBox {

	private TextArea taMensagem = new TextArea();
	
	private Button btnEnviar;
	private Button btnCancelar;
	
	public EnviaNotificacaoDialog() {
		taMensagem.getElement().setAttribute("maxlength", "2048");
		this.add(montaPanelEnviarNotificacao());
	}

	/**
	 * @return monta um <code>Panel</code> com as labels e inputs de preenchimento.
	 */
	private VerticalPanel montaPanelEnviarNotificacao() {
		VerticalPanel fPanel = new VerticalPanel();
		fPanel.add(new Label("Mensagem:"));
		fPanel.add(taMensagem);
		
		fPanel.add(montaPanelBotoesEditar());
		return fPanel;
	}
	
	/**
	 * @return monta <code>panel</code> com os botões.
	 */
	private HorizontalPanel montaPanelBotoesEditar() {
		HorizontalPanel bPanel = new HorizontalPanel();
		btnEnviar = new Button("Enviar");
		btnCancelar = new Button("Cancelar");
		bPanel.add(btnEnviar);
		bPanel.add(btnCancelar);
		return bPanel;
	}
	
	/**
	 * @return String mensagem, caso a mesma seja válida (tamanho menor que 2kbytes)
	 */
	public String getMensagem() {
		String mensagem = taMensagem.getText();
		
		if (Util.isTamanhoDaMensagemInvalido(mensagem)) {
			MessageDialog msgDialog = new MessageDialog(
					"Tamanho máximo permitido para a mensagem é de 2048 caracteres: ",
					"Validação da mensagem");
			msgDialog.center();
			msgDialog.show();
			
			return null;
		}
		
		return mensagem;
	}
	
	@Override
	public void hide() {
		taMensagem.setText("");
		super.hide();
	}
	
	public Button getBtnEnviar() {
		return btnEnviar;
	}
	
	public Button getBtnCancelar() {
		return btnCancelar;
	}
	
}
