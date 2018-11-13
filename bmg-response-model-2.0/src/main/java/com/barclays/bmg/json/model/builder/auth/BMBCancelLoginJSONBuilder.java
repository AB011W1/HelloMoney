package com.barclays.bmg.json.model.builder.auth;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;

public class BMBCancelLoginJSONBuilder implements BMBJSONBuilder {

	@Override
	public Object createJSONResponse(ResponseContext responseContext) {
		BMBPayload bmbPayload = new BMBPayload(createHeader(responseContext));

		return bmbPayload;
	}

	protected BMBPayloadHeader createHeader(ResponseContext responseContext) {

		BMBPayloadHeader header = new BMBPayloadHeader();
		header.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
		header.setResMsg("");
		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
		header.setResId(ResponseIdConstants.CANCEL_LOGIN);

		return header;

	}

}
