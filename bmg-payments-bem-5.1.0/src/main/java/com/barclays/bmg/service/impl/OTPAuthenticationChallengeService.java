package com.barclays.bmg.service.impl;

import com.barclays.bmg.audit.annotation.AuditSupport;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.dao.AuthenticationChallengeDAO;
import com.barclays.bmg.service.AuthenticationChallengeService;
import com.barclays.bmg.service.request.AuthenticationServiceRequest;
import com.barclays.bmg.service.response.AuthenticationServiceResponse;

/**
 * OTP Authentication Challenge Service
 * 
 * @author e20026338
 * 
 */
public class OTPAuthenticationChallengeService implements AuthenticationChallengeService {

    AuthenticationChallengeDAO authenticationChallengeDAO;

    /**
     * Retrieve OTP challenge
     * 
     * @param request
     * @return
     */
    @AuditSupport(auditType = AuditConstant.AUDIT_TYPE_TRANSACTION, activityState = AuditConstant.SRC_COM_SSC, serviceDescription = "CHALLENGE_OTP_DESC", stepId = "2", activityType = "auth")
    public AuthenticationServiceResponse retrieveChallenge(AuthenticationServiceRequest request) {

	AuthenticationServiceResponse authenticationServiceResponse = authenticationChallengeDAO.retrieveChallenge(request);

	return authenticationServiceResponse;
    }

    public AuthenticationChallengeDAO getAuthenticationChallengeDAO() {
	return authenticationChallengeDAO;
    }

    public void setAuthenticationChallengeDAO(AuthenticationChallengeDAO authenticationChallengeDAO) {
	this.authenticationChallengeDAO = authenticationChallengeDAO;
    }

}
