package com.barclays.bmg.constants;

public interface AuditConstant {
    // audit type
    public static final String AUDIT_TYPE_ACTIVITY = "TRANSACTION_AUDIT";
    public static final String AUDIT_TYPE_TRANSACTION = "TRANSACTION_AUDIT";
    public static final String AUDIT_TYPE_STATIC_DATA = "TRANSACTION_AUDIT";
    // source component
    public static final String SRC_COM_SSC = "IB";
    public static final String SRC_COM_ASC = "AB";
    public static final String SRC_COM_MW = "MW";
    public static final String SRC_COM_EXECUTOR = "IB";
    public static final String SRC_COM_UB = "UB";

    // activity response code
    public static final String ACT_RES_SUCC_CODE = "0000";
    public static final String ACT_RES_FAIL_CODE = "9999";

    // activity status
    public static final String ACT_STATUS_SUCC = "success";
    public static final String ACT_STATUS_FAIL = "failure";

    // activity state's parameter name in SSCContext session
    public static final String AUDIT_LOG_ACTIVITY_STATE = "AUDIT_LOG_ACTIVITY_STATE";

    //
    public static final String AUDIT_SERVICES = "AUDIT_SERVICES";

    // audit activity state
    public static final String ACTIVITY_STATE_INIT = "Init";
    public static final String ACTIVITY_STATE_VERIFY_STATIC_PWD = "Verify Login Password";
    public static final String ACTIVITY_STATE_VERIFY_TXN_PWD = "Verify Transaction Password";

    public static final String ACTIVITY_STATE_CHALLENGE_OTP = "Challenge OTP";
    public static final String ACTIVITY_STATE_VERIFY_OTP = "Verify OTP";

    public static final String ACTIVITY_STATE_VERIFY_SQA = "Verify SQA";
    public static final String ACTIVITY_STATE_CHALLENGE_SQA = "Challenge SQA";

    public static final String ACTIVITY_STATE_CHALLENGE_PARTIAL_PWD = "Challenge Partial Password";
    public static final String ACTIVITY_STATE_VERIFY_PARTIAL_PWD = "Verify Partial Password";

    public static final String ACTIVITY_STATE_ORDER_PAPER_STATEMENT = "Request Paper Statement";

    public static final String ACTIVITY_STATE_ENROLL_ESTATEMENT = "Enroll EStatement";
    public static final String ACTIVITY_STATE_CANCELL_ESTATEMENT = "Cancell EStatement";

    public static final String ACTIVITY_STATE_REQ_LIABILITY_LETTER = "Request Reliability Letter";

    public static String TXN_STATUS_SUCCESS = "SUCCESS";
    public static String TXN_STATUS_FAILURE = "FAILURE";
    public static String TXN_STATUS_INPROGRESS = "INPROGRESS";
    public static String TXN_SMS_STATUS = "SENT";

    public static String WHITESPACE = " ";
    public static String MWALLET = "MobileWallet";
    public static String MMONEY = "MobileMoney";

    public static String CUSTID = "CustomerId";
    public static String CUSTNAME = "CustomerName";
    public static String SERNAM = "ServiceName";
    public static String SRCMOBNO = "SourceMobileNumber";
    public static String MKDTOACCTNO = "MaskedToAccountNumber";
    public static String OPERATOR = "Operator";
    public static String MOBNO = "MobileNumber";
    public static String BILLERNAM = "BillerName";
    public static String BILLNO = "BillNumber";
    public static String SERTYP = "ServiceType";
    public static String TXNSUBTYP = "TxnSubType";
    public static String BILLHOLDNAM = "BillholdersName";
    public static String BILLHOLDADDR = "BillholdersAddress";
    public static String MKDFRMACCTNO = "MaskedFromAccountNumber";
    public static String TXNON = "TxnOn";
    public static String CNTRYDTTM = "CountryDateAndTime";
    public static String CMNTS = "Comments";
    public static String ERRCD = "ErrorCode";
    public static String REQDT = "RequestDate";
    public static String BRNCHNAM = "BranchName";
    public static String BKSIZE = "BookSize";
    public static String BKTYPE = "BookType";
    public static String SRCNKNAM = "SourceNickName";
    public static String ACCTNO = "AccountNumber";
    public static String PAYID = "PayeeID";
    public static String PAYTYP = "PayeeType";
    public static String LOGINTM = "LogInTime";
    public static String LOGOTTM = "LogOutTime";
    public static String DTFORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String BENEFICIARY_OBJ = "BeneficiaryDTO";
    public static String ATTRIBUTE_NAME = "AttributeName";
    public static String ATTRIBUTE_NAME1 = "AttributeName1";
    public static String ATTRIBUTE_VALUE = "AttributeValue";
    public static String ATTRIBUTE_VALUE1 = "AttributeValue1";
    public static String ATTRIBUTE_NAME2 = "AttributeName2";
    public static String ATTRIBUTE_VALUE2 = "AttributeValue2";
    public static String NUMBER_OF_LEAFLETS = "NumberOfLeaflets";

    public static String ADDITIONAL_INFORMATION = "AdditionalInformation";
    public static String ADDITIONAL_ATTRIBUTE = "AdditionalAttribute";

    public static String FAILURE_REASON = "FailureReason";

    public static String PMT_FT_OWN_CONFIRM = "PMT_FT_OWN_CONFIRM";
    public static String NOTE = "Note";
    public static String REFERENCE_NUMBER = "ReferenceNumber";
    public static String TRANSACTION_DATE = "TransactionDate";
    public static String FX_RATE = "FxRate";
    public static String BENEFICIARY_NAME = "BeneficiaryName";
    public static String BENEFICIARY_NICK_NAME = "BeneficiaryNickName";
    public static String AMOUNT = "Amount";
    public static String TO_ACCOUNT = "ToAccount";
    public static String FROM_ACCOUNT = "FromAccount";

    public static String PAYMENT_DETAILS = "PaymentDetails";
    public static String PAYMENT_REASON = "PaymentReason";
    public static String IBANFLAG = "IBANFlag";
    public static String PAYABLE_AT = "PayableAt";
    public static String DRAFT_TYPE = "DraftType";
    public static String DELIVERY_TYPE = "DeliveryType";

    public static String RENEWAL = "Renewal";
    public static String YEAR_TO_DATE_TAX = "YearToDateTax";
    public static String TD_PRINCIPAL_BALANCE = "TDPrincipalBalance";
    public static String PROJECTED_INTEREST_AMOUNT = "ProjectedInterestAmount";
    public static String NEXT_INTEREST_PAYMENT_DATE = "NextInterestPaymentDate";
    public static String LIEN_AMOUNT = "LienAmount";
    public static String LAST_INTEREST_PAYMENT_DATE = "LastInterestPaymentDate";
    public static String INTEREST_PAID_TO_DATE = "InterestPaidToDate";
    public static String AVAILABLE_FOR_REDEMPTION = "AvailableForRedemption";
    public static String VALUE_DATE = "ValueDate";
    public static String TENURE_TERM = "TenureTerm";
    public static String MATURITY_DATE = "MaturityDate";
    public static String MATURITY_AMOUNT = "MaturityAmount";
    public static String INTEREST_RATE = "InterestRate";
    public static String ACCOUNT_NUMBER = "AccountNumber";

    // START,Added Servicetype,subservicetype,attribute1 for FXRATE
    public static String SER_TYP_FX_RATES = "FOREX";
    public static String SUB_SER_TYP_FX_RATES = "FOREX";
    public static String FX_CURRENCY = "Currency";
    // END,Added Servicetype,subservicetype,attribute1 for FXRATE

    // START,Added Servicetype,subservicetype,attribute1 for UpdateBeneficiary
    public static String SER_TYP_EDIT_BENEFICIARY_OTH = "EDITBENOTH";
    public static String SUB_SER_TYP_EDIT_BENEFICIARY_OTH = "EDITBENOTH";
    public static String SER_TYP_EDIT_BENEFICIARY_DFT = "EDITBENDFT";
    public static String SUB_SER_TYP_EDIT_BENEFICIARY_DFT = "EDITBENDFT";
    // END,Added Servicetype,subservicetype,attribute1 UpdateBeneficiary

    // START,Added Servicetype,subservicetype,attribute1 for creditCard functionality
    public static String SER_TYP_CCD_ATAGLANCE = "CCACDETAIL";
    public static String SUB_SER_TYP_CCD_ATAGLANCE = "CCACDETAIL";
    public static String SER_TYP_CCD_UNBILLED_STATEMENT = "CCUNBILL";
    public static String SUB_SER_TYP_CCD_UNBILLED_STATEMENT = "CCUNBILL";
    public static String SER_TYP_CCD_STATEMENT = "CCSTATEMENT";
    public static String SUB_SER_TYP_CCD_STATEMENT = "CCSTATEMENT";
    public static String CCD_STATEMENT_DATE = "StatementDate";
    public static String SER_TYP_CCD_PAY_CARD_BILL = "CCPAYBILL";
    public static String SUB_SER_TYP_CCD_PAY_CARD_BILL = "CCPAYBILL";

    public static String SER_TYP_CCD_OWN_PAYMENT = "CCPAYBILL";
    public static String SUB_SER_TYP_CCD_OWN_PAYMENT = "CCPAYBILL";
    public static String CARD_NUMBER = "Card Number";
    public static String SER_TYP_CCD_CASH_SEND = "CASHSEND";
    public static String SUB_SER_TYP_CCD_CASH_SEND = "CASHSEND";

    public static String SER_TYP_BRANCH_LOCATE = "BRANCH LOCATE";
    public static String SUB_SER_TYP_BRANCH_LOCATE = "BRANCH LOCATE";
    public static String BRANCH = "Branch";

    public static String SER_TYP_ATM_LOCATE = "ATM LOCATE";
    public static String SUB_SER_TYP_ATM_LOCATE = "ATM LOCATE";
    public static String AREA = "Area";

    // END,Added Servicetype,subservicetype,attribute1 for creditCard functionality

    public static String SER_TYP_FD_RATES = "VWFDDETAIL";
    public static String SUB_SER_TYP_FD_RATES = "VWFDDETAIL";

    public static String SER_TYP_DELETE_BEN = "DELBENEFT";
    public static String SUB_SER_TYP_DELETE_BEN = "DELBENEFT";

    public static String SER_TYP_DELETE_BILLER = "DELBILLER";
    public static String SUB_SER_TYP_DELETE_BILLER = "DELBILLER";

    public static String SER_TYP_CHEQUE_BOOK = "CHKLFLET";
    public static String SUB_SER_TYP_CHEQUE_BOOK = "CHKLFLET";

    public static String SER_TYP_BAL_ENQUIRY = "BAL";
    public static String SUB_SER_TYP_BAL_ENQUIRY = "BAL";

    public static String SER_TYP_MINI_STMT = "MINI";
    public static String SUB_SER_TYP_MINI_STMT = "MINI";

    public static String SER_TYP_SELF_REGISTRATION = "SEC_ONLN_REG";
    public static String SUB_SER_TYP_SELF_REGISTRATION = "SEC_ONLN_REG";

    public static String SER_TYP_LOGIN = "SEC_LOGIN";
    public static String SUB_SER_TYP_LOGIN = "SEC_LOGIN";

    public static String MAKER_ID = "CreatedBy";
    public static String BILLER_ID = "BillerId";
    public static String TOP_UP_SERVICE = "TopUpService";

    public static String OTHER_PARAMS = "OtherParamters";
    public static String FD_CATEGORY_CODE = "CategoryCode";

    public static String STATEMENT_PERIOD = "StatementPeriod";
    public static String BRANCH_CODE = "BranchCode";

    public static String SER_TYP_PAYEE_LIST_FT = "PAYEE_LIST_FT";
    public static String SUB_SER_TYP_PAYEE_LIST_FT = "PAYEE_LIST_FT";

    public static String SER_TYP_LANG_PREF = "LANGPREF";
    public static String SUB_SER_TYP_LANG_PREF = "LANGPREF";

    public static String LANGUAGE = "Language";
    //CR46 Crdit card link
    public static String SER_TYP_CCD_LINK = "CCLINK";
    public static String CCD_CARD_NUMBER = "CardNumber";
    public static String SUB_SER_TYP_CCD_LINK = "CCLINK";

    //CR83 Apply Product
    public static String SER_TYP_APP_PROD = "APPLYPRODUCT";
    public static String APP_PROD_CARD_NUMBER = "CardNumber";
    public static String SUB_SER_TYP_APP_PROD = "APPLYPRODUCT";

    //LoanRepayment
    public static String SER_TYP_LOAN_PMT = "MAKELOANPAYMENT";
    public static String SUB_SER_TYP_LOAN_RE_PMT = "MAKELOANPAYMENT";
    public static String LOAN_ACCOUNT_NUMBER = "LoanAccNumber";
    public static String DEBIT_ACCOUNT_NUMBER ="DebitAccNumber";
    public static String DUE_AMOUNT_PAID ="overDueAmount";
}