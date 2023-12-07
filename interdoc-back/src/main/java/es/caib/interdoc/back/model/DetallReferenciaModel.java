package es.caib.interdoc.back.model;

import es.caib.interdoc.service.facade.AccesServiceFacade;
import es.caib.interdoc.service.facade.EntitatServiceFacade;
import es.caib.interdoc.service.facade.FitxerServiceFacade;
import es.caib.interdoc.service.facade.InfoArxiuServiceFacade;
import es.caib.interdoc.service.facade.InfoSignaturaServiceFacade;
import es.caib.interdoc.service.facade.ReferenciaServiceFacade;
import es.caib.interdoc.service.facade.ReferenciaXMLServiceFacade;
import es.caib.interdoc.service.facade.TrazaServiceFacade;
import es.caib.interdoc.service.facade.MetadadaServiceFacade;
import es.caib.interdoc.service.model.AccesDTO;
import es.caib.interdoc.service.model.EntitatDTO;
import es.caib.interdoc.service.model.FitxerDTO;
import es.caib.interdoc.service.model.InfoArxiuDTO;
import es.caib.interdoc.service.model.InfoSignaturaDTO;
import es.caib.interdoc.service.model.MetadadaDTO;
import es.caib.interdoc.service.model.ReferenciaDTO;
import es.caib.interdoc.service.model.ReferenciaXMLDTO;
import es.caib.interdoc.service.model.TrazaDTO;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class DetallReferenciaModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = LoggerFactory.getLogger(DetallReferenciaModel.class);

	@EJB
	private ReferenciaServiceFacade referenciaService;
	
	@EJB
	private FitxerServiceFacade fitxerService;
	
	@EJB
	private InfoSignaturaServiceFacade signaturaService;
	
	@EJB
	private InfoArxiuServiceFacade arxiuService;
	
	@EJB
	private MetadadaServiceFacade metadadaService;
	
	@EJB
	private TrazaServiceFacade trazaService;
	
	@EJB
	private AccesServiceFacade accesService;
	
	@EJB
	private EntitatServiceFacade entitatService;
	
	@EJB
	private ReferenciaXMLServiceFacade referenciaXMLService;

	private ReferenciaDTO referencia = new ReferenciaDTO();
	
	private InfoSignaturaDTO infoSignatura = new InfoSignaturaDTO();
	
	private InfoArxiuDTO infoArxiu = new InfoArxiuDTO();
	
	private EntitatDTO entitat = new EntitatDTO();
	
	private FitxerDTO fitxer = new FitxerDTO();
	
	private ReferenciaXMLDTO referenciaXML = new ReferenciaXMLDTO();
	
	private List<AccesDTO> accesos = new ArrayList<AccesDTO>();
	
	private List<MetadadaDTO> metadades = new ArrayList<MetadadaDTO>();
	
	private List<TrazaDTO> trazabilitat = new ArrayList<TrazaDTO>();
		
	public ReferenciaDTO getReferencia() {
		return referencia;
	}

	public void setReferencia (ReferenciaDTO referencia) {
		this.referencia = referencia;
	}

	public InfoSignaturaDTO getInfoSignatura() {
		return infoSignatura;
	}

	public void setInfoSignatura(InfoSignaturaDTO infoSignatura) {
		this.infoSignatura = infoSignatura;
	}

	public InfoArxiuDTO getInfoArxiu() {
		return infoArxiu;
	}

	public void setInfoArxiu(InfoArxiuDTO infoArxiu) {
		this.infoArxiu = infoArxiu;
	}

	public EntitatDTO getEntitat() {
		return entitat;
	}

	public void setEntitat(EntitatDTO entitat) {
		this.entitat = entitat;
	}

	public FitxerDTO getFitxer() {
		return fitxer;
	}

	public void setFitxer(FitxerDTO fitxer) {
		this.fitxer = fitxer;
	}
	
	public ReferenciaXMLDTO getReferenciaXML() {
		return referenciaXML;
	}

	public void setReferenciaXML(ReferenciaXMLDTO referenciaXML) {
		this.referenciaXML = referenciaXML;
	}

	public List<AccesDTO> getAccesos() {
		return accesos;
	}

	public void setAccesos(List<AccesDTO> accesos) {
		this.accesos = accesos;
	}

	public List<MetadadaDTO> getMetadades() {
		return metadades;
	}

	public void setMetadades(List<MetadadaDTO> metadades) {
		this.metadades = metadades;
	}
	
	public List<TrazaDTO> getTrazabilitat() {
		return trazabilitat;
	}

	public void setTrazabilitat(List<TrazaDTO> trazabilitat) {
		this.trazabilitat = trazabilitat;
	}

	public void load() {
		if (referencia.getId() == null) {
			throw new IllegalArgumentException("id is null");
		}
		referencia = referenciaService.findById(referencia.getId()).orElseThrow();
		
		LOG.info("DETALL REFERENCIA");
		LOG.info(referencia.toString());
		
		if (referencia.getInfoSignaturaId() != null && referencia.getInfoSignaturaId() > 0) {
			infoSignatura = signaturaService.findById(referencia.getInfoSignaturaId()).orElseThrow();
			LOG.info("--------------------------------");
			LOG.info(infoSignatura.toString());
		}
		
		if (referencia.getInfoArxiuId() != null && referencia.getInfoArxiuId() > 0) {
			infoArxiu = arxiuService.findById(referencia.getInfoArxiuId()).orElseThrow();
			LOG.info("--------------------------------");
			LOG.info(infoArxiu.toString());
		}
		
		if (referencia.getEntitatId() != null && referencia.getEntitatId() > 0) {
			entitat = entitatService.findById(referencia.getEntitatId()).orElseThrow();
			LOG.info("--------------------------------");
			LOG.info(entitat.toString());
		}
		
		if (referencia.getFitxerId() != null && referencia.getFitxerId() > 0) {
			fitxer = fitxerService.findById(referencia.getFitxerId()).orElseThrow();
			LOG.info("--------------------------------");
			LOG.info(fitxer.toString());
		}
		
		accesos = accesService.findByRefenciaId(referencia.getId()).orElseThrow();
		if (accesos.size() > 0) {
			LOG.info("-------------------------------- ACCESOS --------------");
			accesos.forEach(x -> LOG.info( "Identicacio: " + x.getIdentificacio() + " Tipus: " + x.getTipusIdentificacio()));
		}
		
		metadades = metadadaService.findByReferenciaId(referencia.getId()).orElseThrow();
		if (metadades.size() > 0) {
			LOG.info("-------------------------------- METADADES ------------------");
			metadades.forEach(x -> LOG.info("clau: " + x.getNom() + " - valor: " + x.getValor()));
		}
		
		trazabilitat = trazaService.findByReferenciaId(referencia.getId()).orElseThrow();
		if (trazabilitat.size() > 0) {
			LOG.info("-------------------------------- TRAZABILITAT ---------------");
			trazabilitat.forEach(x -> LOG.info("clau: " + x.getNom() + " - valor: " + x.getValor()));
		}
		
		
		referenciaXML = referenciaXMLService.findByReferenciaId(referencia.getId()).orElseThrow();
		if (referenciaXML != null)
			LOG.info("-------------------------------- XML---------------------");
			LOG.info(referenciaXML.toString());
		
	}

	
}
