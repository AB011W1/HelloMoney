package com.barclays.bmg.json.model.builder.offer;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.exception.BMBCustomClassCastException;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.offer.DiningOfferListJSONModel;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.offer.response.DiningOfferOperationResponse;

public class DiningOffersJSONBldr implements BMBJSONBuilder {

    @Override
    public Object createJSONResponse(ResponseContext responseContext) {

	if (responseContext instanceof DiningOfferOperationResponse) {
	    DiningOfferOperationResponse resp = (DiningOfferOperationResponse) responseContext;

	    BMBPayload bmbPayload = new BMBPayload(createHeader(resp));
	    populatePayLoad(resp, bmbPayload);

	    return bmbPayload;
	} else {
	    throw new BMBCustomClassCastException("BMB Custom Class Cast Exception");
	}
    }

    protected BMBPayloadHeader createHeader(DiningOfferOperationResponse resp) {

	BMBPayloadHeader header = new BMBPayloadHeader();

	if (resp != null && resp.isSuccess()) {
	    header.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
	    header.setResMsg("");
	} else if (resp != null) {
	    header.setResCde(resp.getResCde());
	    header.setResMsg(resp.getResMsg());
	}

	header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
	// header.setResId(ResponseIdConstants.DINE_OFFER_DETLS);
	header.setResId(ResponseIdConstants.DINE_OFFERS);

	return header;

    }

    protected void populatePayLoad(DiningOfferOperationResponse response, BMBPayload bmbPayload) {

	DiningOfferListJSONModel diningOffersJson = null;

	if (response.isSuccess()) {
	    diningOffersJson = new DiningOfferListJSONModel(response.getDiningOfferList());

	    /*
	     * for (ListValueCacheDTO dto : response.getCuisineList()) { ((DiningOfferListJSONModel)
	     * diningOffersJson).getCusinLst().add(dto.getLabel()); }
	     */
	}

	bmbPayload.setPayData(diningOffersJson);

    }

}
