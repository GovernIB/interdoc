package es.caib.interdoc.back.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.shaded.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.interdoc.commons.utils.Configuracio;
import es.caib.interdoc.service.facade.FitxerServiceFacade;
import es.caib.interdoc.service.model.FitxerDTO;

@WebServlet(name = "UploadController", urlPatterns = { "/fileuploadservlet" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
		maxFileSize = 1024 * 1024 * 20, // 20 MB
		maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class UploadController extends HttpServlet {
	
	private static final long serialVersionUID = 5237496673616335944L;
	
	private static final Logger LOG = LoggerFactory.getLogger(UploadController.class);
	
	@EJB
	FitxerServiceFacade fitxerService;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Part filePart = request.getPart("file");
		String fileName = filePart.getSubmittedFileName();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_kkmmssSSS");
		String formattedString = LocalDateTime.now().format(formatter);
		String extension = FilenameUtils.getExtension(fileName);
		
		final String path = Configuracio.getFileTempPath() + File.separator  +  formattedString + "." + extension;
		LOG.info("PATH:" + path);
		
		Long fileLength = 0L;
		ByteArrayOutputStream contentData = new ByteArrayOutputStream();
		
		for (Part part : request.getParts()) {
			part.write(path);
			contentData.write(part.getInputStream().readAllBytes());
			fileLength += part.getSize();
		}
		
		String filePath = Files.probeContentType(new File(path).toPath());
		
		FitxerDTO fitxer = new FitxerDTO();
		fitxer.setNom(fileName);
        fitxer.setDescripcio(fileName);
        fitxer.setData(contentData.toByteArray());
        fitxer.setTamany(fileLength);
        fitxer.setDataCreacio(LocalDate.now());
        fitxer.setMime(filePath);
        fitxer.setRuta(path);
		
        LOG.info("UPLOADED FITXERDTO: " + fitxer.toString());
        
		Long fitxerId = fitxerService.create(fitxer);
		
		LOG.info("FileUpload OK. S'ha generat el fitxerId: " + fitxerId);
		
		response.setContentType("application/json");
		
		JSONObject jo = new JSONObject();
		jo.put("id", fitxerId);
		
		response.getWriter().print(jo);
	}

}
