package com.barclays.bmg.json.model.builder.secondauth;

import com.barclays.bmg.constants.AuthResponseCodeConstants;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.exception.BMBCustomClassCastException;
import com.barclays.bmg.json.model.auth.SQAResponseModel;
import com.barclays.bmg.json.model.billpayment.TxnSQASecondAuthRespModel;
import com.barclays.bmg.json.model.builder.auth.SQAAuthenticationJSONBldr;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.secondauth.TxnSecondAuthSQAOperationResponse;



public class TxnSQASecondAuthJSONBldr extends SQAAuthenticationJSONBldr{

	@Override
	public Object createJSONResponse(ResponseContext responseContext) {

		if(responseContext instanceof TxnSecondAuthSQAOperationResponse) {
			TxnSecondAuthSQAOperationResponse resp = (TxnSecondAuthSQAOperationResponse) responseContext;

			BMBPayload bmbPayload = new BMBPayload(createHeader(resp.getSqaResponse()));

			populatePayLoad(resp, bmbPayload);

			return bmbPayload;
		} else {
			throw new BMBCustomClassCastException("BMB Custom Class Cast Exception");
		}
	}

	@Override
	protected BMBPayloadHeader createHeader(ResponseContext response) {

		BMBPayloadHeader header = new BMBPayloadHeader();
		if(response !=null && response.isSuccess()){
			header.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
		}else{
			header.setResCde(AuthResponseCodeConstants.SECOND_AUTH_INVALID);
		}
		header.setResMsg("");
		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
		header.setResId(ResponseIdConstants.TXN_SECOND_AUTH_SQA_RESPONSE_ID);

		return header;

	}

	protected void populatePayLoad(
			TxnSecondAuthSQAOperationResponse response,
			BMBPayload bmbPayload) {
		TxnSQASecondAuthRespModel responseModel = null;
		if(response!=null && response.isSuccess()){
			responseModel = new TxnSQASecondAuthRespModel();

			super.populatePayLoad(response.getSqaResponse(), bmbPayload);
			SQAResponseModel sqaResponseModel = (SQAResponseModel)bmbPayload.getPayData();
			if(sqaResponseModel!=null){
				responseModel.setSqaQues(sqaResponseModel.getSqaQues());
			}
			responseModel.setTxnRefNo(response.getTxnRefNo());
		}
		bmbPayload.setPayData(responseModel);
	}
}
