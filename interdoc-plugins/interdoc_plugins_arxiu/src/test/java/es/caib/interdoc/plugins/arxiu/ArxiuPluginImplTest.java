package es.caib.interdoc.plugins.arxiu;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


import java.util.Properties;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import es.caib.pluginsib.arxiu.api.ContingutArxiu;
import es.caib.pluginsib.arxiu.api.IArxiuPlugin;


public class ArxiuPluginImplTest {






    
/* 
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void crearExpedientShouldReturnIdentifierWhenPluginReturnsCreatedRecord() throws Exception {
        IArxiuPlugin plugin = IArxiuPlugin.class);
        ContingutArxiu expedientCreat = mock(ContingutArxiu.class);
        when(expedientCreat.getIdentificador()).thenReturn("EXP-001");
        when(plugin.expedientCrear(any())).thenReturn(expedientCreat);

        Properties props = new Properties();
        props.setProperty("es.caib.interdoc.pluginsib.arxiu.serieDocumental", "SD-01");
        props.setProperty("es.caib.interdoc.pluginsib.arxiu.classificacio", "CL-01");

        ArxiuPluginImpl arxiuPlugin = new ArxiuPluginImpl(plugin, props);

        DocumentInfo documentInfo = new DocumentInfo();
        documentInfo.setNom("Expedient prova");

        String identificador = arxiuPlugin.crearExpedient(documentInfo);

        assertNotNull(identificador);
        assertEquals("EXP-001", identificador);
        verify(plugin, times(1)).expedientCrear(any());
    }

    @Test
    public void crearExpedientShouldThrowWhenDocumentInfoIsNull() throws Exception {
        IArxiuPlugin plugin = mock(IArxiuPlugin.class);
        ArxiuPluginImpl arxiuPlugin = new ArxiuPluginImpl(plugin, new Properties());

        expectedException.expect(DocumentNotValidException.class);

        arxiuPlugin.crearExpedient(null);
    }
        */
}
