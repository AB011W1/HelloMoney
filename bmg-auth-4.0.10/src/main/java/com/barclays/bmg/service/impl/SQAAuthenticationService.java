package com.barclays.bmg.service.impl;

import com.barclays.bmg.audit.annotation.AuditSupport;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.dao.AuthenticationDAO;
import com.barclays.bmg.service.AuthenticationService;
import com.barclays.bmg.service.request.AuthenticationServiceRequest;
import com.barclays.bmg.service.response.AuthenticationServiceResponse;

/**
 * SQA Authentication Service
 * 
 * @author e20026338
 * 
 */
public class SQAAuthenticationService implements AuthenticationService {

    AuthenticationDAO authenticationDAO;

    /**
     * Verify SQA challenge
     * 
     * @param request
     * @return
     */
    @AuditSupport(auditType = AuditConstant.AUDIT_TYPE_TRANSACTION, activityState = AuditConstant.SRC_COM_SSC, serviceDescription = "SQA_AUTH_DESC", stepId = "3", activityType = "auth")
    public AuthenticationServiceResponse verify(AuthenticationServiceRequest request) {
	AuthenticationServiceResponse authenticationServiceResponse = authenticationDAO.verify(request);
	return authenticationServiceResponse;
    }

    public AuthenticationDAO getAuthenticationDAO() {
	return authenticationDAO;
    }

    public void setAuthenticationDAO(AuthenticationDAO authenticationDAO) {
	this.authenticationDAO = authenticationDAO;
    }

}
