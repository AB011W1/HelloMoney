package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.AccountSummaryReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.AccountSummaryRespAdptOperation;

public class AccountSummaryDAOController implements Controller {

    private AccountSummaryReqAdptOperation accountSummaryReqAdptOperation;

    private TransmissionOperation transmissionOperation;

    private AccountSummaryRespAdptOperation accountSummaryRespAdptOperation;

    @Override
    public Object handleRequest(WorkContext workContext) throws Exception {

	Object obj = accountSummaryReqAdptOperation.adaptRequestForAccountSummary(workContext);

	Object obj1 = transmissionOperation.sendAndReceive(obj);

	Object obj2 = accountSummaryRespAdptOperation.adaptResponseForAllAccountSummary(workContext, obj1);

	return obj2;
    }

    public AccountSummaryReqAdptOperation getAccountSummaryReqAdptOperation() {
	return accountSummaryReqAdptOperation;
    }

    public void setAccountSummaryReqAdptOperation(AccountSummaryReqAdptOperation accountSummaryReqAdptOperation) {
	this.accountSummaryReqAdptOperation = accountSummaryReqAdptOperation;
    }

    public TransmissionOperation getTransmissionOperation() {
	return transmissionOperation;
    }

    public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
	this.transmissionOperation = transmissionOperation;
    }

    public AccountSummaryRespAdptOperation getAccountSummaryRespAdptOperation() {
	return accountSummaryRespAdptOperation;
    }

    public void setAccountSummaryRespAdptOperation(AccountSummaryRespAdptOperation accountSummaryRespAdptOperation) {
	this.accountSummaryRespAdptOperation = accountSummaryRespAdptOperation;
    }

}
