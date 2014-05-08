package br.ufg.inf.integracao.sendnotification4android.client.controller;

import java.util.HashMap;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Widget;

/**
 * Classe abstrata que define o componente controlador default. O controlador como intermediador dos componentes GUI com a camada de serviço.
 * 
 * <p>O controlador também herda <code>ClickHandler</code> para centralizar as operações relacionadas a partir de ações dos usuários (<i>eventos</i>).</p>
 * 
 * @author douglas.japiassu
 */
public abstract class AbstractController implements ClickHandler {

	private HashMap<String, ClickHandler> handlers;

	public AbstractController() {
		handlers = new HashMap<String, ClickHandler>();
	}
	
	/**
	 * Método executado no clique do usuário.
	 */
	@Override
	public void onClick(ClickEvent event) {
		Widget w = (Widget) event.getSource();
		if (w == null) {
			return;
		}
		String text = w.getElement().getInnerText();
		ClickHandler handler = handlers.get(text);
		if (handler != null) {
			handler.onClick(event);
		}
	}
	
	/**
	 * Método utilizado para vincular uma ação (tratador de evento) clique com um <code>Widget</code>.
	 * @param w componente que deve ser vinculado a ação.
	 * @param h tratador da ação.
	 */
	public void registerHandler(Widget w, ClickHandler h) {
		if (w == null || h == null) {
			return;
		}
		w.addDomHandler(h, ClickEvent.getType());
		String text = w.getElement().getInnerText();
		handlers.put(text, h);
	}
}
