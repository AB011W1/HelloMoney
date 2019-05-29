package com.barclays.ussd.bmg.gepgbillers.request;

import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.bmg.dto.SystemParameterDTO;
import com.barclays.bmg.service.SystemParameterService;
import com.barclays.bmg.service.request.SystemParameterServiceRequest;
import com.barclays.bmg.service.response.SystemParameterServiceResponse;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDExceptions;

/**
 *
 * @author G01156975
 * The Class builds the request object to be shared with BMG.
 *
 */
public class GePGControlNumberRequestBuilder implements
		BmgBaseRequestBuilder {

	@Autowired
    private SystemParameterService systemParameterService;

	/**
	 * @param requestBuilderParamsDTO
     * @return USSDBaseRequest
	 */
	@Override
	public USSDBaseRequest getRequestObject(
			RequestBuilderParamsDTO requestBuilderParamsDTO)
			throws USSDNonBlockingException, USSDBlockingException {

		USSDSessionManagement ussdSessionMgmt=requestBuilderParamsDTO.getUssdSessionMgmt();
		String business_id=ussdSessionMgmt.getBusinessId();

		SystemParameterServiceRequest sysrequest=new SystemParameterServiceRequest();
		SystemParameterDTO dto=new SystemParameterDTO();
		dto.setBusinessId(business_id);
		dto.setActivityId("COMMON");
		dto.setParameterId("gePGEnabled");
		dto.setSystemId("UB");
		sysrequest.setSystemParameterDTO(dto);
		SystemParameterServiceResponse response= systemParameterService.getSystemParameter(sysrequest);

		if (null == response || (null != response && null != response.getSystemParameterDTO()
						&& "N".equalsIgnoreCase(response.getSystemParameterDTO().getParameterValue()))) {
		    throw new USSDNonBlockingException(USSDExceptions.USSD_SERVICE_DISABLED.getBmgCode());
		}
		return null;
	}
}