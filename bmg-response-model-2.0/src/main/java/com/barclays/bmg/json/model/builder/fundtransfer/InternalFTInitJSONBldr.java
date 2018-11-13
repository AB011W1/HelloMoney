package com.barclays.bmg.json.model.builder.fundtransfer;

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
import com.barclays.bmg.exception.BMBCustomClassCastException;
import com.barclays.bmg.json.model.AmountJSONModel;
import com.barclays.bmg.json.model.CasaAccountJSONModel;
import com.barclays.bmg.json.model.builder.BMBCommonJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.fundtransfer.CategorizedPayeeJSONModel;
import com.barclays.bmg.json.model.fundtransfer.PayeeJSONModel;
import com.barclays.bmg.json.model.internalfundtransfer.InternalFTInitJSONResponseModel;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.internalfundtransfer.InternalFTInitOperationResponse;
import com.barclays.bmg.utils.BMGFormatUtility;

public class InternalFTInitJSONBldr extends BMBCommonJSONBuilder implements BMBJSONBuilder {

	@Override
	public Object createJSONResponse(ResponseContext responseContext) {

		if(responseContext instanceof InternalFTInitOperationResponse) {
			InternalFTInitOperationResponse response = (InternalFTInitOperationResponse) responseContext;

			BMBPayload bmbPayload = new BMBPayload(createHeader(response));

			populatePayLoad(response, bmbPayload);

			return bmbPayload;
		} else {
			throw new BMBCustomClassCastException("BMB Custom Class Cast Exception");
		}
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
		header.setResId(ResponseIdConstants.INTERNAL_FUND_TRANSFER_INIT_RESPONSE_ID);

		return header;

	}

	protected void populatePayLoad(
			InternalFTInitOperationResponse response,
			BMBPayload bmbPayload) {

		InternalFTInitJSONResponseModel responseModel = null;

		if(response != null && response.isSuccess()){
			responseModel = new InternalFTInitJSONResponseModel();
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
			responseModel.setSrcLst(getCASAAccountList(response.getSourceAccts(),response));
			AmountJSONModel txnLmt = null;
			if(response.getTxnLimit()!=null){
				txnLmt = new AmountJSONModel();
				txnLmt.setAmt(BMGFormatUtility.getFormattedAmount(response.getTxnLimit()));
				txnLmt.setCurr(response.getContext().getLocalCurrency());
			}
			responseModel.setTxnLmt(txnLmt);
		}
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
