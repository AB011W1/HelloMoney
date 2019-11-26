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
package com.barclays.bmg.operation.accountdetails;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.CustomerDTO;
import com.barclays.bmg.dto.ListValueCacheDTO;
import com.barclays.bmg.operation.BMBCommonOperation;
import com.barclays.bmg.operation.request.RetrieveAllCustAcctOperationRequest;
import com.barclays.bmg.operation.response.RetrieveAllCustAcctOperationResponse;
import com.barclays.bmg.service.RetrieveAllCustAcctService;
import com.barclays.bmg.service.RetrieveIndCustBySCVIDService;
import com.barclays.bmg.service.accountdetails.impl.CASADetailsServiceImpl;
import com.barclays.bmg.service.request.RetrieveAllCustAcctServiceRequest;
import com.barclays.bmg.service.request.RetrieveIndCustBySCVIDServiceRequest;
import com.barclays.bmg.service.response.RetrieveAllCustAcctServiceResponse;
import com.barclays.bmg.service.response.RetrieveIndCustBySCVIDServiceResponse;

public class RetrieveAllCustAcctOperation extends BMBCommonOperation {

	private RetrieveIndCustBySCVIDService retrieveIndCustBySCVIDService;

	private CASADetailsServiceImpl casaDetailsService;

	public void setRetrieveIndCustBySCVIDService(RetrieveIndCustBySCVIDService retrieveIndCustBySCVIDService) {
		this.retrieveIndCustBySCVIDService = retrieveIndCustBySCVIDService;
	}

	/**
	 * The instance variable named "retrieveAllCustAcctService" is created.
	 */
	private RetrieveAllCustAcctService retrieveAllCustAcctService;

	/**
	 * The method is written for Retrieve all cust account.
	 * 
	 * @param request
	 *            the request
	 * @return the RetrieveAllCustAcctOperationResponse's object
	 */
	public RetrieveAllCustAcctOperationResponse retrieveAllCustAccount(RetrieveAllCustAcctOperationRequest request) {
		RetrieveAllCustAcctOperationResponse opResponse = new RetrieveAllCustAcctOperationResponse();

		Context context = request.getContext();
		loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID, context.getActivityId());

		RetrieveAllCustAcctServiceRequest serRequest = new RetrieveAllCustAcctServiceRequest();
		serRequest.setContext(request.getContext());
		RetrieveAllCustAcctServiceResponse serResponse = retrieveAllCustAcctService.retrieveAllCustAccount(serRequest);

		if (serResponse != null) {
			RetrieveIndCustBySCVIDServiceRequest retrieveIndCustBySCVIDServiceRequest = new RetrieveIndCustBySCVIDServiceRequest();
			retrieveIndCustBySCVIDServiceRequest.setCustomer(serResponse.getCustomer());
			retrieveIndCustBySCVIDServiceRequest.setContext(context);
			RetrieveIndCustBySCVIDServiceResponse retrieveIndCustBySCVIDServiceResponse = retrieveIndCustBySCVIDService
					.retrieveIndCustBySCVID(retrieveIndCustBySCVIDServiceRequest);

			if (retrieveIndCustBySCVIDServiceResponse != null) {
				serResponse.getContext().setPpMap(retrieveIndCustBySCVIDServiceResponse.getPpMap());
			}

			opResponse.setContext(serResponse.getContext());
			CustomerDTO customerDTO = serResponse.getCustomer();

			if (customerDTO != null) {
				customerDTO.setFirstName(retrieveIndCustBySCVIDServiceResponse.getFirstName());
				customerDTO.setLastName(retrieveIndCustBySCVIDServiceResponse.getLastName());
				customerDTO.setFullName(retrieveIndCustBySCVIDServiceResponse.getContext().getFullName());
			}

			boolean respSuccessFlg = serResponse.isSuccess();
			if (respSuccessFlg) {
				opResponse.setSuccess(respSuccessFlg);
				opResponse.setAccountList(serResponse.getAccountList());
				opResponse.setCustomer(customerDTO);
			} else {
				opResponse.setSuccess(false);
			}
			opResponse.setResCde(serResponse.getResCde());
			opResponse.setResMsg(serResponse.getResMsg());
		}
		return opResponse;
	}

	/**
	 * The method is written for Retrieve all customer information from MCE.
	 *
	 * @param request
	 *            the request
	 * @return the RetrieveAllCustAcctOperationResponse's object
	 */
	public RetrieveAllCustAcctOperationResponse retrieveCustInfo(RetrieveAllCustAcctOperationRequest request) {
		RetrieveAllCustAcctOperationResponse opResponse = new RetrieveAllCustAcctOperationResponse();

		Context context = null;
		if (null != request)
			context = request.getContext();
		loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID, context.getActivityId());

		// Get welcome message for Botswana. INC INC0063990
		List<ListValueCacheDTO> objListValueCacheDTO = new ArrayList<ListValueCacheDTO>();		
		objListValueCacheDTO = getListValueByGroup(context, ActivityConstant.BANNER_GROUP_ID);	

		RetrieveAllCustAcctServiceRequest serRequest = new RetrieveAllCustAcctServiceRequest();
		serRequest.setContext(request.getContext());

		RetrieveAllCustAcctServiceResponse serResponse = retrieveAllCustAcctService.retrieveAllCustAccount(serRequest);
		if (serResponse != null) {
			CustomerDTO customerDTO = serResponse.getCustomer();

			// Set welcome banner in customerDTO for INC INC0063990
			if (null != objListValueCacheDTO && !objListValueCacheDTO.isEmpty() && objListValueCacheDTO.size() > 0) {
				for (ListValueCacheDTO valueresDTO : objListValueCacheDTO) {

					if (valueresDTO.getKey() != null
							&& SystemParameterConstant.LOGIN_ANNOUNCE_FLAG.equals(valueresDTO.getKey())) {
						customerDTO.setBocBannerFlag(valueresDTO.getLabel());
					}
					else if(valueresDTO.getKey() != null
							&& SystemParameterConstant.LOGIN_ANNOUNCE_L1.equals(valueresDTO.getKey())) {
						customerDTO.setWelcomeBanner(valueresDTO.getLabel());
					}

				}
			}			
			

			boolean respSuccessFlg = serResponse.isSuccess();
			if (respSuccessFlg) {
				opResponse.setSuccess(respSuccessFlg);
				opResponse.setAccountList(serResponse.getAccountList());
				opResponse.setCustomer(customerDTO);
			} else {
				opResponse.setSuccess(false);
			}
			opResponse.setResCde(serResponse.getResCde());
			opResponse.setResMsg(serResponse.getResMsg());
		}

		return opResponse;
	}

	/**
	 * Gets the retrieve all cust acct service.
	 *
	 * @return the RetrieveAllCustAcctService
	 */
	public RetrieveAllCustAcctService getRetrieveAllCustAcctService() {
		return retrieveAllCustAcctService;
	}

	/**
	 * Sets values for RetrieveAllCustAcctService.
	 *
	 * @param retrieveAllCustAcctService
	 *            the retrieve all cust acct service
	 */
	public void setRetrieveAllCustAcctService(RetrieveAllCustAcctService retrieveAllCustAcctService) {
		this.retrieveAllCustAcctService = retrieveAllCustAcctService;
	}

	/**
	 * @return the casaDetailsService
	 */
	public CASADetailsServiceImpl getCasaDetailsService() {
		return casaDetailsService;
	}

	/**
	 * @param casaDetailsService
	 *            the casaDetailsService to set
	 */
	public void setCasaDetailsService(CASADetailsServiceImpl casaDetailsService) {
		this.casaDetailsService = casaDetailsService;
	}
}