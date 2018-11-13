package com.barclays.bmg.json.model.builder.fundtransfer.external;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BillerDTO;
import com.barclays.bmg.dto.BillerInfoDTO;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.fundtransfer.external.AddOrgValidationOperationResponse;

/**
 * @author E20041929
 *
 */
public class AddOrgBeneficiaryFormValidationJSONBldr extends
		BMBMultipleResponseJSONBuilder implements BMBJSONBuilder {

	@Override
	public Object createMultipleJSONResponse(
			ResponseContext... responseContexts) {
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

	protected BMBPayloadHeader createHeader(ResponseContext response) {

		BMBPayloadHeader header = new BMBPayloadHeader();
		if (response != null && response.isSuccess()) {
			header.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
			header.setResMsg("");
		} else  if(response != null){
			header.setResCde(response.getResCde());
			header.setResMsg(response.getResMsg());
		}
		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
		header
				.setResId(ResponseIdConstants.ADD_ORG_VALIDATIONCONTROLLER_BENEFICIARY);

		return header;
	}

	protected void populatePayLoad(BMBPayload bmbPayload,
			ResponseContext... responses) {
		AddOrgBeneficiaryValidationConfirmJSONModel confirmAfterValidationJsonModel = new AddOrgBeneficiaryValidationConfirmJSONModel();
		AddOrgValidationOperationResponse addorgValidationResponse = (AddOrgValidationOperationResponse) responses[0];

		confirmAfterValidationJsonModel.setOrgRefNo(addorgValidationResponse.getOrgRefNo());
		confirmAfterValidationJsonModel.setPayeNickName(addorgValidationResponse.getPayeNickName());

		BillerDTO billerDTO = addorgValidationResponse.getBillerDTO();

		BillerInfoDTO billerInfoDTO = new BillerInfoDTO();
		billerInfoDTO.setBillerCategoryId(billerDTO.getBillerCategoryId());
		billerInfoDTO.setBillerCategoryName(billerDTO.getBillerCategoryName());
		billerInfoDTO.setBillerId(billerDTO.getBillerId());
		billerInfoDTO.setBillerName(billerDTO.getBillerName());
		billerInfoDTO.setBillerRefName(billerDTO.getBillerName());
 		confirmAfterValidationJsonModel.setBillerDTO(billerInfoDTO);

		bmbPayload.setPayData(confirmAfterValidationJsonModel);
	}

}
