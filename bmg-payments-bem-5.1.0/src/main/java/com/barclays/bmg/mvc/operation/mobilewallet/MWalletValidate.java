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
 * Package name is com.barclays.bmg.mvc.operation.mobilewallet
 * /
 */
package com.barclays.bmg.mvc.operation.mobilewallet;

import java.util.List;

import com.barclays.bmg.constants.MWalletResponseConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BillerDTO;
import com.barclays.bmg.operation.payments.BMBPaymentsOperation;
import com.barclays.bmg.operation.request.MWalletOperationRequest;
import com.barclays.bmg.operation.request.MWalletValidateRequest;
import com.barclays.bmg.operation.response.MWalletOperationResponse;
import com.barclays.bmg.service.response.MWalletValidateResponse;

/**
 * ****************** Revision History **********************
 * </br>
 * Project Name  is  <b>bmg-payments-bem-5.1.0</b>
 * </br>
 * The file name is  <b>MWalletValidate.java</b>
 * </br>
 * Description  is  <b>V 1.1</b>
 * </br>
 * Updated Date  is  <b>May 28, 2013</b>
 * </br>
 * ******************************************************
 *
 * @author e20037686
 * </br>
 * * The Class MWalletValidate created using JRE 1.6.0_10
 */
public class MWalletValidate extends BMBPaymentsOperation {

	/**
	 * The instance variable named "mWalletOperationService" is created.
	 */
	private MWalletOperationService mWalletOperationService;

	// minimum and maximum transaction required

	/**
	 * The method is written for Validate request.
	 *
	 * @param context the context
	 * @param mWalletValidateRequest the m wallet validate request
	 * @param responseContexts the response contexts
	 * @return the MWalletValidateResponse's object
	 */
	public MWalletValidateResponse validateRequest(Context context,
			MWalletValidateRequest mWalletValidateRequest,
			ResponseContext... responseContexts) {

		MWalletValidateResponse response = new MWalletValidateResponse();

		MWalletOperationRequest mWalletOperationRequest = new MWalletOperationRequest();

		mWalletOperationRequest.setBillerCatId(mWalletValidateRequest
				.getBillerCatId());

		mWalletOperationRequest.setContext(mWalletValidateRequest.getContext());

		MWalletOperationResponse mWalletOperationResponse = mWalletOperationService
				.getBillPaymentsBillerList(mWalletOperationRequest);

		if (mWalletOperationResponse.isSuccess()
				&& mWalletOperationResponse.getBillerList() != null) {

			List<BillerDTO> billerList = mWalletOperationResponse
					.getBillerList();

			if (billerList != null) {
				for (BillerDTO billerDTO : billerList) {
					if (billerDTO.getBillerId().equals(
							mWalletValidateRequest.getBillerId())) {
						response.setTxnAmt(mWalletValidateRequest.getAmt());
						response.setMblNo(mWalletValidateRequest.getMblNo());
						response.setBillerDTO(billerDTO);
						response
								.setContext(mWalletValidateRequest.getContext());
					}
				}
			} else {
				response.setSuccess(false);

				response
						.setResCde(MWalletResponseConstant.MOBILE_WALLET_MTP_BILLER_TYPE_EMPTY);

				response.setContext(mWalletValidateRequest.getContext());

				getMessage(mWalletOperationResponse);

			}
		}
		return response;
	}

	/**
	 * @return the mWalletOperationService
	 */
	public MWalletOperationService getMWalletOperationService() {
		return mWalletOperationService;
	}

	/**
	 *
	 */
	public void setMWalletOperationService(
			MWalletOperationService walletOperationService) {
		mWalletOperationService = walletOperationService;
	}
}