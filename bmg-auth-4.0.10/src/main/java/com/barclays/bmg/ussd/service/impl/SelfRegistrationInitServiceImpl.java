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

import com.barclays.bmg.ussd.auth.service.request.SelfRegistrationInitServiceRequest;
import com.barclays.bmg.ussd.auth.service.response.SelfRegistrationInitServiceResponse;
import com.barclays.bmg.ussd.dao.SelfRegistrationInitDAO;
import com.barclays.bmg.ussd.service.SelfRegistrationInitService;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>SelfRegistrationInitServiceImpl.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 22, 2013</b> </br>
 * ***********************************************************
 * 
 * @author E20043104
 * 
 */
public class SelfRegistrationInitServiceImpl implements SelfRegistrationInitService {

    private SelfRegistrationInitDAO selfRegistrationInitDAO;

    /**
     * Getter for SelfRegistrationInitDAO
     * 
     *@param none
     *@return SelfRegistrationInitDAO
     */
    public SelfRegistrationInitDAO getSelfRegistrationInitDAO() {
	return selfRegistrationInitDAO;
    }

    /**
     * Setter for SelfRegistrationInitDAO
     * 
     * @param SelfRegistrationInitDAO
     * @return void
     */
    public void setSelfRegistrationInitDAO(SelfRegistrationInitDAO selfRegistrationInitDAO) {
	this.selfRegistrationInitDAO = selfRegistrationInitDAO;
    }

    /**
     * This implementation method createCustomerInCrypto has the purpose to make a call to DAO for creating customer in crypto.
     * 
     * @param serviceRequest
     * @return SelfRegistrationInitServiceResponse
     */
    @Override
    public SelfRegistrationInitServiceResponse createCustomerInCrypto(SelfRegistrationInitServiceRequest serviceRequest) {

	return selfRegistrationInitDAO.createCustomerInCrypto(serviceRequest);
    }

}
