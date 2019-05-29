
package com.barclays.bmg.mvc.controller.pesalink;


import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;

import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.Amount;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.Charge;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.TransactionDTO;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.payments.MakeBillPaymentOperation;
import com.barclays.bmg.operation.request.billpayment.MakeBillPaymentOperationRequest;
import com.barclays.bmg.operation.response.billpayment.MakeBillPaymentOperationResponse;
import com.barclays.bmg.utils.BMBCommonUtility;

public class KitsOutwardPaymentController extends BMBAbstractCommandController{

	private static final Logger LOGGER = Logger.getLogger(KitsOutwardPaymentController.class);
	 private String activityId;

	private BMBMultipleResponseJSONBuilder bmbJSONBuilder;
	private MakeBillPaymentOperation kitsOutwardPaymentOperation;


    @Override
    protected BMBBaseResponseModel handle1(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)
	    throws Exception {

    	logger.info("Entry KitsOutwardPaymentController ");

    	MakeBillPaymentOperationRequest kitsOutwardPaymentOperationRequest = makeRequest(request);

     	MakeBillPaymentOperationResponse kitsOutwardPaymentOperationResponse=kitsOutwardPaymentOperation.makeBillPayment(kitsOutwardPaymentOperationRequest);


    	return (BMBBaseResponseModel) bmbJSONBuilder.createMultipleJSONResponse(kitsOutwardPaymentOperationResponse);
    }

    /**
     * This method makeRequest has the purpose to construct the operation request.
     *
     * @param HttpRequest
     * @return RetrieveindividualCustCardListOperationRequest
     */
    private MakeBillPaymentOperationRequest makeRequest(HttpServletRequest request) {

		MakeBillPaymentOperationRequest kitsOutwardPaymentOperationRequest=new MakeBillPaymentOperationRequest();

		Context context = createContext(request);
		context.getContextMap().put("debitAccount", (String)request.getParameter("debitAccount"));
    	context.getContextMap().put("txnReason", (String)request.getParameter("txnReason"));
    	context.getContextMap().put("txnAmount", (String)request.getParameter("txnAmount"));
    	context.getContextMap().put("selectedBank", (String)request.getParameter("selectedBank"));
    	context.getContextMap().put("selectedBankSortCode", (String)request.getParameter("selectedBankSortCode"));
    	context.getContextMap().put("receipientAccountNo", (String)request.getParameter("receipientAccountNo"));
    	context.setActivityId(request.getParameter("activityId"));
    	context.setActivityRefNo(BMBCommonUtility.generateRefNo());
		context.setOpCde(request.getParameter("opCde"));

		//Set the fields for MakeBillPaymentRequest - CPB 09/05
		Charge chargeDTO = null;
		if(request.getParameter("CpbMakeBillPaymentFields")!=null && request.getParameter("CpbMakeBillPaymentFields").equals("setCpbFields") && context.getBusinessId().equals("KEBRB")){
			chargeDTO = new Charge();
			Double cpbChargeAmount = Double.parseDouble(request.getParameter("CpbChargeAmount"));
			chargeDTO.setCpbChargeAmount(cpbChargeAmount);
			chargeDTO.setFeeGLAccount((String)request.getParameter("CpbFeeGLAccount"));
			Double cpbTaxAmount = Double.parseDouble(request.getParameter("CpbTaxAmount"));
			chargeDTO.setTaxAmount(cpbTaxAmount);
			chargeDTO.setTaxGLAccount((String)request.getParameter("CpbTaxGLAccount"));
			chargeDTO.setChargeNarration((String)request.getParameter("CpbChargeNarration"));
			chargeDTO.setExciseDutyNarration((String)request.getParameter("CpbExciseDutyNarration"));
			chargeDTO.setTypeCode((String)request.getParameter("CpbtypeCode"));
			chargeDTO.setValue((String)request.getParameter("CpbValue"));
			chargeDTO.setCpbMakeBillPaymentFlag("setBillPayFields");

		}else if(request.getParameter("CpbMakeBillPaymentFields")!=null && request.getParameter("CpbMakeBillPaymentFields").equals("xelerateOffline")){
			chargeDTO = new Charge();
			Double cpbChargeAmount = Double.parseDouble(request.getParameter("CpbChargeAmount"));
			chargeDTO.setCpbChargeAmount(cpbChargeAmount);
			Double cpbTaxAmount = Double.parseDouble(request.getParameter("CpbTaxAmount"));
			chargeDTO.setTaxAmount(cpbTaxAmount);
			chargeDTO.setCpbMakeBillPaymentFlag("xelerateOffline");
		}

    	kitsOutwardPaymentOperationRequest.setContext(context);

    	TransactionDTO transactionDTO=new TransactionDTO();
    	//txn amount and currency
    	Amount amount=new Amount();
    	amount.setAmount(BigDecimal.valueOf(Double.valueOf(request.getParameter("txnAmount"))));
    	amount.setCurrency("KES");
    	transactionDTO.setTxnAmt(amount);

    	//Getting account number from correlation Id
    	String actNoCorr = request.getParameter("debitAccount");
    	String accountNumber=getAccountNumber(actNoCorr, request, BMGProcessConstants.OWN_AND_INTERNAL_FUND_TRANSFER);

    	CustomerAccountDTO sourceAcct = new CustomerAccountDTO();
    	sourceAcct.setAccountNumber(accountNumber);
    	sourceAcct.setCurrency("KES");
    	transactionDTO.setSourceAcct(sourceAcct);


    	transactionDTO.setTxnNot("Pesa Link Transaction");

    	//BeneficiaryDTO  DTO setup
    	 BeneficiaryDTO beneficiaryDTO = new BeneficiaryDTO();
    	 //Updated on 30/10/2016 G01022861
         beneficiaryDTO.setBillerCategoryName(BMGProcessConstants.KITS_BILLER_ORGANIGATIONCODE_CATEGORYID);
         beneficiaryDTO.setBillerId(BMGProcessConstants.KITS_BILLER_ORGANIGATIONCODE_BILLERID);
         //Ended 03/10/2016
         beneficiaryDTO.setBillRefNo((String)request.getParameter("receipientAccountNo"));
         beneficiaryDTO.setExternalBillerId("500");
         beneficiaryDTO.setDestinationBankName((String)request.getParameter("selectedBank"));
         beneficiaryDTO.setDestinationBankSortCode((String)request.getParameter("selectedBankSortCode"));
         beneficiaryDTO.setNarration((String)request.getParameter("txnReason"));

         if("KITS_PTA_BILLPAY".equals(request.getParameter("activityId")))
         {
	     	transactionDTO.setTxnType("PSTA");
	     	beneficiaryDTO.setDestinationAccountNumber((String)request.getParameter("receipientAccountNo"));

         }else if("KITS_PTP_BILLPAY".equals(request.getParameter("activityId")))
         {
        	transactionDTO.setTxnType("PSTP");
        	// Append Country ISD CODE on 03/10/2016 G01022861
//        	beneficiaryDTO.setDestinationAccountNumber((context.getContextMap().get(SMSConstants.SMS_ISD_CODE))+
//        			(String)request.getParameter("receipientMobileNo"));
        	String mobileNo=(String)request.getParameter("receipientMobileNo");
        	if(mobileNo.startsWith("0"))
        		mobileNo= mobileNo.substring(1);
        	beneficiaryDTO.setDestinationAccountNumber(BMGProcessConstants.KITS_BILLER_ORGANIGATIONCODE_ISDCODE+
        			mobileNo);
        	// END Country ISD CODE
         }
         transactionDTO.setBeneficiaryDTO(beneficiaryDTO);

         //CPB set chargeDTO 10/05
         transactionDTO.setChargeDTO(chargeDTO);

         kitsOutwardPaymentOperationRequest.setTransactionDTO(transactionDTO);

    	return kitsOutwardPaymentOperationRequest;
    }



	public MakeBillPaymentOperation getKitsOutwardPaymentOperation() {
		return kitsOutwardPaymentOperation;
	}

	public void setKitsOutwardPaymentOperation(
			MakeBillPaymentOperation kitsOutwardPaymentOperation) {
		this.kitsOutwardPaymentOperation = kitsOutwardPaymentOperation;
	}


	public BMBMultipleResponseJSONBuilder getBmbJSONBuilder() {
		return bmbJSONBuilder;
	}

	public void setBmbJSONBuilder(BMBMultipleResponseJSONBuilder bmbJSONBuilder) {
		this.bmbJSONBuilder = bmbJSONBuilder;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	 @Override
	    protected String getActivityId(Object command) {
		// TODO Auto-generated method stub
		return null;
	    }


}
