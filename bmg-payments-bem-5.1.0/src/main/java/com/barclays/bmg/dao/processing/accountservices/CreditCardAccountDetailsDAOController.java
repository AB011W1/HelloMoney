package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.creditcard.CreditCardAccountDetailsReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.creditcard.CreditCardAccountDetailsRespAdptOperation;

public class CreditCardAccountDetailsDAOController implements Controller {

    private CreditCardAccountDetailsReqAdptOperation creditCardAccountDetailsReqAdptOperation;

    private TransmissionOperation transmissionOperation;

    private CreditCardAccountDetailsRespAdptOperation creditCardAccountDetailsRespAdptOperation;

    @Override
    public Object handleRequest(WorkContext workContext) throws Exception {

	Object obj = creditCardAccountDetailsReqAdptOperation.adaptRequestForCreditCardAccountDetails(workContext);

	Object obj1 = transmissionOperation.sendAndReceive(obj);

	Object obj2 = creditCardAccountDetailsRespAdptOperation.adaptResponseForCreditCardAccountDetails(workContext, obj1);

	return obj2;
    }

    public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
	this.transmissionOperation = transmissionOperation;
    }

    public void setCreditCardAccountDetailsReqAdptOperation(CreditCardAccountDetailsReqAdptOperation creditCardAccountDetailsReqAdptOperation) {
	this.creditCardAccountDetailsReqAdptOperation = creditCardAccountDetailsReqAdptOperation;
    }

    public void setCreditCardAccountDetailsRespAdptOperation(CreditCardAccountDetailsRespAdptOperation creditCardAccountDetailsRespAdptOperation) {
	this.creditCardAccountDetailsRespAdptOperation = creditCardAccountDetailsRespAdptOperation;
    }

}
