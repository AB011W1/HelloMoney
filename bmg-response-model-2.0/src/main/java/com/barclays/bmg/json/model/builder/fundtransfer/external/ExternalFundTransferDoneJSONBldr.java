package com.barclays.bmg.json.model.builder.fundtransfer.external;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.json.model.billpayment.PaymentConfirmationJSONBean;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.fundtransfer.external.InternationalFundTransferOperationResponse;
import com.barclays.bmg.utils.BMGFormatUtility;

public class ExternalFundTransferDoneJSONBldr extends BMBMultipleResponseJSONBuilder implements BMBJSONBuilder{

	@Override
	public Object createMultipleJSONResponse(
			ResponseContext... responseContexts) {
		BMBPayloadHeader bmbPayloadHeader = null;
		BMBPayload bmbPayload = null;
		for(ResponseContext response: responseContexts){
			if(response!=null && !response.isSuccess()){
				bmbPayloadHeader = createHeader(response);
				break;
			}
		}

		if(bmbPayloadHeader!=null){
			bmbPayload = new BMBPayload(bmbPayloadHeader);
		}else{
			bmbPayload = new BMBPayload(createHeader(responseContexts[0]));
			populatePayLoad(bmbPayload, responseContexts);
		}

		return bmbPayload;
	}

	protected BMBPayloadHeader createHeader(ResponseContext response) {

		BMBPayloadHeader header = new BMBPayloadHeader();
		if (response != null && response.isSuccess()) {
			header.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
			header.setResMsg("");
		} else  if(response != null){
			header.setResCde(response.getResCde());
			header.setResMsg(response.getResMsg());
		}
		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
		header.setResId(ResponseIdConstants.EXTERNAL_FUND_TRANSFER_DONE);

		return header;
	}

	protected void populatePayLoad(BMBPayload bmbPayload,
			ResponseContext... responses
			) {
		InternationalFundTransferOperationResponse response =
				(InternationalFundTransferOperationResponse)responses[0];
		PaymentConfirmationJSONBean paymentConfirmBean = null;

		if(response !=null && response.isSuccess()){
			paymentConfirmBean = new PaymentConfirmationJSONBean();
			paymentConfirmBean.setBemRefNo(response.getTrnRef());
			paymentConfirmBean.setTxnRefNo(response.getTrnRef());
			paymentConfirmBean.setTxnResRefNo(response.getTrnRef());
			paymentConfirmBean.setResDtTm(BMGFormatUtility.getLongDate(response.getTrnDate()));
			paymentConfirmBean.setTxnDtTm(BMGFormatUtility.getLongDate(response.getTrnDate()));
			paymentConfirmBean.setTxnMsg(response.getTxnMsg());
		}

		bmbPayload.setPayData(paymentConfirmBean);

	}


}
