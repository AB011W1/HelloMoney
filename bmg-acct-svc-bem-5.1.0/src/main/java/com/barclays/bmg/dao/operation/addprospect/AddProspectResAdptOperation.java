package com.barclays.bmg.dao.operation.addprospect;

import com.barclays.bem.AddProspect.AddProspectResponse;
import com.barclays.bem.BEMServiceHeader.BEMResHeader;
import com.barclays.bmg.constants.AddProspectErrorCodeConstant;
import com.barclays.bmg.constants.ErrorCodeConstant;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dao.operation.accountservices.AbstractResAdptOperationAcct;
import com.barclays.bmg.service.request.AddProspectServiceRequest;
import com.barclays.bmg.service.response.AddProspectServiceResponse;

public class AddProspectResAdptOperation extends AbstractResAdptOperationAcct {

    public AddProspectServiceResponse adaptResponse(WorkContext workContext, Object obj) throws Exception {

	AddProspectServiceResponse addProspectServiceResponse = new AddProspectServiceResponse();
	DAOContext daoContext = (DAOContext) workContext;
	Object[] args = daoContext.getArguments();

	AddProspectServiceRequest addProspectServiceRequest = (AddProspectServiceRequest) args[0];
	addProspectServiceResponse.setContext(addProspectServiceRequest.getContext());

	AddProspectResponse addProspectResponse = (AddProspectResponse) obj;
	String respCode = checkServiceResponseHeader(addProspectResponse.getResponseHeader(), addProspectServiceResponse);

	if (respCode.equals(ErrorCodeConstant.SUCCESS_CODE) || respCode.equals(ErrorCodeConstant.PARTIAL_SUCCESS_CODE)) {
	    addProspectServiceResponse.setProspectId(addProspectResponse.getAddProspectEntities().getProspectId());
	    addProspectServiceResponse.setAssignedTo(addProspectResponse.getAddProspectEntities().getAssignedTo());
	    // addProspectServiceResponse.setDuplicateFlag(addProspectResponse.getAddProspectEntities().getDuplicateFlag());
	    addProspectServiceResponse.setSuccess(true);
	    addProspectServiceResponse.setResCde(AddProspectErrorCodeConstant.SUCCESS_CODE);

	} else if (respCode.equals(ErrorCodeConstant.BUSINESS_ERROR)) {
	    addProspectServiceResponse.setSuccess(true);
	    addProspectServiceResponse.setResCde(respCode);
	} else {
	    addProspectServiceResponse.setSuccess(false);
	    addProspectServiceResponse.setResCde(respCode);
	}
	return addProspectServiceResponse;
    }

    private String checkServiceResponseHeader(BEMResHeader resHeader, AddProspectServiceResponse addProspectServiceResponse) throws Exception {
	String returnCode = "";
	if (resHeader.getServiceResStatus() != null) {
	    String resCode = resHeader.getServiceResStatus().getServiceResCode();
	    if (AddProspectErrorCodeConstant.SUCCESS_CODE.equals(resCode) || AddProspectErrorCodeConstant.PARTIAL_SUCCESS_CODE.equals(resCode)) {
		returnCode = resCode;
	    } else if (ErrorCodeConstant.BUSINESS_ERROR.equals(resCode)) {
		returnCode = resCode;
	    }
	}
	return returnCode;
    }
}
