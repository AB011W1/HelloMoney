package com.barclays.bmg.mvc.controller.fundtransfer.external;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.ActivityIdConstantBean;
import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.CustomerDTO;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractController;
import com.barclays.bmg.operation.accountdetails.RetrieveAllCustAcctOperation;
import com.barclays.bmg.operation.accountservices.RetrieveAccountListOperation;
import com.barclays.bmg.operation.request.RetrieveAllCustAcctOperationRequest;
import com.barclays.bmg.operation.request.SessionSummaryOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.RetrieveAcctListOperationRequest;
import com.barclays.bmg.operation.response.RetrieveAllCustAcctOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrieveAcctListOperationResponse;

public class SearchIndividualCustByAcctController extends BMBAbstractController {

	private RetrieveAllCustAcctOperation retrieveAllCustAcctOperation;
	private RetrieveAccountListOperation retrieveAccountListOperation;
	private BMBJSONBuilder bmbJsonBuilder;
	private static final String OPCD = "OP0988";
	//private static final String OPCD99 = "OP0999";
	private static final String OPCDSTR = "opCde";

	@Override
	protected String getActivityId() {

		return ActivityIdConstantBean.GHIPPS_FUND_TRANSFER_OTHER_BANK_ACCOUNTS;
	}

	@Override
	protected BMBBaseResponseModel handleRequestInternal1(HttpServletRequest request, HttpServletResponse httpResponse) throws Exception {
		Context context = createContext(request);
		RetrieveAllCustAcctOperationResponse opResAllCust;
		RetrieveAcctListOperationResponse opResAccList;
		if (OPCD.equals(request.getParameter(OPCDSTR))){
			RetrieveAllCustAcctOperationRequest opReq = makeRequest(request, context);
			opResAllCust = retrieveAllCustAcctOperation.retrieveAllCustAccount(opReq);
			if (opResAllCust != null) {
			boolean isSuccess = opResAllCust.isSuccess();
			if (isSuccess) {
				if (OPCD.equals(request.getParameter(OPCDSTR))) {
					CustomerDTO custDto = opResAllCust.getCustomer();
					if (custDto != null && opResAllCust.getContext() != null) {
						setUserMapIntoSession(request, SessionConstant.SESSION_LANGUAGE_ID, custDto.getLanguage());
						setUserMapIntoSession(request, SessionConstant.SESSION_FULL_NAME, custDto.getFullName());
						setUserMapIntoSession(request, SessionConstant.SESSION_CUSTOMER_DTO, custDto);
						setUserMapIntoSession(request, SessionConstant.SESSION_CUSTOMER_ID, custDto.getCustomerID());
						setUserMapIntoSession(request, SessionConstant.SESSION_USER_ID, context.getMobilePhone());
						setUserMapIntoSession(request, SessionConstant.SESSION_PIN_STATUS, custDto.getPinStatus());
						setUserMapIntoSession(request, SessionConstant.SESSION_PP_MAP, opResAllCust.getContext().getPpMap());

						//Changes for caching of account list & reduce one call to enhance performance
						setUserMapIntoSession(request, SessionConstant.SESSION_ACCOUNT_LIST+context.getSessionId(), opResAllCust.getAccountList());
					}
					SessionSummaryOperationRequest seOperationRequest = new SessionSummaryOperationRequest();
					seOperationRequest.setContext(createContext(request));
				}
				clearCorrelationIds(request, BMGProcessConstants.ACCOUNTS_PROCESS);
				mapCorrelationIds(opResAllCust.getAccountList(), opReq, request, opResAllCust, BMGProcessConstants.ACCOUNTS_PROCESS);
			}
		}
			return (BMBBaseResponseModel) bmbJsonBuilder.createJSONResponse(opResAllCust);
		}else{
			/*RetrieveAcctListOperationRequest opReq = makeRequest1(request, context);
			opResAccList=retrieveAccountListOperation.getSourceAcctList(opReq);
			return (BMBBaseResponseModel) bmbJsonBuilder.createJSONResponse(opResAccList);*/
			//context.setActivityId(activityId);
			//clearCorrelationIds(httpRequest, BMGProcessConstants.BILL_PAYMENT);

			// Get the source accounts.
			RetrieveAcctListOperationRequest retrieveAcctListOperationRequest = new RetrieveAcctListOperationRequest();
			context.setActivityId(ActivityConstant.CASA_DETAIL_ACTIVITY_ID);
			retrieveAcctListOperationRequest.setContext(context);
			RetrieveAcctListOperationResponse retrieveAcctListOperationResponse = retrieveAccountListOperation
					.getSourceAccountsForLocalCurrency(retrieveAcctListOperationRequest);

			if(retrieveAcctListOperationResponse.isSuccess()){
				mapCorrelationIds(retrieveAcctListOperationResponse.getAcctList(), retrieveAcctListOperationRequest, request, retrieveAcctListOperationResponse, BMGProcessConstants.BILL_PAYMENT);
			}

			/*if(checkAllOperationResponses(retrieveAcctListOperationResponse,transactionLimitOperationResponse,oneTimeBillPayOperationResponse)){

				TransactionDTO transactionDTO=oneTimeBillPayOperation.mergeBillerForOneTimePay(oneTimeBillPayOperationResponse.getBillerDTO(),oneTimeBillPayOperationRequest);
				transactionDTO.setTxnType(txnType);
				setIntoProcessMap(httpRequest, BMGProcessConstants.BILL_PAYMENT, BillPaymentConstants.TRANSACTION_DTO, transactionDTO);
			}*/

			return (BMBBaseResponseModel) bmbJsonBuilder.createJSONResponse(retrieveAcctListOperationResponse);
		}





	}

	private RetrieveAllCustAcctOperationRequest makeRequest(HttpServletRequest request, Context context) {
		RetrieveAllCustAcctOperationRequest opReq = new RetrieveAllCustAcctOperationRequest();
		context.setActivityId(ActivityConstant.CASA_DETAIL_ACTIVITY_ID);
		opReq.setContext(context);
		return opReq;
	}
	private RetrieveAcctListOperationRequest makeRequest1(HttpServletRequest request, Context context) {
		RetrieveAcctListOperationRequest opReq = new RetrieveAcctListOperationRequest();
		context.setActivityId(ActivityConstant.CASA_DETAIL_ACTIVITY_ID);
		opReq.setContext(context);
		return opReq;
	}

	public RetrieveAllCustAcctOperation getRetrieveAllCustAcctOperation() {
		return retrieveAllCustAcctOperation;
	}

	public void setRetrieveAllCustAcctOperation(
			RetrieveAllCustAcctOperation retrieveAllCustAcctOperation) {
		this.retrieveAllCustAcctOperation = retrieveAllCustAcctOperation;
	}

	public BMBJSONBuilder getBmbJsonBuilder() {
		return bmbJsonBuilder;
	}

	public void setBmbJsonBuilder(BMBJSONBuilder bmbJsonBuilder) {
		this.bmbJsonBuilder = bmbJsonBuilder;
	}

	public RetrieveAccountListOperation getRetrieveAccountListOperation() {
		return retrieveAccountListOperation;
	}

	public void setRetrieveAccountListOperation(
			RetrieveAccountListOperation retrieveAccountListOperation) {
		this.retrieveAccountListOperation = retrieveAccountListOperation;
	}





}
