package com.barclays.bmg.mvc.controller.statement;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.constants.StatementResponseCodeConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.StatementRequestDTO;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.statement.StatementRequestExecuteCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.accountservices.GetSelectedAccountOperation;
import com.barclays.bmg.operation.request.statement.StatementExecuteOperationRequest;
import com.barclays.bmg.operation.response.statement.StatmentExecuteOperationResponse;
import com.barclays.bmg.operation.statement.StatementRequestExcecuteOperation;

public class StatementRequestExecuteController extends BMBAbstractCommandController{

	private BMBJSONBuilder bmbJSONBuilder;
	private StatementRequestExcecuteOperation statementRequestExcecuteOperation;
	private GetSelectedAccountOperation getSelectedAccountOperation;

	@Override
	protected String getActivityId(Object command) {

		return "CUS_ORDER_PAPER_STMT";
	}

	@Override
	protected BMBBaseResponseModel handle1(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		setLastStep(request);
		// Build Context
		Context context = buildContext(request);

		StatmentExecuteOperationResponse statmentExecuteOperationResponse = null;

		StatementRequestExecuteCommand statementRequestExecuteCommand = (StatementRequestExecuteCommand) command;

		String txnRefNo = statementRequestExecuteCommand.getTxnRefNo();

		// get the data from he session
		StatementRequestDTO statementRequestDTO = (StatementRequestDTO)getSessionAttribute(request, txnRefNo);

		if(statementRequestDTO != null){

		StatementExecuteOperationRequest statementExecuteOperationRequest = new StatementExecuteOperationRequest();

		statementExecuteOperationRequest.setStatementRequestDTO(statementRequestDTO);

		statementExecuteOperationRequest.setContext(context);

		statmentExecuteOperationResponse = statementRequestExcecuteOperation.statmentExecute(statementExecuteOperationRequest);

		statmentExecuteOperationResponse.setRefNo(context.getOrgRefNo());

		}else{
			statmentExecuteOperationResponse = new StatmentExecuteOperationResponse();
			statmentExecuteOperationResponse.setContext(context);
			statmentExecuteOperationResponse.setSuccess(false);
			statmentExecuteOperationResponse.setResCde(StatementResponseCodeConstant.STATEMENT_NOT_COMPLETE);
			getMessage(statmentExecuteOperationResponse);

		}

		return (BMBBaseResponseModel) ((BMBMultipleResponseJSONBuilder) bmbJSONBuilder)
									.createMultipleJSONResponse(statmentExecuteOperationResponse);

	}

	/*private StatmentValidateOperationResponse setresopnceObj(
			StatementValidationOperationRequest statementExecuteOperationRequest) {

		StatmentValidateOperationResponse validateOperationResponse = new StatmentValidateOperationResponse();
		validateOperationResponse.setActNo(statementExecuteOperationRequest.getAccountDTO());
		validateOperationResponse.setBrnCde(statementExecuteOperationRequest.getBrchCde());
		validateOperationResponse.setBrnNam(statementExecuteOperationRequest.getBrchNm());
		validateOperationResponse.setFromDate(statementExecuteOperationRequest.getFromDate());
		validateOperationResponse.setToDate(statementExecuteOperationRequest.getToDate());
		validateOperationResponse.setTxnTyp(statementExecuteOperationRequest.getContext().getOrgRefNo());

		return validateOperationResponse;
	}

	private void setResponseInProcessMap(
			HttpServletRequest request,GetSelectedAccountOperationResponse getSelectedAccountOperationResponse,
			StatmentValidateOperationResponse statmentExecuteOperationResponse) {
		StatementRequestDTO statementRequestDTO = new StatementRequestDTO();

		statementRequestDTO.setAccount((CASAAccountDTO) getSelectedAccountOperationResponse.getSelectedAcct());
		statementRequestDTO.setStmtEndDate(Long.valueOf(statmentExecuteOperationResponse.getFromDate()));
		statementRequestDTO.setStmtStartDate(Long.valueOf(statmentExecuteOperationResponse.getToDate()));
		statementRequestDTO.setBranchCode(statmentExecuteOperationResponse.getBrnCde());
		statementRequestDTO.setBranchName(statmentExecuteOperationResponse.getBrnNam());

		setSessionAttribute(request, statmentExecuteOperationResponse.getContext().getOrgRefNo(), statementRequestDTO);
	}*/

	/**
	 * This method takes in httpserquest object as the input parameter and
	 * builds the context.
	 *
	 * @param httpRequest
	 * @return
	 */
	protected Context buildContext(HttpServletRequest httpRequest) {
		Context context = createContext(httpRequest);

/*		Map<String, String> ppMap = (Map<String, String>) getSessionAttribute(
				httpRequest, SessionConstant.SESSION_PP_MAP);
*/
		Map<String, Object> userMap = getUserMapFromSession(httpRequest);

		context.setPpMap((Map<String, String>) userMap.get(SessionConstant.SESSION_PP_MAP));

		context.setActivityId("CUS_ORDER_PAPER_STMT");

		return context;

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
	 * @return the statementRequestExcecuteOperation
	 */
	public StatementRequestExcecuteOperation getStatementRequestExcecuteOperation() {
		return statementRequestExcecuteOperation;
	}

	/**
	 * @param statementRequestExcecuteOperation the statementRequestExcecuteOperation to set
	 */
	public void setStatementRequestExcecuteOperation(
			StatementRequestExcecuteOperation statementRequestExcecuteOperation) {
		this.statementRequestExcecuteOperation = statementRequestExcecuteOperation;
	}


}
