package es.caib.interdoc.ejb.repository;

import es.caib.interdoc.commons.utils.Configuracio;
import es.caib.interdoc.commons.utils.Constants;
import es.caib.interdoc.ejb.utils.DataBaseUtils;
import es.caib.interdoc.persistence.model.Usuari;
import es.caib.interdoc.service.model.RolDTO;
import es.caib.interdoc.service.model.UsuariDTO;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * Implementació del repositori d'Unitats Orgàniques.
 *
 * @author areus
 */
@Stateless
@Local(UsuariRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class UsuariRepositoryBean extends AbstractCrudRepository<Usuari, Long>
        implements UsuariRepository {

    protected UsuariRepositoryBean() {
        super(Usuari.class);
    }


    @Override
    public List<UsuariDTO> findPagedByFilterAndOrder(int firstResult, int maxResult) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UsuariDTO> criteriaQuery = builder.createQuery(UsuariDTO.class);
        Root<Usuari> root = criteriaQuery.from(Usuari.class);

        criteriaQuery.select(builder.construct(UsuariDTO.class,
                root.get("id"),
                root.get("nom")));


        TypedQuery<UsuariDTO> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResult);
        return query.getResultList();
    }

    @Override
    public List<UsuariDTO> getAll() {
        TypedQuery<UsuariDTO> query = entityManager.createNamedQuery(Usuari.GET_ALL, UsuariDTO.class);
        List<UsuariDTO> resultats = query.getResultList();
        System.out.println("========== INICI GET ALL ==========");
        for (UsuariDTO a : resultats) {
            System.out.println(a.toString());
        }
        System.out.println("=========== FI GET ALL =========");
        return resultats;
    }

    @Override
    public Optional<UsuariDTO> findByIdentificador(String identificador) {

        TypedQuery<UsuariDTO> query = entityManager.createQuery("Select usuari from Usuari as usuari where usuari.identificador = :identificador", UsuariDTO.class);
        query.setParameter("identificador", identificador);

        List<UsuariDTO> usuari = query.getResultList();
        return (usuari.size() == 1 ) ? Optional.of(usuari.get(0)) :  Optional.empty();
    }

    @Override
    public Optional<UsuariDTO> findByDocument(String document) {

        TypedQuery<UsuariDTO> query = entityManager.createQuery("Select usuari from Usuari as usuari where usuari.document = :document", UsuariDTO.class);
        query.setParameter("document", document);
        List<UsuariDTO> usuari = query.getResultList();
        return (usuari.size() == 1) ? Optional.of(usuari.get(0)) : Optional.empty();
    }

    @Override
    public List<UsuariDTO> busqueda(Integer pageNumber, String identificador, String nombre, String apellido1, String apellido2, String documento, Long tipoUsuario) {

        Query q;
        Map<String, Object> parametros = new HashMap<String, Object>();
        List<String> where = new ArrayList<String>();

        StringBuilder query = new StringBuilder("Select usuari from Usuari as usuari ");

        if (identificador != null && identificador.length() > 0) {
            where.add(DataBaseUtils.like("usuari.identificador", "identificador", parametros, identificador));
        }
        if (nombre != null && nombre.length() > 0) {
            where.add(DataBaseUtils.like("usuari.nom", "nom", parametros, nombre));
        }
        if (apellido1 != null && apellido1.length() > 0) {
            where.add(DataBaseUtils.like("usuari.llinatge1", "llinatge1", parametros, apellido1));
        }
        if (apellido2 != null && apellido2.length() > 0) {
            where.add(DataBaseUtils.like("usuari.llinatge2", "llinatge2", parametros, apellido2));
        }
        if (documento != null && documento.length() > 0) {
            where.add(" upper(usuari.document) like upper(:document) ");
            parametros.put("document", "%" + documento.toLowerCase() + "%");
        }

        if (parametros.size() != 0) {
            query.append("where ");
            int count = 0;
            for (String w : where) {
                if (count != 0) {
                    query.append(" and ");
                }
                query.append(w);
                count++;
            }

            q = entityManager.createQuery(query.toString());

            for (Map.Entry<String, Object> param : parametros.entrySet()) {
                q.setParameter(param.getKey(), param.getValue());
            }

        } else {
            query.append("order by usuari.nom, usuari.llinatge1");
            q = entityManager.createQuery(query.toString());
        }
        return q.getResultList();
    }

    @Override
    public Integer asociarIdioma() {

        Query q = entityManager.createQuery("Update Usuari set idioma = :idioma where idioma is null");
        q.setParameter("idioma", Constants.IDIOMA_ID_BY_CODIGO.get(Configuracio.getDefaultLanguage()));
        return q.executeUpdate();

    }

    @Override
    public void actualizarRoles(Usuari usuario, List<RolDTO> rolesUsuario) {
        usuario.setItd_usuari(rolesUsuario.contains(new RolDTO(Constants.ITD_USER)));
        usuario.setItd_admin(rolesUsuario.contains(new RolDTO(Constants.ITD_ADMIN)));
        update(usuario);
    }

    @Override
    public List<RolDTO> getByRol(List<String> roles) throws Exception {

        TypedQuery q = entityManager.createQuery("Select rol from Rol as rol where rol.nom IN (:roles)", RolDTO.class);
        q.setParameter("roles", roles);
        q.setHint("org.hibernate.readOnly", true);
        return q.getResultList();

    }
}
