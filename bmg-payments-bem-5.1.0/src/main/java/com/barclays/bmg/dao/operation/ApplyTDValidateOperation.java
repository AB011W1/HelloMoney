package com.barclays.bmg.dao.operation;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.barclays.bmg.audit.annotation.AuditSupport;
import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.constants.BillPaymentResponseCodeConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.operation.accountdetails.request.ApplyTDValidationOperationRequest;
import com.barclays.bmg.operation.accountdetails.response.ApplyTDValidationOperationResponse;
import com.barclays.bmg.operation.payments.BMBPaymentsOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.RetrieveAcctListOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrieveAcctListOperationResponse;

public class ApplyTDValidateOperation extends BMBPaymentsOperation {

	//private final static String SYS_PARAM_TD = "SYS_PARAM_TD";

	public ApplyTDValidationOperationResponse validateInputs(
			ApplyTDValidationOperationRequest request) {

		Context context = request.getContext();
		ApplyTDValidationOperationResponse response = new ApplyTDValidationOperationResponse();
		response.setSuccess(true);
		response.setContext(context);

		/*
		 * As for Hello MOney FD options are getting displayed on amt enetered & FD data (tenure etc) is coming from DB itself
		 * No Validation is required.
		 *
		 */

/*		String acctNo = request.getAcctNo();
		String depositAmount = request.getDepositAmount();
		String tenureTermMonths = request.getTenureMonths();
		String tenureTermDays = request.getTenureDays();

		Double termDepositAmount = Double.valueOf(depositAmount);
*/

		// if tenure period is less than 18 month
		// if tenure amount less than $500

		/*
		 * Check for TD Tenure based on Sys Pref
		 *

		Integer tenureMonths = Integer.valueOf(tenureTermMonths);
		Integer tenureDays = Integer.valueOf(tenureTermDays);
		Integer tenurePeriod = tenureMonths * 30 +tenureDays;

		String systemId = request.getContext().getSystemId();
		// String systemId=SystemIdConstants.UB; //!!!!!!!!!! mocked
		if (SystemIdConstants.UB.equals(systemId)) {
			ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
			listValueResServiceRequest.setContext(context);
			TransactionLimitServiceResponse txnLimitServiceResponse = new TransactionLimitServiceResponse();
			listValueResServiceRequest.setGroup(SYS_PARAM_TD);
			listValueResServiceRequest.getContext().setSystemId(
					SystemIdConstants.UB); // Mocked !!!!!!!!!!!!!!!!!
			ListValueResByGroupServiceResponse listResp = listValueResService
					.getListValueByGroup(listValueResServiceRequest);

			List<ListValueCacheDTO> listValuesDTOList = null;
			if (listResp != null) {
				listValuesDTOList = listResp.getListValueCahceDTO();
			}

			if (listValuesDTOList != null && !listValuesDTOList.isEmpty()) {

				// set the transaction limit on response based on system
				// preferences

				for (ListValueCacheDTO valueresDTO : listValuesDTOList) {

					if (valueresDTO.getKey() != null && SystemParameterConstant.MIN_DAYS_TD.equals(valueresDTO.getKey())) {
						txnLimitServiceResponse.setMinDaysTD(Integer.valueOf(valueresDTO.getLabel()));
					} else if (valueresDTO.getKey() != null && SystemParameterConstant.MAX_DAYS_TD .equals(valueresDTO.getKey())) {

						txnLimitServiceResponse.setMaxDaysTD(Integer
								.valueOf(valueresDTO.getLabel()));
					}
				}
			}

			if (tenurePeriod < txnLimitServiceResponse.getMinDaysTD() || tenurePeriod > txnLimitServiceResponse.getMaxDaysTD()) {
				response.setSuccess(false);
				response.setResCde(ApplyTDResponseCodeConstant.APPLY_TD_DAYS_LIMIT);
			}
		}
*/
/*		if (termDepositAmount < 500) {
			response.setSuccess(false);
			response.setResMsg("Term deposit is less than $500");
		}
*/
		return response;
	}

	public ApplyTDValidationOperationResponse getSourceAccountDetails(
			ApplyTDValidationOperationRequest request) {

		Context context = request.getContext();
		ApplyTDValidationOperationResponse response = new ApplyTDValidationOperationResponse();
		response.setSuccess(false);
		response.setResMsg("Account number ");
		loadParameters(context, ActivityConstant.COMMON_ID,
				ActivityConstant.SEC_COMMON_ID);
		response.setContext(context);
		String acctNo = request.getAcctNo();
		response.setResMsg("Account number " + acctNo + "  Does not exist in ");
		List<? extends CustomerAccountDTO> allAccounts = getAllAccounts(
				request, response);
		if (allAccounts != null && !allAccounts.isEmpty()) {
			for (CustomerAccountDTO sourceCustomerAccountDTO : allAccounts) {
				if (acctNo.equalsIgnoreCase(sourceCustomerAccountDTO.getAccountNumber())) {
					response.setSourceCustomerAccountDTO(sourceCustomerAccountDTO);
					response.setSuccess(true);
					response.setResMsg("");
					response.setDepositAmount(request.getDepositAmount());
					response.setTenureMonths(request.getTenureMonths());
					response.setTenureDays(request.getTenureDays());
					response.setAcctNo(acctNo);
					response.setProductId(request.getProductId());
					break;
				}
			}
		}

		return response;
	}

	/**
	 * Returns Source Account List as per PE for activity Id in context.
	 *
	 * @param request
	 * @return
	 */
	public RetrieveAcctListOperationResponse getSourceAcctList(
			RetrieveAcctListOperationRequest request) {
		Context context = request.getContext();
		RetrieveAcctListOperationResponse response = new RetrieveAcctListOperationResponse();
		loadParameters(context, ActivityConstant.COMMON_ID,
				ActivityConstant.SEC_COMMON_ID);
		response.setContext(context);

		List<? extends CustomerAccountDTO> allAccounts = getAllAccounts(
				request, response);

//		List<? extends CustomerAccountDTO> sourceAccts = getSourceAccountsList(
//				allAccounts, request);

		if (allAccounts != null && !allAccounts.isEmpty()) {
			response.setAcctList(allAccounts);
		} else {
			response.setSuccess(false);
			response
					.setResCde(BillPaymentResponseCodeConstants.NO_ACCOUNTS_FOR_SUMMARY);
		}

		if (!response.isSuccess()) {
			getMessage(response);
		}

		return response;
	}

	/**
	 * Returns Only CASA Account List as per PE for activity Id in context.
	 *
	 * @param request
	 * @return
	 */
	public RetrieveAcctListOperationResponse getCASASourceAccounts(
			RetrieveAcctListOperationRequest request) {
		List<CustomerAccountDTO> casaList = null;
		RetrieveAcctListOperationResponse response = getSourceAcctList(request);
		if (response.isSuccess()) {
			List<? extends CustomerAccountDTO> sourceAccts = response
					.getAcctList();
			casaList = new ArrayList<CustomerAccountDTO>();
			if (sourceAccts != null && !sourceAccts.isEmpty()) {
				for (CustomerAccountDTO sourceAcct : sourceAccts) {
					if (sourceAcct instanceof CASAAccountDTO) {
						casaList.add(sourceAcct);
					}
				}
			}
			if (casaList != null && !casaList.isEmpty()) {
				response.setAcctList(casaList);
			} else {
				response.setSuccess(false);
				response
						.setResCde(BillPaymentResponseCodeConstants.NO_ACCOUNTS_FOR_SUMMARY);
			}

			if (!response.isSuccess()) {
				getMessage(response);
			}

		}

		return response;
	}

	/**
	 * Returns Only CASA Account List as per PE for activity Id in context.
	 *
	 * @param request
	 * @return
	 */
	public RetrieveAcctListOperationResponse getCASASourceAccountsForLocalCurrency(
			RetrieveAcctListOperationRequest request) {
		List<CustomerAccountDTO> casaList = null;
		RetrieveAcctListOperationResponse response = getSourceAcctList(request);
		String localCurr = request.getContext().getLocalCurrency();
		if (response.isSuccess()) {
			List<? extends CustomerAccountDTO> sourceAccts = response
					.getAcctList();
			casaList = new ArrayList<CustomerAccountDTO>();
			if (sourceAccts != null && !sourceAccts.isEmpty()) {
				for (CustomerAccountDTO sourceAcct : sourceAccts) {
					if (sourceAcct instanceof CASAAccountDTO) {
						if (localCurr.equals(sourceAcct.getCurrency())) {
							casaList.add(sourceAcct);
						}
					}
				}
			}
			if (casaList != null && !casaList.isEmpty()) {
				response.setAcctList(casaList);
			} else {
				response.setSuccess(false);
				response
						.setResCde(BillPaymentResponseCodeConstants.NO_ACCOUNTS_FOR_SUMMARY);
			}
		}

		if (!response.isSuccess()) {
			getMessage(response);
		}
		return response;
	}

	/**
	 * Returns Source Account List as per PE for activity Id in context and
	 * filtered as per local currency.
	 *
	 * @param request
	 * @return
	 */
	public RetrieveAcctListOperationResponse getSourceAccountsForLocalCurrency(
			RetrieveAcctListOperationRequest request) {
		List<CustomerAccountDTO> currSrcActLst = null;
		String localCurr = request.getContext().getLocalCurrency();
		RetrieveAcctListOperationResponse response = getSourceAcctList(request);
		if (response.isSuccess() && !StringUtils.isEmpty(localCurr)) {
			List<? extends CustomerAccountDTO> sourceAccts = response
					.getAcctList();
			currSrcActLst = new ArrayList<CustomerAccountDTO>();

			if (sourceAccts != null && !sourceAccts.isEmpty()) {
				for (CustomerAccountDTO sourceAcct : sourceAccts) {
					if (localCurr.equals(sourceAcct.getCurrency())) {
						currSrcActLst.add(sourceAcct);
					}
				}
			}
			if (currSrcActLst != null && !currSrcActLst.isEmpty()) {
				response.setAcctList(currSrcActLst);
			} else {
				response.setSuccess(false);
				response
						.setResCde(BillPaymentResponseCodeConstants.NO_ACCOUNTS_FOR_SUMMARY);
			}

			if (!response.isSuccess()) {
				getMessage(response);
			}

		}

		return response;
	}

	/**
	 * Returns Destination Account List as per PE for activity Id in context
	 *
	 * @param request
	 * @return
	 */

	public RetrieveAcctListOperationResponse getDestAcctList(
			RetrieveAcctListOperationRequest request) {
		Context context = request.getContext();
		RetrieveAcctListOperationResponse response = new RetrieveAcctListOperationResponse();
		loadParameters(context, ActivityConstant.COMMON_ID,
				ActivityConstant.SEC_COMMON_ID);
		response.setContext(context);

		List<? extends CustomerAccountDTO> allAccounts = getAllAccounts(
				request, response);

		List<? extends CustomerAccountDTO> destAccts = getDestinationAccountsList(
				allAccounts, request);

		if (destAccts != null && !destAccts.isEmpty()) {
			response.setAcctList(destAccts);
		} else {
			response.setSuccess(false);
			response
					.setResCde(BillPaymentResponseCodeConstants.NO_ACCOUNTS_FOR_SUMMARY);
		}

		if (!response.isSuccess()) {
			getMessage(response);
		}

		return response;
	}

	/**
	 * Returns Only CASA Account List for Destination as per PE for activity Id
	 * in context.
	 *
	 * @param request
	 * @return
	 */
	@AuditSupport(auditType = AuditConstant.AUDIT_TYPE_TRANSACTION, activityState = AuditConstant.SRC_COM_SSC, activityId = "PMT_PAYEE_LIST", serviceDescription = "SD_RETRIEVE_IND_BENE_LIST", stepId = "1", activityType = "auditAcctList")
	public RetrieveAcctListOperationResponse getCASADestAccounts(
			RetrieveAcctListOperationRequest request) {
		List<CustomerAccountDTO> casaList = null;
		RetrieveAcctListOperationResponse response = getDestAcctList(request);
		if (response.isSuccess()) {
			List<? extends CustomerAccountDTO> destAccts = response
					.getAcctList();
			casaList = new ArrayList<CustomerAccountDTO>();
			if (destAccts != null && !destAccts.isEmpty()) {
				for (CustomerAccountDTO destAcct : destAccts) {
					if (destAcct instanceof CASAAccountDTO) {
						casaList.add(destAcct);
					}
				}
			}
			if (casaList != null && !casaList.isEmpty()) {
				response.setAcctList(casaList);
			} else {
				response.setSuccess(false);
				response
						.setResCde(BillPaymentResponseCodeConstants.NO_ACCOUNTS_FOR_SUMMARY);
			}

			if (!response.isSuccess()) {
				getMessage(response);
			}

		}

		return response;
	}

}
