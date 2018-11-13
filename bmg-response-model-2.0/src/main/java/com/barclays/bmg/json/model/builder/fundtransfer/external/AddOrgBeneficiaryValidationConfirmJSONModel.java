package com.barclays.bmg.json.model.builder.fundtransfer.external;

import java.io.Serializable;

import com.barclays.bmg.dto.BillerInfoDTO;
import com.barclays.bmg.json.response.BMBPayloadData;

public class AddOrgBeneficiaryValidationConfirmJSONModel extends BMBPayloadData
		implements Serializable {
	private static final long serialVersionUID = 1L;
	private String orgRefNo;
	private BillerInfoDTO billerDTO;
	private String payeNickName;


	public BillerInfoDTO getBillerDTO() {
		return billerDTO;
	}

	public void setBillerDTO(BillerInfoDTO billerDTO) {
		this.billerDTO = billerDTO;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
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
