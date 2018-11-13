package com.barclays.bmg.logging.service;

import com.barclays.bmg.logging.service.request.LoggingServiceRequest;

public interface LoggingService {

    public void log(LoggingServiceRequest request);

}
