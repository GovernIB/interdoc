package es.caib.interdoc.commons.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBIntrospector;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public final class MarshallUtil {

	private MarshallUtil() {
		throw new UnsupportedOperationException("Not instanciable class!");
	}

	
	public static <T> String generateXML(Class<T> clase, T object) throws JAXBException {
		if (clase == null)
			throw new IllegalArgumentException("La clase del objeto a extraer no puede ser nula");
		if (object == null)
			throw new IllegalArgumentException("El objeto a extraer no puede ser nulo");
		String xmlDeMensaje = "";
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(new Class[] { clase });
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty("jaxb.formatted.output", Boolean.valueOf(true));
			StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(object, sw);
			xmlDeMensaje = sw.toString();
		} catch (JAXBException e) {
			e.printStackTrace();
			throw e;
		}
		return xmlDeMensaje;
	}

	
	public static <T> T generateObject(byte[] bytes, Class<T> clase) throws JAXBException {
		if (bytes == null)
			throw new IllegalArgumentException("Los bytes del fichero no pueden ser nulos");
		if (clase == null)
			throw new IllegalArgumentException("La clase del objeto a extraer no puede ser nula");
		T object = null;
		ByteArrayInputStream input = new ByteArrayInputStream(bytes);
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(new Class[] { clase });
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			if (jaxbUnmarshaller.unmarshal(input) instanceof javax.xml.bind.JAXBElement) {
				JAXBIntrospector introspector = jaxbContext.createJAXBIntrospector();
				input = new ByteArrayInputStream(bytes);
				object = (T) JAXBIntrospector.getValue(jaxbUnmarshaller.unmarshal(input));
			} else {
				input = new ByteArrayInputStream(bytes);
				object = (T) jaxbUnmarshaller.unmarshal(input);
			}
		} catch (JAXBException e) {
			e.printStackTrace();
			throw e;
		}
		return object;
	}
	
	
	public static <T> T generateObject(InputStreamReader inputStreamReader, Class<T> clase) throws JAXBException {
	    T object = null;
	    try {
	      JAXBContext jaxbContext = JAXBContext.newInstance(new Class[] { clase });
	      Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	      if (jaxbUnmarshaller.unmarshal(inputStreamReader) instanceof javax.xml.bind.JAXBElement) {
	        JAXBIntrospector introspector = jaxbContext.createJAXBIntrospector();
	        object = (T)JAXBIntrospector.getValue(jaxbUnmarshaller.unmarshal(inputStreamReader));
	      } else {
	        object = (T)jaxbUnmarshaller.unmarshal(inputStreamReader);
	      } 
	    } catch (JAXBException e) {
	      e.printStackTrace();
	      throw e;
	    } 
	    return object;
	  }

}
