package com.barclays.bmg.dao;

import com.barclays.bmg.service.request.RetrieveIndCustBySCVIDServiceRequest;
import com.barclays.bmg.service.response.RetrieveIndCustBySCVIDServiceResponse;

public interface RetrieveIndCustBySCVIDDAO {

    public RetrieveIndCustBySCVIDServiceResponse retrieveIndCustBySCVID(RetrieveIndCustBySCVIDServiceRequest request);

}
