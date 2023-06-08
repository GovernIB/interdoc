package es.caib.interdoc.back.controller;

import es.caib.interdoc.back.utils.PFUtils;
import es.caib.interdoc.service.facade.AccesServiceFacade;
import es.caib.interdoc.service.model.AccesAtribut;
import es.caib.interdoc.service.model.AccesDTO;
import es.caib.interdoc.service.model.Ordre;
import es.caib.interdoc.service.model.Pagina;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Controlador pels llistats de aplicacions. El definim a l'scope de view perquè a nivell de request es
 * reconstruiria per cada petició AJAX, com ara amb la paginació. Amb view es manté mentre no es canvii de vista.
 *
 * @author areus
 */
@Named
@ViewScoped
public class ListAcces extends AbstractController implements Serializable {

    private static final long serialVersionUID = -6015369276336087696L;

    private static final Logger LOG = LoggerFactory.getLogger(ListAcces.class);

    @EJB
    private AccesServiceFacade accesService;

    /**
     * Model de dades emprat pel compoment dataTable de primefaces.
     */
    private LazyDataModel<AccesDTO> lazyModel;

    public LazyDataModel<AccesDTO> getLazyModel() {
        return lazyModel;
    }

    /**
     * Inicialitzam el bean amb les dades inicials.
     */
    @PostConstruct
    public void init() {
        LOG.debug("init");

        lazyModel = new LazyDataModel<AccesDTO>() {

            private static final long serialVersionUID = 1L;

            /*
            Primefaces cridarà automàticament aquest mètode quan necessita actualitzar les dades del dataTable
            per qualsevol circumstància (filtres, ordenació, canvi de pàgina ...)
            */

            @Override
            public List<AccesDTO> load(int first, int pageSize, Map<String, SortMeta> sortBy,
                                       Map<String, FilterMeta> filterBy) {
                LOG.info("filterBy: {}", filterBy);

                // Dins JSF emprarem noms que coincideixin amb els valors de l'enumeració AtributUnitat
                Map<AccesAtribut, Object> filter = PFUtils.filterMetaToFilter(AccesAtribut.class, filterBy);
                List<Ordre<AccesAtribut>> ordenacions = PFUtils.sortMetaToOrdre(AccesAtribut.class, sortBy);

                Pagina<AccesDTO> pagina = accesService
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

        accesService.delete(id);
        addGlobalMessage(labelsBundle.getString("msg.eliminaciocorrecta"));

    }
}
