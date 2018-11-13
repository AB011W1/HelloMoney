package com.barclays.bmg.operation.beneficiary;

import java.util.List;
import java.util.Map;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.BillerDTO;
import com.barclays.bmg.operation.payments.BMBPaymentsOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.MergeBillerInfoOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.MergeBillerInfoOperationResponse;

public class MergeBillerInfoOperation extends BMBPaymentsOperation {

    // @AuditSupport(auditType = AuditConstant.AUDIT_TYPE_TRANSACTION,
    // activityState = AuditConstant.SRC_COM_SSC, activityId = "PMT_PAYEE_LIST",
    // serviceDescription = "SD_RETRIEVE_ORG_BENE_DETAILS", stepId = "2",
    // activityType="auditPayeeBillerInfo")
    public MergeBillerInfoOperationResponse mergeBillerInformation(MergeBillerInfoOperationRequest request) {
	Context context = request.getContext();
	loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);
	MergeBillerInfoOperationResponse response = new MergeBillerInfoOperationResponse();
	response.setContext(context);

	Map<String, Object> sysMap = request.getContext().getContextMap();

	BeneficiaryDTO beneficiaryDTO = request.getBeneficiaryDTO();
	if (beneficiaryDTO != null) {
	    BillerDTO billerDTO = setBeneficiaryBranchInfo(request, beneficiaryDTO);

	    if (!BillPaymentConstants.FUND_TRANSFER_CARD_PAYMENT.equals(request.getPayeeType())) {
		if (billerDTO != null) {
		    beneficiaryDTO.setBillerName(billerDTO.getBillerName());
		    beneficiaryDTO.setBillerId(billerDTO.getBillerId());
		    beneficiaryDTO.setCurrency(billerDTO.getCurrency());
		    beneficiaryDTO.setPresentmentFlag(billerDTO.getPresentmentFlag());
		    beneficiaryDTO.setMobileDenominaiton(billerDTO.getMobileDenominaiton());
		    beneficiaryDTO.setRefNoKey1(billerDTO.getReferenceNoKey1());
		    if (billerDTO.getMobileDenominaiton() != null) {
			beneficiaryDTO.setMobileDenominaitonList(billerDTO.getMobileDenominaitonList());
		    }

		    beneficiaryDTO.setBillerCategoryId(billerDTO.getBillerCategoryId());
		    beneficiaryDTO.setBillerCategoryName(billerDTO.getBillerCategoryName());

		    if (beneficiaryDTO.getBillersMinPaymentAmount() == null) {
			beneficiaryDTO.setBillersMinPaymentAmount(billerDTO.getMinPaymentAmount());
		    }
		    if (beneficiaryDTO.getBillersMaxPaymentAmount() == null) {
			beneficiaryDTO.setBillersMaxPaymentAmount(billerDTO.getMaxPaymentAmount());
		    }
		    beneficiaryDTO.setExternalBillerId(billerDTO.getExternalBillerId());
		    beneficiaryDTO.setOnlineBillerFlag(billerDTO.getOnlineBillerFlag());// 07072014
		}
	    }

	    if (BillPaymentConstants.PAYEE_TYPE_BILL_PAYMENT.equals(request.getPayeeType())
		    || BillPaymentConstants.PAYEE_TYPE_MOBILE_TOPUP.equals(request.getPayeeType())) {

		String destBankCode = (String) sysMap.get(SystemParameterConstant.BUSINESS_BANK_CODE);
		request.getBeneficiaryDTO().setDestinationBankCode(destBankCode);
	    }

	    // Interval Amount
	    String intervalAmt = null;
	    if (BillPaymentConstants.PAYEE_TYPE_MOBILE_TOPUP.equals(request.getPayeeType())) {
		intervalAmt = (String) sysMap.get(BillPaymentConstants.PARAM_PMT_MOBILE_TOPUP_AMOUNT_INTERVAL);

		if (intervalAmt == null || "".equals(intervalAmt)) {
		    intervalAmt = BillPaymentConstants.DEFAULT_PMT_MOBILE_TOPUP_AMOUNT_INTERVAL;
		}
	    }

	    response.setBeneficiaryDTO(beneficiaryDTO);
	    response.setBillerDTO(billerDTO);
	    response.setIntervalAmt(intervalAmt);
	}
	return response;
    }

    /**
     * Set the beneficiary branch information using biller.
     * 
     * @param request
     * @param beneficiaryDTO
     */
    private BillerDTO setBeneficiaryBranchInfo(MergeBillerInfoOperationRequest request, BeneficiaryDTO beneficiaryDTO) {
	BillerDTO biller = null;
	if (BillPaymentConstants.PAYEE_TYPE_BILL_PAYMENT.equals(request.getPayeeType())
		|| BillPaymentConstants.PAYEE_TYPE_MOBILE_TOPUP.equals(request.getPayeeType())) {
	    List<BillerDTO> billerList = getAllBillerList(request);

	    for (BillerDTO billerDTO : billerList) {
		if (billerDTO.getBillerId().equals(beneficiaryDTO.getBillerId())) {
		    beneficiaryDTO.setDestinationAccountNumber(billerDTO.getBillerAccountNumber());
		    beneficiaryDTO.setDestinationBranchCode(billerDTO.getBranchCode());
		    beneficiaryDTO.setCurrency(billerDTO.getCurrency());
		    biller = billerDTO;
		    break;
		}
	    }
	}
	return biller;
    }

}
