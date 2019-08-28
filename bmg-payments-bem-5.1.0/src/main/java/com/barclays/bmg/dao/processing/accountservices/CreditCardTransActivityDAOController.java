package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.creditcard.CreditCardTransActivityReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.creditcard.CreditCardTransActivityRespAdptOperation;

public class CreditCardTransActivityDAOController implements Controller {

    private CreditCardTransActivityReqAdptOperation creditCardTransActivityReqAdptOperation;

    private TransmissionOperation transmissionOperation;

    private CreditCardTransActivityRespAdptOperation creditCardTransActivityRespAdptOperation;

    @Override
    public Object handleRequest(WorkContext workContext) throws Exception {

	Object obj = creditCardTransActivityReqAdptOperation.adaptRequestForCreditCardTransActivity(workContext);

	Object obj1 = transmissionOperation.sendAndReceive(obj);

	Object obj2 = creditCardTransActivityRespAdptOperation.adaptResponseForCreditCardTransActivity(workContext, obj1);

	return obj2;
    }

    public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
	this.transmissionOperation = transmissionOperation;
    }

    public void setCreditCardTransActivityReqAdptOperation(CreditCardTransActivityReqAdptOperation creditCardTransActivityReqAdptOperation) {
	this.creditCardTransActivityReqAdptOperation = creditCardTransActivityReqAdptOperation;
    }

    public void setCreditCardTransActivityRespAdptOperation(CreditCardTransActivityRespAdptOperation creditCardTransActivityRespAdptOperation) {
	this.creditCardTransActivityRespAdptOperation = creditCardTransActivityRespAdptOperation;
    }

}
