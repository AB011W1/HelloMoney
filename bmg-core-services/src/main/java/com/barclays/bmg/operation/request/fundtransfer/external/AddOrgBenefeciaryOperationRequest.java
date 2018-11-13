package com.barclays.bmg.operation.request.fundtransfer.external;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.BeneficiaryDTO;

public class AddOrgBenefeciaryOperationRequest extends RequestContext {

    private String acctNumber;
    private String billerId;
    private String billerType;
    private String billerNickName;
    private String areaCode;

    public String getAreaCode() {
	return areaCode;
    }

    public void setAreaCode(String areaCode) {
	this.areaCode = areaCode;
    }

    private BeneficiaryDTO beneficiaryDTO;

    public String getAcctNumber() {
	return acctNumber;
    }

    public String getBillerId() {
	return billerId;
    }

    public void setBillerId(String billerId) {
	this.billerId = billerId;
    }

    public String getBillerType() {
	return billerType;
    }

    public void setBillerType(String billerType) {
	this.billerType = billerType;
    }

    public void setAcctNumber(String acctNumber) {
	this.acctNumber = acctNumber;
    }

    public void setBeneficiaryDTO(BeneficiaryDTO beneficiaryDTO) {
	this.beneficiaryDTO = beneficiaryDTO;
    }

    public BeneficiaryDTO getBeneficiaryDTO() {
	return beneficiaryDTO;
    }

    public void setBillerNickName(String billerNickName) {
	this.billerNickName = billerNickName;
    }

    public String getBillerNickName() {
	return billerNickName;
    }

}
