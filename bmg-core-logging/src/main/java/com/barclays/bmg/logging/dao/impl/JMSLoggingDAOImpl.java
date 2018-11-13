package com.barclays.bmg.logging.dao.impl;

import java.util.Calendar;

import com.barclays.bmg.context.Context;
import com.barclays.bmg.logging.dao.LoggingDAO;
import com.barclays.bmg.logging.jms.JMSLogSender;
import com.barclays.bmg.logging.service.request.LoggingServiceRequest;
import com.barclays.mcfe.logging.common.LoggerBody;
import com.barclays.mcfe.logging.common.LoggerEventDetails;
import com.barclays.mcfe.logging.common.LoggerHeader;
import com.barclays.mcfe.logging.common.LoggerMCFE;
import com.barclays.mcfe.logging.common.Mcfe;

public class JMSLoggingDAOImpl implements LoggingDAO {

    JMSLogSender jmsLogSender;

    @Override
    public void log(LoggingServiceRequest request) {

	Context context = request.getContext();
	String level = request.getLevel();
	String message = request.getMessage();
	LoggerMCFE loggerMCFE = new LoggerMCFE();
	try {
	    Mcfe mcfe = new Mcfe();
	    LoggerBody body = new LoggerBody();
	    LoggerHeader header = new LoggerHeader();
	    LoggerEventDetails details = new LoggerEventDetails();

	    try {
		if (context != null) {
		    String user = context.getUserId();
		    String customer = context.getCustomerId();
		    String sessionId = context.getSessionId();
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
	    } catch (NullPointerException nullpointerexception) {
	    }
	    header.setSrcCom("LogSvr");

	    body.setThreadId(String.valueOf(Thread.currentThread().getId()));
	    body.setLogType("REMOTE");
	    body.setSeverity(level);

	    details.setEventDetails(message);
	    // details.setEventName(componentId);
	    body.setEventDetail(details);
	    body.setLogDateTime(Calendar.getInstance());
	    // body.setLogServerName(serverName);
	    mcfe.setHeader(header);
	    mcfe.setBody(body);
	    loggerMCFE.setMCFE(mcfe);
	    jmsLogSender.send(loggerMCFE);
	} catch (Exception e) {
	    
	}

    }

    public JMSLogSender getJmsLogSender() {
	return jmsLogSender;
    }

    public void setJmsLogSender(JMSLogSender jmsLogSender) {
	this.jmsLogSender = jmsLogSender;
    }

}
