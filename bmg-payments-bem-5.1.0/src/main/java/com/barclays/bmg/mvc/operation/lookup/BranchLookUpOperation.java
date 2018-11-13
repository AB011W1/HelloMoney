package com.barclays.bmg.mvc.operation.lookup;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.FundTransferResponseCodeConstants;
import com.barclays.bmg.dto.BranchLookUpDTO;
import com.barclays.bmg.operation.payments.BMBPaymentsOperation;
import com.barclays.bmg.operation.request.lookup.BranchLookUpOperationRequest;
import com.barclays.bmg.operation.response.lookup.BranchLookUpOperationResponse;
import com.barclays.bmg.service.lookup.BranchLookUpService;
import com.barclays.bmg.service.request.lookup.BranchLookUpServiceRequest;
import com.barclays.bmg.service.response.lookup.BranchLookUpServiceResponse;

public class BranchLookUpOperation extends BMBPaymentsOperation {

	private BranchLookUpService branchLookUpService;

	/**
	 *
	 * @param branchLookUpOperationRequest
	 * @return
	 */
	public BranchLookUpOperationResponse retrieveBranches(
			BranchLookUpOperationRequest branchLookUpOperationRequest) {

		loadParameters(branchLookUpOperationRequest.getContext(),
				ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);

		BranchLookUpServiceRequest branchLookUpServiceRequest = new BranchLookUpServiceRequest();
		branchLookUpServiceRequest.setContext(branchLookUpOperationRequest
				.getContext());
		branchLookUpServiceRequest.setBankName(branchLookUpOperationRequest
				.getBankName());
		branchLookUpServiceRequest.setBranchName(branchLookUpOperationRequest
				.getBranchName());
		branchLookUpServiceRequest.setCityName(branchLookUpOperationRequest
				.getCityName());
		BranchLookUpServiceResponse branchLookUpServiceResponse = branchLookUpService
				.getBranchName(branchLookUpServiceRequest);

		BranchLookUpOperationResponse branchLookUpOperationResponse = new BranchLookUpOperationResponse();
		branchLookUpOperationResponse.setContext(branchLookUpOperationRequest
				.getContext());

		List<BranchLookUpDTO> brList = branchLookUpServiceResponse
				.getBranchList();
		if (brList != null && brList.size() > 0) {
			branchLookUpOperationResponse.setBranchList(brList);
			branchLookUpOperationResponse.setSuccess(true);
		} else {
			branchLookUpOperationResponse.setSuccess(false);
			branchLookUpOperationResponse
					.setResCde(FundTransferResponseCodeConstants.NO_BRANCHES_FOUND);
			getMessage(branchLookUpOperationResponse);
		}

		return branchLookUpOperationResponse;
	}

	public BranchLookUpOperationResponse retrieveBranchName(
			BranchLookUpOperationRequest branchLookUpOperationRequest) {

		loadParameters(branchLookUpOperationRequest.getContext(),
				ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);

		BranchLookUpServiceRequest branchLookUpServiceRequest = new BranchLookUpServiceRequest();
		branchLookUpServiceRequest.setContext(branchLookUpOperationRequest
				.getContext());

		List<String> brnCdeLst = new ArrayList<String>();

		String brncde = branchLookUpOperationRequest.getBranchCode();

		if (StringUtils.isNotEmpty(brncde)) {
			String[] brnCdeAry = brncde.split(",");
			if (brnCdeAry != null && brnCdeAry.length > 0) {
				for (int i = 0; i < brnCdeAry.length; i++) {
					brnCdeLst.add(getFormattedBranchCode(branchLookUpServiceRequest,brnCdeAry[i]));
				}
			}else{
				brnCdeLst.add(getFormattedBranchCode(branchLookUpServiceRequest,brncde));
			}
		}

		branchLookUpServiceRequest.setBranchCodeList(brnCdeLst);

		/*branchLookUpServiceRequest.setBranchCode(getFormattedBranchCode(
				branchLookUpServiceRequest, branchLookUpOperationRequest
						.getBranchCode()));*/

		BranchLookUpServiceResponse branchLookUpServiceResponse = branchLookUpService
				.getBranchName(branchLookUpServiceRequest);
		BranchLookUpOperationResponse branchLookUpOperationResponse = new BranchLookUpOperationResponse();
		branchLookUpOperationResponse.setContext(branchLookUpOperationRequest
				.getContext());

		List<BranchLookUpDTO> brList = branchLookUpServiceResponse
				.getBranchList();
		if (brList != null && brList.size() > 0) {
			branchLookUpOperationResponse.setBranchList(brList);
			branchLookUpOperationResponse.setSuccess(true);
		} else {
			branchLookUpOperationResponse.setSuccess(false);
			branchLookUpOperationResponse
					.setResCde(FundTransferResponseCodeConstants.NO_BRANCHES_FOUND);
			getMessage(branchLookUpOperationResponse);
		}

		return branchLookUpOperationResponse;
	}

	public BranchLookUpService getBranchLookUpService() {
		return branchLookUpService;
	}

	public void setBranchLookUpService(BranchLookUpService branchLookUpService) {
		this.branchLookUpService = branchLookUpService;
	}

}
