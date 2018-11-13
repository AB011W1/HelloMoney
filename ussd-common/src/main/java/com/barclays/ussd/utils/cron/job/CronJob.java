package com.barclays.ussd.utils.cron.job;

import org.apache.log4j.Logger;

/**
 * @author E20041929
 * 
 *         Cron job to be scheduled in work method. This work method is invoked as springs scheduled tasks from spring scheduler.xml
 */
public class CronJob implements Worker {

    private static final Logger LOGGER = Logger.getLogger(CronJob.class);

    private String runSchedular;

    public void work() {
	if (Boolean.valueOf(runSchedular)) {
	    String threadName = Thread.currentThread().getName();
	    LOGGER.debug("   " + threadName + " has began working.");
	    try {
		LOGGER.debug("working...");
		Thread.sleep(1); // simulates work
	    } catch (InterruptedException e) {
		Thread.currentThread().interrupt();
	    }
	    LOGGER.debug("   " + threadName + " has completed work.");
	}
    }

    public void setRunSchedular(String runSchedular) {
	this.runSchedular = runSchedular;
    }

    public String isRunSchedular() {
	return runSchedular;
    }

}
