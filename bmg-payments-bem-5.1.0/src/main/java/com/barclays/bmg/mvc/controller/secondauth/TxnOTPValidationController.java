package com.barclays.bmg.mvc.controller.secondauth;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.BeneficiaryConstants;
import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.constants.FundTransferConstants;
import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.auth.OTPCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.OTPAuthenticationOperation;
import com.barclays.bmg.operation.request.OTPAuthenticationOperationRequest;
import com.barclays.bmg.operation.response.OTPAuthenticationOperationResponse;

public class TxnOTPValidationController extends BMBAbstractCommandController {

	private String fundTransferURL;
	private String billPaymentURL;
	private String internationalURL;
	private String externalURL;
	private OTPAuthenticationOperation otpAuthenticationOperation;
	private BMBJSONBuilder bmbJSONBldr;
	private String internalNonRegisteredFundTransferURL;
	private String addBeneficiaryURL;

	@Override
	protected BMBBaseResponseModel handle1(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {

		OTPCommand otpCommand = (OTPCommand) command;

		OTPAuthenticationOperationRequest otpAuthenticationOperationRequest = makeRequest(request);

		otpAuthenticationOperationRequest.setOtp(otpCommand.getOtp());

		OTPAuthenticationOperationResponse otpAuthenticationOperationResponse = otpAuthenticationOperation
				.verify(otpAuthenticationOperationRequest);

		if (otpAuthenticationOperationResponse != null
				&& otpAuthenticationOperationResponse.isSuccess()) {
			String flowId = (String) getFromProcessMap(request,
					BMGProcessConstants.TXN_SECOND_LEVEL_AUTHENTICATION,
					BillPaymentConstants.SECOND_AUTH_FLOW_ID);
			setIntoProcessMap(request,
					BMGProcessConstants.TXN_SECOND_LEVEL_AUTHENTICATION,
					BillPaymentConstants.SECOND_AUTH_DONE,
					BillPaymentConstants.TRUE);

			if (FundTransferConstants.FT_FLOW_ID.equals(flowId)) {
				request.getRequestDispatcher(fundTransferURL).forward(request,
						response);
			} else if (BillPaymentConstants.BP_FLOW_ID.equals(flowId)) {
				request.getRequestDispatcher(billPaymentURL).forward(request,
						response);
			} else if (FundTransferConstants.INTL_FLOW_ID.equals(flowId)) {
				request.getRequestDispatcher(internationalURL).forward(request,
						response);
			} else if (FundTransferConstants.EXTERNAL_FLOW_ID.equals(flowId)) {
				request.getRequestDispatcher(externalURL).forward(request,
						response);
			}else if (FundTransferConstants.INTL_NON_REGISTERED_FT_FLOW_ID.equals(flowId)) {
				request.getRequestDispatcher(internalNonRegisteredFundTransferURL).forward(request,
						response);
			} else if (BeneficiaryConstants.AB_FLOW_ID.equals(flowId)) {
				request.getRequestDispatcher(addBeneficiaryURL).forward(request,
						response);
				}
		}

		return (BMBBaseResponseModel) bmbJSONBldr
				.createJSONResponse(otpAuthenticationOperationResponse);
	}

	/**
	 * make request for otp verification
	 *
	 * @param request
	 * @return
	 */
	private OTPAuthenticationOperationRequest makeRequest(
			HttpServletRequest request) {

		OTPAuthenticationOperationRequest otpAuthenticationOperationRequest = new OTPAuthenticationOperationRequest();

		Context context = createContext(request);

		otpAuthenticationOperationRequest.setContext(context);

		Map<String, Object> processMap = getProcessMapFromSession(request);

		otpAuthenticationOperationRequest.setChallengeId((String) processMap
				.get(SessionConstant.SESSION_CHALLENGE_ID));
		otpAuthenticationOperationRequest.setMobilePhone((String) processMap
				.get(SessionConstant.SESSION_MOBILE_PHONE));

		return otpAuthenticationOperationRequest;

	}

	public String getFundTransferURL() {
		return fundTransferURL;
	}

	public void setFundTransferURL(String fundTransferURL) {
		this.fundTransferURL = fundTransferURL;
	}

	public String getBillPaymentURL() {
		return billPaymentURL;
	}

	public void setBillPaymentURL(String billPaymentURL) {
		this.billPaymentURL = billPaymentURL;
	}

	public OTPAuthenticationOperation getOtpAuthenticationOperation() {
		return otpAuthenticationOperation;
	}

	public void setOtpAuthenticationOperation(
			OTPAuthenticationOperation otpAuthenticationOperation) {
		this.otpAuthenticationOperation = otpAuthenticationOperation;
	}

	public BMBJSONBuilder getBmbJSONBldr() {
		return bmbJSONBldr;
	}

	public void setBmbJSONBldr(BMBJSONBuilder bmbJSONBldr) {
		this.bmbJSONBldr = bmbJSONBldr;
	}

	public String getInternationalURL() {
		return internationalURL;
	}

	public void setInternationalURL(String internationalURL) {
		this.internationalURL = internationalURL;
	}

	public String getExternalURL() {
		return externalURL;
	}

	public void setExternalURL(String externalURL) {
		this.externalURL = externalURL;
	}

	@Override
	protected String getActivityId(Object command) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getInternalNonRegisteredFundTransferURL() {
		return internalNonRegisteredFundTransferURL;
	}

	public void setInternalNonRegisteredFundTransferURL(
			String internalNonRegisteredFundTransferURL) {
		this.internalNonRegisteredFundTransferURL = internalNonRegisteredFundTransferURL;
	}

	public void setAddBeneficiaryURL(String addBeneficiaryURL) {
		this.addBeneficiaryURL = addBeneficiaryURL;
	}

}
