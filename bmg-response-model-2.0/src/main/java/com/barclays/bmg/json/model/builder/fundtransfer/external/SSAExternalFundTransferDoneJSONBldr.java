package com.barclays.bmg.json.model.builder.fundtransfer.external;

import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.json.model.billpayment.PaymentConfirmationJSONBean;
import com.barclays.bmg.json.model.builder.fundtransfer.own.DomesticFundTransferJSONBldr;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.fundtransfer.DomesticFundTransferExecuteOperationResponse;
import com.barclays.bmg.utils.BMGFormatUtility;

public class SSAExternalFundTransferDoneJSONBldr extends
		DomesticFundTransferJSONBldr {

	@Override
	public Object createMultipleJSONResponse(
			ResponseContext... responseContexts) {
		BMBPayloadHeader bmbPayloadHeader = null;
		BMBPayload bmbPayload = null;
		for (ResponseContext response : responseContexts) {
			if (response != null && !response.isSuccess()) {
				bmbPayloadHeader = createHeader(response,
						ResponseIdConstants.EXTERNAL_FUND_TRANSFER_DONE);
				break;
			}
		}

		if (bmbPayloadHeader != null) {
			bmbPayload = new BMBPayload(bmbPayloadHeader);
		} else {
			bmbPayload = new BMBPayload(createHeader(responseContexts[0],
					ResponseIdConstants.EXTERNAL_FUND_TRANSFER_DONE));
			populatePayLoad(bmbPayload, responseContexts);
		}

		return bmbPayload;
	}

	@Override
	protected void populatePayLoad(BMBPayload bmbPayload,
			ResponseContext... responseContexts) {
		DomesticFundTransferExecuteOperationResponse response = (DomesticFundTransferExecuteOperationResponse) responseContexts[0];
		PaymentConfirmationJSONBean paymentConfirmBean = null;

		if (response != null && response.isSuccess()) {
			paymentConfirmBean = new PaymentConfirmationJSONBean();
			paymentConfirmBean.setBemRefNo(response.getTrnRef());
			paymentConfirmBean.setTxnRefNo(response.getTrnRef());
			paymentConfirmBean.setTxnResRefNo(response.getTrnRef());
			paymentConfirmBean.setResDtTm(BMGFormatUtility.getLongDate(response
					.getTrnDate()));
			paymentConfirmBean.setTxnDtTm(BMGFormatUtility.getLongDate(response
					.getTrnDate()));
			paymentConfirmBean.setTxnMsg(response.getTxnMsg());
		}

		bmbPayload.setPayData(paymentConfirmBean);
	}

}
