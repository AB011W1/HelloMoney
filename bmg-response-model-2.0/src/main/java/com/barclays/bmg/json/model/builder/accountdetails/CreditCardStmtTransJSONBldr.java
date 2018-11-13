package com.barclays.bmg.json.model.builder.accountdetails;

import java.util.List;

import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.CreditCardStmtBalanceInfoDTO;
import com.barclays.bmg.exception.BMBCustomClassCastException;
import com.barclays.bmg.json.model.CreditCardStmtTransJSONModel;
import com.barclays.bmg.json.model.builder.BMBCommonJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.accountdetails.response.CreditCardStmtTransOperationResponse;

public class CreditCardStmtTransJSONBldr extends BMBCommonJSONBuilder implements BMBJSONBuilder {

    @Override
    public Object createJSONResponse(ResponseContext responseContext) {

	if (responseContext instanceof CreditCardStmtTransOperationResponse) {
	    CreditCardStmtTransOperationResponse resp = (CreditCardStmtTransOperationResponse) responseContext;

	    BMBPayload bmbPayload = new BMBPayload(createHeader(resp));
	    populatePayLoad(resp, bmbPayload);

	    return bmbPayload;
	} else {
	    throw new BMBCustomClassCastException("BMB Custom Class Cast Exception");
	}
    }

    protected BMBPayloadHeader createHeader(CreditCardStmtTransOperationResponse resp) {

	BMBPayloadHeader header = new BMBPayloadHeader();

	header.setResCde(resp.getResCde());
	header.setResMsg(resp.getResMsg());
	header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
	header.setResId(ResponseIdConstants.CREDIT_CARD_STMT_TXNS_RESPONSE_ID);

	return header;

    }

    protected void populatePayLoad(CreditCardStmtTransOperationResponse response, BMBPayload bmbPayload) {

	CreditCardStmtTransJSONModel ccStmtTransJsonModel = null;

	if (response.isSuccess()) {

	    CreditCardAccountDTO ccAccDto = response.getCreditCardAccountDTO();
	    List<CreditCardStmtBalanceInfoDTO> ccStmtBalInfo = response.getCreditCardStmtBalanceInfoDTO();

	    ccStmtTransJsonModel = new CreditCardStmtTransJSONModel(ccAccDto, ccStmtBalInfo);

	}
	bmbPayload.setPayData(ccStmtTransJsonModel);
    }

}
