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
package com.barclays.bmg.mvc.operation.airtimetopup;

import com.barclays.bmg.constants.BillPaymentResponseCodeConstants;
import com.barclays.bmg.operation.payments.BMBPaymentsOperation;
import com.barclays.bmg.operation.request.AirTimeTopUpOperationRequest;
import com.barclays.bmg.operation.response.AirTimeTopUpOperationResponse;
import com.barclays.bmg.service.request.BillerServiceRequest;
import com.barclays.bmg.service.response.BillerServiceResponse;
/**
 * ****************** Revision History **********************
 * </br>
 * Project Name  is  <b>bmg-payments-bem-5.1.0</b>
 * </br>
 * The file name is  <b>AirTimeTopUpOperationService.java</b>
 * </br>
 * Description  is  <b>Initial Version</b>
 * </br>
 * Updated Date  is  <b>May 17, 2013</b>
 * </br>
 * ******************************************************
 *
 * @author e20037686
 * </br>
 * * The Class AirTimeTopUpOperationService created using JRE 1.6.0_10
 */
public class AirTimeTopUpOperationService  extends BMBPaymentsOperation
{
		/**
		 * Gets the bill payments biller list.
		 *
		 * @param request the request
		 * @return the BillPaymentsBillerList
		 */
		public AirTimeTopUpOperationResponse getBillPaymentsBillerList(
				AirTimeTopUpOperationRequest request)
		{
			BillerServiceRequest billerServiceRequest = new BillerServiceRequest();
			billerServiceRequest.setContext(request.getContext());
			billerServiceRequest.setBillerCategoryId(request.getBillerCatId());
			//billerServiceRequest.setBillerName(request.getBillerId());
			AirTimeTopUpOperationResponse airTimeTopUpOperationResponse  = new AirTimeTopUpOperationResponse();

			BillerServiceResponse billerServiceResponse = super.getBillerService().getBillPaymentsBillerList(billerServiceRequest);

			if(billerServiceResponse.isSuccess() && (billerServiceResponse.getBillerList() != null))
			{
				airTimeTopUpOperationResponse.setBillerServiceResponse(billerServiceResponse);
			}
			else
			{
				airTimeTopUpOperationResponse.setSuccess(false);
				airTimeTopUpOperationResponse.setResCde(BillPaymentResponseCodeConstants.NO_BILLER_FOUND_FOR_USER);
			}
			if(!billerServiceResponse.isSuccess() && (billerServiceResponse.getBillerList() == null))
			{
				getMessage(airTimeTopUpOperationResponse);
			}
			return airTimeTopUpOperationResponse;
		}
}