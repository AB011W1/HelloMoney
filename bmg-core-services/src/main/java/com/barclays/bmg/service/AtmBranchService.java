package com.barclays.bmg.service;

import com.barclays.bmg.service.request.AtmBranchServiceRequest;
import com.barclays.bmg.service.response.AtmBranchServiceResponse;

public interface AtmBranchService {

    public AtmBranchServiceResponse retrieveBranchList(AtmBranchServiceRequest request);

    public AtmBranchServiceResponse retrieveATMList(AtmBranchServiceRequest request);

    public AtmBranchServiceResponse retrieveAtmBranchList(AtmBranchServiceRequest request);

}
