package com.barclays.bmg.constants;

public interface FundTransferConstants {

    String PAYEE_TYPE_INTERNAL = "INTERNAL";
    String PAYEE_TYPE_EXTERNAL = "EXTERNAL";
    String PAYEE_TYPE_FUND_TRANSFER_INTERNAL = "IAC";
    String TXN_TYPE_FUND_TRANSFER_INTERNAL = "IT";
    String TXN_TYPE_OWN_FUND_TRANSFER = "OWN";
    String TXN_TYPE_FUND_TRANSFER_EXTERNAL = "DT";
    String TXN_TYPE_FUND_TRANSFER_GHIPPS = "RT";
    String TXN_TYPE_BANK_DRAFT = "PBC";
    String TXN_TYPE_MANAGERS_CHEQUE = "PMC";
    String BANK_DRAFT_TXN_TYPE_KEY = "BKD";
    int DEFAULT_BRANCH_CODE_LENGTH = 3;
    String BRANCH_CODE_LENGTH = "BRANCH_CODE_LENGTH";
    String FT_FLOW_ID = "FT";
    String FUND_TRANSFER_EXTERNAL = "DAC";

    String PMT_FT_SUPPORT_CURRENCY = "PMT_FT_SUPPORT_CURRENCY";
    String FUND_TRANSFER_DTO = "fundTransferDTO";
    String BANK_DRAFT_DTO = "bankDraftDTO";
    String FUND_TRANSFER_INTL_DOMESTIC = "INT2";

    String INTL_FLOW_ID = "INTL";
    String EXTERNAL_FLOW_ID = "EXTFT";
    String CARD_PAYMENT_FLOW_ID = "CP";

    String TRANSACTION_DTO = "transactionDTO";
    String TXN_REF_NO = "txnRefNo";

    String LCYLCY = "LCYLCY";
    String FCYFCY = "FCYFCY";
    String LCYFCY = "LCYFCY";
    String FCYLCY = "FCYLCY";
    String FCY1FCY2 = "FCY1FCY2";

    String LIST_VAL_CURR_SUPPORT_INT_ACC = "CURR_SUPPORT_INT_ACC";
    String LIST_VAL_CURR_SUPPORT_OWN_ACC = "CURR_SUPPORT_OWN_ACC";

    String INTL_NON_REGISTERED_FT_FLOW_ID = "INTL_NON_REGISTERED_FT_FLOW_ID";

    String SER_TYP_FD_OWN = "FTOWN";
    String SER_SUB_TYP_FD_OWN = "FTOWN";

    String SER_TYP_FD_EXT = "FTDFT";
    String SER_SUB_TYP_FD_EXT = "FTDFT";

    String SER_TYP_FD_INT = "FTOTH";
    String SER_SUB_TYP_FD_INT = "FTOTH";

    String SYS_PARAM_CS = "SYS_PARAM_CS";
    String FUND_TRANSFER_OTHER_BARCLAYS_SAVE_BILLER = "FT_OTB_ACC_SB";
    String ACTIVITY_PARAM = "ActivityParam";
    String CREDIT_CARD_NUMBER = "creditCardNumber" ;

}
