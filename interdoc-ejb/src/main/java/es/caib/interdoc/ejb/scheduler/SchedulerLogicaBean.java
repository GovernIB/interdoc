package es.caib.interdoc.ejb.scheduler;

import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;

import org.apache.log4j.Logger;
import org.fundaciobit.pluginsib.core.v3.utils.AbstractPluginProperties;

import es.caib.interdoc.service.facade.EntitatServiceFacade;
import es.caib.interdoc.service.facade.InfoArxiuServiceFacade;
import es.caib.interdoc.service.model.EntitatDTO;
import es.caib.interdoc.service.model.PluginDTO;
import es.caib.pluginsib.arxiu.api.IArxiuPlugin;
import es.caib.interdoc.commons.utils.Configuracio;
import es.caib.interdoc.commons.utils.Constants;
import es.caib.interdoc.ejb.PluginArxiuLogicaEJB;
import es.caib.interdoc.ejb.PluginArxiuLogicaService;
import es.caib.interdoc.ejb.facade.PluginArxiuServiceFacade;
import es.caib.interdoc.plugins.arxiu.ArxiuPluginImpl;
import es.caib.interdoc.plugins.arxiu.InterdocArxiuPlugin;
import es.caib.interdoc.commons.config.PropertyFileConfigSource;



@Startup
@Singleton
public class SchedulerLogicaBean implements SchedulerLogicaService{
	
	protected static final Logger log = Logger.getLogger(SchedulerLogicaBean.class);
	
	
	   private static final String PROPERTY_START_TIME = INTERDOC_SCHEDULER_PROPERTY + "starttime";
       private static final String PROPERTY_END_TIME = INTERDOC_SCHEDULER_PROPERTY + "endtime";
       private static final String PROPERTY_FREQUENCY = INTERDOC_SCHEDULER_PROPERTY + "frequency";
       
       private final int MINTIME_ARCHIVAT = 3;

	
	@EJB(mappedName = InfoArxiuServiceFacade.JNDI_NAME)
	protected InfoArxiuServiceFacade infoArxiuService;
	
	@EJB(mappedName = EntitatServiceFacade.JNDI_NAME)
	protected EntitatServiceFacade entitatService;
	
	//@EJB(mappedName = PluginArxiuServiceFacade.JNDI_NAME)
	//protected PluginArxiuServiceFacade pluginService;

    @EJB(mappedName = PluginArxiuLogicaService.JNDI_NAME)
	protected PluginArxiuLogicaService pluginService;
	
	@Resource
	public TimerService timerService;
	
	
	
	public void setTimerService(TimerService timerService) {
		this.timerService = timerService;
	}
	
	
	@PostConstruct
	public void init() {
	    int startTime=-1, endTime=-2, frequency=-3;
	    
	    log.info("**************** Scheduler INIT ********");	    

        PropertyFileConfigSource prop = new PropertyFileConfigSource();
	    if(!prop.getValue(PROPERTY_START_TIME).isBlank() && !prop.getValue(PROPERTY_END_TIME).isBlank()) {
	        startTime = Integer.parseInt(prop.getValue(PROPERTY_START_TIME));
	        endTime = Integer.parseInt(prop.getValue(PROPERTY_END_TIME));
	    }
	    if(!prop.getValue(PROPERTY_START_TIME).isBlank()) {
	        frequency = Integer.parseInt(prop.getValue(PROPERTY_FREQUENCY));
	    }

	    /*
	    
	    // Pre-inicialitzar tots els plugins d'arxiu actius per a cada entitat
	    log.info("Pre-inicialitzant plugins d'arxiu per a totes les entitats...");
	    try {
	        List<EntitatDTO> entitats = entitatService.getAll();
	        int pluginsInicialitzats = 0;
	        int pluginsNoDisponibles = 0;
	        
	        for (EntitatDTO entitat : entitats) {
	            try {
	                InterdocArxiuPlugin plugin = pluginService.getByTipus(.getId());
	                if (plugin != null) {
	                    log.info("Plugin d'arxiu inicialitzat per l'entitat: " + entitat.getNom() + " (ID: " + entitat.getId() + ")");
	                    pluginsInicialitzats++;
	                } else {
	                    log.warn("No s'ha trobat plugin d'arxiu ACTIU per l'entitat: " + entitat.getNom() + " (ID: " + entitat.getId() + ")");
	                    pluginsNoDisponibles++;
	                }
	            } catch (Exception e) {
	                log.error("Error inicialitzant plugin d'arxiu per l'entitat: " + entitat.getNom() + " (ID: " + entitat.getId() + ")", e);
	                pluginsNoDisponibles++;
	            }
	        }
	        
	        log.info("Resum inicialització plugins: " + pluginsInicialitzats + " inicialitzats correctament, " + 
	                 pluginsNoDisponibles + " no disponibles o amb errors.");
	    } catch (Exception e) {
	        log.error("Error durant la pre-inicialització dels plugins d'arxiu", e);
	    }
	    */
		ScheduleExpression expression = new ScheduleExpression();
        expression.dayOfWeek("Sun,Mon,Tue,Wed,Thu,Fri,Sat");
        
        if(frequency >= MINTIME_ARCHIVAT) {
            expression.minute("*/"+frequency);
            expression.second("0");
        }else {
            expression.minute("*/"+MINTIME_ARCHIVAT);
            expression.second("0");
        }
        
        if( startTime >= 0 && startTime <= 24 && endTime >= 0 && endTime <= 24) {
            expression.hour(startTime+"-"+endTime);
        }else {
            expression.hour("*");
        }
        
        expression.timezone("Europe/Madrid");
        
        if(Configuracio.isDesenvolupament())
        	log.info("> > > SCHEDULER STARTUP  > > > SCHEDULER TIMER: " + expression.toString());	
		
		TimerConfig config = new TimerConfig();
		config.setInfo("Tancar expedients");
		config.setPersistent(false);
		
		timerService.createCalendarTimer(expression, config);
	
	}
	

	@Timeout
	public void execute(Timer timer){
		
		log.info("====> Inici execució scheduler tancar expedients");
		List<EntitatDTO> entitats = entitatService.getAll();
		log.info("Total entitats a processar: " + entitats.size());
		
		entitats.forEach( entitat -> {
			
			log.info("====> Processant entitat: " + entitat.getNom() + " (ID: " + entitat.getId() + ")");
			
			// Obtenir expedients NOMÉS d'aquesta entitat específica
			List<String> expedients = infoArxiuService.getExpedientsObertsPerEntitat(entitat.getId());
			log.info("Número d'expedients oberts per l'entitat " + entitat.getNom() + ": " + expedients.size());
			
			if (expedients.isEmpty()) {
				log.info("No hi ha expedients oberts per tancar a l'entitat " + entitat.getNom());
				return;
			}
			
			// Obtenir el plugin d'arxiu ACTIU específic d'aquesta entitat
            ArxiuPluginImpl plugin;
			try {
            plugin = pluginService.getInstanceOfPlugin(entitat.getId());
            } catch (Exception e) {
                log.error("Error obtenint el plugin d'arxiu per l'entitat " + entitat.getNom() + " (ID: " + entitat.getId() + ")", e);
                return;
            }
			
            
			
			// Processar cada expedient AMB EL PLUGIN DE LA SEVA ENTITAT
			int expedientsTancats = 0;
			int expedientsError = 0;
			
			for (String expedientId : expedients) {
				try {
					log.info("Processant expedient: " + expedientId + " de l'entitat: " + entitat.getNom() + " (ID: " + entitat.getId() + ")");
					
					boolean closed = plugin.tancarExpedient(expedientId);
					
					if (closed) {
						log.info("✓ Expedient tancat correctament: " + expedientId + " (Entitat: " + entitat.getNom() + ") - actualitzant BD");
						infoArxiuService.tancarExpedient(expedientId, entitat.getId());
						expedientsTancats++;
					} else {
						log.warn("✗ No s'ha pogut tancar l'expedient: " + expedientId + " (Entitat: " + entitat.getNom() + ") - augmentant reintents");
						infoArxiuService.aumentarReintents(expedientId, entitat.getId(), 10L);
						expedientsError++;
					}
					
				} catch (Exception e) {
					log.error("✗ Error processant expedient: " + expedientId + " (Entitat: " + entitat.getNom() + ")", e);
					expedientsError++;
					try {
						infoArxiuService.aumentarReintents(expedientId, entitat.getId(), 10L);
					} catch (Exception ex) {
						log.error("Error augmentant reintents per expedient: " + expedientId, ex);
					}
				}
			}
			
			log.info("====> Resum entitat " + entitat.getNom() + ": " + expedientsTancats + " expedients tancats, " + expedientsError + " amb errors");
			
		});
		
		log.info("====> Fi execució scheduler tancar expedients");
	}

}
