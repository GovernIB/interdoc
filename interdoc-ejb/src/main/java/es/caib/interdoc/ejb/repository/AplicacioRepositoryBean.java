package es.caib.interdoc.ejb.repository;

import es.caib.interdoc.persistence.model.Aplicacio;
import es.caib.interdoc.persistence.model.Aplicacio_;
import es.caib.interdoc.service.model.AplicacioAtribut;
import es.caib.interdoc.service.model.AplicacioDTO;
import es.caib.interdoc.service.model.Ordre;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Implementació del repositori d'Unitats Orgàniques.
 *
 * @author areus
 */
@Stateless
@Local(AplicacioRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class AplicacioRepositoryBean extends AbstractCrudRepository<Aplicacio, Long>
        implements AplicacioRepository {

    protected AplicacioRepositoryBean() {
        super(Aplicacio.class);
    }

    @Override
    public Optional<Aplicacio> findByCodiDir3(String codiDir3) {
        TypedQuery<Aplicacio> query = entityManager.createNamedQuery(
                Aplicacio.FIND_BY_CODIDIR3,
                Aplicacio.class);
        query.setParameter("codiDir3", codiDir3);
        List<Aplicacio> result = query.getResultList();
        return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
    }

    @Override
    public List<AplicacioDTO> findPagedByFilterAndOrder(int firstResult, int maxResult,
                                                        Map<AplicacioAtribut, Object> filter,
                                                        List<Ordre<AplicacioAtribut>> ordenacio) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<AplicacioDTO> criteriaQuery = builder.createQuery(AplicacioDTO.class);
        Root<Aplicacio> root = criteriaQuery.from(Aplicacio.class);

        criteriaQuery.select(builder.construct(AplicacioDTO.class,
                root.get(Aplicacio_.id),
                root.get(Aplicacio_.codiDir3),
                root.get(Aplicacio_.nom),
                root.get(Aplicacio_.usuari),
                root.get(Aplicacio_.dataCreacio),
                root.get(Aplicacio_.estat)));

        AplicacioCriteriaHelper aplicacioCriteriaHelper = new AplicacioCriteriaHelper(builder, root);
        criteriaQuery.where(aplicacioCriteriaHelper.getPredicates(filter));
        criteriaQuery.orderBy(aplicacioCriteriaHelper.getOrderList(ordenacio));

        TypedQuery<AplicacioDTO> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResult);
        return query.getResultList();
    }

    @Override
    public List<Aplicacio> getAll() {
        TypedQuery<Aplicacio> query = entityManager.createNamedQuery(Aplicacio.GET_ALL, Aplicacio.class);
        return query.getResultList();
    }

    @Override
    public long countByFilter(Map<AplicacioAtribut, Object> filter) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<Aplicacio> root = criteriaQuery.from(Aplicacio.class);

        criteriaQuery.select(builder.count(root));

        AplicacioCriteriaHelper aplicacioCriteriaHelper = new AplicacioCriteriaHelper(builder, root);
        criteriaQuery.where(aplicacioCriteriaHelper.getPredicates(filter));

        TypedQuery<Long> query = entityManager.createQuery(criteriaQuery);
        return query.getSingleResult();
    }
}
