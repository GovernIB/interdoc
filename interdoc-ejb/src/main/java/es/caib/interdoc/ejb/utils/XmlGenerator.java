package es.caib.interdoc.ejb.utils;

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.List;
import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import es.caib.interdoc.plugins.arxiu.ArxiuPluginImpl;
import es.caib.pluginsib.arxiu.api.Document;

public class XmlGenerator {

    private XmlGenerator() {
    }

    // Replica la lògica de CSVQueryDocumentServiceImpl.generarEniDoc per forçar la generació local del XML.
    public static String generarReferencia(ArxiuPluginImpl plugin, String uuid) throws Exception {
	Document doc = plugin.descarregarDocument(uuid);

	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	DocumentBuilder builder = factory.newDocumentBuilder();
	org.w3c.dom.Document xmlDoc = builder.newDocument();

	TransformerFactory transformerFactory = TransformerFactory.newInstance();
	Transformer transformer = transformerFactory.newTransformer();
	StringWriter writer = new StringWriter();

	// Arrel del document ENIDOC XML
	org.w3c.dom.Element rootEnidoc = xmlDoc.createElementNS(
		"http://administracionelectronica.gob.es/ENI/XSD/v1.0/documento-e", "enidoc:documento");
	rootEnidoc.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:enidoc",
		"http://administracionelectronica.gob.es/ENI/XSD/v1.0/documento-e");
	rootEnidoc.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:enids",
		"http://administracionelectronica.gob.es/ENI/XSD/v1.0/firma");
	rootEnidoc.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:enidocmeta",
		"http://administracionelectronica.gob.es/ENI/XSD/v1.0/documento-e/metadatos");
	rootEnidoc.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:enifile",
		"http://administracionelectronica.gob.es/ENI/XSD/v1.0/documento-e/contenido");
	xmlDoc.appendChild(rootEnidoc);

	org.w3c.dom.Element contenido = xmlDoc.createElementNS(
		"http://administracionelectronica.gob.es/ENI/XSD/v1.0/documento-e/contenido", "enifile:contenido");
	contenido.setAttribute("Id", "CONTENT_ID_1");
	rootEnidoc.appendChild(contenido);

	org.w3c.dom.Element valorBinario = xmlDoc.createElement("enifile:ValorBinario");
	byte[] contingut = (doc.getContingut() != null) ? doc.getContingut().getContingut() : null;
	String contingutBase64 = (contingut != null) ? Base64.getEncoder().encodeToString(contingut) : "";
	valorBinario.setTextContent(contingutBase64);
	contenido.appendChild(valorBinario);

	org.w3c.dom.Element nombreFormato = xmlDoc.createElement("enifile:NombreFormato");
	nombreFormato.setTextContent(doc.getMetadades().getFormat().toString());
	contenido.appendChild(nombreFormato);

	// Metadatos
	org.w3c.dom.Element metadatos = xmlDoc.createElement("enidocmeta:metadatos");
	metadatos.setAttribute("Id", "METADATA_1");
	rootEnidoc.appendChild(metadatos);

	org.w3c.dom.Element versionNTI = xmlDoc.createElementNS(
		"http://administracionelectronica.gob.es/ENI/XSD/v1.0/documento-e/metadatos",
		"enidocmeta:VersionNTI");
	versionNTI.setTextContent("http://administracionelectronica.gob.es/ENI/XSD/v1.0/documento-e");
	metadatos.appendChild(versionNTI);

	org.w3c.dom.Element identificador = xmlDoc.createElement("enidocmeta:Identificador");
	identificador.setTextContent(doc.getMetadades().getIdentificador());
	metadatos.appendChild(identificador);

	org.w3c.dom.Element organo = xmlDoc.createElement("enidocmeta:Organo");
	List<String> organs = doc.getMetadades().getOrgans();
	organo.setTextContent(!organs.isEmpty() ? organs.get(0) : "");
	metadatos.appendChild(organo);

	org.w3c.dom.Element fechaCaptura = xmlDoc.createElement("enidocmeta:FechaCaptura");
	fechaCaptura.setTextContent(formatXmlDateTime(doc.getMetadades().getDataCaptura()));
	metadatos.appendChild(fechaCaptura);

	org.w3c.dom.Element origenCiudadanoAdministracion = xmlDoc
		.createElement("enidocmeta:OrigenCiudadanoAdministracion");
	origenCiudadanoAdministracion.setTextContent(toBooleanText(doc.getMetadades().getOrigen()));
	metadatos.appendChild(origenCiudadanoAdministracion);

	org.w3c.dom.Element estadoElaboracion = xmlDoc.createElement("enidocmeta:EstadoElaboracion");
	metadatos.appendChild(estadoElaboracion);

	org.w3c.dom.Element valorEstadoElaboracion = xmlDoc.createElement("enidocmeta:ValorEstadoElaboracion");
	valorEstadoElaboracion.setTextContent(doc.getMetadades().getEstatElaboracio().toString());
	estadoElaboracion.appendChild(valorEstadoElaboracion);

	org.w3c.dom.Element tipoDocumental = xmlDoc.createElement("enidocmeta:TipoDocumental");
	tipoDocumental.setTextContent(doc.getMetadades().getTipusDocumental().toString());
	metadatos.appendChild(tipoDocumental);

	// Firma
	org.w3c.dom.Element firmas = xmlDoc
		.createElementNS("http://administracionelectronica.gob.es/ENI/XSD/v1.0/firma", "enids:firmas");
	rootEnidoc.appendChild(firmas);

	org.w3c.dom.Element firma = xmlDoc.createElement("enids:firma");
	firma.setAttribute("Id", "SIGNATURE_ID_1");
	firmas.appendChild(firma);

	if (doc.getMetadades() != null && doc.getMetadades().getMetadadesAddicionals() != null
		&& !doc.getMetadades().getMetadadesAddicionals().isEmpty()
		&& doc.getMetadades().getMetadadesAddicionals().get("eni:tipoFirma") != null) {
	    org.w3c.dom.Element tipoFirma = xmlDoc.createElement("enids:TipoFirma");
	    tipoFirma.setTextContent(doc.getMetadades().getMetadadesAddicionals().get("eni:tipoFirma").toString());
	    firma.appendChild(tipoFirma);
	}

	org.w3c.dom.Element contenidoFirma = xmlDoc.createElement("enids:ContenidoFirma");
	firma.appendChild(contenidoFirma);

	org.w3c.dom.Element firmaConCertificado = xmlDoc.createElement("enids:FirmaConCertificado");
	contenidoFirma.appendChild(firmaConCertificado);

	org.w3c.dom.Element referenciaFirma = xmlDoc.createElement("enids:ReferenciaFirma");
	referenciaFirma.setTextContent("#CONTENT_ID_1");
	firmaConCertificado.appendChild(referenciaFirma);

	transformer.transform(new DOMSource(xmlDoc), new StreamResult(writer));
	return writer.toString();
    }

    private static String formatXmlDateTime(java.util.Date date) {
	if (date == null) {
	    return "";
	}
	TimeZone tz = TimeZone.getTimeZone("UTC");
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	sdf.setTimeZone(tz);
	return sdf.format(date);
    }

    private static String formatXmlDateTimeWithOffset(java.util.Date date) {
	if (date == null) {
	    return "";
	}
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
	return sdf.format(date);
    }

    private static String safeText(Object value) {
	return (value != null) ? value.toString() : "";
    }

    private static String toBooleanText(Object value) {
	String normalizedValue = safeText(value).trim();
	if ("1".equals(normalizedValue) || "true".equalsIgnoreCase(normalizedValue)) {
	    return "true";
	}
	if ("0".equals(normalizedValue) || "false".equalsIgnoreCase(normalizedValue)) {
	    return "false";
	}
	return "false";
    }

    public static String generarEniDocXades(ArxiuPluginImpl plugin, String uuid) throws Exception {
	Document doc = plugin.descarregarDocument(uuid);

	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	DocumentBuilder builder = factory.newDocumentBuilder();
	org.w3c.dom.Document xmlDoc = builder.newDocument();

	TransformerFactory transformerFactory = TransformerFactory.newInstance();
	Transformer transformer = transformerFactory.newTransformer();
	StringWriter writer = new StringWriter();

	// Arrel del document ENIDOC XML
	org.w3c.dom.Element rootEnidoc = xmlDoc.createElementNS(
		"http://administracionelectronica.gob.es/ENI/XSD/v1.0/documento-e", "enidoc:documento");
	rootEnidoc.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:enidoc",
		"http://administracionelectronica.gob.es/ENI/XSD/v1.0/documento-e");
	rootEnidoc.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:enids",
		"http://administracionelectronica.gob.es/ENI/XSD/v1.0/firma");
	rootEnidoc.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:enidocmeta",
		"http://administracionelectronica.gob.es/ENI/XSD/v1.0/documento-e/metadatos");
	rootEnidoc.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:enifile",
		"http://administracionelectronica.gob.es/ENI/XSD/v1.0/documento-e/contenido");
	xmlDoc.appendChild(rootEnidoc);

	org.w3c.dom.Element contenido = xmlDoc.createElementNS(
		"http://administracionelectronica.gob.es/ENI/XSD/v1.0/documento-e/contenido", "enifile:contenido");
	contenido.setAttribute("Id", "CONTENT_ID_1");
	rootEnidoc.appendChild(contenido);

	org.w3c.dom.Element datosXml = xmlDoc.createElement("enifile:DatosXML");
	datosXml.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:type", "xs:string");
	datosXml.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xs", "http://www.w3.org/2001/XMLSchema");
	datosXml.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xsi",
		"http://www.w3.org/2001/XMLSchema-instance");
	byte[] contingut = (doc.getContingut() != null) ? doc.getContingut().getContingut() : null;
	String contingutXml = (contingut != null) ? new String(contingut, StandardCharsets.UTF_8) : "";
	datosXml.appendChild(xmlDoc.createCDATASection(contingutXml));
	contenido.appendChild(datosXml);
    

	org.w3c.dom.Element nombreFormato = xmlDoc.createElement("enifile:NombreFormato");
	nombreFormato.setTextContent(doc.getMetadades().getFormat().toString());
	contenido.appendChild(nombreFormato);

	org.w3c.dom.Element metadatos = xmlDoc.createElement("enidocmeta:metadatos");
	metadatos.setAttribute("Id", "METADATA_1");
	rootEnidoc.appendChild(metadatos);

	org.w3c.dom.Element versionNTI = xmlDoc.createElementNS(
		"http://administracionelectronica.gob.es/ENI/XSD/v1.0/documento-e/metadatos",
		"enidocmeta:VersionNTI");
	versionNTI.setTextContent("http://administracionelectronica.gob.es/ENI/XSD/v1.0/documento-e");
	metadatos.appendChild(versionNTI);

	org.w3c.dom.Element identificador = xmlDoc.createElement("enidocmeta:Identificador");
	String identificadorValue = (doc.getMetadades() != null && doc.getMetadades().getIdentificador() != null)
		? doc.getMetadades().getIdentificador()
		: safeText(uuid);
	identificador.setTextContent(identificadorValue);
	metadatos.appendChild(identificador);

	org.w3c.dom.Element organo = xmlDoc.createElement("enidocmeta:Organo");
	String organoValue = "";
	if (doc.getMetadades() != null) {
	    List<String> organs = doc.getMetadades().getOrgans();
	    if (organs != null && !organs.isEmpty()) {
		organoValue = safeText(organs.get(0));
	    }
	}
	organo.setTextContent(organoValue);
	metadatos.appendChild(organo);

	org.w3c.dom.Element fechaCaptura = xmlDoc.createElement("enidocmeta:FechaCaptura");
	String fechaCapturaValue = (doc.getMetadades() != null)
		? formatXmlDateTimeWithOffset(doc.getMetadades().getDataCaptura())
		: "";
	fechaCaptura.setTextContent(fechaCapturaValue);
	metadatos.appendChild(fechaCaptura);

	org.w3c.dom.Element origenCiudadanoAdministracion = xmlDoc
		.createElement("enidocmeta:OrigenCiudadanoAdministracion");
	String origenValue = (doc.getMetadades() != null) ? toBooleanText(doc.getMetadades().getOrigen()) : "false";
	origenCiudadanoAdministracion.setTextContent(origenValue);
	metadatos.appendChild(origenCiudadanoAdministracion);

	org.w3c.dom.Element estadoElaboracion = xmlDoc.createElement("enidocmeta:EstadoElaboracion");
	metadatos.appendChild(estadoElaboracion);

    //TODO: Sempre estat EE99 per Interdoc?
	org.w3c.dom.Element valorEstadoElaboracion = xmlDoc.createElement("enidocmeta:ValorEstadoElaboracion");
	String estadoElaboracionValue = (doc.getMetadades() != null
		&& doc.getMetadades().getEstatElaboracio() != null)
			? doc.getMetadades().getEstatElaboracio().toString()
			: "EE99";
	valorEstadoElaboracion.setTextContent(estadoElaboracionValue);
	estadoElaboracion.appendChild(valorEstadoElaboracion);

    //TODO: Sempre TD99 per Interdoc?
	org.w3c.dom.Element tipoDocumental = xmlDoc.createElement("enidocmeta:TipoDocumental");
	String tipoDocumentalValue = (doc.getMetadades() != null && doc.getMetadades().getTipusDocumental() != null)
		? doc.getMetadades().getTipusDocumental().toString()
		: "TD99";
	tipoDocumental.setTextContent(tipoDocumentalValue);
	metadatos.appendChild(tipoDocumental);

	org.w3c.dom.Element firmas = xmlDoc
		.createElementNS("http://administracionelectronica.gob.es/ENI/XSD/v1.0/firma", "enids:firmas");
	rootEnidoc.appendChild(firmas);

	org.w3c.dom.Element firma = xmlDoc.createElement("enids:firma");
	firma.setAttribute("Id", "SIGNATURE_ID_1");
	firmas.appendChild(firma);

	String tipoFirmaValue = "TF02";
	if (doc.getMetadades() != null && doc.getMetadades().getMetadadesAddicionals() != null
		&& !doc.getMetadades().getMetadadesAddicionals().isEmpty()
		&& doc.getMetadades().getMetadadesAddicionals().get("eni:tipoFirma") != null) {
	    tipoFirmaValue = doc.getMetadades().getMetadadesAddicionals().get("eni:tipoFirma").toString();
	}
	org.w3c.dom.Element tipoFirma = xmlDoc.createElement("enids:TipoFirma");
	tipoFirma.setTextContent(tipoFirmaValue);
	firma.appendChild(tipoFirma);

	org.w3c.dom.Element contenidoFirma = xmlDoc.createElement("enids:ContenidoFirma");
	firma.appendChild(contenidoFirma);

	org.w3c.dom.Element firmaConCertificado = xmlDoc.createElement("enids:FirmaConCertificado");
	contenidoFirma.appendChild(firmaConCertificado);

    //TODO:Es sempre CONTENT_ID_1 per Interdoc?
	org.w3c.dom.Element referenciaFirma = xmlDoc.createElement("enids:ReferenciaFirma");
	referenciaFirma.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:type", "xs:string");
	referenciaFirma.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xs",
		"http://www.w3.org/2001/XMLSchema");
	referenciaFirma.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xsi",
		"http://www.w3.org/2001/XMLSchema-instance");
	referenciaFirma.setTextContent("#CONTENT_ID_1");
	firmaConCertificado.appendChild(referenciaFirma);

	transformer.transform(new DOMSource(xmlDoc), new StreamResult(writer));
	return writer.toString();
	}

}
