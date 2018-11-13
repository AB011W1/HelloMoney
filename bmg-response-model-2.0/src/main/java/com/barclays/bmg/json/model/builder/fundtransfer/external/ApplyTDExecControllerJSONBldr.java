package com.barclays.bmg.json.model.builder.fundtransfer.external;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.service.response.ApplyTDServiceResponse;

/**
 * @author E20041929
 *
 */
public class ApplyTDExecControllerJSONBldr extends
		BMBMultipleResponseJSONBuilder implements BMBJSONBuilder {

	@Override
	public Object createMultipleJSONResponse(
			ResponseContext... responseContexts) {
		BMBPayloadHeader bmbPayloadHeader = null;
		BMBPayload bmbPayload = null;

		for (ResponseContext response : responseContexts) {
			if (response != null && !response.isSuccess()) {
				bmbPayloadHeader = createHeader(response);
				break;
			}
		}

		if (bmbPayloadHeader != null) {
			bmbPayload = new BMBPayload(bmbPayloadHeader);
		} else {
			bmbPayload = new BMBPayload(createHeader(responseContexts[0]));
			populatePayLoad(bmbPayload, responseContexts);
		}

		return bmbPayload;
	}

	protected BMBPayloadHeader createHeader(ResponseContext response) {

		BMBPayloadHeader header = new BMBPayloadHeader();
		if (response != null && response.isSuccess()) {
			header.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
			header.setResMsg("");
		} else  if(response != null){
			header.setResCde(response.getResCde());
			header.setResMsg(response.getResMsg());
		}
		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
		header.setResId(ResponseIdConstants.APPLY_TD_ACCOUNT_DETAILS_RESPONSE_ID);

		return header;
	}

	protected void populatePayLoad(BMBPayload bmbPayload,
			ResponseContext... responses) {
		ApplyTDProblemResContrJSONModel applyTDResponseJsonModel = new ApplyTDProblemResContrJSONModel();

		ApplyTDServiceResponse applyTDSerResponse = (ApplyTDServiceResponse) responses[0];

		applyTDResponseJsonModel.setTxnResRefNo(applyTDSerResponse.getTransactionRefNum());
		applyTDResponseJsonModel.setTxnRefNo(applyTDSerResponse.getTransactionRefNum());
		applyTDResponseJsonModel.setTxnDtTm(applyTDSerResponse.getTransDate());





		bmbPayload.setPayData(applyTDResponseJsonModel);
	}

}
