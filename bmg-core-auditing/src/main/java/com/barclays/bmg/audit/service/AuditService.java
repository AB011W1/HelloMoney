package com.barclays.bmg.audit.service;

import com.barclays.bmg.audit.service.request.AuditServiceRequest;

public interface AuditService {

    public void auditTransaction(AuditServiceRequest request);

}
