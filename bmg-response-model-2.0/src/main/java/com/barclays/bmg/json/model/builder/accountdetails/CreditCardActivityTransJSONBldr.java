package com.barclays.bmg.json.model.builder.accountdetails;

import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.CreditCardStmtBalanceInfoDTO;
import com.barclays.bmg.exception.BMBCustomClassCastException;
import com.barclays.bmg.json.model.CreditCardStmtBalanceInfoJSONModel;
import com.barclays.bmg.json.model.builder.BMBCommonJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.service.accountdetails.response.CreditCardTransActivityServiceResponse;

public class CreditCardActivityTransJSONBldr extends BMBCommonJSONBuilder implements BMBJSONBuilder {

	@Override
	public Object createJSONResponse(ResponseContext responseContext) {
		// TODO Auto-generated method stub

		if (responseContext instanceof CreditCardTransActivityServiceResponse) {
			CreditCardTransActivityServiceResponse resp = (CreditCardTransActivityServiceResponse) responseContext;

			BMBPayload bmbPayload = new BMBPayload(createHeader(resp));
			populatePayLoad(resp, bmbPayload);

			return bmbPayload;
		} else {
			throw new BMBCustomClassCastException("BMB Custom Class Cast Exception");
		}
	}

	protected BMBPayloadHeader createHeader(CreditCardTransActivityServiceResponse resp) {

		BMBPayloadHeader header = new BMBPayloadHeader();

		header.setResCde(resp.getResCde());
		header.setResMsg(resp.getResMsg());
		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
		header.setResId(ResponseIdConstants.CREDIT_CARD_STMT_TXNS_RESPONSE_ID);

		return header;

	}

	protected void populatePayLoad(CreditCardTransActivityServiceResponse response, BMBPayload bmbPayload) {

		CreditCardStmtBalanceInfoJSONModel ccStmtTransJsonModel = null;
		CreditCardStmtBalanceInfoDTO ccStmtBalInfo = new CreditCardStmtBalanceInfoDTO();
		if (response.isSuccess()) {
			if(null != response.getCreditCardTransactionHistoryListDTO() && null != response.getCreditCardTransactionHistoryListDTO().getBalanceInfo()) {
				ccStmtBalInfo = response.getCreditCardTransactionHistoryListDTO().getBalanceInfo();
			}

			ccStmtTransJsonModel = new CreditCardStmtBalanceInfoJSONModel(ccStmtBalInfo, response.getCurrency());

		}
		bmbPayload.setPayData(ccStmtTransJsonModel);
	}

}