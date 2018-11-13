package com.barclays.bmg.json.model.builder.auth;

import com.barclays.bmg.constants.AuthResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;

public class BMBInvaidateSessJSONBuilder implements BMBJSONBuilder {

    @Override
    public Object createJSONResponse(ResponseContext responseContext) {
	BMBPayload bmbPayload = new BMBPayload(createHeader(responseContext));

	return bmbPayload;
    }

    protected BMBPayloadHeader createHeader(ResponseContext responseContext) {

	BMBPayloadHeader header = new BMBPayloadHeader();
	header.setResCde(AuthResponseCodeConstants.SESSION_EXPIRED);
	header.setResMsg("");
	header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
	header.setResId(ResponseIdConstants.SESSION_INVALIDATED);
	header.setResMsg(responseContext.getResMsg());
	return header;

    }
}
