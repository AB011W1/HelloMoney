package com.barclays.bmg.ussd.dao.operation;

import com.barclays.bem.RegisterCustomerToProductOneWay.RegisterCustomerToProductRequestOneWay;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.ussd.dao.adapter.RegisterCustomerToProductHeaderAdapter;
import com.barclays.bmg.ussd.dao.adapter.RegisterCustomerToProductPayloadAdapter;

public class RegisterCustomerToProductReqAdptOperation {

	private RegisterCustomerToProductHeaderAdapter registerCustomerToProductHeaderAdapter;
	private RegisterCustomerToProductPayloadAdapter registerCustomerToProductPayloadAdapter;

	public final RegisterCustomerToProductRequestOneWay adaptRequestForRegisterCustomerToProduct(
			final WorkContext context) {

		RegisterCustomerToProductRequestOneWay request = new RegisterCustomerToProductRequestOneWay();

		request.setRequestHeader(registerCustomerToProductHeaderAdapter
				.buildRequestHeader(context));

       request.setRequest(registerCustomerToProductPayloadAdapter.adaptPayLoad(context));
		return request;

	}

	public void setRegisterCustomerToProductHeaderAdapter(
			RegisterCustomerToProductHeaderAdapter registerCustomerToProductHeaderAdapter) {
		this.registerCustomerToProductHeaderAdapter = registerCustomerToProductHeaderAdapter;
	}

	public void setRegisterCustomerToProductPayloadAdapter(
			RegisterCustomerToProductPayloadAdapter registerCustomerToProductPayloadAdapter) {
		this.registerCustomerToProductPayloadAdapter = registerCustomerToProductPayloadAdapter;
	}
}
