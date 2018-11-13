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

package com.barclays.bmg.ussd.mvc.command.auth;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>SecondAuthenticationCommand.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 21, 2013</b> </br>
 * ************************************************************
 * 
 * @author E20043104
 * 
 */
public class SecondAuthenticationCommand {

    // private String txnRefNo;

    /**
     * The instance variable for SQA of type String
     */
    private String SQA;

    /*
     * private String position1;
     * 
     * private String position2;
     */

    /*
     * public String getTxnRefNo() { return txnRefNo; }
     * 
     * public void setTxnRefNo(String txnRefNo) { this.txnRefNo = txnRefNo; }
     * 
     * public String getPosition1() { return position1; }
     * 
     * public void setPosition1(String position1) { this.position1 = position1; }
     * 
     * public String getPosition2() { return position2; }
     * 
     * public void setPosition2(String position2) { this.position2 = position2; }
     */

    /**
     * Getter for SQA
     * 
     *@param none
     *@return SQA
     */
    public String getSQA() {
	return SQA;
    }

    /**
     * Setter for SQA
     * 
     * @param SQA
     * @return void
     */
    public void setSQA(String sqa) {
	SQA = sqa;
    }

}
