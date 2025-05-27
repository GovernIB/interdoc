package es.caib.interdoc.ejb.scheduler;

import java.util.List;

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

import es.caib.interdoc.service.facade.EntitatServiceFacade;
import es.caib.interdoc.service.facade.InfoArxiuServiceFacade;
import es.caib.interdoc.service.model.EntitatDTO;
import es.caib.interdoc.service.model.PluginDTO;
import es.caib.interdoc.commons.utils.Configuracio;
import es.caib.interdoc.commons.utils.Constants;
import es.caib.interdoc.ejb.facade.PluginArxiuServiceFacade;
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
	
	@EJB(mappedName = PluginArxiuServiceFacade.JNDI_NAME)
	protected PluginArxiuServiceFacade pluginService;
	
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
		
		List<EntitatDTO> entitats = entitatService.getAll();
		
		entitats.forEach( entitat -> {
			
			log.info("====>  Execució scheduler tancar expedients per l'entitat " + entitat.getNom() + " amb Id " + entitat.getId());
			
			List<String> expedients = infoArxiuService.getExpedientsObertsPerEntitat(entitat.getId());
			log.info("Número d'expedients no tancats: " + expedients.size());
			
			// Comprobam si existeix un plugin d'arxiu per l'entitat
			List<PluginDTO> plugins = pluginService.getByTipus(Constants.PLUGIN_ARXIU, entitat.getId());
			if (plugins.size() > 0) {
				
				//ArxiuController arxiuController = new ArxiuController(entitat.getId());
				
				InterdocArxiuPlugin plugin = null;
                try {
                    plugin = pluginService.getPlugin(entitat.getId());
                } catch (Exception e) {
                    log.error("ERROR: No s'ha pogut inicialitzar el Plugin de Arxiu.",e);
                    e.printStackTrace();
                }
					
				for (String expedientId : expedients) {
					try {
						
						boolean closed = false;
						
						if (plugin != null) {
							
							log.info("Tancant expedient: " + expedientId);
							closed = plugin.tancarExpedient(expedientId);
							
							if (closed) {
								log.info("Expedient tancat: " + expedientId + " - actualitzam a la BD");
								infoArxiuService.tancarExpedient(expedientId, entitat.getId());
							} else {
								log.info("Error al tancar expedient: " + expedientId + " - augmentam reintents");
								infoArxiuService.aumentarReintents(expedientId, entitat.getId(), 10L);
							}
							
						}
					} catch (Exception e) {
						log.info("Error al tancar expedient: " + expedientId);
						e.printStackTrace();
					}
				}
			}else {
				log.error("L'entitat " + entitat.getNom() + " amb ID " + entitat.getId() + " no té cap plugin de tipus ARXIU configurat" );
			}
		});
		
	}

}
