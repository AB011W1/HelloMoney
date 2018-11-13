package com.barclays.bmg.json.model.builder.accountdetails;

import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.AccountActivityListDTO;
import com.barclays.bmg.exception.BMBCustomClassCastException;
import com.barclays.bmg.json.model.CasaAccountActivityJSONBean;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.accountdetails.response.CasaTransactionActivityOperationResponse;

public class CasaTransactionActivityJSONBldr implements BMBJSONBuilder {

	@Override
	public Object createJSONResponse(ResponseContext responseContext) {

		if(responseContext instanceof CasaTransactionActivityOperationResponse ) {
			CasaTransactionActivityOperationResponse  resp = (CasaTransactionActivityOperationResponse) responseContext;

			BMBPayload bmbPayload = new BMBPayload(createHeader(resp));
			populatePayLoad(resp, bmbPayload);

			return bmbPayload;
		} else {
			throw new BMBCustomClassCastException("BMB Custom Class Cast Exception");
		}
	}

	protected BMBPayloadHeader createHeader(CasaTransactionActivityOperationResponse  resp) {

		BMBPayloadHeader header = new BMBPayloadHeader();

		header.setResCde(resp.getResCde());
		header.setResMsg(resp.getResMsg());
		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
		header.setResId(ResponseIdConstants.CASA_ACTIVITY_RESPONSE_ID);

		return header;

	}

	protected void populatePayLoad(	CasaTransactionActivityOperationResponse response, BMBPayload bmbPayload) {

		CasaAccountActivityJSONBean casaActActvJSONBean = null;

		if(response.isSuccess()){

			AccountActivityListDTO accountActivityListDTO = response.getAccountActivityListDTO();
			casaActActvJSONBean = new CasaAccountActivityJSONBean(accountActivityListDTO);

		}
		bmbPayload.setPayData(casaActActvJSONBean);

	}

}
