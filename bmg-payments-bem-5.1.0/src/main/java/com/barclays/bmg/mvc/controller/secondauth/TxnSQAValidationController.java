package com.barclays.bmg.mvc.controller.secondauth;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.constants.FundTransferConstants;
import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.auth.SQACommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.SQAAuthenticationOperation;
import com.barclays.bmg.operation.request.SQAAuthenticationOperationRequest;
import com.barclays.bmg.operation.response.SQAAuthenticationOperationResponse;



public class TxnSQAValidationController extends BMBAbstractCommandController{
	private String fundTransferURL;
	private String billPaymentURL;
	private String internationalURL;
	private String externalURL;
	SQAAuthenticationOperation sqaAuthenticationOperation;
	BMBJSONBuilder bmbJSONBldr;

	@Override
	protected BMBBaseResponseModel handle1(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {


		//SQA verification

		SQACommand sqaCommand = (SQACommand)command;

		SQAAuthenticationOperationRequest sqaAuthenticationOperationRequest =
					makeRequest(request);

		sqaAuthenticationOperationRequest.setSqa(sqaCommand.getSqa());

		SQAAuthenticationOperationResponse sqaAuthenticationOperationResponse =
							sqaAuthenticationOperation.verify(sqaAuthenticationOperationRequest);

		if(sqaAuthenticationOperationResponse!=null && sqaAuthenticationOperationResponse.isSuccess()){

			String flowId = (String)getFromProcessMap(request, BMGProcessConstants.TXN_SECOND_LEVEL_AUTHENTICATION, BillPaymentConstants.SECOND_AUTH_FLOW_ID);
			setIntoProcessMap(request, BMGProcessConstants.TXN_SECOND_LEVEL_AUTHENTICATION, BillPaymentConstants.SECOND_AUTH_DONE, BillPaymentConstants.TRUE);

		/*	Map<String,Object> processMap = getProcessMapFromSession(request);
			String flowId = (String)processMap.get(BillPaymentConstants.SECOND_AUTH_FLOW_ID);
			setProcessMapIntoSession(request,BillPaymentConstants.SECOND_AUTH_DONE,BillPaymentConstants.TRUE);*/
			if(FundTransferConstants.FT_FLOW_ID.equals(flowId)){
				request.getRequestDispatcher(fundTransferURL).forward(
						request, response);
			}else if(BillPaymentConstants.BP_FLOW_ID.equals(flowId)){
				request.getRequestDispatcher(billPaymentURL).forward(
						request, response);
			}else if(FundTransferConstants.INTL_FLOW_ID.equals(flowId)){
				request.getRequestDispatcher(internationalURL).forward(
						request, response);
			}else if(FundTransferConstants.EXTERNAL_FLOW_ID.equals(flowId)){
				request.getRequestDispatcher(externalURL).forward(
						request, response);
			}
		}


		return (BMBBaseResponseModel)bmbJSONBldr.createJSONResponse(sqaAuthenticationOperationResponse);
	}


	/**
	 * make request for sqa verification
	 * @param request
	 * @return
	 */
	private SQAAuthenticationOperationRequest makeRequest(HttpServletRequest request){

		SQAAuthenticationOperationRequest sqaAuthenticationOperationRequest =
					new SQAAuthenticationOperationRequest();

		Context context =createContext(request);

		sqaAuthenticationOperationRequest.setContext(context);

		Map<String, Object> processMap = getProcessMapFromSession(request);


		sqaAuthenticationOperationRequest.setQuestionId((String)processMap.get(SessionConstant.SESSION_QUESTION_ID));

		return sqaAuthenticationOperationRequest;

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


	public SQAAuthenticationOperation getSqaAuthenticationOperation() {
		return sqaAuthenticationOperation;
	}


	public void setSqaAuthenticationOperation(
			SQAAuthenticationOperation sqaAuthenticationOperation) {
		this.sqaAuthenticationOperation = sqaAuthenticationOperation;
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

}
