package es.caib.interdoc.ejb.repository;

import es.caib.interdoc.persistence.model.Fitxer;
import es.caib.interdoc.persistence.model.Fitxer_;
import es.caib.interdoc.service.model.FitxerAtribut;
import es.caib.interdoc.service.model.FitxerDTO;
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
 * Implementació del repositori de fitxers.
 *
 * @author jagarcia
 */
@Stateless
@Local(FitxerRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class FitxerRepositoryBean extends AbstractCrudRepository<Fitxer, Long>
        implements FitxerRepository {

    protected FitxerRepositoryBean() {
        super(Fitxer.class);
    }


    @Override
    public List<FitxerDTO> findPagedByFilterAndOrder(int firstResult, int maxResult,
                                                        Map<FitxerAtribut, Object> filter,
                                                        List<Ordre<FitxerAtribut>> ordenacio) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<FitxerDTO> criteriaQuery = builder.createQuery(FitxerDTO.class);
        Root<Fitxer> root = criteriaQuery.from(Fitxer.class);

        criteriaQuery.select(builder.construct(FitxerDTO.class,
                root.get(Fitxer_.id),
                root.get(Fitxer_.nom),
                root.get(Fitxer_.descripcio),
                root.get(Fitxer_.mime),
                root.get(Fitxer_.tamany),
                root.get(Fitxer_.dataCreacio)));

        FitxerCriteriaHelper fitxerCriteriaHelper = new FitxerCriteriaHelper(builder, root);
        criteriaQuery.where(fitxerCriteriaHelper.getPredicates(filter));
        criteriaQuery.orderBy(fitxerCriteriaHelper.getOrderList(ordenacio));

        TypedQuery<FitxerDTO> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResult);
        return query.getResultList();
    }

    @Override
    public List<Fitxer> getAll() {
        TypedQuery<Fitxer> query = entityManager.createNamedQuery(Fitxer.GET_ALL, Fitxer.class);
        return query.getResultList();
    }

    @Override
    public long countByFilter(Map<FitxerAtribut, Object> filter) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<Fitxer> root = criteriaQuery.from(Fitxer.class);

        criteriaQuery.select(builder.count(root));

        FitxerCriteriaHelper fitxerCriteriaHelper = new FitxerCriteriaHelper(builder, root);
        criteriaQuery.where(fitxerCriteriaHelper.getPredicates(filter));

        TypedQuery<Long> query = entityManager.createQuery(criteriaQuery);
        return query.getSingleResult();
    }
}
