package com.barclays.bmg.json.model;

import java.io.Serializable;

import com.barclays.bmg.dto.TdAccountDTO;
import com.barclays.bmg.json.response.BMBPayloadData;

public class TDDetailsJSONResponseModel extends BMBPayloadData implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -5400115824684843258L;
	private TDDetailsJSONModel tdDtls;

	public TDDetailsJSONResponseModel(TdAccountDTO tdAccountDTO) {
		this.tdDtls = new TDDetailsJSONModel(tdAccountDTO);
	}

	public TDDetailsJSONModel getTdDtls() {
		return tdDtls;
	}

	public void setTdDtls(TDDetailsJSONModel tdDtls) {
		this.tdDtls = tdDtls;
	}

}
