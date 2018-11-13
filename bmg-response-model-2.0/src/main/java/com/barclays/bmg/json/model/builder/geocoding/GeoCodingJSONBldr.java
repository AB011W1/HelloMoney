package com.barclays.bmg.json.model.builder.geocoding;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.exception.BMBCustomClassCastException;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.geocoding.GecodingDetailsJSONModel;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.geocoding.response.GeoCodingOperationResponse;

public class GeoCodingJSONBldr implements BMBJSONBuilder{

	@Override
	public Object createJSONResponse(ResponseContext responseContext) {

		if(responseContext instanceof GeoCodingOperationResponse) {
			GeoCodingOperationResponse  resp = (GeoCodingOperationResponse) responseContext;

			BMBPayload bmbPayload = new BMBPayload(createHeader(resp));
			populatePayLoad(resp, bmbPayload);

			return bmbPayload;
		} else {
			throw new BMBCustomClassCastException("BMB Custom Class Cast Exception");
		}
	}

	protected BMBPayloadHeader createHeader(GeoCodingOperationResponse  resp) {

		BMBPayloadHeader header = new BMBPayloadHeader();

		if(resp != null && resp.isSuccess()){
			header.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
			header.setResMsg("");
		}else if(resp != null){
			header.setResCde(resp.getResCde());
			header.setResMsg(resp.getResMsg());
		}

		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
		header.setResId(ResponseIdConstants.GEO_CODING_RESPONSE_ID);

		return header;

	}

	protected void populatePayLoad(GeoCodingOperationResponse response, BMBPayload bmbPayload) {

		GecodingDetailsJSONModel geoCoding = null;

		if(response.isSuccess()){
						geoCoding = new GecodingDetailsJSONModel(response.getImageURLPrefix(),response.getFilteredLocations());
		}

		bmbPayload.setPayData(geoCoding);

	}

}
