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
/**
 * Package name is com.barclays.bmg.service
 * /
 */
package com.barclays.bmg.service;

import com.barclays.bmg.service.request.TacticalHelloMoneyEncryptPinServiceRequest;
import com.barclays.bmg.service.response.TacticalHelloMoneyEncryptPinServiceResponse;

public interface TacticalHelloMoneyEncryptPinService {

    public TacticalHelloMoneyEncryptPinServiceResponse encryptThmPin(TacticalHelloMoneyEncryptPinServiceRequest request);
}
