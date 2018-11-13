package com.barclays.bmg.service.response;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BeneficiaryDTO;

public class AddOrgBeneficiaryServiceResponse extends ResponseContext {

    /**
	 *
	 */
    private static final long serialVersionUID = 6243722108043740189L;
    private String txnReferenceNumber;
    private String consumerUniqueRefNo;

    public String getTxnReferenceNumber() {
	return txnReferenceNumber;
    }

    public void setTxnReferenceNumber(String txnReferenceNumber) {
	this.txnReferenceNumber = txnReferenceNumber;
    }

    private BeneficiaryDTO beneficiaryDTO;

    public BeneficiaryDTO getBeneficiaryDTO() {
	return beneficiaryDTO;
    }

    public void setBeneficiaryDTO(BeneficiaryDTO beneficiaryDTO) {
	this.beneficiaryDTO = beneficiaryDTO;
    }

    public void setConsumerUniqueRefNo(String consumerUniqueRefNo) {
	this.consumerUniqueRefNo = consumerUniqueRefNo;
    }

    public String getConsumerUniqueRefNo() {
	return consumerUniqueRefNo;
    }

}
