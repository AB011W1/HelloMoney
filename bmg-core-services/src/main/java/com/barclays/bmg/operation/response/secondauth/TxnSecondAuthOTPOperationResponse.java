package com.barclays.bmg.operation.response.secondauth;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.operation.response.OTPGenerateAuthenticationOperationResponse;

public class TxnSecondAuthOTPOperationResponse extends ResponseContext {

    private static final long serialVersionUID = -583225083246206575L;

    private OTPGenerateAuthenticationOperationResponse otpResponse;
    private String txnRefNo;

    public String getTxnRefNo() {
	return txnRefNo;
    }

    public void setTxnRefNo(String txnRefNo) {
	this.txnRefNo = txnRefNo;
    }

    public OTPGenerateAuthenticationOperationResponse getOtpResponse() {
	return otpResponse;
    }

    public void setOtpResponse(OTPGenerateAuthenticationOperationResponse otpResponse) {
	this.otpResponse = otpResponse;
    }

}
