package com.barclays.bmg.json.model.builder.fundtransfer.external.urgent;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.CategorizedPayeeListDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.TransferFacadeDTO;
import com.barclays.bmg.json.model.CasaAccountJSONModel;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.model.fundtransfer.CategorizedPayeeJSONModel;
import com.barclays.bmg.json.model.fundtransfer.ExternalFundTransferInitJSONModel;
import com.barclays.bmg.json.model.fundtransfer.PayeeJSONModel;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.fundtransfer.external.FilterUrgentPayeeListOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrieveAcctListOperationResponse;

public class UrgentFundTransferInitJSONBldr extends BMBMultipleResponseJSONBuilder {

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
		if(response != null && response.isSuccess()){
		header.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
		header.setResMsg("");
		}else if(response != null){
			header.setResCde(response.getResCde());
			header.setResMsg(response.getResMsg());
		}
		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
		header.setResId(ResponseIdConstants.URGENT_External_Fund_Transfer_Payee_List);
		return header;

	}

	protected void populatePayLoad(BMBPayload bmbPayload,
			ResponseContext... responses) {

		ExternalFundTransferInitJSONModel responseModel = new ExternalFundTransferInitJSONModel();
		//RetrievePayeeListOperationResponse payeeListOperationResponse = (RetrievePayeeListOperationResponse)responses[0];
		FilterUrgentPayeeListOperationResponse filterUrgentPayeeListOperationResponse = (FilterUrgentPayeeListOperationResponse) responses[2];
		List<CategorizedPayeeListDTO> categorizedPayeeList = filterUrgentPayeeListOperationResponse.getUrgentPayeeList();

		for(CategorizedPayeeListDTO categorizedPayee: categorizedPayeeList){
			CategorizedPayeeJSONModel categorizedPayeeJSONModel = new CategorizedPayeeJSONModel();
			categorizedPayeeJSONModel.setPayCat(categorizedPayee.getPayeeCategory());

			List<BeneficiaryDTO> payeeList = categorizedPayee.getPayeeList();
			if(payeeList!=null){
				for(BeneficiaryDTO payee : payeeList){
					PayeeJSONModel beneficiary = new PayeeJSONModel();
					beneficiary.setDisLbl(payee.getPayeeNickname());

					TransferFacadeDTO facadeDTO = new TransferFacadeDTO();
					facadeDTO.setBeneficiary(payee);
					facadeDTO.setTransactionFacadeRoutineType(categorizedPayee.getTransactionFacadeRoutineType());

					beneficiary.setTransferFacadeDTO(facadeDTO);
					categorizedPayeeJSONModel.add(beneficiary);
				}
			}

		responseModel.addCategorizedPayeeBean(categorizedPayeeJSONModel);
		}
		RetrieveAcctListOperationResponse acctListOperationResponse = (RetrieveAcctListOperationResponse)responses[1];

		responseModel.setSrcLst(getCASAAccountList(acctListOperationResponse.getAcctList(),acctListOperationResponse));

		bmbPayload.setPayData(responseModel);
	}


	private List<CasaAccountJSONModel> getCASAAccountList(
			List<? extends CustomerAccountDTO> accounts, ResponseContext response) {

		List<CasaAccountJSONModel> casaAccountList = new ArrayList<CasaAccountJSONModel>();
		if (accounts != null) {
			for (int i = 0; i < accounts.size(); i++) {
				CASAAccountDTO account = (CASAAccountDTO) accounts.get(i);
				CasaAccountJSONModel accountJSONModel = new CasaAccountJSONModel(
						account);
				// CHECK added masking
				/*accountJSONModel.setMkdActNo(getMaskedAccountNumber(account.getBranchCode()
								, account.getAccountNumber()));*/

				  if(branchCodeCountryList.contains(BMBContextHolder.getContext().getBusinessId())){
					accountJSONModel.setMkdActNo(getMaskedAccountNumber(account.getBranchCode(), account.getAccountNumber()));
				    }
				    else {
					accountJSONModel.setMkdActNo(getMaskedAccountNumber(null, account.getAccountNumber()));
				    }

				accountJSONModel.setActNo(getCorrelationId(account.getAccountNumber(), response));
				casaAccountList.add(accountJSONModel);

			}
		}
		return casaAccountList;

	}
}
