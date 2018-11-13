package com.barclays.bmg.operation.beneficiary;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.constants.BillPaymentResponseCodeConstants;
import com.barclays.bmg.constants.CommonConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.CategorizedPayeeListDTO;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.ListValueCacheDTO;
import com.barclays.bmg.operation.payments.BMBPaymentsOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.GetOwnCreditCardAccountsOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.GetOwnCreditCardAccountsOperationResponse;
import com.barclays.bmg.service.product.ListValueResService;
import com.barclays.bmg.service.product.request.ListValueResServiceRequest;
import com.barclays.bmg.service.product.request.ProductEligibilityServiceRequest;
import com.barclays.bmg.service.product.response.ListValueResByGroupServiceResponse;

public class GetOwnCreditCardAccountsOperation extends BMBPaymentsOperation {

	private final static String PAYEE_TYPE_GROUP = "FT_FACADE_DEST";
	private ListValueResService listValueResService;

	public GetOwnCreditCardAccountsOperationResponse getOwnCreditCardAccounts(
			GetOwnCreditCardAccountsOperationRequest request) {
		Context context = request.getContext();
		GetOwnCreditCardAccountsOperationResponse response = new GetOwnCreditCardAccountsOperationResponse();
		response.setContext(context);

		List<CategorizedPayeeListDTO> categorizedPayeeList = request
				.getCategorizedPayeeList();
		if (categorizedPayeeList == null) {
			categorizedPayeeList = new ArrayList<CategorizedPayeeListDTO>();
		}

		List<? extends CustomerAccountDTO> allAccounts = getAllAccounts(
				request, response);
		if (allAccounts != null && !allAccounts.isEmpty()) {
			/*List<? extends CustomerAccountDTO> sourceAccts = getSourceAccountsList(
					allAccounts, request);*/
			List<? extends CustomerAccountDTO> sourceAccts = allAccounts;
			if (!sourceAccts.isEmpty()) {
				List<? extends CustomerAccountDTO> creditCardActLst = getOwnCreditCardAccountsList(
						allAccounts, context);
				List<ListValueCacheDTO> payeeTypeGroup = getPayeeTypeGroup(request);
				addCreditCardAccountsInList(creditCardActLst, payeeTypeGroup,
						categorizedPayeeList);
			}else {
				response.setSuccess(false);
				response
						.setResCde(BillPaymentResponseCodeConstants.NO_ACCOUNTS_FOR_SUMMARY);
			}
		}else {
			response.setSuccess(false);
			response
					.setResCde(BillPaymentResponseCodeConstants.NO_ACCOUNTS_FOR_SUMMARY);
		}

		if (categorizedPayeeList.isEmpty() && response.isSuccess()) {
			response.setSuccess(false);
			response
					.setResCde(BillPaymentResponseCodeConstants.NO_PAYEES_REGISTERED);
		}

		response.setCategorizedPayeeList(categorizedPayeeList);

		return response;
	}

	/**
	 * Get Credit Card account list.
	 *
	 * @param accountList
	 * @param fundTransferInitOprReq
	 * @return
	 */
	private List<? extends CustomerAccountDTO> getOwnCreditCardAccountsList(
			List<? extends CustomerAccountDTO> accountList, Context context) {
		ProductEligibilityServiceRequest destProductEligibilityServiceRequest = new ProductEligibilityServiceRequest();
		destProductEligibilityServiceRequest.setContext(context);
		destProductEligibilityServiceRequest
				.setActivityId(ActivityConstant.FUND_TRANSFER_CARD_PAYMENT_PAYEE_ACTIVITY_ID);
		destProductEligibilityServiceRequest
				.setProductIndicator(CommonConstants.CREDIT_PRODUCT);
		destProductEligibilityServiceRequest.setAccountList(accountList);
		destProductEligibilityServiceRequest
				.setDrOrCr(CommonConstants.CREDIT_PRODUCT);
		destProductEligibilityServiceRequest.getContext().setActivityId(
				ActivityConstant.FUND_TRANSFER_CARD_PAYMENT_PAYEE_ACTIVITY_ID);

		/*ProductEligibilityListServiceResponse sourceProductEligibilityListServiceResponse = getProductEligibilityService()
				.getEligibleAccounts(destProductEligibilityServiceRequest);

		List<? extends CustomerAccountDTO> eligibleAccounts = sourceProductEligibilityListServiceResponse
				.getAccountList();*/
		List<? extends CustomerAccountDTO> eligibleAccounts = accountList;
		eligibleAccounts = getCreditCardAccounts(eligibleAccounts);
		return eligibleAccounts;
	}

	private List<? extends CustomerAccountDTO> getCreditCardAccounts(
			List<? extends CustomerAccountDTO> accountList) {
		List<CustomerAccountDTO> creditList = null;
		if (accountList != null && !accountList.isEmpty()) {
			creditList = new ArrayList<CustomerAccountDTO>();
			for (CustomerAccountDTO custAcct : accountList) {
				if (custAcct instanceof CreditCardAccountDTO) {
					creditList.add(custAcct);
				}
			}
		}
		return creditList;
	}

	/**
	 * @param ccAccountList
	 * @param payeeTypeGroup
	 * @param categorizedPayeeList
	 */
	private void addCreditCardAccountsInList(
			List<? extends CustomerAccountDTO> ccAccountList,
			List<ListValueCacheDTO> payeeTypeGroup,
			List<CategorizedPayeeListDTO> categorizedPayeeList) {

		if (payeeTypeGroup != null) {
			for (ListValueCacheDTO payeeType : payeeTypeGroup) {
				String key = payeeType.getKey();
				if (BillPaymentConstants.PAYEE_TYPE_CREDIT_CARD_PAYMENT
						.equals(key)) {
					if (ccAccountList != null && !ccAccountList.isEmpty()) {
						CategorizedPayeeListDTO categorizedPayeeListDTO = new CategorizedPayeeListDTO();
						categorizedPayeeListDTO.setPayeeCategory(payeeType
								.getLabel());
						categorizedPayeeListDTO
								.setDestAccountList(ccAccountList);
						categorizedPayeeListDTO
								.setTransactionFacadeRoutineType(BillPaymentConstants.FUND_TRANSFER_CARD_PAYMENT);
						categorizedPayeeList.add(categorizedPayeeListDTO);
					}
				}
			}
		}
	}

	/**
	 * Get Payee type list eligible for particular country,
	 *
	 * @param retreivePayeeListOperationRequest
	 * @return
	 */
	private List<ListValueCacheDTO> getPayeeTypeGroup(RequestContext request) {
		ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
		listValueResServiceRequest.setContext(request.getContext());
		listValueResServiceRequest.setGroup(PAYEE_TYPE_GROUP);
		ListValueResByGroupServiceResponse listValueResByGroupServiceResponse = listValueResService
				.getListValueByGroup(listValueResServiceRequest);
		return listValueResByGroupServiceResponse.getListValueCahceDTO();
	}

	@Override
	public ListValueResService getListValueResService() {
		return listValueResService;
	}

	@Override
	public void setListValueResService(ListValueResService listValueResService) {
		this.listValueResService = listValueResService;
	}

}
