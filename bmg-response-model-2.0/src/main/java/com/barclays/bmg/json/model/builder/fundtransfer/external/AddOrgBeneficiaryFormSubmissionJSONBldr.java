package com.barclays.bmg.json.model.builder.fundtransfer.external;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BillerDTO;
import com.barclays.bmg.dto.BillerInfoDTO;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.fundtransfer.external.AddOrgBeneficiaryOperationResponse;

/**
 * @author E20041929
 *
 */
public class AddOrgBeneficiaryFormSubmissionJSONBldr extends
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
		header.setResId(ResponseIdConstants.ADD_ORG_FORMSUBMISSION_BENEFICIARY);

		return header;
	}

	protected void populatePayLoad(BMBPayload bmbPayload,
			ResponseContext... responses) {
		AddOrgBeneficiaryJSONModel addOrgBeneficiaryJSONModel = new AddOrgBeneficiaryJSONModel();

		AddOrgBeneficiaryOperationResponse addOrgBenefFormSubmissionOpResp = (AddOrgBeneficiaryOperationResponse) responses[0];

		addOrgBeneficiaryJSONModel.setBillerList(fillBillerInfoDTOList(addOrgBeneficiaryJSONModel,
				addOrgBenefFormSubmissionOpResp));

		bmbPayload.setPayData(addOrgBeneficiaryJSONModel);
	}

	private List<BillerInfoDTO> fillBillerInfoDTOList(
			AddOrgBeneficiaryJSONModel addOrgBeneficiaryJSONModel,
			AddOrgBeneficiaryOperationResponse addOrgBenefFormSubmissionOpResp) {

		List<BillerDTO> billerList = addOrgBenefFormSubmissionOpResp.getBillerList();
		List<BillerInfoDTO> targetBillerLst = new ArrayList<BillerInfoDTO>();
		for(BillerDTO billerDto:billerList){
			BillerInfoDTO billerInfoDTO = new BillerInfoDTO();
			billerInfoDTO.setBillerCategoryId(billerDto.getBillerCategoryId());
			billerInfoDTO.setBillerCategoryName(billerDto.getBillerCategoryName());
			billerInfoDTO.setBillerId(billerDto.getBillerId());
			billerInfoDTO.setBillerName(billerDto.getBillerName());
			billerInfoDTO.setBillerRefName(billerDto.getBillerName());
			targetBillerLst.add(billerInfoDTO);
		}
		return targetBillerLst;
	}

}
