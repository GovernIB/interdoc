package es.caib.interdoc.ws.api;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import javax.xml.ws.BindingProvider;

import org.jboss.logging.Logger;

public class TestObtenerReferencia {

    public static void main(String[] args) {
        try {
            
            Properties prop = new Properties();
            prop.load(new FileInputStream(new File("test.properties")));
            
            String url = prop.getProperty("url");
            String username = prop.getProperty("username");
            String password = prop.getProperty("password");
            
                       
            ObtenerReferenciaWs referenciaWs = getObtenerReferenciaService(url, username, password);
                        
            String modoFirmaAnexoStr = prop.getProperty("modoFirmaAnexo");
            int modoFirmaAnexo = Integer.parseInt(modoFirmaAnexoStr);

            String fitxerAnexoStr = prop.getProperty("fitxerAnexo");
            File fitxerAnexo = new File(fitxerAnexoStr);
            
            String fitxerAnexoSignedStr = prop.getProperty("fitxerAnexoSigned");
            File fitxerAnexoSigned = new File(fitxerAnexoSignedStr);
            
            
            Date fechaRegistro = new Date();
            Long tipoRegistro = 0L; // 0 = Registro de entrada
            
            String documentoInteresado = prop.getProperty("documentoInteresado");
            String receptor = prop.getProperty("receptor");
            
            String numeroRegistroFormateado = prop.getProperty("numeroRegistroFormateado");
            
            /*String response = guardarDocumentoInterdoc(referenciaWs, documentoInteresado,
                    receptor, numeroRegistroFormateado, fechaRegistro, tipoRegistro, modoFirmaAnexo,
                    fitxerAnexo);*/
            
            String response = guardarDocumentoInterdoc(referenciaWs, documentoInteresado,
                    receptor, numeroRegistroFormateado, fechaRegistro, tipoRegistro, modoFirmaAnexo,
                    fitxerAnexoSigned);
            
            System.out.println("Referencia obtenida: " + response);
            
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    static int MODO_FIRMA_ANEXO_SINFIRMA = 0;
    static int MODO_FIRMA_ANEXO_ATTACHED = 1; // Document amb firma adjunta
    static int MODO_FIRMA_ANEXO_DETACHED = 2;

    static String FORMATO_FECHA_SICRES4 = "yyyyMMddHHmmssZ";

    public static final Logger log = Logger.getLogger(TestObtenerReferencia.class);

    //private static final String OBTENER_REFERENCIA_WS = "interdocapi/interna/protected/ObtenerReferenciaWs";

    /**
     * @return
     */
    public static ObtenerReferenciaWs getObtenerReferenciaService(String server, String username, String password)
            throws java.lang.Exception {

        final String endpoint = server;
        log.info(server);

        URL wsdlLocation = null;
        wsdlLocation = new URL(endpoint + "?wsdl");
        log.info("url " + wsdlLocation);

        ObtenerReferenciaWsService service = new ObtenerReferenciaWsService(wsdlLocation);

        ObtenerReferenciaWs api = service.getObtenerReferenciaWs();

        Map<String, Object> reqContext = ((BindingProvider) api).getRequestContext();
        reqContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint);
        reqContext.put(BindingProvider.USERNAME_PROPERTY, username);
        reqContext.put(BindingProvider.PASSWORD_PROPERTY, password);

        reqContext.put("javax.xml.ws.client.connectionTimeout", 500000L);
        reqContext.put("javax.xml.ws.client.receiveTimeout", 500000L);

        return api;
    }

    public static String guardarDocumentoInterdoc(ObtenerReferenciaWs referenciaWs, String documentoInteresado,
            String receptor, String numeroRegistroFormateado, Date fechaRegistro, Long tipoRegistro, int modoFirmaAnexo,
            File fitxerAnexo) throws java.lang.Exception {

        Long idEntidad = 0L; // 0 = CAIB

        String codigoDir3 = "A04003003"; // Código DIR3 de la entidad a la que se le asignará la referencia. En caso de ser 0, se asignará a CAIB

        //Preparamos el objeto a enviar
        ObtenerReferenciaRequestInfo obtenerReferenciaRequestInfo = new ObtenerReferenciaRequestInfo();
        obtenerReferenciaRequestInfo.setAplicacioId("interdoc");
        obtenerReferenciaRequestInfo.setEmisor("A04019281");
        obtenerReferenciaRequestInfo.setReceptor(receptor);
        obtenerReferenciaRequestInfo.setEntitatId(codigoDir3);

        obtenerReferenciaRequestInfo.setOrigen("1");
        obtenerReferenciaRequestInfo.setEstatElaboracio("EE99");
        obtenerReferenciaRequestInfo.setTipusDocumental("TD01");
        obtenerReferenciaRequestInfo.setNumeroRegistre(numeroRegistroFormateado);

        boolean isJustificante = false;
        if (isJustificante) {
            //obtenerReferenciaRequestInfo.setUuid(anexo.getCustodiaID());
            throw new java.lang.Exception("No se ha implementado el caso de justificante");
        } else {
            Fitxer fitxer = new Fitxer();
            Fitxer fitxerFirma = new Fitxer();
            Firma firma = new Firma();

            if (MODO_FIRMA_ANEXO_SINFIRMA == modoFirmaAnexo) {
                //Preparamos el fitxer a enviar
                System.out.println("Modo de firma: SIN FIRMA");
                fitxer.setData(Files.readAllBytes(fitxerAnexo.toPath()));
                fitxer.setDescripcio("Descripcio del fitxer " + fitxerAnexo.getName());
                fitxer.setMime("application/pdf");
                fitxer.setNom(fitxerAnexo.getName());
            }else if (MODO_FIRMA_ANEXO_ATTACHED == modoFirmaAnexo) {
                System.out.println("Modo de firma: ATTACHED");
                //Preparamos el Documento a enviar
                fitxer.setData(Files.readAllBytes(fitxerAnexo.toPath()));
                fitxer.setDescripcio("Descripcio del fitxer " + fitxerAnexo.getName());
                fitxer.setMime("application/pdf");
                fitxer.setNom(fitxerAnexo.getName());
            
                //PADES("TF06")
                firma.setFormat("TF06");
                firma.setPerfil("AdES-BES");
                obtenerReferenciaRequestInfo.setFirma(firma);
            
            }
            
            /*if (MODO_FIRMA_ANEXO_DETACHED == modoFirmaAnexo) {
                // Preparamos el fitxer del documento, el fitxer de la firma y los datos de la firma a enviar
                fitxer.setData(anexoFull.getData());
                fitxer.setDescripcio(anexoFull.getTituloCorto());
                fitxer.setMime(anexoFull.getMime());
                fitxer.setNom(anexoFull.getFileName());
            
                fitxerFirma.setData(anexoFull.getSignData());
                fitxerFirma.setDescripcio(anexoFull.getSignaturaTituloCorto());
                fitxerFirma.setMime(anexoFull.getSignMime());
                fitxerFirma.setNom(anexoFull.getSignFileName());
            
                firma.setFitxer(fitxerFirma);
                firma.setFormat(anexoFull.transformarTipoFirma(anexo));
                firma.setPerfil(anexo.getSignProfile());
            
                obtenerReferenciaRequestInfo.setFirma(firma);
            
            }*/
            else {
                throw new java.lang.Exception("Modo de firma no soportado");
            }
            
            obtenerReferenciaRequestInfo.setDocument(fitxer);

            //Se envia el documento/codigodir3 del interesado
            obtenerReferenciaRequestInfo.getInteressats().add(documentoInteresado);
            obtenerReferenciaRequestInfo.setCsv(null);

            List<Metadada> metadades = new ArrayList<Metadada>();
            { //Metadatos de escaneo
                {
                    Metadada metadada = new Metadada();
                    metadada.setClau("eni:resolucion");
                    metadada.setValor("300");
                    metadades.add(metadada);
                }
                {
                    Metadada metadada = new Metadada();
                    metadada.setClau("eni:profundidad_color");
                    metadada.setValor("8");
                    metadades.add(metadada);
                }
                {
                    Metadada metadada = new Metadada();
                    metadada.setClau("eni:idioma");
                    metadada.setValor("ca");
                    metadades.add(metadada);
                }

                {
                    Metadada metadada = new Metadada();
                    metadada.setClau("eni:descripcion");
                    metadada.setValor("Descripcion del documento");
                    metadades.add(metadada);
                }

            }

            //Metadata CM:TITLE
            {
                Metadada metadada = new Metadada();

                metadada.setClau("cm:title");
                metadada.setValor("Prova de creacio de referencia");
                metadades.add(metadada);
            }

            //Fecha de Captura
            {
                Metadada metadada = new Metadada();
                GregorianCalendar fecha = new GregorianCalendar();
                fecha.setTime(new Date());
                metadada.setClau("eni:fecha_inicio");
                DateFormat formatter = new SimpleDateFormat(FORMATO_FECHA_SICRES4);
                metadada.setValor(formatter.format(fecha.getTime()));
                metadades.add(metadada);
            }

            {
                Metadada metadada = new Metadada();

                //Metadata eni:numero_asiento_registral
                metadada.setClau("eni:numero_asiento_registral");
                metadada.setValor(numeroRegistroFormateado);
                metadades.add(metadada);
            }

            //Metadata eni:fecha_asiento_registral
            if (fechaRegistro != null) {
                TimeZone tz = TimeZone.getTimeZone("UTC");
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                df.setTimeZone(tz);
                {
                    Metadada metadada = new Metadada();

                    metadada.setClau("eni:fecha_asiento_registral");
                    metadada.setValor(df.format(fechaRegistro));
                    metadades.add(metadada);
                }
            }

            //Metadata eni:codigo_oficina_registro
            {
                Metadada metadada = new Metadada();

                metadada.setClau("eni:codigo_oficina_registro");
                metadada.setValor("O00015973");
                metadades.add(metadada);
            }
            //Metadata eni:codigo_oficina_registro
            {
                Metadada metadada = new Metadada();

                metadada.setClau("eni:tipo_asiento_registral");
                metadada.setValor("0"); // 0 =  Registro de entrada
                metadades.add(metadada);
            }
            {
                Metadada metadada = new Metadada();

                metadada.setClau("eni:app_tramite_exp");
                metadada.setValor("REGWEB3");
                metadades.add(metadada);
            }

            obtenerReferenciaRequestInfo.setMetadades(metadades);
        }

        return referenciaWs.creaReferencia(obtenerReferenciaRequestInfo);

    }

}