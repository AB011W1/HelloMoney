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
package com.barclays.bmg.ussd.service.impl;

import com.barclays.bmg.ussd.auth.service.request.SelfRegistrationExecutionServiceRequest;
import com.barclays.bmg.ussd.auth.service.response.SelfRegistrationExecutionServiceResponse;
import com.barclays.bmg.ussd.dao.SelfRegistrationExecutionDAO;
import com.barclays.bmg.ussd.service.SelfRegistrationExecutionService;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>SelfRegistrationExecutionServiceImpl.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 22, 2013</b> </br>
 * ***********************************************************
 * 
 * @author E20043104
 * 
 */

public class SelfRegistrationExecutionServiceImpl implements SelfRegistrationExecutionService {

    /**
     * The instance variable for selfRegistrationExecutionDAO of type SelfRegistrationExecutionDAO
     */
    private SelfRegistrationExecutionDAO selfRegistrationExecutionDAO;

    /**
     * Getter for SelfRegistrationExecutionDAO
     * 
     *@param none
     *@return SelfRegistrationExecutionDAO
     */
    public SelfRegistrationExecutionDAO getSelfRegistrationExecutionDAO() {
	return selfRegistrationExecutionDAO;
    }

    /**
     * Setter for SelfRegistrationExecutionDAO
     * 
     * @param SelfRegistrationExecutionDAO
     * @return void
     */
    public void setSelfRegistrationExecutionDAO(SelfRegistrationExecutionDAO selfRegistrationExecutionDAO) {
	this.selfRegistrationExecutionDAO = selfRegistrationExecutionDAO;
    }

    /**
     * This implementation method getCustomerDetailsAndRegister has the purpose to get customer details and execute registration of customer to hello
     * money.
     * 
     * @param serviceRequest
     * @return SelfRegistrationExecutionServiceResponse
     */
    @Override
    public SelfRegistrationExecutionServiceResponse getCustomerDetailsAndRegister(SelfRegistrationExecutionServiceRequest serviceRequest) {

	return selfRegistrationExecutionDAO.getCustomerDetailsAndRegister(serviceRequest);
    }

}
