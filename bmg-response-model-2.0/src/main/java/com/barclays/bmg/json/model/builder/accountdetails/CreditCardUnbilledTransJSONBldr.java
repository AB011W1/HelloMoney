package com.barclays.bmg.json.model.builder.accountdetails;

import java.util.List;

import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.CreditCardActivityDTO;
import com.barclays.bmg.dto.CreditCardTransactionHistoryDTO;
import com.barclays.bmg.json.model.CreditCardTransactionHistoryJSONModel;
import com.barclays.bmg.json.model.CreditCardUnbilledTransJSONBean;
import com.barclays.bmg.json.model.builder.BMBCommonJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.accountdetails.response.CreditCardUnbilledTransOperationResponse;

public class CreditCardUnbilledTransJSONBldr extends BMBCommonJSONBuilder implements BMBJSONBuilder {

    @Override
    public Object createJSONResponse(ResponseContext responseContext) {

	CreditCardUnbilledTransOperationResponse resp = (CreditCardUnbilledTransOperationResponse) responseContext;

	BMBPayload bmbPayload = new BMBPayload(createHeader(resp));
	populatePayLoad(resp, bmbPayload);

	return bmbPayload;
    }

    protected BMBPayloadHeader createHeader(CreditCardUnbilledTransOperationResponse resp) {

	BMBPayloadHeader header = new BMBPayloadHeader();

	header.setResCde(resp.getResCde());
	header.setResMsg(resp.getResMsg());
	header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
	header.setResId(ResponseIdConstants.CREDIT_CARD_ULBILD_RESPONSE_ID);

	return header;

    }

    protected void populatePayLoad(CreditCardUnbilledTransOperationResponse response, BMBPayload bmbPayload) {

	CreditCardUnbilledTransJSONBean ccDetlJson = null;

	if (response.isSuccess()) {

	    CreditCardAccountDTO ccAccDto = response.getCreditCardAccountDTO();
	    CreditCardTransactionHistoryDTO ccHistory = response.getCreditCardHistory();
	    List<CreditCardActivityDTO> activityList = response.getActivityList();

	    ccHistory.setCreditCardActivityList(activityList);

	    ccDetlJson = new CreditCardUnbilledTransJSONBean(ccAccDto, ccHistory);

	    // masking

	    if (ccDetlJson != null) {

		CreditCardTransactionHistoryJSONModel ccDetls = ccDetlJson.getCrdDetls();
		if (ccDetls != null && ccDetls.getCrdNo() != null) {
		    ccDetls.setMkdCrdNo(getMaskedCreditCardNumber(ccDetls.getCrdNo()));
		}

	    }

	}

	bmbPayload.setPayData(ccDetlJson);
    }
}
