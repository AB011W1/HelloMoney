package com.barclays.bmg.json.model.builder.bankdraft;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.ListValueCacheDTO;
import com.barclays.bmg.dto.SystemParameterDTO;
import com.barclays.bmg.json.model.billpayment.BankDraftBeneficiaryJSONModel;
import com.barclays.bmg.json.model.billpayment.CountryCodeJSONModel;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.model.fundtransfer.BankDraftDetailsJSONResponseModel;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrievePayeeInfoOperationResponse;
import com.barclays.bmg.service.product.impl.ListValueResServiceImpl;
import com.barclays.bmg.service.product.request.ListValueResServiceRequest;
import com.barclays.bmg.service.product.response.ListValueResByGroupServiceResponse;
import com.barclays.bmg.service.product.response.ListValueResServiceResponse;
import com.barclays.bmg.service.request.SystemParameterServiceRequest;
import com.barclays.bmg.service.response.SystemParameterServiceResponse;

public class BankDraftPayeeDetailsJSONBldr extends
BMBMultipleResponseJSONBuilder implements BMBJSONBuilder{

	private ListValueResServiceImpl listValueResServiceImpl;
	private String responseId;

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
		} else if(response != null){
			header.setResCde(response.getResCde());
			header.setResMsg(response.getResMsg());
		}
		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
		header.setResId(getResponseId());

		return header;
	}

	protected void populatePayLoad(BMBPayload bmbPayload,
			ResponseContext... responses) {
		BankDraftDetailsJSONResponseModel responseModel = new BankDraftDetailsJSONResponseModel();

		Context responseCtx = null;

		RetrievePayeeInfoOperationResponse retrievePayeeInfoOperationResponse = (RetrievePayeeInfoOperationResponse) responses[0];

		if (retrievePayeeInfoOperationResponse != null) {

			BeneficiaryDTO beneficiaryDTO = retrievePayeeInfoOperationResponse
					.getBeneficiaryDTO();
			responseCtx = retrievePayeeInfoOperationResponse.getContext();

			BankDraftBeneficiaryJSONModel beneficiaryJSONModel = new BankDraftBeneficiaryJSONModel(
					beneficiaryDTO);
			responseModel.setPayInfo(beneficiaryJSONModel);
			beneficiaryJSONModel.setCntry(getLabel(responseCtx, responseCtx
					.getCountryCode(), "CMN_COUNTRY"));

		}

/*		if (responseCtx != null) {
			responseModel.setCntrLst(getCountryList(responseCtx));
			responseModel.setCurrLst(getSysParamListById(responseCtx,
					"PMT_FT_BKD_SUPPORT_CURRENCY", "PMT_FT_BKD_ONETIME"));
		}*/

		postProcess(responseModel, responses);
		bmbPayload.setPayData(responseModel);
	}

	protected void postProcess(BankDraftDetailsJSONResponseModel responseModel,
			ResponseContext... responses) {

	}

	private List<CountryCodeJSONModel> getCountryList(Context context) {

		List<CountryCodeJSONModel> cntryJSONLst = null;
		ListValueResServiceRequest listReq = new ListValueResServiceRequest();
		listReq.setContext(context);
		listReq.setGroup("CMN_COUNTRY");

		ListValueResByGroupServiceResponse listvByGroupServiceResponse = listValueResServiceImpl
				.getListValueByGroup(listReq);

		List<ListValueCacheDTO> countryCodeList = listvByGroupServiceResponse
				.getListValueCahceDTO();

		if (countryCodeList != null && countryCodeList.size() > 0) {
			cntryJSONLst = new ArrayList<CountryCodeJSONModel>();
			for (ListValueCacheDTO dto : countryCodeList) {
				CountryCodeJSONModel countryCodeJSONModel = new CountryCodeJSONModel();
				countryCodeJSONModel.setCntrCde(dto.getKey());
				countryCodeJSONModel.setCntrNam(dto.getLabel());
				cntryJSONLst.add(countryCodeJSONModel);
			}
		}
		return cntryJSONLst;
	}

	public ListValueResServiceImpl getListValueResServiceImpl() {
		return listValueResServiceImpl;
	}

	public void setListValueResServiceImpl(
			ListValueResServiceImpl listValueResServiceImpl) {
		this.listValueResServiceImpl = listValueResServiceImpl;
	}

	protected String getLabel(Context responseCtx, String key, String group) {

		String label = "";
		ListValueResServiceRequest listReq = new ListValueResServiceRequest();
		listReq.setContext(responseCtx);
		listReq.setGroup(group);
		listReq.setListValueKey(key);
		ListValueResServiceResponse listResp = listValueResServiceImpl
				.getListValueRes(listReq);
		if (listResp != null) {
			label = listResp.getListValueLabel();
		}
		return label;
	}

	public String getResponseId() {
		return responseId;
	}

	public void setResponseId(String responseId) {
		this.responseId = responseId;
	}

	protected List<String> getSysParamListById(Context context, String paramId,
			String activityId) {
		String params = getSysParamValue(context, paramId, activityId);

		List<String> valueList = new ArrayList<String>();

		if (params != null) {
			String[] valueArray = params.split(",");

			for (String value : valueArray) {
				valueList.add(value);
			}

			return valueList;
		}

		return valueList;
	}

	// Added to get only system parameter value based on param id...
	protected String getSysParamValue(Context context, String paramId,
			String activityId) {

		String paramValue = "";

		SystemParameterDTO systemParameterDTO = new SystemParameterDTO();

		systemParameterDTO.setBusinessId(context.getBusinessId());
		systemParameterDTO.setSystemId(context.getSystemId());
		systemParameterDTO.setActivityId(activityId);
		systemParameterDTO.setParameterId(paramId);

		SystemParameterServiceRequest systemParameterServiceRequest = new SystemParameterServiceRequest();
		systemParameterServiceRequest.setSystemParameterDTO(systemParameterDTO);

		SystemParameterServiceResponse response = getSystemParameterService()
				.getSystemParameter(systemParameterServiceRequest);

		if (response != null && response.getSystemParameterDTO() != null) {
			paramValue = response.getSystemParameterDTO().getParameterValue();
		}

		return paramValue;
	}

}
