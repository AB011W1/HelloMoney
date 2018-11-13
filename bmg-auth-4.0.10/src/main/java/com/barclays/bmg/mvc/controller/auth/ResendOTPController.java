package com.barclays.bmg.mvc.controller.auth;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.auth.ResendOTPAuthenticationJSONBldr;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.operation.OTPAuthenticationOperation;
import com.barclays.bmg.operation.request.OTPGenerateAuthenticationOperationRequest;
import com.barclays.bmg.operation.response.OTPGenerateAuthenticationOperationResponse;

/**
 * Login Controller
 * 
 * @author e20026338
 * 
 */
public class ResendOTPController extends BMBAbstractController {

    OTPAuthenticationOperation otpAuthenticationOperation;
    ResendOTPAuthenticationJSONBldr resendOTPAuthenticationJSONBldr;

    /**
     * Entry point into controller. Handle http request.
     */
    @Override
    protected BMBBaseResponseModel handleRequestInternal1(HttpServletRequest request, HttpServletResponse response) throws Exception {

	BMBBaseResponseModel responseModel = null;

	/*
	 * BMBPayload returnBMBPayloadResponse = new BMBPayload(); BMBPayloadHeader bmbPayloadHeader = new BMBPayloadHeader();
	 * 
	 * BMBPayloadData responseModel = null;
	 */

	// String resId = null;
	OTPGenerateAuthenticationOperationRequest otpAuthenticationOperationRequest = makeRequest(request);
	OTPGenerateAuthenticationOperationResponse otpAuthenticationOperationResponse = otpAuthenticationOperation
		.generate(otpAuthenticationOperationRequest);

	setProcessMapIntoSession(request, SessionConstant.SESSION_CHALLENGE_ID, otpAuthenticationOperationResponse.getChallengeId());

	if (otpAuthenticationOperationResponse.isSuccess()) {

	    responseModel = (BMBBaseResponseModel) resendOTPAuthenticationJSONBldr.createJSONResponse(otpAuthenticationOperationResponse);

	    /*
	     * setUserMapIntoSession(request, SessionConstant.SESSION_CUSTOMER_ID, otpAuthenticationOperationResponse.getCustomerId());
	     * setUserMapIntoSession(request, SessionConstant.SESSION_USER_ID, otpAuthenticationOperationResponse.getContext().getUserId());
	     * setProcessMapIntoSession(request, SessionConstant.SESSION_CHALLENGE_ID, otpAuthenticationOperationResponse.getChallengeId());
	     * 
	     * 
	     * OTPResponseModel otpResponseModel = new OTPResponseModel(); // otpResponseModel.setSuccess(true);
	     * otpResponseModel.setOtpPfx(otpAuthenticationOperationResponse .getOtpPrefix());
	     * otpResponseModel.setOtpFtr(otpAuthenticationOperationResponse .getOtpFooter());
	     * 
	     * 
	     * String mobile = otpAuthenticationOperationResponse.getMobilePhone(); mobile = getMaskedMobile(otpAuthenticationOperationResponse,
	     * mobile); String otpHeaderLine1 = MessageFormat.format(otpAuthenticationOperationResponse .getOtpHeaderLine1(), new Object[] { mobile
	     * }); otpResponseModel .setOtpHdrLn1(otpHeaderLine1); otpResponseModel .setOtpHdrLn2(otpAuthenticationOperationResponse
	     * .getOtpHeaderLine2());
	     * 
	     * responseModel = otpResponseModel;
	     */

	}

	/*
	 * bmbPayloadHeader.setResId(ResponseIdConstants.RESEND_OTP_RESPONSE_ID); bmbPayloadHeader
	 * .setResCde(otpAuthenticationOperationResponse.getResCde()); bmbPayloadHeader .setResMsg(otpAuthenticationOperationResponse.getResMsg());
	 * bmbPayloadHeader .setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
	 * 
	 * returnBMBPayloadResponse.setPayHdr(bmbPayloadHeader); returnBMBPayloadResponse.setPayData(responseModel);
	 */

	return responseModel;

    }

    /**
     * make request for otp genrate
     * 
     * @param request
     * @return
     */
    private OTPGenerateAuthenticationOperationRequest makeRequest(HttpServletRequest request) {

	OTPGenerateAuthenticationOperationRequest otpAuthenticationOperationRequest = new OTPGenerateAuthenticationOperationRequest();

	Context context = new Context();

	context.setActivityId(ActivityConstant.LOGIN_ACTIVITY_ID);

	Map<String, Object> userMap = getUserMapFromSession(request);
	context.setBusinessId((String) userMap.get(SessionConstant.SESSION_BUSINESS_ID));
	context.setCountryCode((String) userMap.get(SessionConstant.SESSION_COUNTRY_CODE));
	context.setLanguageId((String) userMap.get(SessionConstant.SESSION_LANGUAGE_ID));
	context.setSystemId((String) userMap.get(SessionConstant.SESSION_SYSTEM_ID));
	context.setCustomerId((String) userMap.get(SessionConstant.SESSION_CUSTOMER_ID));
	context.setUserId((String) userMap.get(SessionConstant.SESSION_USER_ID));

	otpAuthenticationOperationRequest.setContext(context);

	// Map<String, Object> processMap = getProcessMapFromSession(request);

	otpAuthenticationOperationRequest.setMobilePhone((String) userMap.get(SessionConstant.SESSION_MOBILE_PHONE));
	otpAuthenticationOperationRequest.setCustomerId((String) userMap.get(SessionConstant.SESSION_CUSTOMER_ID));

	return otpAuthenticationOperationRequest;

    }

    private String getMaskedMobile(OTPGenerateAuthenticationOperationResponse otpAuthenticationOperationResponse, String mobile) {
	String maskPattern = getSystemParam(otpAuthenticationOperationResponse.getContext(), "mobileNumberMaskPattern");
	String toMaskNumber = maskNumber(mobile, maskPattern);

	return toMaskNumber;
    }

    public String maskNumber(String toMaskNumber, String maskPattern) {
	String updatedmaskPattern = maskPattern;
	updatedmaskPattern = constructMaskPattern(toMaskNumber, updatedmaskPattern);
	int maskPatternLength = updatedmaskPattern.length();
	char[] cArray = updatedmaskPattern.toCharArray();
	int numberLen = 0;
	int lastPosition = 0;
	for (int i = 0; i < maskPatternLength; i++) {
	    char c = cArray[i];

	    if (Character.isDigit(c)) {
		numberLen++;
		lastPosition = i;
	    }
	}

	if (toMaskNumber.length() <= numberLen) {
	    return toMaskNumber;
	}

	String maskNumber = toMaskNumber;
	// use the real length of the toMaskNumber
	int maskLength = maskPatternLength - numberLen;
	if (maskLength > toMaskNumber.length() - numberLen) {
	    maskLength = toMaskNumber.length() - numberLen;
	}

	if (lastPosition == maskPatternLength - 1) {
	    // "*******999" "XXXX0000"
	    String maskString = updatedmaskPattern.substring(0, maskLength);
	    maskNumber = maskString + toMaskNumber.substring(toMaskNumber.length() - numberLen);
	} else if (lastPosition == numberLen - 1) {
	    // "9999*******" "0000XXXX"
	    String maskString = updatedmaskPattern.substring(numberLen, numberLen + maskLength);

	    maskNumber = toMaskNumber.substring(0, numberLen) + maskString;
	}

	return maskNumber;
    }

    public String constructMaskPattern(String toMaskNumber, String maskPattern) {
	// maskPattern
	// "xxxxxxxxxxxxxxxxxxxxx0000" "xxxx00000000000000000000"
	// "0000xxxxxxxxxxxxxxxxx" "00000000000000000xxxx"
	// toMaskNumber
	// "1234567890"
	String updatedmaskPattern = maskPattern;
	int maskPatternLength = updatedmaskPattern.length();
	if (maskPatternLength < toMaskNumber.length()) {
	    return updatedmaskPattern;
	}

	char[] cArray = updatedmaskPattern.toCharArray();
	int numLength = 0;
	int numLastPosition = 0;
	StringBuffer numBuffer = new StringBuffer();
	StringBuffer maskBuffer = new StringBuffer();
	for (int i = 0; i < maskPatternLength; i++) {
	    char c = cArray[i];

	    if (Character.isDigit(c)) {
		numLength++;
		numLastPosition = i;
		numBuffer.append(c);
	    } else {
		maskBuffer.append(c);
	    }

	}

	int maskLength = maskPatternLength - numLength;
	if (numLastPosition == numLength - 1) {
	    // "0000xxxxxxxxxxxxxxxxx" "00000000000000000xxxx"
	    if (maskLength > numLength) {
		// "0000xxxxxxxxxxxxxxxxx"
		updatedmaskPattern = updatedmaskPattern.substring(0, toMaskNumber.length());
		return updatedmaskPattern;
	    } else {
		// "00000000000000000xxxx"
		if (toMaskNumber.length() < maskLength) {
		    return maskBuffer.substring(0, toMaskNumber.length());
		}
		String subNumber = numBuffer.substring(0, toMaskNumber.length() - maskLength);

		return subNumber + maskBuffer.toString();
	    }

	}
	if (numLastPosition == maskPatternLength - 1) {
	    // "xxxxxxxxxxxxxxxxxxxxx0000" "xxxx00000000000000000000"
	    if (maskLength > numLength) {
		// "xxxxxxxxxxxxxxxxxxxxx0000"
		if (toMaskNumber.length() < numLength) {
		    return numBuffer.substring(0, toMaskNumber.length());
		}
		String subMask = maskBuffer.substring(0, toMaskNumber.length() - numLength);

		return subMask + numBuffer.toString();
	    } else if (maskLength < numLength) {
		// "xxxx00000000000000000000"
		updatedmaskPattern = updatedmaskPattern.substring(0, toMaskNumber.length());
		return updatedmaskPattern;
	    }

	}

	return updatedmaskPattern;

    }// end

    public OTPAuthenticationOperation getOtpAuthenticationOperation() {
	return otpAuthenticationOperation;
    }

    public void setOtpAuthenticationOperation(OTPAuthenticationOperation otpAuthenticationOperation) {
	this.otpAuthenticationOperation = otpAuthenticationOperation;
    }

    public ResendOTPAuthenticationJSONBldr getResendOTPAuthenticationJSONBldr() {
	return resendOTPAuthenticationJSONBldr;
    }

    public void setResendOTPAuthenticationJSONBldr(ResendOTPAuthenticationJSONBldr resendOTPAuthenticationJSONBldr) {
	this.resendOTPAuthenticationJSONBldr = resendOTPAuthenticationJSONBldr;
    }

    @Override
    protected String getActivityId() {

	return ActivityConstant.LOGIN_ACTIVITY_ID;
    }

}
