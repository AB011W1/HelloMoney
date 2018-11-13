package com.barclays.bmg.dao.processing.accountservices.ssa;

import com.barclays.bem.BEMServiceHeader.BEMReqHeader;
import com.barclays.bem.SSAMakeBillPayment.SSAMakeBillPaymentRequest;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.ssa.PayBillReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.ssa.PayBillRespAdptOperation;
import com.barclays.bmg.service.response.PayBillServiceResponse;

public class PayBillDAOController implements Controller {

    private PayBillReqAdptOperation payBillReqAdptOperation;
    private TransmissionOperation transmissionOperation;
    private PayBillRespAdptOperation payBillRespAdptOperation;

    @Override
    public Object handleRequest(WorkContext workContext) throws Exception {

	Object obj = payBillReqAdptOperation.adaptRequestForPayBill(workContext);
	Object obj1 = transmissionOperation.sendAndReceive(obj);
	Object obj2 = payBillRespAdptOperation.adaptResponse(workContext, obj1);

	PayBillServiceResponse payBillServiceResponse = (PayBillServiceResponse) obj2;

	if (payBillServiceResponse.isSuccess()) {
	    SSAMakeBillPaymentRequest ssaMakeBillPaymentRequest = (SSAMakeBillPaymentRequest) obj;
	    BEMReqHeader reqHeader = ssaMakeBillPaymentRequest.getRequestHeader();
	    payBillServiceResponse.setTxnRefNo(reqHeader.getServiceContext().getConsumerUniqueRefNo());
	    payBillServiceResponse.setTxnTime(reqHeader.getServiceContext().getServiceDateTime().getTime());
	}

	return payBillServiceResponse;
    }

    public PayBillReqAdptOperation getPayBillReqAdptOperation() {
	return payBillReqAdptOperation;
    }

    public void setPayBillReqAdptOperation(PayBillReqAdptOperation payBillReqAdptOperation) {
	this.payBillReqAdptOperation = payBillReqAdptOperation;
    }

    public TransmissionOperation getTransmissionOperation() {
	return transmissionOperation;
    }

    public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
	this.transmissionOperation = transmissionOperation;
    }

    public PayBillRespAdptOperation getPayBillRespAdptOperation() {
	return payBillRespAdptOperation;
    }

    public void setPayBillRespAdptOperation(PayBillRespAdptOperation payBillRespAdptOperation) {
	this.payBillRespAdptOperation = payBillRespAdptOperation;
    }

}
