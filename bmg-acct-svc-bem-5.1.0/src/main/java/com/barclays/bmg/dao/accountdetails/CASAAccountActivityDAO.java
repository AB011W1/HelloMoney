package com.barclays.bmg.dao.accountdetails;

import com.barclays.bmg.service.accountdetails.request.CASAAccountActivityServiceRequest;
import com.barclays.bmg.service.accountdetails.response.CASAAccountActivityServiceResponse;

public interface CASAAccountActivityDAO {

    public CASAAccountActivityServiceResponse retrieveCASADetails(CASAAccountActivityServiceRequest request);

}
