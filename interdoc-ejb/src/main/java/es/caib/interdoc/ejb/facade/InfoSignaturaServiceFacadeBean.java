package es.caib.interdoc.ejb.facade;

import es.caib.interdoc.commons.utils.Constants;
import es.caib.interdoc.ejb.converter.InfoSignaturaConverter;
import es.caib.interdoc.ejb.interceptor.ExceptionTranslate;
import es.caib.interdoc.ejb.interceptor.Logged;
import es.caib.interdoc.ejb.repository.InfoSignaturaRepository;
import es.caib.interdoc.persistence.model.InfoSignatura;
import es.caib.interdoc.service.exception.RecursNoTrobatException;
import es.caib.interdoc.service.facade.InfoSignaturaServiceFacade;
import es.caib.interdoc.service.model.InfoSignaturaAtribut;
import es.caib.interdoc.service.model.InfoSignaturaDTO;
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
@Local(InfoSignaturaServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class InfoSignaturaServiceFacadeBean implements InfoSignaturaServiceFacade {

    @Inject
    private InfoSignaturaRepository repository;

    @Inject
    private InfoSignaturaConverter converter;

    @Override
    @PermitAll
    public Long create(InfoSignaturaDTO dto) {
    	InfoSignatura infoSignatura = converter.toEntity(dto);
        repository.create(infoSignatura);
        return infoSignatura.getId();
    }

    @Override
    @RolesAllowed(Constants.ITD_ADMIN)
    public void update(InfoSignaturaDTO dto) throws RecursNoTrobatException {
    	InfoSignatura infoSignatura = repository.getReference(dto.getId());
        converter.updateFromDTO(infoSignatura, dto);
    }

    @Override
    @RolesAllowed(Constants.ITD_ADMIN)
    public void delete(Long id) throws RecursNoTrobatException {
    	InfoSignatura infoSignatura = repository.getReference(id);
        repository.delete(infoSignatura);
    }

    @Override
    @PermitAll
    public Optional<InfoSignaturaDTO> findById(Long id) {
    	InfoSignatura infoSignatura = repository.findById(id);
    	InfoSignaturaDTO infoSignaturaDTO = converter.toDTO(infoSignatura);
        return Optional.ofNullable(infoSignaturaDTO);
    }

    @Override
    @PermitAll
    public List<InfoSignaturaDTO> getAll() {
        List<InfoSignaturaDTO> items = new ArrayList<InfoSignaturaDTO>();
        List<InfoSignatura> llista = repository.getAll();
        for (InfoSignatura a : llista ) {
            items.add(converter.toDTO(a));
        }
        return items;
    }

    @Override
    @PermitAll
    public Pagina<InfoSignaturaDTO> findFiltered(int firstResult, int maxResult, Map<InfoSignaturaAtribut, Object> filter,
                                             List<Ordre<InfoSignaturaAtribut>> ordenacio) {

        List<InfoSignaturaDTO> items = repository.findPagedByFilterAndOrder(firstResult, maxResult, filter, ordenacio);
        long total = repository.countByFilter(filter);

        return new Pagina<>(items, total);
    }
    
}
