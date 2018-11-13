package com.barclays.bmg.service;

import com.barclays.bmg.service.request.DeleteBeneficiaryServiceRequest;
import com.barclays.bmg.service.response.DeleteBeneficiaryServiceResponse;

/**
 * @author BTCI
 * 
 */
public interface DeleteBeneficiaryService {

    /**
     * @param deleteBeneficiaryServiceRequest
     * @return DeleteBeneficiaryServiceResponse
     */
    DeleteBeneficiaryServiceResponse deleteBeneficiary(DeleteBeneficiaryServiceRequest deleteBeneficiaryServiceRequest);

}
