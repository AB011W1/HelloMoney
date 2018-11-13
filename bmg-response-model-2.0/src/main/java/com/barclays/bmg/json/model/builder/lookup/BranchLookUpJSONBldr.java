package com.barclays.bmg.json.model.builder.lookup;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BranchLookUpDTO;
import com.barclays.bmg.exception.BMBCustomClassCastException;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.fundtransfer.BranchLookUpJSONResponseModel;
import com.barclays.bmg.json.model.lookup.BranchJSONModel;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.lookup.BranchLookUpOperationResponse;

public class BranchLookUpJSONBldr implements BMBJSONBuilder {

	@Override
	public Object createJSONResponse(ResponseContext responseContext) {

		if(responseContext instanceof BranchLookUpOperationResponse) {
			BranchLookUpOperationResponse resp = (BranchLookUpOperationResponse) responseContext;

			BMBPayload bmbPayload = new BMBPayload(createHeader(resp));

			populatePayLoad(resp, bmbPayload);
			return bmbPayload;
		} else {
			throw new BMBCustomClassCastException("BMB Custom Class Cast Exception");
		}
	}

	protected BMBPayloadHeader createHeader(BranchLookUpOperationResponse resp) {

		BMBPayloadHeader header = new BMBPayloadHeader();

		if(resp != null && resp.isSuccess()){
			header.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
			header.setResMsg("");
		}else if(resp != null){
			header.setResCde(resp.getResCde());
			header.setResMsg(resp.getResMsg());
		}
		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
		header.setResId(ResponseIdConstants.BRANCH_LOOKUP);

		return header;

	}

	void populatePayLoad(BranchLookUpOperationResponse resp,
			BMBPayload bmbPayload) {
		List<BranchLookUpDTO> list = resp.getBranchList();
		BranchLookUpJSONResponseModel branchLookUpJSONResponseModel = new BranchLookUpJSONResponseModel();

		if (list != null && list.size() > 0) {
			List<BranchJSONModel> branchJSONList = new ArrayList<BranchJSONModel>();
			for (BranchLookUpDTO branchLookUpDTO : list) {
				BranchJSONModel branchJSONModel = new BranchJSONModel();
				branchJSONModel.setBnkCde(branchLookUpDTO.getBankCode());
				branchJSONModel.setBnkNam(branchLookUpDTO.getBankName());
				branchJSONModel.setCty(branchLookUpDTO.getCityName());
				branchJSONModel.setBrnCde(branchLookUpDTO.getBranchCode());
				branchJSONModel.setBrnNam(branchLookUpDTO.getBranchName());
				branchJSONList.add(branchJSONModel);
			}
			branchLookUpJSONResponseModel.setBrnLst(branchJSONList);
		} else {
			branchLookUpJSONResponseModel.setDisLst(false);
		}

		bmbPayload.setPayData(branchLookUpJSONResponseModel);
	}

}
