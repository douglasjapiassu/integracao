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
	private TextBox tbDescricao;
	private TextBox tbQuantidade;
	private TextBox tbPreco;
	private TextBox tbId;
	
	private Button bSalvar;
	private Button bCancelar;
	
	public EnviaNotificacaoDialog() {
		tbNome = new TextBox();
		tbDescricao = new TextBox();
		tbQuantidade = new TextBox();
		tbPreco = new TextBox();
		tbId = new TextBox();
		tbId.setEnabled(false);
		
		this.add(montaPanelEditarMercadoria());
	}

	/**
	 * @return monta um <code>Panel</code> com as labels e inputs de preenchimento.
	 */
	private VerticalPanel montaPanelEditarMercadoria() {
		VerticalPanel fPanel = new VerticalPanel();
		fPanel.add(new Label("Nome:"));
		fPanel.add(tbNome);
		
		fPanel.add(new Label("Descricao:"));
		fPanel.add(tbDescricao);
		
		fPanel.add(new Label("Quantidade:"));
		fPanel.add(tbQuantidade);
		
		fPanel.add(new Label("Preco:"));
		fPanel.add(tbPreco);
		
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
		tbDescricao.setText("");
		tbPreco.setText("");
		tbQuantidade.setText("1");
	}
	
	/**
	 * Preenche os campos da tela com o objeto <code>Mercadoria</code>.
	 * @param m
	 */
	private void populaTextFields(Usuario m){
		tbId.setText(m.getId().toString());
		tbNome.setText(m.getNome());
		tbDescricao.setText(m.getDescricao());
		tbQuantidade.setText(m.getQuantidade().toString());
		tbPreco.setText(Usuario.convertPrecoToString(m.getPreco()));
	}
	
	/**
	 * Valida o preenchimento dos campos, de acordo com os problemas encontrados um string é definida.
	 * 
	 * @return string com informações de validação.
	 */
	private String validador() {
		StringBuilder sb = new StringBuilder();
		sb.append(tbNome.getText() == null || "".equals(tbNome.getText().trim()) ? "Nome, " : "");
		sb.append(tbPreco.getText() == null || "".equals(tbPreco.getText().trim()) ? "Preco, " : "");
		sb.append(tbQuantidade.getText() == null || "".equals(tbQuantidade.getText().trim()) ? "Quantidade, " : "");
		
		if (!sb.toString().isEmpty()) {
			sb.delete(sb.toString().length()-2, sb.toString().length());
		}
		return sb.toString();
	}
	
	/**
	 * @return <code>Mercadoria</code> de acordo com o que foi preenchido na tela.
	 *        Caso não passe pela validação devolve <code>null</code>.
	 */
	public Usuario getMercadoria() {
		String msg = validador();
		if (!msg.isEmpty()) {
			MessageDialog msgDialog = new MessageDialog("Preencha o(s) campo(s): "+msg, "Validação dos campos");
			msgDialog.center();
			msgDialog.show();
			
			return null;
		}

		String nome = tbNome.getText();
		String descricao = tbDescricao.getText();

		Integer quantidade = null;
		try {
			quantidade = Integer.valueOf(tbQuantidade.getText());
		} catch (NumberFormatException nex) {
			throw new RuntimeException("Campo quantidade com conteúdo inválido!");
		}
		if (quantidade <= 0)
			throw new RuntimeException("Quantidade inválida. Quantidade mínima é 1!");

		Double preco = null;
		try {
			preco = Usuario.formatStringToPreco(tbPreco.getText());
		} catch (Exception nex) {
			throw new RuntimeException("Campo preco com conteudo invalido!");
		}
		if (preco.intValue() <= 0)
			throw new RuntimeException("Preço inválida. Preço mínimo é 1!");

		Long id = null;
		try {
			id = Long.parseLong(tbId.getText());
		} catch (Exception nex) {}

		return new Usuario(id, nome, descricao, quantidade, preco);
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
