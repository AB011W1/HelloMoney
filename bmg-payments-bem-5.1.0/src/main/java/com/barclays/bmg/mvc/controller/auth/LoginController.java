package com.barclays.bmg.mvc.controller.auth;

//USSD Test
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.AuthenticationDTO;
import com.barclays.bmg.dto.CustomerDTO;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.BMBPayloadData;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.auth.LoginCommand;
import com.barclays.bmg.operation.OTPAuthenticationOperation;
import com.barclays.bmg.operation.SPAuthenticationOperation;
import com.barclays.bmg.operation.SQAAuthenticationOperation;
import com.barclays.bmg.operation.request.OTPGenerateAuthenticationOperationRequest;
import com.barclays.bmg.operation.request.SPAuthenticationOperationRequest;
import com.barclays.bmg.operation.request.SQAGenerateAuthenticationOperationRequest;
import com.barclays.bmg.operation.response.OTPGenerateAuthenticationOperationResponse;
import com.barclays.bmg.operation.response.SPAuthenticationOperationResponse;
import com.barclays.bmg.operation.response.SQAGenerateAuthenticationOperationResponse;
import com.barclays.bmg.type.AuthType;
import com.barclays.bmg.utils.BMGFormatUtility;

/**
 * Login Controller
 *
 * @author e20026338
 *
 */
public class LoginController extends BMBAbstractCommandController {

    SPAuthenticationOperation spAuthenticationOperation;
    OTPAuthenticationOperation otpAuthenticationOperation;
    SQAAuthenticationOperation sqaAuthenticationOperation;
    private BMBJSONBuilder otpAuthenticationJSONBldr;
    private BMBJSONBuilder sqaAuthenticationJSONBldr;
    private BMBJSONBuilder invalidLoginJSONBldr;

    /**
     * Entry point into controller. Handle http request.
     */

    @Override
    protected BMBBaseResponseModel handle1(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)
	    throws Exception {

	BMBBaseResponseModel responseModel = null;

	LoginCommand loginCommand = (LoginCommand) command;

	SPAuthenticationOperationRequest spAuthenticationOperationRequest = new SPAuthenticationOperationRequest(loginCommand.getUsrNam(),
		loginCommand.getPass());

	setUserMapIntoSession(request, SessionConstant.SESSION_LOGIN_ID, loginCommand.getUsrNam());

	setUserMapIntoSession(request, SessionConstant.SESSION_LOGIN_ID, "BIRTZ001");

	addContext(spAuthenticationOperationRequest, request);

	SPAuthenticationOperationResponse spAuthenticationOperationResponse = generateMockSPResponse(spAuthenticationOperationRequest);

	/**
	 * call second auth method and prepare response model
	 */

	if (spAuthenticationOperationResponse.isSuccess()) {

	    setSessionAttribute(request, SessionConstant.SESSION_PP_MAP, spAuthenticationOperationResponse.getAuthenticationDTO().getPPMap());

	    String secondAuthMethod = spAuthenticationOperationResponse.getSecondAuthMethod();

	    // --- Call method to set common attribute into session & context
	    setCommonAttribInSessionNContext(spAuthenticationOperationResponse, request);

	    // --- Call method to set password expiry warning & expiry attribute
	    // into session
	    setPasswordExpiryWarning(spAuthenticationOperationResponse, request);

	    if (AuthType.OTP.getValue().equals(secondAuthMethod)) {

		responseModel = generateOTP(spAuthenticationOperationResponse, request);

		setProcessMapIntoSession(request, SessionConstant.SESSION_SECOND_AUTH_TYPE, ResponseIdConstants.OTP_RESPONSE_ID);

	    } else if (AuthType.SQA.getValue().equals(secondAuthMethod)) {

		responseModel = getSQAChallenge(spAuthenticationOperationResponse, request);

		setProcessMapIntoSession(request, SessionConstant.SESSION_SECOND_AUTH_TYPE, ResponseIdConstants.SQA_RESPONSE_ID);

	    } else {

		response.sendRedirect(request.getContextPath() + getSecurityCheckRedirectURL());
		responseModel = (BMBBaseResponseModel) invalidLoginJSONBldr.createJSONResponse(spAuthenticationOperationResponse);
	    }

	} else {
	    responseModel = (BMBBaseResponseModel) invalidLoginJSONBldr.createJSONResponse(spAuthenticationOperationResponse);

	}

	return responseModel;

    }

    private SPAuthenticationOperationResponse generateMockSPResponse(SPAuthenticationOperationRequest spAuthenticationOperationRequest) {

	SPAuthenticationOperationResponse response = new SPAuthenticationOperationResponse();
	AuthenticationDTO auth = new AuthenticationDTO();
	CustomerDTO cust = new CustomerDTO();
	Map<String, String> ppMap = new HashMap<String, String>();

	ppMap.put("BK", "180006971");

	auth.setPPMap(ppMap);

	// response.setSecondAuthMethod("OTP");
	auth.setAuthenticated(true);
	// auth.setAuthType(AuthType.OTP);
	auth.setLoginName("BIRTZ001");
	auth.setUserId("1341");
	cust.setUserId("1341");
	cust.setCustomerID("100000103472");
	cust.setMobilePhone("65-94889106");
	auth.setCustomerDTO(cust);
	response.setSuccess(true);
	response.setAuthenticationDTO(auth);

	response.setContext(spAuthenticationOperationRequest.getContext());

	return response;
    }

    /**
     * Add parameters to context
     *
     * @param request
     */
    private void addContext(SPAuthenticationOperationRequest request, HttpServletRequest httpRequest) {

	Context context = createContext(httpRequest);

	context.setActivityId(ActivityConstant.LOGIN_ACTIVITY_ID);

	request.setContext(context);
    }

    /**
     * generate OTP
     *
     * @param spAuthenticationOperationResponse
     * @return
     */
    private BMBBaseResponseModel generateOTP(SPAuthenticationOperationResponse spAuthenticationOperationResponse, HttpServletRequest httpRequest) {
	BMBBaseResponseModel responseModel = null;

	OTPGenerateAuthenticationOperationRequest otpAuthenticationOperationRequest = new OTPGenerateAuthenticationOperationRequest();
	otpAuthenticationOperationRequest.setContext(spAuthenticationOperationResponse.getContext());
	AuthenticationDTO authenticationDTO = spAuthenticationOperationResponse.getAuthenticationDTO();
	otpAuthenticationOperationRequest.setCustomerId(authenticationDTO.getCustomerDTO().getCustomerID());
	otpAuthenticationOperationRequest.setMobilePhone(authenticationDTO.getCustomerDTO().getMobilePhone());
	otpAuthenticationOperationRequest.setSmsParams(authenticationDTO.getSmsParams());
	otpAuthenticationOperationRequest.setSmsTemplate(authenticationDTO.getSmsTemplate());

	otpAuthenticationOperationRequest.setAuthenticationDTO(spAuthenticationOperationResponse.getAuthenticationDTO());
	// OTPGenerateAuthenticationOperationResponse
	// otpAuthenticationOperationResponse = otpAuthenticationOperation
	// .generate(otpAuthenticationOperationRequest);

	// Mock it
	OTPGenerateAuthenticationOperationResponse otpAuthenticationOperationResponse = new OTPGenerateAuthenticationOperationResponse();

	otpAuthenticationOperationResponse.setSuccess(true);

	otpAuthenticationOperationResponse.setContext(spAuthenticationOperationResponse.getContext());

	otpAuthenticationOperationResponse.setMobilePhone(otpAuthenticationOperationRequest.getMobilePhone());

	if (otpAuthenticationOperationResponse.isSuccess()) {

	    setProcessMapIntoSession(httpRequest, SessionConstant.SESSION_CHALLENGE_ID, otpAuthenticationOperationResponse.getChallengeId());

	}

	responseModel = (BMBBaseResponseModel) otpAuthenticationJSONBldr.createJSONResponse(otpAuthenticationOperationResponse);

	return responseModel;
    }

    /**
     * get SQA Challenge
     *
     * @param spAuthenticationOperationResponse
     * @return
     */
    private BMBBaseResponseModel getSQAChallenge(SPAuthenticationOperationResponse spAuthenticationOperationResponse, HttpServletRequest httpRequest) {
	BMBBaseResponseModel responseModel = null;

	Context context = spAuthenticationOperationResponse.getContext();

	SQAGenerateAuthenticationOperationRequest sqaAuthenticationOperationRequest = new SQAGenerateAuthenticationOperationRequest();
	sqaAuthenticationOperationRequest.setContext(context);

	SQAGenerateAuthenticationOperationResponse sqaAuthenticationOperationResponse = sqaAuthenticationOperation
		.generate(sqaAuthenticationOperationRequest);

	if (sqaAuthenticationOperationResponse.isSuccess()) {

	    setProcessMapIntoSession(httpRequest, SessionConstant.SESSION_QUESTION_ID, sqaAuthenticationOperationResponse.getQuestionId());

	    responseModel = (BMBBaseResponseModel) sqaAuthenticationJSONBldr.createJSONResponse(sqaAuthenticationOperationResponse);

	}

	return responseModel;
    }

    private void setCommonAttribInSessionNContext(SPAuthenticationOperationResponse spAuthenticationOperationResponse, HttpServletRequest httpRequest) {

	String salutFullName = "";
	String fullName = "";

	Context context = spAuthenticationOperationResponse.getContext();

	salutFullName = spAuthenticationOperationResponse.getAuthenticationDTO().getCustomerDTO().getSalutation();

	fullName = spAuthenticationOperationResponse.getAuthenticationDTO().getCustomerDTO().getFullName();

	if (StringUtils.isEmpty(fullName)) {
	    fullName = spAuthenticationOperationResponse.getAuthenticationDTO().getCustomerDTO().getGivenName() + " "
		    + spAuthenticationOperationResponse.getAuthenticationDTO().getCustomerDTO().getSurname1();
	}

	salutFullName = salutFullName + " " + fullName;

	CustomerDTO customerDTO = spAuthenticationOperationResponse.getAuthenticationDTO().getCustomerDTO();

	String customerId = customerDTO.getCustomerID();
	String userId = customerDTO.getUserId();
	String mobileNo = customerDTO.getMobilePhone();

	// FIXME Remove these entries. Now we have a CustomerDTO in session

	setUserMapIntoSession(httpRequest, SessionConstant.SESSION_CUSTOMER_ID, customerId);
	setUserMapIntoSession(httpRequest, SessionConstant.SESSION_USER_ID, userId);
	setUserMapIntoSession(httpRequest, SessionConstant.SESSION_FULL_NAME, BMGFormatUtility.removeSpaceInBetween(salutFullName));

	setUserMapIntoSession(httpRequest, SessionConstant.SESSION_MOBILE_PHONE, mobileNo);

	setUserMapIntoSession(httpRequest, SessionConstant.SESSION_CUSTOMER_DTO, customerDTO);

	BMBContextHolder.getContext().setCustomerId(customerId);

	context.setCustomerId(customerId);
	context.setMobilePhone(mobileNo);
	context.setUserId(userId);
	context.setFullName(salutFullName);

    }

    private void setPasswordExpiryWarning(SPAuthenticationOperationResponse spAuthenticationOperationResponse, HttpServletRequest httpRequest) {

	/* Checking for password expiry */

	boolean isNeedWarningChangePassword = false;

	int leftWarningDays = spAuthenticationOperationResponse.getAuthenticationDTO().getPasswordLeftWarningDays();

	if (leftWarningDays > 0) {

	    isNeedWarningChangePassword = true;

	    setProcessMapIntoSession(httpRequest, SessionConstant.LEFT_WARNING_DAYS,Integer.valueOf(leftWarningDays).toString());
	}

	// --- set for PASSWORD EXPIRY
	setProcessMapIntoSession(httpRequest, SessionConstant.IS_NEED_CHANGE_PWD, Boolean.valueOf(spAuthenticationOperationResponse
		.getAuthenticationDTO().isNeedChangePWD()).toString());

	// --- set for PASSWORD WARNING
	setProcessMapIntoSession(httpRequest, SessionConstant.IS_NEED_WARNING_CHAHGE_PWD,Boolean.valueOf(isNeedWarningChangePassword).toString());
    }

    /**
     * create failure model
     *
     * @return
     */
    private BMBPayloadData createFailureModel() {

	BMBPayloadData responseModel = null;
	// responseModel.setSuccess(false);
	return responseModel;

    }

    private String getMaskedMobile(OTPGenerateAuthenticationOperationResponse otpAuthenticationOperationResponse, String mobile) {
	String maskPattern = getSystemParam(otpAuthenticationOperationResponse.getContext(), "mobileNumberMaskPattern");
	String toMaskNumber = maskNumber(mobile, maskPattern);

	return toMaskNumber;
    }

    public String maskNumber(String toMaskNumber, String maskPattern) {
	maskPattern = constructMaskPattern(toMaskNumber, maskPattern);
	int maskPatternLength = maskPattern.length();
	char[] cArray = maskPattern.toCharArray();
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
	    String maskString = maskPattern.substring(0, maskLength);
	    maskNumber = maskString + toMaskNumber.substring(toMaskNumber.length() - numberLen);
	} else if (lastPosition == numberLen - 1) {
	    // "9999*******" "0000XXXX"
	    String maskString = maskPattern.substring(numberLen, numberLen + maskLength);

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
	int maskPatternLength = maskPattern.length();
	if (maskPatternLength < toMaskNumber.length()) {
	    return maskPattern;
	}

	char[] cArray = maskPattern.toCharArray();
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
		maskPattern = maskPattern.substring(0, toMaskNumber.length());
		return maskPattern;
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
		maskPattern = maskPattern.substring(0, toMaskNumber.length());
		return maskPattern;
	    }

	}

	return maskPattern;

    }// end

    public OTPAuthenticationOperation getOtpAuthenticationOperation() {
	return otpAuthenticationOperation;
    }

    public void setOtpAuthenticationOperation(OTPAuthenticationOperation otpAuthenticationOperation) {
	this.otpAuthenticationOperation = otpAuthenticationOperation;
    }

    public SPAuthenticationOperation getSpAuthenticationOperation() {
	return spAuthenticationOperation;
    }

    public void setSpAuthenticationOperation(SPAuthenticationOperation spAuthenticationOperation) {
	this.spAuthenticationOperation = spAuthenticationOperation;
    }

    public SQAAuthenticationOperation getSqaAuthenticationOperation() {
	return sqaAuthenticationOperation;
    }

    public void setSqaAuthenticationOperation(SQAAuthenticationOperation sqaAuthenticationOperation) {
	this.sqaAuthenticationOperation = sqaAuthenticationOperation;
    }

    public BMBJSONBuilder getOtpAuthenticationJSONBldr() {
	return otpAuthenticationJSONBldr;
    }

    public void setOtpAuthenticationJSONBldr(BMBJSONBuilder otpAuthenticationJSONBldr) {
	this.otpAuthenticationJSONBldr = otpAuthenticationJSONBldr;
    }

    public BMBJSONBuilder getSqaAuthenticationJSONBldr() {
	return sqaAuthenticationJSONBldr;
    }

    public void setSqaAuthenticationJSONBldr(BMBJSONBuilder sqaAuthenticationJSONBldr) {
	this.sqaAuthenticationJSONBldr = sqaAuthenticationJSONBldr;
    }

    public BMBJSONBuilder getInvalidLoginJSONBldr() {
	return invalidLoginJSONBldr;
    }

    public void setInvalidLoginJSONBldr(BMBJSONBuilder invalidLoginJSONBldr) {
	this.invalidLoginJSONBldr = invalidLoginJSONBldr;
    }

    @Override
    protected String getActivityId(Object command) {

	return ActivityConstant.LOGIN_ACTIVITY_ID;
    }

}
