package com.barclays.bmg.json.model.builder.USSDAuthentication;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.exception.BMBCustomClassCastException;
import com.barclays.bmg.json.model.UssdUpdateRecordsJSONModel;
import com.barclays.bmg.json.model.builder.BMBCommonJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.USSDAuthentication.AuthenticateChangeOperationResponse;

/**
 * The Class is a Json Response Builder for USSD Sybrin Update Records service
 *
 */
public class UssdUpdateRecordsJSONBldr extends BMBCommonJSONBuilder implements
		BMBJSONBuilder {

	@Override
	public Object createJSONResponse(ResponseContext responseContext) {
		if (responseContext instanceof AuthenticateChangeOperationResponse) {
			AuthenticateChangeOperationResponse response = (AuthenticateChangeOperationResponse) responseContext;

			BMBPayload bmbPayload = new BMBPayload(createHeader(response));

			populatePayLoad(response, bmbPayload);

			return bmbPayload;
		} else {
			throw new BMBCustomClassCastException(
					"BMB Custom Class Cast Exception");
		}
	}

	/**
	 * This method createHeader has the purpose to create header for JSON
	 * response.
	 *
	 * @param AuthenticateChangeOperationResponse
	 * @return BMBPayloadHeader
	 */
	protected BMBPayloadHeader createHeader(
			AuthenticateChangeOperationResponse response) {

		BMBPayloadHeader header = new BMBPayloadHeader();
		if (response != null && response.isSuccess()) {
			header.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
			header.setResMsg(response.getResMsg());

		} else if (response != null && !response.isSuccess()) {
			header.setResCde(response.getResCde());
			header.setResMsg(response.getResMsg());
		}
		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);

		return header;
	}

	/**
	 * This method populatePayLoad has the purpose to create data for JSON
	 * response.
	 *
	 * @param AuthenticateChangeOperationResponse
	 * @param BMBPayload
	 * @return void
	 */
	protected void populatePayLoad(
			AuthenticateChangeOperationResponse authenticateChangeOperationResponse,
			BMBPayload bmbPayload) {

		UssdUpdateRecordsJSONModel responseModel = new UssdUpdateRecordsJSONModel();

		responseModel.setServiceResponse(authenticateChangeOperationResponse
				.getServiceResponse());
		responseModel.setTxnDt(authenticateChangeOperationResponse.getTxnDt());
		bmbPayload.setPayData(responseModel);

	}

}
