package com.barclays.bmg.json.model.builder.pesalink;

import java.util.LinkedList;
import java.util.List;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.json.model.GroupNonpersonalCustomerInfoJsonModel;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.accountdetails.response.AccountAdditionalInfoOps;
import com.barclays.bmg.operation.accountdetails.response.AccountOps;
import com.barclays.bmg.service.accounts.response.AllGroupWalletAccountOperationResponse;

public class GroupWalletOneTimeJsonBldr extends BMBMultipleResponseJSONBuilder implements
		BMBJSONBuilder {

	/*@Override
	public Object createJSONResponse(ResponseContext responseContext) {

		if (responseContext instanceof AllGroupWalletAccountOperationResponse) {
			AllGroupWalletAccountOperationResponse response = (AllGroupWalletAccountOperationResponse) responseContext;

			BMBPayload bmbPayload = new BMBPayload(createHeader(response));

			if (response.isSuccess()) {
				populatePayLoad(response, bmbPayload);
			}

			return bmbPayload;
		} else {
			throw new BMBCustomClassCastException(
					"BMB Custom Class Cast Exception");
		}

	}
*/
	/**
	 * This method createHeader has the purpose to create header for JSON
	 * response.
	 *
	 * @param CreditCardLinkConfirmOperationResponse
	 * @return BMBPayloadHeader
	 */
	protected BMBPayloadHeader createHeader(
			ResponseContext response) {

		BMBPayloadHeader header = new BMBPayloadHeader();
		if (response != null && response.isSuccess()) {
			header.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
			header.setResMsg("Succes GroupWalllet Account Retrival");

		} else if (response != null && !response.isSuccess()) {
			header.setResCde(response.getResCde());
			header.setResMsg(response.getResMsg());
		}
		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);

		return header;
	}

	/**
	 * This method populatePayLoad has the purpose to create data for JSON
	 * response.
	 *
	 * @param CreditCardLinkConfirmOperationResponse
	 * @param BMBPayload
	 * @return void
	 */
	protected void populatePayLoad(BMBPayload bmbPayload,ResponseContext... responses) {
		AllGroupWalletAccountOperationResponse response=(AllGroupWalletAccountOperationResponse)responses[0];
		GroupNonpersonalCustomerInfoJsonModel responseModel = null;
		if (response != null && response.isSuccess()) {
			responseModel = new GroupNonpersonalCustomerInfoJsonModel();
			List<AccountOps> aListSer = response.getNonPersonalCustAcctList();
			List<AccountOps> aListOps = new LinkedList<AccountOps>();

			if (aListSer != null && aListSer.size() > 0) {
				for (AccountOps aser : aListSer) {
					AccountOps ops = new AccountOps();
					AccountAdditionalInfoOps aops = new AccountAdditionalInfoOps();
					AccountAdditionalInfoOps aaiser = aser
							.getAccountAdditionalInfo();

					aops.setAccountId(aaiser.getAccountId());
					aops.setMaskedAccountId(aaiser.getMaskedAccountId());
					aops.setAccountTitle(aaiser.getAccountTitle());
					aops.setAuthLevel(aaiser.getAuthLevel());
					aops.setAvailableBalance(aaiser.getAvailableBalance());
					aops.setBranchCode(aaiser.getBranchCode());
					aops.setBranchName(aaiser.getBranchName());
					aops.setCurrencyCode(aaiser.getCurrencyCode());
					aops.setCurrencyShortName(aaiser.getCurrencyShortName());
					aops.setCurrentBalance(aaiser.getCurrentBalance());
					aops.setCurrentStatus(aaiser.getCurrentStatus());
					aops.setCustomerId(aaiser.getCustomerId());
					aops.setCustomerRelationship(aaiser
							.getCustomerRelationship());
					aops.setProductCode(aaiser.getProductCode());
					aops.setProductName(aaiser.getProductName());

					ops.setAccountAdditionalInfo(aops);
					aListOps.add(ops);
				}
			}
			responseModel.setNonPersonalCustAcctList(aListOps);

		}

		bmbPayload.setPayData(responseModel);
	}

	@Override
	public Object createMultipleJSONResponse(
			ResponseContext... responseContexts) {
		// TODO Auto-generated method stub

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

}
