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

import com.barclays.bmg.dao.TacticalHelloMoneyEncryptPinDAO;
import com.barclays.bmg.service.TacticalHelloMoneyEncryptPinService;
import com.barclays.bmg.service.request.TacticalHelloMoneyEncryptPinServiceRequest;
import com.barclays.bmg.service.response.TacticalHelloMoneyEncryptPinServiceResponse;

public class TacticalHelloMoneyEncryptPinServiceImpl implements TacticalHelloMoneyEncryptPinService {
    private TacticalHelloMoneyEncryptPinDAO thmEncryptPinDAO;

    @Override
    public TacticalHelloMoneyEncryptPinServiceResponse encryptThmPin(TacticalHelloMoneyEncryptPinServiceRequest request) {
	return thmEncryptPinDAO.encryptPin(request);
    }

    public TacticalHelloMoneyEncryptPinDAO getThmEncryptPinDAO() {
	return thmEncryptPinDAO;
    }

    public void setThmEncryptPinDAO(TacticalHelloMoneyEncryptPinDAO thmEncryptPinDAO) {
	this.thmEncryptPinDAO = thmEncryptPinDAO;
    }

}