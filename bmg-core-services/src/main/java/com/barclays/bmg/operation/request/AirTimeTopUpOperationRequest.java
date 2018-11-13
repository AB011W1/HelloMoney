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
 * Package name is com.barclays.bmg.operation.request
 * /
 */
package com.barclays.bmg.operation.request;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.BeneficiaryDTO;

/**
 * ****************** Revision History ********************** </br> Project Name is <b>bmg-core-services</b> </br> The file name is
 * <b>AirTimeTopUpOperationRequest.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 17, 2013</b> </br>
 * ******************************************************
 * 
 * @author e20037686 </br> * The Class AirTimeTopUpOperationRequest created using JRE 1.6.0_10
 */
public class AirTimeTopUpOperationRequest extends RequestContext {

    /**
     * The instance variable named "billerId" is created.
     */
    private String billerId;

    /**
     * The instance variable named "billerCatId" is created.
     */
    private String billerCatId;

    /**
     * The instance variable named "billerNickName" is created.
     */
    private String billerNickName;

    /**
     * The instance variable named "beneficiaryDTO" is created.
     */
    private BeneficiaryDTO beneficiaryDTO;

    /**
     * Gets the biller id.
     * 
     * @return the BillerId
     */
    public String getBillerId() {
	return billerId;
    }

    /**
     * Sets values for BillerId.
     * 
     * @param billerId
     *            the biller id
     */
    public void setBillerId(String billerId) {
	this.billerId = billerId;
    }

    /**
     * Gets the biller cat id.
     * 
     * @return the BillerCatId
     */
    public String getBillerCatId() {
	return billerCatId;
    }

    /**
     * Sets values for BillerCatId.
     * 
     * @param billerCatId
     *            the biller cat id
     */
    public void setBillerCatId(String billerCatId) {
	this.billerCatId = billerCatId;
    }

    /**
     * Gets the biller nick name.
     * 
     * @return the BillerNickName
     */
    public String getBillerNickName() {
	return billerNickName;
    }

    /**
     * Sets values for BillerNickName.
     * 
     * @param billerNickName
     *            the biller nick name
     */
    public void setBillerNickName(String billerNickName) {
	this.billerNickName = billerNickName;
    }

    /**
     * Gets the beneficiary dto.
     * 
     * @return the BeneficiaryDTO
     */
    public BeneficiaryDTO getBeneficiaryDTO() {
	return beneficiaryDTO;
    }

    /**
     * Sets values for BeneficiaryDTO.
     * 
     * @param beneficiaryDTO
     *            the beneficiary dto
     */
    public void setBeneficiaryDTO(BeneficiaryDTO beneficiaryDTO) {
	this.beneficiaryDTO = beneficiaryDTO;
    }

}
