package com.barclays.bmg.json.model.builder.cashsend;

import com.barclays.bmg.cashsend.operation.response.CashSendOneTimeExecuteOperationResponse;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.json.model.CashSendOneTimeExecuteJSONModel;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;

public class CashSendOneTimeExecuteJSONBulider extends BMBMultipleResponseJSONBuilder implements BMBJSONBuilder {

    @Override
    /*
     * Overrides super class method to provide its own implementation.
     *
     * @param ResponseContext
     *
     * @return Object
     */
    public Object createMultipleJSONResponse(ResponseContext... responseContexts) {
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

    /**
     * This method createHeader has the purpose to create header for JSON response.
     *
     * @param SelfRegistrationExecutionOperationResponse
     * @return BMBPayloadHeader
     */

    protected BMBPayloadHeader createHeader(ResponseContext response) {

	BMBPayloadHeader header = new BMBPayloadHeader();
	if (response != null && response.isSuccess()) {
	    header.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
	    header.setResMsg(response.getResMsg());
	} else if (response != null) {
	    header.setResCde(response.getResCde());
	    header.setResMsg(response.getResMsg());
	}
	header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
	if(null != response)
		header.setResId(getResponseId(response.getTxnTyp()));
	return header;
    }

    /**
     * This method populatePayLoad has the purpose to create data for JSON response.
     *
     * @param SelfRegistrationExecutionOperationResponse
     * @param BMBPayload
     * @return void
     */

    protected void populatePayLoad(BMBPayload bmbPayload, ResponseContext... responses) {

	CashSendOneTimeExecuteJSONModel responseModel = new CashSendOneTimeExecuteJSONModel();

	CashSendOneTimeExecuteOperationResponse cashSendOneTimeExecuteOperationResponse = (CashSendOneTimeExecuteOperationResponse) responses[0];
	bmbPayload.setPayData(responseModel);

	responseModel.setTxnRefNo(cashSendOneTimeExecuteOperationResponse.getTxnRefNo());
	responseModel.setVoucherId(cashSendOneTimeExecuteOperationResponse.getVoucherId());
	responseModel.setTxnMsg(cashSendOneTimeExecuteOperationResponse.getTxnMsg());
	bmbPayload.setPayData(responseModel);

    }

    /**
     * This method getResponseId has the purpose to get response Id for JSON response.
     *
     * @param String
     *            txnType
     * @return String respId
     */
    private String getResponseId(String txnType) {

	// Which Response ID to use
	String respId = ResponseIdConstants.SELF_REGISTRATION__EXEC_RESPONSE_ID;
	return respId;
    }
}
