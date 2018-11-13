package com.barclays.bmg.service;

import com.barclays.bmg.service.request.AddProspectServiceRequest;
import com.barclays.bmg.service.response.AddProspectServiceResponse;

public interface AddProspectService {

    public AddProspectServiceResponse addProspect(AddProspectServiceRequest request);
}
