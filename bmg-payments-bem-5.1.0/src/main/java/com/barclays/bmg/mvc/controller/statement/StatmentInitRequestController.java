package com.barclays.bmg.mvc.controller.statement;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractController;
import com.barclays.bmg.mvc.operation.lookup.BranchLookUpOperation;
import com.barclays.bmg.operation.accountservices.RetrieveAccountListOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.RetrieveAcctListOperationRequest;
import com.barclays.bmg.operation.request.lookup.BranchLookUpOperationRequest;
import com.barclays.bmg.operation.request.statement.RetrieveAcctListOperationForStmtRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrieveAcctListOperationResponse;
import com.barclays.bmg.operation.response.lookup.BranchLookUpOperationResponse;
import com.barclays.bmg.operation.response.statement.StatmentInitOperationResponse;

public class StatmentInitRequestController extends BMBAbstractController {

	private BMBJSONBuilder bmbJSONBuilder;
	private RetrieveAccountListOperation retrieveSourceAccountListOperation;
	//StmtRequestOperation stmtRequestOperation;
	private BranchLookUpOperation branchLookUpOperation;

	@Override
	protected String getActivityId() {
		// TODO Auto-generated method stub
		return "CUS_ORDER_PAPER_STMT";
	}

	@Override
	protected BMBBaseResponseModel handleRequestInternal1(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		setFirstStep(request);
		// Clear the co-relation ids.
		clearCorrelationIds(request, BMGProcessConstants.STMT_REQ_PROCESS);

		// Build Context
		Context context = buildContext(request);

		// Retrieve the SourceAccount List
		// a) Create The Request
		RetrieveAcctListOperationRequest retrieveAccountListOperationReq = new RetrieveAcctListOperationRequest();
		// b) Set The Context
		retrieveAccountListOperationReq.setContext(context);
		// c) Invoke the Service
		RetrieveAcctListOperationResponse retrieveSourceAccountOperationResponse = retrieveSourceAccountListOperation.getCASASourceAccounts((retrieveAccountListOperationReq));

		StatmentInitOperationResponse statmentInitOperationResponse = null;
		BranchLookUpOperationResponse branchLookUpOperationResponse = null;

		if (retrieveSourceAccountOperationResponse.isSuccess()) {

			RetrieveAcctListOperationForStmtRequest stmtRequest = new RetrieveAcctListOperationForStmtRequest();

			stmtRequest.setContext(context);

			String branchCodeList = "";
			if (retrieveSourceAccountOperationResponse.getAcctList() != null
					&& retrieveSourceAccountOperationResponse.getAcctList()
							.size() > 0) {

				List<? extends CustomerAccountDTO> srcActLst = retrieveSourceAccountOperationResponse
						.getAcctList();

				for (int i = 0; i < srcActLst.size(); i++) {
					CASAAccountDTO account = (CASAAccountDTO) srcActLst.get(i);

					if (StringUtils.isNotEmpty(account.getBranchCode())) {
						if (i == 0) {
							branchCodeList += account.getBranchCode();
						} else {
							branchCodeList += "," + account.getBranchCode();
						}
					}
				}
			}

			if (StringUtils.isNotEmpty(branchCodeList)) {

				BranchLookUpOperationRequest branchLookUpOperationRequest = new BranchLookUpOperationRequest();
				branchLookUpOperationRequest.setContext(context);
				branchLookUpOperationRequest.setBranchCode(branchCodeList);

				branchLookUpOperationResponse = branchLookUpOperation
						.retrieveBranchName(branchLookUpOperationRequest);

				branchLookUpOperationResponse.setSuccess(true);
			}

			mapCorrelationIds(retrieveSourceAccountOperationResponse
					.getAcctList(), retrieveAccountListOperationReq, request,
					retrieveSourceAccountOperationResponse,
					BMGProcessConstants.STMT_REQ_PROCESS);

		}
		return (BMBBaseResponseModel) ((BMBMultipleResponseJSONBuilder) bmbJSONBuilder)
				.createMultipleJSONResponse(
						retrieveSourceAccountOperationResponse,
						statmentInitOperationResponse,
						branchLookUpOperationResponse);
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

		context.setActivityId("CUS_ORDER_PAPER_STMT");

		return context;

	}

	public BMBJSONBuilder getBmbJSONBuilder() {
		return bmbJSONBuilder;
	}

	public void setBmbJSONBuilder(BMBJSONBuilder bmbJSONBuilder) {
		this.bmbJSONBuilder = bmbJSONBuilder;
	}

	public RetrieveAccountListOperation getRetrieveSourceAccountListOperation() {
		return retrieveSourceAccountListOperation;
	}

	public void setRetrieveSourceAccountListOperation(
			RetrieveAccountListOperation retrieveSourceAccountListOperation) {
		this.retrieveSourceAccountListOperation = retrieveSourceAccountListOperation;
	}




	public BranchLookUpOperation getBranchLookUpOperation() {
		return branchLookUpOperation;
	}

	public void setBranchLookUpOperation(
			BranchLookUpOperation branchLookUpOperation) {
		this.branchLookUpOperation = branchLookUpOperation;
	}

}
