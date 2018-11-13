package com.barclays.bmg.json.model.builder.auth;

import com.barclays.bmg.constants.ChangePasswordResponseCodeConstants;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.exception.BMBCustomClassCastException;
import com.barclays.bmg.json.model.builder.BMBCommonJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.ChangePasswordOperationResponse;

public class ChangePasswordJSONBuilder extends BMBCommonJSONBuilder implements BMBJSONBuilder {

	public Object createJSONResponse(ResponseContext responseContext) {

		if(responseContext instanceof ChangePasswordOperationResponse) {
			ChangePasswordOperationResponse response = (ChangePasswordOperationResponse)responseContext;
			BMBPayload bmbPayload = new BMBPayload(createHeader(response));

			populatePayLoad(response, bmbPayload);

			return bmbPayload;
		} else {
			throw new BMBCustomClassCastException("BMB Custom Class Cast Exception");
		}
	}

	private void populatePayLoad(
			ChangePasswordOperationResponse response,
			BMBPayload bmbPayload) {

		bmbPayload.setPayData(null);

	}

	private BMBPayloadHeader createHeader(
			ChangePasswordOperationResponse response) {
		BMBPayloadHeader header = new BMBPayloadHeader();
		if (response != null && response.isSuccess()) {
			header.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
			header.setResMsg(ChangePasswordResponseCodeConstants.PASSWORD_CHANGE_SUCCESS_MESSAGE);
		} else  if(response != null){
			header.setResCde(response.getResCde());
			header.setResMsg(response.getResMsg());
		}

		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
		header.setResId(ResponseIdConstants.CHANGE_USERNAME_PASSWORD);

		return header;
	}

}
