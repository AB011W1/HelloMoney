package com.barclays.bmg.service.impl;

import com.barclays.bmg.dao.AddOrganizationBeneficiaryDAO;
import com.barclays.bmg.service.AddOrganizationBeneficiaryService;
import com.barclays.bmg.service.request.AddOrganizationBeneficiaryServiceRequest;
import com.barclays.bmg.service.response.AddOrgBeneficiaryServiceResponse;

public class AddOrganizationBeneficiaryServiceImpl implements
		AddOrganizationBeneficiaryService {

	private AddOrganizationBeneficiaryDAO addOrganizationBeneficiaryDao;

	@Override
	public AddOrgBeneficiaryServiceResponse addOrganizationBeneficiary(
			AddOrganizationBeneficiaryServiceRequest request) {
		AddOrgBeneficiaryServiceResponse addOrganizationBeneficiaryResponse = addOrganizationBeneficiaryDao
				.addOrganizationBeneficiary(request);
		return addOrganizationBeneficiaryResponse;

	}

	public void setAddOrganizationBeneficiaryDao(
			AddOrganizationBeneficiaryDAO addOrganizationBeneficiaryDao) {
		this.addOrganizationBeneficiaryDao = addOrganizationBeneficiaryDao;
	}

	public AddOrganizationBeneficiaryDAO getAddOrganizationBeneficiaryDao() {
		return addOrganizationBeneficiaryDao;
	}

}
