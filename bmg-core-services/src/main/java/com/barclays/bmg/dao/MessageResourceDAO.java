package com.barclays.bmg.dao;

import com.barclays.bmg.service.request.MessageResourceServiceRequest;
import com.barclays.bmg.service.response.MessageResourceServiceResponse;

public interface MessageResourceDAO {
    public MessageResourceServiceResponse getMessageDescByKey(MessageResourceServiceRequest request);
}
