package com.barclays.bmg.json.model.builder.accountdetails;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.exception.BMBCustomClassCastException;
import com.barclays.bmg.json.model.ApplyTDInitJSONResponseModel;
import com.barclays.bmg.json.model.CasaAccountJSONModel;
import com.barclays.bmg.json.model.builder.BMBCommonJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.accountdetails.response.ApplyTDOperationResponse;

public class ApplyTDSourceAccountDetailsJSONBldr extends BMBCommonJSONBuilder implements
		BMBJSONBuilder {

	@Override
	public Object createJSONResponse(ResponseContext responseContext) {

		if(responseContext instanceof ApplyTDOperationResponse) {
			ApplyTDOperationResponse response = (ApplyTDOperationResponse) responseContext;
			BMBPayload bmbPayload = new BMBPayload(createHeader(response));

			populatePayload(response, bmbPayload);

			return bmbPayload;
		} else {
			throw new BMBCustomClassCastException("BMB Custom Class Cast Exception");
		}

	}

	protected BMBPayloadHeader createHeader(ResponseContext response) {

		BMBPayloadHeader header = new BMBPayloadHeader();

		header.setResCde(response.getResCde());
		header.setResMsg(response.getResMsg());
		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
		header.setResId(ResponseIdConstants.APPLY_TD_ACCOUNT_DETAILS_RESPONSE_ID);

		return header;
	}

	protected void populatePayload(ApplyTDOperationResponse response,
			BMBPayload bmbPayload) {
		ApplyTDInitJSONResponseModel  applyTdResJsonModel = new ApplyTDInitJSONResponseModel();

		if (response != null && response.isSuccess()) {
			List<CasaAccountJSONModel> casaAccountList = getCASAAccountList(response.getSourceAccList(),response);
			applyTdResJsonModel.setSrcLst(casaAccountList);
		}

		bmbPayload.setPayData(applyTdResJsonModel);
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
