package com.barclays.bmg.service.impl.pesalink;


import com.barclays.bmg.dao.pesalink.CreateIndividualCustomerDAO;
import com.barclays.bmg.service.pesalink.CreateIndividualCustomerService;
import com.barclays.bmg.service.request.pesalink.CreateIndividualCustomerServiceRequest;
import com.barclays.bmg.service.response.pesalink.CreateIndividualCustomerServiceResponse;


public class CreateIndividualCustomerServiceImpl  implements CreateIndividualCustomerService{

	private CreateIndividualCustomerDAO createIndividualCustomerDAO;

	@Override
	public CreateIndividualCustomerServiceResponse createIndividualCustomer(CreateIndividualCustomerServiceRequest request) {

		CreateIndividualCustomerServiceResponse createIndividualCustomerServiceResponse=createIndividualCustomerDAO.createIndividualCustomer(request);

		return createIndividualCustomerServiceResponse;
	}

	public CreateIndividualCustomerDAO getCreateIndividualCustomerDAO() {
		return createIndividualCustomerDAO;
	}

	public void setCreateIndividualCustomerDAO(
			CreateIndividualCustomerDAO createIndividualCustomerDAO) {
		this.createIndividualCustomerDAO = createIndividualCustomerDAO;
	}

}
