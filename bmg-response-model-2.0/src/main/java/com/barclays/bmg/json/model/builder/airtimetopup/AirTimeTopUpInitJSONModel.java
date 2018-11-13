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
/**
 * Package name is com.barclays.bmg.json.model.builder.airtimetopup
 * /
 */
package com.barclays.bmg.json.model.builder.airtimetopup;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.json.model.CasaAccountJSONModel;
import com.barclays.bmg.json.model.billpayment.BillerJSONModel;
import com.barclays.bmg.json.response.BMBPayloadData;

/**
 * ****************** Revision History **********************
 * </br>
 * Project Name  is  <b>bmg-response-model-2.0</b>
 * </br>
 * The file name is  <b>AirTimeTopUpInitJSONModel.java</b>
 * </br>
 * Description  is  <b>Initial Version</b>
 * </br>
 * Updated Date  is  <b>May 17, 2013</b>
 * </br>
 * ******************************************************
 *
 * @author e20037686
 * </br>
 * * The Class AirTimeTopUpInitJSONModel created using JRE 1.6.0_10
 */
public class AirTimeTopUpInitJSONModel extends BMBPayloadData
{

	/**
	 *
	 */
	private static final long serialVersionUID = -4467749266212626895L;

	/**
	 * The instance variable named "srcLst" is created.
	 */
	private List<CasaAccountJSONModel> srcLst;

	/**
	 * The instance variable named "prvderLst" is created.
	 */
	private List<BillerJSONModel> prvderLst = new ArrayList<BillerJSONModel>();

	/**
	 * Gets the src lst.
	 *
	 * @return the SrcLst
	 */
	public List<CasaAccountJSONModel> getSrcLst() {
		return srcLst;
	}

	/**
	 * Sets values for SrcLst.
	 *
	 * @param srcLst the src lst
	 */
	public void setSrcLst(List<CasaAccountJSONModel> srcLst) {
		this.srcLst = srcLst;
	}

	/**
	 * Gets the prvder lst.
	 *
	 * @return the PrvderLst
	 */
	public List<BillerJSONModel> getPrvderLst() {
		return prvderLst;
	}

	/**
	 * Sets values for PrvderLst.
	 *
	 * @param prvderLst the prvder lst
	 */
	public void setPrvderLst(List<BillerJSONModel> prvderLst) {
		this.prvderLst = prvderLst;
	}
}