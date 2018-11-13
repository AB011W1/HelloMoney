package com.barclays.bmg.constants;

public interface BillPaymentConstants {
    String PAYEE_TYPE_BILL_PAYMENT = "BP";
    String PAYEE_TYPE_MOBILE_TOPUP = "MTP";
    String PAYEE_TYPE_MOBILE_WALLET = "MobileWallet";
    String PAYEE_TYPE_BARCLAY_CARD = "BCD";
    String PAYEE_TYPE_CREDIT_CARD_PAYMENT = "CCP";
    String PAYEE_TYPE_BCD_FROM_BEM = "BCP";

    String ACTIVE_STATUS = "ACTIVE";
    String PRIMARY = "1";
    String TXN_FACADE_RTN_TYPE_BILL_PAYMENT = "BP";
    String TXN_FACADE_RTN_TYPE_MOBILE_TOPUP = "MTP";
    String TXN_FACADE_RTN_TYPE_BARCLAY_CARD = "BCD";
    String TXN_FACADE_RTN_TYPE_CREDIT_CARD = "CCP";

    String FUND_TRANSFER_CARD_PAYMENT = "CCP";
    String PARAM_PMT_MOBILE_TOPUP_AMOUNT_INTERVAL = "PMT_MOBILE_TOPUP_AMOUNT_INTERVAL";
    String DEFAULT_PMT_MOBILE_TOPUP_AMOUNT_INTERVAL = "10";
    String BILL_PAYEE_INFORMTION = "beneficiaryDTO";
    String BILL_PAY_ACT_ID = "billPayActId";

    String BARCLAYCARD_BILLERTYPE = "BarclaycardBill";
    String TXN_REF_NO = "billPayTxnRefNo";
    String CREDIT_CARD_MAP = "creditCardMap";

    String AUTH_TYPE_NON = "NON";
    String AUTH_TYPE_OTP = "OTP";
    String AUTH_TYPE_SQA = "SQA";

    int OTP_LAST_CHARS_LENGTH = 4;

    String SECOND_AUTH_FLOW_ID = "secondAuthFlowId";
    String SECOND_AUTH_DONE = "secondAuthDone";

    String TRUE = "true";
    String FALSE = "false";
    String BP_FLOW_ID = "BP";

    String TRANSACTION_DTO = "transactionDTO";

    String PAYEE_TYPE_BILL_PAYMENT_ONE_TIME = "BP_ONE_TIME";

    // String CREDIT_CARD_PAYEE_INFORMTION = "creditCardDTO";

    String SER_TYP_BP = "PAYBILL";
    String SUB_SER_TYP_BP = "PAYBILL";

    String SER_TYP_BP_MTP = "TOPUP";
    String SUB_SER_TYP_BP_MTP = "TOPUP";

    String SUB_SER_TYP_BP_CCP = "Credit Card";
    String SUB_SER_TYP_BP_BCD = "Barclays Card";

    String SER_TYP_STATEMENT = "STMT";
    String SUB_SER_TYP_STATEMENT = "STMT";

    String SER_TYP_ADD_BEN = "ADDPAYEE";
    String SUB_SER_TYP_ADD_BEN = "ADDPAYEE";

    String SER_TYP_SMS = "SMS";
    String SUB_SER_TYP_SMS = "SMS";

    String SER_TYP_VIEW_LASTPAID_BILL = "VIEWLASTPAID";
    String SUB_SER_TYP_VIEW_LASTPAID_BILL = "VIEWLASTPAID";

    String SER_TYP_VIEW_MY_BILLER = "MYBILLER";
    String SUB_SER_TYP_VIEW_MY_BILLER = "MYBILLER";

    String SER_TYP_ADD_ORG_BEN = "BILLREG";
    String SUB_SER_TYP_ADD_ORG_BEN = "BILLREG";
    String BILLER_NAME = "BILLERNAME";
    String BILLER_NICK_NAME = "BILLERNICKNAME";
    String BILLER_DATE = "BillerDate";

    String SER_TYP_CHANGE_PIN = "MPIN";
    String SUB_SER_TYP_CHANGE_PIN = "MPIN";

    String SER_TYP_BP_ONETIME = "PAYONETIME";
    String SUB_SER_TYP_BP_ONETIME = "PAYONETIME";
    String TXN_TYP = "CCP";
    String PMT_FT_CARD_PAYMENT_OWN = "PMT_FT_CARD_PAYMENT_OWN";
    String PMT_DONE = "done";
    String POST_TO_CARD = "Card";
    String HOST_TRANSACTION_TYPE_CODE = "LOCALINTERNETCC";
    //CR-64 Credit card Payment EBOX
    String HOST_TRANSACTION_TYPE_CODE_EBOX = "LOCALCCARDPAYT";

    /**
     * CR 73
     */
    String ONE_TIME_BILL_PAYMENT_SAVE_BILLER = "BP_ONE_TIME_SB";

    //Kits changes starts
    String KITS_PAY_TO_PHONE = "PSTP";
    String KITS_PAY_TO_ACCOUNT = "PSTA";
    //Kits changes ends
	//CR82
    String  MWALLET_WON_NUMBER = "MW_WON_NUMBER";
    String AT_MW_SAVED_BENEF ="AT_MW_SAVED_BENEF";
    String AT_MW_DEFAULT ="default";

}
