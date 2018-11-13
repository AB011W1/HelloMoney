package com.barclays.bmg.json.model.builder.fundtransfer.external;

import java.io.Serializable;
import java.util.List;

import com.barclays.bmg.dto.BillerInfoDTO;
import com.barclays.bmg.json.response.BMBPayloadData;

public class AddOrgBeneficiaryJSONModel extends BMBPayloadData implements
		Serializable {

	 private List<BillerInfoDTO> billerList ;
  	 public void setBillerList(List<BillerInfoDTO> allBillerList) {
		this.billerList = allBillerList;
	 }
	 public List<BillerInfoDTO> getBillerList() {
		return billerList;
	 }

}
