package com.barclays.bmg.ussd.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.bem.RegisterCustomerToProduct.RegisterCustomerToProductRequest;
import com.barclays.bem.RegisterCustomerToProduct.RegisterCustomerToProductResponse;
import com.barclays.bmg.ussd.auth.service.request.RegisterCustomerToProductServiceRequest;
import com.barclays.bmg.ussd.auth.service.response.RegisterCustomerToProductServiceResponse;
import com.barclays.bmg.ussd.dao.RegisterCustomerToProductDAO;
import com.barclays.bmg.ussd.service.RegisterCustomerToProductService;

public class RegisterCustomerToProductServiceImpl implements RegisterCustomerToProductService {

private RegisterCustomerToProductDAO registerCustomerToProductDAO;

public RegisterCustomerToProductDAO getRegisterCustomerToProductDAO() {
	return registerCustomerToProductDAO;
}

public void setRegisterCustomerToProductDAO(
		RegisterCustomerToProductDAO registerCustomerToProductDAO) {
	this.registerCustomerToProductDAO = registerCustomerToProductDAO;
}

@Override
public RegisterCustomerToProductServiceResponse getcustomerDetails(
		RegisterCustomerToProductServiceRequest serviceRequest) {
	return  registerCustomerToProductDAO.getcustomerDetails(serviceRequest);
}

}
