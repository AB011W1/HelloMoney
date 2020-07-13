package com.barclays.bmg.json.model.builder.mobilewallet;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.service.response.MWalletValidateServiceResopnse;

/* Mobile Number  Validation */
public class MwaletPayeeValidateJSONBldr extends BMBMultipleResponseJSONBuilder{

	@Override
	public Object createMultipleJSONResponse(ResponseContext... responseContexts) {
		// TODO Auto-generated method stub
		BMBPayloadHeader bmbPayloadHeader = null;
		BMBPayload bmbPayload = null;
		for(ResponseContext response: responseContexts){
			if(response!=null && !response.isSuccess()){
				bmbPayloadHeader = createHeader(response);
				break;
			}
		}
		
		if(bmbPayloadHeader!=null){
			bmbPayload = new BMBPayload(bmbPayloadHeader);
		}else{
			bmbPayload = new BMBPayload(createHeader(responseContexts[0]));
			populatePayLoad(bmbPayload, responseContexts);
		}
		return bmbPayload;
	}
	
	
	/**
	 * The method is written for Creates the header.
	 *
	 * @param response the response
	 * @return the BMBPayloadHeader's object
	 */
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
		header.setResId(ResponseIdConstants.M_WALLET_VALIDATION);
		return header;

	}

	/**
	 * The method is written for Populate pay load.
	 *
	 * @param bmbPayload the bmb payload
	 * @param responses the responses
	 */
	protected void populatePayLoad(BMBPayload bmbPayload,
			ResponseContext... responses) {

		MwaletValidateJSONModel responseModel = new MwaletValidateJSONModel();		
		MWalletValidateServiceResopnse response = (MWalletValidateServiceResopnse) responses[0];
		responseModel.setPayeeName(response.getPayeeName());
		
		bmbPayload.setPayData(responseModel);
	}
	
	
	

}
