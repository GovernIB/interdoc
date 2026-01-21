package es.caib.interdoc.back.controller;

import es.caib.interdoc.back.utils.PFUtils;
import es.caib.interdoc.service.facade.EntitatServiceFacade;
import es.caib.interdoc.service.facade.UsuariEntitatServiceFacade;
import es.caib.interdoc.service.model.*;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Controlador pels llistats de aplicacions. El definim a l'scope de view perquè a nivell de request es
 * reconstruiria per cada petició AJAX, com ara amb la paginació. Amb view es manté mentre no es canvii de vista.
 *
 * @author areus
 */
@Named
@ViewScoped
public class ListEntitat extends AbstractController implements Serializable {

    private static final long serialVersionUID = -6015369276336087696L;

    private static final Logger LOG = LoggerFactory.getLogger(ListEntitat.class);

    @EJB
    private EntitatServiceFacade entitatService;
    
    @EJB
    private UsuariEntitatServiceFacade usuariEntitatService;
    
    @Inject
    private UserLocale userLocale;

    /**
     * Model de dades emprat pel compoment dataTable de primefaces.
     */
    private LazyDataModel<EntitatDTO> lazyModel;

    public LazyDataModel<EntitatDTO> getLazyModel() {
        return lazyModel;
    }

    /**
     * Inicialitzam el bean amb les dades inicials.
     */
    @PostConstruct
    public void init() {
        LOG.debug("init");

        lazyModel = new LazyDataModel<EntitatDTO>() {

            private static final long serialVersionUID = 1L;

            /*
            Primefaces cridarà automàticament aquest mètode quan necessita actualitzar les dades del dataTable
            per qualsevol circumstància (filtres, ordenació, canvi de pàgina ...)
            */

            @Override
            public List<EntitatDTO> load(int first, int pageSize, Map<String, SortMeta> sortBy,
                                           Map<String, FilterMeta> filterBy) {
                LOG.info("load: " + first + " - "  + pageSize);
            	LOG.info("filterBy: {}", filterBy);

                Map<EntitatAtribut, Object> filter = PFUtils.filterMetaToFilter(EntitatAtribut.class, filterBy);
                List<Ordre<EntitatAtribut>> ordenacions = PFUtils.sortMetaToOrdre(EntitatAtribut.class, sortBy);

                Pagina<EntitatDTO> pagina = entitatService
                        .findFiltered(first, pageSize, filter, ordenacions);

                setRowCount((int) pagina.getTotal());
                return pagina.getItems();
            }
        };
    }

    // ACCIONS

    /**
     * Esborra l'unitat orgànica amb l'identificador indicat. El mètode retorna void perquè no cal navegació ja que
     * l'eliminació es realitza des de la pàgina de llistat, i quedam en aquesta pàgina.
     *
     * @param id identificador de l'unitat orgànica
     */
    public void delete(Long id) {
        LOG.debug("delete");
        // Obtenir el resource bundle d'etiquetes definit a faces-config.xml
        ResourceBundle labelsBundle = getBundle("labels");

        entitatService.delete(id);
        addGlobalMessage(labelsBundle.getString("msg.eliminaciocorrecta"));

    }

    /**
     * Obté totes les entitats assignades a l'usuari actual.
     * Si l'usuari no té ID, retorna una llista buida.
     * 
     * @return Llista d'entitats assignades a l'usuari actual
     */
    public List<EntitatDTO> getAllEntitats() {
        // Obtenir l'ID de l'usuari des del UserLocale
        Long usuariId = userLocale != null ? userLocale.getUsuariId() : null;
        
        if (usuariId == null) {
            LOG.warn("No hi ha usuari identificat. Retornant llista buida d'entitats.");
            return Collections.emptyList();
        }
        
        LOG.debug("Obtenint entitats per l'usuari amb ID: {}", usuariId);
        
        // Crear filtre per usuariId
        Map<UsuariEntitatAtribut, Object> filter = new HashMap<>();
        filter.put(UsuariEntitatAtribut.usuariId, usuariId);
        
        // Obtenir totes les relacions UsuariEntitat per aquest usuari
        Pagina<UsuariEntitatDTO> paginaUsuariEntitat = usuariEntitatService.findFiltered(
            0, 
            Integer.MAX_VALUE, 
            filter, 
            Collections.emptyList()
        );
        
        // Extreure els IDs d'entitats i obtenir les entitats completes
        List<EntitatDTO> entitats = paginaUsuariEntitat.getItems().stream()
            .map(ue -> entitatService.findById(ue.getEntitatId()))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
        
        LOG.debug("Retornant {} entitats per l'usuari {}", entitats.size(), usuariId);
        
        return entitats;
    }
}
