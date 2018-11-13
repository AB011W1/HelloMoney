package com.barclays.bmg.operation.statement;

import com.barclays.bmg.audit.annotation.AuditSupport;
import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.StatementRequestDTO;
import com.barclays.bmg.operation.BMBCommonOperation;
import com.barclays.bmg.operation.request.statement.StatementExecuteOperationRequest;
import com.barclays.bmg.operation.response.statement.StatmentExecuteOperationResponse;
import com.barclays.bmg.service.StatementRequestExecuteService;
import com.barclays.bmg.service.request.StatmentExecuteServiceRequest;
import com.barclays.bmg.service.response.StatmentExecuteServiceResponse;

public class StatementRequestExcecuteOperation extends BMBCommonOperation {

	private StatementRequestExecuteService statementRequestExecuteService;

	@AuditSupport(auditType = AuditConstant.AUDIT_TYPE_TRANSACTION, activityState = AuditConstant.SRC_COM_SSC, serviceDescription = "SD_RETRIEVE_ACCOUNT_STMT_DATE", stepId = "3", activityType = "statementRequestExecute")
	public StatmentExecuteOperationResponse statmentExecute(StatementExecuteOperationRequest statementExecuteOperationRequest) {

		StatmentExecuteOperationResponse statmentExecuteOperationResponse = new StatmentExecuteOperationResponse()  ;

		StatementRequestDTO statementRequestDTO = statementExecuteOperationRequest.getStatementRequestDTO();

		Context context = statementExecuteOperationRequest.getContext();

		StatmentExecuteServiceRequest statmentExecuteServiceRequest = new StatmentExecuteServiceRequest();
		statmentExecuteServiceRequest.setContext(context);
		statmentExecuteServiceRequest.setAccountDTO(statementRequestDTO.getAccount());
		//statmentExecuteServiceRequest.setBrchCde(statementRequestDTO.getBranchCode());
		statmentExecuteServiceRequest.setFromDate(statementRequestDTO.getFromDate());
		statmentExecuteServiceRequest.setToDate(statementRequestDTO.getToDate());

		loadParameters(context, context.getActivityId(),ActivityConstant.COMMON_ID);

		StatmentExecuteServiceResponse statmentExecuteServiceResponse = statementRequestExecuteService.
																		executeStatmentRequest(statmentExecuteServiceRequest);

		statmentExecuteOperationResponse.setSuccess(statmentExecuteServiceResponse.isSuccess());
		/*statmentExecuteOperationResponse.setActNo(statmentExecuteServiceResponse.getgetActNo());
		statmentExecuteOperationResponse.setBrnCde(statmentExecuteOperationResponse.getBrnCde());
		statmentExecuteOperationResponse.setBrnNam(statmentExecuteOperationResponse)
		statmentExecuteOperationResponse.setTxnRefNo(statmentExecuteServiceResponse.getTxnRefNo());
		statmentExecuteOperationResponse.setTxnMsg(statmentExecuteServiceResponse.getTxnMsg());*/

		return statmentExecuteOperationResponse;
	}


	/**
	 * @return the statementRequestExecuteService
	 */
	public StatementRequestExecuteService getStatementRequestExecuteService() {
		return statementRequestExecuteService;
	}


	/**
	 * @param statementRequestExecuteService the statementRequestExecuteService to set
	 */
	public void setStatementRequestExecuteService(
			StatementRequestExecuteService statementRequestExecuteService) {
		this.statementRequestExecuteService = statementRequestExecuteService;
	}



}
