package com.barclays.bmg.json.model.builder.auth;

import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CustomerDTO;
import com.barclays.bmg.dto.ValidateUserDTO;
import com.barclays.bmg.exception.BMBCustomClassCastException;
import com.barclays.bmg.json.model.billpayment.VerifyUserJSONModel;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.ValidateUserOperationResponse;

public class VerifyUserUserJSONBldr implements BMBJSONBuilder{

	private static final String RESPONSE_CODE = "0000";
	private static final String RESPONSE_ID = "RES5555";
	private static final String RESPONSE_ID_SUCCESS = "RES0199";


	@Override
	public Object createJSONResponse(ResponseContext responseContext) {

		if(responseContext instanceof ValidateUserOperationResponse) {
			ValidateUserOperationResponse resp = (ValidateUserOperationResponse) responseContext;

			BMBPayload bmbPayload = new BMBPayload(createHeader(resp));

			populatePayLoad(resp, bmbPayload);

			return bmbPayload;
		} else {
			throw new BMBCustomClassCastException("BMB Custom Class Cast Exception");
		}
	}

	protected BMBPayloadHeader createHeader(ValidateUserOperationResponse response) {


		BMBPayloadHeader header = new BMBPayloadHeader();
		if(response !=null && response.isSuccess()){
			header.setResId(RESPONSE_ID);
			header.setResCde(RESPONSE_CODE);
			header.setResMsg("User is valid");
		}else if (response != null){
			header.setResId(RESPONSE_ID_SUCCESS);
			header.setResCde(response.getResCde());
			header.setResMsg("User is not valid");
		}
		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
		return header;
	}


	protected void populatePayLoad(ValidateUserOperationResponse response,
			BMBPayload bmbPayload) {
		VerifyUserJSONModel payData = new VerifyUserJSONModel();
		payData.setStatus(response.getResCde());
		ValidateUserDTO userDTO = response.getValidateUserDto();
		if (userDTO != null) {
			payData.setBusinessID(response.getContext().getBusinessId());
			CustomerDTO customerDTO = userDTO.getCustomerDTO();
			payData.setPreferredLanguage(userDTO.getPreferredLanguage());
			payData.setUserId(userDTO.getUserId());
			if (customerDTO != null) {
				payData.setMobilePhone(customerDTO.getMobilePhone());
				payData.setCountryCode(customerDTO.getPostalAddresses().get(0)
						.getCountryCode());
			}
		}
		// payData.setValidationMessage(response.getResMsg());
		//System.out.println(" BMB payload populated as  payData:" + payData.toString());
		bmbPayload.setPayData(payData);
	}
}
