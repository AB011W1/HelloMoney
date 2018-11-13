package com.barclays.bmg.ministatement.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.accountdetails.CasaTransactionActivityCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.accountdetails.CASADetailsOperation;
import com.barclays.bmg.operation.accountdetails.CasaTransactionActivityOperation;
import com.barclays.bmg.operation.accountdetails.request.CASADetailsOperationRequest;
import com.barclays.bmg.operation.accountdetails.request.CasaTransactionActivityOperationRequest;
import com.barclays.bmg.operation.accountdetails.response.CASADetailsOperationResponse;
import com.barclays.bmg.operation.accountdetails.response.CasaTransactionTrnxHistoryOperationResponse;

/**
 * Controller for the Ministatment Transaction.
 *
 */
public class MiniStatmentRequestController extends BMBAbstractCommandController {

	private BMBJSONBuilder bmbJSONBuilder;
	private CASADetailsOperation casaDetailsOperation;
	private CasaTransactionActivityOperation casaTransactionActivityOperation;

	private String ACTIVITY_ID = ActivityConstant.MINI_STMT_ACTIVITY_ID;

	@Override
	protected String getActivityId(Object command) {
		return ACTIVITY_ID;
	}

	@Override
	protected BMBBaseResponseModel handle1(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		setLastStep(request);
		Context context = buildContext(request);
		CasaTransactionActivityCommand miniStmtRequestCommand  = (CasaTransactionActivityCommand) command;

		CASADetailsOperationRequest casaDetailsOperationRequest = new CASADetailsOperationRequest();
		casaDetailsOperationRequest.setAccountNo(getAccountNumber(miniStmtRequestCommand.getActNo(),request, BMGProcessConstants.ACCOUNTS_PROCESS));
		casaDetailsOperationRequest.setContext(context);

		CASADetailsOperationResponse casaDetailsOperationResponse = casaDetailsOperation.retrieveCASAAccountDTO(casaDetailsOperationRequest);

		CasaTransactionActivityOperationRequest casaTranxActvOperationReq = new CasaTransactionActivityOperationRequest();
		casaTranxActvOperationReq.setContext(context);
		casaTranxActvOperationReq.setAccountNo(getAccountNumber(miniStmtRequestCommand.getActNo(),request, BMGProcessConstants.ACCOUNTS_PROCESS));
		casaTranxActvOperationReq.setCasaAccountDTO(casaDetailsOperationResponse.getCasaAccountDTO());

		CasaTransactionTrnxHistoryOperationResponse casaTranxHstryOperationResp = casaTransactionActivityOperation.retrieveCasaTransactionHistoryActivity(casaTranxActvOperationReq);



		/*CASADetailsOperationResponse casaDetailsOperationResponse = casaDetailsOperation
				.retrieveCASAAccountDTO(casaDetailsOperationRequest);

		CasaTransactionActivityOperationRequest casaTranxActvOperationReq = makeRequest(httpRequest,context);
		casaTranxActvOperationReq.setAccountNo(getAccountNumber(casaTranxActvCommand.getActNo(),httpRequest, BMGProcessConstants.ACCOUNTS_PROCESS));
		casaTranxActvOperationReq.setDays(casaTranxActvCommand.getDays());
		casaTranxActvOperationReq.setCasaAccountDTO(casaDetailsOperationResponse.getCasaAccountDTO());

		CasaTransactionActivityOperationResponse casaTranxActvOperationResp
							= casaTransactionActivityOperation.retrieveCasaTransactionActivity(casaTranxActvOperationReq);

		return (BMBBaseResponseModel) bmbJsonBuilder.createJSONResponse(casaTranxActvOperationResp);
*/
		//bmbJSONBuilder.createJSONResponse(casaTranxActvOperationResp);
		//return null;

		return (BMBBaseResponseModel) bmbJSONBuilder.createJSONResponse(casaTranxHstryOperationResp);
		//return (BMBBaseResponseModel) bmbJSONBuilder.createJSONResponse(casaTranxHstryOperationResp);
	}

	/**
	 * This method takes in httpserquest object as the input parameter and
	 * builds the context.
	 *
	 * @param httpRequest
	 * @return
	 */
	protected Context buildContext(HttpServletRequest httpRequest) {
		Context context = createContext(httpRequest);
		context.setActivityId(ACTIVITY_ID);
		return context;

	}



	public CASADetailsOperation getCasaDetailsOperation() {
		return casaDetailsOperation;
	}

	public void setCasaDetailsOperation(CASADetailsOperation casaDetailsOperation) {
		this.casaDetailsOperation = casaDetailsOperation;
	}

	public CasaTransactionActivityOperation getCasaTransactionActivityOperation() {
		return casaTransactionActivityOperation;
	}

	public void setCasaTransactionActivityOperation(
			CasaTransactionActivityOperation casaTransactionActivityOperation) {
		this.casaTransactionActivityOperation = casaTransactionActivityOperation;
	}

	/**
	 * @return the bmbJSONBuilder
	 */
	public BMBJSONBuilder getBmbJSONBuilder() {
		return bmbJSONBuilder;
	}

	/**
	 * @param bmbJSONBuilder the bmbJSONBuilder to set
	 */
	public void setBmbJSONBuilder(BMBJSONBuilder bmbJSONBuilder) {
		this.bmbJSONBuilder = bmbJSONBuilder;
	}




}
