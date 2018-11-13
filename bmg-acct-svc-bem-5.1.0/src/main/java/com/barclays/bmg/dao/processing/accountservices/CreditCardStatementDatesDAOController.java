package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.creditcard.CreditCardStatementDatesReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.creditcard.CreditCardStatementDatesRespAdptOperation;

public class CreditCardStatementDatesDAOController implements Controller {

    private CreditCardStatementDatesReqAdptOperation creditCardStatementDatesReqAdptOperation;

    private TransmissionOperation transmissionOperation;

    private CreditCardStatementDatesRespAdptOperation creditCardStatementDatesRespAdptOperation;

    @Override
    public Object handleRequest(WorkContext workContext) throws Exception {

	Object obj = creditCardStatementDatesReqAdptOperation.adaptRequestForCreditCardStatementDates(workContext);

	Object obj1 = transmissionOperation.sendAndReceive(obj);

	Object obj2 = creditCardStatementDatesRespAdptOperation.adaptResponseForCreditCardStatementDates(workContext, obj1);

	return obj2;
    }

    public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
	this.transmissionOperation = transmissionOperation;
    }

    public void setCreditCardStatementDatesReqAdptOperation(CreditCardStatementDatesReqAdptOperation creditCardStatementDatesReqAdptOperation) {
	this.creditCardStatementDatesReqAdptOperation = creditCardStatementDatesReqAdptOperation;
    }

    public void setCreditCardStatementDatesRespAdptOperation(CreditCardStatementDatesRespAdptOperation creditCardStatementDatesRespAdptOperation) {
	this.creditCardStatementDatesRespAdptOperation = creditCardStatementDatesRespAdptOperation;
    }

}
