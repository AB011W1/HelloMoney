package com.barclays.bmg.operation.payments;

import org.springframework.transaction.annotation.Transactional;

import com.barclays.bmg.audit.annotation.AuditSupport;
import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.constants.FundTransferResponseCodeConstants;
import com.barclays.bmg.constants.SystemIdConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.Amount;
import com.barclays.bmg.dto.CustomerDTO;
import com.barclays.bmg.dto.TransactionDTO;
import com.barclays.bmg.operation.request.billpayment.MakeBillPaymentOperationRequest;
import com.barclays.bmg.operation.response.billpayment.MakeBillPaymentOperationResponse;
import com.barclays.bmg.service.PayBillService;
import com.barclays.bmg.service.RetreiveChargeDetailsService;
import com.barclays.bmg.service.RetrieveIndCustBySCVIDService;
import com.barclays.bmg.service.product.request.ListValueResServiceRequest;
import com.barclays.bmg.service.product.response.ListValueResByGroupServiceResponse;
import com.barclays.bmg.service.request.PayBillServiceRequest;
import com.barclays.bmg.service.request.RetreiveChargeDetailsServiceRequest;
import com.barclays.bmg.service.request.RetrieveIndCustBySCVIDServiceRequest;
import com.barclays.bmg.service.response.PayBillServiceResponse;
import com.barclays.bmg.service.response.RetreiveChargeDetailsServiceResponse;
import com.barclays.bmg.service.response.RetrieveIndCustBySCVIDServiceResponse;
import com.barclays.bmg.ussd.service.SMSDetailsService;

public class OtherBarclayBillPaymentOperation extends BMBPaymentsOperation {
    private PayBillService payBillService;
    private RetreiveChargeDetailsService retreiveChargeDetailsService;
    private RetrieveIndCustBySCVIDService retrieveIndCustBySCVIDService;

    public RetrieveIndCustBySCVIDService getRetrieveIndCustBySCVIDService() {
	return retrieveIndCustBySCVIDService;
    }

    public void setRetrieveIndCustBySCVIDService(RetrieveIndCustBySCVIDService retrieveIndCustBySCVIDService) {
	this.retrieveIndCustBySCVIDService = retrieveIndCustBySCVIDService;
    }

    /**
     * The instance variable for smsDetailsService of type SMSDetailsService
     */
    private SMSDetailsService smsDetailsService;

    public SMSDetailsService getSmsDetailsService() {
	return smsDetailsService;
    }

    public void setSmsDetailsService(SMSDetailsService smsDetailsService) {
	this.smsDetailsService = smsDetailsService;
    }

    @AuditSupport(auditType = AuditConstant.AUDIT_TYPE_TRANSACTION, activityState = AuditConstant.SRC_COM_SSC, serviceDescription = "SD_OTHER_BARCLAY_PAY_BILL", stepId = "3", activityType = "auditOtherBarclayBillPayment")
    public MakeBillPaymentOperationResponse makeBillPayment(MakeBillPaymentOperationRequest request) {
	Context context = request.getContext();
	MakeBillPaymentOperationResponse response = new MakeBillPaymentOperationResponse();
	response.setContext(context);
	loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);
	TransactionDTO transactionDTO = request.getTransactionDTO();

	String systemId = request.getContext().getSystemId();
	if (systemId != null && SystemIdConstants.UB.equals(systemId)) {
	    transactionDTO.setScndLvlAuthTyp("NON"); // !!!!!!!!!!!!!!!!!!! mocked
	}

	if (checkTransactionAbility(request)) {
	    if (transactionDTO.isScndLvlauthReq() && !BillPaymentConstants.AUTH_TYPE_NON.equals(transactionDTO.getScndLvlAuthTyp())) {
		response.setScndLvlAuthReq(true);
		return response;
	    }

	    PayBillServiceRequest payBillServiceRequest = createServiceRequest(request);
	    makeBillPayment(payBillServiceRequest, transactionDTO, response);
	} else {
	    response.setSuccess(false);
	    response.setResCde(FundTransferResponseCodeConstants.FT_TRANSACTIONABILITY_ERROR);
	}
	return response;
    }

    protected void getTransactionFeeAndCharges(TransactionDTO transactionDTO, PayBillServiceRequest payBillServiceRequest) {

	String taskCode = getSysParamValue(payBillServiceRequest.getContext(), "chargeDetailTaskCode", "PMT_BP_BILLPAY_PAYEE");

	Amount transacFee = null;

	if (taskCode != null && taskCode.length() > 0) {

	    RetreiveChargeDetailsServiceRequest retreiveChargeDetailsServiceRequest = new RetreiveChargeDetailsServiceRequest();
	    retreiveChargeDetailsServiceRequest.setChargeDetailTaskCode(taskCode);
	    retreiveChargeDetailsServiceRequest.setContext(payBillServiceRequest.getContext());
	    retreiveChargeDetailsServiceRequest.setFrmAcct(transactionDTO.getSourceAcct().getAccountNumber());
	    retreiveChargeDetailsServiceRequest.setTxnAmt(transactionDTO.getTxnAmt().getAmount());
	    retreiveChargeDetailsServiceRequest.setCurrency(transactionDTO.getTxnAmt().getCurrency());
	    retreiveChargeDetailsServiceRequest.setCustSegmentCode("MASS");

	    RetreiveChargeDetailsServiceResponse retreiveChargeDetailsServiceResponse = retreiveChargeDetailsService
		    .retreiveChargeDetails(retreiveChargeDetailsServiceRequest);

	    transacFee = retreiveChargeDetailsServiceResponse.getTotalFeeAmount();

	}
	payBillServiceRequest.setTransactionFee(transacFee);
    }

    @Transactional
    private void makeBillPayment(PayBillServiceRequest payBillServiceRequest, TransactionDTO transactionDTO, MakeBillPaymentOperationResponse response) {

	retrieveCustomerFullNameAndSetInContext(payBillServiceRequest);
	PayBillServiceResponse payBillServiceResponse = payBillService.payBill(payBillServiceRequest);

	if (payBillServiceResponse != null && payBillServiceResponse.isSuccess()) {

	    response.setTrnDate(payBillServiceResponse.getTxnTime());
	    response.setTrnRef(payBillServiceResponse.getTxnRefNo());
	    response.setPymntRefNo(payBillServiceResponse.getPymntRefNo());
	    response.setRcptNo(payBillServiceResponse.getRcptNo());
	    response.setTokenNo(payBillServiceResponse.getTokenNo());
	    response.setVoucherNo(payBillServiceResponse.getVoucherNo());
	    response.setTxnMsg(payBillServiceResponse.getTxnMsg());

	    upgradeTransactionLimit(payBillServiceRequest, transactionDTO.getTxnAmtInLCY());
	}
    }

    private PayBillServiceRequest createServiceRequest(MakeBillPaymentOperationRequest request) {
	TransactionDTO transactionDTO = request.getTransactionDTO();
	PayBillServiceRequest payBillServiceRequest = new PayBillServiceRequest();
	payBillServiceRequest.setBeneficiaryDTO(transactionDTO.getBeneficiaryDTO());
	payBillServiceRequest.setBillAmount(transactionDTO.getTxnAmt().getAmount());
	payBillServiceRequest.setCurrency(transactionDTO.getTxnAmt().getCurrency());
	payBillServiceRequest.setFromAccount(transactionDTO.getSourceAcct());
	payBillServiceRequest.setContext(request.getContext());
	payBillServiceRequest.setTxnNote(transactionDTO.getTxnNot());
	payBillServiceRequest.setBillPayTxnTyp(BillPaymentConstants.TXN_TYP);
	payBillServiceRequest.setOutBalAmt(transactionDTO.getOutBalAmt());
	payBillServiceRequest.setAmtInLCY(transactionDTO.getTxnAmt().getAmount());
	payBillServiceRequest.setExternalBillerId(transactionDTO.getBeneficiaryDTO().getExternalBillerId());
	payBillServiceRequest.setOrdCode(transactionDTO.getOrgCode());
	return payBillServiceRequest;
    }

    /**
     * This method getSMSEvent has the purpose to get event, for which SMS has to be sent.
     * 
     * @param MakeBillPaymentOperationRequest
     * @return String
     */
    @SuppressWarnings("unused")
    private String getSMSEvent(MakeBillPaymentOperationRequest makeBillPaymentOperationRequest, String smsKey) {

	ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
	listValueResServiceRequest.setContext(makeBillPaymentOperationRequest.getContext());
	listValueResServiceRequest.setListValueKey(smsKey);
	ListValueResByGroupServiceResponse listValueResByGroupServiceResponse = listValueResService.getListValueByKey(listValueResServiceRequest);

	String event = listValueResByGroupServiceResponse.getListValueCahceDTO().get(0).getLabel();
	return event;

    }

    /**
     * This method getSMSPriority has the purpose to get the priority of the SMS to be sent.
     * 
     * @param MakeBillPaymentOperationRequest
     * @return String
     */
    @SuppressWarnings("unused")
    private String getSMSPriority(MakeBillPaymentOperationRequest makeBillPaymentOperationRequest, String smsKey) {

	ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
	listValueResServiceRequest.setContext(makeBillPaymentOperationRequest.getContext());
	listValueResServiceRequest.setListValueKey(smsKey);
	ListValueResByGroupServiceResponse listValueResByGroupServiceResponse = listValueResService.getListValueByKey(listValueResServiceRequest);

	String priority = listValueResByGroupServiceResponse.getListValueCahceDTO().get(0).getLabel();
	return priority;

    }

    public PayBillService getPayBillService() {
	return payBillService;
    }

    public void setPayBillService(PayBillService payBillService) {
	this.payBillService = payBillService;
    }

    public RetreiveChargeDetailsService getRetreiveChargeDetailsService() {
	return retreiveChargeDetailsService;
    }

    public void setRetreiveChargeDetailsService(RetreiveChargeDetailsService retreiveChargeDetailsService) {
	this.retreiveChargeDetailsService = retreiveChargeDetailsService;
    }

    private void retrieveCustomerFullNameAndSetInContext(PayBillServiceRequest payBillServiceRequest) {
	// code for getting customer info by scvid
	RetrieveIndCustBySCVIDServiceRequest retrieveIndCustBySCVIDServiceRequest = new RetrieveIndCustBySCVIDServiceRequest();
	CustomerDTO customer = new CustomerDTO();
	customer.setCustomerID(payBillServiceRequest.getContext().getCustomerId());
	retrieveIndCustBySCVIDServiceRequest.setCustomer(customer);
	retrieveIndCustBySCVIDServiceRequest.setContext(payBillServiceRequest.getContext());
	RetrieveIndCustBySCVIDServiceResponse retrieveIndCustBySCVIDServiceResponse = retrieveIndCustBySCVIDService
		.retrieveIndCustBySCVID(retrieveIndCustBySCVIDServiceRequest);
	if (retrieveIndCustBySCVIDServiceResponse != null) {
	    payBillServiceRequest.getContext().setFullName(retrieveIndCustBySCVIDServiceResponse.getContext().getFullName());
	}

    }
}
