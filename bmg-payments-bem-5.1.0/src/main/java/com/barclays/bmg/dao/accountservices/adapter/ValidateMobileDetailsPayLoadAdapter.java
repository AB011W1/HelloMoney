package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bem.RetrieveMobileDetails.IndividualCustomerInfo;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.request.MWalletValidateServiceRequest;


public class ValidateMobileDetailsPayLoadAdapter {

	public com.barclays.bem.RetrieveMobileDetails.IndividualCustomerInfo adaptPayLoad(WorkContext workContext) {
		DAOContext daoContext = (DAOContext) workContext;

		Object[] args = daoContext.getArguments();

		MWalletValidateServiceRequest serRequest = (MWalletValidateServiceRequest) args[0];
//		/Context context = serRequest.getContext();

		IndividualCustomerInfo info = new IndividualCustomerInfo();
		info.setMobileNumber(serRequest.getPayeeNumber());
		
		return info;
	}
	
	
}
