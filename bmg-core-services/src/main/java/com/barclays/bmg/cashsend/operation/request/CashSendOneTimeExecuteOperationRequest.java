package com.barclays.bmg.cashsend.operation.request;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.CashSendRequestDTO;

public class CashSendOneTimeExecuteOperationRequest extends RequestContext {

    private CashSendRequestDTO cashSendRequestDTO;

    public CashSendRequestDTO getCashSendRequestDTO() {
	return cashSendRequestDTO;
    }

    public void setCashSendRequestDTO(CashSendRequestDTO cashSendRequestDTO) {
	this.cashSendRequestDTO = cashSendRequestDTO;
    }

}
