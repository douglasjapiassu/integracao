package br.ufg.inf.integracao.sendnotification4android.client;


import br.ufg.inf.integracao.sendnotification4android.client.controller.UsuarioController;
import br.ufg.inf.integracao.sendnotification4android.client.ui.MessageDialog;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.UmbrellaException;

public class SendNotification4Android implements EntryPoint {

	private final UsuarioServiceAsync usuarioService = GWT.create(UsuarioService.class);

	public void onModuleLoad() {
		GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {

			@Override
			public void onUncaughtException(Throwable e) {
				Throwable ue = unwrap(e);
				MessageDialog msgDialog = new MessageDialog(ue, "Erro durante execução");
				msgDialog.center();
				msgDialog.show();
			}
		});
		
		new UsuarioController(usuarioService);
	}

	/**
	 * @param e Recebe uma exceção
	 * @return Procura pela exceção original caso o GWT use um wrapper.
	 * @see UmbrellaException
	 */
	private Throwable unwrap(Throwable e) {
		if (e instanceof UmbrellaException) {
			UmbrellaException ue = (UmbrellaException) e;
			if (ue.getCauses().size() == 1) {
				return unwrap(ue.getCauses().iterator().next());
			}
		}
		return e;
	}

}
