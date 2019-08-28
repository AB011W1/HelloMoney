package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bem.RetrieveIndividualCustBySCVID.IndividuaCustomerSearchInfo;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.request.RetrieveIndCustBySCVIDServiceRequest;

public class RetrieveIndCustBySCVIDPayloadAdapter {

    public IndividuaCustomerSearchInfo adaptPayLoad(WorkContext workContext) {

	DAOContext daoContext = (DAOContext) workContext;
	Object[] args = daoContext.getArguments();

	RetrieveIndCustBySCVIDServiceRequest retrieveIndCustBySCVIDServiceRequest = (RetrieveIndCustBySCVIDServiceRequest) args[0];

	IndividuaCustomerSearchInfo individuaCustomerSearchInfo = new IndividuaCustomerSearchInfo();
	individuaCustomerSearchInfo.setSCVID(retrieveIndCustBySCVIDServiceRequest.getCustomer().getCustomerID());
	return individuaCustomerSearchInfo;
    }

}
