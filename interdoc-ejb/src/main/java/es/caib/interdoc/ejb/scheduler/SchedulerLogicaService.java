package es.caib.interdoc.ejb.scheduler;

import javax.ejb.Local;
import javax.ejb.Timer;
import javax.ejb.TimerService;

/**
 * @author jagarcia
 */


@Local
public interface SchedulerLogicaService {
	
	public static final String JNDI_NAME = "java:app/interdoc-ejb/SchedulerLogicaServiceBean!es.caib.interdoc.ejb.scheduler.SchedulerLogicaService";
	
	public void init();
	
	public void execute(Timer timer);
	
	public void setTimerService(TimerService timerService);

}
