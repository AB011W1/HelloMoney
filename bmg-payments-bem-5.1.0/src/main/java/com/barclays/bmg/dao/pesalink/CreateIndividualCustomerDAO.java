package com.barclays.bmg.dao.pesalink;


import com.barclays.bmg.service.request.pesalink.CreateIndividualCustomerServiceRequest;
import com.barclays.bmg.service.response.pesalink.CreateIndividualCustomerServiceResponse;

public interface CreateIndividualCustomerDAO {

	 public CreateIndividualCustomerServiceResponse createIndividualCustomer(CreateIndividualCustomerServiceRequest request);

}
