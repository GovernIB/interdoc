package es.caib.interdoc.commons.utils;

public enum DocumentExtensio {

	GML(".gml"),
	GZ(".gz"),
	ZIP(".zip"),
	AVI(".avi"),
	CSV(".csv"),
	HTML(".html"),
	HTM(".htm"),
	CSS(".css"),
	JPG(".jpg"),
	JPEG(".jpeg"),
	MHTML(".mhtml"),
	MHT(".mht"),
	ODT(".odt"),
	ODS(".ods"),
	ODP(".odp"),
	ODG(".odg"),
	DOCX(".docx"),
	XLSX(".xlsx"),
	PPTX(".pptx"),
	PDF(".pdf"),
	PNG(".png"),
	RTF(".rtf"),
	SVG(".svg"),
	TIFF(".tiff"),
	TXT(".txt"),
	MP3(".mp3"),
	OGG(".ogg"),
	OGA(".oga"),
	MPEG(".mpeg"),
	MP4(".mp4"),
	WEBM(".webm"),
	CSIG(".csig"),
	XSIG(".xsig"),
	XML(".xml");

	private String str;

	DocumentExtensio(String str) {
		this.str = str;
    }

	public static DocumentExtensio toEnum(String str) {
		if (str != null) {
		    for (DocumentExtensio valor: DocumentExtensio.values()) {
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

    public static DocumentExtensio getExtensioArxiu(String extensio) {
        return DocumentExtensio.toEnum(extensio);
    }

}