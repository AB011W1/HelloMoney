package com.barclays.bmg.logging.dao.impl;

import java.net.InetAddress;
import java.util.Calendar;

import org.apache.log4j.Logger;

import com.barclays.bmg.context.Context;
import com.barclays.bmg.logging.dao.LoggingDAO;
import com.barclays.bmg.logging.jms.JMSLogSender;
import com.barclays.bmg.logging.service.request.LoggingServiceRequest;
import com.barclays.mcfe.logging.common.LoggerBody;
import com.barclays.mcfe.logging.common.LoggerEventDetails;
import com.barclays.mcfe.logging.common.LoggerHeader;
import com.barclays.mcfe.logging.common.LoggerMCFE;
import com.barclays.mcfe.logging.common.Mcfe;

public class RemoteLoggingDAOImpl implements LoggingDAO {

    JMSLogSender jmsLogSender;

    private static final Logger LOGGER = Logger.getLogger(RemoteLoggingDAOImpl.class);

    @Override
    public void log(LoggingServiceRequest request) {
	LOGGER.debug("Entering log() [RemoteLoggingDAOImpl] ");
	Context context = request.getContext();
	LoggerMCFE loggerMCFE = new LoggerMCFE();

	try {
	    Mcfe mcfe = new Mcfe();
	    LoggerBody body = new LoggerBody();
	    LoggerHeader header = new LoggerHeader();
	    LoggerEventDetails details = new LoggerEventDetails();

	    if (context != null) {

		String user = context.getMobileUserId();
		String customer = context.getCustomerId();
		String sessionId = context.getSessionId();
		LOGGER.debug("user :" + user + " ,customer :" + customer);
		if (user != null && !"".equals(user))
		    header.setUserId(user);
		else
		    header.setUserId("CFW");
		if (customer != null)
		    header.setCustomerId(customer);
		if (sessionId != null)
		    header.setSessionId(sessionId);
		if (context.getActivityRefNo() != null)
		    header.setActivityRefNo(context.getActivityRefNo());
		if (context.getBusinessId() != null)
		    header.setBusinessId(context.getBusinessId());
		if (context.getActivityId() != null)
		    header.setActivityId(context.getActivityId());
		if (context.getSystemId() != null)
		    header.setSystemID(context.getSystemId());

	    } else {
		header.setUserId("CFW");
	    }

	    header.setSrcCom("LogSvr");

	    body.setThreadId(String.valueOf(Thread.currentThread().getId()));
	    body.setLogType("Application");
	    body.setSeverity(request.getLevel());

	    details.setEventDetails(request.getMessage());
	    details.setEventName("SOAP_LOGGER");
	    body.setEventDetail(details);
	    body.setLogDateTime(Calendar.getInstance());
	    body.setLogServerName(InetAddress.getLocalHost().getCanonicalHostName());
	    mcfe.setHeader(header);
	    mcfe.setBody(body);
	    loggerMCFE.setMCFE(mcfe);
	    jmsLogSender.send(loggerMCFE);

	} catch (Exception e) {
	    LOGGER.error("catch block Exception [RemoteLoggingDAOImpl]  :", e);

	}

    }

    public JMSLogSender getJmsLogSender() {
	return jmsLogSender;
    }

    public void setJmsLogSender(JMSLogSender jmsLogSender) {
	this.jmsLogSender = jmsLogSender;
    }

}
