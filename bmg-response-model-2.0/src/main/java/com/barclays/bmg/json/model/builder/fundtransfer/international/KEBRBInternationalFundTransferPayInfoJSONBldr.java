package com.barclays.bmg.json.model.builder.fundtransfer.international;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.json.model.billpayment.ExternalBeneficiaryJSONModel;
import com.barclays.bmg.json.model.builder.fundtransfer.external.ExternalFundTransferPayInfoJSONBldr;
import com.barclays.bmg.json.model.fundtransfer.ExternalFTPayInfoJSONResponseModel;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrievePayeeInfoOperationResponse;

public class KEBRBInternationalFundTransferPayInfoJSONBldr extends
		ExternalFundTransferPayInfoJSONBldr {

	@Override
	protected void populatePayLoad(BMBPayload bmbPayload,
			ResponseContext... responses) {

		super.populatePayLoad(bmbPayload, responses);

		RetrievePayeeInfoOperationResponse retrievePayeeInfoOperationResponse = (RetrievePayeeInfoOperationResponse) responses[1];

		BeneficiaryDTO beneficiaryDTO = retrievePayeeInfoOperationResponse
				.getBeneficiaryDTO();

		ExternalBeneficiaryJSONModel beneficiaryJSONModel = ((ExternalFTPayInfoJSONResponseModel) bmbPayload
				.getPayData()).getPayInfo();

		InternationalFundTransferCountryCheckUtility.bankCountryCodeCheck(
				beneficiaryJSONModel, beneficiaryDTO);

	}

}
