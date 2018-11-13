package com.barclays.bmg.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barclays.bmg.context.Context;
import com.barclays.bmg.dao.ScriptResourceDAO;
import com.barclays.bmg.dto.ScriptResDTO;
import com.barclays.bmg.service.request.ScriptResourceServiceRequest;
import com.barclays.bmg.service.response.ScriptResourceServiceResponse;

public class ScriptResourceDAOImpl extends BaseDAOImpl implements ScriptResourceDAO {

    public ScriptResourceServiceResponse getScriptResourceByKey(ScriptResourceServiceRequest request) {

	Context context = request.getContext();
	String scriptKey = request.getScriptKey();
	ScriptResourceServiceResponse response = new ScriptResourceServiceResponse();

	Map<String, String> map = new HashMap<String, String>();
	map.put("systemId", context.getSystemId());
	map.put("businessId", context.getBusinessId());
	map.put("langId", context.getLanguageId());
	map.put("scriptKey", scriptKey);
	List resultList = super.queryForList("getScriptResourceByKey", map);

	if (resultList.size() == 1) {
	    response.setScriptValue(((ScriptResDTO) resultList.get(0)).getScriptValue());

	}
	StringBuffer buffer = new StringBuffer();
	for (Object obj : resultList) {
	    ScriptResDTO dto = (ScriptResDTO) obj;
	    if (dto.getScriptValue() != null) {
		buffer.append(dto.getScriptValue());
	    }

	}

	if (buffer.length() > 0) {
	    response.setScriptValue(buffer.toString());
	}

	return response;
    }

}
