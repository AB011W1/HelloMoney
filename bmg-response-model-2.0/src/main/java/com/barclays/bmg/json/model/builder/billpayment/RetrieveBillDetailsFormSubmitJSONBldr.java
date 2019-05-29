package com.barclays.bmg.json.model.builder.billpayment;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.json.model.billpayment.BillDetailsJSONBean;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.service.response.RetrieveBillDetailsServiceResponse;


public class RetrieveBillDetailsFormSubmitJSONBldr extends
		BMBMultipleResponseJSONBuilder implements BMBJSONBuilder {

	protected void populatePayLoad(BMBPayload bmbPayload, ResponseContext... responseContexts) {
		BillDetailsJSONBean billDetailsJSONBean = null;
		RetrieveBillDetailsServiceResponse response = (RetrieveBillDetailsServiceResponse) responseContexts[0];
		if (response != null && response.isSuccess()) {
			billDetailsJSONBean = new BillDetailsJSONBean();
			billDetailsJSONBean.setFeeAmount(response.getFeeAmount());
			billDetailsJSONBean.setPaymentType(response.getPaymentType());
			billDetailsJSONBean.setBillDueDate(response.getBillDueDate());
			billDetailsJSONBean.setPrimaryReferenceNumber(response.getPrimaryReferenceNumber());
			billDetailsJSONBean.setSecondaryReferenceNumber(response.getSecondaryReferenceNumber());
		}
		bmbPayload.setPayData(billDetailsJSONBean);
	}

	protected BMBPayloadHeader createHeader(ResponseContext response) {

		BMBPayloadHeader header = new BMBPayloadHeader();
		if (null != response && response.isSuccess()) {
			header.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
			header.setResMsg("");
		} else  if(null != response){
			header.setResCde(response.getResCde());
			header.setResMsg(response.getResMsg());
		}
		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
		header.setResId(ResponseIdConstants.GEPG_BILL_DETAILS);

		return header;
	}


	@Override
	public Object createMultipleJSONResponse(ResponseContext... responseContexts) {
		BMBPayloadHeader bmbPayloadHeader = null;
		BMBPayload bmbPayload = null;
		for (ResponseContext response : responseContexts) {
			if (null != response && !response.isSuccess()) {
				bmbPayloadHeader = createHeader(response, ResponseIdConstants.GEPG_BILL_DETAILS);
				break;
			}
		}

		if (null != bmbPayloadHeader) {
			bmbPayload = new BMBPayload(bmbPayloadHeader);
		} else {
			ResponseContext response = responseContexts[0];
			bmbPayload = new BMBPayload(createHeader(response, ResponseIdConstants.GEPG_BILL_DETAILS));
			populatePayLoad(bmbPayload, responseContexts);
		}

		return bmbPayload;
	}



}
