package com.barclays.bmg.service;

import com.barclays.bmg.service.request.MessageResourceServiceRequest;
import com.barclays.bmg.service.response.MessageResourceServiceResponse;

public interface MessageResourceService {
    public MessageResourceServiceResponse getMessageDescByKey(MessageResourceServiceRequest request);
}
