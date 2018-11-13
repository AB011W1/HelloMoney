package com.barclays.bmg.service.sessionactivity;

import com.barclays.bmg.service.sessionactivity.dto.SessionSummaryDTO;

public interface SessionSummaryService {

	SessionSummaryDTO getSessionSummary(SessionSummaryServiceRequest sessionActivityServiceRequest);

}
