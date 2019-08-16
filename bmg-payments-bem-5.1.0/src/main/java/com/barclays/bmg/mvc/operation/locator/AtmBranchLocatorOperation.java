package com.barclays.bmg.mvc.operation.locator;

import com.barclays.bmg.audit.annotation.AuditSupport;
import com.barclays.bmg.constants.AccountErrorCodeConstant;
import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.mvc.operation.locator.request.AtmBranchLocatorOperationRequest;
import com.barclays.bmg.mvc.operation.locator.response.AtmBranchLocatorOperationResponse;
import com.barclays.bmg.operation.BMBPaymentsOperation;
import com.barclays.bmg.service.AtmBranchService;
import com.barclays.bmg.service.request.AtmBranchServiceRequest;
import com.barclays.bmg.service.response.AtmBranchServiceResponse;

public class AtmBranchLocatorOperation extends BMBPaymentsOperation {

    private AtmBranchService atmBranchService;

    @AuditSupport(auditType = AuditConstant.AUDIT_TYPE_TRANSACTION, activityState = AuditConstant.SRC_COM_SSC, serviceDescription = "SD_BRANCH_LOCATOR", stepId = "3", activityType = "auditBranchLocator")
    public AtmBranchLocatorOperationResponse retrieveBranchList(AtmBranchLocatorOperationRequest request) {
	Context context = request.getContext();
	AtmBranchLocatorOperationResponse atmBranchLocatorOperationResponse = new AtmBranchLocatorOperationResponse();
	loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);
	atmBranchLocatorOperationResponse.setContext(context);

	AtmBranchServiceRequest atmBranchServicerequest = new AtmBranchServiceRequest();
	atmBranchServicerequest.setBusinessId(request.getBusinessId());
	atmBranchServicerequest.setActivityId(request.getActivityId());
	atmBranchServicerequest.setCity(request.getCity());
	atmBranchServicerequest.setArea(request.getArea());

	AtmBranchServiceResponse atmBranchServiceResponse = atmBranchService.retrieveBranchList(atmBranchServicerequest);

	if (atmBranchServiceResponse != null) {

	    if (atmBranchServiceResponse.getBranchList() != null && atmBranchServiceResponse.getBranchList().size() > 0) {
		atmBranchLocatorOperationResponse.setBranchList(atmBranchServiceResponse.getBranchList());
		atmBranchLocatorOperationResponse.setSuccess(true);
		atmBranchLocatorOperationResponse.setResCde(AccountErrorCodeConstant.SUCCESS_CODE);
	    } else {
		atmBranchLocatorOperationResponse.setBranchList(null);
		atmBranchLocatorOperationResponse.setSuccess(false);
		atmBranchLocatorOperationResponse.setResCde(AccountErrorCodeConstant.NO_BRANCH_FOUND);
	    }

	}

	return atmBranchLocatorOperationResponse;

    }

    @AuditSupport(auditType = AuditConstant.AUDIT_TYPE_TRANSACTION, activityState = AuditConstant.SRC_COM_SSC, serviceDescription = "SD_ATM_LOCATOR", stepId = "3", activityType = "auditAtmLocator")
    public AtmBranchLocatorOperationResponse retrieveATMList(AtmBranchLocatorOperationRequest request) {
	Context context = request.getContext();
	AtmBranchLocatorOperationResponse atmBranchLocatorOperationResponse = new AtmBranchLocatorOperationResponse();
	loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);
	atmBranchLocatorOperationResponse.setContext(context);

	AtmBranchServiceRequest atmBranchServicerequest = new AtmBranchServiceRequest();
	atmBranchServicerequest.setBusinessId(request.getBusinessId());
	atmBranchServicerequest.setActivityId(request.getActivityId());
	atmBranchServicerequest.setCity(request.getCity());
	atmBranchServicerequest.setArea(request.getArea());

	AtmBranchServiceResponse atmBranchServiceResponse = atmBranchService.retrieveATMList(atmBranchServicerequest);

	if (atmBranchServiceResponse != null) {

	    if (atmBranchServiceResponse.getAtmList() != null && atmBranchServiceResponse.getAtmList().size() > 0) {
		atmBranchLocatorOperationResponse.setAtmList(atmBranchServiceResponse.getAtmList());
		atmBranchLocatorOperationResponse.setSuccess(true);
		atmBranchLocatorOperationResponse.setResCde(AccountErrorCodeConstant.SUCCESS_CODE);
	    } else {
		atmBranchLocatorOperationResponse.setBranchList(null);
		atmBranchLocatorOperationResponse.setSuccess(false);
		atmBranchLocatorOperationResponse.setResCde(AccountErrorCodeConstant.NO_ATM_FOUND);
	    }

	}

	return atmBranchLocatorOperationResponse;

    }

    public AtmBranchLocatorOperationResponse retrieveAtmBranchList(AtmBranchLocatorOperationRequest request) {

	AtmBranchLocatorOperationResponse atmBranchLocatorOperationResponse = new AtmBranchLocatorOperationResponse();

	AtmBranchServiceRequest atmBranchServicerequest = new AtmBranchServiceRequest();
	atmBranchServicerequest.setBusinessId(request.getBusinessId());
	atmBranchServicerequest.setActivityId(request.getActivityId());
	atmBranchServicerequest.setCity(request.getCity());
	atmBranchServicerequest.setArea(request.getArea());

	AtmBranchServiceResponse atmBranchServiceResponse = atmBranchService.retrieveAtmBranchList(atmBranchServicerequest);

	if (atmBranchServiceResponse != null) {

	    if (atmBranchServiceResponse.getBranchList() != null && atmBranchServiceResponse.getBranchList().size() > 0) {
		atmBranchLocatorOperationResponse.setBranchList(atmBranchServiceResponse.getBranchList());
		atmBranchLocatorOperationResponse.setSuccess(true);
		atmBranchLocatorOperationResponse.setResCde(AccountErrorCodeConstant.SUCCESS_CODE);
	    } else if (atmBranchServiceResponse.getAtmList() != null && atmBranchServiceResponse.getAtmList().size() > 0) {
		atmBranchLocatorOperationResponse.setAtmList(atmBranchServiceResponse.getAtmList());
		atmBranchLocatorOperationResponse.setSuccess(true);
		atmBranchLocatorOperationResponse.setResCde(AccountErrorCodeConstant.SUCCESS_CODE);
	    } else {
		atmBranchLocatorOperationResponse.setBranchList(null);
		atmBranchLocatorOperationResponse.setSuccess(false);
		atmBranchLocatorOperationResponse.setResCde(AccountErrorCodeConstant.NO_ATM_BRANCH_FOUND);
	    }

	}

	return atmBranchLocatorOperationResponse;

    }

    public AtmBranchService getAtmBranchService() {
	return atmBranchService;
    }

    public void setAtmBranchService(AtmBranchService atmBranchService) {
	this.atmBranchService = atmBranchService;
    }

}
