package com.barclays.bmg.json.model.builder.offer;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.exception.BMBCustomClassCastException;
import com.barclays.bmg.json.model.builder.BMBCommonJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.offer.EIPOfferDetailJSONModel;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.offer.response.EIPOfferDetailOperationResponse;

public class EIPOfferDetailsJSONBldr extends BMBCommonJSONBuilder implements
		BMBJSONBuilder {


	@Override
	public Object createJSONResponse(ResponseContext responseContext) {

		if(responseContext instanceof EIPOfferDetailOperationResponse) {
			EIPOfferDetailOperationResponse resp = (EIPOfferDetailOperationResponse) responseContext;

			BMBPayload bmbPayload = new BMBPayload(createHeader(resp));
			populatePayLoad(resp, bmbPayload);

			return bmbPayload;
		} else {
			throw new BMBCustomClassCastException("BMB Custom Class Cast Exception");
		}
	}

	protected BMBPayloadHeader createHeader(EIPOfferDetailOperationResponse  resp) {

		BMBPayloadHeader header = new BMBPayloadHeader();

		if(resp != null && resp.isSuccess()){
			header.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
			header.setResMsg("");
		}else if(resp != null){
			header.setResCde(resp.getResCde());
			header.setResMsg(resp.getResMsg());
		}

		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
		header.setResId(ResponseIdConstants.EIP_OFFER_DETLS);

		return header;

	}

	protected void populatePayLoad(EIPOfferDetailOperationResponse response, BMBPayload bmbPayload) {

		EIPOfferDetailJSONModel diningOffersJson = null;

		if(response.isSuccess()){
			diningOffersJson = new EIPOfferDetailJSONModel(response.getEipOffer());
		}

		bmbPayload.setPayData(diningOffersJson);

	}

}
