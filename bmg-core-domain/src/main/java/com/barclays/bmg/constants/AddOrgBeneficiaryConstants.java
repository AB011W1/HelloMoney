package com.barclays.bmg.constants;

public interface AddOrgBeneficiaryConstants {

    String BILLER_DTO_MAP = "billerDTOMap";
    String biller_registration_referenceNumber = "billerRegistrationReferenceNumber";

    String BILLER_REF_NUM = "biller_ref_number";
    String PAYEE_DOMESTIC_BARCLAYS = "Domestic Barclays Accounts";
    String PAYEE_DOMESTIC_NON_BARCLAYS = "Domestic Non-Barclays Accounts";

    String AB_FLOW_ID = "AB";
    String TRUE = "true";
    String FALSE = "false";

    String AUTH_TYPE_OTP = "OTP";
    String AUTH_TYPE_SQA = "SQA";
    String AUTH_TYPE_NON = "NON";
    String ENLIST_AVLBL_BILLERS = "Listing all available billers";
    String TXN_REF_NO = "addBenefTxnRefNo";
    String BILLER_LIST_NOT_EXIST = "BLR00601";
    String BILLER_REFNUM_EMPTY = "BEM06673";
    String BILLER_NICKNAMEALREADY_EXISTS = "BEM06696";
    String BILLER_NICKNAME_EMPTY = "BLR06674";
    String BILLER_BILLERID_EMPTY = "BLR06675";

    //
    String BILLER_REF_NOTPASSED_ERR = "Biller Reference Number was not passed to the service.";
    String BILLER_REF_BUSINESS_ERR = "Business Error";
    String BILLERID_REFERENCENUMBER_ALREADYEXISTS = "BEM06697";

}
