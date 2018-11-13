package com.barclays.bmg.service;

import com.barclays.bmg.service.request.RetrieveIndCustBySCVIDServiceRequest;
import com.barclays.bmg.service.response.RetrieveIndCustBySCVIDServiceResponse;

public interface RetrieveIndCustBySCVIDService {

    public RetrieveIndCustBySCVIDServiceResponse retrieveIndCustBySCVID(RetrieveIndCustBySCVIDServiceRequest request);
}
