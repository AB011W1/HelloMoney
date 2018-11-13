package com.barclays.bmg.json.model.builder.auth;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.exception.BMBCustomClassCastException;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.ussd.auth.operation.response.SelfRegistrationAccountValidationOperationResponse;
import com.barclays.bmg.ussd.auth.operation.response.SelfRegistrationInitOperationResponse;

public class SelfRegistrationAccountValidationJSONBldr implements BMBJSONBuilder {

    @Override
    /*
     * Overrides super class method to provide its own implementation.
     *
     * @param ResponseContext
     *
     * @return Object
     */
    public Object createJSONResponse(ResponseContext responseContext) {
	if (responseContext instanceof SelfRegistrationInitOperationResponse) {
		SelfRegistrationInitOperationResponse response = (SelfRegistrationInitOperationResponse) responseContext;

	    BMBPayload bmbPayload = new BMBPayload(createHeader(response));

	    populatePayLoad(response, bmbPayload);

	    return bmbPayload;
	} else {
	    throw new BMBCustomClassCastException("BMB Custom Class Cast Exception");
	}
    }

    /**
     * This method createHeader has the purpose to create header for JSON response.
     *
     * @param SelfRegistrationInitOperationResponse
     * @return BMBPayloadHeader
     */
    protected BMBPayloadHeader createHeader(SelfRegistrationInitOperationResponse response) {

	BMBPayloadHeader header = new BMBPayloadHeader();
	if (response != null && response.isSuccess()) {
	    header.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
	    header.setResMsg("");
	    header.setResId(getResponseId(response.getTxnTyp()));
	} else if (response != null && !response.isSuccess()) {
	    header.setResCde(response.getResCde());
	    header.setResMsg(response.getResMsg());
	    header.setResId(getResponseId(response.getTxnTyp()));
	}
	header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);

	return header;
    }

    /**
     * This method populatePayLoad has the purpose to create data for JSON response.
     *
     * @param SelfRegistrationInitOperationResponse
     * @param BMBPayload
     * @return void
     */
    protected void populatePayLoad(SelfRegistrationInitOperationResponse response, BMBPayload bmbPayload) {
    	SelfRegistrationAccountValidationOperationJSONResponseModel responseModel = null;

	if (response != null) {

	    responseModel = new SelfRegistrationAccountValidationOperationJSONResponseModel();
	    responseModel.setServiceResponse(response.getServiceResponse());

	}
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

	String respId = ResponseIdConstants.SELF_REGISTRATION_ACCOUNT_VALIDATION_RESPONSE_ID;

	return respId;
    }

}
