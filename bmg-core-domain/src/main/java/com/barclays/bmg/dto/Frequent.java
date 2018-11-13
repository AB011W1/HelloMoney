/*
 * Copyright (c) 2009 Barclays Bank Plc, All Rights Reserved.
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

/* *************************** Revision History *********************************
 * Version        Author          Date                            Description
 * 0.1            E20002626DEV    Jul 29, 2009                    Initial version
 *
 *
 ********************************************************************************/

/**
 * @author E20002626DEV
 * 
 */
public class Frequent implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1585282860083552740L;

    private String frequentKey;

    private String description;

    /**
     * TODO Constructor description, including pre/post conditions and invariants.
     * 
     * @param frequentKey
     */
    public Frequent(String frequentKey) {
	super();
	this.frequentKey = frequentKey;
    }

    public Frequent() {
	super();

    }

    public String getFrequentKey() {
	return frequentKey;
    }

    public void setFrequentKey(String frequentKey) {
	this.frequentKey = frequentKey;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

}
