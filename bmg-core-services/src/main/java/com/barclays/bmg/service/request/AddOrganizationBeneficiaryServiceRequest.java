package com.barclays.bmg.service.request;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.BeneficiaryDTO;

/**
 * @author E20041929 Need to verify actual req parameters required for addorganizationservice
 */

public class AddOrganizationBeneficiaryServiceRequest extends RequestContext {

    private String acctNumber;
    private String billerId;
    private String billerType;
    private String beneficiaryNickName;
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

    public void setBeneficiaryNickName(String beneficiaryNickName) {
	this.beneficiaryNickName = beneficiaryNickName;
    }

    public String getBeneficiaryNickName() {
	return beneficiaryNickName;
    }

}
