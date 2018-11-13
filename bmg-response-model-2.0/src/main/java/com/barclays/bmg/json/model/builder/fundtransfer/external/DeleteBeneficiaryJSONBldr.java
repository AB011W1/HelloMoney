package com.barclays.bmg.json.model.builder.fundtransfer.external;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.json.model.billpayment.AddBeneficiaryJSONModel;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.model.fundtransfer.BeneficiaryJSONResponseModel;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.fundtransfer.external.DeleteBeneficiaryOperationResponse;
import com.barclays.bmg.utils.BMGFormatUtility;
import com.barclays.bmg.utils.DateTimeUtil;

/**
 * @author BTCI
 * 
 */
public class DeleteBeneficiaryJSONBldr extends BMBMultipleResponseJSONBuilder implements BMBJSONBuilder {

    /*
     * (non-Javadoc)
     * 
     * @seecom.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder# createMultipleJSONResponse(com.barclays.bmg.context.ResponseContext[])
     */
    @Override
    public Object createMultipleJSONResponse(ResponseContext... responseContexts) {
	BMBPayloadHeader bmbPayloadHeader = null;
	BMBPayload bmbPayload = null;
	for (ResponseContext response : responseContexts) {
	    if (response != null && !response.isSuccess()) {
		bmbPayloadHeader = createHeader(response);
		break;
	    }
	}

	if (bmbPayloadHeader != null) {
	    bmbPayload = new BMBPayload(bmbPayloadHeader);
	} else {
	    bmbPayload = new BMBPayload(createHeader(responseContexts[0]));
	    populatePayLoad(bmbPayload, responseContexts);
	}

	return bmbPayload;
    }

    /**
     * @param response
     * @return BMBPayloadHeader
     */
    protected BMBPayloadHeader createHeader(ResponseContext response) {

	BMBPayloadHeader header = new BMBPayloadHeader();
	if (response != null) {
	    if (response.isSuccess()) {
		header.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
		header.setResMsg("");
	    } else {
		header.setResCde(response.getResCde());
		header.setResMsg(response.getResMsg());
	    }
	}
	header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
	header.setResId(ResponseIdConstants.DELETE_BENEFICIARY);

	return header;
    }

    /**
     * @param bmbPayload
     * @param responses
     */
    protected void populatePayLoad(BMBPayload bmbPayload, ResponseContext... responses) {
	BeneficiaryJSONResponseModel responseModel = new BeneficiaryJSONResponseModel();

	DeleteBeneficiaryOperationResponse deleteBeneficiaryOperationResponse = (DeleteBeneficiaryOperationResponse) responses[0];

	BeneficiaryDTO beneficiaryDTO = deleteBeneficiaryOperationResponse.getBeneficiaryDTO();

	AddBeneficiaryJSONModel beneficiaryJSONModel = new AddBeneficiaryJSONModel(beneficiaryDTO);
	Context context = deleteBeneficiaryOperationResponse.getContext();

	responseModel.setBeneficiaryInfo(beneficiaryJSONModel);
	responseModel.setTxnRefNo(context.getOrgRefNo());
	responseModel.setTxnDate(BMGFormatUtility.getLongDate(DateTimeUtil
		.getCurrentBusinessCalendar(deleteBeneficiaryOperationResponse.getContext()).getTime()));
	bmbPayload.setPayData(responseModel);
    }

}
