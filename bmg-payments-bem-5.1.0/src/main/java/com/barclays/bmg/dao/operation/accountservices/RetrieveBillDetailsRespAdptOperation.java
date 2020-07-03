package com.barclays.bmg.dao.operation.accountservices;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.barclays.bem.BEMServiceHeader.BEMResHeader;
import com.barclays.bem.Bill.Bill;
import com.barclays.bem.Bill.BillInvoiceDetails;
import com.barclays.bem.Bill.InvoiceReferenceDetails;
import com.barclays.bem.RetrieveBillDetails.RetrieveBillDetailsResponse;
import com.barclays.bmg.constants.ErrorCodeConstant;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ServiceErrorCodeConstant;
import com.barclays.bmg.constants.ServiceIdConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dto.Amount;
import com.barclays.bmg.dto.InvoiceDetails;
import com.barclays.bmg.exception.BMBDataAccessException;
import com.barclays.bmg.service.response.RetrieveBillDetailsServiceResponse;

public class RetrieveBillDetailsRespAdptOperation {

    public RetrieveBillDetailsServiceResponse adaptResponse(WorkContext workContext, Object obj){

        RetrieveBillDetailsServiceResponse response = new RetrieveBillDetailsServiceResponse();

        RetrieveBillDetailsResponse bemResponse = (RetrieveBillDetailsResponse) obj;
        if(null!= bemResponse.getBillDetails())
        {
        bemResponse.getBillDetails(0);
        }

       response.setSuccess(checkResHeader(bemResponse.getResponseHeader()));
       
       //Ghana DataBundle change
       DAOContext daoContext = (DAOContext) workContext;

		Object[] args = daoContext.getArguments();

		RequestContext request = (RequestContext) args[0];
		Context context = request.getContext();
		String businessId = context.getBusinessId().toString();

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

				//Probase
				if (null!= billDetail.getBillInvoiceDetails() && businessId.equalsIgnoreCase("ZMBRB")) {
					InvoiceDetails invoiceDetails = new InvoiceDetails();
					int refSize = billDetail.getBillInvoiceDetails(0).getInvoiceReferenceDetails().length;
					LinkedHashMap<String, String> temp = new LinkedHashMap<String, String>();
					for(int i=0;i<refSize;i++){
						temp.put(billDetail.getBillInvoiceDetails(0).getInvoiceReferenceDetails(i).getReferenceTypeCode(),
								billDetail.getBillInvoiceDetails(0).getInvoiceReferenceDetails(i).getReferenceValue());
					}
					invoiceDetails.setProbaseDetails(temp);
					response.setBillInvoiceDetails(invoiceDetails);
				}
				
				//Ghana Databundle change
				//TODO add condition 
				if(null != businessId && businessId.equalsIgnoreCase("GHBRB"))
				{
					if (null!= billDetail.getBillInvoiceDetails()) {
						InvoiceDetails invoiceDetails = new InvoiceDetails();
						LinkedHashMap<String, String> temp = new LinkedHashMap<String, String>();
						List<String> bundleLife = new ArrayList<String>();
						LinkedHashMap<String, String> referenceDataBundle = new LinkedHashMap<String, String>(); //SSAMakeBillPayment Ghana Data Bundle
						for(int i=0;i<billDetail.getBillInvoiceDetails().length;i++) {
							if(null!= billDetail.getBillInvoiceDetails(i).getInvoiceReferenceDetails(0)) {
							temp.put(billDetail.getBillInvoiceDetails(i).getInvoiceReferenceDetails(0).getReferenceTypeCode(),
									billDetail.getBillInvoiceDetails(i).getInvoiceReferenceDetails(0).getReferenceValue());
							//SSAMakeBillPayment Ghana Data Bundle
							referenceDataBundle.put(billDetail.getBillInvoiceDetails(i).getInvoiceReferenceDetails(0).getReferenceValue(),billDetail.getBillInvoiceDetails(i).getInvoiceReferenceNumber());
							}
							if(null != billDetail.getBillInvoiceDetails(i).getInvoiceReferenceDetails(0).getReferenceID())
								bundleLife.add(billDetail.getBillInvoiceDetails(i).getInvoiceReferenceDetails(0).getReferenceID());
						}
						invoiceDetails.setProbaseDetails(temp);
						invoiceDetails.setBundleLife(bundleLife);
						invoiceDetails.setInvoiceRefNo(referenceDataBundle);//SSAMakeBillPayment Ghana Data Bundle
						response.setBillInvoiceDetails(invoiceDetails);
					}
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

					}else if (ErrorCodeConstant.DATA_BUNDLE_BILL_INFO.equals(error.getPPErrorCode())) {
						String errorCode = ErrorCodeConstant.MW_MESSAGE_PREFIX + error.getPPErrorCode();
						response.setResCde(errorCode);
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
				|| ResponseCodeConstants.SUBMITTED_TXN_RES_CODE.equals(resCode)){
			valid = true;
		}

		//URA Biller Changes
		if(ErrorCodeConstant.BUSINESS_ERROR.equals(resCode) && resHeader.getErrorList() != null && resHeader.getErrorList().length > 0)
			{
			for (com.barclays.bem.BEMServiceHeader.Error error : resHeader
					.getErrorList()) {
				if(ErrorCodeConstant.MUBS_ERROR
						.equals(error.getPPErrorCode())|| ErrorCodeConstant.URA_ERROR
						.equals(error.getPPErrorCode())||ErrorCodeConstant.DATA_BUNDLE_BILL_INFO.equals("309")){
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
