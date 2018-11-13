package com.barclays.bmg.json.model.builder.auth;

import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.ValidateUserDTO;
import com.barclays.bmg.exception.BMBCustomClassCastException;
import com.barclays.bmg.json.model.billpayment.ValidateUserJSONModel;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.ValidateUserOperationResponse;

public class ValidateUserJSONBldr implements BMBJSONBuilder {
	private static final String RESID = "RES0110";

	@Override
	public Object createJSONResponse(ResponseContext responseContext) {

		if(responseContext instanceof ValidateUserOperationResponse) {
			ValidateUserOperationResponse resp = (ValidateUserOperationResponse) responseContext;

			BMBPayload bmbPayload = new BMBPayload(createHeader(resp));

			populatePayLoad(resp, bmbPayload);

			return bmbPayload;
		} else {
			throw new BMBCustomClassCastException("BMB Custom Class Cast Exception");
		}
	}

	protected BMBPayloadHeader createHeader(ValidateUserOperationResponse response) {
		BMBPayloadHeader header = new BMBPayloadHeader();
		header.setResMsg(response.getResMsg());
		header.setResCde(response.getResCde());
		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
		header.setResId(RESID);

		return header;
	}

	protected void populatePayLoad( ValidateUserOperationResponse response, BMBPayload bmbPayload) {
		ValidateUserJSONModel payData = new ValidateUserJSONModel();

		Boolean isSuccess = response.isSuccess();
		if(isSuccess != null && isSuccess) {
			ValidateUserDTO dto = response.getValidateUserDto();
			if(dto != null) {
				payData.setPrefLang(dto.getPreferredLanguage());
				payData.setUsrSta(dto.getUserStatus());
			}
		}
        //System.out.println(" BMB payload populated as  payData:"+payData.toString());
		bmbPayload.setPayData(payData);
	}
}
