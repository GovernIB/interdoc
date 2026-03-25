package es.caib.interdoc.api.interna.secure;

import java.io.StringReader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.InputSource;

import es.caib.interdoc.plugins.arxiu.DocumentInfo;
import es.caib.interdoc.plugins.arxiu.InterdocArxiuPlugin;

public class TestConsultaDocumentService {

    @Test
    public void testGenerarEnidocRetornaXmlBenFormatat() throws Exception {
        String uuid = "uuid-test-123";

        InterdocArxiuPlugin pluginStub = buildPluginStub();
        String xml = invokeGenerarEnidoc(pluginStub, uuid);

        Assert.assertNotNull("La sortida XML no pot ser null", xml);
        Assert.assertFalse("La sortida XML no pot ser buida", xml.trim().isEmpty());
        Assert.assertTrue("Ha de contenir l'UUID del document", xml.contains(uuid));
        Assert.assertTrue("Ha de contenir l'arrel ENIDOC", xml.contains("enidoc:documento"));
        Assert.assertTrue("Ha de contenir el node Identificador", xml.contains("enidocmeta:Identificador"));

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);

        org.w3c.dom.Document xmlDocument = factory.newDocumentBuilder()
                .parse(new InputSource(new StringReader(xml)));

        Assert.assertEquals("documento", xmlDocument.getDocumentElement().getLocalName());
        Assert.assertEquals("http://administracionelectronica.gob.es/ENI/XSD/v1.0/documento-e",
                xmlDocument.getDocumentElement().getNamespaceURI());
    }

    private String invokeGenerarEnidoc(InterdocArxiuPlugin plugin, String uuid) throws Exception {
        ConsultaDocumentService service = new ConsultaDocumentService();
        Method method = ConsultaDocumentService.class.getDeclaredMethod("generarEnidoc", InterdocArxiuPlugin.class,
                String.class);
        method.setAccessible(true);

        try {
            return (String) method.invoke(service, plugin, uuid);
        } catch (InvocationTargetException ex) {
            Throwable cause = ex.getCause();
            if (cause instanceof Exception) {
                throw (Exception) cause;
            }
            throw ex;
        }
    }

    private InterdocArxiuPlugin buildPluginStub() {
        DocumentInfo doc = new DocumentInfo();
        Map<String, Object> metadades = new HashMap<>();
        metadades.put("Identificador", "ID-METADATA");
        doc.setMetadades(metadades);

        InvocationHandler handler = (Object proxy, Method method, Object[] args) -> {
            if ("getDocument".equals(method.getName())) {
                return doc;
            }
            return defaultValue(method.getReturnType());
        };

        return (InterdocArxiuPlugin) Proxy.newProxyInstance(
                InterdocArxiuPlugin.class.getClassLoader(),
                new Class<?>[] { InterdocArxiuPlugin.class },
                handler);
    }

    private Object defaultValue(Class<?> returnType) {
        if (returnType == boolean.class) {
            return false;
        }
        if (returnType == byte.class) {
            return (byte) 0;
        }
        if (returnType == short.class) {
            return (short) 0;
        }
        if (returnType == int.class) {
            return 0;
        }
        if (returnType == long.class) {
            return 0L;
        }
        if (returnType == float.class) {
            return 0f;
        }
        if (returnType == double.class) {
            return 0d;
        }
        if (returnType == char.class) {
            return '\0';
        }
        return null;
    }
}