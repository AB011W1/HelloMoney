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
 * Package name is com.barclays.bmg.service.request
 * /
 */
package com.barclays.bmg.service.request;

import com.barclays.bmg.context.RequestContext;

/**
 * ****************** Revision History ********************** </br> Project Name is <b>bmg-core-services</b> </br> The file name is
 * <b>RetrieveAllCustAcctServiceRequest.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 15, 2013</b> </br>
 * ******************************************************
 * 
 * @author e20037686 </br> * The Class RetrieveAllCustAcctServiceRequest created using JRE 1.6.0_10
 */
public class RetrieveAllCustAcctServiceRequest extends RequestContext {
    private String accountType;

    public String getAccountType() {
	return accountType;
    }

    public void setAccountType(String accountType) {
	this.accountType = accountType;
    }

}