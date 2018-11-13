package com.barclays.bmg.mvc.controller.billpayment;

import com.barclays.bmg.dto.BillerDTO;

public class BillerAndOrgBeneficiaryDTO {
    public String getBillerId() {
	return billerId;
    }

    public void setBillerId(String billerId) {
	this.billerId = billerId;
    }

    public String getBillerRefAccNum() {
	return billerRefAccNum;
    }

    public void setBillerRefAccNum(String billerRefAccNum) {
	this.billerRefAccNum = billerRefAccNum;
    }

    public BillerDTO getSelectedBillerDTO() {
	return selectedBillerDTO;
    }

    public void setSelectedBillerDTO(BillerDTO selectedBillerDTO) {
	this.selectedBillerDTO = selectedBillerDTO;
    }

    public void setBillerNickName(String billerNickName) {
	this.billerNickName = billerNickName;
    }

    public String getBillerNickName() {
	return billerNickName;
    }

    private String billerId;
    private String billerRefAccNum;
    private BillerDTO selectedBillerDTO;
    private String billerNickName;
    private String areaCode;

    public String getAreaCode() {
	return areaCode;
    }

    public void setAreaCode(String areaCode) {
	this.areaCode = areaCode;
    }

}
