package com.barclays.bmg.json.model.builder.accountdetails;

import com.barclays.bmg.constants.AccountConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.TdAccountDTO;
import com.barclays.bmg.exception.BMBCustomClassCastException;
import com.barclays.bmg.json.model.MaturityInstructionJSONModel;
import com.barclays.bmg.json.model.TDDetailsJSONResponseModel;
import com.barclays.bmg.json.model.builder.BMBCommonJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.accountdetails.response.TDAccountDetailsOperationResponse;

public class TDAccountDetailsJSONBldr extends BMBCommonJSONBuilder implements
		BMBJSONBuilder {

	@Override
	public Object createJSONResponse(ResponseContext responseContext) {

		if(responseContext instanceof TDAccountDetailsOperationResponse) {
			TDAccountDetailsOperationResponse response = (TDAccountDetailsOperationResponse) responseContext;
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
		header.setResId(ResponseIdConstants.TD_ACCOUNT_DETAILS_RESPONSE_ID);

		return header;
	}

	protected void populatePayload(TDAccountDetailsOperationResponse response,
			BMBPayload bmbPayload) {
		TDDetailsJSONResponseModel tdResponseModel = null;

		if (response != null && response.isSuccess()) {
			TdAccountDTO tdAccountDTO = response.getTdAccountDTO();
			tdResponseModel = new TDDetailsJSONResponseModel(response
					.getTdAccountDTO());
			tdResponseModel.getTdDtls().setActNo(
					(String) response.getContext().getValue(
							AccountConstants.CORRELATION_ID));
			MaturityInstructionJSONModel maturityInstructionJSONModel = new MaturityInstructionJSONModel();
			maturityInstructionJSONModel
					.setPri(response.getPriMatInstruction());
			maturityInstructionJSONModel.setIntrst(response
					.getIntMatInstruction());

			tdResponseModel.getTdDtls().setMatInstr(
					maturityInstructionJSONModel);
			tdResponseModel.getTdDtls().setMkdActNo(
					getMaskedTDAccountNumber(tdAccountDTO));
		}
		bmbPayload.setPayData(tdResponseModel);
	}
}
