package com.barclays.bmg.operation;

import com.barclays.bmg.audit.annotation.AuditSupport;
import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.operation.request.UpdateLanguagePrefOperationRequest;
import com.barclays.bmg.operation.response.UpdateLanguagePrefOperationResponse;
import com.barclays.bmg.service.UpdateLanguagePrefService;
import com.barclays.bmg.service.request.UpdateLanguagePrefServiceRequest;
import com.barclays.bmg.service.response.UpdateLanguagePrefServiceResponse;

/**
 * @author BTCI
 * 
 */
public class UpdateLanguagePrefOperation extends BMBCommonOperation {

    private UpdateLanguagePrefService updateLanguagePrefService;

    /**
     * @param updateLanguagePrefService
     */
    public void setUpdateLanguagePrefService(UpdateLanguagePrefService updateLanguagePrefService) {
	this.updateLanguagePrefService = updateLanguagePrefService;
    }

    /**
     * @param UpdateLanguagePrefServiceRequest
     * @return UpdateLanguagePrefServiceResponse
     */
    @AuditSupport(auditType = AuditConstant.AUDIT_TYPE_TRANSACTION, activityState = AuditConstant.SRC_COM_SSC, serviceDescription = "CHG_LANG_PREF_LOG", stepId = "1", activityType = "updateLanguagePreference")
    public UpdateLanguagePrefOperationResponse updateLanguagePref(UpdateLanguagePrefOperationRequest request) {
	Context context = request.getContext();
	loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);

	UpdateLanguagePrefServiceRequest updateLanguagePrefServiceRequest = new UpdateLanguagePrefServiceRequest();
	updateLanguagePrefServiceRequest.setContext(context);
	updateLanguagePrefServiceRequest.setPrefLang(request.getPrefLang());
	UpdateLanguagePrefServiceResponse response = updateLanguagePrefService.updateLanguagePref(updateLanguagePrefServiceRequest);
	UpdateLanguagePrefOperationResponse updateLanguagePrefOperationResponse = new UpdateLanguagePrefOperationResponse();
	updateLanguagePrefOperationResponse.setSuccess(response.isSuccess());
	if (!updateLanguagePrefOperationResponse.isSuccess()) {
	    getMessage(response);
	}
	updateLanguagePrefOperationResponse.setContext(context);
	return updateLanguagePrefOperationResponse;
    }

}
