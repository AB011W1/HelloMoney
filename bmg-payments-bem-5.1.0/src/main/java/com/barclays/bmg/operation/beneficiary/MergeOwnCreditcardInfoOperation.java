package com.barclays.bmg.operation.beneficiary;

import java.math.BigDecimal;
import java.util.List;

import com.barclays.bmg.audit.annotation.AuditSupport;
import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.BillerDTO;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.CreditCardTransactionHistoryListDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.operation.payments.BMBPaymentsOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.MergeOwnCreditcardInfoOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.MergeOwnCreditcardInfoOperationResponse;
import com.barclays.bmg.service.accountdetails.CreditCardDetailsService;
import com.barclays.bmg.service.accountdetails.request.CreditCardAccountActivityServiceRequest;
import com.barclays.bmg.service.accountdetails.response.CreditCardTransActivityServiceResponse;

public class MergeOwnCreditcardInfoOperation extends BMBPaymentsOperation {

	private CreditCardDetailsService creditCardDetailsService;
	public static final String BARCLAYCARD_BILLERTYPE = "BarclaycardBill";

	@AuditSupport(auditType = AuditConstant.AUDIT_TYPE_TRANSACTION, activityState = AuditConstant.SRC_COM_SSC, activityId = "PMT_PAYEE_LIST", serviceDescription = "SD_RETRIEVE_ORG_BENE_DETAILS", stepId = "2", activityType = "auditOwnCreditCardInfo")
	public MergeOwnCreditcardInfoOperationResponse mergeOwnCreditCardInfo(
			MergeOwnCreditcardInfoOperationRequest request) {
		Context context = request.getContext();
		loadParameters(context, ActivityConstant.COMMON_ID,
				ActivityConstant.SEC_COMMON_ID);
		MergeOwnCreditcardInfoOperationResponse response = new MergeOwnCreditcardInfoOperationResponse();
		response.setContext(context);
		CustomerAccountDTO customerAccountDTO = request.getCustomerAccountDTO();
		BeneficiaryDTO beneficiaryDTO = null;
		if (customerAccountDTO instanceof CreditCardAccountDTO) {
			CreditCardAccountDTO creditCardAccountDTO = (CreditCardAccountDTO) customerAccountDTO;
			if (getCreditCardDetails(request, creditCardAccountDTO)) {
				beneficiaryDTO = new BeneficiaryDTO();
				beneficiaryDTO.setDestinationAccount(creditCardAccountDTO);
				beneficiaryDTO.setCardNumber(creditCardAccountDTO.getPrimary()
						.getCardNumber());
				beneficiaryDTO.setBeneficiaryName(creditCardAccountDTO
						.getPrimary().getCardHolder());
				beneficiaryDTO.setDestinationBranchCode(creditCardAccountDTO
						.getBranchCode());
				beneficiaryDTO
						.setPayeeTypeCode(BillPaymentConstants.TXN_FACADE_RTN_TYPE_CREDIT_CARD);
				beneficiaryDTO.setCurrency(creditCardAccountDTO.getCurrency());
				beneficiaryDTO.setDestinationAccountNumber(creditCardAccountDTO
						.getAccountNumber());

				// Implementation to get GL Account No, Branch Code & Currency
				setBeneficiaryBranchInfo(request, beneficiaryDTO);

			}
		} else {
			response.setSuccess(false);
			// Not a Credit card account.
		}
		response.setBeneficiaryDTO(beneficiaryDTO);
		return response;
	}

	/**
	 * @param request
	 * @param toAcct
	 * @return Get Credit card payment details
	 */
	private boolean getCreditCardDetails(
			MergeOwnCreditcardInfoOperationRequest request,
			CreditCardAccountDTO toAcct) {

		boolean detailsRetreived = false;
		CreditCardAccountActivityServiceRequest serviceRequest = new CreditCardAccountActivityServiceRequest();
		serviceRequest.setContext(request.getContext());
		serviceRequest.setAccountNumber(toAcct.getAccountNumber());
		serviceRequest.setStatementTrxFlag(true);
		serviceRequest.setStatementDate(toAcct.getLastBilledDate());
		CreditCardTransActivityServiceResponse serviceResponse = creditCardDetailsService
				.retrieveCreditCardAccountActivity(serviceRequest);
		CreditCardTransactionHistoryListDTO creditCardTransactionHistoryListDTO = serviceResponse
				.getCreditCardTransactionHistoryListDTO();

		if (creditCardTransactionHistoryListDTO!=null && creditCardTransactionHistoryListDTO.getAccountInfo() != null) {
			toAcct.setMinPaymentAmount(creditCardTransactionHistoryListDTO
					.getAccountInfo().getMinPaymentDue());
			toAcct.setOutstandingBalance(creditCardTransactionHistoryListDTO
					.getAccountInfo().getPaymentDue());
			detailsRetreived = true;
		}else{
			toAcct.setMinPaymentAmount(new BigDecimal(0));
			toAcct.setOutstandingBalance(new BigDecimal(0));
			detailsRetreived = true;
		}

		return detailsRetreived;
	}

	public CreditCardDetailsService getCreditCardDetailsService() {
		return creditCardDetailsService;
	}

	public void setCreditCardDetailsService(
			CreditCardDetailsService creditCardDetailsService) {
		this.creditCardDetailsService = creditCardDetailsService;
	}

	// Implementation to get GL Account No, Branch Code & Currency
	private void setBeneficiaryBranchInfo(
			MergeOwnCreditcardInfoOperationRequest request,
			BeneficiaryDTO beneficiaryDTO) {

		List<BillerDTO> billerList = getAllBillerList(request);

		for (BillerDTO billerDTO : billerList) {

			if (billerDTO.getBillerCategoryId().equals(BARCLAYCARD_BILLERTYPE)) {
				beneficiaryDTO.setDestinationAccountNumber(billerDTO
						.getBillerAccountNumber());
				beneficiaryDTO.setDestinationBranchCode(billerDTO
						.getBranchCode());
				beneficiaryDTO.setCurrency(billerDTO.getCurrency());

				break;
			}
		}

	}
}
