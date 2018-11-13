package com.barclays.bmg.json.model.internalfundtransfer;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.CategorizedPayeeListDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.TransferFacadeDTO;
import com.barclays.bmg.json.model.BMGJSONAdapter;
import com.barclays.bmg.json.model.CasaAccountJSONModel;
import com.barclays.bmg.json.model.fundtransfer.CategorizedPayeeJSONModel;
import com.barclays.bmg.json.model.fundtransfer.PayeeJSONModel;
import com.barclays.bmg.json.response.BMBPayloadData;
import com.barclays.bmg.operation.response.internalfundtransfer.InternalFTInitOperationResponse;

public class InternalFTInitModelAdapter implements BMGJSONAdapter{

	@Override
	public BMBPayloadData adaptToJSONModel(Object obj) {

		InternalFTInitJSONResponseModel responseModel = new InternalFTInitJSONResponseModel();

		InternalFTInitOperationResponse response = (InternalFTInitOperationResponse) obj;



		if(response.isSuccess()){
			List<CategorizedPayeeListDTO> categorizedPayeeList = response.getCategorizedPayeeList();

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

		responseModel.setSrcLst(getCASAAccountList(response.getSourceAccts()));
		}
		return responseModel;
	}

	private List<CasaAccountJSONModel> getCASAAccountList(
			List<? extends CustomerAccountDTO> accounts) {

		List<CasaAccountJSONModel> casaAccountList = new ArrayList<CasaAccountJSONModel>();
		if (accounts != null) {
			for (int i = 0; i < accounts.size(); i++) {
				CASAAccountDTO account = (CASAAccountDTO) accounts.get(i);
				CasaAccountJSONModel accountJSONModel = new CasaAccountJSONModel(
						account);
				// CHECK added masking
				accountJSONModel.setMkdActNo(account
								.getBranchCode()
								+ account.getAccountNumber());
				casaAccountList.add(accountJSONModel);

			}
		}
		return casaAccountList;

	}
}
