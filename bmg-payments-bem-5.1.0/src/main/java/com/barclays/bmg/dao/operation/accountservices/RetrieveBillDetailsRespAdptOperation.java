package com.barclays.bmg.dao.operation.accountservices;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import com.barclays.bem.BEMServiceHeader.BEMResHeader;
import com.barclays.bem.Bill.Bill;
import com.barclays.bem.Bill.BillInvoiceDetails;
import com.barclays.bem.Bill.InvoiceReferenceDetails;
import com.barclays.bem.RetrieveBillDetails.RetrieveBillDetailsResponse;
import com.barclays.bmg.constants.ErrorCodeConstant;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ServiceErrorCodeConstant;
import com.barclays.bmg.constants.ServiceIdConstants;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dto.Amount;
import com.barclays.bmg.exception.BMBDataAccessException;
import com.barclays.bmg.service.response.RetrieveBillDetailsServiceResponse;

public class RetrieveBillDetailsRespAdptOperation {

    public RetrieveBillDetailsServiceResponse adaptResponse(WorkContext workContext, Object obj){

        RetrieveBillDetailsServiceResponse response = new RetrieveBillDetailsServiceResponse();

        RetrieveBillDetailsResponse bemResponse = (RetrieveBillDetailsResponse) obj;
        bemResponse.getBillDetails(0);

       response.setSuccess(checkResHeader(bemResponse.getResponseHeader()));

       if (response.isSuccess()) {

			Bill billDetail = bemResponse.getBillDetails()[0];
			if(null != billDetail) {
				if (null != billDetail.getPrimaryReferenceNumber()) {
					response.setPrimaryReferenceNumber(billDetail.getPrimaryReferenceNumber());
				}

				if (null != billDetail.getSecondaryReferenceNumber()) {
					response.setSecondaryReferenceNumber(billDetail.getSecondaryReferenceNumber());
				}

				if (null != billDetail.getBillAmount()) {
					response.setFeeAmount(new Amount(billDetail.getBillAmountCurrencyCode(), new BigDecimal(billDetail.getBillAmount())));
				}
				if (null != billDetail.getBillDueDate()) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					response.setBillDueDate(sdf.format(billDetail.getBillDueDate().getTime()));
				}

				BillInvoiceDetails[] invDetail = billDetail.getBillInvoiceDetails();
				if(null != invDetail[0] && invDetail[0].getInvoiceReferenceDetails().length > 0){
					for(InvoiceReferenceDetails referenceDetails : invDetail[0].getInvoiceReferenceDetails()) {
						if("BillPayOpt".equalsIgnoreCase(referenceDetails.getReferenceTypeCode())) {
							response.setPaymentType(referenceDetails.getReferenceValue());
						}
					}
				}
			}
		} else {
			//MUBS Biller Changes
			//URA Biller Changes
			String resCode = bemResponse.getResponseHeader().getServiceResStatus().getServiceResCode();
			if(ErrorCodeConstant.BUSINESS_ERROR.equals(resCode) && bemResponse.getResponseHeader().getErrorList() != null && bemResponse.getResponseHeader().getErrorList().length > 0){
				for (com.barclays.bem.BEMServiceHeader.Error error : bemResponse.getResponseHeader()
						.getErrorList()) {
					if(ErrorCodeConstant.MUBS_ERROR
							.equals(error.getPPErrorCode())|| ErrorCodeConstant.URA_ERROR
							.equals(error.getPPErrorCode())){
						response.setResCde(error.getPPErrorCode());
						response.setResMsg(error.getPPErrorDesc());

					}
				}
			}

		}
		return response;

    }

    private boolean checkResHeader(BEMResHeader resHeader) {
		String resCode = null;
		if(null != resHeader && null != resHeader.getServiceResStatus())
			resCode = resHeader.getServiceResStatus().getServiceResCode();
		boolean valid = false;
		if (ResponseCodeConstants.SUCCESS_RES_CODE.equals(resCode)
				|| ResponseCodeConstants.SUBMITTED_TXN_RES_CODE.equals(resCode)) {
			valid = true;
		}

		//URA Biller Changes
		if(ErrorCodeConstant.BUSINESS_ERROR.equals(resCode) && resHeader.getErrorList() != null && resHeader.getErrorList().length > 0){
			for (com.barclays.bem.BEMServiceHeader.Error error : resHeader
					.getErrorList()) {
				if(ErrorCodeConstant.MUBS_ERROR
						.equals(error.getPPErrorCode())|| ErrorCodeConstant.URA_ERROR
						.equals(error.getPPErrorCode())){
					return false;
				}
			}
		}

		if (!valid && resHeader != null && resHeader.getErrorList() != null
				&& resHeader.getErrorList().length > 0) {
			String serviceId = resHeader.getServiceContext().getServiceID();
			for (com.barclays.bem.BEMServiceHeader.Error error : resHeader.getErrorList()) {
				// Error Message Scenario for Payments.
				if ((ErrorCodeConstant.MW_FINANCIAL_SERVICE_TIMEOUT
						.equals(error.getErrorCode()) && (ServiceIdConstants.SERVICE_MAKE_CREDIT_CARD_PAYMENT.equals(serviceId)
						|| ServiceIdConstants.SERVICE_MAKE_BILL_PAYMENT.equals(serviceId)
						|| ServiceIdConstants.SERVICE_MAKE_DOMESTIC_DEMAND_DRAFT_BY_ACCOUNT.equals(serviceId)
						|| ServiceIdConstants.SERVICE_MAKE_DOMESTIC_FUND_TRANSFER.equals(serviceId)
						|| ServiceIdConstants.SERVICE_MAKE_INTERNATIONAL_FUND_TRANSFER.equals(serviceId)))
						|| (ErrorCodeConstant.MW_VAS_TIMEOUT.equals(error.getErrorCode())
							&& (ServiceIdConstants.SERVICE_MAKE_CREDIT_CARD_PAYMENT.equals(serviceId)
							|| ServiceIdConstants.SERVICE_MAKE_BILL_PAYMENT.equals(serviceId)))) {
					BMBDataAccessException dae = new BMBDataAccessException(ErrorCodeConstant.PREFIX_FINANCIAL_SERVICE
									+ error.getErrorCode());
					dae.printStackTrace();
					throw dae;
				} else if (ServiceErrorCodeConstant.UBP_HOST_ERROR_CODE.equals(error.getErrorCode())) {
					BMBDataAccessException dae = new BMBDataAccessException(error.getErrorCode(),
							error.getPPErrorCode(), error.getErrorDesc());
					dae.printStackTrace();
					throw dae;
				} else {
					BMBDataAccessException dae = new BMBDataAccessException(error.getErrorCode());
					dae.printStackTrace();
					throw dae;
				}
			}
		}
		if (!valid) {
			BMBDataAccessException dae = new BMBDataAccessException(resCode);
			dae.printStackTrace();
			throw dae;
		}
		return valid;
	}
}