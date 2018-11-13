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
 * Package name is com.barclays.bmg.json.model
 * /
 */
package com.barclays.bmg.json.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.barclays.bmg.json.model.accounts.CustomerProfileJSONModel;
import com.barclays.bmg.json.model.accounts.EmailMessageJSONModel;
import com.barclays.bmg.json.response.BMBPayloadData;

/**
 * ****************** Revision History **********************
 * </br>
 * Project Name  is  <b>bmg-response-model-2.0</b>
 * </br>
 * The file name is  <b>RetrieveAllCustAcctJSONModel.java</b>
 * </br>
 * Description  is  <b>Initial Version</b>
 * </br>
 * Updated Date  is  <b>May 15, 2013</b>
 * </br>
 * ******************************************************
 *
 * @author e20037686
 * </br>
 * * The Class RetrieveAllCustAcctJSONModel created using JRE 1.6.0_10
 */
public class RetrieveAllCustAcctJSONModel extends BMBPayloadData
{
	private static final long serialVersionUID = 9552781662631641L;

	/**
	 * The instance variable named "custActs" is created.
	 */
	private List<? extends AccountJSONModel> custActs = Collections.emptyList();

	/**
	 * The instance variable named "custProf" is created.
	 */
	private CustomerProfileJSONModel custProf = new CustomerProfileJSONModel();

	/**
	 * The instance variable named "emsgs" is created.
	 */
	private List<EmailMessageJSONModel> emsgs  = new ArrayList<EmailMessageJSONModel>();

	/**
	 * Gets the cust acts.
	 *
	 * @return the cust acts
	 */
	public List<? extends AccountJSONModel> getCustActs() {
		return custActs;
	}

	/**
	 * Sets values for CustActs.
	 *
	 * @param custActs the cust acts
	 */
	public void setCustActs(List<? extends AccountJSONModel> custActs) {
		this.custActs = custActs;
	}

	/**
	 * Gets the cust prof.
	 *
	 * @return the CustProf
	 */
	public CustomerProfileJSONModel getCustProf() {
		return custProf;
	}

	/**
	 * Sets values for CustProf.
	 *
	 * @param custProf the cust prof
	 */
	public void setCustProf(CustomerProfileJSONModel custProf) {
		this.custProf = custProf;
	}

	/**
	 * Gets the emsgs.
	 *
	 * @return the Emsgs
	 */
	public List<EmailMessageJSONModel> getEmsgs() {
		return emsgs;
	}

	/**
	 * Sets values for Emsgs.
	 *
	 * @param emsgs the emsgs
	 */
	public void setEmsgs(List<EmailMessageJSONModel> emsgs) {
		this.emsgs = emsgs;
	}
}