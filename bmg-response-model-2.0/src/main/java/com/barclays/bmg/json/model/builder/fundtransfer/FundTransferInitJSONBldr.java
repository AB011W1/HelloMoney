package com.barclays.bmg.json.model.builder.fundtransfer;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.exception.BMBCustomClassCastException;
import com.barclays.bmg.json.model.AmountJSONModel;
import com.barclays.bmg.json.model.CasaAccountJSONModel;
import com.barclays.bmg.json.model.builder.BMBCommonJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.fundtransfer.OwnFundTransferInitJSONResponseModel;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.fundtransfer.FundTransferInitOperationResponse;
import com.barclays.bmg.utils.BMGFormatUtility;


public class FundTransferInitJSONBldr extends BMBCommonJSONBuilder implements BMBJSONBuilder {

	@Override
	public Object createJSONResponse(ResponseContext responseContext) {


		if(responseContext instanceof FundTransferInitOperationResponse) {
			FundTransferInitOperationResponse response = (FundTransferInitOperationResponse) responseContext;

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
		header.setResId(ResponseIdConstants.OWN_FUND_TRANSFER_INIT_RESPONSE_ID);

		return header;

	}

	protected void populatePayLoad(
			FundTransferInitOperationResponse response,
			BMBPayload bmbPayload) {

		OwnFundTransferInitJSONResponseModel responseModel = null;

		if(response != null && response.isSuccess()){
			responseModel = new OwnFundTransferInitJSONResponseModel();
			responseModel.setSrcLst(getCASAAccountList(response.getSourceAccountList(),response));
			responseModel.setPayLst(getCASAAccountList(response.getDestAccountList(),response));
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
			List<? extends CustomerAccountDTO> accounts,ResponseContext response) {

		List<CasaAccountJSONModel> casaAccountList = new ArrayList<CasaAccountJSONModel>();
		if (accounts != null) {
			for (int i = 0; i < accounts.size(); i++) {
				CASAAccountDTO account = (CASAAccountDTO) accounts.get(i);
				CasaAccountJSONModel accountJSONModel = new CasaAccountJSONModel(
						account);
				// CHECK added masking
				/*accountJSONModel.setMkdActNo(getMaskedAccountNumber(account.getBranchCode(),
																		account.getAccountNumber()));


				*/

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
