package com.barclays.bmg.service.sessionactivity.dao;

import java.util.List;

import com.barclays.bmg.service.sessionactivity.SessionSummaryServiceRequest;
import com.barclays.bmg.service.sessionactivity.dto.SessionActivityDTO;

public interface SessionActivityDAO {

	void insert(SessionActivityDTO summary);

	List<SessionActivityDTO> findBySession(SessionSummaryServiceRequest req);

	List<SessionActivityDTO> findByUser(SessionSummaryServiceRequest req);

}
