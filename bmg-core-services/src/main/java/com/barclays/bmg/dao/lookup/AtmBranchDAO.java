package com.barclays.bmg.dao.lookup;

import com.barclays.bmg.service.request.AtmBranchServiceRequest;
import com.barclays.bmg.service.response.AtmBranchServiceResponse;

public interface AtmBranchDAO {

    public AtmBranchServiceResponse retrieveBranchList(AtmBranchServiceRequest atmBranchServiceRequest);

    public AtmBranchServiceResponse retrieveATMList(AtmBranchServiceRequest atmBranchServiceRequest);

}
