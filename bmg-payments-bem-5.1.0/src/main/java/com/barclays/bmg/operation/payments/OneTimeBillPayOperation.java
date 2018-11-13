package com.barclays.bmg.operation.payments;

import java.util.Map;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.BillerDTO;
import com.barclays.bmg.dto.TransactionDTO;
import com.barclays.bmg.operation.request.billpayment.OneTimeBillPayOperationRequest;
import com.barclays.bmg.operation.response.billpayment.OneTimeBillPayOperationResponse;
import com.barclays.bmg.service.request.BillerServiceRequest;
import com.barclays.bmg.service.response.BillerServiceResponse;

public class OneTimeBillPayOperation extends BMBPaymentsOperation {

    public OneTimeBillPayOperationResponse getBillerDetails(OneTimeBillPayOperationRequest request) {

	Context context = request.getContext();
	OneTimeBillPayOperationResponse response = new OneTimeBillPayOperationResponse();
	loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);

	BillerServiceRequest billerServiceRequest = new BillerServiceRequest();
	billerServiceRequest.setBillerId(request.getBillerId());
	billerServiceRequest.setBusinessId(context.getBusinessId());
	billerServiceRequest.setContext(context);
	billerServiceRequest.setBillerCategoryId(request.getBillerType());
	BillerServiceResponse billerServiceResponse = getBillerService().getBillerForBillerId(billerServiceRequest);

	if (null != billerServiceResponse && billerServiceResponse.getBillerDTO() != null) {
	    response.setBillerDTO(billerServiceResponse.getBillerDTO());
	    response.setSuccess(true);
	} else {
	    response.setSuccess(false);
	}
	response.setContext(context);
	return response;
    }

    public TransactionDTO mergeBillerForOneTimePay(BillerDTO billerDTO, OneTimeBillPayOperationRequest request) {

	TransactionDTO transactionDTO = new TransactionDTO();

	Map<String, Object> sysMap = request.getContext().getContextMap();

	BeneficiaryDTO beneficiaryDTO = new BeneficiaryDTO();
	if (billerDTO != null) {
	    beneficiaryDTO.setDestinationAccountNumber(billerDTO.getBillerAccountNumber());
	    beneficiaryDTO.setDestinationBranchCode(billerDTO.getBranchCode());
	    beneficiaryDTO.setCurrency(billerDTO.getCurrency());

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
	    beneficiaryDTO.setOnlineBillerFlag(billerDTO.getOnlineBillerFlag());// 03072014
	}

	String destBankCode = (String) sysMap.get(SystemParameterConstant.BUSINESS_BANK_CODE);
	beneficiaryDTO.setDestinationBankCode(destBankCode);

	transactionDTO.setBeneficiaryDTO(beneficiaryDTO);
	return transactionDTO;

    }

    @Override
    public String getAuthType(RequestContext request, String activityId) {
	return super.getAuthType(request, activityId);
    }

}
