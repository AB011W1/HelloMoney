package com.barclays.bmg.json.model.builder.accountdetails;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.exception.BMBCustomClassCastException;
import com.barclays.bmg.json.model.InvestmentAccountDetailsJSONModel;
import com.barclays.bmg.json.model.builder.BMBCommonJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.accountdetails.response.BondDetailsOperationResponse;


public class BondDetailsJSONBldr extends BMBCommonJSONBuilder implements BMBJSONBuilder {

	@Override
	public Object createJSONResponse(ResponseContext responseContext) {

		if(responseContext instanceof BondDetailsOperationResponse) {
			BondDetailsOperationResponse  resp = (BondDetailsOperationResponse) responseContext;

			BMBPayload bmbPayload = new BMBPayload(createHeader(resp));

			populatePayLoad(resp, bmbPayload);

			return bmbPayload;
		} else {
			throw new BMBCustomClassCastException("BMB Custom Class Cast Exception");
		}
	}

	protected BMBPayloadHeader createHeader(BondDetailsOperationResponse  response) {

		BMBPayloadHeader header = new BMBPayloadHeader();

		if (response.isSuccess()) {
			header.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
			header.setResMsg("");
		} else {
			header.setResCde(response.getResCde());
			header.setResMsg(response.getResMsg());
		}
		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
		header.setResId(ResponseIdConstants.BOND_RESPONSE_ID);

		return header;

	}

	protected void populatePayLoad(	BondDetailsOperationResponse response, BMBPayload bmbPayload) {

		if(response.isSuccess()){
			InvestmentAccountDetailsJSONModel mfDetlsJson = new InvestmentAccountDetailsJSONModel(response.getInvestmentAccountDTOList());
			bmbPayload.setPayData(mfDetlsJson);
		}

	}

}
