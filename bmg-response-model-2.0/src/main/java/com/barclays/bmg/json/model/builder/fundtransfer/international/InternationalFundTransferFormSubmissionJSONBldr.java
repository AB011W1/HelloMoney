package com.barclays.bmg.json.model.builder.fundtransfer.international;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.json.model.builder.fundtransfer.external.ExternalFundTransferFormSubmissionJSONBldr;
import com.barclays.bmg.json.response.BMBPayloadHeader;

public class InternationalFundTransferFormSubmissionJSONBldr extends ExternalFundTransferFormSubmissionJSONBldr {

	@Override
	protected BMBPayloadHeader createHeader(ResponseContext response) {

		BMBPayloadHeader header = new BMBPayloadHeader();
		if (response != null && response.isSuccess()) {
			header.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
			header.setResMsg("");
		} else if(response != null){
			header.setResCde(response.getResCde());
			header.setResMsg(response.getResMsg());
		}
		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
		header.setResId(ResponseIdConstants.INTERNATIONAL_FUND_TRANSFER_FORM_SUBMIT);

		return header;
	}

}
