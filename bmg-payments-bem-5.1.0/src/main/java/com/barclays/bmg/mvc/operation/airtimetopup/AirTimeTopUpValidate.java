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

import java.util.List;

import com.barclays.bmg.constants.AirTimeTopUpResponseConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BillerDTO;
import com.barclays.bmg.operation.payments.BMBPaymentsOperation;
import com.barclays.bmg.operation.request.AirTimeTopUpOperationRequest;
import com.barclays.bmg.operation.request.AirTimeTopUpValidateRequest;
import com.barclays.bmg.operation.response.AirTimeTopUpOperationResponse;
import com.barclays.bmg.service.response.AirTimeTopUpValidateResponse;

/**
 * ****************** Revision History **********************
 * </br>
 * Project Name  is  <b>bmg-payments-bem-5.1.0</b>
 * </br>
 * The file name is  <b>AirTimeTopUpValidate.java</b>
 * </br>
 * Description  is  <b>Initial Version</b>
 * </br>
 * Updated Date  is  <b>May 17, 2013</b>
 * </br>
 * ******************************************************
 *
 * @author e20037686
 * </br>
 * * The Class AirTimeTopUpValidate created using JRE 1.6.0_10
 */
public class AirTimeTopUpValidate extends BMBPaymentsOperation{

	/**
	 * The instance variable named "airTimeTopUpOperationService" is created.
	 */
	private AirTimeTopUpOperationService airTimeTopUpOperationService;

	// minimum and maximum transaction required
	/**
	 * The method is written for Validate request.
	 *
	 * @param context the context
	 * @param airTimeTopUpValidateRequest the air time top up validate request
	 * @param responseContexts the response contexts
	 * @return the AirTimeTopUpValidateResponse's object
	 */
//	@AuditSupport(auditType = AuditConstant.AUDIT_TYPE_ACTIVITY, activityState = AuditConstant.SRC_COM_UB, serviceDescription = "SD_AIRTIME_TOPUP", stepId = "2", activityType = "auditAirtimeTopup")
	public AirTimeTopUpValidateResponse validateRequest(Context context, AirTimeTopUpValidateRequest airTimeTopUpValidateRequest,
			ResponseContext... responseContexts){
		AirTimeTopUpValidateResponse response = new AirTimeTopUpValidateResponse();

		AirTimeTopUpOperationRequest airTimeTopUpOperationRequest = new AirTimeTopUpOperationRequest();

		airTimeTopUpOperationRequest.setBillerCatId(airTimeTopUpValidateRequest.getBillerCatId());
		airTimeTopUpOperationRequest.setContext(airTimeTopUpValidateRequest.getContext());

		AirTimeTopUpOperationResponse airTimeTopUpOperationResponse = getAirTimeTopUpOperationService().getBillPaymentsBillerList(
				airTimeTopUpOperationRequest);

		if(airTimeTopUpOperationResponse.isSuccess() && airTimeTopUpOperationResponse.getBillerServiceResponse().getBillerList() != null){

			List<BillerDTO> billerList = airTimeTopUpOperationResponse.getBillerServiceResponse().getBillerList();
			if(billerList != null)
			{
				for (BillerDTO billerDTO : billerList)
				{
					if(billerDTO.getBillerId().equals(airTimeTopUpValidateRequest.getBillerId()))
					{
						response.setTxnAmt(airTimeTopUpValidateRequest.getAmt());
						response.setMblNo(airTimeTopUpValidateRequest.getMblNo());
						response.setBillerAcctNmbr(airTimeTopUpValidateRequest.getPrvdAcctNmbr());
						response.setBillerDTO(billerDTO);
						response.setContext(airTimeTopUpValidateRequest.getContext());
					}
		}
	}else
	{
				response.setSuccess(false);
				response.setResCde(AirTimeTopUpResponseConstant.ATP_MTP_BILLER_TYPE_EMPTY);
				response.setContext(airTimeTopUpValidateRequest.getContext());
				getMessage(airTimeTopUpOperationResponse);

			}
		}else{
				response.setSuccess(false);
				response.setResCde(AirTimeTopUpResponseConstant.ATP_MTP_BILLER_TYPE_EMPTY);
				response.setContext(airTimeTopUpValidateRequest.getContext());
				getMessage(airTimeTopUpOperationResponse);

			}

	return  response;
	}
	/**
	 * Gets the air time top up operation service.
	 *
	 * @return the AirTimeTopUpOperationService
	 */
	public AirTimeTopUpOperationService getAirTimeTopUpOperationService() {
		return airTimeTopUpOperationService;
	}
	/**
	 * Sets values for AirTimeTopUpOperationService.
	 *
	 * @param airTimeTopUpOperationService the air time top up operation service
	 */
	public void setAirTimeTopUpOperationService(
			AirTimeTopUpOperationService airTimeTopUpOperationService) {
		this.airTimeTopUpOperationService = airTimeTopUpOperationService;
	}
}