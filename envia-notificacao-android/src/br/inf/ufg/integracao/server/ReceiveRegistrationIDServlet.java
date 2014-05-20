package br.inf.ufg.integracao.server;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.inf.ufg.integracao.dao.UsuarioDAO;
import br.inf.ufg.integracao.dao.UsuarioDAOObjectify;
import br.inf.ufg.integracao.model.Usuario;

public class ReceiveRegistrationIDServlet extends HttpServlet {
 
    private static final long serialVersionUID = 1L;
    private UsuarioDAO dao = new UsuarioDAOObjectify();
 
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	    		throws ServletException, IOException {
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String registrationId = request.getParameter("regId");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        Date data = new Date();
        
        if (registrationId == null)
        	response.sendError(400);
        else {
            final Usuario usuario = new Usuario();
            usuario.setNome(name);
            usuario.setRegistrationId(registrationId);
            usuario.setEmail(email);
            usuario.setDataRegistro(data);
            
            Usuario ue = dao.findById(dao.save(usuario));
            
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