package es.caib.interdoc.back.controller;

import es.caib.interdoc.back.model.UsuariEntitatModel;
import es.caib.interdoc.back.utils.PFUtils;
import es.caib.interdoc.service.facade.EntitatServiceFacade;
import es.caib.interdoc.service.facade.UsuariEntitatServiceFacade;
import es.caib.interdoc.service.facade.UsuariServiceFacade;
import es.caib.interdoc.service.model.EntitatDTO;
import es.caib.interdoc.service.model.Ordre;
import es.caib.interdoc.service.model.Pagina;
import es.caib.interdoc.service.model.UsuariDTO;
import es.caib.interdoc.service.model.UsuariEntitatAtribut;
import es.caib.interdoc.service.model.UsuariEntitatDTO;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Controlador pels llistats de usuaris-entitat. El definim a l'scope de view perquè a nivell de request es
 * reconstruiria per cada petició AJAX, com ara amb la paginació. Amb view es manté mentre no es canvii de vista.
 *
 * @author areus
 */
@Named
@ViewScoped
@RolesAllowed("ITD_ADMIN")
public class ListUsuariEntitat extends AbstractController implements Serializable {

    private static final long serialVersionUID = -6015369276336087696L;

    private static final Logger LOG = LoggerFactory.getLogger(ListUsuariEntitat.class);

    @EJB
    private UsuariEntitatServiceFacade usuariEntitatService;

    @EJB
    private UsuariServiceFacade usuariService;
    @EJB
    private EntitatServiceFacade entitatService;

    /**
     * Model de dades emprat pel compoment dataTable de primefaces.
     */
    private LazyDataModel<UsuariEntitatModel> lazyModel;

    public LazyDataModel<UsuariEntitatModel> getLazyModel() {
        return lazyModel;
    }

    /**
     * Inicialitzam el bean amb les dades inicials.
     */
    @PostConstruct
    public void init() {
        LOG.debug("init");

        lazyModel = new LazyDataModel<UsuariEntitatModel>() {

            private static final long serialVersionUID = 1L;

            /*
            Primefaces cridarà automàticament aquest mètode quan necessita actualitzar les dades del dataTable
            per qualsevol circumstància (filtres, ordenació, canvi de pàgina ...)
            */

            @Override
            public List<UsuariEntitatModel> load(int first, int pageSize, Map<String, SortMeta> sortBy,
                    Map<String, FilterMeta> filterBy) {
                LOG.info("load: " + first + " - " + pageSize);
                LOG.info("filterBy: {}", filterBy);

                Map<UsuariEntitatAtribut, Object> filter = PFUtils.filterMetaToFilter(UsuariEntitatAtribut.class,
                        filterBy);
                List<Ordre<UsuariEntitatAtribut>> ordenacions = PFUtils.sortMetaToOrdre(UsuariEntitatAtribut.class,
                        sortBy);

                Pagina<UsuariEntitatDTO> paginaDTO = usuariEntitatService.findFiltered(first, pageSize, filter,
                        ordenacions);
                List<UsuariEntitatModel> usuariEntitatItems = new ArrayList<UsuariEntitatModel>();

                for (UsuariEntitatDTO dto : paginaDTO.getItems()) {
                    LOG.info("DTO abans de passar a model: {}", dto);
                    UsuariEntitatModel model = dtoToModel(dto);
                    usuariEntitatItems.add(model);
                }

                this.setRowCount((int) paginaDTO.getTotal()); // <-- molt important

                return usuariEntitatItems;
            }
        };
    }

 // Converteix el model a DTO
    public UsuariEntitatDTO modelToDTO(UsuariEntitatModel model) {
        UsuariEntitatDTO dto = new UsuariEntitatDTO();

        if (model.getUsuariEntitatId() != null) {
            dto = new UsuariEntitatDTO();
            dto.setUsuariEntitatId(model.getUsuariEntitatId());
            dto.setActiu(model.isActiu());
            dto.setUsuariId(model.getUsuariId());
            dto.setEntitatId(model.getEntitatId());
        }else {
            UsuariDTO usuariDTO = usuariService.findByUsername(model.getUsername()).orElseThrow();
            dto.setUsuariId(usuariDTO.getUsuariId());
            
            EntitatDTO entitatDTO = entitatService.findByNom(model.getEntitatNom()).orElseThrow();
            dto.setEntitatId(entitatDTO.getId());
        }
        return dto;
    }

    public UsuariEntitatModel dtoToModel(UsuariEntitatDTO dto) {
        UsuariEntitatModel model = new UsuariEntitatModel();
        model.setUsuariEntitatId(dto.getUsuariEntitatId());
        model.setUsuariId(dto.getUsuariId());
        model.setEntitatId(dto.getEntitatId());
        model.setActiu(dto.isActiu());
        
        model.setUsername(usuariService.findById(dto.getUsuariId()).get().getUsername());
        model.setEntitatNom(entitatService.findById(dto.getEntitatId()).get().getNom());
        
        return model;
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

        usuariEntitatService.delete(id);
        addGlobalMessage(labelsBundle.getString("msg.eliminaciocorrecta"));

    }
}
