package es.caib.interdoc.ejb.facade;

import es.caib.interdoc.service.exception.AplicacioDuplicadaException;
import es.caib.interdoc.service.exception.RecursNoTrobatException;
import es.caib.interdoc.service.facade.AplicacioServiceFacade;
import es.caib.interdoc.service.model.AplicacioDTO;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import javax.ejb.EJBAccessException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Realitza tests de persistència i validació damunt les aplicacions.
 * <p>
 * Els tests s'executen sobre una instància de JBoss que o bé s'arranca automàticament (-Parq-jboss-managed), o bé
 * ja està en marxa (-Parq-jboss-remote).
 */
@RunWith(Arquillian.class)
public class AplicacioServiceFacadeBeanIT extends AbstractFacadeIT {
    ;

    @EJB
    private AplicacioServiceFacade aplicacioServiceFacade;

    /**
     * Crea una aplicacio amb codiDir3 "U87654321".
     */
    @Test
    @InSequence(1)
    public void testCreateUnitat() {
        AplicacioDTO dto = new AplicacioDTO();

        dto.setNom("Unitat test arquillian");
        dto.setUsuari("U87654321");
        dto.setClau("clave");
        dto.setCodiDir3("U87654321");
        dto.setDataCreacio(LocalDate.of(2020, 10, 19));

        adminManager.exec(() -> {
            Long result = aplicacioServiceFacade.create(dto);
            Assert.assertNotNull(result);
        });
    }

    /**
     * Crea una unitat orgància amb codiDir3 "U87654321".
     */
    @Test(expected = AplicacioDuplicadaException.class)
    @InSequence(2)
    public void testCreateUnitatDuplicat() {
        AplicacioDTO dto = new AplicacioDTO();
        dto.setNom("Unitat test arquillian duplicada");
        dto.setUsuari("U87654321");
        dto.setClau("clave2");
        dto.setCodiDir3("U87654321");
        dto.setDataCreacio(LocalDate.of(2020, 10, 19));

        adminManager.exec(() -> {
            aplicacioServiceFacade.create(dto);
            Assert.fail("No s'hauria d'haver pogut crear");
        });
    }

    /**
     * Selecciona la unitat orgànica
     */
    @Test
    @InSequence(3)
    public void testFindById() {

        userManager.exec(() -> {
            Optional<AplicacioDTO> dto = aplicacioServiceFacade.findById(101L);

            Assert.assertTrue(dto.isPresent());
            Assert.assertEquals("Unitat test arquillian", dto.get().getNom());
            Assert.assertEquals("U87654321", dto.get().getCodiDir3());
        });
    }

    /**
     * Selecciona la unitat orgànica inexistent
     */
    @Test
    @InSequence(4)
    public void testFindByIdError() {

        userManager.exec(() -> {
            Optional<AplicacioDTO> dto = aplicacioServiceFacade.findById(999L);
            Assert.assertTrue(dto.isEmpty());
        });
    }

    /**
     * Actualitza la unitat orgànica
     */
    @Test
    @InSequence(5)
    public void testUpdateUnitat() {
        var dto = new AplicacioDTO();
        dto.setId(101L);
        dto.setCodiDir3("A87654321");
        dto.setNom("Unitat test arquillian 2");
        dto.setUsuari("A87654321");
        dto.setClau("clave");
        dto.setDataCreacio(LocalDate.of(2020, 10, 20));

        adminManager.exec(() -> {
            aplicacioServiceFacade.update(dto);

            var updated = aplicacioServiceFacade.findById(101L).orElseThrow();
            Assert.assertEquals(dto.getId(), updated.getId());
            Assert.assertEquals(dto.getCodiDir3(), updated.getCodiDir3());
            Assert.assertEquals(dto.getNom(), updated.getNom());
            Assert.assertEquals(dto.getUsuari(), updated.getUsuari());
            Assert.assertEquals(dto.getClau(), updated.getClau());
            Assert.assertEquals(dto.getDataCreacio(), updated.getDataCreacio());

        });
    }

    /**
     * Actualitza la unitat orgànica inexistent
     */
    @Test(expected = RecursNoTrobatException.class)
    @InSequence(6)
    public void testUpdateUnitatError() {
        AplicacioDTO dto = new AplicacioDTO();
        dto.setId(999L);
        dto.setCodiDir3("J87654321");
        dto.setNom("Unitat test arquillian 2 error");
        dto.setDataCreacio(LocalDate.of(2020, 10, 20));

        adminManager.exec(() -> {
            aplicacioServiceFacade.update(dto);
            Assert.fail("No s'hauria de poder haver actualitzat sense error");
        });
    }

    /**
     * Esborra la unitat
     */
    @Test
    @InSequence(7)
    public void testDelete() {
        adminManager.exec(() -> aplicacioServiceFacade.delete(101L));
    }

    /**
     * Esborra una unitat que no existeix
     */
    @Test(expected = RecursNoTrobatException.class)
    @InSequence(8)
    public void testDeleteError() {
        adminManager.exec(() -> {
            aplicacioServiceFacade.delete(999L);
            Assert.fail("No s'hauria de poder haver borrat sense error");
        });
    }

    /**
     * Llistat
     */
    @Test
    @InSequence(9)
    public void testLlistat() {
        List<AplicacioDTO> llistat = aplicacioServiceFacade.getAll();
        Assert.assertEquals(10, llistat.size());
        Assert.assertEquals("A00000011", llistat.get(0).getCodiDir3());
    }

    @Test(expected = EJBAccessException.class)
    @InSequence(10)
    public void testCreateSensePermisos() {
        AplicacioDTO dto = new AplicacioDTO();
        userManager.exec(() -> {
            aplicacioServiceFacade.create(dto);
            Assert.fail("No hauria de poder crear");
        });
    }

    @Test(expected = EJBAccessException.class)
    @InSequence(12)
    public void testUpdateSensePermisos() {
        AplicacioDTO dto = new AplicacioDTO();
        userManager.exec(() -> {
            aplicacioServiceFacade.update(dto);
            Assert.fail("No hauria de poder actualitzar");
        });
    }

    @Test(expected = EJBAccessException.class)
    @InSequence(13)
    public void testDeleteSensePermisos() {
        userManager.exec(() -> {
            aplicacioServiceFacade.delete(1L);
            Assert.fail("No hauria de poder esborrar");
        });
    }
}
