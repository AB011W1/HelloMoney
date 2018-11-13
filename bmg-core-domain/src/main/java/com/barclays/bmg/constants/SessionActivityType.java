package com.barclays.bmg.constants;

public interface SessionActivityType {
    public static final String INPROCESS = "IN PROGRESS";
    public static final String SUCCESS = "SUCCESS";
    public static final String FAIL = "FAIL";

    public static final String LOGOUT = "LOG_OUT";
    public static final String LOGIN = "LOG_IN";

    public static final String CHG_PWD = "CHG_PWD";

    public static final String CHG_SQA = "CHG_SQA";

    public static final String ORDER_PAPER_STATEMENT = "ORDER_PAPERSTMT";
    public static final String REQ_LIABILITY_LETTER = "REQ_LIABILITYLETTER";

    public static final String STANDINGINSTRUCTION_CANCEL = "PMT_SI_CANCEL_AT";

    public static final String STANDINGINSTRUCTION_CANCELNEXT = "PMT_SI_CANCELNEXT_AT";

    public static final String STANDINGINSTRUCTION_UPDATE = "PMT_SI_UPDATE_AT";

    public static final String STANDINGINSTRUCTION_SETUP = "PMT_SI_SETUP_AT";

    public static final String BILL_PAYMENT = "BP";
    public static final String FUND_TRANSFER_OWN = "OWN";
    public static final String FUND_TRANSFER_CARD_PAYMENT = "CCP";
    public static final String FUND_TRANSFER_INTERNAL = "IT";
    public static final String FUND_TRANSFER_EXTERNAL = "DT";
    public static final String FUND_TRANSFER_INTERNATIONAL = "INTL";
    public static final String LOAN_REPAYMENT = "LP";
    public static final String MOBILE_TOPUP = "MTP";
    public static final String BARCLAY_CARD_PAYMENT = "BCD";
    public static final String PURCHASE_BANK_DRAFT = "PBC";
    public static final String BANK_DRAFT = "PBC"; // NOT IN USE; Please check and delete this
    public static final String CHEQUE_BOOK_REQUEST = "CHECK_BOOK_REQUEST";
    public static final String PURCHASE_MANAGER_CHEQUE = "PMC";

    public static final String ESTATEMENT = "ESTATEMENT";

    // Credit Card Features
    public static final String ACTIVATE_CARD = "ACTIVATE_CREDITCARD";
    public static final String LINE_INCREASE = "INC_CREDITLINE";
    public static final String CASH_PIN = "PIN_REQUEST";

    // TD activity ID
    String OPEN_TD = "OPEN_TD";
    String CHANGE_TD = "CHANGE_TD";

    // Report Lost Credit Card
    public static final String REPORT_LOST_CCD = "REPORT_LOST_CCD";

    public static String ADD_PAYEE = "ADD_PAYEE";
    public static String DEL_PAYEE = "DEL_PAYEE";
    public static String ACTIVATE_PAYEE = "ACTIVATE_PAYEE";

    public static String PASSWORD_TYPE = "PASSWORD_TYPE";
    public static String SITUATION = "Situation";

    // add TD Detail
    // change TD Detail
    public static final String SOURCE_ACCOUNT = "SourceAccount";

    public static final String TD_PRODUCT = "DepositProduct";
    public static final String TD_ACCOUNT = "TDAccount";
    public static final String DEPOSIT_NUMBER = "DepositNumber";
    public static final String TENOR = "Tenor";
    public static final String DEPOSIT_AMOUNT = "DepositAmount";
    public static final String ROLLOVER_INSTRUCTION = "RenewalInstruction";

    // Report Lost Credit Card Detail
    // public static final String CARD_NUMBER = "CardNumber";
    // public static final String CARD_HOLDER_NAME = "CardholderName";
    // public static final String REASON = "Reason";
    // public static final String ACTION = "Action";

    // direct debit
    public static final String SETUP_DIRECT_DEBIT = "CRD_SETUP_DD";
    public static final String CANCEL_DIRECT_DEBIT = "CRD_CANCEL_DD";

    public static final String MY_PREF = "MY_PREF";
    public static final String UPDATE_CONTACT_INFO = "UPDATE_CONTACT_INFO";
    public static final String ADD_SUP_CARD = "ADDON_SUPPCARD";

    public static final String CARD_DISPUTE = "CARD_DISPUTE";

    public static final String OPEN_ESAVER = "OPEN_ESAVER";

    public static final String LINK_MY_ACCOUNTS = "LINK_MY_ACCOUNTS";
}
