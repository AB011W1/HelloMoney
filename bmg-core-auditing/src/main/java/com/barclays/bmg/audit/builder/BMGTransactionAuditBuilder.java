package com.barclays.bmg.audit.builder;

import com.barclays.bmg.audit.dto.TransactionAuditDTO;

public interface BMGTransactionAuditBuilder {

    TransactionAuditDTO buildFromRequest(Object args[], Object result);

    TransactionAuditDTO buildFromResponse(Object args[], Object result);

}
