package com.barclays.bmg.service.accountdetails;

import com.barclays.bmg.service.accountdetails.request.CASAAccountActivityServiceRequest;
import com.barclays.bmg.service.accountdetails.request.CASADetailsServiceRequest;
import com.barclays.bmg.service.accountdetails.response.CASAAccountActivityServiceResponse;
import com.barclays.bmg.service.accountdetails.response.CASAAccountTrnxHistoryServiceResponse;
import com.barclays.bmg.service.accountdetails.response.CASADetailsServiceResponse;

public interface CASADetailsService {

    public CASADetailsServiceResponse retrieveCASADetails(CASADetailsServiceRequest request);

    public CASAAccountActivityServiceResponse retrieveCASAAccountActivity(CASAAccountActivityServiceRequest request);

    /**
     * Addded for the Mini Statment
     * 
     * @param request
     * @return
     */
    public CASAAccountTrnxHistoryServiceResponse retrieveAcctTransactionHistory(CASAAccountActivityServiceRequest request);

}
