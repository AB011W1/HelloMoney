package com.barclays.bmg.json.model.builder.fxrate;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.FxBoardRatesDTO;
import com.barclays.bmg.fxrate.operation.response.FxRateOperationResponse;
import com.barclays.bmg.json.model.FxRateJSONModel;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;

public class FxRateJSONBldr extends BMBMultipleResponseJSONBuilder implements BMBJSONBuilder {

    @Override
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
     * @param FxRateOperationResponse
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
     * @param FxRateOperationResponse
     * @param BMBPayload
     * @return void
     */

    protected void populatePayLoad(BMBPayload bmbPayload, ResponseContext... responses) {
	FxRateJSONModel responseModel = new FxRateJSONModel();
	FxRateOperationResponse fxRateOperationResponse = (FxRateOperationResponse) responses[0];
	bmbPayload.setPayData(responseModel);
	FxBoardRatesDTO fxBoardRatesDTO = fxRateOperationResponse.getFxBoardRateDTO();
	if(fxBoardRatesDTO.getBuyRate()!=null){
	responseModel.setBuyRate(fxBoardRatesDTO.getBuyRate());
	}if(fxBoardRatesDTO.getSellRate()!=null){
	responseModel.setSellRate(fxBoardRatesDTO.getSellRate());}
	bmbPayload.setPayData(responseModel);
    }

    /**
     * This method getResponseId has the purpose to get response Id for JSON response.
     *
     * @param String
     *            txnTyp
     * @return String respId
     */
    private String getResponseId(String txnType) {
	return ResponseIdConstants.FOREX_RATE_RESPONSE_ID;
    }
}
