package com.barclays.bmg.json.model.builder.auth;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.exception.BMBCustomClassCastException;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.PostAuthenticationOperationResponse;

public class PostAuthenticationJSONBldr implements BMBJSONBuilder {

	@Override
	public Object createJSONResponse(ResponseContext responseContext) {

		if(responseContext instanceof PostAuthenticationOperationResponse) {
			PostAuthenticationOperationResponse resp = (PostAuthenticationOperationResponse) responseContext;

			BMBPayload bmbPayload = new BMBPayload(createHeader(resp));

			populatePayLoad(resp, bmbPayload);

			return bmbPayload;
		} else {
			throw new BMBCustomClassCastException("BMB Custom Class Cast Exception");
		}
	}

	protected BMBPayloadHeader createHeader(PostAuthenticationOperationResponse response) {

		BMBPayloadHeader header = new BMBPayloadHeader();
		if(response !=null && response.isSuccess()){
			header.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
		}else if(response != null){
			header.setResCde(response.getResCde());
			header.setResMsg(response.getResMsg());
			header.setResId(response.getSecondAuthResId());
		}

		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);

		return header;

	}

	protected void populatePayLoad(
			PostAuthenticationOperationResponse response,
			BMBPayload bmbPayload) {
		bmbPayload.setPayData(null);
	}
}
