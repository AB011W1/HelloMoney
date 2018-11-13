package com.barclays.bmg.operation.pesalink;


import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.operation.BMBCommonOperation;
import com.barclays.bmg.operation.request.pesalink.CreateIndividualCustomerOperationRequest;
import com.barclays.bmg.operation.request.pesalink.KitsOutwardPaymentOperationRequest;
import com.barclays.bmg.operation.response.pesalink.CreateIndividualCustomerOperationResponse;
import com.barclays.bmg.operation.response.pesalink.KitsOutwardPaymentOperationResponse;
import com.barclays.bmg.service.PayBillService;
import com.barclays.bmg.service.pesalink.CreateIndividualCustomerService;
import com.barclays.bmg.service.request.pesalink.CreateIndividualCustomerServiceRequest;
import com.barclays.bmg.service.request.pesalink.KitsOutwardPaymentServiceRequest;
import com.barclays.bmg.service.response.pesalink.CreateIndividualCustomerServiceResponse;
import com.barclays.bmg.service.response.pesalink.KitsOutwardPaymentServiceResponse;


public class KitsOutwardPaymentOperation extends BMBCommonOperation{


	private PayBillService payBillServicekits;


	//@AuditSupport(auditType = AuditConstant.AUDIT_TYPE_TRANSACTION, activityState = AuditConstant.SRC_COM_SSC, serviceDescription = "SD_CCD_LINK", stepId = "1", activityType = "auditCCDLink")
	public KitsOutwardPaymentOperationResponse kitsBillPayment(KitsOutwardPaymentOperationRequest kitsOutwardPaymentOperationRequest) {

		KitsOutwardPaymentOperationResponse kitsOutwardPaymentOperationResponse = new KitsOutwardPaymentOperationResponse();
		KitsOutwardPaymentServiceRequest kitsOutwardPaymentServiceRequest = new KitsOutwardPaymentServiceRequest();
		kitsOutwardPaymentServiceRequest.setDebitAccount(kitsOutwardPaymentOperationRequest.getDebitAccount());
		kitsOutwardPaymentServiceRequest.setReceipientAccountNo(kitsOutwardPaymentOperationRequest.getReceipientAccountNo());
		kitsOutwardPaymentServiceRequest.setSelectedBank(kitsOutwardPaymentOperationRequest.getSelectedBank());
		kitsOutwardPaymentServiceRequest.setSelectedBankSortCode(kitsOutwardPaymentOperationRequest.getSelectedBankSortCode());
		kitsOutwardPaymentServiceRequest.setTxnAmount(kitsOutwardPaymentOperationRequest.getTxnAmount());
		kitsOutwardPaymentServiceRequest.setTxnReason(kitsOutwardPaymentOperationRequest.getTxnReason());
		//Loading Request Parameter
		loadParameters(kitsOutwardPaymentOperationRequest.getContext(), ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);
		kitsOutwardPaymentServiceRequest.setContext(kitsOutwardPaymentOperationRequest.getContext());
	//	KitsOutwardPaymentServiceResponse kitsOutwardPaymentServiceResponse = payBillServicekits.payBill(kitsOutwardPaymentServiceRequest);

	/*	if(kitsOutwardPaymentServiceResponse != null){

			if(kitsOutwardPaymentServiceResponse.isSuccess())
			{
				kitsOutwardPaymentOperationResponse.setTxnRefNo(kitsOutwardPaymentServiceResponse.getTxnRefNo());
			}
			kitsOutwardPaymentOperationResponse.setSuccess(kitsOutwardPaymentServiceResponse.isSuccess());
			kitsOutwardPaymentOperationResponse.setResCde(kitsOutwardPaymentServiceResponse.getResCde());
			kitsOutwardPaymentOperationResponse.setResMsg(kitsOutwardPaymentServiceResponse.getResMsg());

		}
		return kitsOutwardPaymentOperationResponse;

*/

		return null;
	}


	public PayBillService getPayBillServicekits() {
		return payBillServicekits;
	}


	public void setPayBillServicekits(PayBillService payBillServicekits) {
		this.payBillServicekits = payBillServicekits;
	}







}
