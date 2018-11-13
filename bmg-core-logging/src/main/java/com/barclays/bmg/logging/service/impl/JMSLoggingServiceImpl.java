package com.barclays.bmg.logging.service.impl;

import com.barclays.bmg.logging.dao.LoggingDAO;
import com.barclays.bmg.logging.service.LoggingService;
import com.barclays.bmg.logging.service.request.LoggingServiceRequest;

public class JMSLoggingServiceImpl implements LoggingService {

    LoggingDAO loggingDAO;

    @Override
    public void log(LoggingServiceRequest request) {

	loggingDAO.log(request);

    }

    public LoggingDAO getLoggingDAO() {
	return loggingDAO;
    }

    public void setLoggingDAO(LoggingDAO loggingDAO) {
	this.loggingDAO = loggingDAO;
    }

}
