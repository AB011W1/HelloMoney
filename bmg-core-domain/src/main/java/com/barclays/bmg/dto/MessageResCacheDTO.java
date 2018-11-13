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

/* *************************** Revision History ***************************
 * Version        Author          Date                         Description
 * 0.1           Tony Ni        Aug 25, 2009                  Initial version
 *
 *
 **************************************************************************/

/**
 * TODO Class/Interface description.
 * 
 * @author Tony Ni
 */
public class MessageResCacheDTO extends BaseDomainDTO {

    /** TODO Comment for <code>serialVersionUID</code>. */

    private static final long serialVersionUID = -280207842440611923L;

    private String languageId;

    private String messageValue;

    private String category;

    public String getCategory() {
	return category;
    }

    public void setCategory(String category) {
	this.category = category;
    }

    /**
     * @return the languageId
     */
    public String getLanguageId() {
	return languageId;
    }

    /**
     * @param languageId
     *            the languageId to set
     */
    public void setLanguageId(String languageId) {
	this.languageId = languageId;
    }

    /**
     * @return the messageValue
     */
    public String getMessageValue() {
	return messageValue;
    }

    /**
     * @param messageValue
     *            the messageValue to set
     */
    public void setMessageValue(String messageValue) {
	this.messageValue = messageValue;
    }

}
