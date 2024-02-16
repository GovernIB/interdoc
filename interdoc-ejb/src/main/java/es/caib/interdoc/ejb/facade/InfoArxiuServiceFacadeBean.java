package es.caib.interdoc.ejb.facade;

import es.caib.interdoc.commons.utils.Constants;
import es.caib.interdoc.ejb.converter.InfoArxiuConverter;
import es.caib.interdoc.ejb.interceptor.ExceptionTranslate;
import es.caib.interdoc.ejb.interceptor.Logged;
import es.caib.interdoc.ejb.repository.InfoArxiuRepository;
import es.caib.interdoc.persistence.model.InfoArxiu;
import es.caib.interdoc.service.exception.RecursNoTrobatException;
import es.caib.interdoc.service.facade.InfoArxiuServiceFacade;
import es.caib.interdoc.service.model.InfoArxiuAtribut;
import es.caib.interdoc.service.model.InfoArxiuDTO;
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
@Local(InfoArxiuServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class InfoArxiuServiceFacadeBean implements InfoArxiuServiceFacade {

    @Inject
    private InfoArxiuRepository repository;

    @Inject
    private InfoArxiuConverter converter;

    @Override
    @PermitAll
    public Long create(InfoArxiuDTO dto) {
    	InfoArxiu infoArxiu = converter.toEntity(dto);
        repository.create(infoArxiu);
        return infoArxiu.getId();
    }

    @Override
    @RolesAllowed(Constants.ITD_ADMIN)
    public void update(InfoArxiuDTO dto) throws RecursNoTrobatException {
        InfoArxiu infoArxiu = repository.getReference(dto.getId());
        converter.updateFromDTO(infoArxiu, dto);
    }

    @Override
    @RolesAllowed(Constants.ITD_ADMIN)
    public void delete(Long id) throws RecursNoTrobatException {
    	InfoArxiu infoArxiu = repository.getReference(id);
        repository.delete(infoArxiu);
    }

    @Override
    @PermitAll
    public Optional<InfoArxiuDTO> findById(Long id) {
    	InfoArxiu infoArxiu = repository.findById(id);
    	InfoArxiuDTO infoArxiuDTO = converter.toDTO(infoArxiu);
        return Optional.ofNullable(infoArxiuDTO);
    }

    @Override
    @PermitAll
    public List<InfoArxiuDTO> getAll() {
        List<InfoArxiuDTO> items = new ArrayList<InfoArxiuDTO>();
        List<InfoArxiu> llista = repository.getAll();
        for (InfoArxiu a : llista ) {
            items.add(converter.toDTO(a));
        }
        return items;
    }
    
    @Override
    @PermitAll
    public List<InfoArxiuDTO> getExpedientsOberts(String estat) {
         return repository.getExpedientsOberts(estat);
    }

    @Override
    @PermitAll
    public Pagina<InfoArxiuDTO> findFiltered(int firstResult, int maxResult, Map<InfoArxiuAtribut, Object> filter,
                                             List<Ordre<InfoArxiuAtribut>> ordenacio) {

        List<InfoArxiuDTO> items = repository.findPagedByFilterAndOrder(firstResult, maxResult, filter, ordenacio);
        long total = repository.countByFilter(filter);

        return new Pagina<>(items, total);
    }
    
}
