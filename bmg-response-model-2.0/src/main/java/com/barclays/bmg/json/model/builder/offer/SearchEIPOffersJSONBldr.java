package com.barclays.bmg.json.model.builder.offer;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.exception.BMBCustomClassCastException;
import com.barclays.bmg.json.model.builder.BMBCommonJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.offer.EIPOfferListJSONModel;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.offer.response.EIPOfferOperationResponse;

public class SearchEIPOffersJSONBldr extends BMBCommonJSONBuilder implements BMBJSONBuilder {

    @Override
    public Object createJSONResponse(ResponseContext responseContext) {

	if (responseContext instanceof EIPOfferOperationResponse) {
	    EIPOfferOperationResponse resp = (EIPOfferOperationResponse) responseContext;

	    BMBPayload bmbPayload = new BMBPayload(createHeader(resp));
	    populatePayLoad(resp, bmbPayload);

	    return bmbPayload;
	} else {
	    throw new BMBCustomClassCastException("BMB Custom Class Cast Exception");
	}
    }

    protected BMBPayloadHeader createHeader(EIPOfferOperationResponse resp) {

	BMBPayloadHeader header = new BMBPayloadHeader();

	if (resp != null && resp.isSuccess()) {
	    header.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
	    header.setResMsg("");
	} else if (resp != null) {
	    header.setResCde(resp.getResCde());
	    header.setResMsg(resp.getResMsg());
	}

	header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
	header.setResId(ResponseIdConstants.EIP_OFFERS_SEARCH);

	return header;

    }

    protected void populatePayLoad(EIPOfferOperationResponse response, BMBPayload bmbPayload) {

	EIPOfferListJSONModel diningOffersJson = null;

	if (response.isSuccess()) {
	    diningOffersJson = new EIPOfferListJSONModel(response.getCategoryList(), response.getEipOfferList());
	}

	bmbPayload.setPayData(diningOffersJson);

    }

}
