package com.barclays.bmg.ussd.dao.adapter;

import com.barclays.bem.BEMServiceHeader.BEMReqHeader;
import com.barclays.bem.BEMServiceHeader.RoutingIndicator;
import com.barclays.bmg.dao.adapter.request.AbstractReqAdptOperation;
import com.barclays.bmg.dao.core.context.WorkContext;

public class RegisterCustomerToProductHeaderAdapter extends
		AbstractReqAdptOperation {

	public static final String REGISTER_CUSTOMER_TO_PRODUCT = "RegisterCustomerToProductOneWay";

	public BEMReqHeader buildRequestHeader(WorkContext workContext) {

		BEMReqHeader requestHeader = super.buildRequestHeader(workContext,
				REGISTER_CUSTOMER_TO_PRODUCT);

		// Setting routing Indicator in request header
		RoutingIndicator routingIndicator = new RoutingIndicator();
		routingIndicator.setIndicator("BankAccountRegistrationAM");
		requestHeader.setRoutingIndicator(routingIndicator);

		return requestHeader;

	}

}
