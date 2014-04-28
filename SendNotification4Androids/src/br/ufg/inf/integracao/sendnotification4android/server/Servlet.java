package br.ufg.inf.integracao.sendnotification4android.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Servlet extends HttpServlet {
 
    private static final long serialVersionUID = 1L;
 
    // NOTE THIS IS A SIMPLE HTTP GET REQUEST
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String registrationId = request.getParameter("regId");
        String resposta = 
        		"<html> " + 
        		"<head>" +
        		"	<title>Hello World</title>" +
        		"</head>" + 
        		"<body>" + 
        		"	<h1>" + registrationId + "</h1>" +
        		"</body>" +
        		"</html>";
        
 
        // SET THE RESPONSE TYPE TO BE XML
        response.setContentType("text/html");
        response.setHeader("Cache-Control", "no-cache");
 
        // THIS IS THE RESPONSE THAT YOU WRITE BACK TO THE USER
        response.getWriter().write(resposta);
    }
 
}