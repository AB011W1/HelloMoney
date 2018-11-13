package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bem.RetrieveIndividualCustByCCNumber.IndividuaCustomerSearchInfo;
import com.barclays.bem.RetrieveIndividualCustByCCNumber.ProductProcessorDetails;
import com.barclays.bmg.constants.CommonConstants;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.request.RetreiveIndvCustInfoServiceRequest;

public class RetrIndvCustByCCPayloadAdapter {

    public IndividuaCustomerSearchInfo adaptPayload(WorkContext workContext) {

	DAOContext daoContext = (DAOContext) workContext;

	Object[] args = daoContext.getArguments();

	RetreiveIndvCustInfoServiceRequest request = (RetreiveIndvCustInfoServiceRequest) args[0];
	IndividuaCustomerSearchInfo requestBody = new IndividuaCustomerSearchInfo();

	// -- Added as Orchard changes; Need to send additional parameter as
	// 'CARD' in request
	ProductProcessorDetails prodProcessDet = new ProductProcessorDetails();
	prodProcessDet.setProductProcessorId(CommonConstants.CARD);

	requestBody.setCreditCardNumber(request.getCreditCardNo());
	requestBody.setProductProcessorDetails(prodProcessDet);

	return requestBody;

    }
}
