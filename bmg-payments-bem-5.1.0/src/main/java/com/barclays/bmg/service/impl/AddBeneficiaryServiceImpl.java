package com.barclays.bmg.service.impl;

import com.barclays.bmg.dao.AddBeneficiaryDAO;
import com.barclays.bmg.service.AddBeneficiaryService;
import com.barclays.bmg.service.request.AddBeneficiaryServiceRequest;
import com.barclays.bmg.service.response.AddBeneficiaryServiceResponse;

public class AddBeneficiaryServiceImpl implements AddBeneficiaryService {

	private AddBeneficiaryDAO addBeneficiaryDAO;


	public void setAddBeneficiaryDAO(AddBeneficiaryDAO addBeneficiaryDAO) {
		this.addBeneficiaryDAO = addBeneficiaryDAO;
	}

	@Override
	public AddBeneficiaryServiceResponse addBeneficiary(
			AddBeneficiaryServiceRequest addBeneficiaryServiceRequest) {
		AddBeneficiaryServiceResponse addBeneficiaryServiceResponse = addBeneficiaryDAO.addBeneficiary(addBeneficiaryServiceRequest);
		return addBeneficiaryServiceResponse;
	}



}
