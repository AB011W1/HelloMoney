package com.barclays.bmg.mvc.controller.billpayment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.ActivityIdConstantBean;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.TransactionHistoryDTO;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.billpayment.SearchTransactionHistoryCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.payments.SearchTransactionHistoryOperation;
import com.barclays.bmg.operation.request.billpayment.SearchTransactionHistoryOperationRequest;
import com.barclays.bmg.operation.response.billpayment.SearchTransactionHistoryOperationResponse;

/**
 * @author BTCI
 *
 */
public class SearchTransactionHistoryController extends
		BMBAbstractCommandController {
	private BMBMultipleResponseJSONBuilder bmbJSONBuilder;
	private SearchTransactionHistoryOperation searchTransactionHistoryOperation;

	public void setSearchTransactionHistoryOperation(
			SearchTransactionHistoryOperation searchTransactionHistoryOperation) {
		this.searchTransactionHistoryOperation = searchTransactionHistoryOperation;
	}

	public void setBmbJSONBuilder(BMBMultipleResponseJSONBuilder bmbJSONBuilder) {
		this.bmbJSONBuilder = bmbJSONBuilder;
	}

	@Override
	protected String getActivityId(Object command) {
		return ActivityIdConstantBean.TXN_HISTORY_ACTIVITY_ID;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController#handle1
	 * (javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, java.lang.Object,
	 * org.springframework.validation.BindException)
	 */
	@Override
	protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		setLastStep(httpRequest);
		SearchTransactionHistoryCommand searchTransactionHistoryCommand = (SearchTransactionHistoryCommand) command;
		SearchTransactionHistoryOperationRequest searchTransactionHistoryOperationRequest = new SearchTransactionHistoryOperationRequest();
		Context context = addContext(searchTransactionHistoryOperationRequest,
				httpRequest);
		searchTransactionHistoryOperationRequest.setContext(context);
		TransactionHistoryFactory factory = new TransactionHistoryFactory();
		TransactionHistoryDTO transactionHistoryDTO = factory
				.createTransactionHistory(searchTransactionHistoryCommand
						.getTransactionType());
		searchTransactionHistoryOperationRequest
				.setTransactionHistoryDTO(transactionHistoryDTO);
		if(searchTransactionHistoryCommand.getGroupWalletFlow()!=null && searchTransactionHistoryCommand.getGroupWalletFlow().equals("true")){
			searchTransactionHistoryOperationRequest.setGroupWalletFlow(searchTransactionHistoryCommand.getGroupWalletFlow());
			searchTransactionHistoryOperationRequest.setBillerCode(searchTransactionHistoryCommand.getBillerCode());
			searchTransactionHistoryOperationRequest.setDebitAccountNumber(searchTransactionHistoryCommand.getDebitAccountNumber());
			searchTransactionHistoryOperationRequest.setFundsTransferType(searchTransactionHistoryCommand.getFundsTransferType());
		}
		SearchTransactionHistoryOperationResponse searchTransactionHistoryOperationResponse = searchTransactionHistoryOperation
				.searchTransactionHistory(searchTransactionHistoryOperationRequest);
		searchTransactionHistoryOperationResponse
				.setFundTransferType(searchTransactionHistoryCommand
						.getTransactionType());
		return (BMBBaseResponseModel) bmbJSONBuilder
				.createMultipleJSONResponse(searchTransactionHistoryOperationResponse);
	}

	/**
	 * @param request
	 * @param httpRequest
	 * @return Context
	 */
	private Context addContext(RequestContext request,
			HttpServletRequest httpRequest) {
		Context context = createContext(httpRequest);
		context.setActivityId(ActivityIdConstantBean.TXN_HISTORY_ACTIVITY_ID);
		request.setContext(context);
		return context;
	}

}
