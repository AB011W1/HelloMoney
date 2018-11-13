package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bem.RetrieveIndividualCustBeneficiaryList.IndividualCustomerBeneficiaryInfo;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.request.RetreivePayeeListServiceRequest;

public class RetreiveIndvPayeeListPayloadAdapter {

    public IndividualCustomerBeneficiaryInfo adaptPayLoad(WorkContext workContext) {

	IndividualCustomerBeneficiaryInfo requestBody = new IndividualCustomerBeneficiaryInfo();

	DAOContext daoContext = (DAOContext) workContext;

	Object[] args = daoContext.getArguments();

	RetreivePayeeListServiceRequest retreivePayeeListServiceRequest = (RetreivePayeeListServiceRequest) args[0];
	Context context = retreivePayeeListServiceRequest.getContext();
	requestBody.setCustomerNumber(context.getCustomerId());
	requestBody.setIncludeDeletedRecordIndicator(Boolean.TRUE);
	return requestBody;
    }
}
