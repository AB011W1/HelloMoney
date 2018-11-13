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
 * Package name is com.barclays.bmg.operation.response
 * /
 */
package com.barclays.bmg.operation.response;

import java.math.BigDecimal;
import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.CustomerDTO;

/**
 * ****************** Revision History ********************** </br> Project Name is <b>bmg-core-services</b> </br> The file name is
 * <b>RetrieveAllCustAcctOperationResponse.java</b> </br> Description is <b>V 1.2</b> </br> Updated Date is <b>June 04, 2013</b> </br>
 * ******************************************************
 * 
 * @author e20037686 </br> * The Class RetrieveAllCustAcctOperationResponse created using JRE 1.6.0_10
 */
public class RetrieveAllCustAcctOperationResponse extends ResponseContext {

    /**
     * The Constant named "serialVersionUID" is created.
     */
    private static final long serialVersionUID = -5650859992960757304L;

    /**
     * The instance variable named "accountList" is created.
     */
    private List<? extends CustomerAccountDTO> accountList;
    private CustomerDTO customer;

    private BigDecimal netBalanceAmount;

    private BigDecimal currentBookBalanceAmount;

    /**
     * @return the customer
     */
    public CustomerDTO getCustomer() {
	return customer;
    }

    /**
	 *
	 */
    public void setCustomer(CustomerDTO customer) {
	this.customer = customer;
    }

    /**
     * Gets the account list.
     * 
     * @return the account list
     */
    public List<? extends CustomerAccountDTO> getAccountList() {
	return accountList;
    }

    /**
     * Sets values for AccountList.
     * 
     * @param accountList
     *            the account list
     */
    public void setAccountList(List<? extends CustomerAccountDTO> accountList) {
	this.accountList = accountList;
    }

    /**
     * @return the netBalanceAmount
     */
    public BigDecimal getNetBalanceAmount() {
	return netBalanceAmount;
    }

    /**
     * @param netBalanceAmount
     *            the netBalanceAmount to set
     */
    public void setNetBalanceAmount(BigDecimal netBalanceAmount) {
	this.netBalanceAmount = netBalanceAmount;
    }

    /**
     * @return the currentBookBalanceAmount
     */
    public BigDecimal getCurrentBookBalanceAmount() {
	return currentBookBalanceAmount;
    }

    /**
     * @param currentBookBalanceAmount
     *            the currentBookBalanceAmount to set
     */
    public void setCurrentBookBalanceAmount(BigDecimal currentBookBalanceAmount) {
	this.currentBookBalanceAmount = currentBookBalanceAmount;
    }
}