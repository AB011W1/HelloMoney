package com.barclays.bmg.json.model.builder.fundtransfer.international;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.json.model.builder.fundtransfer.external.ExternalFundTransferInitJSONBldr;
import com.barclays.bmg.json.response.BMBPayloadHeader;

public class InternationalFundTransferInitJSONBldr extends ExternalFundTransferInitJSONBldr {

	@Override
	protected BMBPayloadHeader createHeader(ResponseContext response) {

		BMBPayloadHeader header = new BMBPayloadHeader();
		if(response != null && response.isSuccess()){
		header.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
		header.setResMsg("");
		}else if(response != null){
			header.setResCde(response.getResCde());
			header.setResMsg(response.getResMsg());
		}
		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
		header.setResId(ResponseIdConstants.INTERNATIONAL_Fund_Transfer_Payee_List);
		return header;

	}
}
