package com.barclays.bmg.json.model.builder.pesalink;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.json.model.ManageFundtransferStatusJsonModel;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.billpayment.ManageFundtransferStatusOperationResponse;

public class ManageFundtransferStatusJsonBldr extends
		BMBMultipleResponseJSONBuilder implements BMBJSONBuilder {

	@Override
	public Object createMultipleJSONResponse(
			ResponseContext... responseContexts) {
		// TODO Auto-generated method stub
		BMBPayloadHeader bmbPayloadHeader = null;
		BMBPayload bmbPayload = null;
		for (ResponseContext response : responseContexts) {
		    if (response != null && !response.isSuccess()) {
			bmbPayloadHeader = createHeader(response);
			break;
		    }
		}

		if (bmbPayloadHeader != null) {
		    bmbPayload = new BMBPayload(bmbPayloadHeader);
		} else {
		    bmbPayload = new BMBPayload(createHeader(responseContexts[0]));
		    populatePayLoad(bmbPayload, responseContexts);
		}

		return bmbPayload;

	}

	protected void populatePayLoad(BMBPayload bmbPayload,ResponseContext... responses) {
		ManageFundtransferStatusOperationResponse response=(ManageFundtransferStatusOperationResponse)responses[0];
		ManageFundtransferStatusJsonModel responseModel = null;
		if (response != null && response.isSuccess()) {
			responseModel = new ManageFundtransferStatusJsonModel();
			responseModel.setErrorCode(response.getErrorCode());
			responseModel.setErrorDesc(response.getErrorDesc());
			responseModel.setTxnDt(response.getTxnDt());
			responseModel.setTxnRefNo(response.getTxnRefNo());
			responseModel.setServiceResponseCode(response.getServiceResponseCode());
			responseModel.setErrorList(response.getErrorList());
		}

		bmbPayload.setPayData(responseModel);
	}

	protected BMBPayloadHeader createHeader(
			ResponseContext response) {

		BMBPayloadHeader header = new BMBPayloadHeader();
		if (response != null && response.isSuccess()) {
			header.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
			header.setResMsg("Succes GroupWalllet Transaction Initiation");

		} else if (response != null && !response.isSuccess()) {
			header.setResCde(response.getResCde());
			header.setResMsg(response.getResMsg());
		}
		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);

		return header;
	}
}
