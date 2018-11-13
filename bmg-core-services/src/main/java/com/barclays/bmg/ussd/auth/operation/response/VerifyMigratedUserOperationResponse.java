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
package com.barclays.bmg.ussd.auth.operation.response;

import com.barclays.bmg.context.ResponseContext;

public class VerifyMigratedUserOperationResponse extends ResponseContext {

    /**
	 *
	 */
    private static final long serialVersionUID = 5111658142863696788L;
    private String scvid;
    private String serviceResponse;

    public String getServiceResponse() {
	return serviceResponse;
    }

    public void setServiceResponse(String serviceResponse) {
	this.serviceResponse = serviceResponse;
    }

    public String getScvid() {
        return scvid;
    }

    public void setScvid(String scvid) {
        this.scvid = scvid;
    }



}