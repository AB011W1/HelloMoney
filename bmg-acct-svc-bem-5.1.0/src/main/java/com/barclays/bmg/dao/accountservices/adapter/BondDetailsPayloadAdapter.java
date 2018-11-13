package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bem.BEMBaseDataTypes.ProductProcessorTypeCode;
import com.barclays.bem.RetrieveInvestmentAcctDetailsByCustNo.IndividualCustomerInfo;
import com.barclays.bem.RetrieveInvestmentAcctDetailsByCustNo.RetrieveInvestmentAccountDetailsByCustomerNoRequest;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.accountdetails.request.BondDetailsServiceRequest;

public class BondDetailsPayloadAdapter {

    public void adaptPayLoad(WorkContext workContext, RetrieveInvestmentAccountDetailsByCustomerNoRequest retInvAvtDetByCustNoReq) {

	DAOContext daoContext = (DAOContext) workContext;
	Object[] args = daoContext.getArguments();

	BondDetailsServiceRequest bondDetailsServiceRequest = (BondDetailsServiceRequest) args[0];

	IndividualCustomerInfo individualCustomerInfo = new IndividualCustomerInfo();

	if (individualCustomerInfo != null) {

	    individualCustomerInfo.setAssetTypeCode(bondDetailsServiceRequest.getAssetClass());

	    individualCustomerInfo.setCustomerNumber(bondDetailsServiceRequest.getContext().getPpMap().get(ProductProcessorTypeCode._IN));

	    retInvAvtDetByCustNoReq.setIndividualCustomerInfo(individualCustomerInfo);
	}

    }

}
