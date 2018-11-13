package com.barclays.bmg.mvc.controller.statement;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.constants.StatementResponseCodeConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.StatementRequestDTO;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.statement.StatmentRequestValidateCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.accountservices.GetSelectedAccountOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.GetSelectedAccountOperationRequest;
import com.barclays.bmg.operation.request.statement.StatementValidationOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.GetSelectedAccountOperationResponse;
import com.barclays.bmg.operation.response.statement.StatmentValidateOperationResponse;
import com.barclays.bmg.operation.statement.StatementRequestValidationOperation;
import com.barclays.bmg.operation.statement.StatementRequestValidationValidate;

public class StatementRequestValidationController extends BMBAbstractCommandController{

	private BMBJSONBuilder bmbJSONBuilder;
	private StatementRequestValidationOperation statementRequestValidationOperation;
	private GetSelectedAccountOperation getSelectedAccountOperation;
	private StatementRequestValidationValidate statementRequestValidationValidate;

	@Override
	protected String getActivityId(Object command) {
		// TODO Auto-generated method stub
		return "CUS_ORDER_PAPER_STMT";
	}

	@Override
	protected BMBBaseResponseModel handle1(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {

		// Build Context
		Context context = buildContext(request);

		StatmentRequestValidateCommand statmentRequestValidateCommand = (StatmentRequestValidateCommand) command;

		GetSelectedAccountOperationRequest getSelectedAccountOperationRequest = new GetSelectedAccountOperationRequest();

		getSelectedAccountOperationRequest.setContext(context);

		getSelectedAccountOperationRequest.setAcctNumber(getAccountNumber(statmentRequestValidateCommand.getActNo(), request,
				BMGProcessConstants.STMT_REQ_PROCESS));

		GetSelectedAccountOperationResponse getSelectedAccountOperationResponse = getSelectedAccountOperation
				.getSelectedSourceAccount(getSelectedAccountOperationRequest);

		StatmentValidateOperationResponse statmentExecuteOperationResponse = new StatmentValidateOperationResponse();

		statementRequestValidationValidate.dateValidation(context,statmentRequestValidateCommand,statmentExecuteOperationResponse);

		if(statmentExecuteOperationResponse.isSuccess()){

		if(getSelectedAccountOperationResponse.isSuccess()){

			StatementValidationOperationRequest statementExecuteOperationRequest =
										new StatementValidationOperationRequest();

			statementExecuteOperationRequest.setAccountDTO((CASAAccountDTO) getSelectedAccountOperationResponse.getSelectedAcct());

			/*statementExecuteOperationRequest.setFromDate(longStringCal(new Date().getTime()));
			statementExecuteOperationRequest.setToDate(longStringCal(new Date().getTime()));*/

			statementExecuteOperationRequest.setFromDate(statmentRequestValidateCommand.getFromDate());
			statementExecuteOperationRequest.setToDate(statmentRequestValidateCommand.getToDate());
			statementExecuteOperationRequest.setContext(context);
			statmentExecuteOperationResponse = setresopnceObj(statementExecuteOperationRequest);
			setResponseInProcessMap(request, getSelectedAccountOperationResponse,statmentExecuteOperationResponse);
		}
	}else{
			statmentExecuteOperationResponse = new StatmentValidateOperationResponse();
			statmentExecuteOperationResponse.setContext(context);
			statmentExecuteOperationResponse.setSuccess(false);
			statmentExecuteOperationResponse.setResCde(StatementResponseCodeConstant.STATEMENT_NOT_COMPLETE);
			getMessage(statmentExecuteOperationResponse);

		}

		return (BMBBaseResponseModel) ((BMBMultipleResponseJSONBuilder) bmbJSONBuilder)
									.createMultipleJSONResponse(statmentExecuteOperationResponse);

	}

	private StatmentValidateOperationResponse setresopnceObj(
			StatementValidationOperationRequest statementExecuteOperationRequest) {

		StatmentValidateOperationResponse validateOperationResponse = new StatmentValidateOperationResponse();
/*		BranchLookUpDTO branchLookUpDTO = new BranchLookUpDTO();
		branchLookUpDTO.setBranchCode(statementExecuteOperationRequest.getBranchLookUpDTO().getBranchCode());
		validateOperationResponse.setBranchLookUpDTO(branchLookUpDTO);
*/
		//validateOperationResponse.setBrnCde(statementExecuteOperationRequest.getAccountDTO().getBranchCode());
		validateOperationResponse.setActNo(statementExecuteOperationRequest.getAccountDTO());
		validateOperationResponse.setFromDate(statementExecuteOperationRequest.getFromDate());
		validateOperationResponse.setToDate(statementExecuteOperationRequest.getToDate());
		validateOperationResponse.setTxnRefNo(statementExecuteOperationRequest.getContext().getOrgRefNo());

		return validateOperationResponse;
	}

	private void setResponseInProcessMap(
			HttpServletRequest request,GetSelectedAccountOperationResponse getSelectedAccountOperationResponse,
			StatmentValidateOperationResponse statmentExecuteOperationResponse) {

		StatementRequestDTO statementRequestDTO = new StatementRequestDTO();

		statementRequestDTO.setAccount((CASAAccountDTO) getSelectedAccountOperationResponse.getSelectedAcct());
		statementRequestDTO.setFromDate(statmentExecuteOperationResponse.getFromDate());
		statementRequestDTO.setToDate(statmentExecuteOperationResponse.getToDate());

		/*statementRequestDTO.setStmtEndDate(convertCalToLong(statmentExecuteOperationResponse.getFromDate()));
		statementRequestDTO.setStmtStartDate(convertCalToLong(statmentExecuteOperationResponse.getToDate()));*/

		setSessionAttribute(request, statmentExecuteOperationResponse.getTxnRefNo(), statementRequestDTO);
	}


	private long convertCalToLong(String calendar){

		/*Calendar cal = new GregorianCalendar();

		cal.setTime(new Date(calendar));*/

		Long l = Long.valueOf(calendar);

		return l;
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

		Map<String, String> ppMap = (Map<String, String>) getSessionAttribute(
				httpRequest, SessionConstant.SESSION_PP_MAP);

		context.setPpMap(ppMap);

		context.setActivityId("CUS_ORDER_PAPER_STMT");

		return context;

	}

	// convert long to calander
	private static Calendar longToCal(long longDate){
		Calendar calendar = new GregorianCalendar();
		// set lenient false
		calendar.setLenient(false);

		calendar.setTimeInMillis(longDate);

		return calendar;
	}


	// convert long to calander
	private static String longStringCal(long longDate){

		return String.valueOf(longDate);
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

	/**
	 * @return the getSelectedAccountOperation
	 */
	public GetSelectedAccountOperation getGetSelectedAccountOperation() {
		return getSelectedAccountOperation;
	}

	/**
	 * @param getSelectedAccountOperation the getSelectedAccountOperation to set
	 */
	public void setGetSelectedAccountOperation(
			GetSelectedAccountOperation getSelectedAccountOperation) {
		this.getSelectedAccountOperation = getSelectedAccountOperation;
	}

	/**
	 * @return the statementRequestValidationOperation
	 */
	public StatementRequestValidationOperation getStatementRequestValidationOperation() {
		return statementRequestValidationOperation;
	}

	/**
	 * @param statementRequestValidationOperation the statementRequestValidationOperation to set
	 */
	public void setStatementRequestValidationOperation(
			StatementRequestValidationOperation statementRequestValidationOperation) {
		this.statementRequestValidationOperation = statementRequestValidationOperation;
	}
	public StatementRequestValidationValidate getStatementRequestValidationValidate() {
		return statementRequestValidationValidate;
	}

	public void setStatementRequestValidationValidate(
			StatementRequestValidationValidate statementRequestValidationValidate) {
		this.statementRequestValidationValidate = statementRequestValidationValidate;
	}




}
