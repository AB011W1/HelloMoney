package com.barclays.bmg.service;

import com.barclays.bmg.service.request.ScriptResourceServiceRequest;
import com.barclays.bmg.service.response.ScriptResourceServiceResponse;

public interface ScriptResourceService {

    public ScriptResourceServiceResponse getScriptResourceByKey(ScriptResourceServiceRequest request);

}
