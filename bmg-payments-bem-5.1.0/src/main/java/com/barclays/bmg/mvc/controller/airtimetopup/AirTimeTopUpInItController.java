/*
 * Copyright (c) 2013 Barclays Bank Plc, All Rights Reserved.
 *
 * This code is confidential to Barclays Bank Plc and shall not be disclosed
 * outside the Bank without the prior written permission of the Director of
 * CIO
 *
 * In the event that such disclosure is permitted the code shall not be copied
 * or distributed other than on a need-to-know basis and any recipients may be
 * required to sign a confidentiality undertaking in favour of Barclays Bank
 * Plc.
 */
/**
 * Package name is com.barclays.bmg.mvc.controller.airtimetopup
 * /
 */
package com.barclays.bmg.mvc.controller.airtimetopup;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractController;
import com.barclays.bmg.mvc.operation.airtimetopup.AirTimeTopUpInitOperation;
import com.barclays.bmg.mvc.operation.lookup.BranchLookUpOperation;
import com.barclays.bmg.operation.accountservices.RetrieveAccountListOperation;
import com.barclays.bmg.operation.beneficiary.RetrievePayeeListOperation;
import com.barclays.bmg.operation.request.AirTimeTopUpInItRequest;
import com.barclays.bmg.operation.request.AirTimeTopUpOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.RetrieveAcctListOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.RetrievePayeeListOperationRequest;
import com.barclays.bmg.operation.response.AirTimeTopUpInItOperationResponse;
import com.barclays.bmg.operation.response.AirTimeTopUpOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrieveAcctListOperationResponse;
import com.barclays.bmg.operation.response.lookup.BranchLookUpOperationResponse;
/**
 * ****************** Revision History **********************
 * </br>
 * Project Name  is  <b>bmg-payments-bem-5.1.0</b>
 * </br>
 * The file name is  <b>AirTimeTopUpInItController.java</b>
 * </br>
 * Description  is  <b>Initial Version</b>
 * </br>
 * Updated Date  is  <b>May 17, 2013</b>
 * </br>
 * ******************************************************
 *
 * @author e20037686
 * </br>
 * * The Class AirTimeTopUpInItController created using JRE 1.6.0_10
 */
public class AirTimeTopUpInItController extends BMBAbstractController{

	/**
	 * The instance variable named "activityId" is created.
	 */
	private String activityId;

	/**
	 * The instance variable named "payGrp" is created.
	 */
	private String payGrp;

	/**
	 * The instance variable named "billerCatId" is created.
	 */
	private String billerCatId;

	/**
	 * The instance variable named "airTimeTopUpInitOperation" is created.
	 */
	private AirTimeTopUpInitOperation airTimeTopUpInitOperation;

	/**
	 * The instance variable named "retrievePayeeListOperation" is created.
	 */
	private RetrievePayeeListOperation retrievePayeeListOperation;

	/**
	 * The instance variable named "retrieveAccountListOperation" is created.
	 */
	private RetrieveAccountListOperation retrieveAccountListOperation;

	/**
	 * The instance variable named "branchLookUpOperation" is created.
	 */
	private BranchLookUpOperation branchLookUpOperation;

	/**
	 * The instance variable named "bmbJSONBuilder" is created.
	 */
	private BMBMultipleResponseJSONBuilder bmbJSONBuilder;

	/* (non-Javadoc)
	 * @see com.barclays.bmg.mvc.controller.auth.BMBAbstractController#handleRequestInternal1(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected BMBBaseResponseModel handleRequestInternal1(
			HttpServletRequest httpRequest, HttpServletResponse httpResponse)
			throws Exception {

		setFirstStep(httpRequest);

		Context context = createContext(httpRequest);

		context.setActivityId(getActivityId());

		AirTimeTopUpInItRequest airTimeTopUpInItRequest = new AirTimeTopUpInItRequest();

		AirTimeTopUpInItOperationResponse operationResponse = new AirTimeTopUpInItOperationResponse();

		RetrievePayeeListOperationRequest retrievePayeeListOperationRequest = new RetrievePayeeListOperationRequest();
			//airTimeTopUpInItRequest.getPayeeListOperationRequest();

		retrievePayeeListOperationRequest.setContext(context);

		retrievePayeeListOperationRequest.setPayGrp(getPayGrp());

		airTimeTopUpInItRequest.setContext(context);
		airTimeTopUpInItRequest.setPayeeListOperationRequest(retrievePayeeListOperationRequest);

		RetrieveAcctListOperationRequest retrieveAcctListOperationRequest =
			new RetrieveAcctListOperationRequest();

		retrieveAcctListOperationRequest.setContext(context);

		RetrieveAcctListOperationResponse retrieveAcctListOperationResponse = retrieveAccountListOperation
				.getSourceAcctList(retrieveAcctListOperationRequest);

		/*RetrievePayeeListOperationResponse retrievePayeeListOperationResponse =
				retrievePayeeListOperation.retrievePayeeList(retrievePayeeListOperationRequest);*/
		AirTimeTopUpOperationRequest airTimeTopUpOperationRequest = new AirTimeTopUpOperationRequest();
		airTimeTopUpOperationRequest.setContext(context);
		airTimeTopUpOperationRequest.setBillerCatId(getBillerCatId());

		// get the biller List
		AirTimeTopUpOperationResponse airTimeTopUpOperationResponse = airTimeTopUpInitOperation
				.getBillPaymentsBillerList(airTimeTopUpOperationRequest);
		BranchLookUpOperationResponse branchLookUpOperationResponse= null;
		if(retrieveAcctListOperationResponse.isSuccess() && airTimeTopUpOperationResponse.isSuccess())
		{
			operationResponse.setAcctListOperationResponse(retrieveAcctListOperationResponse);

			operationResponse.setAirTimeTopUpOperationResponse(airTimeTopUpOperationResponse);

			mapCorrelationIds(retrieveAcctListOperationResponse.getAcctList(), retrievePayeeListOperationRequest,
					httpRequest,operationResponse, BMGProcessConstants.BILL_PAYMENT);

			retrieveAcctListOperationResponse.setContext(retrievePayeeListOperationRequest.getContext());
		}
		return (BMBBaseResponseModel) bmbJSONBuilder.createMultipleJSONResponse(retrieveAcctListOperationResponse,branchLookUpOperationResponse,airTimeTopUpOperationResponse);
	}

	/* (non-Javadoc)
	 * @see com.barclays.bmg.mvc.controller.auth.BMBAbstractController#getActivityId()
	 */
	@Override
	protected String getActivityId() {
		return activityId;
	}

	/**
	 * Gets the retrieve payee list operation.
	 *
	 * @return the RetrievePayeeListOperation
	 */
	public RetrievePayeeListOperation getRetrievePayeeListOperation() {
		return retrievePayeeListOperation;
	}

	/**
	 * Sets values for RetrievePayeeListOperation.
	 *
	 * @param retrievePayeeListOperation the retrieve payee list operation
	 */
	public void setRetrievePayeeListOperation(
			RetrievePayeeListOperation retrievePayeeListOperation) {
		this.retrievePayeeListOperation = retrievePayeeListOperation;
	}

	/**
	 * Gets the retrieve account list operation.
	 *
	 * @return the RetrieveAccountListOperation
	 */
	public RetrieveAccountListOperation getRetrieveAccountListOperation() {
		return retrieveAccountListOperation;
	}

	/**
	 * Sets values for RetrieveAccountListOperation.
	 *
	 * @param retrieveAccountListOperation the retrieve account list operation
	 */
	public void setRetrieveAccountListOperation(
			RetrieveAccountListOperation retrieveAccountListOperation) {
		this.retrieveAccountListOperation = retrieveAccountListOperation;
	}

	/**
	 * Gets the bmb json builder.
	 *
	 * @return the BmbJSONBuilder
	 */
	public BMBMultipleResponseJSONBuilder getBmbJSONBuilder() {
		return bmbJSONBuilder;
	}

	/**
	 * Sets values for BmbJSONBuilder.
	 *
	 * @param bmbJSONBuilder the bmb json builder
	 */
	public void setBmbJSONBuilder(BMBMultipleResponseJSONBuilder bmbJSONBuilder) {
		this.bmbJSONBuilder = bmbJSONBuilder;
	}

	/**
	 * Sets values for ActivityId.
	 *
	 * @param activityId the activity id
	 */
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	/**
	 * Gets the pay grp.
	 *
	 * @return the PayGrp
	 */
	public String getPayGrp() {
		return payGrp;
	}

	/**
	 * Sets values for PayGrp.
	 *
	 * @param payGrp the pay grp
	 */
	public void setPayGrp(String payGrp) {
		this.payGrp = payGrp;
	}

	/**
	 * Gets the biller cat id.
	 *
	 * @return the BillerCatId
	 */
	public String getBillerCatId() {
		return billerCatId;
	}

	/**
	 * Sets values for BillerCatId.
	 *
	 * @param billerCatId the biller cat id
	 */
	public void setBillerCatId(String billerCatId) {
		this.billerCatId = billerCatId;
	}

	/**
	 * Gets the branch look up operation.
	 *
	 * @return the BranchLookUpOperation
	 */
	public BranchLookUpOperation getBranchLookUpOperation() {
		return branchLookUpOperation;
	}

	/**
	 * Sets values for BranchLookUpOperation.
	 *
	 * @param branchLookUpOperation the branch look up operation
	 */
	public void setBranchLookUpOperation(BranchLookUpOperation branchLookUpOperation) {
		this.branchLookUpOperation = branchLookUpOperation;
	}

	/**
	 * Gets the air time top up init operation.
	 *
	 * @return the AirTimeTopUpInitOperation
	 */
	public AirTimeTopUpInitOperation getAirTimeTopUpInitOperation() {
		return airTimeTopUpInitOperation;
	}

	/**
	 * Sets values for AirTimeTopUpInitOperation.
	 *
	 * @param airTimeTopUpInitOperation the air time top up init operation
	 */
	public void setAirTimeTopUpInitOperation(
			AirTimeTopUpInitOperation airTimeTopUpInitOperation) {
		this.airTimeTopUpInitOperation = airTimeTopUpInitOperation;
	}
}
