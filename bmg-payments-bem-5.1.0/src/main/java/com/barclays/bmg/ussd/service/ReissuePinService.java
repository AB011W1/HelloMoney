/*
 * Copyright (c) 2013 Barclays Bank Plc, All Rights Reserved.
 *
 * This code is confidential to Barclays Bank Plc and shall not be disclosed
 * outside the Bank without the prior written permission of the Director of
 * CIO
 *
 * In the event that such disclosure is permitted the code shall not be copied
 * or distributed other than on a need-to-know basis and any recipients may be
 * required to sign a confidentiality undertaking in favour of Barclays Bank
 * Plc.
 */

package com.barclays.bmg.ussd.service;

import com.barclays.bmg.ussd.auth.service.request.ReissuePinServiceRequest;
import com.barclays.bmg.ussd.auth.service.response.ReissuePinServiceResponse;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>ReissuePinService.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 22, 2013</b> </br>
 * ***********************************************************
 * 
 * @author E20043104
 * 
 */
public interface ReissuePinService {

    /**
     * This contract method reissuePin has the purpose to create pin using bem service.
     * 
     * @param ReissuePinServiceRequest
     * @return ReissuePinServiceResponse
     */
    public ReissuePinServiceResponse reissuePin(ReissuePinServiceRequest serviceRequest);
}
