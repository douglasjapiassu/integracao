package br.ufg.inf.integracao.sendnotification4android.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gwt.core.client.GWT;

import br.ufg.inf.integracao.sendnotification4android.client.UsuarioService;
import br.ufg.inf.integracao.sendnotification4android.client.UsuarioServiceAsync;
import br.ufg.inf.integracao.sendnotification4android.client.callback.DefaultCallback;
import br.ufg.inf.integracao.sendnotification4android.client.model.Usuario;
import br.ufg.inf.integracao.sendnotification4android.server.dao.UsuarioDAO;
import br.ufg.inf.integracao.sendnotification4android.server.dao.UsuarioDAOJDO;
import br.ufg.inf.integracao.sendnotification4android.server.model.UsuarioEntity;

public class ReceiveRegistrationIDServlet extends HttpServlet {
 
    private static final long serialVersionUID = 1L;
    private UsuarioDAO dao = new UsuarioDAOJDO();
 
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	    		throws ServletException, IOException {
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String registrationId = request.getParameter("regId");
        
        if (registrationId == null)
        	response.sendError(400);
        else {
            final Usuario usuario = new Usuario();
            usuario.setNome("Teste");
            usuario.setRegistrationId(registrationId);
            
            UsuarioEntity ue = dao.save(new UsuarioEntity(usuario));
            
            String resposta = 
            		"<html> " + 
            		"<head>" +
            		"	<title>Registration Id</title>" +
            		"</head>" + 
            		"<body>" + 
            		"	<h1> Bem-vindo ao Receive Notification </h1>" +
            		"	<p> Voc� foi registrado no Servidor e agora passar� a receber nossas notifica��es. <br>" +
            		"		Seu id de usuário é " + ue.getId() + " <br>" +
            		"		Seu identificador do aparelho é " + registrationId + ")</p>" +
            		"</body>" +
            		"</html>";
            
            response.setContentType("text/html");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().write(resposta);
        }
        
    }
 
}