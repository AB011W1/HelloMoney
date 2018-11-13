package com.barclays.bmg.chequebook.mvc.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.chequebook.mvc.command.ChequeBookRequestExecuteCommand;
import com.barclays.bmg.chequebook.operation.ChequeBookRequestExecuteOperation;
import com.barclays.bmg.chequebook.operation.request.ChequeBookExecuteOperationRequest;
import com.barclays.bmg.chequebook.operation.response.ChequeBookExecuteOperationResponse;
import com.barclays.bmg.constants.ChequeBookResponseCodeConstant;
import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.ChequeBookRequestDTO;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;

public class ChequeBookRequestExecuteController extends BMBAbstractCommandController{

	private BMBJSONBuilder bmbJSONBuilder;
	private ChequeBookRequestExecuteOperation chequeBookRequestExecuteOperation;

	@Override
	protected String getActivityId(Object command) {
		// TODO Auto-generated method stub
		return "ACT_CHK_BOOK_REQUEST";
	}

	@Override
	protected BMBBaseResponseModel handle1(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		setLastStep(request);
		ChequeBookRequestExecuteCommand chequeBookRequestExecuteCommand =
						(ChequeBookRequestExecuteCommand) command;

		ChequeBookExecuteOperationResponse chequeBookExecuteOperationResponse = null;

		String txnRefNo = chequeBookRequestExecuteCommand.getTxnRefNo();

		ChequeBookRequestDTO chequeBookRequestDTO = (ChequeBookRequestDTO)getSessionAttribute(request, txnRefNo);

		// Build Context
		Context context = buildContext(request);

		if(chequeBookRequestDTO != null){


		ChequeBookExecuteOperationRequest chequeBookExecuteOperationRequest =
										new ChequeBookExecuteOperationRequest();

		chequeBookExecuteOperationRequest.setChequeBookRequestDTO(chequeBookRequestDTO);

		chequeBookExecuteOperationRequest.setContext(context);
		//context.setActivityRefNo(txnRefNo);

		/*ChequeBookRequestDTO chequeBookRequestDTO = (ChequeBookRequestDTO)getSessionAttribute(request,
											chequeBookRequestExecuteCommand.getTxnRefNo());*/

		chequeBookExecuteOperationResponse =
				chequeBookRequestExecuteOperation.chequeBookExecute(chequeBookExecuteOperationRequest);

		chequeBookExecuteOperationResponse.setTxnRefNo(context.getActivityRefNo());
		}else{
			chequeBookExecuteOperationResponse = new ChequeBookExecuteOperationResponse();
			chequeBookExecuteOperationResponse.setContext(context);
			chequeBookExecuteOperationResponse.setSuccess(false);
			chequeBookExecuteOperationResponse.setResCde(ChequeBookResponseCodeConstant.CHEQUE_REF_NO_INVALID);
			getMessage(chequeBookExecuteOperationResponse);

		}


		return (BMBBaseResponseModel) ((BMBMultipleResponseJSONBuilder) bmbJSONBuilder)
									.createMultipleJSONResponse(chequeBookExecuteOperationResponse);
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

		Map<String, Object> userMap = getUserMapFromSession(httpRequest);

/*
		Map<String, String> ppMap = (Map<String, String>) getSessionAttribute(
				httpRequest, SessionConstant.SESSION_PP_MAP);
*/
		context.setPpMap((Map<String, String>) userMap.get(SessionConstant.SESSION_PP_MAP));

		context.setActivityId("ACT_CHK_BOOK_REQUEST");

		return context;

	}

	public BMBJSONBuilder getBmbJSONBuilder() {
		return bmbJSONBuilder;
	}

	public void setBmbJSONBuilder(BMBJSONBuilder bmbJSONBuilder) {
		this.bmbJSONBuilder = bmbJSONBuilder;
	}

	public ChequeBookRequestExecuteOperation getChequeBookRequestExecuteOperation() {
		return chequeBookRequestExecuteOperation;
	}

	public void setChequeBookRequestExecuteOperation(
			ChequeBookRequestExecuteOperation chequeBookRequestExecuteOperation) {
		this.chequeBookRequestExecuteOperation = chequeBookRequestExecuteOperation;
	}

}
