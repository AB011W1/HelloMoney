package com.barclays.bmg.json.model.builder.lookup;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.AtmDTO;
import com.barclays.bmg.dto.BranchDTO;
import com.barclays.bmg.exception.BMBCustomClassCastException;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.fundtransfer.AtmBranchJSONResponseModel;
import com.barclays.bmg.json.model.lookup.ATMVOJSONModel;
import com.barclays.bmg.json.model.lookup.BranchVOJSONModel;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.mvc.operation.locator.response.AtmBranchLocatorOperationResponse;

public class AtmBranchJSONBldr implements BMBJSONBuilder {

    @Override
    public Object createJSONResponse(ResponseContext responseContext) {

	if (responseContext instanceof AtmBranchLocatorOperationResponse) {
	    AtmBranchLocatorOperationResponse resp = (AtmBranchLocatorOperationResponse) responseContext;

	    BMBPayload bmbPayload = new BMBPayload(createHeader(resp));

	    populatePayLoad(resp, bmbPayload);
	    return bmbPayload;
	} else {
	    throw new BMBCustomClassCastException("BMB Custom Class Cast Exception");
	}
    }

    protected BMBPayloadHeader createHeader(AtmBranchLocatorOperationResponse resp) {

	BMBPayloadHeader header = new BMBPayloadHeader();

	if (resp != null && resp.isSuccess()) {
	    header.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
	    header.setResMsg("");
	} else if (resp != null) {
	    header.setResCde(resp.getResCde());
	    header.setResMsg(resp.getResMsg());
	}
	header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
	header.setResId(ResponseIdConstants.BRANCH_LOOKUP);

	return header;

    }

    void populatePayLoad(AtmBranchLocatorOperationResponse resp, BMBPayload bmbPayload) {
	List<BranchDTO> branchList = resp.getBranchList();
	List<AtmDTO> atmList = resp.getAtmList();

	AtmBranchJSONResponseModel atmBranchJSONResponseModel = new AtmBranchJSONResponseModel();

	if (branchList != null && branchList.size() > 0) {
	    List<BranchVOJSONModel> branchVOJSONList = new ArrayList<BranchVOJSONModel>();
	    for (BranchDTO branchDTO : branchList) {
		BranchVOJSONModel branchVOJSONModel = new BranchVOJSONModel();

		branchVOJSONModel.setBusinessId(branchDTO.getBusinessId());
		branchVOJSONModel.setName(branchDTO.getName());
		branchVOJSONModel.setCity(branchDTO.getCity());
		branchVOJSONModel.setState(branchDTO.getState());
		branchVOJSONModel.setAddress(branchDTO.getAddress());
		branchVOJSONModel.setContNum(branchDTO.getContNum());
		branchVOJSONModel.setBusinessHours(branchDTO.getBusinessHours());
		branchVOJSONList.add(branchVOJSONModel);
	    }

	    atmBranchJSONResponseModel.setBrnLst(branchVOJSONList);

	}

	if (atmList != null && atmList.size() > 0) {
	    List<ATMVOJSONModel> atmVOJSONList = new ArrayList<ATMVOJSONModel>();
	    for (AtmDTO atmDTO : atmList) {

		ATMVOJSONModel atmVOJSONModel = new ATMVOJSONModel();

		atmVOJSONModel.setBusinessId(atmDTO.getBusinessId());
		atmVOJSONModel.setName(atmDTO.getName());
		atmVOJSONModel.setCity(atmDTO.getCity());
		atmVOJSONModel.setAddress(atmDTO.getAddress());
		atmVOJSONModel.setState(atmDTO.getState());
		atmVOJSONList.add(atmVOJSONModel);
	    }

	    atmBranchJSONResponseModel.setAtmLst(atmVOJSONList);

	}

	bmbPayload.setPayData(atmBranchJSONResponseModel);
    }
}
