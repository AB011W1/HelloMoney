package com.barclays.bmg.mvc.command.billpayment;


import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.BaseJSONResponseModel;

public class AddOrgBeneficiaryJSONBuilder extends
		BMBMultipleResponseJSONBuilder {

	@Override
	public Object createMultipleJSONResponse(ResponseContext... responseContexts) {
		BaseJSONResponseModel responseModel = null;
		for (ResponseContext response : responseContexts) {
			if (response != null && !response.isSuccess()) {
				responseModel = createRel1ErrorResponseModel(response);
				break;
			}
		}
		AddOrgBeneficiaryJSONModel billerRegJSONModel = new AddOrgBeneficiaryJSONModel();

//		if (responseModel == null) {
//
//			AddOrgBeneficiaryOperationResponse billerRegistrationOpResponse = (AddOrgBeneficiaryOperationResponse) responseContexts[0];
//
//
//			}

			// if(!alreadyAdded){
			responseModel = billerRegJSONModel;


		return responseModel;
	}

}
