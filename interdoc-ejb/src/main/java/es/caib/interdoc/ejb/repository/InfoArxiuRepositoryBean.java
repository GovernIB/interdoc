package es.caib.interdoc.ejb.repository;

import es.caib.interdoc.commons.utils.Utils;
import es.caib.interdoc.persistence.model.InfoArxiu;
import es.caib.interdoc.persistence.model.InfoArxiu_;
import es.caib.interdoc.service.model.InfoArxiuAtribut;
import es.caib.interdoc.service.model.InfoArxiuDTO;
import es.caib.interdoc.service.model.Ordre;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Implementació del repositori d'Unitats Orgàniques.
 *
 * @author areus
 */
@Stateless
@Local(InfoArxiuRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class InfoArxiuRepositoryBean extends AbstractCrudRepository<InfoArxiu, Long> implements InfoArxiuRepository {

	protected InfoArxiuRepositoryBean() {
		super(InfoArxiu.class);
	}

	@Override
	public List<InfoArxiuDTO> findPagedByFilterAndOrder(int firstResult, int maxResult,
			Map<InfoArxiuAtribut, Object> filter, List<Ordre<InfoArxiuAtribut>> ordenacio) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<InfoArxiuDTO> criteriaQuery = builder.createQuery(InfoArxiuDTO.class);
		Root<InfoArxiu> root = criteriaQuery.from(InfoArxiu.class);

		criteriaQuery.select(builder.construct(InfoArxiuDTO.class, root.get(InfoArxiu_.id),
				root.get(InfoArxiu_.originalFileUrl), root.get(InfoArxiu_.csv),
				root.get(InfoArxiu_.csvGenerationDefinition), root.get(InfoArxiu_.csvValidationWeb),
				root.get(InfoArxiu_.arxiuExpedientId), root.get(InfoArxiu_.arxiuDocumentId),
				root.get(InfoArxiu_.printableUrl), root.get(InfoArxiu_.eniFileUrl),
				root.get(InfoArxiu_.validationFileUrl), root.get(InfoArxiu_.estatExpedient)));

		InfoArxiuCriteriaHelper infoArxiuCriteriaHelper = new InfoArxiuCriteriaHelper(builder, root);
		criteriaQuery.where(infoArxiuCriteriaHelper.getPredicates(filter));
		criteriaQuery.orderBy(infoArxiuCriteriaHelper.getOrderList(ordenacio));

		TypedQuery<InfoArxiuDTO> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResult);
		return query.getResultList();
	}

	@Override
	public List<InfoArxiu> getAll() {
		TypedQuery<InfoArxiu> query = entityManager.createNamedQuery(InfoArxiu.GET_ALL, InfoArxiu.class);
		return query.getResultList();
	}

	@Override
	public List<InfoArxiuDTO> getExpedientsOberts(String estat) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<InfoArxiuDTO> criteriaQuery = builder.createQuery(InfoArxiuDTO.class);
		Root<InfoArxiu> root = criteriaQuery.from(InfoArxiu.class);

		criteriaQuery.select(builder.construct(InfoArxiuDTO.class, root.get(InfoArxiu_.id),
				root.get(InfoArxiu_.originalFileUrl), root.get(InfoArxiu_.csv),
				root.get(InfoArxiu_.csvGenerationDefinition), root.get(InfoArxiu_.csvValidationWeb),
				root.get(InfoArxiu_.arxiuExpedientId), root.get(InfoArxiu_.arxiuDocumentId),
				root.get(InfoArxiu_.printableUrl), root.get(InfoArxiu_.eniFileUrl),
				root.get(InfoArxiu_.validationFileUrl), root.get(InfoArxiu_.estatExpedient)));

		InfoArxiuCriteriaHelper infoArxiuCriteriaHelper = new InfoArxiuCriteriaHelper(builder, root);
		criteriaQuery.where(infoArxiuCriteriaHelper.getPredicate(InfoArxiuAtribut.estatExpedient, estat));

		TypedQuery<InfoArxiuDTO> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}

	@Override
	public HashMap<Long, List<String>> getExpedientsObertsPerEntitat(String estat, Long entitatId) {

		StringBuilder query = new StringBuilder(
				"Select distinct r.entitatId, i.arxiuExpedientId from Referencia as r left outer join r.infoArxiu as i where i.estatExpedient = :estat and i.reintents < 10");

		if (entitatId > 0L)
			query.append(" and r.entitatId = :entitatId");

		Query q = entityManager.createQuery(query.toString());
		q.setParameter("estat", (Utils.isEmpty(estat)) ? InfoArxiuDTO.EXPEDIENT_OBERT : estat);

		if (entitatId > 0L)
			q.setParameter("entitatId", entitatId);

		// recoger los resultados de q e imprimirlos por pantalla
		List<Object[]> results = q.getResultList();
		
		HashMap<Long, List<String>> expedientsPerEntitat = new HashMap<Long, List<String>>();
		for (Object[] result : results) {
			Long entitat = (Long) result[0];
			String expedientId = (String) result[1];
			if (expedientsPerEntitat.containsKey(entitat)) {
				List<String> expedients = expedientsPerEntitat.get(entitat);
				expedients.add(expedientId);
			} else {
				List<String> expedients = new ArrayList<String>();
				expedients.add(expedientId);
				expedientsPerEntitat.put(entitat, expedients);
			}
		}

		return expedientsPerEntitat;
	}
	
	
	@Override
	public List<String> getExpedientsObertsPerEntitat(Long entitatId) {
		
		StringBuilder query = new StringBuilder(
				"Select distinct r.entitatId, i.arxiuExpedientId from Referencia as r left outer join r.infoArxiu as i where i.estatExpedient = :estat and i.reintents < 10");
		
		if (entitatId > 0L)
			query.append(" and r.entitatId = :entitatId");

		Query q = entityManager.createQuery(query.toString());
		q.setParameter("estat", InfoArxiuDTO.EXPEDIENT_OBERT);

		if (entitatId > 0L)
			q.setParameter("entitatId", entitatId);

		List<Object[]> resultats = q.getResultList();
		
		List<String> expedientsPendentsTancar = new ArrayList<String>(resultats.size());
		for (Object[] result : resultats) {
			expedientsPendentsTancar.add((String) result[1]);
		}
		
		return expedientsPendentsTancar;
		
	}
	

	@Override
	public int aumentarReintents(String expedientId, Long entitatId, Long valor) {

		StringBuilder query = new StringBuilder(
				"UPDATE InfoArxiu i SET i.reintents = :valor WHERE i.id IN (Select distinct r.infoArxiuId from Referencia as r left outer join r.infoArxiu as n where r.entitatId = :entitatId and n.arxiuExpedientId = :expedientId)");

		Query q = entityManager.createQuery(query.toString());

		q.setParameter("expedientId", expedientId);
		q.setParameter("entitatId", entitatId);
		q.setParameter("valor", valor.intValue());

		return q.executeUpdate();
	}

	
	@Override
	public Boolean tancarExpedient(String expedientId, Long entitatId) {

		StringBuilder query = new StringBuilder(
				"UPDATE InfoArxiu i SET i.estatExpedient = :estat WHERE i.id IN (Select distinct r.infoArxiuId from Referencia as r left outer join r.infoArxiu as n where r.entitatId = :entitatId and n.arxiuExpedientId = :expedientId)");

		Query q = entityManager.createQuery(query.toString());
		q.setParameter("estat", InfoArxiuDTO.EXPEDIENT_TANCAT);
		q.setParameter("expedientId", expedientId);
		q.setParameter("entitatId", entitatId);
		
		return q.executeUpdate() > 0;

	}

	@Override
	public long countByFilter(Map<InfoArxiuAtribut, Object> filter) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<InfoArxiu> root = criteriaQuery.from(InfoArxiu.class);

		criteriaQuery.select(builder.count(root));

		InfoArxiuCriteriaHelper infoArxiuCriteriaHelper = new InfoArxiuCriteriaHelper(builder, root);
		criteriaQuery.where(infoArxiuCriteriaHelper.getPredicates(filter));

		TypedQuery<Long> query = entityManager.createQuery(criteriaQuery);
		return query.getSingleResult();
	}
}
