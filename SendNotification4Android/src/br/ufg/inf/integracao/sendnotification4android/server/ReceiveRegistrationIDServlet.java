package br.ufg.inf.integracao.sendnotification4android.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gwt.core.client.GWT;

import br.ufg.inf.integracao.sendnotification4android.client.NotificationService;
import br.ufg.inf.integracao.sendnotification4android.client.NotificationServiceAsync;
import br.ufg.inf.integracao.sendnotification4android.client.callback.DefaultCallback;
import br.ufg.inf.integracao.sendnotification4android.client.model.Usuario;

public class ReceiveRegistrationIDServlet extends HttpServlet {
 
    private static final long serialVersionUID = 1L;
    private final NotificationServiceAsync notificationService = GWT.create(NotificationService.class);
 
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	    		throws ServletException, IOException {
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String registrationId = request.getParameter("regId");
        
        if (registrationId == null)
        	response.sendError(400);
        else {
        	String resposta = 
            		"<html> " + 
            		"<head>" +
            		"	<title>Registration Id</title>" +
            		"</head>" + 
            		"<body>" + 
            		"	<h1> Bem-vindo ao Receive Notification </h1>" +
            		"	<p> Você foi registrado no Servidor e agora passará a receber nossas notificações. <br>" +
            		"		(" + registrationId + ")</p>" +
            		"</body>" +
            		"</html>";
            
            response.setContentType("text/html");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().write(resposta);
            
            final Usuario usuario = new Usuario();
            usuario.setNome("Teste");
            usuario.setRegistrationId(registrationId);
            
            notificationService.add(usuario, new DefaultCallback<Long>() {
				@Override
				public void onSuccess(Long result) {
					usuario.setId(result);
				}
				
			});
        }
        
    }
 
}