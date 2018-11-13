package com.barclays.bmg.json.model.builder.auth;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.constants.UpdateLanguagePrefResponseCodeConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.exception.BMBCustomClassCastException;
import com.barclays.bmg.json.model.builder.BMBCommonJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.UpdateLanguagePrefOperationResponse;

/**
 * @author BTCI
 *
 *  JSON Builder class for Update Language Pref Operation Response
 */
public class UpdateLanguagePrefJSONBuilder extends BMBCommonJSONBuilder implements BMBJSONBuilder {

	/**
	 * @param responseContext
	 * @return Object
	 */
	public Object createJSONResponse(ResponseContext responseContext) {

		if(responseContext instanceof UpdateLanguagePrefOperationResponse) {
			UpdateLanguagePrefOperationResponse response = (UpdateLanguagePrefOperationResponse) responseContext;

			BMBPayload bmbPayload = new BMBPayload(createHeader(response));

			populatePayLoad(response, bmbPayload);

			return bmbPayload;
		} else {
			throw new BMBCustomClassCastException("BMB Custom Class Cast Exception");
		}
	}

	/**
	 * @param response
	 * @param bmbPayload
	 */
	private void populatePayLoad(UpdateLanguagePrefOperationResponse response,
			BMBPayload bmbPayload) {

		bmbPayload.setPayData(null);

	}

	/**
	 * @param response
	 * @return BMBPayloadHeader
	 */
	private BMBPayloadHeader createHeader(
			UpdateLanguagePrefOperationResponse response) {
		BMBPayloadHeader header = new BMBPayloadHeader();
		if (response != null) {
			if (response.isSuccess()) {
				header.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
				header
						.setResMsg(UpdateLanguagePrefResponseCodeConstants.UPDATE_LANGUAGE_PREF_SUCCESS_MESSAGE);
			} else {
				header.setResCde(response.getResCde());
				header.setResMsg(response.getResMsg());
			}
		}

		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
		header.setResId(ResponseIdConstants.UPDATE_LANGUAGE_PREF);

		return header;
	}

}
