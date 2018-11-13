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

package com.barclays.bmg.type;

/* *************************** Revision History *********************************
 * Version        Author          Date                     Description
 * 0.1            Billy Li        5 Nov 2009                  Initial version
 *
 *
 ********************************************************************************/

/**
 * Authentication Type Enum
 * 
 * @author Billy Li
 */

public enum AuthType {
SQA("SQA"), OTP("OTP"), TXN("TXN"), SP("SP");

private String value;

private AuthType(String value) {
    this.value = value;
}

public String getValue() {
    return value;
}

}
