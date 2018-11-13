package com.barclays.bmg.json.model.builder.fundtransfer.international;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.json.model.billpayment.ExternalBeneficiaryJSONModel;
import com.barclays.bmg.json.model.builder.fundtransfer.external.ExternalBeneficiaryDetailsJSONBldr;
import com.barclays.bmg.json.model.fundtransfer.ExternalBeneficiaryDetailsJSONResponseModel;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrievePayeeInfoOperationResponse;

public class KEBRBInternationalBeneficiaryDetailsJSONBldr extends
		ExternalBeneficiaryDetailsJSONBldr {

	@Override
	protected void populatePayLoad(BMBPayload bmbPayload,
			ResponseContext... responses) {

		super.populatePayLoad(bmbPayload, responses);

		RetrievePayeeInfoOperationResponse retrievePayeeInfoOperationResponse = (RetrievePayeeInfoOperationResponse) responses[0];

		BeneficiaryDTO beneficiaryDTO = retrievePayeeInfoOperationResponse
				.getBeneficiaryDTO();

		ExternalBeneficiaryJSONModel beneficiaryJSONModel = ((ExternalBeneficiaryDetailsJSONResponseModel) bmbPayload
				.getPayData()).getPayInfo();

		InternationalFundTransferCountryCheckUtility.bankCountryCodeCheck(
				beneficiaryJSONModel, beneficiaryDTO);

	}

}
