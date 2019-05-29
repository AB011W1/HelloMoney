package com.barclays.bmg.ussd.service;

import com.barclays.bmg.ussd.auth.service.request.RegisterCustomerToProductServiceRequest;
import com.barclays.bmg.ussd.auth.service.response.RegisterCustomerToProductServiceResponse;

public interface RegisterCustomerToProductService {

	public RegisterCustomerToProductServiceResponse getcustomerDetails(
			RegisterCustomerToProductServiceRequest serviceRequest);

}
