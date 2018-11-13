package com.barclays.bmg.chequebook.operation;

import com.barclays.bmg.audit.annotation.AuditSupport;
import com.barclays.bmg.chequebook.operation.request.ChequeBookExecuteOperationRequest;
import com.barclays.bmg.chequebook.operation.response.ChequeBookExecuteOperationResponse;
import com.barclays.bmg.chequebook.service.ChequeBookRequestExecuteService;
import com.barclays.bmg.chequebook.service.request.ChequeBookExecuteServiceRequest;
import com.barclays.bmg.chequebook.service.response.ChequeBookExecuteServiceResponse;
import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.operation.BMBCommonOperation;

public class ChequeBookRequestExecuteOperation extends BMBCommonOperation {

	private ChequeBookRequestExecuteService chequeBookRequestExecuteService;

	@AuditSupport(auditType = AuditConstant.AUDIT_TYPE_TRANSACTION, activityState = AuditConstant.SRC_COM_SSC, serviceDescription = "SD_REQ_CHECK_BOOK", stepId = "3", activityType = "auditChequeBookRequest")
	public ChequeBookExecuteOperationResponse chequeBookExecute(
			ChequeBookExecuteOperationRequest request) {
		ChequeBookExecuteOperationResponse chequeBookExecuteOperationResponse = new ChequeBookExecuteOperationResponse();

		ChequeBookExecuteServiceRequest chequeBookExecuteServiceRequest = new ChequeBookExecuteServiceRequest();
		Context context = request.getContext();

		chequeBookExecuteServiceRequest.setContext(context);

		chequeBookExecuteServiceRequest.setChequeBookRequestDTO(request
				.getChequeBookRequestDTO());

		loadParameters(context, context.getActivityId(),
				ActivityConstant.COMMON_ID);

		ChequeBookExecuteServiceResponse chequeBookExecuteServiceResponse = chequeBookRequestExecuteService
				.executeChequeBookRequest(chequeBookExecuteServiceRequest);
		//System.out.println(chequeBookExecuteServiceResponse.getTxnRefNo());

		chequeBookExecuteOperationResponse
				.setSuccess(chequeBookExecuteServiceResponse.isSuccess());
		chequeBookExecuteOperationResponse
				.setTxnDt(chequeBookExecuteServiceResponse.getTxnDt());
		chequeBookExecuteOperationResponse
				.setTxnRefNo(chequeBookExecuteOperationResponse.getTxnRefNo());

		chequeBookExecuteOperationResponse
				.setTxnMsg(chequeBookExecuteServiceResponse.getTxnMsg());
		//System.out.println(chequeBookExecuteServiceResponse.getTxnRefNo());

		return chequeBookExecuteOperationResponse;
	}

	public ChequeBookRequestExecuteService getChequeBookRequestExecuteService() {
		return chequeBookRequestExecuteService;
	}

	public void setChequeBookRequestExecuteService(
			ChequeBookRequestExecuteService chequeBookRequestExecuteService) {
		this.chequeBookRequestExecuteService = chequeBookRequestExecuteService;
	}

}
