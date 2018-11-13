package com.barclays.bmg.json.model.builder.fundtransfer.own;

import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.model.fundtransfer.OwnFundTransferExecutedJSONResponseModel;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.fundtransfer.DomesticFundTransferExecuteOperationResponse;
import com.barclays.bmg.utils.BMGFormatUtility;

public class DomesticFundTransferJSONBldr extends
		BMBMultipleResponseJSONBuilder {

	@Override
	public Object createMultipleJSONResponse(
			ResponseContext... responseContexts) {
		BMBPayloadHeader bmbPayloadHeader = null;
		BMBPayload bmbPayload = null;
		for (ResponseContext response : responseContexts) {
			if (response != null && !response.isSuccess()) {
				bmbPayloadHeader = createHeader(
						response,
						ResponseIdConstants.OWN_FUND_TRANSFER_EXECUTE_RESPONSE_ID);
				break;
			}
		}

		if (bmbPayloadHeader != null) {
			bmbPayload = new BMBPayload(bmbPayloadHeader);
		} else {
			bmbPayload = new BMBPayload(createHeader(responseContexts[0],
					ResponseIdConstants.OWN_FUND_TRANSFER_EXECUTE_RESPONSE_ID));
			populatePayLoad(bmbPayload, responseContexts);
		}

		return bmbPayload;
	}

	protected void populatePayLoad(BMBPayload bmbPayload,
			ResponseContext... responseContexts) {
		DomesticFundTransferExecuteOperationResponse response = (DomesticFundTransferExecuteOperationResponse) responseContexts[0];
		OwnFundTransferExecutedJSONResponseModel responseModel = null;
		if (response != null && response.isSuccess()) {
			responseModel = new OwnFundTransferExecutedJSONResponseModel();
			responseModel.setRefNo(response.getTrnRef());
			responseModel.setTxnDt(BMGFormatUtility.getLongDate(response
					.getTrnDate()));
			responseModel.setTxnMsg(response.getTxnMsg());

		}
		bmbPayload.setPayData(responseModel);
	}

}
