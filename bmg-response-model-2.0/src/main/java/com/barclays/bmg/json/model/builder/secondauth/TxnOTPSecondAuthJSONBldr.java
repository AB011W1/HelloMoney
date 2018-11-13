package com.barclays.bmg.json.model.builder.secondauth;

import com.barclays.bmg.constants.AuthResponseCodeConstants;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.exception.BMBCustomClassCastException;
import com.barclays.bmg.json.model.auth.OTPResponseModel;
import com.barclays.bmg.json.model.billpayment.TxnOTPSecondAuthRespModel;
import com.barclays.bmg.json.model.builder.auth.OTPAuthenticationJSONBldr;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.secondauth.TxnSecondAuthOTPOperationResponse;


public class TxnOTPSecondAuthJSONBldr extends OTPAuthenticationJSONBldr {

	@Override
	public Object createJSONResponse(ResponseContext responseContext) {

		if(responseContext instanceof TxnSecondAuthOTPOperationResponse) {
			TxnSecondAuthOTPOperationResponse resp = (TxnSecondAuthOTPOperationResponse) responseContext;

			BMBPayload bmbPayload = new BMBPayload(createHeader(resp.getOtpResponse()));

			populatePayLoad(resp, bmbPayload);

			return bmbPayload;
		} else {
			throw new BMBCustomClassCastException("BMB Custom Class Cast Exception");
		}
	}

	@Override
	protected BMBPayloadHeader createHeader(ResponseContext response) {

		BMBPayloadHeader header = new BMBPayloadHeader();
		if(response !=null && response.isSuccess()){
			header.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
		}else{
			header.setResCde(AuthResponseCodeConstants.SECOND_AUTH_INVALID);
		}
		header.setResMsg("");
		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
		header.setResId(ResponseIdConstants.TXN_SECOND_AUTH_OTP_RESPONSE_ID);

		return header;

	}

	protected void populatePayLoad(
			TxnSecondAuthOTPOperationResponse response,
			BMBPayload bmbPayload) {
		TxnOTPSecondAuthRespModel responseModel = null;
		if(response!=null && response.isSuccess()){
			responseModel = new TxnOTPSecondAuthRespModel();

			super.populatePayLoad(response.getOtpResponse(), bmbPayload);
			OTPResponseModel otpresponsemodel = (OTPResponseModel)bmbPayload.getPayData();
			if(otpresponsemodel!=null){
				responseModel.setOtpFtr(otpresponsemodel.getOtpFtr());
				responseModel.setOtpHdrLn1(otpresponsemodel.getOtpHdrLn1());
				responseModel.setOtpHdrLn2(otpresponsemodel.getOtpHdrLn2());
				responseModel.setOtpPfx(otpresponsemodel.getOtpPfx());
			}
			responseModel.setTxnRefNo(response.getTxnRefNo());
		}
		bmbPayload.setPayData(responseModel);
	}
}
