package com.barclays.bmg.json.model.builder.auth;

import com.barclays.bmg.constants.AuthResponseCodeConstants;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.exception.BMBCustomClassCastException;
import com.barclays.bmg.json.model.auth.SQAResponseModel;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.SQAGenerateAuthenticationOperationResponse;

public class SQAAuthenticationJSONBldr implements BMBJSONBuilder{

	@Override
	public Object createJSONResponse(ResponseContext responseContext) {

		if(responseContext instanceof SQAGenerateAuthenticationOperationResponse) {
			SQAGenerateAuthenticationOperationResponse resp = (SQAGenerateAuthenticationOperationResponse) responseContext;

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
		}else{
			header.setResCde(AuthResponseCodeConstants.SECOND_AUTH_INVALID);
		}
		header.setResMsg("");
		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
		header.setResId(ResponseIdConstants.SQA_RESPONSE_ID);

		return header;
	}

	protected void populatePayLoad(
			SQAGenerateAuthenticationOperationResponse response,
			BMBPayload bmbPayload) {
		SQAResponseModel sqaResponseModel = null;
		if(response !=null && response.isSuccess()){
			sqaResponseModel = new SQAResponseModel();
			sqaResponseModel
				.setSqaQues(response
					.getQuestion());
		}
		bmbPayload.setPayData(sqaResponseModel);
	}
}
