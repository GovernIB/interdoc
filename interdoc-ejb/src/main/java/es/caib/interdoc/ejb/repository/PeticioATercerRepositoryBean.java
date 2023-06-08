package es.caib.interdoc.ejb.repository;

import es.caib.interdoc.persistence.model.PeticioATercer;
import es.caib.interdoc.persistence.model.PeticioATercer_;
import es.caib.interdoc.service.model.Ordre;
import es.caib.interdoc.service.model.PeticioATercerAtribut;
import es.caib.interdoc.service.model.PeticioATercerDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Interfície de les operacions bàsiques sobre peticions a tecers.
 *
 * @author jagarcia
 */

@Stateless
@Local(PeticioATercerRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class PeticioATercerRepositoryBean extends AbstractCrudRepository<PeticioATercer, Long> implements PeticioATercerRepository {

    protected PeticioATercerRepositoryBean() {
        super(PeticioATercer.class);
    }

    @Override
    public List<PeticioATercerDTO> getAll(Integer state) {
        TypedQuery<PeticioATercerDTO> query = entityManager.createQuery("SELECT p from PeticioATercer p", PeticioATercerDTO.class);
        List<PeticioATercerDTO> resultats = new ArrayList<PeticioATercerDTO>();
        resultats = query.getResultList();
        return resultats;
    }

    @Override
    public List<PeticioATercerDTO> findPagedByFilterAndOrder(int firstResult, int maxResult,
                                                             Map<PeticioATercerAtribut, Object> filter, List<Ordre<PeticioATercerAtribut>> ordenacio) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PeticioATercerDTO> criteriaQuery = builder.createQuery(PeticioATercerDTO.class);
        Root<PeticioATercer> root = criteriaQuery.from(PeticioATercer.class);

        criteriaQuery.select(builder.construct(PeticioATercerDTO.class,
                root.get(PeticioATercer_.id),
                root.get(PeticioATercer_.csvId),
                root.get(PeticioATercer_.eniId),
                root.get(PeticioATercer_.codiDir3),
                root.get(PeticioATercer_.esRecuperacioOriginal),
                root.get(PeticioATercer_.documentEni),
                root.get(PeticioATercer_.nif),
                root.get(PeticioATercer_.tipusIdentificacio),
                root.get(PeticioATercer_.ip),
                root.get(PeticioATercer_.estat),
                root.get(PeticioATercer_.dataCreacio)
                ));

        PeticioATercerCriteriaHelper peticioATercerCriteriaHelper = new PeticioATercerCriteriaHelper(builder, root);
        criteriaQuery.where(peticioATercerCriteriaHelper.getPredicates(filter));
        criteriaQuery.orderBy(peticioATercerCriteriaHelper.getOrderList(ordenacio));

        TypedQuery<PeticioATercerDTO> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResult);

        try{

            List<PeticioATercerDTO> resultats = query.getResultList();

            System.out.println("RESULTATS: " + resultats.size());

            return resultats;

        }catch(Exception e){
            System.out.println("======================= ERROR =============================== ");
            e.printStackTrace();
            System.out.println("==============================================================");

            return new ArrayList<PeticioATercerDTO>();
        }

    }

    @Override
    public long countByFilter(Map<PeticioATercerAtribut, Object> filter) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<PeticioATercer> root = criteriaQuery.from(PeticioATercer.class);

        criteriaQuery.select(builder.count(root));

        PeticioATercerCriteriaHelper peticioATercerCriteriaHelper = new PeticioATercerCriteriaHelper(builder, root);
        criteriaQuery.where(peticioATercerCriteriaHelper.getPredicates(filter));

        TypedQuery<Long> query = entityManager.createQuery(criteriaQuery);
        return query.getSingleResult();
    }

}
