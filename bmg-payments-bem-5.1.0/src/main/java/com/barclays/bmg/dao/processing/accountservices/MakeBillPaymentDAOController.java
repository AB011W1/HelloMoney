package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bem.BEMServiceHeader.BEMReqHeader;
import com.barclays.bem.MakeBillPayment.MakeBillPaymentRequest;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.MakePayBillReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.MakePayBillRespAdptOperation;
import com.barclays.bmg.service.response.PayBillServiceResponse;

public class MakeBillPaymentDAOController implements Controller {

    private MakePayBillReqAdptOperation makePayBillReqAdptOperation;
    private TransmissionOperation transmissionOperation;
    private MakePayBillRespAdptOperation makePayBillRespAdptOperation;

    @Override
    public Object handleRequest(WorkContext workContext) throws Exception {

	Object obj = makePayBillReqAdptOperation.adaptRequestForPayBill(workContext);
	Object obj1 = transmissionOperation.sendAndReceive(obj);
	Object obj2 = makePayBillRespAdptOperation.adaptResponse(workContext, obj1);

	PayBillServiceResponse payBillServiceResponse = (PayBillServiceResponse) obj2;
	if (payBillServiceResponse.isSuccess()) {

	    MakeBillPaymentRequest makeBillPaymentRequest = (MakeBillPaymentRequest) obj;
	    BEMReqHeader reqHeader = makeBillPaymentRequest.getRequestHeader();

	    payBillServiceResponse.setTxnRefNo(reqHeader.getServiceContext().getConsumerUniqueRefNo());
	    payBillServiceResponse.setTxnTime(reqHeader.getServiceContext().getServiceDateTime().getTime());
	}

	return obj2;
    }

    public MakePayBillReqAdptOperation getMakePayBillReqAdptOperation() {
	return makePayBillReqAdptOperation;
    }

    public void setMakePayBillReqAdptOperation(MakePayBillReqAdptOperation makePayBillReqAdptOperation) {
	this.makePayBillReqAdptOperation = makePayBillReqAdptOperation;
    }

    public TransmissionOperation getTransmissionOperation() {
	return transmissionOperation;
    }

    public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
	this.transmissionOperation = transmissionOperation;
    }

    public MakePayBillRespAdptOperation getMakePayBillRespAdptOperation() {
	return makePayBillRespAdptOperation;
    }

    public void setMakePayBillRespAdptOperation(MakePayBillRespAdptOperation makePayBillRespAdptOperation) {
	this.makePayBillRespAdptOperation = makePayBillRespAdptOperation;
    }

}
