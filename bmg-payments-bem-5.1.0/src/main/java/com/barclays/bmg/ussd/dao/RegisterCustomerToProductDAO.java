package com.barclays.bmg.ussd.dao;

import com.barclays.bmg.ussd.auth.service.request.RegisterCustomerToProductServiceRequest;
import com.barclays.bmg.ussd.auth.service.response.RegisterCustomerToProductServiceResponse;



public interface RegisterCustomerToProductDAO {


	public RegisterCustomerToProductServiceResponse getcustomerDetails(RegisterCustomerToProductServiceRequest request);

}
