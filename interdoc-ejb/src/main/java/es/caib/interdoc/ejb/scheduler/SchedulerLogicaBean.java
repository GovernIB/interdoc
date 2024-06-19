package es.caib.interdoc.ejb.scheduler;

import java.util.HashMap;
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

import es.caib.interdoc.service.facade.InfoArxiuServiceFacade;
import es.caib.interdoc.commons.utils.Configuracio;
import es.caib.interdoc.plugins.arxiu.ArxiuController;


@Startup
@Singleton
public class SchedulerLogicaBean implements SchedulerLogicaService{
	
	protected static final Logger log = Logger.getLogger(SchedulerLogicaBean.class);
	
	@EJB(mappedName = InfoArxiuServiceFacade.JNDI_NAME)
	protected InfoArxiuServiceFacade infoArxiuService;
	
	@Resource
	public TimerService timerService;
	
	public void setTimerService(TimerService timerService) {
		this.timerService = timerService;
	}
	
	
	@PostConstruct
	public void init() {
		
		ScheduleExpression expression = new ScheduleExpression();
        expression.dayOfWeek("Sun,Mon,Tue,Wed,Thu,Fri,Sat");
        expression.hour("17");
        expression.minute("0");
        expression.second("0");
        expression.timezone("Europe/Madrid");
        
        if(Configuracio.isDesenvolupament())
        	log.info("> > > SCHEDULER STARTUP  > > > SCHEDULER TIMER: " + expression.toString());	
		
		TimerConfig config = new TimerConfig();
		config.setInfo("Tancar expedients");
		config.setPersistent(false);
		
		timerService.createCalendarTimer(expression, config);
	
	}
	

	@Timeout
	public void execute(Timer timer) {
		
		HashMap<Long, List<String>> resultats = infoArxiuService.getExpedientsObertsPerEntitat("", 0L);
		
		for (Long entitatId : resultats.keySet()) {
			List<String> expedients = resultats.get(entitatId);
			
			ArxiuController arxiuController = new ArxiuController(entitatId);
			
			for (String expedientId : expedients) {
				try {
					
					boolean closed = false;
					
					if (arxiuController.getPlugin() != null) {
						closed = arxiuController.getPlugin().tancarExpedient(expedientId);
						
						if (closed) {
							log.info("Expedient tancat: " + expedientId + " - actualitzam a la BD");
							infoArxiuService.tancarExpedient(expedientId, entitatId);
						} else {
							log.info("Error al tancar expedient: " + expedientId + " - augmentam reintents");
							infoArxiuService.aumentarReintents(expedientId, entitatId, 10L);
						}
						
					}
				} catch (Exception e) {
					log.info("Error al tancar expedient: " + expedientId);
					e.printStackTrace();
				}
			}
		}
		
	}

}
