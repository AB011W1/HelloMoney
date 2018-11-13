package com.barclays.bmg.service.request;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.BeneficiaryDTO;

/**
 * @author BTCI
 * 
 */
public class DeleteBeneficiaryServiceRequest extends RequestContext {

    private String payeeId;
    private String payeeType;
    private BeneficiaryDTO beneficiaryDTO;

    /**
     * @return BeneficiaryDTO
     */
    public BeneficiaryDTO getBeneficiaryDTO() {
	return beneficiaryDTO;
    }

    /**
     * @param beneficiaryDTO
     */
    public void setBeneficiaryDTO(BeneficiaryDTO beneficiaryDTO) {
	this.beneficiaryDTO = beneficiaryDTO;
    }

    /**
     * @return String
     */
    public String getPayeeId() {
	return payeeId;
    }

    /**
     * @param payeeId
     */
    public void setPayeeId(String payeeId) {
	this.payeeId = payeeId;
    }

    /**
     * @return String
     */
    public String getPayeeType() {
	return payeeType;
    }

    /**
     * @param payeeType
     */
    public void setPayeeType(String payeeType) {
	this.payeeType = payeeType;
    }

}
