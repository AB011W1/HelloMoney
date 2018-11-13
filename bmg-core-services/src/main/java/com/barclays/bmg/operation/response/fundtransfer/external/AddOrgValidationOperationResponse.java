package com.barclays.bmg.operation.response.fundtransfer.external;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BillerDTO;

public class AddOrgValidationOperationResponse extends ResponseContext {

    private static final long serialVersionUID = -5633675296200870996L;

    private BillerDTO billerDTO;
    private String billerReferenceAccNum;
    private String selectedBillerId;
    private String orgRefNo;
    private String payeNickName;

    public void setBillerDTO(BillerDTO billerDTO) {
	this.billerDTO = billerDTO;
    }

    public BillerDTO getBillerDTO() {
	return billerDTO;
    }

    public void setBillerReferenceAccNum(String billerReferenceAccNum) {
	this.billerReferenceAccNum = billerReferenceAccNum;
    }

    public String getBillerReferenceAccNum() {
	return billerReferenceAccNum;
    }

    public void setSelectedBillerId(String selectedBillerId) {
	this.selectedBillerId = selectedBillerId;
    }

    public String getSelectedBillerId() {
	return selectedBillerId;
    }

    public void setOrgRefNo(String orgRefNo) {
	this.orgRefNo = orgRefNo;
    }

    public String getOrgRefNo() {
	return orgRefNo;
    }

    public void setPayeNickName(String payeNickName) {
	this.payeNickName = payeNickName;
    }

    public String getPayeNickName() {
	return payeNickName;
    }

}
