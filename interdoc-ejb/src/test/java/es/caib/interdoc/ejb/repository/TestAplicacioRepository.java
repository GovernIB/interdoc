package es.caib.interdoc.ejb.repository;

import es.caib.interdoc.persistence.model.Aplicacio;

import es.caib.interdoc.service.model.AplicacioDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class TestAplicacioRepository {

    public AplicacioRepository repository;

    @Mock
    public EntityManager entityManager;

    @Before
    public void setup() {
        AplicacioRepositoryBean repositoryBean = new AplicacioRepositoryBean();
        repositoryBean.entityManager = entityManager;
        repository = repositoryBean;
    }

    @Test
    public void testfindByCodiDir3Present() {
        Aplicacio aplicacio = new Aplicacio();
        aplicacio.setCodiDir3("A012345678");

        @SuppressWarnings("unchecked")
        TypedQuery<Aplicacio> mockedQuery = Mockito.mock(TypedQuery.class);
        Mockito.when(mockedQuery.getResultList()).thenReturn(List.of(aplicacio));
        Mockito.when(entityManager.createNamedQuery(Aplicacio.FIND_BY_CODIDIR3, Aplicacio.class))
                .thenReturn(mockedQuery);

        Optional<Aplicacio> result = repository.findByCodiDir3("A012345678");

        Mockito.verify(mockedQuery).setParameter("codiDir3", "A012345678");
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(aplicacio, result.get());


        result = repository.findByCodiDir3("A04019898");
        Mockito.verify(mockedQuery).setParameter("codiDir3", "A04019898");
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(aplicacio, result.get());

    }


    @Test
    public void testfindByCodiDir3NotPresent() {

        @SuppressWarnings("unchecked")
        TypedQuery<Aplicacio> mockedQuery = Mockito.mock(TypedQuery.class);
        Mockito.when(mockedQuery.getResultList()).thenReturn(Collections.emptyList());
        Mockito.when(entityManager.createNamedQuery(Aplicacio.FIND_BY_CODIDIR3, Aplicacio.class))
                .thenReturn(mockedQuery);

        Optional<Aplicacio> result = repository.findByCodiDir3("A012345678");

        Mockito.verify(mockedQuery).setParameter("codiDir3", "A012345678");
        Assert.assertTrue(result.isEmpty());
    }


    @Test
    public void getTotalItems() {

        if (repository == null) {
            AplicacioRepositoryBean repositoryBean = new AplicacioRepositoryBean();
            repositoryBean.entityManager = entityManager;
            repository = repositoryBean;
        }

        List<Aplicacio> resultats = repository.getAll();

        for (Aplicacio aplicacio : resultats) {
            System.out.println(aplicacio.getNom());
        }

        Assert.assertTrue(resultats.size() > 0);

    }

}
