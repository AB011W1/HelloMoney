package com.barclays.bmg.dao;

import com.barclays.bmg.service.request.ScriptResourceServiceRequest;
import com.barclays.bmg.service.response.ScriptResourceServiceResponse;

public interface ScriptResourceDAO {

    public ScriptResourceServiceResponse getScriptResourceByKey(ScriptResourceServiceRequest request);

}
