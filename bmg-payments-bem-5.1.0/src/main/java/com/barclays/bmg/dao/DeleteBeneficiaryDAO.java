package com.barclays.bmg.dao;

import com.barclays.bmg.service.request.DeleteBeneficiaryServiceRequest;
import com.barclays.bmg.service.response.DeleteBeneficiaryServiceResponse;

/**
 * @author BTCI
 *
 */
public interface DeleteBeneficiaryDAO {

	/**
	 * @param deleteBeneficiaryServiceRequest
	 * @return DeleteBeneficiaryServiceResponse
	 */
	DeleteBeneficiaryServiceResponse deleteBeneficiary(
			DeleteBeneficiaryServiceRequest deleteBeneficiaryServiceRequest);

}
