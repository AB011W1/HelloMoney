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

import com.barclays.bmg.constants.BillPaymentResponseCodeConstants;
import com.barclays.bmg.operation.payments.BMBPaymentsOperation;
import com.barclays.bmg.operation.request.MWalletOperationRequest;
import com.barclays.bmg.operation.response.MWalletOperationResponse;
import com.barclays.bmg.service.request.BillerServiceRequest;
import com.barclays.bmg.service.response.BillerServiceResponse;

/**
 * ****************** Revision History **********************
 * </br>
 * Project Name  is  <b>bmg-payments-bem-5.1.0</b>
 * </br>
 * The file name is  <b>MWalletOperationService.java</b>
 * </br>
 * Description  is  <b>V 1.1</b>
 * </br>
 * Updated Date  is  <b>May 28, 2013</b>
 * </br>
 * ******************************************************
 *
 * @author e20037686
 * </br>
 * * The Class MWalletOperationService created using JRE 1.6.0_10
 */
public class MWalletOperationService  extends BMBPaymentsOperation {


	/**
	 * Gets the bill payments biller list.
	 *
	 * @param walletOperationRequest the wallet operation request
	 * @return the BillPaymentsBillerList
	 */

	public MWalletOperationResponse getBillPaymentsBillerList(
			MWalletOperationRequest walletOperationRequest) {

		MWalletOperationResponse walletOperationResponse = new MWalletOperationResponse();

		BillerServiceRequest billerServiceRequest = new BillerServiceRequest();

		billerServiceRequest.setContext(walletOperationRequest.getContext());

		billerServiceRequest.setBillerCategoryId(walletOperationRequest.getBillerCatId());

		BillerServiceResponse billerServiceResponse = super.getBillerService()
		.getBillPaymentsBillerList(billerServiceRequest);

		if(billerServiceResponse.isSuccess() && (billerServiceResponse.getBillerList() != null)){

			walletOperationResponse.setBillerList(billerServiceResponse.getBillerList());

		}else {

			walletOperationResponse.setSuccess(false);

			walletOperationResponse.setResCde(BillPaymentResponseCodeConstants.NO_BILLER_FOUND_FOR_USER);
		}
		if(!billerServiceResponse.isSuccess() && (billerServiceResponse.getBillerList() == null)){

			getMessage(walletOperationResponse);
		}

		return walletOperationResponse;
	}
}
