package br.ufg.inf.integracao.sendnotification4android.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Uma tela simples, para apresentar um título e uma descrição. Especialização de <code>DialogBox</code>.
 * 
 * <p>Poder ser utilizada para apresentar informações de erro e validação, por exemplo.</p>
 * 
 * @author YaW Tecnologia
 */
public class MessageDialog extends DialogBox {

	/**
	 * Cria um <code>Dialog</code> com informações de uma exceção.
	 * 
	 * @param ex
	 * @param title
	 */
	public MessageDialog(Throwable ex, String title) {
		this(ex.getMessage(), title, "labelError");
	}

	/**
	 * Cria um <code>Dialog</code> com a mensagem e titulo.
	 * 
	 * @param msg
	 * @param title
	 */
	public MessageDialog(String msg, String title) {
		this(msg, title, "");
	}
	
	/**
	 * Cria um <code>Dialog</code> com a mensagem, titulo e a definição da classe css.
	 * 
	 * @param msg
	 * @param title
	 * @param cssClass
	 */
	public MessageDialog(String msg, String title, String cssClass) {
		final Button bFechar = new Button("Fechar");
		bFechar.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
			}
		});

		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<h4>" + title + "</h4>"));
		dialogVPanel.add(new HTML("<p class='" + (cssClass == null ? "" : cssClass) +"'>" + msg + "</p>"));

		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_JUSTIFY);
		dialogVPanel.add(bFechar);
		add(dialogVPanel);
	}
}
