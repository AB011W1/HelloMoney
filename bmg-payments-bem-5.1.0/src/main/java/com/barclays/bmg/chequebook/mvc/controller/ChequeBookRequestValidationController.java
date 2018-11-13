package com.barclays.bmg.chequebook.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.chequebook.mvc.command.ChequeBookRequestValidationCommand;
import com.barclays.bmg.chequebook.operation.ChequeBookRequestValidationOperation;
import com.barclays.bmg.chequebook.operation.request.ChequeBookValidationOperationRequest;
import com.barclays.bmg.chequebook.operation.response.ChequeBookValidationOperationResponse;
import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.ChequeBookRequestDTO;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.accountservices.GetSelectedAccountOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.GetSelectedAccountOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.GetSelectedAccountOperationResponse;

public class ChequeBookRequestValidationController extends
		BMBAbstractCommandController {

	private BMBJSONBuilder bmbJSONBuilder;
	private ChequeBookRequestValidationOperation chequeBookRequestValidationOperation;
	private GetSelectedAccountOperation getSelectedAccountOperation;

	@Override
	protected String getActivityId(Object command) {
		// TODO Auto-generated method stub
		return "ACT_CHK_BOOK_REQUEST";
	}

	@Override
	protected BMBBaseResponseModel handle1(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {

		// Build Context
		Context context = buildContext(request);

		ChequeBookValidationOperationResponse chequeBookValidationOperationResponse = null;

		ChequeBookRequestValidationCommand chequeBookRequestValidationCommand = (ChequeBookRequestValidationCommand) command;

		// make request for account validation
		GetSelectedAccountOperationRequest getSelectedAccountOperationRequest = new GetSelectedAccountOperationRequest();

		getSelectedAccountOperationRequest.setContext(context);
		getSelectedAccountOperationRequest.setAcctNumber(getAccountNumber(
				chequeBookRequestValidationCommand.getActNo(), request,
				BMGProcessConstants.CHEQUE_BOOK_PROCESS));

		GetSelectedAccountOperationResponse getSelectedAccountOperationResponse = getSelectedAccountOperation
				.getSelectedSourceAccount(getSelectedAccountOperationRequest);

		if (getSelectedAccountOperationResponse.isSuccess()) {

			ChequeBookValidationOperationRequest chequeBookValidationOperationRequest = new ChequeBookValidationOperationRequest();

			chequeBookValidationOperationRequest.setContext(context);

			chequeBookValidationOperationRequest.setActNo(getAccountNumber(
					chequeBookRequestValidationCommand.getActNo(), request,
					BMGProcessConstants.CHEQUE_BOOK_PROCESS));

			chequeBookValidationOperationRequest
					.setBkSize(chequeBookRequestValidationCommand.getBkSize());
			chequeBookValidationOperationRequest
					.setBkTyp(chequeBookRequestValidationCommand.getBkTyp());
			chequeBookValidationOperationRequest
					.setBrnCde(chequeBookRequestValidationCommand.getBrnCde());
			chequeBookValidationOperationRequest
					.setBrnNam(chequeBookRequestValidationCommand.getBrnNam());

			chequeBookValidationOperationResponse = chequeBookRequestValidationOperation
					.chequeBookValidate(chequeBookValidationOperationRequest);
			if (chequeBookValidationOperationResponse.isSuccess()) {
				setResponseInProcessMap(request,
						getSelectedAccountOperationResponse,
						chequeBookValidationOperationResponse);
			}

		}

		// Make request for cheque book validation

		return (BMBBaseResponseModel) ((BMBMultipleResponseJSONBuilder) bmbJSONBuilder)
				.createMultipleJSONResponse(
						getSelectedAccountOperationResponse,
						chequeBookValidationOperationResponse);
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

		context.setActivityId("ACT_CHK_BOOK_REQUEST");

		return context;

	}

	/**
	 * @param httprequest
	 * @param response
	 */
	private void setResponseInProcessMap(
			HttpServletRequest httprequest,
			GetSelectedAccountOperationResponse getSelectedAccountOperationResponse,
			ChequeBookValidationOperationResponse chequeBookValidationOperationResponse) {
		ChequeBookRequestDTO chequeBookRequestDTO = new ChequeBookRequestDTO();

		chequeBookRequestDTO
				.setAccount((CASAAccountDTO) getSelectedAccountOperationResponse
						.getSelectedAcct());
		chequeBookRequestDTO.setBookSize(chequeBookValidationOperationResponse
				.getBkSize());
		chequeBookRequestDTO.setBookType(chequeBookValidationOperationResponse
				.getBkTyp());
		chequeBookRequestDTO
				.setBranchCode(chequeBookValidationOperationResponse
						.getBrnCde());
		chequeBookRequestDTO
				.setBranchName(chequeBookValidationOperationResponse
						.getBrnNam());

		setSessionAttribute(httprequest, chequeBookValidationOperationResponse
				.getTxnRefNo(), chequeBookRequestDTO);
	}

	public BMBJSONBuilder getBmbJSONBuilder() {
		return bmbJSONBuilder;
	}

	public void setBmbJSONBuilder(BMBJSONBuilder bmbJSONBuilder) {
		this.bmbJSONBuilder = bmbJSONBuilder;
	}

	public ChequeBookRequestValidationOperation getChequeBookRequestValidationOperation() {
		return chequeBookRequestValidationOperation;
	}

	public void setChequeBookRequestValidationOperation(
			ChequeBookRequestValidationOperation chequeBookRequestValidationOperation) {
		this.chequeBookRequestValidationOperation = chequeBookRequestValidationOperation;
	}

	public GetSelectedAccountOperation getGetSelectedAccountOperation() {
		return getSelectedAccountOperation;
	}

	public void setGetSelectedAccountOperation(
			GetSelectedAccountOperation getSelectedAccountOperation) {
		this.getSelectedAccountOperation = getSelectedAccountOperation;
	}

}
