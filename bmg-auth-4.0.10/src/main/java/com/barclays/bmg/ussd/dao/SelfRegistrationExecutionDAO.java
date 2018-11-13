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

import com.barclays.bmg.ussd.auth.service.request.SelfRegistrationExecutionServiceRequest;
import com.barclays.bmg.ussd.auth.service.response.SelfRegistrationExecutionServiceResponse;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>SelfRegistrationExecutionDAO.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 22, 2013</b> </br>
 * ***********************************************************
 * 
 * @author E20043104
 * 
 */
public interface SelfRegistrationExecutionDAO {

    /**
     * This contract method getCustomerDetailsAndRegister has the purpose to get customer details and execute registration of customer to hello money
     * by the bem service.
     * 
     * @param serviceRequest
     * @return SelfRegistrationExecutionServiceResponse
     */
    public SelfRegistrationExecutionServiceResponse getCustomerDetailsAndRegister(SelfRegistrationExecutionServiceRequest serviceRequest);

}
