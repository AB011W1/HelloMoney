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
package com.barclays.bmg.service.request;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.BillerDTO;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.CreditCardAccountDTO;

/**
 * ****************** Revision History ********************** </br> Project Name is <b>bmg-core-services</b> </br> The file name is
 * <b>AirTimeTopUpValidateServiceRequest.java</b> </br> Description is <b>V 1.2</b> </br> Updated Date is <b>May 17, 2013</b> </br>
 * ******************************************************
 *
 * @author e20037686 </br> * The Class AirTimeTopUpValidateServiceRequest created using JRE 1.6.0_10
 */
public class AirTimeTopUpValidateServiceRequest extends RequestContext {
    /**
     * The instance variable named "account" is created.
     */
    private CASAAccountDTO account;

	private CreditCardAccountDTO creditcardAccountDTO;

    public CreditCardAccountDTO getCreditcardAccountDTO() {
		return creditcardAccountDTO;
	}

	public void setCreditcardAccountDTO(CreditCardAccountDTO creditcardAccountDTO) {
		this.creditcardAccountDTO = creditcardAccountDTO;
	}

    /**
     * The instance variable named "billerDTO" is created.
     */
    private BillerDTO billerDTO;

    /**
     * Gets the account.
     * 
     * @return the Account
     */
    public CASAAccountDTO getAccount() {
	return account;
    }

    /**
     * Sets values for Account.
     * 
     * @param account
     *            the account
     */
    public void setAccount(CASAAccountDTO account) {
	this.account = account;
    }

    /**
     * Gets the biller dto.
     * 
     * @return the BillerDTO
     */
    public BillerDTO getBillerDTO() {
	return billerDTO;
    }

    /**
     * Sets values for BillerDTO.
     * 
     * @param billerDTO
     *            the biller dto
     */
    public void setBillerDTO(BillerDTO billerDTO) {
	this.billerDTO = billerDTO;
    }
}