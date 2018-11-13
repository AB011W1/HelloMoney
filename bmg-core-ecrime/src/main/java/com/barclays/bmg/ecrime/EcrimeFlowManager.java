package com.barclays.bmg.ecrime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.barclays.bmg.utils.SpringBeansFactory;

public class EcrimeFlowManager {
    private static final ThreadLocal<EcrimeContext> contextHolder = new ThreadLocal<EcrimeContext>();

    private final static String DEFAULT_SERVICE_NAME = "ecrimeService";

    private String ecrimeServiceName = DEFAULT_SERVICE_NAME;

    private SpringBeansFactory beansFactory;

    private EcrimeWorkManager workManger;

    /**
     * set Ecrime Context.
     * 
     * @param context
     */
    public void setEcrimeContext(EcrimeContext context) {
	contextHolder.set(context);
    }

    /**
     * get Ecrime Context.
     * 
     * @return
     */
    public EcrimeContext getEcrimeContext() {
	return contextHolder.get();
    }

    public void clean() {
	contextHolder.set(null);
    }

    public void exeucteRequest(HttpServletRequest request, HttpServletResponse response) {
	EcrimeContext context = getEcrimeContext();
	if (context != null) {
	    getEcrimeService().executeRequest(context, request, response);
	    Runnable reqTask = createEcrimeRequestTask(context);
	    context.setRequestTask(reqTask);
	    // workManger.execute(reqTask);
	}
    }

    /**
     * @param context
     * @return
     */
    private Runnable createEcrimeRequestTask(EcrimeContext context) {
	return getEcrimeService().createRequestTask(context);
    }

    public void executeResponse(Object bean) {
	EcrimeContext context = getEcrimeContext();
	if (context != null) {
	    getEcrimeService().executeResponse(context, bean);
	    Runnable resTask = createEcrimeResponseTask(context, bean);
	    context.setResponseTask(resTask);
	    // if (resTask != null) {
	    // workManger.execute(resTask);
	    // }
	}
    }

    public void executeTask() {
	EcrimeContext context = getEcrimeContext();
	if (context != null) {

	    try {
		Runnable reqTask = context.getRequestTask();
		if (reqTask != null) {
		    workManger.execute(reqTask);
		}
	    } catch (Exception e) {
		
	    }

	    Runnable resTask = context.getResponseTask();
	    try {
		if (resTask != null) {
		    workManger.execute(resTask);
		}
	    } catch (Exception e) {
		
	    }
	}
    }

    public void setTransactionStatus(boolean status, String description) {
	EcrimeContext context = getEcrimeContext();
	if (context != null) {
	    getEcrimeService().setTransactionStatus(context, status, description);
	}
    }

    /**
     * @param context
     * @return
     */
    private Runnable createEcrimeResponseTask(EcrimeContext context, Object bean) {
	return getEcrimeService().createResponseTask(context, bean);
    }

    private EcrimeService getEcrimeService() {
	return getBeansFactory().getBean(ecrimeServiceName, EcrimeService.class);
    }

    /**
     * @param beansFactory
     *            the beansFactory to set
     */
    public void setBeansFactory(SpringBeansFactory beansFactory) {
	this.beansFactory = beansFactory;
    }

    /**
     * @return the beansFactory
     */
    public SpringBeansFactory getBeansFactory() {
	return beansFactory;
    }

    /**
     * @param workManger
     *            the workManger to set
     */
    public void setWorkManger(EcrimeWorkManager workManger) {
	this.workManger = workManger;
    }

    /**
     * @return the workManger
     */
    public EcrimeWorkManager getWorkManger() {
	return workManger;
    }

    /**
     * @param ecrimeServiceName
     *            the ecrimeServiceName to set
     */
    public void setExecutorName(String ecrimeServiceName) {
	this.ecrimeServiceName = ecrimeServiceName;
    }
}
