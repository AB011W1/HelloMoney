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
 * Package name is com.barclays.bmg.mvc.controller.mobilewallet
 * /
 */
package com.barclays.bmg.mvc.controller.mobilewallet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractController;
import com.barclays.bmg.mvc.operation.lookup.BranchLookUpOperation;
import com.barclays.bmg.mvc.operation.mobilewallet.MWalletOperationService;
import com.barclays.bmg.operation.accountservices.RetrieveAccountListOperation;
import com.barclays.bmg.operation.request.MWalletOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.RetrieveAcctListOperationRequest;
import com.barclays.bmg.operation.request.lookup.BranchLookUpOperationRequest;
import com.barclays.bmg.operation.response.MWalletInItOperationResponse;
import com.barclays.bmg.operation.response.MWalletOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrieveAcctListOperationResponse;
import com.barclays.bmg.operation.response.lookup.BranchLookUpOperationResponse;

/**
 * ****************** Revision History **********************
 * </br>
 * Project Name  is  <b>bmg-payments-bem-5.1.0</b>
 * </br>
 * The file name is  <b>MWalletUpInItController.java</b>
 * </br>
 * Description  is  <b>V 1.2</b>
 * </br>
 * Updated Date  is  <b>May 17, 2013</b>
 * </br>
 * ******************************************************
 *
 * @author e20037686
 * </br>
 * * The Class MWalletUpInItController created using JRE 1.6.0_10
 */
public class MWalletUpInItController extends BMBAbstractController
{
	/**
	 * The instance variable named "activityId" is created.
	 */
	private String activityId;
	/**
	 * The instance variable named "billerCatId" is created.
	 */
	private String billerCatId;
	/**
	 * The instance variable named "walletOperationService" is created.
	 */
	private MWalletOperationService walletOperationService;
	//private RetreiveOrganizationBeneficiaryDetailsDAO beneficiaryDetailsDAO;
	//private RetrievePayeeListOperation retrievePayeeListOperation;
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

		/*CopyOfRetreiveBeneficiaryDetailsServiceRequest beneficiaryDetailsServiceRequest = new CopyOfRetreiveBeneficiaryDetailsServiceRequest();
		beneficiaryDetailsServiceRequest.setContext(context);*/
		//beneficiaryDetailsDAO.retreiveMWalletBeneficiaryDetails(beneficiaryDetailsServiceRequest);

		//AirTimeTopUpInItRequest airTimeTopUpInItRequest = new AirTimeTopUpInItRequest();

		MWalletInItOperationResponse operationResponse = new MWalletInItOperationResponse();

		//RetrievePayeeListOperationRequest retrievePayeeListOperationRequest = new RetrievePayeeListOperationRequest();
			//airTimeTopUpInItRequest.getPayeeListOperationRequest();

		//retrievePayeeListOperationRequest.setContext(context);

		//retrievePayeeListOperationRequest.setPayGrp(getPayGrp());

		RetrieveAcctListOperationRequest retrieveAcctListOperationRequest =	new RetrieveAcctListOperationRequest();

		retrieveAcctListOperationRequest.setContext(context);

		RetrieveAcctListOperationResponse retrieveAcctListOperationResponse = retrieveAccountListOperation
				.getSourceAcctList(retrieveAcctListOperationRequest);

		/*RetrievePayeeListOperationResponse retrievePayeeListOperationResponse =
				retrievePayeeListOperation.retrievePayeeList(retrievePayeeListOperationRequest);*/

		MWalletOperationRequest walletOperationRequest = new MWalletOperationRequest();

		walletOperationRequest.setContext(context);

		walletOperationRequest.setBillerCatId(getBillerCatId());

		// get the biller List
		MWalletOperationResponse mWalletOperationResponse = walletOperationService.getBillPaymentsBillerList(walletOperationRequest);

		//BranchLookUpOperationResponse branchLookUpOperationResponse= null;
		if(retrieveAcctListOperationResponse.isSuccess() && mWalletOperationResponse.isSuccess()){

			//branchLookUpOperationResponse = branchLookUpForAccount(retrieveAcctListOperationResponse, context);

			operationResponse.setAcctList(retrieveAcctListOperationResponse.getAcctList());

			operationResponse.setBillerList(mWalletOperationResponse.getBillerList());

			mapCorrelationIds(retrieveAcctListOperationResponse.getAcctList(), walletOperationRequest,
					httpRequest,operationResponse, BMGProcessConstants.BILL_PAYMENT);
			//BMGProcessConstants.AIR_TIME_TOP_UP);

			retrieveAcctListOperationResponse.setContext(context);
		}
		 //return (BMBBaseResponseModel) bmbJSONBuilder.createMultipleJSONResponse(retrievePayeeListOperationResponse,retrieveAcctListOperationResponse,airTimeTopUpOperationResponse);
		return (BMBBaseResponseModel) bmbJSONBuilder.createMultipleJSONResponse(retrieveAcctListOperationResponse,mWalletOperationResponse);
	}
	/**
	 * The method is written for Branch look up for account.
	 *
	 * @param retrieveSourceAccountOperationResponse the retrieve source account operation response
	 * @param context the context
	 * @return the BranchLookUpOperationResponse's object
	 */
	@SuppressWarnings("unused")
	private BranchLookUpOperationResponse branchLookUpForAccount(RetrieveAcctListOperationResponse retrieveSourceAccountOperationResponse, Context context){
		BranchLookUpOperationResponse branchLookUpOperationResponse = null;
		String branchCodeList = "";
		if (retrieveSourceAccountOperationResponse.getAcctList() != null
				&& retrieveSourceAccountOperationResponse.getAcctList()
						.size() > 0)
		{
			List<? extends CustomerAccountDTO> srcActLst = retrieveSourceAccountOperationResponse
					.getAcctList();

			for (int i = 0; i < srcActLst.size(); i++) {
				CASAAccountDTO account = (CASAAccountDTO) srcActLst.get(i);

				if (StringUtils.isNotEmpty(account.getBranchCode()))
				{
					if (i == 0) {
						branchCodeList += account.getBranchCode();
					} else {
						branchCodeList += "," + account.getBranchCode();
					}
				}
			}
		}
		if (StringUtils.isNotEmpty(branchCodeList)) {

			BranchLookUpOperationRequest branchLookUpOperationRequest = new BranchLookUpOperationRequest();
			branchLookUpOperationRequest.setContext(context);
			branchLookUpOperationRequest.setBranchCode(branchCodeList);

			branchLookUpOperationResponse = branchLookUpOperation
					.retrieveBranchName(branchLookUpOperationRequest);

			branchLookUpOperationResponse.setSuccess(true);
		}
		return branchLookUpOperationResponse;
	}
	/* (non-Javadoc)
	 * @see com.barclays.bmg.mvc.controller.auth.BMBAbstractController#getActivityId()
	 */
	@Override
	protected String getActivityId() {
		return activityId;
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
	 * Gets the wallet operation service.
	 *
	 * @return the WalletOperationService
	 */
	public MWalletOperationService getWalletOperationService() {
		return walletOperationService;
	}

	/**
	 * Sets values for WalletOperationService.
	 *
	 * @param walletOperationService the wallet operation service
	 */
	public void setWalletOperationService(
			MWalletOperationService walletOperationService) {
		this.walletOperationService = walletOperationService;
	}
}