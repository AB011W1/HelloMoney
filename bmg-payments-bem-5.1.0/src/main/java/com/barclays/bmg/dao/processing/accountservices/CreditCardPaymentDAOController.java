package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bem.BEMServiceHeader.BEMReqHeader;
import com.barclays.bem.MakeCreditCardPayment.MakeCreditCardPaymentRequest;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.CreditCardPayReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.CreditCardPayRespAdptOperation;
import com.barclays.bmg.service.response.PayBillServiceResponse;

public class CreditCardPaymentDAOController implements Controller {

	private CreditCardPayReqAdptOperation creditCardPayReqAdptOperation;
	private TransmissionOperation transmissionOperation;
	private CreditCardPayRespAdptOperation creditCardPayRespAdptOperation;
	@Override
	public Object handleRequest(WorkContext workContext) throws Exception {

		Object obj = creditCardPayReqAdptOperation.adaptRequestForCCP(workContext);
		Object obj1 = transmissionOperation.sendAndReceive(obj);
		Object obj2 = creditCardPayRespAdptOperation.adaptResponse(workContext, obj1);
		PayBillServiceResponse payBillServiceResponse = (PayBillServiceResponse) obj2;
		if(payBillServiceResponse.isSuccess()){

			MakeCreditCardPaymentRequest makeCreditCardPaymentRequest =
									(MakeCreditCardPaymentRequest) obj;
			BEMReqHeader reqHeader = makeCreditCardPaymentRequest.getRequestHeader();

			payBillServiceResponse
				.setTxnRefNo(reqHeader.getServiceContext().getConsumerUniqueRefNo());
			payBillServiceResponse
				.setTxnTime(reqHeader.getServiceContext().getServiceDateTime().getTime());
		}
		return payBillServiceResponse;
	}
	public CreditCardPayReqAdptOperation getCreditCardPayReqAdptOperation() {
		return creditCardPayReqAdptOperation;
	}
	public void setCreditCardPayReqAdptOperation(
			CreditCardPayReqAdptOperation creditCardPayReqAdptOperation) {
		this.creditCardPayReqAdptOperation = creditCardPayReqAdptOperation;
	}
	public TransmissionOperation getTransmissionOperation() {
		return transmissionOperation;
	}
	public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
		this.transmissionOperation = transmissionOperation;
	}
	public CreditCardPayRespAdptOperation getCreditCardPayRespAdptOperation() {
		return creditCardPayRespAdptOperation;
	}
	public void setCreditCardPayRespAdptOperation(
			CreditCardPayRespAdptOperation creditCardPayRespAdptOperation) {
		this.creditCardPayRespAdptOperation = creditCardPayRespAdptOperation;
	}

}
