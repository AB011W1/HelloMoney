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

import com.barclays.bmg.dao.AddProspectDAO;
import com.barclays.bmg.service.AddProspectService;
import com.barclays.bmg.service.request.AddProspectServiceRequest;
import com.barclays.bmg.service.response.AddProspectServiceResponse;

public class AddProspectServiceImpl implements AddProspectService {

    private AddProspectDAO addProspectDAO;

    @Override
    public AddProspectServiceResponse addProspect(AddProspectServiceRequest request) {

	return addProspectDAO.addProspect(request);
    }

    public AddProspectDAO getAddProspectDAO() {
	return addProspectDAO;
    }

    public void setAddProspectDAO(AddProspectDAO addProspectDAO) {
	this.addProspectDAO = addProspectDAO;
    }

}