package es.caib.interdoc.ejb.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import es.caib.interdoc.persistence.model.Referencia;
import es.caib.interdoc.persistence.model.Referencia_;
import es.caib.interdoc.service.model.Ordre;
import es.caib.interdoc.service.model.ReferenciaAtribut;
import es.caib.interdoc.service.model.ReferenciaDTO;

/**
 * Implementació del repositori d'Unitats Orgàniques.
 *
 * @author areus
 */
@Stateless
@Local(ReferenciaRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class ReferenciaRepositoryBean extends AbstractCrudRepository<Referencia, Long> implements ReferenciaRepository {

	protected ReferenciaRepositoryBean() {
		super(Referencia.class);
	}

	@Override
	public List<ReferenciaDTO> findPagedByFilterAndOrder(int firstResult, int maxResult,
			Map<ReferenciaAtribut, Object> filter, List<Ordre<ReferenciaAtribut>> ordenacio) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ReferenciaDTO> criteriaQuery = builder.createQuery(ReferenciaDTO.class);
		Root<Referencia> root = criteriaQuery.from(Referencia.class);

		criteriaQuery.select(builder.construct(ReferenciaDTO.class, root.get(Referencia_.id),
				root.get(Referencia_.csvId), root.get(Referencia_.uuId), root.get(Referencia_.direccio),
				root.get(Referencia_.emisor), root.get(Referencia_.receptor), root.get(Referencia_.formatFirma),
				root.get(Referencia_.dataCreacio), root.get(Referencia_.infoSignaturaId),
				root.get(Referencia_.infoArxiuId), root.get(Referencia_.fitxerId), root.get(Referencia_.referencia),
				root.get(Referencia_.entitatId), root.get(Referencia_.numeroRegistre)));

		ReferenciaCriteriaHelper referenciaCriteriaHelper = new ReferenciaCriteriaHelper(builder, root);
		criteriaQuery.where(referenciaCriteriaHelper.getPredicates(filter));
		criteriaQuery.orderBy(referenciaCriteriaHelper.getOrderList(ordenacio));

		TypedQuery<ReferenciaDTO> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResult);
		return query.getResultList();
	}

	@Override
	public List<ReferenciaDTO> findByReferencia(String referencia) {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ReferenciaDTO> criteriaQuery = builder.createQuery(ReferenciaDTO.class);
		Root<Referencia> root = criteriaQuery.from(Referencia.class);

		criteriaQuery.select(builder.construct(ReferenciaDTO.class, root.get(Referencia_.id),
				root.get(Referencia_.csvId), root.get(Referencia_.uuId), root.get(Referencia_.direccio),
				root.get(Referencia_.emisor), root.get(Referencia_.receptor), root.get(Referencia_.formatFirma),
				root.get(Referencia_.dataCreacio), root.get(Referencia_.infoSignaturaId),
				root.get(Referencia_.infoArxiuId), root.get(Referencia_.fitxerId), root.get(Referencia_.referencia),
				root.get(Referencia_.entitatId)));

		ReferenciaCriteriaHelper referenciaCriteriaHelper = new ReferenciaCriteriaHelper(builder, root);
		criteriaQuery.where(referenciaCriteriaHelper.getPredicate(ReferenciaAtribut.referencia, referencia));

		TypedQuery<ReferenciaDTO> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}

	@Override
	public List<ReferenciaDTO> findByUUID(String uuid) {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ReferenciaDTO> criteriaQuery = builder.createQuery(ReferenciaDTO.class);
		Root<Referencia> root = criteriaQuery.from(Referencia.class);

		criteriaQuery.select(builder.construct(ReferenciaDTO.class, root.get(Referencia_.id),
				root.get(Referencia_.csvId), root.get(Referencia_.uuId), root.get(Referencia_.direccio),
				root.get(Referencia_.emisor), root.get(Referencia_.receptor), root.get(Referencia_.formatFirma),
				root.get(Referencia_.dataCreacio), root.get(Referencia_.infoSignaturaId),
				root.get(Referencia_.infoArxiuId), root.get(Referencia_.fitxerId), root.get(Referencia_.referencia),
				root.get(Referencia_.entitatId)));

		ReferenciaCriteriaHelper referenciaCriteriaHelper = new ReferenciaCriteriaHelper(builder, root);
		criteriaQuery.where(referenciaCriteriaHelper.getPredicate(ReferenciaAtribut.uuId, uuid));

		TypedQuery<ReferenciaDTO> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}

	@Override
	public List<ReferenciaDTO> findByCSV(String csvId) {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ReferenciaDTO> criteriaQuery = builder.createQuery(ReferenciaDTO.class);
		Root<Referencia> root = criteriaQuery.from(Referencia.class);

		criteriaQuery.select(builder.construct(ReferenciaDTO.class, root.get(Referencia_.id),
				root.get(Referencia_.csvId), root.get(Referencia_.uuId), root.get(Referencia_.direccio),
				root.get(Referencia_.emisor), root.get(Referencia_.receptor), root.get(Referencia_.formatFirma),
				root.get(Referencia_.dataCreacio), root.get(Referencia_.infoSignaturaId),
				root.get(Referencia_.infoArxiuId), root.get(Referencia_.fitxerId), root.get(Referencia_.referencia),
				root.get(Referencia_.entitatId)));

		ReferenciaCriteriaHelper referenciaCriteriaHelper = new ReferenciaCriteriaHelper(builder, root);
		criteriaQuery.where(referenciaCriteriaHelper.getPredicate(ReferenciaAtribut.csvId, csvId));

		TypedQuery<ReferenciaDTO> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}

	@Override
	public long countByFilter(Map<ReferenciaAtribut, Object> filter) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Referencia> root = criteriaQuery.from(Referencia.class);

		criteriaQuery.select(builder.count(root));

		ReferenciaCriteriaHelper referenciaCriteriaHelper = new ReferenciaCriteriaHelper(builder, root);
		criteriaQuery.where(referenciaCriteriaHelper.getPredicates(filter));

		TypedQuery<Long> query = entityManager.createQuery(criteriaQuery);
		return query.getSingleResult();
	}

	@Override
	public List<Referencia> findBetweenDates(LocalDate inici, LocalDate fi) {

		TypedQuery<Referencia> query = entityManager.createNamedQuery(Referencia.FILTER_BETWEEN_DATES,
				Referencia.class);
		// Convertir LocalDate a LocalDateTime per la consulta
		query.setParameter("inici", inici.atStartOfDay());
		query.setParameter("fi", fi.plusDays(1).atStartOfDay());

		return query.getResultList();
	}

	@Override
	public String findExpedientByNumeroRegistre(String numeroRegistre, Long entitatId) {

		StringBuilder query = new StringBuilder(
				"Select distinct i.arxiuExpedientId from Referencia as r left outer join r.infoArxiu as i where r.numeroRegistre = :numeroRegistre and r.entitatId = :entitatId");

		Query q = entityManager.createQuery(query.toString());
		q.setParameter("numeroRegistre", numeroRegistre);
		q.setParameter("entitatId", entitatId);

		List<String> resultats = q.getResultList();
		
		return (resultats.size() > 0) ? resultats.get(0) : null;
	}
}
