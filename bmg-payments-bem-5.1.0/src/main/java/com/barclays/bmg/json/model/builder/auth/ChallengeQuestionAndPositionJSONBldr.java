/*
 * Copyright (c) 2013 Barclays Bank Plc, All Rights Reserved.
 *
 * This code is confidential to Barclays Bank Plc and shall not be disclosed
 * outside the Bank without the prior written permission of the Director of
 * CIO
 *
 * In the event that such disclosure is permitted the code shall not be copied
 * or distributed other than on a need-to-know basis and any recipients may be
 * required to sign a confidentiality undertaking in favour of Barclays Bank
 * Plc.
 */
package com.barclays.bmg.json.model.builder.auth;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.exception.BMBCustomClassCastException;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.ussd.auth.operation.response.ChallengeQuestionAndPositionOperationResponse;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>ChallengeQuestionAndPositionJSONBldr.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 21, 2013</b> </br>
 * ***********************************************************
 * 
 * @author E20043104
 * 
 */
public class ChallengeQuestionAndPositionJSONBldr implements BMBJSONBuilder {

    @Override
    /*
     * Overrides super class method to provide its own implementation.
     * 
     * @param ResponseContext
     * 
     * @return Object
     */
    public Object createJSONResponse(ResponseContext responseContext) {
	if (responseContext instanceof ChallengeQuestionAndPositionOperationResponse) {
	    ChallengeQuestionAndPositionOperationResponse response = (ChallengeQuestionAndPositionOperationResponse) responseContext;

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
     * @param ChallengeQuestionAndPositionOperationResponse
     * @return BMBPayloadHeader
     */
    protected BMBPayloadHeader createHeader(ChallengeQuestionAndPositionOperationResponse response) {

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
     * @param ChallengeQuestionAndPositionOperationResponse
     * @param BMBPayload
     * @return void
     */
    protected void populatePayLoad(ChallengeQuestionAndPositionOperationResponse response, BMBPayload bmbPayload) {
	ChallengeQuestionAndPositionJSONResponseModel responseModel = null;
	if (response != null && response.isSuccess()) {
	    responseModel = new ChallengeQuestionAndPositionJSONResponseModel();
	    // if(response!=null && response.isSuccess()){
	    if (response.getQuestionWithPositions() != null && response.getQuestionWithPositions().length > 0) {
		responseModel.setQuestionWithPositions(response.getQuestionWithPositions());

	    }
	    // }
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

	String respId = ResponseIdConstants.CHALLENGE_QUES_RESPONSE_ID;

	return respId;
    }

}
