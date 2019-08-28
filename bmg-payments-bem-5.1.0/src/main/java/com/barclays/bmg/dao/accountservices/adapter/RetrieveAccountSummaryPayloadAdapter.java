package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bem.BEMBaseDataTypes.ProductProcessorTypeCode;
import com.barclays.bem.RetrieveIndividualCustAcctList.IndividualCustomerInfo;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.accounts.request.AllAccountServiceRequest;

public class RetrieveAccountSummaryPayloadAdapter {

    public IndividualCustomerInfo adaptPayLoad(WorkContext workContext) {

	DAOContext daoContext = (DAOContext) workContext;
	Object[] args = daoContext.getArguments();
	AllAccountServiceRequest allAccountServiceRequest = (AllAccountServiceRequest) args[0];
	Context context = allAccountServiceRequest.getContext();

	IndividualCustomerInfo individualCustomerInfo = new IndividualCustomerInfo();
	individualCustomerInfo.setCustomerNumber(context.getCustomerId());

	if ("CH".equals(allAccountServiceRequest.getAccountType())) {
	    individualCustomerInfo.setProductGroupTypeCode(new String[] { "CH" });
	    individualCustomerInfo.setProductProcessorTypeCode(ProductProcessorTypeCode.BK);
	} else if ("CC".equals(allAccountServiceRequest.getAccountType())) {
	    individualCustomerInfo.setProductGroupTypeCode(new String[] { "CC" });
	}
	return individualCustomerInfo;
    }
}
