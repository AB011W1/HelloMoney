package com.barclays.bmg.dto;

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

/**
 * Entitlement DTO.
 * 
 * @version $Revision: 1.1 $
 * 
 * 
 * @author Max Zhang
 */

public class EntitlementDTO extends BaseDomainDTO {

    /** serialVersionUID. */

    private static final long serialVersionUID = 5878869104083982219L;

    private String activityId;
    private String relationship;
    private String cardHolderType;

    /**
     * @return the cardHolderType
     */
    public String getCardHolderType() {
	return cardHolderType;
    }

    /**
     * @param cardHolderType
     *            the cardHolderType to set
     */
    public void setCardHolderType(String cardHolderType) {
	this.cardHolderType = cardHolderType;
    }

    /**
     * @return the activityId
     */
    public final String getActivityId() {
	return activityId;
    }

    /**
     * @param activityId
     *            the activityId to set
     */
    public final void setActivityId(String activityId) {
	this.activityId = activityId;
    }

    /**
     * @return the relationship
     */
    public final String getRelationship() {
	return relationship;
    }

    /**
     * @param relationship
     *            the relationship to set
     */
    public final void setRelationship(String relationship) {
	this.relationship = relationship;
    }

}
