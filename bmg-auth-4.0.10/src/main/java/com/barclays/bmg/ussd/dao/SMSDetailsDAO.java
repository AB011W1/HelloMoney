package com.barclays.bmg.ussd.dao;

import com.barclays.bmg.ussd.auth.service.request.SMSDetailsServiceRequest;
import com.barclays.bmg.ussd.auth.service.response.SMSDetailsServiceResponse;

public interface SMSDetailsDAO {

    public SMSDetailsServiceResponse getSMSDetails(SMSDetailsServiceRequest request);
}
