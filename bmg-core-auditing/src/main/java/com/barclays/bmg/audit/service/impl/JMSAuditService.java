package com.barclays.bmg.audit.service.impl;

import com.barclays.bmg.audit.dao.AuditDAO;
import com.barclays.bmg.audit.service.AuditService;
import com.barclays.bmg.audit.service.request.AuditServiceRequest;

public class JMSAuditService implements AuditService {

    AuditDAO auditDAO;

    @Override
    public void auditTransaction(AuditServiceRequest request) {

	auditDAO.auditTransaction(request);

    }

    public AuditDAO getAuditDAO() {
	return auditDAO;
    }

    public void setAuditDAO(AuditDAO auditDAO) {
	this.auditDAO = auditDAO;
    }

}
