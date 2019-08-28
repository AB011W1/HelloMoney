package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.creditcard.CreditCardUnbilledTransReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.creditcard.CreditCardUnbilledTransRespAdptOperation;

public class CreditCardUnbilledTransDAOController implements Controller {

    private CreditCardUnbilledTransReqAdptOperation creditCardUnbilledTransReqAdptOperation;

    private TransmissionOperation transmissionOperation;

    private CreditCardUnbilledTransRespAdptOperation creditCardUnbilledTransRespAdptOperation;

    @Override
    public Object handleRequest(WorkContext workContext) throws Exception {

	Object obj = creditCardUnbilledTransReqAdptOperation.adaptRequestForCreditCardUnbilledTrans(workContext);

	Object obj1 = transmissionOperation.sendAndReceive(obj);

	Object obj2 = creditCardUnbilledTransRespAdptOperation.adaptResponseForCreditCardUnbilledTrans(workContext, obj1);

	return obj2;
    }

    public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
	this.transmissionOperation = transmissionOperation;
    }

    public void setCreditCardUnbilledTransReqAdptOperation(CreditCardUnbilledTransReqAdptOperation creditCardUnbilledTransReqAdptOperation) {
	this.creditCardUnbilledTransReqAdptOperation = creditCardUnbilledTransReqAdptOperation;
    }

    public void setCreditCardUnbilledTransRespAdptOperation(CreditCardUnbilledTransRespAdptOperation creditCardUnbilledTransRespAdptOperation) {
	this.creditCardUnbilledTransRespAdptOperation = creditCardUnbilledTransRespAdptOperation;
    }

}
