package com.barclays.bmg.operation.response;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.service.sessionactivity.dto.SessionSummaryDTO;

public class SessionSummaryOperationResponse extends ResponseContext {
    /**
	 *
	 */
    private static final long serialVersionUID = 4426162470395387497L;
    private SessionSummaryDTO sessionSummaryDTO;

    public SessionSummaryDTO getSessionSummaryDTO() {
	return sessionSummaryDTO;
    }

    public void setSessionSummaryDTO(SessionSummaryDTO sessionSummaryDTO) {
	this.sessionSummaryDTO = sessionSummaryDTO;
    }
}