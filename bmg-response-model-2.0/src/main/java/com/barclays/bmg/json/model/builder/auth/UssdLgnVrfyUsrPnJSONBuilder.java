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
/**
 * Package name is com.barclays.bmg.json.model.builder.auth
 * /
 */
package com.barclays.bmg.json.model.builder.auth;

import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.exception.BMBCustomClassCastException;
import com.barclays.bmg.json.model.builder.BMBCommonJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.TacticalHelloMoneyVerifyUserOperationResponse;
import com.barclays.bmg.operation.response.UssdLgnVrfyUsrPnOperationResponse;

/**
 * ****************** Revision History ********************** </br> Project Name is <b>bmg-response-model-2.0</b> </br> The file name is
 * <b>UssdLgnVrfyUsrPnJSONBuilder.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 15, 2013</b> </br>
 * ******************************************************
 * 
 * @author e20037686 </br> * The Class UssdLgnVrfyUsrPnJSONBuilder created using JRE 1.6.0_10
 */
public class UssdLgnVrfyUsrPnJSONBuilder extends BMBCommonJSONBuilder implements BMBJSONBuilder {

    /**
     * The Constant named "RESID" is created.
     */
    private static final String RESID = "RES0109";

    /*
     * (non-Javadoc)
     * 
     * @see com.barclays.bmg.json.model.builder.BMBJSONBuilder#createJSONResponse(com.barclays.bmg.context.ResponseContext)
     */
    @Override
    public Object createJSONResponse(ResponseContext responseContext) {

	if (responseContext instanceof UssdLgnVrfyUsrPnOperationResponse) {
	    UssdLgnVrfyUsrPnOperationResponse resp = (UssdLgnVrfyUsrPnOperationResponse) responseContext;

	    BMBPayload bmbPayload = new BMBPayload(createUssdHeader(resp));

	    return bmbPayload;
	} else if (responseContext instanceof TacticalHelloMoneyVerifyUserOperationResponse) {
	    TacticalHelloMoneyVerifyUserOperationResponse resp = (TacticalHelloMoneyVerifyUserOperationResponse) responseContext;

	    BMBPayload bmbPayload = new BMBPayload(createThmHeader(resp));

	    return bmbPayload;
	} else {
	    throw new BMBCustomClassCastException("BMB Custom Class Cast Exception");
	}

    }

    /**
     * The method is written for Creates the header.
     * 
     * @param resp
     *            the resp
     * @return the BMBPayloadHeader's object
     */
    protected BMBPayloadHeader createUssdHeader(UssdLgnVrfyUsrPnOperationResponse resp) {
	BMBPayloadHeader header = new BMBPayloadHeader();

	header.setResCde(resp.getResCde());
	header.setResMsg(resp.getResMsg());
	header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
	header.setResId(RESID);
	return header;
    }

    protected BMBPayloadHeader createThmHeader(TacticalHelloMoneyVerifyUserOperationResponse resp) {
	BMBPayloadHeader header = new BMBPayloadHeader();

	header.setResCde(resp.getResCde());
	header.setResMsg(resp.getResMsg());
	header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
	header.setResId(RESID);
	return header;
    }
}