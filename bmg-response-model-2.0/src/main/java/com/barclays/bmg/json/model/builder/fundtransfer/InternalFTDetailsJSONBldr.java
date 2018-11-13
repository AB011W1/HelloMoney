package com.barclays.bmg.json.model.builder.fundtransfer;

import java.util.List;

import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.ListValueCacheDTO;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.model.fundtransfer.InternalFTDetailsJSONResponseModel;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrievePayeeInfoOperationResponse;

public class InternalFTDetailsJSONBldr extends BMBMultipleResponseJSONBuilder{

	private String CURRENCY_GROUP_ID	=	"CURRENCY";
	@Override
	public Object createMultipleJSONResponse(
			ResponseContext... responseContexts) {
		BMBPayloadHeader bmbPayloadHeader = null;
		BMBPayload bmbPayload = null;
		for(ResponseContext response: responseContexts){
			if(response!=null && !response.isSuccess()){
				bmbPayloadHeader = createHeader(response, ResponseIdConstants.INTERNAL_FUND_TRANSFER_DETAILS_RESPONSE_ID);
				break;
			}
		}

		if(bmbPayloadHeader!=null){
			bmbPayload = new BMBPayload(bmbPayloadHeader);
		}else{
			bmbPayload = new BMBPayload(createHeader(responseContexts[0],ResponseIdConstants.INTERNAL_FUND_TRANSFER_DETAILS_RESPONSE_ID));
			populatePayLoad(bmbPayload, responseContexts);
		}

		return bmbPayload;
	}


	protected void populatePayLoad(	BMBPayload bmbPayload,ResponseContext... responseContexts) {

		InternalFTDetailsJSONResponseModel responseModel = null;
		RetrievePayeeInfoOperationResponse retrievePayeeInfoOperationResponse = (RetrievePayeeInfoOperationResponse) responseContexts[0];
		if(retrievePayeeInfoOperationResponse !=null && retrievePayeeInfoOperationResponse.isSuccess()){
			responseModel = new InternalFTDetailsJSONResponseModel();
			CustomerAccountDTO destAcct = retrievePayeeInfoOperationResponse.getCasaAccountDTO();
			BeneficiaryDTO beneficiaryDTO = retrievePayeeInfoOperationResponse.getBeneficiaryDTO();

			//responseModel.setBeneAcct(getMaskedAccountNumber(destAcct.getBranchCode(),beneficiaryDTO.getDestinationAccountNumber()));


			if(branchCodeCountryList.contains(BMBContextHolder.getContext().getBusinessId())){
			responseModel.setBeneAcct(getMaskedAccountNumber(destAcct.getBranchCode(),beneficiaryDTO.getDestinationAccountNumber()));
			    }
			    else {
				responseModel.setBeneAcct(getMaskedAccountNumber(null,beneficiaryDTO.getDestinationAccountNumber()));
			    }

			responseModel.setBeneName(beneficiaryDTO.getBeneficiaryName());
			responseModel.setPayId(beneficiaryDTO.getPayeeId());
			responseModel.setCurr(destAcct.getCurrency());
			responseModel.setBeneBrnCde(getFormattedBranchCode(beneficiaryDTO.getDestinationBranchCode()));
			//---currency list implementation
			Context	context	=	retrievePayeeInfoOperationResponse.getContext();
			List<ListValueCacheDTO>	currLst	=	getListValueByGroup(context,CURRENCY_GROUP_ID);
			responseModel.setCurrLst(currLst);

		}
		bmbPayload.setPayData(responseModel);
	}


}
