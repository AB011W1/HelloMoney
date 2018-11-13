package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.ViewBillPaymentDetailsReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.ViewBillPaymentDetailsResAdptOperation;

public class ViewBillPaymentDetailsDAOController implements Controller {

    private ViewBillPaymentDetailsReqAdptOperation viewBillPaymentDetailsReqAdptOperation;
    private ViewBillPaymentDetailsResAdptOperation viewBillPaymentDetailsResAdptOperation;
    private TransmissionOperation transmissionOperation;

    public void setViewBillPaymentDetailsReqAdptOperation(ViewBillPaymentDetailsReqAdptOperation viewBillPaymentDetailsReqAdptOperation) {
	this.viewBillPaymentDetailsReqAdptOperation = viewBillPaymentDetailsReqAdptOperation;
    }

    public void setViewBillPaymentDetailsResAdptOperation(ViewBillPaymentDetailsResAdptOperation viewBillPaymentDetailsResAdptOperation) {
	this.viewBillPaymentDetailsResAdptOperation = viewBillPaymentDetailsResAdptOperation;
    }

    public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
	this.transmissionOperation = transmissionOperation;
    }

    @Override
    public Object handleRequest(WorkContext workContext) throws Exception {
	// TODO Auto-generated method stub
	Object obj = viewBillPaymentDetailsReqAdptOperation.adaptRequestforViewBillPaymentDetails(workContext);
	Object obj1 = transmissionOperation.sendAndReceive(obj);
	Object obj2 = viewBillPaymentDetailsResAdptOperation.adaptResponse(workContext, obj1);
	return obj2;
    }

}
