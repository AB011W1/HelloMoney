package com.barclays.bmg.dao;

import com.barclays.bmg.service.request.SendMultipleNotificationsServiceRequest;
import com.barclays.bmg.service.response.SendMultipleNotificationsServiceResponse;

public interface SendMultipleNotificationsDAO {
	public SendMultipleNotificationsServiceResponse sendMultipleNotifications(
			SendMultipleNotificationsServiceRequest sendMultipleNotificationsServiceRequest);
}
