package com.barclays.bmg.cashsend.service;

import com.barclays.bmg.cashsend.service.request.CashSendOneTimeExecuteServiceRequest;
import com.barclays.bmg.cashsend.service.response.CashSendOneTimeExecuteServiceResponse;

public interface CashSendOneTimeExecuteService {

    public CashSendOneTimeExecuteServiceResponse executeCashSend(CashSendOneTimeExecuteServiceRequest request);

    public CashSendOneTimeExecuteServiceResponse encryptCashSendPin(CashSendOneTimeExecuteServiceRequest request);

}
