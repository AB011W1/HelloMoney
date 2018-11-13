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
package com.barclays.bmg.service.impl;

import com.barclays.bmg.dao.RetrieveAllCustAcctDAO;
import com.barclays.bmg.service.RetrieveAllCustAcctService;
import com.barclays.bmg.service.request.RetrieveAllCustAcctServiceRequest;
import com.barclays.bmg.service.response.RetrieveAllCustAcctServiceResponse;

/**
 * ****************** Revision History ********************** </br> Project Name is <b>bmg-core-services</b> </br> The file name is
 * <b>RetrieveAllCustAcctServiceImpl.java</b> </br> Description is <b>V 1.2</b> </br> Updated Date is <b>Jun 4, 2013</b> </br>
 * ******************************************************
 * 
 * @author e20037686 </br> * The Class RetrieveAllCustAcctServiceImpl created using JRE 1.6.0_10
 */
public class RetrieveAllCustAcctServiceImpl implements RetrieveAllCustAcctService {

    /**
     * The instance variable named "retrieveAllCustAcctDAO" is created.
     */
    private RetrieveAllCustAcctDAO retrieveAllCustAcctDAO;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.barclays.bmg.service.RetrieveAllCustAcctService#retrieveAllCustAccount(com.barclays.bmg.service.request.RetrieveAllCustAcctServiceRequest)
     */
    @Override
    public RetrieveAllCustAcctServiceResponse retrieveAllCustAccount(RetrieveAllCustAcctServiceRequest request) {
	// TODO Auto-generated method stub

	return retrieveAllCustAcctDAO.retrieveAllCustAccount(request);
    }

    /**
     * Gets the retrieve all cust acct dao.
     * 
     * @return the RetrieveAllCustAcctDAO
     */
    public RetrieveAllCustAcctDAO getRetrieveAllCustAcctDAO() {
	return retrieveAllCustAcctDAO;
    }

    /**
     * Sets values for RetrieveAllCustAcctDAO.
     * 
     * @param retrieveAllCustAcctDAO
     *            the retrieve all cust acct dao
     */
    public void setRetrieveAllCustAcctDAO(RetrieveAllCustAcctDAO retrieveAllCustAcctDAO) {
	this.retrieveAllCustAcctDAO = retrieveAllCustAcctDAO;
    }
}