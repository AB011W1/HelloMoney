package com.barclays.bmg.json.model.builder.offer;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.exception.BMBCustomClassCastException;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.offer.DiningOfferDetailsJSONModel;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.offer.response.DiningOfferDetailsOperationResponse;

public class DiningOfferDetailsJSONBldr  implements BMBJSONBuilder {


	@Override
	public Object createJSONResponse(ResponseContext responseContext) {

		if(responseContext instanceof DiningOfferDetailsOperationResponse) {
			DiningOfferDetailsOperationResponse resp = (DiningOfferDetailsOperationResponse) responseContext;

			BMBPayload bmbPayload = new BMBPayload(createHeader(resp));
			populatePayLoad(resp, bmbPayload);

			return bmbPayload;
		} else {
			throw new BMBCustomClassCastException("BMB Custom Class Cast Exception");
		}
	}

	protected BMBPayloadHeader createHeader(DiningOfferDetailsOperationResponse  resp) {

		BMBPayloadHeader header = new BMBPayloadHeader();

		if(resp != null && resp.isSuccess()){
			header.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
			header.setResMsg("");
		}else if(resp != null){
			header.setResCde(resp.getResCde());
			header.setResMsg(resp.getResMsg());
		}

		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
		//header.setResId(ResponseIdConstants.DINE_OFFERS);
		header.setResId(ResponseIdConstants.DINE_OFFER_DETLS);

		return header;

	}

	protected void populatePayLoad(DiningOfferDetailsOperationResponse response, BMBPayload bmbPayload) {

		DiningOfferDetailsJSONModel diningOfferDetls = null;

		if(response.isSuccess()){
			diningOfferDetls = new DiningOfferDetailsJSONModel(response.getDiningOffer());
		}

		bmbPayload.setPayData(diningOfferDetls);

	}

}