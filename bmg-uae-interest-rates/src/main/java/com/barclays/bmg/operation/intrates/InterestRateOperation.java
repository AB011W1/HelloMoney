package com.barclays.bmg.operation.intrates;

import java.util.List;

import com.barclays.bmg.audit.annotation.AuditSupport;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.IntrateDTO;
import com.barclays.bmg.operation.BMBCommonOperation;
import com.barclays.bmg.operation.request.intrates.InterestRatesOperationRequest;
import com.barclays.bmg.operation.response.intrates.InterestRatesOperationResponse;
import com.barclays.bmg.service.intrates.InterestRatesService;
import com.barclays.bmg.service.intrates.request.InterestRatesServiceRequest;
import com.barclays.bmg.service.intrates.response.InterestRatesServiceResponse;
import com.barclays.bmg.service.product.ProductEligibilityService;

public class InterestRateOperation extends BMBCommonOperation{

	private InterestRatesService intRatesService;

	private ProductEligibilityService productEligibilityService;

	public ProductEligibilityService getProductEligibilityService() {
		return productEligibilityService;
	}

	/**
	 * 1. Loads the system parameter into context 2. Retrieve the Interest Rates
	 * BEM request
	 */

	@AuditSupport(auditType = AuditConstant.AUDIT_TYPE_ACTIVITY, activityState = AuditConstant.SRC_COM_EXECUTOR, serviceDescription = "SD_RETRIEVE_INTRATES_LIST", stepId = "1", activityType = "auditInterestRate")
	public InterestRatesOperationResponse getIntratesList(
			InterestRatesOperationRequest request) {

		Context context = request.getContext();

		InterestRatesOperationResponse intratesOperationResponse = new InterestRatesOperationResponse();

		/*
		 * ---------- Get the Interest Rates Service Request to set Context and
		 * Category Code i.e. TMD -------------
		 */

		InterestRatesServiceRequest intratesServiceRequest = new InterestRatesServiceRequest();

		intratesServiceRequest.setContext(context);
		intratesServiceRequest.setCategoryCode(request.getCategoryCode());

		/* Check whether the requested country is FCR country or not
		 */
		
		if(!branchCodeCountryList.contains(context.getBusinessId()))
		{
			intratesServiceRequest.setFCRCountryFlag(true);
		}
		
		
		/*
		 * ---------- Get the Interest Rates Service Response to call Retrieve
		 * InterestRates List through a common service i.e. investment Account
		 * Detail Service -------------
		 */

		InterestRatesServiceResponse intratesServiceResponse = null;

		intratesServiceResponse = intRatesService
				.getIntratesList(intratesServiceRequest);

		List<IntrateDTO> intratesDTOList = intratesServiceResponse
				.getIntrateDTOList();

/*		Set<String> unqSet = new HashSet<String>();

		if (!intratesDTOList.isEmpty()) {
			for (int i = 0; i < intratesDTOList.size(); i++) {
				unqSet.add(intratesDTOList.get(i).getProductCode());
			}
		}

		Iterator it = unqSet.iterator();

		ProductEligibilityServiceRequest productEligibilityServiceRequest = new ProductEligibilityServiceRequest();
		productEligibilityServiceRequest.setContext(request.getContext());
		productEligibilityServiceRequest.setActivityId(request.getContext()
				.getActivityId());
		productEligibilityServiceRequest.setProductCategory(request
				.getCategoryCode());

		List<String> realProductList = new ArrayList<String>();

		while (it.hasNext()) {

			productEligibilityServiceRequest.setProductCode((it.next())
					.toString());

			if (productEligibilityService
					.isProductEligible(productEligibilityServiceRequest)) {
				realProductList.add(productEligibilityServiceRequest
						.getProductCode());
			}

		}

		List<IntrateDTO> finalProductList = new ArrayList<IntrateDTO>();

		if (!intratesDTOList.isEmpty()) {
			for (int k = 0; k < intratesDTOList.size(); k++) {

				for (int l = 0; l < realProductList.size(); l++) {

					if (((intratesDTOList.get(k).getProductCode()).toString())
							.equals(realProductList.get(l))) {
						finalProductList.add(intratesDTOList.get(k));
					}
				}
			}
		}
*/
		/*
		 * Set set = new HashSet(intratesDTOList); List<IntrateDTO>
		 * uniqueProductList = new ArrayList(set);
		 *
		 * List<IntrateDTO> realProductList = new ArrayList<IntrateDTO>();
		 *
		 * for(int j=0;j< uniqueProductList.size();j++){
		 *
		 * //List<IntrateDTO> selectedItem =
		 * intratesDTOList.get(j).getProductCode();
		 *
		 * ProductEligibilityServiceRequest productEligibilityServiceRequest =
		 * new ProductEligibilityServiceRequest();
		 * productEligibilityServiceRequest.setContext(request.getContext());
		 * productEligibilityServiceRequest
		 * .setActivityId(request.getContext().getActivityId());
		 * productEligibilityServiceRequest
		 * .setProductCode(intratesDTOList.get(j).getProductCode());
		 *
		 * if(productEligibilityService.isProductEligible(
		 * productEligibilityServiceRequest)){ realProductList.add((IntrateDTO)
		 * uniqueProductList.get(j)); }
		 *
		 * }
		 */

		//intratesDTOList = finalProductList;

		// List<? extends IntrateDTO> intDTOList =
		// getEligibleAccountList(intratesDTOList, request);

		intratesOperationResponse.setIntrateDTOList(intratesDTOList);

		return intratesOperationResponse;
	}

	public void setIntRatesService(InterestRatesService intRatesService) {
		this.intRatesService = intRatesService;
	}

	public void setProductEligibilityService(
			ProductEligibilityService productEligibilityService) {
		this.productEligibilityService = productEligibilityService;
	}

}
