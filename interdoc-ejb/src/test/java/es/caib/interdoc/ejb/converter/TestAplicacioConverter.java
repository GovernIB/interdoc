package es.caib.interdoc.ejb.converter;

import es.caib.interdoc.persistence.model.Aplicacio;
import es.caib.interdoc.persistence.model.Procediment;
import es.caib.interdoc.persistence.model.UnitatOrganica;
import es.caib.interdoc.service.model.AplicacioDTO;
import es.caib.interdoc.service.model.ProcedimentDTO;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;

public class TestAplicacioConverter {

    private final AplicacioConverter converter = new AplicacioConverterImpl();

    @Test
    public void testToDTO() {

        Aplicacio entity = new Aplicacio();
        entity.setId(1L);
        entity.setNom("Entidad A04026929");
        entity.setUsuari("A04026929");
        entity.setClau("clave");
        entity.setCodiDir3("A04026929");
        entity.setDataCreacio(LocalDate.now());

        AplicacioDTO dto = converter.toDTO(entity);

        Assert.assertEquals(1L, (long) dto.getId());
        Assert.assertEquals("Entidad A04026929", dto.getNom());
        Assert.assertEquals("A04026929", dto.getUsuari());
        Assert.assertEquals("clave", dto.getClau());
        Assert.assertEquals("A04026929", dto.getCodiDir3());
    }

    @Test
    public void testToEntity() {
        AplicacioDTO dto = new AplicacioDTO();
        dto.setId(1L);
        dto.setNom("Entidad A04026929");
        dto.setUsuari("A04026929");
        dto.setClau("clave");
        dto.setCodiDir3("A04026929");

        Aplicacio entity = converter.toEntity(dto);

        Assert.assertEquals(1L, (long) entity.getId());
        Assert.assertEquals("Entidad A04026929", entity.getNom());
        Assert.assertEquals("A04026929", entity.getUsuari());
        Assert.assertEquals("clave", entity.getClau());
        Assert.assertEquals("A04026929", entity.getCodiDir3());
    }


    @Test
    public void testUpdateFromDTO() {
        Aplicacio entity = new Aplicacio();
        entity.setId(1L);
        entity.setNom("Entidad A04026929");
        entity.setUsuari("A04026929");
        entity.setClau("clave");
        entity.setCodiDir3("A04026929");
        entity.setDataCreacio(LocalDate.now());

        AplicacioDTO dto = new AplicacioDTO();
        dto.setId(2L);
        dto.setNom("Entidad codigo");
        dto.setUsuari("A04026930");
        dto.setClau("clave2");
        dto.setCodiDir3("A04026930");

        converter.updateFromDTO(entity, dto);

        Assert.assertEquals(2L, (long) entity.getId());
        Assert.assertEquals("Entidad codigo", entity.getNom());
        Assert.assertEquals("A04026930", entity.getUsuari());
        Assert.assertEquals("clave2", entity.getClau());
        Assert.assertEquals("A04026930", entity.getCodiDir3());
    }
}
