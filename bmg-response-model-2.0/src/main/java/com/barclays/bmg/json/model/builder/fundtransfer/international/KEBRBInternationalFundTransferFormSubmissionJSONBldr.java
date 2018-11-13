package com.barclays.bmg.json.model.builder.fundtransfer.international;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.json.model.billpayment.ExternalBeneficiaryJSONModel;
import com.barclays.bmg.json.model.builder.fundtransfer.external.ExternalFundTransferFormSubmissionJSONBldr;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.operation.response.fundtransfer.external.ExternalFTFormSubmissionOperationResponse;

public class KEBRBInternationalFundTransferFormSubmissionJSONBldr extends
		ExternalFundTransferFormSubmissionJSONBldr {

	@Override
	protected void populatePayLoad(BMBPayload bmbPayload,
			ResponseContext... responses) {

		super.populatePayLoad(bmbPayload, responses);

		ExternalFTFormSubmissionOperationResponse response = (ExternalFTFormSubmissionOperationResponse) responses[0];

		BeneficiaryDTO beneficiaryDTO = response.getBeneficiaryDTO();

		ExternalBeneficiaryJSONModel beneficiaryJSONModel = new ExternalBeneficiaryJSONModel(
				beneficiaryDTO);

		InternationalFundTransferCountryCheckUtility.bankCountryCodeCheck(
				beneficiaryJSONModel, beneficiaryDTO);

	}

}
