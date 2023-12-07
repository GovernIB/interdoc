package es.caib.interdoc.back.controller;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.interdoc.commons.utils.Version;


public class PublicController extends HttpServlet{
	
	protected final Logger log = LoggerFactory.getLogger(PublicController.class);
	
	@Inject
	protected Version version;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)  
			throws ServletException,IOException {
		
		response.getWriter().write(version.getVersion());
		response.getWriter().flush();
		response.getWriter().close();
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)  
			throws ServletException,IOException {
		doGet(request,response);
	}
	
	

}
