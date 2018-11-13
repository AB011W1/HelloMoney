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

import com.barclays.bmg.dao.UssdLgnVrfyUsrPnDAO;
import com.barclays.bmg.service.UssdLgnVrfyUsrPnService;
import com.barclays.bmg.service.request.UssdLgnVrfyUsrPnServiceRequest;
import com.barclays.bmg.service.response.UssdLgnVrfyUsrPnServiceResponse;

/**
 * ****************** Revision History ********************** </br> Project Name is <b>bmg-core-services</b> </br> The file name is
 * <b>UssdLgnVrfyUsrPnServiceImpl.java</b> </br> Description is <b>V 1.2</b> </br> Updated Date is <b>Jun 13, 2013</b> </br>
 * ******************************************************
 * 
 * @author e20037686 </br> * The Class UssdLgnVrfyUsrPnServiceImpl created using JRE 1.6.0_10
 */
public class UssdLgnVrfyUsrPnServiceImpl implements UssdLgnVrfyUsrPnService {
    private UssdLgnVrfyUsrPnDAO ussdLgnVrfyUsrPnDAO;

    /*
     * (non-Javadoc)
     * 
     * @see com.barclays.bmg.service.UssdLgnVrfyUsrPnService#verifyUserWithPin(com.barclays.bmg.service.request.UssdLgnVrfyUsrPnServiceRequest)
     */
    @Override
    public UssdLgnVrfyUsrPnServiceResponse verifyUserWithPin(UssdLgnVrfyUsrPnServiceRequest request) {
	return ussdLgnVrfyUsrPnDAO.verifyUserWithPin(request);
    }

    /**
     * @return the ussdLgnVrfyUsrPnDAO
     */
    public UssdLgnVrfyUsrPnDAO getUssdLgnVrfyUsrPnDAO() {
	return ussdLgnVrfyUsrPnDAO;
    }

    /**
     * @param ussdLgnVrfyUsrPnDAO
     *            the ussdLgnVrfyUsrPnDAO to set
     */
    public void setUssdLgnVrfyUsrPnDAO(UssdLgnVrfyUsrPnDAO ussdLgnVrfyUsrPnDAO) {
	this.ussdLgnVrfyUsrPnDAO = ussdLgnVrfyUsrPnDAO;
    }
}