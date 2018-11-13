package com.barclays.bmg.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barclays.bmg.dao.SystemParameterDAO;
import com.barclays.bmg.dto.SystemParameterDTO;
import com.barclays.bmg.helper.DisasterRecoveryHelper;
import com.barclays.bmg.service.request.SystemParameterServiceRequest;
import com.barclays.bmg.service.response.SystemParameterListServiceResponse;
import com.barclays.bmg.service.response.SystemParameterServiceResponse;

public class SystemParameterDAOImpl extends BaseDAOImpl implements SystemParameterDAO {

    @SuppressWarnings("unchecked")
    public SystemParameterListServiceResponse getSysParamByActivityId(SystemParameterServiceRequest request) {

	SystemParameterListServiceResponse systemParameterServiceResponse = new SystemParameterListServiceResponse();

	SystemParameterDTO spDTO = request.getSystemParameterDTO();

	Map<String, String> params = new HashMap<String, String>();

	params.put("systemId", spDTO.getSystemId());
	params.put("businessId", spDTO.getBusinessId());
	params.put("activityId", spDTO.getActivityId());

	List<SystemParameterDTO> spList = this.queryForList("findSystemParameterByActivityId", params);

	systemParameterServiceResponse.setSystemParameterDTOList(spList);

	return systemParameterServiceResponse;
    }

    public SystemParameterServiceResponse getSystemParameter(SystemParameterServiceRequest request) {

	SystemParameterDTO spDTO = request.getSystemParameterDTO();

	SystemParameterServiceResponse response = new SystemParameterServiceResponse();

	Map<String, String> params = new HashMap<String, String>();

	params.put("systemId", spDTO.getSystemId());
	params.put("businessId", spDTO.getBusinessId());
	params.put("activityId", spDTO.getActivityId());

	/* ------------- Disaster Recovery Implementation START ---------------- */
	String paramId = spDTO.getParameterId();
	String tempStr = paramId;

	paramId = DisasterRecoveryHelper.handleDrParmeter(paramId);
	if (null != tempStr && !tempStr.equals(paramId)) { // is DR
	    params.put("parameterId", paramId); // --- Overwrite the paramId, if DR is enable
	} else {
	    params.put("parameterId", spDTO.getParameterId());
	}

	/* ------------- Disaster Recovery Implementation END ---------------- */

	spDTO = (SystemParameterDTO) this.queryForObject("getSystemParameter", params);

	response.setSystemParameterDTO(spDTO);

	return response;

    }
    public SystemParameterServiceResponse getStatusParameter(SystemParameterServiceRequest request){
    	SystemParameterDTO spDTO = request.getSystemParameterDTO();

    	SystemParameterServiceResponse response = new SystemParameterServiceResponse();

    	Map<String, String> params = new HashMap<String, String>();

    	params.put("systemId", spDTO.getSystemId());
    	params.put("businessId", spDTO.getBusinessId());
    	params.put("paramId", spDTO.getParameterId());

    	spDTO = (SystemParameterDTO) this.queryForObject("findFunctionalityStatusFlag", params);

    	response.setSystemParameterDTO(spDTO);

    	return response;
    }
}
