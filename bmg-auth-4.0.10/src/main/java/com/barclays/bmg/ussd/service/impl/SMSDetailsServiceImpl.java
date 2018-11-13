package com.barclays.bmg.ussd.service.impl;

import com.barclays.bmg.audit.annotation.AuditSupport;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.ussd.auth.service.request.SMSDetailsServiceRequest;
import com.barclays.bmg.ussd.auth.service.response.SMSDetailsServiceResponse;
import com.barclays.bmg.ussd.dao.SMSDetailsDAO;
import com.barclays.bmg.ussd.service.SMSDetailsService;

public class SMSDetailsServiceImpl implements SMSDetailsService {

    private SMSDetailsDAO smsDetailsDAO;

    public SMSDetailsDAO getSmsDetailsDAO() {
	return smsDetailsDAO;
    }

    public void setSmsDetailsDAO(SMSDetailsDAO smsDetailsDAO) {
	this.smsDetailsDAO = smsDetailsDAO;
    }

    @Override
    @AuditSupport(auditType = AuditConstant.AUDIT_TYPE_TRANSACTION, activityState = AuditConstant.SRC_COM_SSC, serviceDescription = "SD_SMS_DETAILS", stepId = "1", activityType = "smsDetails")
    public SMSDetailsServiceResponse getSMSDetails(SMSDetailsServiceRequest serviceRequest) {

	return smsDetailsDAO.getSMSDetails(serviceRequest);
    }

}
