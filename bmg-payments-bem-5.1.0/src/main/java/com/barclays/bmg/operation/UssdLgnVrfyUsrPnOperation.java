/*
 * Copyright (c) 2013 Barclays Bank Plc, All Rights Reserved.
 *
 * This code is confidential to Barclays Bank Plc and shall not be disclosed
 * outside the Bank without the prior written permission of the Director of
 * CIO
 *
 * In the event that such disclosure is permitted the code shall not be copied
 * or distributed other than on a need-to-know basis and any recipients may be
 * required to sign a confidentiality undertaking in favour of Barclays Bank
 * Plc.
 */
package com.barclays.bmg.operation;

import com.barclays.bmg.audit.annotation.AuditSupport;
import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.operation.request.UssdLgnVrfyUsrPnOperationRequest;
import com.barclays.bmg.operation.response.UssdLgnVrfyUsrPnOperationResponse;
import com.barclays.bmg.service.UssdLgnVrfyUsrPnService;
import com.barclays.bmg.service.request.UssdLgnVrfyUsrPnServiceRequest;
import com.barclays.bmg.service.response.UssdLgnVrfyUsrPnServiceResponse;

/**
 * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>UssdLgnVrfyUsrPnOperation.java</b> </br> Description is <b>V 1.2</b> </br> Updated Date is <b>June 13, 2013</b> </br>
 * ******************************************************
 * 
 * @author e20037686 </br> * The Class UssdLgnVrfyUsrPnOperation created using JRE 1.6.0_10
 */
public class UssdLgnVrfyUsrPnOperation extends BMBCommonOperation {
    /**
     * The instance variable named "ussdLgnVrfyUsrPnService" is created.
     */
    private UssdLgnVrfyUsrPnService ussdLgnVrfyUsrPnService;

    /**
     * The method is written for Verify user with pin.
     * 
     * @param request
     *            the request
     * @return the UssdLgnVrfyUsrPnOperationResponse's object
     */
    @AuditSupport(auditType = AuditConstant.AUDIT_TYPE_TRANSACTION, activityState = AuditConstant.SRC_COM_EXECUTOR, serviceDescription = "SD_LOGIN", stepId = "1", activityType = "auditLogin")
    public UssdLgnVrfyUsrPnOperationResponse verifyUserWithPin(UssdLgnVrfyUsrPnOperationRequest request) {
	UssdLgnVrfyUsrPnOperationResponse ussdLgnVrfyUsrPnOperationResponse = new UssdLgnVrfyUsrPnOperationResponse();
	Context context = request.getContext();
	loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID, context.getActivityId());

	UssdLgnVrfyUsrPnServiceRequest ussdLgnVrfyUsrPnServiceRequest = new UssdLgnVrfyUsrPnServiceRequest();
	ussdLgnVrfyUsrPnServiceRequest.setContext(context);
	ussdLgnVrfyUsrPnServiceRequest.setUsrNam(request.getUsrNam());
	ussdLgnVrfyUsrPnServiceRequest.setPass(request.getPass());

	UssdLgnVrfyUsrPnServiceResponse serRes = ussdLgnVrfyUsrPnService.verifyUserWithPin(ussdLgnVrfyUsrPnServiceRequest);
	if (serRes != null) {
	    ussdLgnVrfyUsrPnOperationResponse.setContext(serRes.getContext());
	    ussdLgnVrfyUsrPnOperationResponse.setResCde(serRes.getResCde());
	    ussdLgnVrfyUsrPnOperationResponse.setResMsg(serRes.getResMsg());

	    boolean respSuccessFlg = serRes.isSuccess();
	    if (respSuccessFlg) {
		ussdLgnVrfyUsrPnOperationResponse.setSuccess(respSuccessFlg);
	    } else {

		getMessage(ussdLgnVrfyUsrPnOperationResponse);
		ussdLgnVrfyUsrPnOperationResponse.setSuccess(false);
	    }
	} else {
	    ussdLgnVrfyUsrPnOperationResponse.setSuccess(false);
	}
	return ussdLgnVrfyUsrPnOperationResponse;
    }

    /**
     * Gets the ussd lgn vrfy usr pn service.
     * 
     * @return the UssdLgnVrfyUsrPnService
     */
    public UssdLgnVrfyUsrPnService getUssdLgnVrfyUsrPnService() {
	return ussdLgnVrfyUsrPnService;
    }

    /**
     * Sets values for UssdLgnVrfyUsrPnService.
     * 
     * @param ussdLgnVrfyUsrPnService
     *            the ussd lgn vrfy usr pn service
     */
    public void setUssdLgnVrfyUsrPnService(UssdLgnVrfyUsrPnService ussdLgnVrfyUsrPnService) {
	this.ussdLgnVrfyUsrPnService = ussdLgnVrfyUsrPnService;
    }
}