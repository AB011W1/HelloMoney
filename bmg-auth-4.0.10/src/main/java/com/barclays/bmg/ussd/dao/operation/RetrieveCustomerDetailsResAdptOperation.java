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
package com.barclays.bmg.ussd.dao.operation;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bem.IndividualCustomer.IndividualCustomer;
import com.barclays.bem.IndividualCustomerTelephone.IndividualCustomerTelephone;
import com.barclays.bem.IndividualName.IndividualName;
import com.barclays.bem.RetrieveIndividualCustInformation.CustomerInfo;
import com.barclays.bem.RetrieveIndividualCustInformation.RetrieveIndividualCustomerInformationResponse;
import com.barclays.bem.TelephoneAddress.TelephoneAddress;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dao.operation.accountservices.AbstractResAdptOperation;
import com.barclays.bmg.ussd.auth.service.request.RetrieveCustomerDetailsServiceRequest;
import com.barclays.bmg.ussd.auth.service.response.RetrieveCustomerDetailsServiceResponse;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>RetrieveCustomerDetailsResAdptOperation.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 22, 2013</b> </br>
 * ***********************************************************
 * 
 * @author E20043104
 * 
 */
public class RetrieveCustomerDetailsResAdptOperation extends AbstractResAdptOperation {

    /**
     * This method adaptResponseForDetails has the purpose to adapt response for retrieving details
     * 
     * @param workContext
     * @param obj
     * @return RetrieveCustomerDetailsServiceResponse
     */
    public RetrieveCustomerDetailsServiceResponse adaptResponseForDetails(WorkContext workContext, Object obj) {

	DAOContext daoContext = (DAOContext) workContext;
	Object[] args = daoContext.getArguments();
	RetrieveCustomerDetailsServiceRequest retrieveCustomerDetailsServiceRequest = (RetrieveCustomerDetailsServiceRequest) args[0];
	Context context = retrieveCustomerDetailsServiceRequest.getContext();

	RetrieveCustomerDetailsServiceResponse response = new RetrieveCustomerDetailsServiceResponse();
	RetrieveIndividualCustomerInformationResponse bemResponse = (RetrieveIndividualCustomerInformationResponse) obj;

	if (bemResponse != null) {
	    List<String> mobileNumberList = new ArrayList<String>();
	    String resCde = checkBEMAuthResponse(bemResponse.getResponseHeader());

	    if (ResponseCodeConstants.SUCCESS_RES_CODE.equals(resCde)) {

		CustomerInfo customerInfo = bemResponse.getCustomerInfo();
		String mobileNo = null;
		if (customerInfo != null) {

		    IndividualCustomer individualCustomer = customerInfo.getCustomer();

		    if (individualCustomer != null) {

			IndividualCustomerTelephone individualCustomerTelephone[] = individualCustomer.getTelephoneInfo();
			IndividualName individual = individualCustomer.getIndividualName();
			String customerFullName = "";
			if (individual != null) {
			    customerFullName = individual.getFullName();
			}

			context.setFullName(customerFullName);

			// if (individualCustomerTelephone != null && individualCustomerTelephone[0] != null) {
			if (individualCustomerTelephone != null && individualCustomerTelephone.length > 0) {
			    for (IndividualCustomerTelephone individualCustomerTelephone2 : individualCustomerTelephone) {
				TelephoneAddress telephoneAddress = individualCustomerTelephone2.getTelephoneAddress();
				if (telephoneAddress != null) {

				    mobileNo = telephoneAddress.getPhoneNumber();
				    mobileNumberList.add(mobileNo);
				}
			    }

			}
		    }
		}

		response.setContext(context);
		response.setMobileNumberList(mobileNumberList);
		response.setSuccess(true);
		response.setResCde(resCde);
	    } else {
		response.setSuccess(false);
		response.setResCde(resCde);
	    }

	} else {
	    response.setSuccess(false);
	}

	return response;
    }
}
