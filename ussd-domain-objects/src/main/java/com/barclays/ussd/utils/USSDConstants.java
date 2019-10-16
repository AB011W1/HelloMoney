package com.barclays.ussd.utils;

/**
 * The Interface USSDConstants.
 */
public final class USSDConstants {
    /** The bmg base url. */
    public static final String BMG_BASE_URL = "bmg_base_url";

    /** The enable local gateway. */
    public static final String ENABLE_LOCAL_GATEWAY = "enableLocalGateway";

    /** The enable bmg dummy. */
    public static final String ENABLE_BMG_DUMMY = "enableDummyBMG";

    /** The max page body size. */
    public static final String MAX_PAGE_BODY_SIZE = "maxPageBodySize";

    /** The max screen size. */
    public static final String MAX_SCREEN_SIZE = "maxScreenSize";

    /** The scroll up lbl. */
    public static final String SCROLL_UP_LBL = "label.scroll.up";

    /** The scroll down lbl. */
    public static final String SCROLL_DOWN_LBL = "label.scroll.down";

    /** The input param. */
    public static final String REQ_INPUT_ATTRIBUTE = "INPUT";

    /** The scroll up input. */
    public static final String SCROLL_UP_INPUT = "**";

    /** The scroll down input. */
    public static final String SCROLL_DOWN_INPUT = "##";

    /** The new line. */
    public static final String NEW_LINE = "\\n";

    /** The single white space. */
    public static final String SINGLE_WHITE_SPACE = " ";

    /** The pipe seperator. */
    public static final String PIPE_SEPERATOR = "\\|";

    /** The pipe */
    public static final String PIPE = "|";

    /** The DOT . */
    public static final String DOT_SEPERATOR = ".";
    /** The comma seperator. */
    public static final String COMMA_SEPERATOR = ",";

    /** The home page screen id. */
    public static final String HOME_PAGE_SCREEN_ID = "0";

    /** The option screen flag. */
    public static final String SKIP_SCREEN_INPUT = "0";

    /** The home page default input. */
    public static final String HOME_PAGE_DEFAULT_INPUT = "2";

    /** The go to home page tran id. */
    public static final String GO_TO_HOME_PAGE_TRAN_ID = "USSDOP000";

    /** The welcome page screen id. */
    public static final String WELCOME_PAGE_SCREEN_ID = "00";

    /** The welcome page default input. */
    public static final String WELCOME_PAGE_DEFAULT_INPUT = "0000";

    /** The welcome page default input. */
    public static final String WELCOME_PAGE_NODE_ID = "ussd0.00";

    /** The unreg user welcome page screen id. */
    public static final String UNREG_USER_WELCOME_PAGE_SCREEN_ID = "100";

    /** The USER session retention page screen id. */
    public static final String USER_SESSION_RETAIN_PAGE_SCREEN_ID = "200";

    /** The unreg user welcome page default input. */
    public static final String UNREG_USER_WELCOME_PAGE_DEFAULT_INPUT = "01";

    /** The USER session retention page default input. */
    public static final String USER_SESSION_RETAIN_PAGE_DEFAULT_INPUT = "02";

    /** The unreg user welcome page default input. */
    public static final String UNREG_USER_WELCOME_PAGE_NODE_ID = "ussd1.00";

    /** The USER session retention page node id */
    public static final String USER_SESSION_RETAIN_PAGE_NODE_ID = "ussd2.00";

    /** The go to home page label id. */
    public static final String GO_TO_HOME_PAGE_LABEL_ID = "label.navigate.main";

    /** The go back tran id. */
    public static final String GO_BACK_TRAN_ID = "USSDOP999";

    /** The go back label id. */
    public static final String GO_BACK_LABEL_ID = "label.navigate.previous";

    /** The go back n home label id. */
    public static final String GO_BACK_N_HOME_LABEL_ID = "label.navigate.main.back";

    /** The back lbl. */
    public static final String BACK_LBL = "label.navigate.previous";

    /** The balance inq tran id. */
    public static final String BALANCE_INQ_TRAN_ID = "USSDOP111";

    /** The status continue. */
    public static final String STATUS_CONTINUE = "continue";

    /** The status end. */
    public static final String STATUS_END = "end";

    /** The status error. */
    public static final String STATUS_ERROR = "error";

    /** The invalid input label. */
    public static final String INVALID_INPUT_LABEL = "LBL_INV";

    /** The home page screen node id. */
    public static final String HOME_PAGE_SCREEN_NODE_ID = "ussd0.0";

    /** The session time out. */
    public static final int SESSION_TIME_OUT = 300000;

    /** The password pattern. */
    public static final String PASSWORD_PATTERN = "(\\d){1,9}";

    /** The user input param name. */
    public static final String USER_INPUT_PARAM_NAME = "optionId";

    /** The user msisdn no param name. */
    public static final String USER_MSISDN_NO_PARAM_NAME = "MSISDN";

    /** The header id param name. */
    public static final String HEADER_ID_PARAM_NAME = "headerId";

    /** The confirm action option code. */
    public static final String CONFIRM_ACTION_OPTION_CODE = "1";

    public static final String DEFAULT_OPTION_SELECTION = "1";

    /** The seperator. */
    public static final String UNDERSCORE_SEPERATOR = "_";

    /** The go back option. */
    public static final String GO_BACK_OPTION = "*";

    /** The go to home option. */
    public static final String GO_TO_HOME_OPTION = "#";

    /** The xml node id attr. */
    public static final String XML_NODE_ID_ATTR = "id";

    /** The xml screen id attr. */
    public static final String XML_SCREEN_ID_ATTR = "screenId";

    /** The xml level attr. */
    public static final String XML_LEVEL_ATTR = "level";

    /** The xml option id attr. */
    public static final String XML_OPTION_ID_ATTR = "optionId";

    /** The xml label id attr. */
    public static final String XML_LABEL_ID_ATTR = "labelId";

    /** The xml tran id attr. */
    public static final String XML_TRAN_ID_ATTR = "tranId";

    /** The xml home option attr. */
    public static final String XML_HOME_OPTION_ATTR = "homeOptionReq";

    /** The xml back option attr. */
    public static final String XML_BACK_OPTION_ATTR = "backOptionReq";

    /** The bmg local ke opcode param name. */
    public static final String BMG_LOCAL_KE_OPCODE_PARAM_NAME = "opCde";

    /** The bmg business id param name. */
    public static final String BMG_BUSINESS_ID_PARAM_NAME = "bizId";

    /** The bmg channel id param name. */
    public static final String BMG_CHANNEL_ID_PARAM_NAME = "chId";

    /** The bmg channel id default value. */
    public static final String BMG_CHANNEL_ID_DEFAULT_VALUE = "UB";

    /** The bmg channel id param name. */
    public static final String BMG_LANGUAGE_ID_PARAM_NAME = "langId";

    /** The bmg channel id default value. */
    public static final String BMG_LANGUAGE_ID_DEFAULT_VALUE = "EN";

    /** The bmg local usrnam. */
    public static final String BMG_LOCAL_USRNAM = "usrNam";

    /** The bmg local ke service ver name. */
    public static final String BMG_LOCAL_KE_SERVICE_VER_NAME = "serVer";

    /** The bmg service version value. */
    public static final String BMG_SERVICE_VERSION_VALUE = "2.0";

    /** The transaction date param name. */
    public static final String TRANSACTION_DATE_PARAM_NAME = "txnDt";

    /** The transaction note param name. */
    public static final String TRANSACTION_NOTE_PARAM_NAME = "txnNot";

    /** The transaction to acct param name. */
    public static final String TRANSACTION_TO_ACCT_PARAM_NAME = "toActNo";

    /** The transaction payee acct param name. */
    public static final String TRANSACTION_PAYEE_ACCT_PARAM_NAME = "payId";

    /** The transaction from acct param name. */
    public static final String TRANSACTION_FROM_ACCT_PARAM_NAME = "frActNo";

    /* Below are the bmgopcode constants */
    /** The bmg call not required. */
    public static final String BMG_CALL_NOT_REQUIRED = "BMGCALLNTREQ";

    /** The pseudo call required. */
    public static final String PSEUDO_CALL_REQUIRED = "PSEUDOCALLREQ";

    /** The negate ui rendering. */
    public static final String NEGATE_UI_RENDERING = "NEGUIRENDER";

    /** The retrieve src payee acct bmg opcode. */
    public static final String RETRIEVE_SRC_PAYEE_ACCT_BMG_OPCODE = "OP0500";

    /** The own fund transfer validate bmg opcode. */
    public static final String OWN_FUND_TRANSFER_VALIDATE_BMG_OPCODE = "OP0501";

    /** The own fund transfer confirm bmg opcode. */
    public static final String OWN_FUND_TRANSFER_CONFIRM_BMG_OPCODE = "OP0502";

    /** The checque book req int. */
    public static final String CHECQUE_BOOK_REQ_INT = "OP0901";

    /** The retrieve chq book init opcode. */
    public static final String RETRIEVE_CHQ_BOOK_INIT_OPCODE = "OP0901";

    /** The retrieve chq book validation opcode. */
    public static final String RETRIEVE_CHQ_BOOK_VALIDATION_OPCODE = "OP0902";

    /** The retrieve chq book execution opcode. */
    public static final String RETRIEVE_CHQ_BOOK_EXECUTION_OPCODE = "OP0903";

    /** The account summary. */
    public static final String ACCOUNT_SUMMARY = "OP0200";

    /** The casa detail. */
    public static final String CASA_DETAIL = "OP0301";

    /** The retrieve payee list. */
    public static final String RETRIEVE_PAYEE_LIST = "OP0600";

    /** The retrieve bill pay trans info. */
    public static final String RETRIEVE_BILL_PAY_TRANS_INFO = "OP0601";

    /** The submit pay transfer form. */
    public static final String SUBMIT_PAY_TRANSFER_FORM = "OP0602";

    /** The confirm bill pay. */
    public static final String CONFIRM_BILL_PAY = "OP0603";

    /** The bill pay. */
    public static final String BILL_PAY = "BP";

    /** The ext bnk ft retrieve payee list. */
    public static final String EXT_BNK_FT_RETRIEVE_PAYEE_LIST = "OP0520";

    /** The ext bnk ft payee info. */
    public static final String EXT_BNK_FT_PAYEE_INFO = "OP0521";

    /** The ext bnk ft form validate. */
    public static final String EXT_BNK_FT_FORM_VALIDATE = "OP0522";

    /** The ext bnk ft execute. */
    public static final String EXT_BNK_FT_EXECUTE = "OP0523";

    /** The retrieve obaft payee list. */
    public static final String RETRIEVE_OBAFT_PAYEE_LIST = "OP0510";

    /** The validate obaft. */
    public static final String VALIDATE_OBAFT = "OP0511";

    /** The confirm obaft. */
    public static final String CONFIRM_OBAFT = "OP0502";

    /* Below are the transaction data constants defined in the menuskeleton xmls */
    /** The retrieve payee acct tran data id. */
    public static final String RETRIEVE_PAYEE_ACCT_TRAN_DATA_ID = "label.tx.toaccnum";

    /** The retrieve src acct tran data id. */
    public static final String RETRIEVE_SRC_ACCT_TRAN_DATA_ID = "label.tx.fromaccnum";

    /** The cheque book size list tran data. */
    public static final String CHEQUE_BOOK_SIZE_LIST_TRAN_DATA = "label.tx.bksize";

    /** The cheque book vald tran data. */
    public static final String CHEQUE_BOOK_VALD_TRAN_DATA = "TX009";

    /** The cheque book exc tran data. */
    public static final String CHEQUE_BOOK_EXC_TRAN_DATA = "TX010";

    /* RESPONSE CODES */
    /** The success response code. */
    public static final String SUCCESS_RESPONSE_CODE = "00000";

    /** The bmg account value. */
    public static final String BMG_ACCOUNT_VALUE = "actNo";

    /** The bmg tanx ref no. */
    public static final String BMG_TANX_REF_NO = "txnRefNo";

    /** The chq book size. */

    public static final String BMG_BRN_CDE = "brnCde";
    public static final String BMG_BRN_NAM = "brnNam";

    /** The warning message. */
    public static final String WARNING_MESSAGE = "warningMessage";

    /** The paydata. */
    public static final String PAYDATA = "paydata";

    /** The warning. */
    public static final String WARNING = "WARNING";

    /** The parserused. */
    public static final String PARSERUSED = "parserUsed";

    /** The acnt smry. */
    public static final String ACNT_SMRY = "acntsmry";

    /** The acnt det. */
    public static final String ACNT_DET = "acntdet";

    /** The oba fnd tx init. */
    public static final String OBA_FND_TX_INIT = "obafndtranx";

    /** The oba fnd tx validate. */
    public static final String OBA_FND_TX_VALIDATE = "validateobafndtranx";

    /** The screen. */
    public static final String SCREEN = "screen";

    /** The resource bundle. */
    public static final String RESOURCE_BUNDLE = "resourceBundle";

    /** The ussd response mgr. */
    public static final String USSD_RESPONSE_MGR = "ussdresponsemgr";

    /** The casa detail json. */
    public static final String CASA_DETAIL_JSON = "casadetailjson";

    /** The acnt no. */
    public static final String ACNT_NO = "A/c No:";

    /** The cr. */
    public static final String CR = "CR";

    /** The dr. */
    public static final String DR = "DR";

    /** The lbl curr ac bal. */
    public static final String LBL_CURR_AC_BAL = "LBLCURRACBAL";

    /** The lbl avail ac bal. */
    public static final String LBL_AVAIL_AC_BAL = "LBLAVAILACBAL";

    /** Actual Balance mapped with ‘Net balance’ of FCR. for UGBRB */
    public static final String LBL_ACTUL_AC_BAL = "LBLACTULBAL";

    /** The bill payment pay category. */
    public static final String BILL_PAYMENT_PAY_CATEGORY = "Bill Payment";

    /** The bill pay remarks. */
    public static final String BILL_PAY_REMARKS = "pmtRem";

    /** The ussd name. */
    public static final String USSD_NAME = "USSD";

    /** The lbl transaction. */
    public static final String LBL_TRANSACTION = "LBLTRANS";

    /** The lbl biller. */
    public static final String LBL_BILLER = "LBLBILLER";

    /** The lbl amount. */
    public static final String LBL_AMOUNT = "LBLAMOUNT";

    /** The lbl frm acnt. */
    public static final String LBL_FRM_ACNT = "label.fromaccount";

    /** The lbl confirm. */
    public static final String LBL_CONFIRM = "label.confirm";

    /** The lbl nickname. */
    public static final String LBL_NICK_NAME = "label.tonickname";

    /** The lbl submit message. */
    public static final String LBL_SUBMIT_MESSAGE = "LBLSUBMITMESSAGE";

    /** The txn. */
    public static final String TXN = "TXN ID:";
    // FT - Other Bank parameters
    /** The ext bnk ft frm ac param. */
    public static final String EXT_BNK_FT_FRM_AC_PARAM = "frActNo";

    /** The ext bnk curr param. */
    public static final String EXT_BNK_CURR_PARAM = "curr";

    /** The ext bnk chdesc param. */
    public static final String EXT_BNK_CHDESC_PARAM = "chDesc";

    /** The ext bnk txnnot param. */
    public static final String EXT_BNK_TXNNOT_PARAM = "txnNot";

    /** The ext bnk payrson param. */
    public static final String EXT_BNK_PAYRSON_PARAM = "payRson";

    /** The ext bnk paydtls param. */
    public static final String EXT_BNK_PAYDTLS_PARAM = "payDtls";

    /** The EX t_ bn k_ re m1_ param. */
    public static final String EXT_BNK_REM1_PARAM = "rem1";

    /** The EX t_ bn k_ re m2_ param. */
    public static final String EXT_BNK_REM2_PARAM = "rem2";

    /** The EX t_ bn k_ re m3_ param. */
    public static final String EXT_BNK_REM3_PARAM = "rem3";

    /** The ext bnk txn dt. */
    public static final String EXT_BNK_TXN_DT = "txnDt";

    /** The ext bnk curr. */
    public static final String EXT_BNK_CURR = "curr";

    /** The lbl cc pay. */
    public static final String LBL_CC_PAY = "LBLCCPAY";

    /** The lbl mortgage pay. */
    public static final String LBL_MORTGAGE_PAY = "LBLMORTGAGEPAY";

    /** The lbl insur pay. */
    public static final String LBL_INSUR_PAY = "LBLINSURPAY";

    /** The lbl tax pay. */
    public static final String LBL_TAX_PAY = "LBLTAXPAY";

    /** The lbl others. */
    public static final String LBL_OTHERS = "LBLOTHERS";

    /** The lbl frm ac no. */
    public static final String LBL_FRM_AC_NO = "LBLFRMACNO";

    /** The lbl to ac no. */
    public static final String LBL_TO_AC_NO = "LBLTOACNO";

    /** The lbl txn id. */
    public static final String LBL_TXN_ID = "LBLTXNID";
    // FT - Other Bank
    /** The tran id label id. */
    public static final String TRAN_ID_LABEL_ID = "label.transact.id";

    public static final String TRAN_SUCCESS_LABEL = "label.transaction.success";

    /** The data type ATM pwrd. */
    public static final String DATA_TYPE_ATM_PIN = "atmpin";
    /** The mini stmt detail limit. */
    public static final String MINI_STMT_DETAIL_LIMIT = "miniStmtTxCount";
    /** The data type list. */
    public static final String DATA_TYPE_LIST = "list";

    public static final String DATA_TYPE_REF_NO = "refno";

    /** CR-57*/
    public static final String DATA_TYPE_REF_NO_ZW = "refnozw";

    public static final String DATA_TYPE_REF_NO_NBC = "refnonbc";

    /** The data type amount. */
    public static final String DATA_TYPE_AMOUNT = "amount";

    /** The data type confirm. */
    public static final String DATA_TYPE_CONFIRM = "confirm";

    /** The data type na. */
    public static final String DATA_TYPE_NA = "na";

    /** The data type pwrd. */
    public static final String DATA_TYPE_PWRD = "password";

    public static final String DATA_TYPE_BRANCH_CODE = "branchCode";

    /** The data type two fact ans. */
    public static final String DATA_TYPE_TWO_FACT_ANS = "twoFactAns";

    /** The data type two fact ans. DOB */
    public static final String DATA_TYPE_TWO_FACT_ANS_DOB = "twoFactAnsDOB";

    /** Seearch for DFT and Area, City    */
    public static final String DATA_TYPE_SEARCHER_ALPHA_NUM = "searcherAlphaNum";

    /** The data type two fact ans. DOB */
    public static final String DATA_TYPE_SEARCHER = "searcher";

    /** The data type pwrd. */
    public static final String DATA_TYPE_LOGIN_PWRD = "loginPwd";

    /** The data type kenya biller ref no. */
    public static final String DATA_TYPE_KE_REF_NO = "kerefno";

    /** The pay bill confirm lbl. */
    public static final String PAY_BILL_CONFIRM_LBL = "label.payrequest.accepted";

    /** The pay bill confirm lbl. */
    public static final String PAY_BILL_INPROCESS_LBL = "label.payrequest.inprocess";

    /** The pay bill confirm lbl. */
    public static final String CREDIT_CARD_PAYMENT_SUCCESS = "label.cc.own.payment.success";

    /** The domestic fnd tx pay cat. */
    public static final String DOMESTIC_FND_TX_PAY_CAT = "Domestic Barclays Accounts";

    /** The success. */
    public static final String SUCCESS = "SUCCESS";

    /** The internal payee type. */
    public static final String INTERNAL_PAYEE_TYPE = "IAC";

    /** The external payee type. */
    public static final String EXTERNAL_PAYEE_TYPE = "DAC";

    /** The vlpb list size. */
    public static final int VLPB_LIST_SIZE = 3;
    // View Last Paid Bills
    /** The lbl vlpb biller name. */
    public static final String LBL_VLPB_BILLER_NAME = "LBLVLPBBILLERNAM";

    /** The lbl vlpb biller amt. */
    public static final String LBL_VLPB_BILLER_AMT = "LBLVLPBBILLAMT";

    /** The lbl vlpb pay dt. */
    public static final String LBL_VLPB_PAY_DT = "LBLVLPBPAYDATE";

    /** The domestic non barclays acnt. */
    public static final String DOMESTIC_NON_BARCLAYS_ACNT = "Domestic Non-Barclays Accounts";

    /** The data type end tran. */
    public static final String DATA_TYPE_END_TRAN = "endTran";

    /** The data type end tran. */
    public static final String DATA_TYPE_MSISDN = "msisdn";

    /** The data type end tran. */
    public static final String DATA_TYPE_CASHSEND_MOBILE = "csmsisdn";

    /** The data type end tran. */
    public static final String DATA_TYPE_MOBILE_NUMBER = "mobileNumber";
    /** The data type account number. */
    public static final String DATA_TYPE_ACCTNO = "accountNo";

    /** The USS d_ b e_00001. */
    public static final String USSD_BE_00001 = "USSD_BE_00001";

    /** The ussd session invalid. */
    public static final String USSD_SESSION_INVALID = "USSD_SESSION_INVALID";

    /** The are u sure. */
    public static final String ARE_U_SURE = "label.biller.deletion.askconfirm";

    // Added for Internal Non Registered Fund Transfer
    /** The int bnk ft nr txn dt. */
    public static final String INT_BNK_FT_NR_TXN_DT = "txnDt";

    /** The int bnk ft nr curr. */
    public static final String INT_BNK_FT_NR_CURR = "curr";

    /** The int bnk ft nr frm ac. */
    public static final String INT_BNK_FT_NR_FRM_AC = "frActNo";

    /** The int bnk ft nr branch code. */
    public static final String INT_BNK_FT_NR_BRANCH_CODE = "beneficiaryBranchCode";

    /** The int bnk ft nr to ac. */
    public static final String INT_BNK_FT_NR_TO_AC = "beneficiaryAccountNumber";

    /** The int bnk ft nr ben name. */
    public static final String INT_BNK_FT_NR_BEN_NAME = "beneficiaryName";

    /** The int bnk ft nr pay desc. */
    public static final String INT_BNK_FT_NR_PAY_DESC = "payDesc";

    /** The int bnk ft nr txn amount. */
    public static final String INT_BNK_FT_NR_TXN_AMOUNT = "txnAmt";

    /** The PRES s_1_ confirm. */
    public static final String PRESS_1_CONFIRM = "LBL0052";

    // System startup cache
    /** The get td rates. */
    public static final String GET_TD_RATES = "getTDRates";

    /** The get billers lst. */
    public static final String GET_BILLERS_LST = "getBillers";

    public static final String GET_BILLER_INFO = "getBillerInfo";

    /** The billers list not exist. */
    public static final String BILLERS_LIST_NOT_EXIST = "Orgnizatinal Beneficiary is not retrieved";

    // Authentication and Verification POJO key
    /** The verify usr resp. */
    public static final String VERIFY_USR_RESP = "verify_user";

    /** The auth usr resp. */
    public static final String AUTH_USR_RESP = "auth_user";

    /** The seperator hyphen. */
    public static final String SEPERATOR_HYPHEN = "-";

    /** The seperator underscore. */
    public static final String SEPERATOR_UNDESCORE = "_";

    /** The bmg down. */
    public static final String BMG_DOWN = "BMG_DOWN";

    /** The ussd airtime topup success. */
    public static final String USSD_AIRTIME_TOPUP_SUCCESS = "label.transaction.mwallete.success";

    /** The ussd airtime topup success. */
    public static final String USSD_AIRTIME_TOPUP_INPROGRESS = "USSD_AIRTIME_TOPUP_INPROGRESS";

    /** The ussd otbp success. */
    public static final String USSD_OTBP_SUCCESS = "USSD_OTBP_SUCCESS";

    /** The ussd otbp success. */
    public static final String USSD_OTBP_INPROGRESS = "USSD_OTBP_INPROGRESS";

    public static final String USSD_TRANSACTION_INPROGRESS = "USSD_TRANSACTION_INPROGRESS";

    /** The self registration tran id. */
    public static final String SELF_REGISTRATION_TRAN_ID = "SelfRegistration";

    /** The self registration first quest. */
    public static final int FIRST_QUESTION_INDEX = 0;

    /** The self registration second quest. */
    public static final int SECOND_QUESTION_INDEX = 1;

    /** The self registration third quest. */
    public static final int THIRD_QUESTION_INDEX = 2;

    /** The self registration fourth quest. */
    public static final int FOURTH_QUESTION_INDEX = 3;

    /** The http conection timeout. */
    public static final String HTTP_CONECTION_TIMEOUT = "connectionTimeout";

    /** The http socket timeout. */
    public static final String HTTP_SOCKET_TIMEOUT = "socketTimeout";

    public static final String ANS_POSITIONS = "ANS_POSITIONS";

    public static final String AND = "AND";

    public static final String LBL_ACCNT_NO = "label.accoun.number";

    // User permissions
    public static final String USER_STATUS_LIMITED = "Limited Access";

    // User's pin status
    public static final String USER_PIN_STATUS_TO_BE_CHANGED = "Change Required";

    // Pin status change required
    public static final String PIN_CHANGE_REQ = "Change Required";
    public static final String COLON = ":";

    public static final String FD_ENTER_AMT = "label.fd.view.enter.amt";

    public static final String MOBILE_NUM = "label.mobnum";

    public static final String AGGREGATOR = "aggregator";

    public static final String REQOBJ = "httpReqObject";

    // Mobile Wallet
    public static final String MW_ACT_NO = "actNo";
    public static final String MW_TXN_AMT = "txnAmt";
    public static final String MW_MBL_NO = "mblNo";
    public static final String MW_BLR_ID = "billerId";
    public static final String MW_REF_NO = "refNmbr";

    /* Primary indicator values */
    public static final String PRIMARY_INDICATOR_YES = "Y";
    public static final String PRIMARY_INDICATOR_NO = "N";

    /* Generic error code if BMG returns an unexpected error */
    public static final String GENERIC_ERROR_CODE = "TECHNICAL_ISSUE";

    public static final String PAY_GRP = "payGrp";
    public static final String ACT_NO = "actNo";
    public static final String DEPOSIT_AMT = "depositAmount";
    public static final String TENURE_MON = "tenureMonths";
    public static final String TENURE_DAY = "tenureDays";
    public static final String PRODUCT_ID = "productId";
    public static final String FD_APPLY_CONFIRMATION = "fdApplyConfirmation";

    /* Black page header for the screen */
    public static final String LBL_BLANK_PAGE_HEADER = "LBL9999";

    /* Minis statement related constants */
    public static final String MINI_STATEMENT_DURATION = "miniStatementDuration";
    public static final String DEFAULT_MINI_STMT_DURATION = "60";

    public static final String SELECTED_BILLER_OTBP = "selectedBillerOtbp";
    public static final String SELECTED_BILLER_REGB = "selectedBillerRegb";
    public static final String CUST_REF_ID_DEFAULT = "CUST_REF_ID_DEFAULT";
    public static final String CONF_CR_ID_DEFAULT = "CONF_CR_ID_DEFAULT";
    public static final String CUST_REF_ID = "CUST_REF_ID_";
    public static final String CONF_CR_ID = "CONF_CR_ID_";
    public static final int MAX_TWO_FACT_ATTEMPTS = 3;

    public static final String CUSTOMER_TYPE_NON_HM = "nonHelloMoneyCustomer";
    public static final String CUSTOMER_TYPE_USER_SESSION_RETENTION = "userSessionRetentionFlow";

    public static final String OLD_SESSION_FLAG = "oldSessionFlag";
    public static final String MSISDN_WITHOUT_COUNTRY = "msisdnWithoutCountry";

    public static final String TRANSACTION_REMARKS_FT_BARCLAYS = "transaction.remarks.fund.transfer.barclays";
    public static final String TRANSACTION_REMARKS_FT_OTHER_BANK = "transaction.remarks.fund.transfer.other.bank";
    public static final String TRANSACTION_REMARKS_BILL_PAYMENT = "transaction.remarks.bill.payment";
    public static final String TRANSACTION_REMARKS_AIRTIME_TOPUP = "transaction.remarks.airtime.topup";
    public static final String TRANSACTION_REMARKS_M_WALLET = "transaction.remarks.mwallet";
    public static final String TRANSACTION_REMARKS_CREDIT_CARD_PAYMENT = "transaction.remarks.credit.card.bill.payment";
    public static final String TRANSACTION_REMARKS_CASH_SEND = "transaction.remarks.cash.send";

    public static final String NON_HM_CUSTOMER_FLAG = "nonHMCustomerFlag";

    public static final String BUSINESS_ID_UGBRB = "UGBRB";
    public static final String BUSINESS_ID_TZBRB = "TZBRB";
    public static final String BUSINESS_ID_KEBRB = "KEBRB";
    public static final String BUSINESS_ID_GHBRB = "GHBRB";
    public static final String BUSINESS_ID_ZWBRB = "ZWBRB";
    public static final String BUSINESS_ID_ZMBRB = "ZMBRB";
    public static final String BUSINESS_ID_BWBRB = "BWBRB";
    public static final String BUSINESS_ID_TZNBC = "TZNBC";
    public static final String BUSINESS_ID_MZBRB = "MZBRB";
    public static final String UGBRB_DEF_CURR = "UGX";
    public static final String TZBRB_DEF_CURR = "TZS";
    public static final String KEBRB_DEF_CURR = "KES";
    public static final String GHBRB_DEF_CURR = "GHS";
    public static final String ZWBRB_DEF_CURR = "USD";
    public static final String ZMBRB_DEF_CURR = "ZMW";
    public static final String BWBRB_DEF_CURR = "BWP";

    public static final String UNKNOWN_LABEL = "NOLABEL";

    public static final String USER_STATUS_MIGRATED = "Migrated";

    //ADDED TO INCLUDE DEBIT CARD VALIDATION IN SELF REGISTRATION
	public static final String DATA_TYPE_DEBIT_CARD_NO = "debitCardNo";
	public static final String DATA_TYPE_DEBIT_CARD_EXPIRY_DATE = "debitCardExpDt";
	public static final String RANDOM_DEBIT_CARD_CARD_NO_1 = "randomDebitCardNo1";
	public static final String RANDOM_DEBIT_CARD_CARD_NO_2 = "randomDebitCardNo2";
	public static final String RANDOM_DEBIT_CARD_CARD_NO_3 = "randomDebitCardNo3";
	public static final String RANDOM_DEBIT_CARD_CARD_NO_4 = "randomDebitCardNo4";
	// added for defect-2168
	public static final String SELECTED_MNO_ATTP = "selectedMNO";

	/** CR#51 */
	/** The seperator equalto. */
    public static final String SEPERATOR_EQUALTO = "=";
    /** The seperator equalto. */
    public static final String FOREX_BUY_RATE = "(BUY)";
    public static final String FOREX_SELL_RATE = "(SELL)";
	//added for CR-57
	public static final String SELECTED_DSTV_BILLER_TYPE = "selectedDstvType";
	public static final String SELECTED_DSTV_BO = "dstvBoSelected";

	//CR#46
	public static final String DATA_TYPE_CREDIT_CARD_NUMBER = "creditCardNumber";
	public static final String PRIMARY_ACC_NUMBER = "primaryAccountNumber";

	public static final String USSD_TRANSACTION_MWALLETE_SUCCESS = "label.transaction.mwallete.success";

	public static final String USSD_TRANSACTION_MWALLETE_AMOUNT = "label.amount";

	public static final String LABEL_GROUPWALLET_PAYREQUEST_ACCEPTED= "label.groupwallet.payrequest.accepted";
	public static final String LABEL_TRANSACTION_SUCCESS= "label.tran.success";
	public static final String LABEL_TRANSACTION_FAILED= "label.tran.failure";

	public static final String USSD_TRANSACTION_MWALLETE_MOBILE = "label.mobile.number";

	public static final String USSD_TRANSACTION_MWALLETE_SERVICE = "label.transaction.service";

	public static final String SPACE = " ";

	public static final String LABEL_AIRTIME_CONFIRM = "label.mwallet.confirm";
	//CR#73
    public static final String USSD_OTBP_SAVE_BENEFECIARY = "USSD_OTBP_SAVE_BENEFECIARY";
    public static final String USSD_FTOBA_SAVE_BENEFECIARY = "USSD_FTOBA_SAVE_BENEFECIARY";
    //Forgot Pin Change
    public static final String FORGOT_PIN_REQ = "Forgot Pin Required";

    public static final String LEAD_GEN_PRODUCT_NAME = "productName";
    public static final String LEAD_GEN_SUB_PRODUCT_NAME = "subProductName";
//KITS
	public static final String REASON = "Reason";
	public static final String NUMBER = "Number";
	public static final String BANK = "Bank";
	public static final String ACCNUMBER = "AccNumber";
	public static final String PHONENUMBER = "PhoneNumber";
	public static final String PRIMARYACCOUNT = "PrimaryAccount";
	public static final String DATA_TYPE_KITS_REASON="reason";

	//Added on 30/09/2016
	public static final String NAME = "Name";
	//Ended

    //CR82
    public static final String AIRTIME_PAY = "AT";
    public static final String MOBILEWALLET_PAY = "WT";

    //Kadikope
    public static final String CREDIT_BILL_PAY = "CreditCardBP";
    public static final String CREDIT_MOBILE_WALLET = "CreditCardMW";
    public static final String CREDIT_CARD_FT_CASA = "CreditCardFTCASA";
    public static final String CREDIT_CARD_FT_ONETIME_CASA = "CreditCardOT";
    //CR 85
    /** The data type address. */
    public static final String DATA_TYPE_ADDRESS = "address";
    public static final String DATA_TYPE_RESON_OF_PAYMENT = "resonOfPayment";
	// MZ DFT
    public static final String DATA_TYPE_NIB = "nib";

    //CR-86 Back Flow changes
    public static final String CASH_SEND_APIN_TID = "CSOTP004";
    public static final String CASH_SEND_APIN_CON_TID = "CSOTP005";
    public static final String CASH_SEND_MBNO_TRANSID = "CSOTP001";
    public static final String FTREGBENF_OTHBANKBENF_TRANSID = "RBOBA05";
    public static final String FTREGBENF_BARBANKBENF_TRANSID = "RBB008";
    public static final String FT_NR_BARBANKBENF_TRANSID = "OBAFTNR011";
    public static final String INT_FT_NR_BARBANKBENF_TRANSID = "OBAFTNR018";
    public static final String TRANSACTION_TYPE_AMOUNT = "amount";
    public static final String TRANS_ID_FORGOT_PIN = "FOGP002";
    public static final String TRANS_ID_SELF_REG = "SLR002";
    public static final String GHIPPSPAY_PAYEE_TYPE = "RT";
    //UBP constant
    public static final String UBP_BUSINESS_ID = "KEBRBZMBRB";

    /** Seearch for Bank code and name with min two char    */
    public static final String DATA_TYPE_SEARCHER_ALPHA_NUM_CHAR2 = "searcherAlphaNumChar2";
    public static final String GHIPPS_CUSTOMER_NAME = "customerName";
    public static final String GHIPPS_DATA_TYPE_RESON_OF_PAYMENT = "ghipssResonOfPayment";

	 //LoanRePayment
   /* public static final String LOAN_RE_PAYMENT_ENQUIRY = "LoanEnquiry";
    public static final String[] BACK_FLOW_ERROR_SCREEN_TRANS = {"CSOTP004", "CSOTP005", "CSOTP001","RBOBA05","RBB008","OBAFTNR011",
    	"OBAFTNR018","FOGP002","SLR002","RBOBA011","ROTHBB005","LREQ000","LREQ001","MLP000","MLP001"};
    public static final String LOAN_RE_PAYMENT_OVER_DUE_AMT = "loanOverDueAmount";
    public static final String LOAN_RE_PAYMENT_CURRENCY_CODE= "loanAccCurrency";
    public static final String LOAN_FROM_ACC_MASKED = "AccNoOnScreen";
    public static final String LOAN_FROM_ACCOUNT_SELECTED = "selectedAccount";
    public static final String LOAN_FROM_ACC_CUR_CODE = "debitAccCurCode";*/

    // CPB change
    public static final String TRANSACTION_REMARKS_PESA_LINK = "transaction.remarks.pesa.link";
    public static final String PESA_LINK_REMARKS = "pmtRem";

    // WUC change - Botswana
    public static final String WUC_BILLER_CATEGORY = "wucBillerCategory";
    public static final String DATA_TYPE_WUC_REF_NO = "wucrefno";

    public static final String FREE_DIAL_MWALLET="FREEMDIALMTN";
    public static final String FREE_DIAL_MWALLET_BILLER="MTNCR-5";

    public static final String DATA_TYPE_MZ_REF_NO = "mzrefno";

    public static final String GH_FREE_DIAL_USSD_TRAN_ID="GH_FREE_DIAL_USSD";

    //GePG
    public static final String GEPG_BMG_CONTROL_NUMBER_PARAM_NAME = "controlNumber";
    public static final String GEPG_BMG_BILLER_NM_PARAM_NAME = "billerName";
    public static final String GEPG_BMG_BILLER_ID_PARAM_NAME = "billerID";
    public static final String GEPG_BILLER_INFO = "gePGBiller";
    public static final String GePG_BILL_DETAIL = "gePGBillDetail";

    public static final String GePG_BILL_PAYMENT_TYPE = "EXACT";
    public static final String GePG_FULL_BILL_PAYMENT_TYPE = "FULL";
    public static final String GePG_PARTIAL_BILL_PAYMENT_TYPE = "PART";

    //MasterPassQR
    public static final String MPQR_BILLER_INFO = "mpqrBiller";

  //probase
    public static final String PROBASE_PIN_NUMBER_PARAM_NAME = "controlNumber";
    public static final String PROBASE_BILLER_INFO = "probaseBillInfo";
    public static final String PROBASE_BILL_DETAILS = "probaseBillDetail";
    public static final String PROBASE_BMG_BILLER_NM_PARAM_NAME = "billerName";
    public static final String PROBASE_BMG_BILLER_ID_PARAM_NAME = "billerID";
    public static final String PROBASE_INVOICE_BILL_DETAILS= "probaseBillDetail";
    public static final String PROBASE_INVOICE_DETAILS = "probaseInvoiceDetail";
	
	public static final String LEAD_GEN_SUB_PRODUCT_NAME_TIMIZA = "Timiza";
	
	public static final int REFERENCE_NUMBER_LENGTH = 10;
	public static final int REFERENCE_METHOD_RECURSIVE_LIMIT = 0;
	public static final boolean USE_LETTER = false;
	public static final boolean USE_NUMBER = true;
}
