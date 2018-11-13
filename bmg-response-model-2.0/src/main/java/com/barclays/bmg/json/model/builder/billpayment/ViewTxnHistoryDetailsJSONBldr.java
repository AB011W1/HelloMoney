package com.barclays.bmg.json.model.builder.billpayment;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.billpayment.SearchTransactionHistoryOperationResponse;

/**
 * @author BTCI JSON builder class for View Txn History Details Response
 */
public abstract class ViewTxnHistoryDetailsJSONBldr extends
		BMBMultipleResponseJSONBuilder {

	/**
	 * @param payeeType
	 * @return Get the response id as per payee type
	 */

	public abstract String getResponseId(String transactionType);

	public abstract void populatePayLoad(BMBPayload bmbPayload,
			ResponseContext... responseContexts);

	/*
	 * (non-Javadoc)
	 *
	 * @seecom.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder#
	 * createMultipleJSONResponse(com.barclays.bmg.context.ResponseContext[])
	 */
	@Override
	public Object createMultipleJSONResponse(
			ResponseContext... responseContexts) {
		BMBPayloadHeader bmbPayloadHeader = null;
		BMBPayload bmbPayload = null;
		for (ResponseContext response : responseContexts) {
			if (response != null && !response.isSuccess()) {
				bmbPayloadHeader = createHeader(response,
						getResponseId(response.getTxnTyp()));
				break;
			}
		}

		if (bmbPayloadHeader != null) {
			bmbPayload = new BMBPayload(bmbPayloadHeader);
		} else {
			ResponseContext response = responseContexts[0];
			bmbPayload = new BMBPayload(createHeader(response,
					getResponseId(response.getTxnTyp())));
			populatePayLoad(bmbPayload, responseContexts);
		}

		return bmbPayload;
	}

	/**
	 * @param response
	 * @return BMBPayloadHeader
	 */
	protected BMBPayloadHeader createHeader(ResponseContext response) {

		BMBPayloadHeader header = new BMBPayloadHeader();
		SearchTransactionHistoryOperationResponse resp = (SearchTransactionHistoryOperationResponse) response;

		if (response != null) {
			if (response.isSuccess()) {
				header.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
				header.setResMsg("");
			} else {
				header.setResCde(response.getResCde());
				header.setResMsg(response.getResMsg());
			}
			header.setResId(getResponseId(resp.getFundTransferType()));
		}
		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);

		return header;
	}

}
