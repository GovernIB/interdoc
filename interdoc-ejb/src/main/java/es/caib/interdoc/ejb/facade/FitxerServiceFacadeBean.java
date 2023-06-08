package es.caib.interdoc.ejb.facade;

import es.caib.interdoc.commons.utils.Constants;
import es.caib.interdoc.ejb.converter.FitxerConverter;
import es.caib.interdoc.ejb.interceptor.ExceptionTranslate;
import es.caib.interdoc.ejb.interceptor.Logged;
import es.caib.interdoc.ejb.repository.FitxerRepository;
import es.caib.interdoc.persistence.model.Fitxer;
import es.caib.interdoc.service.exception.RecursNoTrobatException;
import es.caib.interdoc.service.facade.FitxerServiceFacade;
import es.caib.interdoc.service.model.FitxerAtribut;
import es.caib.interdoc.service.model.FitxerDTO;
import es.caib.interdoc.service.model.Ordre;
import es.caib.interdoc.service.model.Pagina;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import java.util.ArrayList;
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
@Local(FitxerServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class FitxerServiceFacadeBean implements FitxerServiceFacade {

    @Inject
    private FitxerRepository repository;

    @Inject
    private FitxerConverter converter;

    @Override
    @PermitAll
    public Long create(FitxerDTO dto) {
    	Fitxer entitat = converter.toEntity(dto);
        repository.create(entitat);
        return entitat.getId();
    }

    @Override
    @RolesAllowed(Constants.ITD_ADMIN)
    public void update(FitxerDTO dto) throws RecursNoTrobatException {
    	Fitxer entitat = repository.getReference(dto.getId());
        converter.updateFromDTO(entitat, dto);
    }

    @Override
    @RolesAllowed(Constants.ITD_ADMIN)
    public void delete(Long id) throws RecursNoTrobatException {
    	Fitxer entitat = repository.getReference(id);
        repository.delete(entitat);
    }

    @Override
    @RolesAllowed({Constants.ITD_USER, Constants.ITD_ADMIN})
    public Optional<FitxerDTO> findById(Long id) {
    	Fitxer entitat = repository.findById(id);
    	FitxerDTO entitatDTO = converter.toDTO(entitat);
        return Optional.ofNullable(entitatDTO);
    }

    @Override
    @PermitAll
    public List<FitxerDTO> getAll() {
        List<FitxerDTO> items = new ArrayList<FitxerDTO>();
        List<Fitxer> llista = repository.getAll();
        for (Fitxer a : llista ) {
            items.add(converter.toDTO(a));
        }
        return items;
    }

    @Override
    @PermitAll
    public Pagina<FitxerDTO> findFiltered(int firstResult, int maxResult, Map<FitxerAtribut, Object> filter,
                                             List<Ordre<FitxerAtribut>> ordenacio) {

        List<FitxerDTO> items = repository.findPagedByFilterAndOrder(firstResult, maxResult, filter, ordenacio);
        long total = repository.countByFilter(filter);

        return new Pagina<>(items, total);
    }
    
}
