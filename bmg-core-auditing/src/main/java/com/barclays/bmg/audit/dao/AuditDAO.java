package com.barclays.bmg.audit.dao;

import com.barclays.bmg.audit.service.request.AuditServiceRequest;

public interface AuditDAO {

    public void auditTransaction(AuditServiceRequest request);

}
