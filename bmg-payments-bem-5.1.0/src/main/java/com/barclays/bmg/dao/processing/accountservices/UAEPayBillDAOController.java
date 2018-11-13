package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bem.BEMServiceHeader.BEMReqHeader;
import com.barclays.bem.UAEMakeBillPayment.UAEMakeBillPaymentRequest;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.UAEPayBillReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.UAEPayBillRespAdptOperation;
import com.barclays.bmg.service.response.PayBillServiceResponse;

public class UAEPayBillDAOController implements Controller{

	private UAEPayBillReqAdptOperation uaePayBillReqAdptOperation;
	private TransmissionOperation transmissionOperation;
	private UAEPayBillRespAdptOperation uaePayBillRespAdptOperation;


	@Override
	public Object handleRequest(WorkContext workContext) throws Exception {

		Object obj = uaePayBillReqAdptOperation.adaptRequestForPayBill(workContext);
		Object obj1 = transmissionOperation.sendAndReceive(obj);
		Object obj2 = uaePayBillRespAdptOperation.adaptResponse(workContext, obj1);

		PayBillServiceResponse payBillServiceResponse = (PayBillServiceResponse) obj2;
		if(payBillServiceResponse.isSuccess()){

			UAEMakeBillPaymentRequest uaeMakeBillPaymentRequest =
									(UAEMakeBillPaymentRequest) obj;
			BEMReqHeader reqHeader = uaeMakeBillPaymentRequest.getRequestHeader();

			payBillServiceResponse
				.setTxnRefNo(reqHeader.getServiceContext().getConsumerUniqueRefNo());
			payBillServiceResponse
				.setTxnTime(reqHeader.getServiceContext().getServiceDateTime().getTime());
		}

		return obj2;
	}
	public UAEPayBillReqAdptOperation getUaePayBillReqAdptOperation() {
		return uaePayBillReqAdptOperation;
	}
	public void setUaePayBillReqAdptOperation(
			UAEPayBillReqAdptOperation uaePayBillReqAdptOperation) {
		this.uaePayBillReqAdptOperation = uaePayBillReqAdptOperation;
	}
	public TransmissionOperation getTransmissionOperation() {
		return transmissionOperation;
	}
	public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
		this.transmissionOperation = transmissionOperation;
	}
	public UAEPayBillRespAdptOperation getUaePayBillRespAdptOperation() {
		return uaePayBillRespAdptOperation;
	}
	public void setUaePayBillRespAdptOperation(
			UAEPayBillRespAdptOperation uaePayBillRespAdptOperation) {
		this.uaePayBillRespAdptOperation = uaePayBillRespAdptOperation;
	}

}
