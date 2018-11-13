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
package com.barclays.bmg.ussd.dao;

import com.barclays.bmg.ussd.auth.service.request.UpdateDetailstoMCEServiceRequest;
import com.barclays.bmg.ussd.auth.service.response.UpdateDetailstoMCEServiceResponse;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>ReissuePinDAO.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>November 22, 2013</b> </br>
 * ***********************************************************
 * 
 * @author E20045924
 * 
 */
public interface UpdateDetailstoMCEDAO {

    /**
     * This contract method updateDetailsToMCE has the purpose to update details to mce using bem service.
     * 
     * @param serviceRequest
     * @return UpdateDetailstoMCEServiceResponse
     */
    public UpdateDetailstoMCEServiceResponse updateDetailsToMCE(UpdateDetailstoMCEServiceRequest serviceRequest);

}
