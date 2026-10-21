package es.caib.interdoc.commons.utils;

public enum DocumentFormat {

	GML("GML"),
	WFS("WFS"),
	WMS("WMS"),
	GZIP("GZIP"),
	ZIP("ZIP"),
	AVI("AVI"),
	MP4A("MPEG-4 MP4 media"),
	CSV("Comma Separated Values"),
	HTML("HTML"),
	CSS("CSS"),
	JPEG("JPEG"),
	MHTML("MHTML"),
	OASIS12("ISO/IEC 26300:2006 OASIS 1.2"),
	SOXML("application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
	PDF("PDF"),
	PDFA("PDF/A"),
	PNG("PNG"),
	RTF("RTF"),
	SVG("SVG"),
	TIFF("TIFF"),
	TXT("TXT"),
	XHTML("XHTML"),
	MP3("MP3. MPEG-1 Audio Layer 3"),
	OGG("OGG-Vorbis"),
	MP4V("MPEG-4 MP4 vídeo"),
	WEBM("WebM"),
	CSIG("csig"),
	XSIG("xsig"),
	XML("xml");

	private String str;

	DocumentFormat(String str) {
		this.str = str;
    }

	public static DocumentFormat toEnum(String str) {
		if (str != null) {
		    for (DocumentFormat valor: DocumentFormat.values()) {
		        if (valor.toString().equals(str)) {
		            return valor;
		        }
		    }
		}
	    return null;
	}

	@Override
	public String toString() {
		return str;
	}

    public static DocumentFormat getDocumentFormat(DocumentExtensio extension) {
        switch (extension) {
            case AVI:
                return DocumentFormat.AVI;
            case CSS:
                return DocumentFormat.CSS;
            case CSV:
                return DocumentFormat.CSV;
            case DOCX:
                return DocumentFormat.SOXML;
            case GML:
                return DocumentFormat.GML;
            case GZ:
                return DocumentFormat.GZIP;
            case HTM:
                return DocumentFormat.XHTML;
            case HTML:
                return DocumentFormat.XHTML;
            case JPEG:
                return DocumentFormat.JPEG;
            case JPG:
                return DocumentFormat.JPEG;
            case MHT:
                return DocumentFormat.MHTML;
            case MHTML:
                return DocumentFormat.MHTML;
            case MP3:
                return DocumentFormat.MP3;
            case MP4:
                return DocumentFormat.MP4V;
            case MPEG:
                return DocumentFormat.MP4V;
            case ODG:
                return DocumentFormat.OASIS12;
            case ODP:
                return DocumentFormat.OASIS12;
            case ODS:
                return DocumentFormat.OASIS12;
            case ODT:
                return DocumentFormat.OASIS12;
            case OGA:
                return DocumentFormat.OGG;
            case OGG:
                return DocumentFormat.OGG;
            case PDF:
                return DocumentFormat.PDF;
            case PNG:
                return DocumentFormat.PNG;
            case PPTX:
                return DocumentFormat.SOXML;
            case RTF:
                return DocumentFormat.RTF;
            case SVG:
                return DocumentFormat.SVG;
            case TIFF:
                return DocumentFormat.TIFF;
            case TXT:
                return DocumentFormat.TXT;
            case WEBM:
                return DocumentFormat.WEBM;
            case XLSX:
                return DocumentFormat.SOXML;
            case ZIP:
                return DocumentFormat.ZIP;
            case CSIG:
                return DocumentFormat.CSIG;
            case XSIG:
                return DocumentFormat.XSIG;
            case XML:
                return DocumentFormat.XML;
        }
        return null;
    }

}