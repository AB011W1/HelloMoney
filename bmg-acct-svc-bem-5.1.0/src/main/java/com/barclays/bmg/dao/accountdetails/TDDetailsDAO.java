package com.barclays.bmg.dao.accountdetails;

import com.barclays.bmg.service.accountdetails.request.TDDetailsServiceRequest;
import com.barclays.bmg.service.accountdetails.response.TDDetailsServiceResponse;

public interface TDDetailsDAO {

    public TDDetailsServiceResponse retreiveTDDetails(TDDetailsServiceRequest request);
}
