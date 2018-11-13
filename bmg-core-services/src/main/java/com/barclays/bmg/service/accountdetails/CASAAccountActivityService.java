package com.barclays.bmg.service.accountdetails;

import com.barclays.bmg.service.accountdetails.request.CASAAccountActivityServiceRequest;
import com.barclays.bmg.service.accountdetails.response.CASAAccountActivityServiceResponse;

public interface CASAAccountActivityService {

    public CASAAccountActivityServiceResponse retrieveCASAAccountActivity(CASAAccountActivityServiceRequest request);

}
