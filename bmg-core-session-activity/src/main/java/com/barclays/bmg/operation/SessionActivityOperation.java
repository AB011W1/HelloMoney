package com.barclays.bmg.operation;

import com.barclays.bmg.audit.annotation.AuditSupport;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.operation.request.SessionSummaryOperationRequest;
import com.barclays.bmg.operation.response.SessionSummaryOperationResponse;
import com.barclays.bmg.service.sessionactivity.SessionActivityPersistenceService;
import com.barclays.bmg.service.sessionactivity.SessionSummaryService;
import com.barclays.bmg.service.sessionactivity.SessionSummaryServiceRequest;
import com.barclays.bmg.service.sessionactivity.dto.SessionActivityDTO;
import com.barclays.bmg.utils.DateTimeUtil;

public class SessionActivityOperation {

	private SessionSummaryService sessionSummaryService;

	private SessionActivityPersistenceService sessionActivityPersistenceService;

	@AuditSupport(auditType = AuditConstant.AUDIT_TYPE_TRANSACTION, activityState = AuditConstant.SRC_COM_SSC, serviceDescription = "SD_SYSTEM_ACCESS", stepId = "1",  activityType = "auditSystemAccess")
	public SessionSummaryOperationResponse getSessionSummary(
			SessionSummaryOperationRequest sessionSummaryOperationRequest) {

		Context context = sessionSummaryOperationRequest.getContext();

		SessionSummaryServiceRequest sessionSummaryServiceRequest = new SessionSummaryServiceRequest();
		sessionSummaryServiceRequest.setContext(context);

		SessionSummaryOperationResponse sessionSummaryOperationResponse = new SessionSummaryOperationResponse();
		sessionSummaryOperationResponse
				.setSessionSummaryDTO(sessionSummaryService
						.getSessionSummary(sessionSummaryServiceRequest));
		sessionSummaryOperationResponse.setContext(context);
		return sessionSummaryOperationResponse;
	}

	public void persistLogoutInformation(
			SessionSummaryOperationRequest sessionSummaryOperationRequest) {
		SessionActivityDTO sessionActivityDTO = new SessionActivityDTO();
		Context context = sessionSummaryOperationRequest.getContext();

		sessionActivityDTO.setBusinessId(context.getBusinessId());
		sessionActivityDTO.setSessionId(context.getSessionId());
		sessionActivityDTO.setSystemId(context.getSystemId());
		sessionActivityDTO.setTxnTyp(SessionActivityDTO.TXN_LOG_OUT);
		sessionActivityDTO.setTxnTime(DateTimeUtil.getCurrentBusinessCalendar(
				context).getTime());
		sessionActivityDTO.setUserId(context.getUserId());
		sessionActivityPersistenceService
				.persistSessionActivity(sessionActivityDTO);

	}

	public void persistLoginInformation(
			SessionSummaryOperationRequest sessionSummaryOperationRequest) {
		SessionActivityDTO sessionActivityDTO = new SessionActivityDTO();
		Context context = sessionSummaryOperationRequest.getContext();

		sessionActivityDTO.setBusinessId(context.getBusinessId());
		sessionActivityDTO.setSessionId(context.getSessionId());
		sessionActivityDTO.setSystemId(context.getSystemId());
		sessionActivityDTO.setTxnTyp(SessionActivityDTO.TXN_LOG_IN);
		sessionActivityDTO.setUserId(context.getUserId());
		sessionActivityDTO.setTxnTime(DateTimeUtil.getCurrentBusinessCalendar(
				context).getTime());
		sessionActivityPersistenceService
				.persistSessionActivity(sessionActivityDTO);

	}

	public SessionSummaryService getSessionSummaryService() {
		return sessionSummaryService;
	}

	public void setSessionSummaryService(
			SessionSummaryService sessionSummaryService) {
		this.sessionSummaryService = sessionSummaryService;
	}

	public SessionActivityPersistenceService getSessionActivityPersistenceService() {
		return sessionActivityPersistenceService;
	}

	public void setSessionActivityPersistenceService(
			SessionActivityPersistenceService sessionActivityPersistenceService) {
		this.sessionActivityPersistenceService = sessionActivityPersistenceService;
	}

}
