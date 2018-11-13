package com.barclays.bmg.json.model.builder.auth;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.exception.BMBCustomClassCastException;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.SPAuthenticationOperationResponse;

public class InvalidLoginJSONBldr implements BMBJSONBuilder {

	@Override
	public Object createJSONResponse(ResponseContext responseContext) {

		if(responseContext instanceof SPAuthenticationOperationResponse) {
			SPAuthenticationOperationResponse resp = (SPAuthenticationOperationResponse) responseContext;

			BMBPayload bmbPayload = new BMBPayload(createHeader(resp));

			populatePayLoad(resp, bmbPayload);

			return bmbPayload;
		} else {
			throw new BMBCustomClassCastException("BMB Custom Class Cast Exception");
		}
	}

	protected BMBPayloadHeader createHeader(ResponseContext response) {

		BMBPayloadHeader header = new BMBPayloadHeader();
		if(response !=null && response.isSuccess()){
			header.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
		}else if(response != null){
			header.setResCde(response.getResCde());
			header.setResMsg(response.getResMsg());
		}

		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
		header.setResId(ResponseIdConstants.LOGIN_RESPONSE_ID);

		return header;

	}

	protected void populatePayLoad(
			SPAuthenticationOperationResponse response,
			BMBPayload bmbPayload) {
		bmbPayload.setPayData(null);
	}
}
