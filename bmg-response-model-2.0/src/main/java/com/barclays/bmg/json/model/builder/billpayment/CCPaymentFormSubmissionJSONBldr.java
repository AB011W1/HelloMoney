package com.barclays.bmg.json.model.builder.billpayment;

import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.json.model.billpayment.PaymentTransferJSONBean;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.fundtransfer.external.GetSelectedAccountOperationResponse;

public class CCPaymentFormSubmissionJSONBldr extends BMBMultipleResponseJSONBuilder implements BMBJSONBuilder {

    /**
     * @param payeeType
     * @return Get the response id as per payee type
     */
    private String getResponseId(String payeeType) {

	String resId = ResponseIdConstants.CCP_PAYMENT_TRANSFER_FORM_SUBMIT_RESPONSE_ID;
	return resId;
    }

    protected void populatePayLoad(BMBPayload bmbPayload, ResponseContext... responseContexts) {
	PaymentTransferJSONBean paymentTransferJSONBean = null;
	GetSelectedAccountOperationResponse selSourceAcctOpResp = (GetSelectedAccountOperationResponse) responseContexts[0];
	Context context = selSourceAcctOpResp.getContext();
	paymentTransferJSONBean = new PaymentTransferJSONBean();
	paymentTransferJSONBean.setTxnRefNo(context.getOrgRefNo());

	bmbPayload.setPayData(paymentTransferJSONBean);
    }

    @Override
    public Object createMultipleJSONResponse(ResponseContext... responseContexts) {
	BMBPayloadHeader bmbPayloadHeader = null;
	BMBPayload bmbPayload = null;
	for (ResponseContext response : responseContexts) {
	    if (response != null && !response.isSuccess()) {
		bmbPayloadHeader = createHeader(response, getResponseId(response.getTxnTyp()));
		break;
	    }
	}

	if (bmbPayloadHeader != null) {
	    bmbPayload = new BMBPayload(bmbPayloadHeader);
	} else {
	    ResponseContext response = responseContexts[0];
	    bmbPayload = new BMBPayload(createHeader(response, getResponseId(response.getTxnTyp())));
	    populatePayLoad(bmbPayload, responseContexts);
	}

	return bmbPayload;
    }

}
