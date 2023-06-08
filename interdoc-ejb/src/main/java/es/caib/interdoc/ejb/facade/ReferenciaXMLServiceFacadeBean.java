package es.caib.interdoc.ejb.facade;

import es.caib.interdoc.commons.utils.Constants;
import es.caib.interdoc.ejb.converter.ReferenciaXMLConverter;
import es.caib.interdoc.ejb.interceptor.ExceptionTranslate;
import es.caib.interdoc.ejb.interceptor.Logged;
import es.caib.interdoc.ejb.repository.ReferenciaXMLRepository;
import es.caib.interdoc.persistence.model.ReferenciaXML;
import es.caib.interdoc.service.exception.RecursNoTrobatException;
import es.caib.interdoc.service.facade.ReferenciaXMLServiceFacade;
import es.caib.interdoc.service.model.ReferenciaXMLAtribut;
import es.caib.interdoc.service.model.ReferenciaXMLDTO;
import es.caib.interdoc.service.model.Ordre;
import es.caib.interdoc.service.model.Pagina;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Implementació dels casos d'ús de manteniment de aplicacions.
 * És responsabilitat d'aquesta capa definir el limit de les transaccions i la seguretat.
 * Les excepcions específiques es llancen mitjançant l'{@link ExceptionTranslate} que transforma
 * els errors JPA amb les excepcions de servei com la {@link RecursNoTrobatException}
 *
 * @author jagarcia
 */
@Logged
@ExceptionTranslate
@Stateless
@Local(ReferenciaXMLServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ReferenciaXMLServiceFacadeBean implements ReferenciaXMLServiceFacade {

    @Inject
    private ReferenciaXMLRepository repository;

    @Inject
    private ReferenciaXMLConverter converter;

    @Override
    @PermitAll
    public Long create(ReferenciaXMLDTO dto) {
        ReferenciaXML referencia = converter.toEntity(dto);
        repository.create(referencia);
        return referencia.getId();
    }

    @Override
    @RolesAllowed(Constants.ITD_ADMIN)
    public void update(ReferenciaXMLDTO dto) throws RecursNoTrobatException {
        ReferenciaXML referenciaXML = repository.getReference(dto.getId());
        converter.updateFromDTO(referenciaXML, dto);
    }

    @Override
    @RolesAllowed(Constants.ITD_ADMIN)
    public void delete(Long id) throws RecursNoTrobatException {
        ReferenciaXML referenciaXML = repository.getReference(id);
        repository.delete(referenciaXML);
    }

    @Override
    @RolesAllowed({Constants.ITD_USER, Constants.ITD_ADMIN})
    public Optional<ReferenciaXMLDTO> findById(Long id) {
        ReferenciaXML referenciaXML = repository.findById(id);
        ReferenciaXMLDTO referenciaXMLDTO = converter.toDTO(referenciaXML);
        return Optional.ofNullable(referenciaXMLDTO);
    }

    @Override
    @PermitAll
    public Pagina<ReferenciaXMLDTO> findFiltered(int firstResult, int maxResult, Map<ReferenciaXMLAtribut, Object> filter,
                                             List<Ordre<ReferenciaXMLAtribut>> ordenacio) {

        List<ReferenciaXMLDTO> items = repository.findPagedByFilterAndOrder(firstResult, maxResult, filter, ordenacio);
        long total = repository.countByFilter(filter);

        return new Pagina<>(items, total);
    }
}
