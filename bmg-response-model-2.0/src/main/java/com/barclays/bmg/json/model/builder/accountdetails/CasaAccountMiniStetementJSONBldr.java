package com.barclays.bmg.json.model.builder.accountdetails;

import java.util.List;

import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.AccountTrnxDTO;
import com.barclays.bmg.dto.AccountTrnxHistoryDTO;
import com.barclays.bmg.exception.BMBCustomClassCastException;
import com.barclays.bmg.json.model.CasaAccountTrnxHistoryDetailsJSONModel;
import com.barclays.bmg.json.model.builder.BMBCommonJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.accountdetails.response.CasaTransactionTrnxHistoryOperationResponse;

public class CasaAccountMiniStetementJSONBldr extends BMBCommonJSONBuilder implements BMBJSONBuilder {

    @Override
    public Object createJSONResponse(ResponseContext responseContext) {

	if (responseContext instanceof CasaTransactionTrnxHistoryOperationResponse) {
	    CasaTransactionTrnxHistoryOperationResponse resp = (CasaTransactionTrnxHistoryOperationResponse) responseContext;

	    BMBPayload bmbPayload = new BMBPayload(createHeader(resp));

	    populatePayLoad(resp, bmbPayload);

	    return bmbPayload;
	} else {
	    throw new BMBCustomClassCastException("BMB Custom Class Cast Exception");
	}
    }

    protected BMBPayloadHeader createHeader(CasaTransactionTrnxHistoryOperationResponse resp) {

	BMBPayloadHeader header = new BMBPayloadHeader();

	header.setResCde(resp.getResCde());
	header.setResMsg(resp.getResMsg());
	header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
	header.setResId(ResponseIdConstants.CASA_DETAILS_RESPONSE_ID);

	return header;

    }

    protected void populatePayLoad(CasaTransactionTrnxHistoryOperationResponse response, BMBPayload bmbPayload) {

	String maskedActNo = null;

	if (response.isSuccess()) {

	    CasaAccountTrnxHistoryDetailsJSONModel casaAccttnxJSONModel = null;
	    AccountTrnxHistoryDTO accountTrnxHistoryDTO = response.getAccountTrnxHistoryDTO();
	    List<AccountTrnxDTO> trnsactionActivityList = null;

	    if (response.getAccountTrnxHistoryDTO() != null) {
		//if (branchCodeCountryList.contains(BMBContextHolder.getContext().getBusinessId())) {
		    // TODO Check branch code
		    maskedActNo = getMaskedAccountNumber(null, accountTrnxHistoryDTO.getAccountNumber());
		/*} else {
		    maskedActNo = getMaskedAccountNumber(null, accountTrnxHistoryDTO.getAccountNumber());
		}*/
		trnsactionActivityList = response.getAccountTrnxHistoryDTO().getTrnsactionActivityList();
	    }

	    casaAccttnxJSONModel = new CasaAccountTrnxHistoryDetailsJSONModel(trnsactionActivityList);
	    casaAccttnxJSONModel.setAccountNumber(maskedActNo);

	    bmbPayload.setPayData(casaAccttnxJSONModel);
	}

    }

}
