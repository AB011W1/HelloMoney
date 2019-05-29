/**
 *
 */
package com.barclays.bmg.service.accounts;

import com.barclays.bmg.service.request.SendMultipleNotificationsServiceRequest;
import com.barclays.bmg.service.response.SendMultipleNotificationsServiceResponse;

/**
 * @author G01097329
 *
 */
public interface SendMultipleNotificationsService {
	SendMultipleNotificationsServiceResponse sendMultipleNotifications(
			SendMultipleNotificationsServiceRequest sendMultipleNotificationsServiceRequest);
}
