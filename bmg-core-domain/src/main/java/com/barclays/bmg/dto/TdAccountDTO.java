/* Copyright 2008 Barclays PLC */

/**************************** Revision History ****************************************************
 * Version        Author          Date                        Description
 * 0.1            Elicer Zheng        2009/02/08                  Initial version
 *
 *
 **************************************************************************************************/

package com.barclays.bmg.dto;

import org.apache.commons.lang.StringUtils;

public class TdAccountDTO extends CustomerAccountDTO {

    private static final long serialVersionUID = 2427108385868008699L;

    private TermDepositDTO depositDTO;

    private boolean requestFlg = true;

    /**
     *Default Constuctor.
     */
    public TdAccountDTO() {
	super();
    }

    /**
     *Default Constuctor.
     */
    public TdAccountDTO(TermDepositDTO depositDTO) {
	this.depositDTO = depositDTO;
    }

    /**
     * @return the depositDTO
     */
    public TermDepositDTO getDepositDTO() {
	return depositDTO;
    }

    /**
     * @param depositDTO
     *            the depositDTO to set
     */
    public void setDepositDTO(TermDepositDTO depositDTO) {
	this.depositDTO = depositDTO;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + ((getAccountNumber() == null) ? 0 : getAccountNumber().hashCode());
	result = prime * result + ((depositDTO == null) ? 0 : depositDTO.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (!super.equals(obj)) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	TdAccountDTO other = (TdAccountDTO) obj;
	if (getAccountNumber() == null) {
	    if (other.getAccountNumber() != null) {
		return false;
	    }
	} else if (!getAccountNumber().equals(other.getAccountNumber())) {
	    return false;
	}
	if (depositDTO == null) {
	    if (other.depositDTO != null) {
		return false;
	    }
	} else if (!depositDTO.equals(other.depositDTO)) {
	    return false;
	}
	return true;
    }

    public boolean isRequestFlg() {
	return requestFlg;
    }

    public void setRequestFlg(boolean requestFlg) {
	this.requestFlg = requestFlg;
    }

    /**
     * To check whether the TD Account is Matured Or Closed.
     * 
     * @return is Matured Or Closed Account
     */
    public boolean isMaturedOrClosed() {
	return this.depositDTO == null || StringUtils.isEmpty(this.depositDTO.getDepositNumber());

    }

}
