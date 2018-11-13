package com.barclays.bmg.service.sessionactivity;

import com.barclays.bmg.service.sessionactivity.dto.SessionActivityDTO;

public interface SessionActivityPersistenceService {

	void persistSessionActivity(SessionActivityDTO sessionActivityDTO);

}
