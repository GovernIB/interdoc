package es.caib.interdoc.ejb.facade;

import es.caib.interdoc.commons.utils.Constants;
import es.caib.interdoc.ejb.converter.LogConverter;
import es.caib.interdoc.ejb.interceptor.ExceptionTranslate;
import es.caib.interdoc.ejb.interceptor.Logged;
import es.caib.interdoc.ejb.repository.LogRepository;
import es.caib.interdoc.persistence.model.Log;
import es.caib.interdoc.service.exception.RecursNoTrobatException;
import es.caib.interdoc.service.facade.LogServiceFacade;
import es.caib.interdoc.service.model.LogAtribut;
import es.caib.interdoc.service.model.LogDTO;
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
@Local(LogServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class LogServiceFacadeBean implements LogServiceFacade {

    @Inject
    private LogRepository repository;

    @Inject
    private LogConverter converter;

    @Override
    @RolesAllowed(Constants.ITD_ADMIN)
    public Long create(LogDTO dto) {
    	Log log = converter.toEntity(dto);
        repository.create(log);
        return log.getId();
    }

    @Override
    @RolesAllowed(Constants.ITD_ADMIN)
    public void delete(Long id) throws RecursNoTrobatException {
    	Log log = repository.getReference(id);
        repository.delete(log);
    }

    @Override
    @RolesAllowed({Constants.ITD_USER, Constants.ITD_ADMIN})
    public Optional<LogDTO> findById(Long id) {
    	Log log = repository.findById(id);
    	LogDTO logDTO = converter.toDTO(log);
        return Optional.ofNullable(logDTO);
    }

    @Override
    @PermitAll
    public Pagina<LogDTO> findFiltered(int firstResult, int maxResult, Map<LogAtribut, Object> filter,
                                             List<Ordre<LogAtribut>> ordenacio) {

        List<LogDTO> items = repository.findPagedByFilterAndOrder(firstResult, maxResult, filter, ordenacio);
        long total = repository.countByFilter(filter);

        return new Pagina<>(items, total);
    }
}
