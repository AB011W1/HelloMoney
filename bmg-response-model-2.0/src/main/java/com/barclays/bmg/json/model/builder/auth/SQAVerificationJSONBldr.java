package com.barclays.bmg.json.model.builder.auth;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.exception.BMBCustomClassCastException;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.SQAAuthenticationOperationResponse;


public class SQAVerificationJSONBldr implements BMBJSONBuilder{

	@Override
	public Object createJSONResponse(ResponseContext responseContext) {

		if(responseContext instanceof SQAAuthenticationOperationResponse) {
			SQAAuthenticationOperationResponse resp = (SQAAuthenticationOperationResponse) responseContext;

			BMBPayload bmbPayload = new BMBPayload(createHeader(resp));

			populatePayLoad(resp, bmbPayload);

			return bmbPayload;
		} else {
			throw new BMBCustomClassCastException("BMB Custom Class Cast Exception");
		}
	}

	protected BMBPayloadHeader createHeader(SQAAuthenticationOperationResponse response) {

		BMBPayloadHeader header = new BMBPayloadHeader();
		if(response !=null && response.isSuccess()){
			header.setResId(ResponseIdConstants.SECOND_AUTH_SUCCESS);
			header.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
			header.setResMsg("");
		}else if(response != null){
			header.setResId(ResponseIdConstants.SQA_VALIDATION_RESPONSE_ID);
			header.setResCde(response.getResCde());
			header.setResMsg(response.getResMsg());
		}

		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);


		return header;
	}


	protected void populatePayLoad(
			SQAAuthenticationOperationResponse response,
			BMBPayload bmbPayload) {
		bmbPayload.setPayData(null);
	}
}
