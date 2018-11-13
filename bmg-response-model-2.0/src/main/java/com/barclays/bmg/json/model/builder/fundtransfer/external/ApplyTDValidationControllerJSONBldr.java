package com.barclays.bmg.json.model.builder.fundtransfer.external;

import com.barclays.bmg.constants.AccountConstants;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.json.model.CasaAccountJSONModel;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.accountdetails.response.ApplyTDValidationOperationResponse;

/**
 * @author E20041929
 *
 */
public class ApplyTDValidationControllerJSONBldr extends
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
		header.setResId(ResponseIdConstants.APPLY_TD_VALIDATIONOPERATION);

		return header;
	}

	protected void populatePayLoad(BMBPayload bmbPayload,
			ResponseContext... responses) {
		ApplyTDValidationResContrJSONModel applyTDValiJSONModel = new ApplyTDValidationResContrJSONModel();

		ApplyTDValidationOperationResponse applyTDValidationOperationRes = (ApplyTDValidationOperationResponse) responses[0];


		applyTDValiJSONModel.setResCode(applyTDValidationOperationRes
				.getResCde());
		applyTDValiJSONModel.setResMsg(applyTDValidationOperationRes
				.getResMsg());
		CustomerAccountDTO sourceCustomerAccountDTO = applyTDValidationOperationRes.getSourceCustomerAccountDTO();
 		if (sourceCustomerAccountDTO instanceof CASAAccountDTO) {
			CASAAccountDTO account = (CASAAccountDTO) sourceCustomerAccountDTO;
			CasaAccountJSONModel accountJSONModel = new CasaAccountJSONModel(account);
			accountJSONModel.setTyp(AccountConstants.CASA_ACCOUNTS);
			applyTDValiJSONModel.setSrcAct(accountJSONModel);
		}

		applyTDValiJSONModel.setDepositAmount(applyTDValidationOperationRes.getDepositAmount());
		applyTDValiJSONModel.setTenureMonths(applyTDValidationOperationRes.getTenureMonths());
		applyTDValiJSONModel.setTenureDays(applyTDValidationOperationRes.getTenureDays());
/*		applyTDValiJSONModel.setAcctNo(applyTDValidationOperationRes.getAcctNo());
*/		applyTDValiJSONModel.setTxnRefNo(applyTDValidationOperationRes.getTransactionRefNum());


		bmbPayload.setPayData(applyTDValiJSONModel);
	}

}
