package com.barclays.bmg.operation.beneficiary;

import java.util.List;
import java.util.Map;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.constants.BillPaymentResponseCodeConstants;
import com.barclays.bmg.constants.CommonConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.BillerDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.CustomerDTO;
import com.barclays.bmg.operation.payments.BMBPaymentsOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.MergeBarclayCardPayeeInfoOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.MergeBarclayCardPayeeInfoOperationResponse;
import com.barclays.bmg.service.RetreiveIndvCustInfoService;
import com.barclays.bmg.service.product.request.ProductEligibilityServiceRequest;
import com.barclays.bmg.service.request.RetreiveIndvCustInfoServiceRequest;
import com.barclays.bmg.service.response.RetreiveIndvCustInfoServiceResponse;

public class MergeBarclayCardPayeeInfoOperation extends BMBPaymentsOperation {
	RetreiveIndvCustInfoService retreiveIndvCustInfoService;
	private String currLstActId;
	private String currLstKey;

	// @AuditSupport(auditType = AuditConstant.AUDIT_TYPE_TRANSACTION,
	// activityState = AuditConstant.SRC_COM_SSC, activityId = "PMT_PAYEE_LIST",
	// serviceDescription = "SD_RETRIEVE_ORG_BENE_DETAILS", stepId = "2",
	// activityType="auditBCDPayeeInfo")
	public MergeBarclayCardPayeeInfoOperationResponse mergeBCDPayeeInfo(
			MergeBarclayCardPayeeInfoOperationRequest request) {
		Context context = request.getContext();
		loadParameters(context, ActivityConstant.COMMON_ID,
				ActivityConstant.SEC_COMMON_ID);
		MergeBarclayCardPayeeInfoOperationResponse response = new MergeBarclayCardPayeeInfoOperationResponse();
		response.setContext(context);
		BeneficiaryDTO beneficiaryDTO = request.getBeneficiaryDTO();

		if (beneficiaryDTO != null) {
			RetreiveIndvCustInfoServiceRequest retreiveIndvCustInfoServiceRequest = new RetreiveIndvCustInfoServiceRequest();
			retreiveIndvCustInfoServiceRequest.setContext(context);
			retreiveIndvCustInfoServiceRequest.setCreditCardNo(beneficiaryDTO
					.getCardNumber());
			RetreiveIndvCustInfoServiceResponse retreiveIndvCustInfoServiceResponse = retreiveIndvCustInfoService
					.retreiveIndvCustByCCNumber(retreiveIndvCustInfoServiceRequest);
			CustomerDTO custDTO = null;

			if (retreiveIndvCustInfoServiceResponse.isSuccess()) {

				custDTO = retreiveIndvCustInfoServiceResponse.getCustDTO();

				if (null != custDTO.getFullName()
						&& checkIfAcctEligible(custDTO
								.getCreditCardAccountDTO(), request, response)) {
					beneficiaryDTO.setBeneficiaryName(custDTO.getFullName());
					BillerDTO billerInfo = getGLBillerAccountInfo(request);
					if (billerInfo != null) { //In kenya, This biller info is not required in card payment
						beneficiaryDTO.setExternalBillerId(billerInfo
								.getExternalBillerId());
						beneficiaryDTO.setOnlineBillerFlag(billerInfo.getOnlineBillerFlag()); // 07072014
						beneficiaryDTO.setDestinationAccountNumber(billerInfo
								.getBillerAccountNumber());
						beneficiaryDTO.setDestinationBranchCode(billerInfo
								.getBranchCode());
						beneficiaryDTO.setCurrency(billerInfo.getCurrency());
					}
				} else {
					response.setSuccess(false);
					response
							.setResCde(BillPaymentResponseCodeConstants.BCD_INVALID_CARD_NUMBER);
				}
			} else {
				response.setSuccess(false);
				response
						.setResCde(BillPaymentResponseCodeConstants.BP_BCD_PRIME_06878);
			}

		} else {
			// Invalid.
		}
		if (!response.isSuccess()) {
			getMessage(response);
		} else {
			response.setBeneficiaryDTO(beneficiaryDTO);
			response.setCurrLst(retrieveCurrencyList(request));
		}
		return response;

	}

	/**
	 * Get curr list for barclay card.
	 *
	 * @param request
	 * @return
	 */
	public List<String> retrieveCurrencyList(RequestContext request) {
		// Context context = request.getContext();
		/*
		 * List<String> currLst = getSysParamListById(context,currLstKey,
		 * currLstActId);
		 */

		List<String> currLst = getFilteredCurrList(request, currLstKey,
				currLstActId);

		return currLst;
	}

	/**
	 * @param customerAcct
	 * @param request
	 * @param response
	 * @return
	 */
	protected boolean checkIfAcctEligible(CustomerAccountDTO customerAcct,
			RequestContext request, ResponseContext response) {

		ProductEligibilityServiceRequest destProductEligibilityServiceRequest = new ProductEligibilityServiceRequest();
		destProductEligibilityServiceRequest.setContext(request.getContext());
		destProductEligibilityServiceRequest.setActivityId(request.getContext()
				.getActivityId());
		destProductEligibilityServiceRequest
				.setProductIndicator(CommonConstants.CREDIT_PRODUCT);
		destProductEligibilityServiceRequest
				.setDrOrCr(CommonConstants.CREDIT_PRODUCT);

		return getProductEligibilityService().isAccountEligible(customerAcct,
				destProductEligibilityServiceRequest);
	}

	public RetreiveIndvCustInfoService getRetreiveIndvCustInfoService() {
		return retreiveIndvCustInfoService;
	}

	public void setRetreiveIndvCustInfoService(
			RetreiveIndvCustInfoService retreiveIndvCustInfoService) {
		this.retreiveIndvCustInfoService = retreiveIndvCustInfoService;
	}

	private BillerDTO getGLBillerAccountInfo(
			MergeBarclayCardPayeeInfoOperationRequest request) {

		BillerDTO billerinfo = null;
		List<BillerDTO> allBillerList = getAllBillerList(request);
		checkForMobileBiller(request, request.getBeneficiaryDTO());
		if (null != allBillerList && allBillerList.size() > 0) {
			for (int i = 0; i < allBillerList.size(); i++) {
				BillerDTO biller = allBillerList.get(i);
				if (null != biller.getBillerCategoryId()
						&& BillPaymentConstants.BARCLAYCARD_BILLERTYPE
								.equals(biller.getBillerCategoryId())) {
					billerinfo = biller;
					break;
				}
			}
		}

		return billerinfo;
	}

	/**
	 * Check if beneficiary is mobile provider.
	 *
	 * @param request
	 * @param beneficiaryDTO
	 */
	private void checkForMobileBiller(
			MergeBarclayCardPayeeInfoOperationRequest request,
			BeneficiaryDTO beneficiaryDTO) {
		Map<String, Object> sysMap = request.getContext().getContextMap();
		String mobileBillers = (String) sysMap.get("PMT_MOBILEBILLERS");
		if (mobileBillers != null) {
			String[] billerIds = mobileBillers.split(",");
			if (beneficiaryDTO.getBillerId() != null) {
				String billerId = beneficiaryDTO.getBillerId();
				for (int i = 0; i < billerIds.length; i++) {
					if (billerId.trim().equals(billerIds[i])) {
						beneficiaryDTO.setMobileProvider(true);
					}
				}
			}

		}
	}

	public String getCurrLstActId() {
		return currLstActId;
	}

	public void setCurrLstActId(String currLstActId) {
		this.currLstActId = currLstActId;
	}

	public String getCurrLstKey() {
		return currLstKey;
	}

	public void setCurrLstKey(String currLstKey) {
		this.currLstKey = currLstKey;
	}

}
