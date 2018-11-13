package com.barclays.bmg.operation.response.fundtransfer.external;

import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.BillerDTO;

public class AddOrgBeneficiaryOperationResponse extends ResponseContext {
    private String orgRefNo;
    private static final long serialVersionUID = -44210520520485420L;

    private List<BillerDTO> billerList;
    private BillerDTO billerDTO;

    public void setBillerDTO(BillerDTO billerDTO) {
	this.billerDTO = billerDTO;
    }

    public BillerDTO getBillerDTO() {
	return billerDTO;
    }

    public void setOrgRefNo(String orgRefNo) {
	this.orgRefNo = orgRefNo;
    }

    public String getOrgRefNo() {
	return orgRefNo;
    }

    public void setBillerList(List<BillerDTO> allBillerList) {
	this.billerList = allBillerList;
    }

    public List<BillerDTO> getBillerList() {
	return billerList;
    }

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
