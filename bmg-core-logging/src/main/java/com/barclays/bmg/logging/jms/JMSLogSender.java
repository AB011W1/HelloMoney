package com.barclays.bmg.logging.jms;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import com.barclays.mcfe.logging.common.LoggerMCFE;
import com.barclays.mcfe.logging.exception.ServiceNotFoundException;
import com.barclays.mcfe.logging.util.JMSServiceFactory;
import com.barclays.mcfe.logging.util.JNDIServiceLocator;

public class JMSLogSender implements InitializingBean {

    private static final Logger LOGGER = Logger.getLogger(JMSLogSender.class);

    String initialContextFactoryName;
    String providerURL;
    String queueBindingName;
    String qcfBindingName;
    QueueConnectionFactory queueFactory;
    Queue queue;
    InitialContext context;

    public void send(Object obj) {
	try {
	    LOGGER.debug("Entering send() [JMSLogSender] ");

	    QueueConnection queueConnection;
	    QueueSession queueSession;
	    javax.jms.QueueSender queueSender;
	    queueConnection = null;
	    queueSession = null;
	    queueSender = null;

	    queueConnection = queueFactory.createQueueConnection();
	    queueSession = queueConnection.createQueueSession(false, 1);
	    queueSender = queueSession.createSender(queue);

	    LOGGER.debug("[ queueSender ] " + queueSender + " [ queueSession ]  " + queueSession);

	    ObjectMessage msg = queueSession.createObjectMessage();
	    msg.setObject((LoggerMCFE) obj);

	    queueSender.send(msg);
	    queueSender.close();
	    queueSession.close();
	    queueConnection.close();

	} catch (JMSException e) {
	    LOGGER.error("[ JMSException ] ", e);
	} catch (Exception e) {
	    LOGGER.error("JMSLogSender.send() Exception", e);

	}
    }

    @Override
    public void afterPropertiesSet() {
	JMSServiceFactory jmsService = JMSServiceFactory.getJmsServiceFactory();
	JNDIServiceLocator serviceLocator;
	try {
	    serviceLocator = new JNDIServiceLocator(providerURL, initialContextFactoryName);
	    queueFactory = (QueueConnectionFactory) jmsService.getTargetJMSConnectionFactory(qcfBindingName, serviceLocator);
	    queue = jmsService.getTargetJMSQueue(queueBindingName, serviceLocator);
	} catch (ServiceNotFoundException e) {
	    LOGGER.error("[ afterPropertiesSet() ]", e);
	}
    }

    public String getInitialContextFactoryName() {
	return initialContextFactoryName;
    }

    public void setInitialContextFactoryName(String initialContextFactoryName) {
	this.initialContextFactoryName = initialContextFactoryName;
    }

    public String getProviderURL() {
	return providerURL;
    }

    public void setProviderURL(String providerURL) {
	this.providerURL = providerURL;
    }

    public String getQueueBindingName() {
	return queueBindingName;
    }

    public void setQueueBindingName(String queueBindingName) {
	this.queueBindingName = queueBindingName;
    }

    public String getQcfBindingName() {
	return qcfBindingName;
    }

    public void setQcfBindingName(String qcfBindingName) {
	this.qcfBindingName = qcfBindingName;
    }

    public QueueConnectionFactory getQueueFactory() {
	return queueFactory;
    }

    public void setQueueFactory(QueueConnectionFactory queueFactory) {
	this.queueFactory = queueFactory;
    }

    public Queue getQueue() {
	return queue;
    }

    public void setQueue(Queue queue) {
	this.queue = queue;
    }

    public InitialContext getContext() {
	return context;
    }

    public void setContext(InitialContext context) {
	this.context = context;
    }

    public static Logger getLog() {
	return LOGGER;
    }

}
