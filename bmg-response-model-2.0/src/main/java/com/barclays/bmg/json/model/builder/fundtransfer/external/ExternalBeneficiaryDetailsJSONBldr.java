package com.barclays.bmg.json.model.builder.fundtransfer.external;

import java.util.List;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.json.model.billpayment.ExternalBankJSONModel;
import com.barclays.bmg.json.model.billpayment.ExternalBeneficiaryJSONModel;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.model.fundtransfer.ExternalBeneficiaryDetailsJSONResponseModel;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrievePayeeInfoOperationResponse;

public class ExternalBeneficiaryDetailsJSONBldr extends BMBMultipleResponseJSONBuilder implements BMBJSONBuilder {

	  private static final String CMN_COUNTRY = "CMN_COUNTRY";
	@Override
	public Object createMultipleJSONResponse(
			ResponseContext... responseContexts) {
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
		header.setResId(ResponseIdConstants.EXTERNAL_FT_PAYEE_DETAILS);

		return header;
	}

	protected void populatePayLoad(BMBPayload bmbPayload,
			ResponseContext... responses
			) {
		ExternalBeneficiaryDetailsJSONResponseModel responseModel =
				new ExternalBeneficiaryDetailsJSONResponseModel();

		RetrievePayeeInfoOperationResponse retrievePayeeInfoOperationResponse = (RetrievePayeeInfoOperationResponse)responses[0];


		BeneficiaryDTO beneficiaryDTO = retrievePayeeInfoOperationResponse.getBeneficiaryDTO();

		ExternalBeneficiaryJSONModel beneficiaryJSONModel = new ExternalBeneficiaryJSONModel(beneficiaryDTO);
		Context context = retrievePayeeInfoOperationResponse.getContext();
		beneficiaryJSONModel.setCntry(getListValueLabel(context, CMN_COUNTRY, beneficiaryJSONModel.getCntry()));
		resolveCountry(beneficiaryJSONModel.getBeneBank(),context);
		resolveCountryList(beneficiaryJSONModel.getIntBankLst(), context);
		beneficiaryJSONModel.setMkdActNo(beneficiaryDTO.getDestinationAccountNumber());
		beneficiaryJSONModel.setBeneNam(beneficiaryDTO.getBeneficiaryName());
		responseModel.setPayInfo(beneficiaryJSONModel);
		bmbPayload.setPayData(responseModel);
	}

	private void resolveCountryList(List<ExternalBankJSONModel> banks, Context context){
		if(banks!=null && !banks.isEmpty()){
			for(ExternalBankJSONModel bank : banks){
				resolveCountry(bank, context);
			}
		}
	}

	private void resolveCountry(ExternalBankJSONModel bank, Context context){
		if(bank!=null){
			bank.setCntry(getListValueLabel(context, CMN_COUNTRY,bank.getCntry()));
		}
	}

}
