package br.ufg.inf.integracao.sendnotification4android.client.ui;

import br.ufg.inf.integracao.sendnotification4android.client.model.Usuario;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Define um <code>dialog</code> para realizar a inclusão e alteração de <code>Mercadoria</code>.
 * 
 * @author YaW Tecnologia
 */
public class EnviaNotificacaoDialog extends DialogBox {

	private TextBox tbNome;
	private TextBox tbEmail;
	private TextBox tbId;
	
	private Button bSalvar;
	private Button bCancelar;
	
	public EnviaNotificacaoDialog() {
		tbNome = new TextBox();
		tbEmail = new TextBox();
		tbId = new TextBox();
		tbId.setEnabled(false);
		
		this.add(montaPanelEnviarNotificacao());
	}

	/**
	 * @return monta um <code>Panel</code> com as labels e inputs de preenchimento.
	 */
	private VerticalPanel montaPanelEnviarNotificacao() {
		VerticalPanel fPanel = new VerticalPanel();
		fPanel.add(new Label("Nome:"));
		fPanel.add(tbNome);
		
		fPanel.add(new Label("Email:"));
		fPanel.add(tbEmail);
		
		fPanel.add(new Label("Id:"));
		fPanel.add(tbId);
		
		fPanel.add(montaPanelBotoesEditar());
		return fPanel;
	}
	
	/**
	 * @return monta <code>panel</code> com os botões.
	 */
	private HorizontalPanel montaPanelBotoesEditar() {
		HorizontalPanel bPanel = new HorizontalPanel();
		bSalvar = new Button("Salvar");
		bCancelar = new Button("Cancelar");
		bPanel.add(bSalvar);
		bPanel.add(bCancelar);
		return bPanel;
	}
	
	/**
	 * Limpa os componentes da tela.
	 */
	private void resetForm(){
		tbId.setText("");
		tbNome.setText("");
		tbEmail.setText("");
	}
	
	/**
	 * Preenche os campos da tela com o objeto Usuario.
	 * @param usuario
	 */
	private void populaTextFields(Usuario usuario){
		tbId.setText(usuario.getId().toString());
		tbNome.setText(usuario.getNome());
		tbEmail.setText(usuario.getEmail());
	}
	
	/**
	 * Valida o preenchimento dos campos, de acordo com os problemas encontrados um string é definida.
	 * 
	 * @return string com informações de validação.
	 */
	private String validador() {
		StringBuilder sb = new StringBuilder();
		sb.append(tbNome.getText() == null || "".equals(tbNome.getText().trim()) ? "Nome, " : "");
		sb.append(tbEmail.getText() == null || "".equals(tbEmail.getText().trim()) ? "Email, " : "");
		
		if (!sb.toString().isEmpty()) {
			sb.delete(sb.toString().length()-2, sb.toString().length());
		}
		return sb.toString();
	}
	
	/**
	 * @return <code>Usuario</code> de acordo com o que foi preenchido na tela.
	 *        Caso não passe pela validação devolve <code>null</code>.
	 */
	public Usuario getUsuario() {
		String msg = validador();
		if (!msg.isEmpty()) {
			MessageDialog msgDialog = new MessageDialog("Preencha o(s) campo(s): "+msg, "Validação dos campos");
			msgDialog.center();
			msgDialog.show();
			
			return null;
		}

		String nome = tbNome.getText();
		String email = tbEmail.getText();

		Long id = null;
		try {
			id = Long.parseLong(tbId.getText());
		} catch (Exception nex) {}

		return new Usuario(id, nome, email);
	}
	
	@Override
	public void hide() {
		resetForm();
		super.hide();
	}
	
	/**
	 * Carrega os componentes do dialog com as informações da <code>Mercadoria</code>.
	 * @param m
	 */
	public void setMercadoria(Usuario m) {
		if (m != null) {
			populaTextFields(m);
		}
	}
	
	public Button getbSalvar() {
		return bSalvar;
	}
	
	public Button getbCancelar() {
		return bCancelar;
	}
	
}
