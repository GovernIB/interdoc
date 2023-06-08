package es.caib.interdoc.commons.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import es.caib.interdoc.commons.exception.ValidateXmlException;

public class XmlValidator {

	private final String xsdPath;

	private final Reader reader;

	private final StreamSource xsdInputStream;

	private static SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");

	public XmlValidator(String xml, StreamSource xsdStreamSource) {
		if (xml == null)
			throw new IllegalArgumentException("El xml de origen no puede ser nulo");
		if (xsdStreamSource == null)
			throw new IllegalArgumentException("El contenido del xsd no puede ser nulo");
		this.xsdPath = null;
		this.xsdInputStream = xsdStreamSource;
		this.reader = new StringReader(xml);
	}

	public XmlValidator(String xml, InputStream xsdInputStream) {
		if (xml == null)
			throw new IllegalArgumentException("El xml de origen no puede ser nulo");
		if (xsdInputStream == null)
			throw new IllegalArgumentException("El contenido del xsd no puede ser nulo");
		this.xsdPath = null;
		this.xsdInputStream = new StreamSource(xsdInputStream);
		this.reader = new StringReader(xml);
	}

	public XmlValidator(String xml, String xsdPath) {
		if (xml == null)
			throw new IllegalArgumentException("El xml de origen no puede ser nulo");
		if (xsdPath == null)
			throw new IllegalArgumentException("La ruta del xsd no puede ser nula");
		this.xsdInputStream = null;
		this.xsdPath = xsdPath;
		this.reader = new StringReader(xml);
	}

	public XmlValidator(File xmlFile, String xsdPath) throws FileNotFoundException {
		if (xmlFile == null)
			throw new IllegalArgumentException("El xml de origen no puede ser nulo");
		if (xsdPath == null)
			throw new IllegalArgumentException("La ruta del xsd no puede ser nulo");
		this.xsdInputStream = null;
		this.xsdPath = xsdPath;
		this.reader = new FileReader(xmlFile);
	}

	public void isValid() throws ValidateXmlException {
		try {
			validate();
		} catch (Exception ex) {
			throw new ValidateXmlException("Se ha producido un error al validar el xml: ".concat(ex.getMessage()),
					ex.getCause());
		}
	}

	public void validate() throws SAXException {
		Schema schema;
		synchronized (schemaFactory) {
			if (this.xsdPath != null) {
				schema = schemaFactory.newSchema(new StreamSource(this.xsdPath));
			} else {
				schema = schemaFactory.newSchema(this.xsdInputStream);
			}
		}
		Validator validator = schema.newValidator();
		Source source = new StreamSource(this.reader);
		try {
			validator.validate(source);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
