package br.inf.ufg.integracao.server;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.inf.ufg.integracao.dao.DadosSistemaDAO;
import br.inf.ufg.integracao.dao.DadosSistemaDAOObjectify;
import br.inf.ufg.integracao.dao.UsuarioDAO;
import br.inf.ufg.integracao.dao.UsuarioDAOObjectify;
import br.inf.ufg.integracao.model.DadosSistema;
import br.inf.ufg.integracao.model.Usuario;

public class ReceiveRegistrationIDServlet extends HttpServlet {
 
    private static final long serialVersionUID = 1L;
    private UsuarioDAO usuarioDAO = new UsuarioDAOObjectify();
    private DadosSistemaDAO dadosSistemaDAO;
    private String senderID;
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    		throws ServletException, IOException {
    	String registrationId = request.getParameter("regId");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        Date data = new Date();
        
        if (registrationId == null) {
        	String resposta = request.getParameterMap().toString();
            
            response.setContentType("text/plain");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().write(resposta);
        	//response.sendError(400);
        } else {
            final Usuario usuario = new Usuario();
            usuario.setNome(name);
            usuario.setRegistrationId(registrationId);
            usuario.setEmail(email);
            usuario.setDataRegistro(data);
            
            Usuario usuarioP = usuarioDAO.findById(usuarioDAO.save(usuario));
            
            String resposta = "Registro efetuado com sucesso! Id: " + usuarioP.getId();
            
            response.setContentType("text/plain");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().write(resposta);
        }
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	response.setContentType("text/plain");
    	response.setHeader("Cache-Control", "no-cache");

    	dadosSistemaDAO = new DadosSistemaDAOObjectify();
    	List<DadosSistema> dadosSistema = dadosSistemaDAO.getAll();
    	if (dadosSistema.isEmpty()) {
    		response.getWriter().write("0");
    	} else {
    		senderID = dadosSistema.get(0).getSenderId();
    		response.getWriter().write(senderID);
    	}
        
    }
 
}