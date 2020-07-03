package com.barclays.bmg.mvc.controller.billpayment;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.CategorizedPayeeListDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.billpayment.PayeeListCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.beneficiary.GetOwnCreditCardAccountsOperation;
import com.barclays.bmg.operation.beneficiary.RetrievePayeeListOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.GetOwnCreditCardAccountsOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.RetrievePayeeListOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.GetOwnCreditCardAccountsOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrievePayeeListOperationResponse;

public class PayeeListController extends BMBAbstractCommandController {


	private RetrievePayeeListOperation retrievePayeeListOperation;
	private GetOwnCreditCardAccountsOperation getOwnCreditCardAccountsOperation;
	private BMBMultipleResponseJSONBuilder bmbJSONBuilder;
	private static final String BARCLAY_CARD="BCD";
	//CR-82 Account Mapping issue fix
	private String AIRTIME_TOPUP="AT";
	private String M_WALLETE="WT";
	private String DATA_BUNDLE ="DB";


	@Override
	protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest,
			HttpServletResponse httpResponse, Object command, BindException errors)
			throws Exception {
		setFirstStep(httpRequest);

		PayeeListCommand payeeListCommand = (PayeeListCommand)command;
		String payGrp = payeeListCommand.getPayGrp();
		//CR-82 Account mapping issue fix for Airtime and Mwallet
		if(payGrp != null && !(payGrp.equalsIgnoreCase(AIRTIME_TOPUP) || payGrp.equalsIgnoreCase(M_WALLETE) || payGrp.equalsIgnoreCase(DATA_BUNDLE))){
			cleanProcess(httpRequest, BMGProcessConstants.BILL_PAYMENT);
		}

		Context context = createContext(httpRequest);
		context.setActivityId(getActivityId(command));

		// Get the payee List.
		RetrievePayeeListOperationRequest retrievePayeeListOperationRequest =
			new RetrievePayeeListOperationRequest();
		retrievePayeeListOperationRequest.setContext(context);
		retrievePayeeListOperationRequest.setPayGrp(payGrp);

		RetrievePayeeListOperationResponse retrievePayeeListOperationResponse =
				retrievePayeeListOperation.retrievePayeeList(retrievePayeeListOperationRequest);

		//For BCD get the Credit card accounts.
		if(BARCLAY_CARD.equals(payGrp)||StringUtils.isEmpty(payGrp)){
			GetOwnCreditCardAccountsOperationRequest getCardAccountsOperationRequest = new GetOwnCreditCardAccountsOperationRequest();
			getCardAccountsOperationRequest.setContext(context);
			getCardAccountsOperationRequest.setCategorizedPayeeList(retrievePayeeListOperationResponse.getCategorizedPayeeList());

			GetOwnCreditCardAccountsOperationResponse getCardAccountsOperationResponse =
								getOwnCreditCardAccountsOperation.getOwnCreditCardAccounts(getCardAccountsOperationRequest);
			retrievePayeeListOperationResponse.setCategorizedPayeeList(getCardAccountsOperationResponse.getCategorizedPayeeList());
			if(getCardAccountsOperationResponse.isSuccess()){
				retrievePayeeListOperationResponse.setSuccess(getCardAccountsOperationResponse.isSuccess());
			}
		}
		setTxnTypeInResponses(payGrp,retrievePayeeListOperationResponse);
		setPayeeTypeInProcessMap(httpRequest,retrievePayeeListOperationResponse);
		return (BMBBaseResponseModel) bmbJSONBuilder.createMultipleJSONResponse(retrievePayeeListOperationResponse);
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

	private void setPayeeTypeInProcessMap(HttpServletRequest httpRequest,RetrievePayeeListOperationResponse response){
		if(response.isSuccess()){
			List<CategorizedPayeeListDTO> categorizedPayeeList =  response.getCategorizedPayeeList();
			if(categorizedPayeeList!=null && !categorizedPayeeList.isEmpty()){
				for(CategorizedPayeeListDTO categorizedPayeeListDTO : categorizedPayeeList){
					if(categorizedPayeeListDTO!=null){
						List<BeneficiaryDTO> payeeList = categorizedPayeeListDTO.getPayeeList();
						List<? extends CustomerAccountDTO> destAccountList = categorizedPayeeListDTO.getDestAccountList();
						String txnType = categorizedPayeeListDTO.getTransactionFacadeRoutineType();
						if(payeeList!=null && !payeeList.isEmpty()){
							for(BeneficiaryDTO payee: payeeList){
								if(payee!=null){
									setIntoProcessMap(httpRequest, BMGProcessConstants.BILL_PAYMENT, payee.getPayeeId(),txnType);
									//setProcessMapIntoSession(httpRequest, payee.getPayeeId(),txnType);
								}

							}
						}
						if(destAccountList!=null && !destAccountList.isEmpty()){
							for(CustomerAccountDTO destAcct: destAccountList){
								if(destAcct!=null){
									setIntoProcessMap(httpRequest, BMGProcessConstants.BILL_PAYMENT, destAcct.getAccountNumber(),txnType);
									//setProcessMapIntoSession(httpRequest, payee.getPayeeId(),txnType);
								}

							}
						}
					}
				}
			}

		}
	}


	@Override
	protected String getActivityId(Object command) {
//		PayeeListCommand payeeListCommand = (PayeeListCommand)command;
//		String payGrp = payeeListCommand.getPayGrp();
		String actId = ActivityConstant.BILL_PAYMENT_ACTIVITY_ID;

		/*if(BillPaymentConstants.PAYEE_TYPE_BILL_PAYMENT.equals(payGrp)){
			actId = ActivityConstant.BILL_PAYMENT_PAYEE_ACTIVITY_ID;
		}else if(BillPaymentConstants.PAYEE_TYPE_MOBILE_TOPUP.equals(payGrp)){
			actId = ActivityConstant.MOBILE_TOPUP_PAYEE_ACTIVITY_ID;
		}*/
		return actId;
	}

	public RetrievePayeeListOperation getRetrievePayeeListOperation() {
		return retrievePayeeListOperation;
	}

	public void setRetrievePayeeListOperation(
			RetrievePayeeListOperation retrievePayeeListOperation) {
		this.retrievePayeeListOperation = retrievePayeeListOperation;
	}

	public BMBMultipleResponseJSONBuilder getBmbJSONBuilder() {
		return bmbJSONBuilder;
	}

	public void setBmbJSONBuilder(BMBMultipleResponseJSONBuilder bmbJSONBuilder) {
		this.bmbJSONBuilder = bmbJSONBuilder;
	}

	public GetOwnCreditCardAccountsOperation getGetOwnCreditCardAccountsOperation() {
		return getOwnCreditCardAccountsOperation;
	}

	public void setGetOwnCreditCardAccountsOperation(
			GetOwnCreditCardAccountsOperation getOwnCreditCardAccountsOperation) {
		this.getOwnCreditCardAccountsOperation = getOwnCreditCardAccountsOperation;
	}



}
