package com.barclays.bmg.service.impl;

import com.barclays.bmg.dao.DeleteBeneficiaryDAO;
import com.barclays.bmg.service.DeleteBeneficiaryService;
import com.barclays.bmg.service.request.DeleteBeneficiaryServiceRequest;
import com.barclays.bmg.service.response.DeleteBeneficiaryServiceResponse;

/**
 * @author BTCI
 *
 */
public class DeleteBeneficiaryServiceImpl implements DeleteBeneficiaryService {

	private DeleteBeneficiaryDAO deleteBeneficiaryDAO;

	/**
	 * @param deleteBeneficiaryDAO
	 */
	public void setDeleteBeneficiaryDAO(
			DeleteBeneficiaryDAO deleteBeneficiaryDAO) {
		this.deleteBeneficiaryDAO = deleteBeneficiaryDAO;
	}

	/* (non-Javadoc)
	 * @see com.barclays.bmg.service.DeleteBeneficiaryService#deleteBeneficiary(com.barclays.bmg.service.request.DeleteBeneficiaryServiceRequest)
	 */
	@Override
	public DeleteBeneficiaryServiceResponse deleteBeneficiary(
			DeleteBeneficiaryServiceRequest deleteBeneficiaryServiceRequest) {

		DeleteBeneficiaryServiceResponse deleteBeneficiaryServiceResponse = deleteBeneficiaryDAO
				.deleteBeneficiary(deleteBeneficiaryServiceRequest);
		return deleteBeneficiaryServiceResponse;
	}

}
