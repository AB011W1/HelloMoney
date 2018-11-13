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

import com.barclays.bmg.service.request.TacticalHelloMoneyVerifyUserServiceRequest;
import com.barclays.bmg.service.response.TacticalHelloMoneyVerifyUserServiceResponse;

public interface TacticalHelloMoneyVerifyUserService {

    public TacticalHelloMoneyVerifyUserServiceResponse verifyUserWithThmPin(TacticalHelloMoneyVerifyUserServiceRequest request);

}
