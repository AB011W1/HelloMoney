package com.barclays.bmg.json.model.builder.statement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.json.model.CasaAccountJSONModel;
import com.barclays.bmg.json.model.billpayment.StatmentInitJsonModel;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrieveAcctListOperationResponse;
import com.barclays.bmg.operation.response.lookup.BranchLookUpOperationResponse;

public class StatmentRequestInitBldr extends BMBMultipleResponseJSONBuilder
		implements BMBJSONBuilder {



	@Override
	public Object createMultipleJSONResponse(
			ResponseContext... responseContexts) {
		BMBPayloadHeader bmbPayloadHeader = null;
		BMBPayload bmbPayload = null;
		for (ResponseContext response : responseContexts) {
			if (response != null && !response.isSuccess()) {
				bmbPayloadHeader = createHeader(response);
				break;
			}
		}

		if (bmbPayloadHeader != null) {
			bmbPayload = new BMBPayload(bmbPayloadHeader);
		} else {
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
		} else if (response != null) {
			header.setResCde(response.getResCde());
			header.setResMsg(response.getResMsg());
		}
		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
		header.setResId(ResponseIdConstants.STATMENT_REQUEST_INIT);

		return header;
	}

	protected void populatePayLoad(BMBPayload bmbPayload,
			ResponseContext... responses) {

		StatmentInitJsonModel responseModel = new StatmentInitJsonModel();

		RetrieveAcctListOperationResponse acctListOperationResponse = (RetrieveAcctListOperationResponse) responses[0];
		BranchLookUpOperationResponse branchLookUpOperationResponse = (BranchLookUpOperationResponse) responses[2];

		responseModel.setSrcLst(getCASAAccountList(acctListOperationResponse
				.getAcctList(), acctListOperationResponse,
				branchLookUpOperationResponse));

	//	StatmentInitOperationResponse  statmentInitOperationResponse = (StatmentInitOperationResponse) responses[1];


		bmbPayload.setPayData(responseModel);

	}

	private List<CasaAccountJSONModel> getCASAAccountList(
			List<? extends CustomerAccountDTO> accounts,
			ResponseContext response,
			BranchLookUpOperationResponse branchResponse) {

		List<CasaAccountJSONModel> casaAccountList = new ArrayList<CasaAccountJSONModel>();

		/* To populate branch code & branch name in Map */
		Map<String, String> brnCdeMap = new HashMap<String, String>();

		if (branchResponse != null && branchResponse.getBranchList() != null
				&& branchResponse.getBranchList().size() > 0) {

			for (int i = 0; i < branchResponse.getBranchList().size(); i++) {

				brnCdeMap.put(branchResponse.getBranchList().get(i)
						.getBranchCode(), branchResponse
						.getBranchList().get(i).getBranchName());
			}

		}

		/* ---------- */

		if (accounts != null) {

			for (int i = 0; i < accounts.size(); i++) {

				CASAAccountDTO account = (CASAAccountDTO) accounts.get(i);
				CasaAccountJSONModel accountJSONModel = new CasaAccountJSONModel(
						account);

				// CHECK added masking
				/*accountJSONModel.setMkdActNo(getMaskedAccountNumber(account
						.getBranchCode(), account.getAccountNumber()));*/


				if(branchCodeCountryList.contains(BMBContextHolder.getContext().getBusinessId())){
					accountJSONModel.setMkdActNo(getMaskedAccountNumber(account.getBranchCode(), account.getAccountNumber()));
				    }
				    else {
					accountJSONModel.setMkdActNo(getMaskedAccountNumber(null, account.getAccountNumber()));
				    }
				accountJSONModel.setActNo(getCorrelationId(account
						.getAccountNumber(), response));

				if (StringUtils.isNotEmpty(account.getBranchCode())) {

					String acctBrnCdeFormatted = getFormattedBranchCode(account
							.getBranchCode());

					/*
					 * To get branch code & branch name from Map & adding into
					 * account json model
					 */
					if (!brnCdeMap.isEmpty()) {
						accountJSONModel.setBrnCde(acctBrnCdeFormatted);
						accountJSONModel.setBrnNam(brnCdeMap
								.get(acctBrnCdeFormatted));
					} else {
						accountJSONModel.setBrnCde("");
						accountJSONModel.setBrnNam("");
					}
				}

				casaAccountList.add(accountJSONModel);

			}
		}
		return casaAccountList;

	}

	}
