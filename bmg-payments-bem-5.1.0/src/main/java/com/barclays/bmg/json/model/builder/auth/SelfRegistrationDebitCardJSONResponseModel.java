/*
 * Copyright (c) 2013 Barclays Bank Plc, All Rights Reserved.
 *
 * This code is confidential to Barclays Bank Plc and shall not be disclosed
 * outside the Bank without the prior written permission of the Director of
 * CIO
 *
 * In the event that such disclosure is permitted the code shall not be copied
 * or distributed other than on a need-to-know basis and any recipients may be
 * required to sign a confidentiality undertaking in favour of Barclays Bank
 * Plc.
 */
package com.barclays.bmg.json.model.builder.auth;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.dto.DebitCardDTO;
import com.barclays.bmg.json.response.BMBPayloadData;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>SelfRegistrationDebitCardJSONResponseModel.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>Jun 09, 2015</b> </br>
 * ***********************************************************
 *
 *
 */
public class SelfRegistrationDebitCardJSONResponseModel extends BMBPayloadData {


	private static final long serialVersionUID = -5987631588534289352L;
	private String serviceResponse;

	private List<DebitCardDTO> debitCardList = new ArrayList<DebitCardDTO>();

    public List<DebitCardDTO> getDebitCardList() {
		return debitCardList;
	}

	public void setDebitCardList(List<DebitCardDTO> debitCardList) {
		this.debitCardList = debitCardList;
	}

	public String getServiceResponse() {
	return serviceResponse;
    }

    public void setServiceResponse(String serviceResponse) {
	this.serviceResponse = serviceResponse;
    }

}
