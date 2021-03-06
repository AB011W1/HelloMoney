package com.barclays.bmg.json.model.builder.accountdetails;

import java.util.List;

import com.barclays.bmg.constants.AccountConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.CreditCardStmtPointsInfoDTO;
import com.barclays.bmg.dto.CreditCardTransactionHistoryDTO;
import com.barclays.bmg.exception.BMBCustomClassCastException;
import com.barclays.bmg.json.model.CreditCardAccountDetailsJSONModel;
import com.barclays.bmg.json.model.CreditCardTransactionHistoryJSONModel;
import com.barclays.bmg.json.model.builder.BMBCommonJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.accountdetails.response.CreditCardDetailsOperationResponse;

public class CreditCardDetailsJSONBldr extends BMBCommonJSONBuilder implements
		BMBJSONBuilder {

	@Override
	public Object createJSONResponse(ResponseContext responseContext) {

		if(responseContext instanceof CreditCardDetailsOperationResponse) {
			CreditCardDetailsOperationResponse resp = (CreditCardDetailsOperationResponse) responseContext;

			BMBPayload bmbPayload = new BMBPayload(createHeader(resp));
			populatePayLoad(resp, bmbPayload);

			return bmbPayload;
		} else {
			throw new BMBCustomClassCastException("BMB Custom Class Cast Exception");
		}
	}

	protected BMBPayloadHeader createHeader(
			CreditCardDetailsOperationResponse resp) {

		BMBPayloadHeader header = new BMBPayloadHeader();

		header.setResCde(resp.getResCde());
		header.setResMsg(resp.getResMsg());
		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
		header.setResId(ResponseIdConstants.CREDIT_CARD_DETAILS_RESPONSE_ID);

		return header;

	}

	protected void populatePayLoad(CreditCardDetailsOperationResponse response,
			BMBPayload bmbPayload) {

		CreditCardAccountDetailsJSONModel ccDetlJsonModel = null;

		if (response.isSuccess()) {

			String corrActNo = (String) response.getContext().getValue(
					AccountConstants.CORRELATION_ID);

			CreditCardAccountDTO ccAccDto = response.getCreditCardAccountDTO();
			CreditCardStmtPointsInfoDTO rewardPoints = response
					.getCreditCardStmtPointsInfo();
			CreditCardTransactionHistoryDTO primaryCardHistory = response
					.getPrimaryCardHistory();
			List<CreditCardTransactionHistoryDTO> replCardGroupedHistoryList = response
					.getReplacementCardGroupedHistoryList();
			List<CreditCardTransactionHistoryDTO> supplCardGroupedHistoryList = response
					.getSupplimentryCardGroupedHistoryList();

			ccDetlJsonModel = new CreditCardAccountDetailsJSONModel(ccAccDto,
					rewardPoints, primaryCardHistory,
					replCardGroupedHistoryList, supplCardGroupedHistoryList);

			// masking
			if (ccDetlJsonModel.getActDetls() != null) {
				ccDetlJsonModel.getActDetls().setActNo(corrActNo);

				/*ccDetlJsonModel.getActDetls().setMkdActNo(
						getMaskedAccountNumber(ccAccDto.getBranchCode(),
								ccAccDto.getAccountNumber()));*/




				if(branchCodeCountryList.contains(BMBContextHolder.getContext().getBusinessId())){
				ccDetlJsonModel.getActDetls().setMkdActNo(
						getMaskedAccountNumber(ccAccDto.getBranchCode(),
								ccAccDto.getAccountNumber()));
				    }
				    else {

					ccDetlJsonModel.getActDetls().setMkdActNo(
						getMaskedAccountNumber(null,
								ccAccDto.getAccountNumber()));
				    }



				ccDetlJsonModel.getActDetls().setMkdCrdNo(
						getMaskedCardNumber(ccDetlJsonModel.getActDetls()
								.getCrdNo()));
			}

			CreditCardTransactionHistoryJSONModel primaryCardJson = ccDetlJsonModel
					.getPriCrdDetls();
			if (primaryCardJson != null) {
				primaryCardJson.setMkdCrdNo(getMaskedCardNumber(primaryCardJson
						.getCrdNo()));
			}

			List<CreditCardTransactionHistoryJSONModel> replCardJsonList = ccDetlJsonModel
					.getReplCrdDetls();
			if (replCardJsonList != null) {
				for (CreditCardTransactionHistoryJSONModel replCardJson : replCardJsonList) {
					replCardJson.setMkdCrdNo(getMaskedCardNumber(replCardJson
							.getCrdNo()));
				}
			}

			List<CreditCardTransactionHistoryJSONModel> supplCardJsonList = ccDetlJsonModel
					.getSuplCrdDetls();
			if (supplCardJsonList != null) {
				for (CreditCardTransactionHistoryJSONModel supplCardJson : supplCardJsonList) {
					supplCardJson.setMkdCrdNo(getMaskedCardNumber(supplCardJson
							.getCrdNo()));
				}
			}

		}

		bmbPayload.setPayData(ccDetlJsonModel);
	}

}
