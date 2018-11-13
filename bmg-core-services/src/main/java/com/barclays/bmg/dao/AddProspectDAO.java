/**
 *
 */
package com.barclays.bmg.dao;

import com.barclays.bmg.service.request.AddProspectServiceRequest;
import com.barclays.bmg.service.response.AddProspectServiceResponse;

public interface AddProspectDAO {

    public AddProspectServiceResponse addProspect(AddProspectServiceRequest request);
}