package com.barclays.bmg.json.model.builder.termsofuse;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.exception.BMBCustomClassCastException;
import com.barclays.bmg.json.model.TermsOfUseDetailsJSONModel;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.TermsOfUseOperationResponse;


public class TermsOfUseDetailsJSONBldr implements BMBJSONBuilder {

	@Override
	public Object createJSONResponse(ResponseContext responseContext) {

		if(responseContext instanceof TermsOfUseOperationResponse) {
			TermsOfUseOperationResponse  resp = (TermsOfUseOperationResponse) responseContext;

			BMBPayload bmbPayload = new BMBPayload(createHeader(resp));
			populatePayLoad(resp, bmbPayload);

			return bmbPayload;
		} else {
			throw new BMBCustomClassCastException("BMB Custom Class Cast Exception");
		}
	}

	protected BMBPayloadHeader createHeader(TermsOfUseOperationResponse  resp) {

		BMBPayloadHeader header = new BMBPayloadHeader();

		if(resp != null && resp.isSuccess()){
			header.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
			header.setResMsg("");
		} else if (resp != null) {
			header.setResCde(resp.getResCde());
			header.setResMsg(resp.getResMsg());
		}

		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
		header.setResId(ResponseIdConstants.TERMS_OF_USE_RESPONSE_ID);

		return header;

	}

	protected void populatePayLoad(	TermsOfUseOperationResponse response, BMBPayload bmbPayload) {

		TermsOfUseDetailsJSONModel termsOfUseRespModel = null;

		if(response.isSuccess()){
			String termsAndCond = response.getTermsAndCondition();
			termsOfUseRespModel = new TermsOfUseDetailsJSONModel(termsAndCond);
		}

		bmbPayload.setPayData(termsOfUseRespModel);

	}

}
