package com.barclays.bmg.constants;

public interface MessageCodeConstant {

    String LOGINCOMMAND_USERNAME_EMPTY = "logincommand.username.empty";
    String LOGINCOMMAND_PASSWORD_EMPTY = "logincommand.password.empty";
    String OTPCOMMAND_PASSWORD_EMPTY = "otpcommand.otp.empty";
    String SQACOMMAND_PASSWORD_EMPTY = "sqacommand.sqa.empty";
    String AUTH_SQA_WRONG = "auth.sqa.wrong";
    String AUTH_OTP_WRONG = "auth.otp.wrong";
    String AUTH_USERNAME_PASSWORD_WRONG = "auth.password.username.wrong";

    // FundTransfer

    String FUNDTRANSFERDATACOMMAND_SOURCEACCOUNT_EMPTY = "FTR00502";
    String FUNDTRANSFERDATACOMMAND_TARGETACCOUNT_EMPTY = "FTR00503";
    String FUNDTRANSFERDATACOMMAND_TXAMOUNT_EMPTY = "FTR00504";
    String FUNDTRANSFERDATACOMMAND_TXDATE_EMPTY = "FTR00505";
    String FUNDTRANSFERDATACOMMAND_FORBID_SAME_ACCOUNT = "FTR00506";
    String FUNDTRANSFEREXECUTECOMMAND_TXREFKEY_EMPTY = "fundtransferexecutecommand.txrefkey.empty";
    String FT_NEVER_INITIATED = "fundtransferexecutecommand.execution.error.ftwasneverinitiated";
    String FT_TRANSACTIONABILITY_ERROR = "fundtransferexecutecommand.execution.transactionability.error";
    String MSG_PMT_COM_INSUFFICIENT_BALANCE = "PMT_COM_INSUFFICIENT_BALANCE";
    String MSG_PMT_COM_LIMIT_ONCE = "PMT_COM_LIMIT_ONCE";
    String MSG_PMT_COM_LIMIT_DAILY = "PMT_COM_LIMIT_DAILY";
    String INVALID_SRC_ACCT = "NO_VALID_SRC_ACCT";
    String INVALID_DEST_ACCT = "NO_VALID_DST_ACCT";

    String OWN_FT_INIT_NOACCOUNTS_FOUND = "FTR00501";
    String OWN_FT_ONLY_ONE_ACCOUNT = "FTR00514";

    // BillPayment
    String PAYMENT_PAYEE_GROUP_EMPTY = "paymentcommand.payeegroup.empty";
    String PAYMENT_BENEFICIARY_ID_EMPTY = "paymentcommand.beneficiaryid.empty";

    String PAYMENT_INVALID_BENEFICIARY = "payment.invalid.beneficiary";

    String NO_ACCOUNTS_FOR_SUMMARY = "ACT_ACTSUMMARY_NOACTFORSUMMARY";
    String PAYMENT_FROM_ACCOUNT_EMPTY = "paymentcommand.fromaccount.empty";
    String PAYMENT_AMOUNT_EMPTY = "paymentcommand.amount.empty";
    String PAYMENT_AMOUNT_GREATER_THEN_ZERO = "payment.amount.greater.then.zero";
    String PAYMENT_INVALID_FROMACCOUNT_NO = "payment.invalid.fromaccount";

    String BILLPAYMENTEXECUTECOMMAND_TXREFKEY_EMPTY = "billpaymentexecutecommand.txrefkey.empty";
    String BP_NEVER_INITIATED = "billpaymentexecutecommand.execution.error.bpwasneverinitiated";

    String NO_OUTSTANDING_BILL = "PMT_BP_NO_OUTSTANDING_BILL";

    String BP_TRANSACTIONABILITY_ERROR = "fundtransferexecutecommand.execution.transactionability.error";

    // MTP

    String PAYMENT_MTP_BENE_ID_EMPTY = "paymentcommand.beneficiaryid.empty";
    String PAYMENT_MTP_SERVICE_EMPTY = "paymentcommand.mtpservice.empty";
    String INVALID_BILL_PAY_AMT = "PMT_BP_INVALID_AMOUNT";
    // Barclays Card.
    String BCD_NO_PAY_ID_ACCT_NO = "paymentcommand.beneficiaryid.empty";

    String CASH_SEND_NEVER_INITIATED = "cashOneTimeExecuteCommand.execution.error.ftwasneverinitiated";
}
