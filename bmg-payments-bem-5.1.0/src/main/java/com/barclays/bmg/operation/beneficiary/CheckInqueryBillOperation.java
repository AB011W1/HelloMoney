package com.barclays.bmg.operation.beneficiary;

import java.math.BigDecimal;
import java.util.List;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.constants.BillPaymentResponseCodeConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.BillerDTO;
import com.barclays.bmg.operation.payments.BMBPaymentsOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.CheckInqueryBillOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.CheckInqueryBillOperationResponse;
import com.barclays.bmg.service.InqueryBillService;
import com.barclays.bmg.service.request.InqueryBillServiceRequest;
import com.barclays.bmg.service.response.InqueryBillServiceResponse;

public class CheckInqueryBillOperation extends BMBPaymentsOperation{

	private InqueryBillService inqueryBillService;

	public CheckInqueryBillOperationResponse checkAccountBill(CheckInqueryBillOperationRequest request){
		Context context = request.getContext();
		loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID );
		CheckInqueryBillOperationResponse response = new CheckInqueryBillOperationResponse();
		response.setContext(context);
		BeneficiaryDTO beneficiaryDTO = request.getBeneficiaryDTO();
		 boolean billStatus = false;
		 InqueryBillServiceRequest inqueryBillServiceRequest = new InqueryBillServiceRequest();
		 inqueryBillServiceRequest.setContext(request.getContext());

		 setServiceType(request,beneficiaryDTO);
		 inqueryBillServiceRequest.setBillRefNo1(beneficiaryDTO.getBillRefNo1());
		 inqueryBillServiceRequest.setBillRefNo2(beneficiaryDTO.getBillRefNo2());
		 inqueryBillServiceRequest.setTopUpService(beneficiaryDTO.getTopupService());
		 inqueryBillServiceRequest.setBillerId(beneficiaryDTO.getBillerId());
		 inqueryBillServiceRequest.setMobileProvider(beneficiaryDTO.isMobileProvider());
		 InqueryBillServiceResponse inqueryBillServiceResponse = inqueryBillService.inqueryBill(inqueryBillServiceRequest);

			 if(BillPaymentConstants.PAYEE_TYPE_BILL_PAYMENT.equals(request.getTxnType())){
				 BigDecimal outBalAmt = inqueryBillServiceResponse.getOutstandingBalanceAmount();
				 billStatus =
                     (null == outBalAmt || new BigDecimal(0).equals(outBalAmt));
			 }else if(BillPaymentConstants.PAYEE_TYPE_MOBILE_TOPUP.equals(request.getTxnType())){
				 billStatus =
                     null == inqueryBillServiceResponse.getMinimumBillAmount()
                             || null == inqueryBillServiceResponse.getMaximumBillAmount();
			 }

		 response.setOutBalAmt(inqueryBillServiceResponse.getOutstandingBalanceAmount());
		 response.setMinBillAmt(inqueryBillServiceResponse.getMinimumBillAmount());
		 response.setMaxBilAmt(inqueryBillServiceResponse.getMaximumBillAmount());
		 beneficiaryDTO.setTransactionReferenceNumber(inqueryBillServiceResponse.getPrimaryRefNumber());
		 if(billStatus){
			 response.setResCde(BillPaymentResponseCodeConstants.NO_OUTSTANDING_BILL);
			 response.setSuccess(false);
		 }
		 return response;
	}

	 /**
	  *
	  * Set service type for beneficiary
	 * @param request
	 * @param beneficiaryDTO
	 */
	private void setServiceType(RequestContext request, BeneficiaryDTO beneficiaryDTO) {
	        List<BillerDTO> billerlist = getAllBillerList(request);
	        for (BillerDTO billerDTO : billerlist) {
	            if (billerDTO.getBillerId() != null
	                    && billerDTO.getBillerId()
	                            .equals(beneficiaryDTO.getBillerId())
	                    && !"ETISALATWASEL".equals(billerDTO.getBillerId())) {
	            	beneficiaryDTO.setTopupService(billerDTO.getServiceType());
	            }
	        }
	    }

	public InqueryBillService getInqueryBillService() {
		return inqueryBillService;
	}

	public void setInqueryBillService(InqueryBillService inqueryBillService) {
		this.inqueryBillService = inqueryBillService;
	}


}
