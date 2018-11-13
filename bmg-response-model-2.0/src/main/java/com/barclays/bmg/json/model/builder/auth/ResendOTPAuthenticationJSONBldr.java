package com.barclays.bmg.json.model.builder.auth;

import java.text.MessageFormat;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.exception.BMBCustomClassCastException;
import com.barclays.bmg.json.model.auth.OTPResponseModel;
import com.barclays.bmg.json.model.builder.BMBCommonJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.OTPGenerateAuthenticationOperationResponse;
import com.barclays.bmg.service.request.ScriptResourceServiceRequest;
import com.barclays.bmg.service.response.ScriptResourceServiceResponse;

public class ResendOTPAuthenticationJSONBldr extends BMBCommonJSONBuilder implements
		BMBJSONBuilder {

	@Override
	public Object createJSONResponse(ResponseContext responseContext) {

		if(responseContext instanceof OTPGenerateAuthenticationOperationResponse) {
			OTPGenerateAuthenticationOperationResponse resp = (OTPGenerateAuthenticationOperationResponse) responseContext;

			BMBPayload bmbPayload = new BMBPayload(createHeader(resp));

			populatePayLoad(resp, bmbPayload);

			return bmbPayload;
		} else {
			throw new BMBCustomClassCastException("BMB Custom Class Cast Exception");
		}
	}

	protected BMBPayloadHeader createHeader(ResponseContext response) {

		BMBPayloadHeader header = new BMBPayloadHeader();
		if (response != null && response.isSuccess()) {
			header.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
			header.setResMsg("");
		} else  if(response != null){
			header.setResCde(response.getResCde());
			header.setResMsg(response.getResMsg());
		}

		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
		header.setResId(ResponseIdConstants.RESEND_OTP_RESPONSE_ID);

		return header;

	}

	protected void populatePayLoad(
			OTPGenerateAuthenticationOperationResponse otpAuthenticationOperationResponse,
			BMBPayload bmbPayload) {
		OTPResponseModel otpResponseModel = null;
		if (otpAuthenticationOperationResponse != null
				&& otpAuthenticationOperationResponse.isSuccess()) {
			otpResponseModel = new OTPResponseModel();

			otpResponseModel.setOtpPfx(otpAuthenticationOperationResponse
					.getOtpPrefix());

			// --- This code belongs to map 'otpFooter' key for Rel-2 Message
			ScriptResourceServiceResponse scriptResourceServiceResponse = null;
			ScriptResourceServiceRequest scriptResourceServiceRequest = new ScriptResourceServiceRequest();

			scriptResourceServiceRequest
					.setContext(otpAuthenticationOperationResponse.getContext());

			scriptResourceServiceRequest.setScriptKey(rel1ErrCdeMap
					.get("otpFooter"));

			scriptResourceServiceResponse = scriptResourceService
					.getScriptResourceByKey(scriptResourceServiceRequest);
			String otpFooter = scriptResourceServiceResponse.getScriptValue();
			otpAuthenticationOperationResponse.setOtpFooter(otpFooter);
			// ----------- Code change end -------------

			otpResponseModel.setOtpFtr(otpAuthenticationOperationResponse
					.getOtpFooter());

			String mobile = otpAuthenticationOperationResponse.getMobilePhone();
			mobile = getMaskedMobile(otpAuthenticationOperationResponse, mobile);
			String otpHeaderLine1 = MessageFormat.format(
					otpAuthenticationOperationResponse.getOtpHeaderLine1(),
					new Object[] { mobile });
			otpResponseModel.setOtpHdrLn1(otpHeaderLine1);
			otpResponseModel.setOtpHdrLn2(otpAuthenticationOperationResponse
					.getOtpHeaderLine2());

		}
		bmbPayload.setPayData(otpResponseModel);
	}

	private String getMaskedMobile(
			OTPGenerateAuthenticationOperationResponse otpAuthenticationOperationResponse,
			String mobile) {
		String maskPattern = getSystemParam(otpAuthenticationOperationResponse
				.getContext(), "mobileNumberMaskPattern");
		String toMaskNumber = maskNumber(mobile, maskPattern);

		return toMaskNumber;
	}
}
