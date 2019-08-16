package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.investment.StructuredNoteDetailsReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.investment.StructuredNoteDetailsRespAdptOperation;

public class StructuredNoteDetailsDAOController implements Controller {

    private StructuredNoteDetailsReqAdptOperation strutNoteDetailsReqAdptOperation;

    private TransmissionOperation investmentTransmissionOperation;

    private StructuredNoteDetailsRespAdptOperation strutNoteDetailsRespAdptOperation;

    @Override
    public Object handleRequest(WorkContext workContext) throws Exception {

	Object obj = strutNoteDetailsReqAdptOperation.adaptRequestForStructuredNoteDetails(workContext);

	Object obj1 = investmentTransmissionOperation.sendAndReceive(obj);

	Object obj2 = strutNoteDetailsRespAdptOperation.adaptResponseForStructuredNoteDetails(workContext, obj1);

	return obj2;
    }

    public void setInvestmentTransmissionOperation(TransmissionOperation investmentTransmissionOperation) {
	this.investmentTransmissionOperation = investmentTransmissionOperation;
    }

    public void setStrutNoteDetailsReqAdptOperation(StructuredNoteDetailsReqAdptOperation strutNoteDetailsReqAdptOperation) {
	this.strutNoteDetailsReqAdptOperation = strutNoteDetailsReqAdptOperation;
    }

    public void setStrutNoteDetailsRespAdptOperation(StructuredNoteDetailsRespAdptOperation strutNoteDetailsRespAdptOperation) {
	this.strutNoteDetailsRespAdptOperation = strutNoteDetailsRespAdptOperation;
    }

}
