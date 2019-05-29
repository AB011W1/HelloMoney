package com.barclays.bmg.dao.operation.accountservices;

import java.math.BigDecimal;

import com.barclays.bem.Bill.Bill;
import com.barclays.bem.UAERetrieveBillDetails.UAERetrieveBillDetailsResponse;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.service.response.InqueryBillServiceResponse;

public class InqueryBillRespAdptOperation extends AbstractResAdptOperationAcct{

	public InqueryBillServiceResponse adaptResponse(WorkContext workContext, Object obj){

		InqueryBillServiceResponse response = new InqueryBillServiceResponse();
		UAERetrieveBillDetailsResponse bemResponse = (UAERetrieveBillDetailsResponse)obj;
		response.setSuccess(checkResponseHeader(bemResponse.getResponseHeader()));

		if(checkResponseHeader(bemResponse.getResponseHeader())){
			Bill billDetail = bemResponse.getBillDetails()[0];

			 if (billDetail.getMaximumBillAmount() != null) {
			response.setMaximumBillAmount(new BigDecimal(billDetail.getMaximumBillAmount()));
			 }
			 if (billDetail.getMinimumBillAmount() != null) {
			response.setMinimumBillAmount(new BigDecimal(billDetail.getMinimumBillAmount()));
			 }
			if (billDetail.getOutstandingBalanceAmount() != null) {
			response.setOutstandingBalanceAmount(new BigDecimal(billDetail.getOutstandingBalanceAmount()));
			}
			if(billDetail.getPrimaryReferenceNumber() !=null){
				response.setPrimaryRefNumber(billDetail.getPrimaryReferenceNumber());
			}
		}
		return response;
	}
}
