/*
 * Copyright (c) 2010 Barclays Bank Plc, All Rights Reserved.
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

package com.barclays.bmg.dto;

import java.io.Serializable;
import java.util.List;

/* *************************** Revision History *********************************
 * Version        Author          Date                     Description
 * 0.1            Elicer Zheng        10 Mar 2010                  Initial version
 *
 *
 ********************************************************************************/

/**
 * TODO This class used to Store the retturn result for Account Summary.
 * 
 * @author Elicer Zheng
 */
@SuppressWarnings("unchecked")
public class SummaryListDTO implements Serializable {
    /** TODO Comment for <code>serialVersionUID</code>. */

    private static final long serialVersionUID = 1L;

    private List AccountList;

    private List<String> errorCodeList;

    /**
     * @return the accountList
     */
    public List getAccountList() {
	return AccountList;
    }

    /**
     * @param accountList
     *            the accountList to set
     */
    public void setAccountList(List accountList) {
	AccountList = accountList;
    }

    /**
     * @return the errorCodeList
     */
    public List<String> getErrorCodeList() {
	return errorCodeList;
    }

    /**
     * @param errorCodeList
     *            the errorCodeList to set
     */
    public void setErrorCodeList(List<String> errorCodeList) {
	this.errorCodeList = errorCodeList;
    }

}
