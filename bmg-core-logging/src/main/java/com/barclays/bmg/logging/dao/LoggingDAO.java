package com.barclays.bmg.logging.dao;

import com.barclays.bmg.logging.service.request.LoggingServiceRequest;

public interface LoggingDAO {

    public void log(LoggingServiceRequest request);

}
