package com.barclays.bmg.mvc.controller.accountsummary;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.TransactionDTO;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.accountdetails.AtAGlanceCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.accounts.AccountSummaryOperation;
import com.barclays.bmg.operation.accounts.request.AccountSummaryOperationRequest;
import com.barclays.bmg.operation.accounts.response.AccountSummaryOperationResponse;
import com.barclays.bmg.operation.beneficiary.MergeBillerInfoOperation;
import com.barclays.bmg.operation.beneficiary.RetrievePayeeInfoOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.MergeBillerInfoOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.RetrievePayeeInfoOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.MergeBillerInfoOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrievePayeeInfoOperationResponse;
import com.barclays.bmg.utils.BMBCommonUtility;

public class AtAGlanceController extends BMBAbstractCommandController {

	private RetrievePayeeInfoOperation retrievePayeeInfoOperation;
	private MergeBillerInfoOperation mergeBillerInfoOperation;
    private AccountSummaryOperation accountSummaryOperation;
    private BMBJSONBuilder bmbJsonBuilder;

    private String activityId;
	private String txnType;

    /*
     * (non-Javadoc)
     *
     * @see com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController#handle1 (javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.validation.BindException)
     */
    @Override
    protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest, HttpServletResponse response, Object command, BindException errors)
	    throws Exception {

	AtAGlanceCommand atAGlanceCommand = (AtAGlanceCommand) command;

	// Get Account Type from USSD Request
	String accountType = null;
	if (!StringUtils.isEmpty(atAGlanceCommand.getAccountType())) {
	    accountType = atAGlanceCommand.getAccountType();
	}

	// Get AccountID Param from USSD Request
	String activityIDParam = null;
	if (!StringUtils.isEmpty(atAGlanceCommand.getActivityId())) {
	    activityIDParam = atAGlanceCommand.getActivityId();
	}

	AccountSummaryOperationRequest accountSummaryOperationRequest = makeRequest(httpRequest);
	//clearCorrelationIds(httpRequest, BMGProcessConstants.ACCOUNTS_PROCESS);

	accountSummaryOperationRequest.setAccountType(accountType);
	accountSummaryOperationRequest.setActivityIDParam(activityIDParam);

	AccountSummaryOperationResponse accountSummaryOperationResponse = accountSummaryOperation
		.retrieveCreditCardList(accountSummaryOperationRequest);

	if (accountSummaryOperationResponse.isSuccess()) {
		clearCorrelationIds(httpRequest, BMGProcessConstants.CREDIT_PAYMENT);
	    mapCorrelationIds(accountSummaryOperationResponse.getAccountList(), accountSummaryOperationRequest, httpRequest,
		    accountSummaryOperationResponse, BMGProcessConstants.CREDIT_PAYMENT);

	}


	if("crditCardFlg".equals(atAGlanceCommand.getCrditCardFlg())){

		Context context = createContext(httpRequest);
		context.setActivityId(activityId);
		clearCorrelationIds(httpRequest, BMGProcessConstants.BILL_PAYMENT);

		// Get Payee Info
		RetrievePayeeInfoOperationRequest retrievePayeeInfoOperationRequest = new RetrievePayeeInfoOperationRequest();
		retrievePayeeInfoOperationRequest.setPayId(atAGlanceCommand.getPayId());

		retrievePayeeInfoOperationRequest.setContext(context);
		retrievePayeeInfoOperationRequest.setPayGrp(getTxnType());
		RetrievePayeeInfoOperationResponse retrievePayeeInfoOperationResponse = retrievePayeeInfoOperation
				.retrievePayeeInfo(retrievePayeeInfoOperationRequest);

		// Get Merge Biller
		MergeBillerInfoOperationResponse mergeBillerInfoOperationResponse = null;
		if (retrievePayeeInfoOperationResponse.isSuccess()) {
			BeneficiaryDTO beneficiaryDTO = retrievePayeeInfoOperationResponse.getBeneficiaryDTO();
			if(beneficiaryDTO!=null){
				MergeBillerInfoOperationRequest mergeBillerInfoOperationRequest = new MergeBillerInfoOperationRequest();
				mergeBillerInfoOperationRequest.setContext(context);
				mergeBillerInfoOperationRequest
						.setBeneficiaryDTO(retrievePayeeInfoOperationResponse
								.getBeneficiaryDTO());
				mergeBillerInfoOperationRequest.setPayeeType(getTxnType());

				mergeBillerInfoOperationResponse = mergeBillerInfoOperation
						.mergeBillerInformation(mergeBillerInfoOperationRequest);
				retrievePayeeInfoOperationResponse.setBeneficiaryDTO(mergeBillerInfoOperationResponse.getBeneficiaryDTO());

			}

		}

		setTxnTypeInResponses(getTxnType(), retrievePayeeInfoOperationResponse,
				null,mergeBillerInfoOperationResponse,null);

		if(checkAllOperationResponses(retrievePayeeInfoOperationResponse,
				mergeBillerInfoOperationResponse)){
			setResponseInProcessMap(httpRequest,atAGlanceCommand,
					retrievePayeeInfoOperationResponse,mergeBillerInfoOperationResponse,null);


		}

	}


	return (BMBBaseResponseModel) bmbJsonBuilder.createJSONResponse(accountSummaryOperationResponse);

    }


    private void setResponseInProcessMap(HttpServletRequest httpRequest,Object command,ResponseContext... responseContexts){
    	//PayeeInformationCommand payeeInformationCommand = (PayeeInformationCommand) command;
		RetrievePayeeInfoOperationResponse retrievePayeeInfoOperationResponse = (RetrievePayeeInfoOperationResponse)responseContexts[0];
		MergeBillerInfoOperationResponse mergeBillerInfoOperationResponse = (MergeBillerInfoOperationResponse)responseContexts[1];
		//CheckInqueryBillOperationResponse checkInqueryBillOperationResponse = (CheckInqueryBillOperationResponse)responseContexts[2];
		TransactionDTO transactionDTO = new TransactionDTO();

		transactionDTO.setBeneficiaryDTO(retrievePayeeInfoOperationResponse.getBeneficiaryDTO());
		transactionDTO.setTxnType(getTxnType());
		//transactionDTO.setMinAmt(checkInqueryBillOperationResponse.getMinBillAmt());
		//transactionDTO.setMaxAmt(checkInqueryBillOperationResponse.getMaxBilAmt());
		if(mergeBillerInfoOperationResponse.getIntervalAmt()!=null){
			transactionDTO.setIntAmt(new BigDecimal(mergeBillerInfoOperationResponse.getIntervalAmt()));
		}
		//transactionDTO.setOutBalAmt(checkInqueryBillOperationResponse.getOutBalAmt());
		//transactionDTO.setMtpService(payeeInformationCommand.getPaySer());
		setIntoProcessMap(httpRequest, BMGProcessConstants.BILL_PAYMENT, BillPaymentConstants.TRANSACTION_DTO, transactionDTO);
	//	setProcessMapIntoSession(httpRequest, "transactionDTO", transactionDTO);
	}


    private void setTxnTypeInResponses(String txnType, ResponseContext... responses){
		if(responses!=null){
			for(ResponseContext response: responses){
				if(response!=null){
					response.setTxnTyp(txnType);
				}
			}
		}
	}

    private AccountSummaryOperationRequest makeRequest(HttpServletRequest request) {
	AccountSummaryOperationRequest accountSummaryOperationRequest = new AccountSummaryOperationRequest();
	Context context = createContext(request);
	context.setActivityId(ActivityConstant.CREDIT_CARD_ATAGLANCE_ACTIVITY_ID);
	context.setActivityRefNo(BMBCommonUtility.generateRefNo());
	accountSummaryOperationRequest.setContext(context);
	return accountSummaryOperationRequest;
    }

    public void setAccountSummaryOperation(AccountSummaryOperation accountSummaryOperation) {
	this.accountSummaryOperation = accountSummaryOperation;
    }

    public void setBmbJsonBuilder(BMBJSONBuilder bmbJsonBuilder) {
	this.bmbJsonBuilder = bmbJsonBuilder;
    }



	public RetrievePayeeInfoOperation getRetrievePayeeInfoOperation() {
		return retrievePayeeInfoOperation;
	}

	public void setRetrievePayeeInfoOperation(
			RetrievePayeeInfoOperation retrievePayeeInfoOperation) {
		this.retrievePayeeInfoOperation = retrievePayeeInfoOperation;
	}

	public MergeBillerInfoOperation getMergeBillerInfoOperation() {
		return mergeBillerInfoOperation;
	}

	public void setMergeBillerInfoOperation(
			MergeBillerInfoOperation mergeBillerInfoOperation) {
		this.mergeBillerInfoOperation = mergeBillerInfoOperation;
	}

	@Override
	protected String getActivityId(Object command) {
		return null;
	}


	public String getActivityId() {
		return activityId;
	}


	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}


	public String getTxnType() {
		return txnType;
	}


	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

}
