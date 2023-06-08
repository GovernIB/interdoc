package es.caib.interdoc.ejb.facade;

import es.caib.interdoc.commons.utils.Constants;
import es.caib.interdoc.ejb.converter.AplicacioConverter;
import es.caib.interdoc.ejb.interceptor.ExceptionTranslate;
import es.caib.interdoc.ejb.interceptor.Logged;
import es.caib.interdoc.ejb.repository.AplicacioRepository;
import es.caib.interdoc.persistence.model.Aplicacio;
import es.caib.interdoc.service.exception.AplicacioDuplicadaException;
import es.caib.interdoc.service.exception.RecursNoTrobatException;
import es.caib.interdoc.service.facade.AplicacioServiceFacade;
import es.caib.interdoc.service.model.AplicacioAtribut;
import es.caib.interdoc.service.model.AplicacioDTO;
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
@Local(AplicacioServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class AplicacioServiceFacadeBean implements AplicacioServiceFacade {

    @Inject
    private AplicacioRepository repository;

    @Inject
    private AplicacioConverter converter;

    @Override
    @RolesAllowed(Constants.ITD_ADMIN)
    public Long create(AplicacioDTO dto) throws AplicacioDuplicadaException {
        if (repository.findByCodiDir3(dto.getCodiDir3()).isPresent()) {
            throw new AplicacioDuplicadaException(dto.getCodiDir3());
        }

        Aplicacio aplicacio = converter.toEntity(dto);
        repository.create(aplicacio);
        return aplicacio.getId();
    }

    @Override
    @RolesAllowed(Constants.ITD_ADMIN)
    public void update(AplicacioDTO dto) throws RecursNoTrobatException {
        Aplicacio aplicacio = repository.getReference(dto.getId());
        converter.updateFromDTO(aplicacio, dto);
    }

    @Override
    @RolesAllowed(Constants.ITD_ADMIN)
    public void delete(Long id) throws RecursNoTrobatException {
        Aplicacio aplicacio = repository.getReference(id);
        repository.delete(aplicacio);
    }

    @Override
    @RolesAllowed({Constants.ITD_USER, Constants.ITD_ADMIN})
    public Optional<AplicacioDTO> findById(Long id) {
        Aplicacio aplicacio = repository.findById(id);
        AplicacioDTO aplicacioDTO = converter.toDTO(aplicacio);
        return Optional.ofNullable(aplicacioDTO);
    }

    @Override
    @PermitAll
    public List<AplicacioDTO> getAll() {
        List<AplicacioDTO> items = new ArrayList<AplicacioDTO>();
        List<Aplicacio> llista = repository.getAll();
        for (Aplicacio a : llista ) {
            items.add(converter.toDTO(a));
        }
        return items;
    }

    @Override
    @PermitAll
    public Pagina<AplicacioDTO> findFiltered(int firstResult, int maxResult, Map<AplicacioAtribut, Object> filter,
                                             List<Ordre<AplicacioAtribut>> ordenacio) {

        List<AplicacioDTO> items = repository.findPagedByFilterAndOrder(firstResult, maxResult, filter, ordenacio);
        long total = repository.countByFilter(filter);

        return new Pagina<>(items, total);
    }
    
    @Override
    @PermitAll
    public boolean autenticarUsuario(String usuario, String clave, String referencia) {
    	return true;
    }
}
