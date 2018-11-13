package com.barclays.bmg.json.model.builder.chequebook;

import com.barclays.bmg.chequebook.operation.response.ChequeBookExecuteOperationResponse;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.model.chequebook.ChequeBookExecuteJsonModel;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.utils.BMGFormatUtility;

public class ChequeBookRequestExecuteBldr extends
		BMBMultipleResponseJSONBuilder implements BMBJSONBuilder {

	@Override
	public Object createMultipleJSONResponse(
			ResponseContext... responseContexts) {
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
		header.setResId(ResponseIdConstants.CHEQUE_BOOK_EXECUTE);

		return header;
	}

	protected void populatePayLoad(BMBPayload bmbPayload,
			ResponseContext... responses) {

		ChequeBookExecuteJsonModel responseModel = new ChequeBookExecuteJsonModel();

		ChequeBookExecuteOperationResponse chequeBookExecuteOperationResponse = (ChequeBookExecuteOperationResponse) responses[0];

		responseModel.setBemRefNo(chequeBookExecuteOperationResponse
				.getTxnRefNo());
		responseModel.setResDtTm(BMGFormatUtility
				.getLongDate(chequeBookExecuteOperationResponse.getTxnDt()));
		responseModel.setTxnDtTm(BMGFormatUtility
				.getLongDate(chequeBookExecuteOperationResponse.getTxnDt()));
		responseModel.setTxnRefNo(chequeBookExecuteOperationResponse
				.getTxnRefNo());
		responseModel.setTxnResRefNo(chequeBookExecuteOperationResponse
				.getTxnRefNo());

		responseModel.setTxnMsg(chequeBookExecuteOperationResponse.getTxnMsg());

		bmbPayload.setPayData(responseModel);

	}

}
