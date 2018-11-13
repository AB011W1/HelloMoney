package com.barclays.bmg.json.model.builder.accountdetails;

import java.util.List;

import com.barclays.bmg.constants.AccountConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.AccountActivityDTO;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.exception.BMBCustomClassCastException;
import com.barclays.bmg.json.model.CasaAccountDetailsJSONModel;
import com.barclays.bmg.json.model.builder.BMBCommonJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.accountdetails.response.CASADetailsOperationResponse;
import com.barclays.bmg.utils.BMGFormatUtility;

public class CasaAccountDetailsJSONBldr extends BMBCommonJSONBuilder implements BMBJSONBuilder {

	@Override
	public Object createJSONResponse(ResponseContext responseContext) {

		if(responseContext instanceof CASADetailsOperationResponse) {
			CASADetailsOperationResponse resp = (CASADetailsOperationResponse) responseContext;

			BMBPayload bmbPayload = new BMBPayload(createHeader(resp));

			populatePayLoad(resp, bmbPayload);

			return bmbPayload;
		} else {
			throw new BMBCustomClassCastException("BMB Custom Class Cast Exception");
		}
	}

	protected BMBPayloadHeader createHeader(CASADetailsOperationResponse resp) {

		BMBPayloadHeader header = new BMBPayloadHeader();

		header.setResCde(resp.getResCde());
		header.setResMsg(resp.getResMsg());
		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
		header.setResId(ResponseIdConstants.CASA_DETAILS_RESPONSE_ID);

		return header;

	}

    protected void populatePayLoad(CASADetailsOperationResponse response, BMBPayload bmbPayload) {

		CasaAccountDetailsJSONModel casaAccountDetailsJSONModel = null;

		if (response.isSuccess()) {

	    String actNoCorr = (String) response.getContext().getValue(AccountConstants.CORRELATION_ID);

			CASAAccountDTO accountDTO = response.getCasaAccountDTO();
			List<AccountActivityDTO> accountActivityListDTO = null;

			if (response.getAccountActivityListDTO() != null) {
		accountActivityListDTO = response.getAccountActivityListDTO().getActivityList();
			}

	    casaAccountDetailsJSONModel = new CasaAccountDetailsJSONModel(accountDTO, accountActivityListDTO);

			// masking
	    String maskedActNo = null;

	    if(branchCodeCountryList.contains(BMBContextHolder.getContext().getBusinessId())){
		maskedActNo = getMaskedAccountNumber(accountDTO.getBranchCode(), accountDTO.getAccountNumber());
	    }
	    else {
		maskedActNo = getMaskedAccountNumber(null, accountDTO.getAccountNumber());
	    }

	    (casaAccountDetailsJSONModel.getActDetls()).setMkdActNo(maskedActNo);
			(casaAccountDetailsJSONModel.getActDetls()).setActNo(actNoCorr);

			if (response.getAccountActivityListDTO() != null) {
		casaAccountDetailsJSONModel.setOpnBal(BMGFormatUtility.getFormattedAmount(response.getAccountActivityListDTO()
								.getOpeningBalanceAmount()));
		casaAccountDetailsJSONModel.setClsBal(BMGFormatUtility.getFormattedAmount(response.getAccountActivityListDTO()
								.getClosingBalanceAmount()));
			}

			bmbPayload.setPayData(casaAccountDetailsJSONModel);
		}

	}

}
