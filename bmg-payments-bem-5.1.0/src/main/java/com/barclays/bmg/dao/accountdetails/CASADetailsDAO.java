package com.barclays.bmg.dao.accountdetails;

import com.barclays.bmg.service.accountdetails.request.CASAAccountActivityServiceRequest;
import com.barclays.bmg.service.accountdetails.request.CASADetailsServiceRequest;
import com.barclays.bmg.service.accountdetails.response.CASAAccountActivityServiceResponse;
import com.barclays.bmg.service.accountdetails.response.CASAAccountTrnxHistoryServiceResponse;
import com.barclays.bmg.service.accountdetails.response.CASADetailsServiceResponse;

public interface CASADetailsDAO {

    public CASADetailsServiceResponse retrieveCASADetails(CASADetailsServiceRequest request);

    public CASAAccountActivityServiceResponse retrieveCASATransactionActivity(CASAAccountActivityServiceRequest request);

    /**
     * Added for the Mini Statment
     * 
     * @param request
     * @return
     */

    public CASAAccountTrnxHistoryServiceResponse retrieveAcctTransactionHistory(CASAAccountActivityServiceRequest request);
}
