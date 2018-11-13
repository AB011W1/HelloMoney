package com.barclays.bmg.ecrime;

import javax.naming.NamingException;

import org.springframework.scheduling.commonj.WorkManagerTaskExecutor;

public class EcrimeWorkManager extends WorkManagerTaskExecutor {
    private final static String ENABLE_MULTI_THREAD_LOGGER = "enableMultiThreadLogger";

    private EcrimeConfiguration ecrimeConfig;

    private final static String WORK_MANAGER_JNDI_NAME = "workManagerJndiName";

    private boolean initialized = false;

    /**
     * clean the method, and let initiated when first use.
     * 
     * @throws NamingException
     * @see org.springframework.scheduling.commonj.WorkManagerTaskExecutor#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws NamingException {

    }

    @Override
    public void execute(Runnable task) {
	if (task != null) {
	    if (isMultiThreadLoggerEnabled()) {
		if (!initialized) {
		    try {
			init();
		    } catch (NamingException e) {
			task.run();
		    }
		}
		super.execute(task);
	    } else {
		task.run();
	    }
	}
    }

    private boolean isMultiThreadLoggerEnabled() {
	return ecrimeConfig.getBooleanParam(ENABLE_MULTI_THREAD_LOGGER, false);
    }

    /**
     * initiate the commonj work manager according to the JNDIName
     * 
     * @throws NamingException
     */
    public synchronized void init() throws NamingException {
	if (initialized) {
	    return;
	}

	if (this.ecrimeConfig != null) {
	    this.setWorkManagerName(getWorkManagerJndiName());
	}
	super.afterPropertiesSet();
	initialized = true;
    }

    private String getWorkManagerJndiName() {
	return ecrimeConfig.getGlobalParam(WORK_MANAGER_JNDI_NAME, "wm/default");
    }

    /**
     * @param ecrimeConfig
     *            the ecrimeConfig to set
     */
    public void setEcrimeConfig(EcrimeConfiguration ecrimeConfig) {
	this.ecrimeConfig = ecrimeConfig;
    }

}
