package com.barclays.bmg.service.sessionactivity.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barclays.bmg.constants.SqlMapSystemConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dao.impl.BaseDAOImpl;
import com.barclays.bmg.service.sessionactivity.SessionSummaryServiceRequest;
import com.barclays.bmg.service.sessionactivity.dao.SessionActivityDAO;
import com.barclays.bmg.service.sessionactivity.dto.SessionActivityDTO;

public class SessionActivityDAOImpl extends BaseDAOImpl implements
		SessionActivityDAO {

	@Override
	public List<SessionActivityDTO> findBySession(
			SessionSummaryServiceRequest req) {
		Map<String, String> params = new HashMap<String, String>();
		Context context = req.getContext();
		params.put("systemId", context.getSystemId());
		params.put("businessId", context.getBusinessId());
		params.put("userId", context.getUserId());
		params.put("sessionId", context.getSessionId());

		return this.queryForList(
				SqlMapSystemConstants.SESSION_ACTIVITY_FIND_BY_SESSION, params);
	}

	@Override
	public List<SessionActivityDTO> findByUser(SessionSummaryServiceRequest req) {

		Map<String, String> params = new HashMap<String, String>();
		Context context = req.getContext();
		params.put("systemId", context.getSystemId());
		params.put("businessId", context.getBusinessId());
		params.put("activityId", context.getActivityId());
		params.put("userId", context.getUserId());
		params.put("txnTyp", context.getActivityId());
		params.put("userId", context.getUserId());
		return this.queryForList(
				SqlMapSystemConstants.SESSION_ACTIVITY_FIND_BY_USER, params);
	}

	@Override
	public void insert(SessionActivityDTO sessionActivity) {
		this.insert(SqlMapSystemConstants.SESSION_ACTIVITY_INSERT,
				sessionActivity);

	}

}
