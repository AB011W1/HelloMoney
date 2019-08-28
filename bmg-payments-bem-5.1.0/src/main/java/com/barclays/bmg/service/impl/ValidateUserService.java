package com.barclays.bmg.service.impl;

import com.barclays.bmg.dao.ValidateUserDAO;
import com.barclays.bmg.service.request.ValidateUserServiceRequest;
import com.barclays.bmg.service.response.ValidateUserServiceResponse;

/**
 * @author E20041929
 * 
 */
public class ValidateUserService implements com.barclays.bmg.service.ValidateUserService {
    private ValidateUserDAO validateUserDAO;

    public ValidateUserDAO getValidateUserDAO() {
	return validateUserDAO;
    }

    public void setValidateUserDAO(ValidateUserDAO validateUserDAO) {
	this.validateUserDAO = validateUserDAO;
    }

    /*
     * @AuditSupport(auditType = AuditConstant.AUDIT_TYPE_TRANSACTION, activityState = AuditConstant.SRC_COM_SSC, serviceDescription =
     * "SQA_AUTH_DESC", stepId = "3", activityType="auth")
     */public ValidateUserServiceResponse validateUserIgnoreCase(ValidateUserServiceRequest request) {
	ValidateUserServiceResponse validateUserServiceResponse = validateUserDAO.validateUserIgnoreCase(request);
	return validateUserServiceResponse;
    }

}
