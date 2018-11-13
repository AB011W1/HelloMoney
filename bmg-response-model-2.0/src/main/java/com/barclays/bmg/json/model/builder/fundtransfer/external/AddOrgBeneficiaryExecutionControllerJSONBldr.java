package com.barclays.bmg.json.model.builder.fundtransfer.external;

import java.util.Date;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.constants.ResponseIdConstants_forsvn;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.json.model.billpayment.AddOrgeneficiaryJSONModel;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.fundtransfer.external.AddOrgBeneficiaryOperationResponse;
import com.barclays.bmg.utils.BMGFormatUtility;

/**
 * @author E20041929
 *
 */
public class AddOrgBeneficiaryExecutionControllerJSONBldr extends
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
		header.setResId(ResponseIdConstants_forsvn.ADD_ORG_EXECUTIONCONTROLLER_BENEFICIARY);

		return header;
	}

	protected void populatePayLoad(BMBPayload bmbPayload,
			ResponseContext... responses) {
		AddOrgBeneficiaryExecContrJSONModel addOrgBeneficiaryJSONModel = new AddOrgBeneficiaryExecContrJSONModel();

		AddOrgBeneficiaryOperationResponse addOrgBenefFormSubmissionOpResp = (AddOrgBeneficiaryOperationResponse) responses[0];
 		/*addOrgBeneficiaryJSONModel
				.setConsumerUniqueRefNo(addOrgBenefFormSubmissionOpResp
						.getConsumerUniqueRefNo());*/

		AddOrgeneficiaryJSONModel beneficiaryJSONModel=new AddOrgeneficiaryJSONModel(addOrgBenefFormSubmissionOpResp.getBeneficiaryDTO());
		//Context context = addOrgBenefFormSubmissionOpResp.getContext();
//		beneficiaryJSONModel.setCntry(getListValueLabel(context, CMN_COUNTRY, beneficiaryJSONModel.getCntry()));
//		resolveCountry(beneficiaryJSONModel.getBeneBank(),context);
//		resolveCountryList(beneficiaryJSONModel.getIntBankLst(), context);
		addOrgBeneficiaryJSONModel.setBillerInfo(beneficiaryJSONModel);
		String consumerUniqueRefNo = addOrgBenefFormSubmissionOpResp
						.getConsumerUniqueRefNo();
		if(consumerUniqueRefNo!= null ) {
			consumerUniqueRefNo=consumerUniqueRefNo.split("-")[0];
		}
		addOrgBeneficiaryJSONModel.setTxnRefNo(consumerUniqueRefNo);
		addOrgBeneficiaryJSONModel.setTxnDate(BMGFormatUtility.getLongDate(new Date()));
		bmbPayload.setPayData(addOrgBeneficiaryJSONModel);
	}

}
