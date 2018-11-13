package com.barclays.bmg.service.impl;

import com.barclays.bmg.dao.ScriptResourceDAO;
import com.barclays.bmg.service.ScriptResourceService;
import com.barclays.bmg.service.request.ScriptResourceServiceRequest;
import com.barclays.bmg.service.response.ScriptResourceServiceResponse;

public class ScriptResourceServiceImpl implements ScriptResourceService {

    ScriptResourceDAO scriptResourceDAO;

    public ScriptResourceServiceResponse getScriptResourceByKey(ScriptResourceServiceRequest request) {

	ScriptResourceServiceResponse response = scriptResourceDAO.getScriptResourceByKey(request);
	return response;
    }

    public ScriptResourceDAO getScriptResourceDAO() {
	return scriptResourceDAO;
    }

    public void setScriptResourceDAO(ScriptResourceDAO scriptResourceDAO) {
	this.scriptResourceDAO = scriptResourceDAO;
    }

}
