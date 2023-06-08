package es.caib.interdoc.ejb.repository;

import es.caib.interdoc.persistence.model.Metadada;
import es.caib.interdoc.persistence.model.Metadada_;
import es.caib.interdoc.service.model.MetadadaAtribut;
import es.caib.interdoc.service.model.MetadadaDTO;
import es.caib.interdoc.service.model.Ordre;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

/**
 * Implementació del repositori d'Unitats Orgàniques.
 *
 * @author areus
 */
@Stateless
@Local(MetadadaRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class MetadadaRepositoryBean extends AbstractCrudRepository<Metadada, Long>
        implements MetadadaRepository {

    protected MetadadaRepositoryBean() {
        super(Metadada.class);
    }


    @Override
    public List<MetadadaDTO> findPagedByFilterAndOrder(int firstResult, int maxResult,
                                                        Map<MetadadaAtribut, Object> filter,
                                                        List<Ordre<MetadadaAtribut>> ordenacio) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<MetadadaDTO> criteriaQuery = builder.createQuery(MetadadaDTO.class);
        Root<Metadada> root = criteriaQuery.from(Metadada.class);

        criteriaQuery.select(builder.construct(MetadadaDTO.class,
                root.get(Metadada_.id),
                root.get(Metadada_.nom),
                root.get(Metadada_.valor),
                root.get(Metadada_.referenciaId),
                root.get(Metadada_.dataCreacio)));

        MetadadaCriteriaHelper metadadaCriteriaHelper = new MetadadaCriteriaHelper(builder, root);
        criteriaQuery.where(metadadaCriteriaHelper.getPredicates(filter));
        criteriaQuery.orderBy(metadadaCriteriaHelper.getOrderList(ordenacio));

        TypedQuery<MetadadaDTO> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResult);
        return query.getResultList();
    }

    @Override
    public long countByFilter(Map<MetadadaAtribut, Object> filter) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<Metadada> root = criteriaQuery.from(Metadada.class);

        criteriaQuery.select(builder.count(root));

        MetadadaCriteriaHelper metadadaCriteriaHelper = new MetadadaCriteriaHelper(builder, root);
        criteriaQuery.where(metadadaCriteriaHelper.getPredicates(filter));

        TypedQuery<Long> query = entityManager.createQuery(criteriaQuery);
        return query.getSingleResult();
    }
}
