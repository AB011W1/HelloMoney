package com.barclays.bmg.json.model.builder.fundtransfer.external;

import java.io.Serializable;
import java.util.List;

import com.barclays.bmg.dto.AddOrgBillerDisplayDTO;
import com.barclays.bmg.json.response.BMBPayloadData;


public class AddOrgValidationConfirmJSONModel extends  BMBPayloadData implements Serializable{

	private static final long serialVersionUID = 1L;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}


	List<AddOrgBillerDisplayDTO> billerDisplayList;

	public List<AddOrgBillerDisplayDTO> getBillerDisplayList() {
		return billerDisplayList;
	}
	public void setBillerDisplayListDTOMap(List<AddOrgBillerDisplayDTO> billerDisplayList) {
		this.billerDisplayList = billerDisplayList;
	}


}
