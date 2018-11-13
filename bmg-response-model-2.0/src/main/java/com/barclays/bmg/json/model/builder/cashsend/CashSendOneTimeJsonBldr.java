package com.barclays.bmg.json.model.builder.cashsend;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.SystemParameterDTO;
import com.barclays.bmg.json.model.CasaAccountJSONModel;
import com.barclays.bmg.json.model.CashSendOneTimeJsonModel;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.model.fundtransfer.BankDraftDetailsJSONResponseModel;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrieveAcctListOperationResponse;
import com.barclays.bmg.service.product.impl.ListValueResServiceImpl;
import com.barclays.bmg.service.product.request.ListValueResServiceRequest;
import com.barclays.bmg.service.product.response.ListValueResServiceResponse;
import com.barclays.bmg.service.request.SystemParameterServiceRequest;
import com.barclays.bmg.service.response.SystemParameterServiceResponse;

public class CashSendOneTimeJsonBldr extends BMBMultipleResponseJSONBuilder implements BMBJSONBuilder {

    private ListValueResServiceImpl listValueResServiceImpl;
    private String responseId;

    @Override
    public Object createMultipleJSONResponse(ResponseContext... responseContexts) {

	BMBPayloadHeader bmbPayloadHeader = null;
	BMBPayload bmbPayload = null;
	for (ResponseContext response : responseContexts) {
	    if (response != null && !response.isSuccess()) {
		bmbPayloadHeader = createHeader(response, ResponseIdConstants.CASH_SEND_INIT_RESPONSE_ID);
		break;
	    }
	}

	if (bmbPayloadHeader != null) {
	    bmbPayload = new BMBPayload(bmbPayloadHeader);
	} else {
	    bmbPayload = new BMBPayload(createHeader(responseContexts[0], ResponseIdConstants.CASH_SEND_INIT_RESPONSE_ID));
	    populatePayLoad(bmbPayload, responseContexts);
	}

	return bmbPayload;

    }

    protected BMBPayloadHeader createHeader(ResponseContext response) {

	BMBPayloadHeader header = new BMBPayloadHeader();
	if (response != null && response.isSuccess()) {
	    header.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
	    header.setResMsg("");
	} else if (response != null) {
	    header.setResCde(response.getResCde());
	    header.setResMsg(response.getResMsg());
	}
	header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
	header.setResId(getResponseId());

	return header;
    }

    protected void populatePayLoad(BMBPayload bmbPayload, ResponseContext... responses) {

	CashSendOneTimeJsonModel responseModel = new CashSendOneTimeJsonModel();

	RetrieveAcctListOperationResponse acctListOperationResponse = (RetrieveAcctListOperationResponse) responses[0];

	responseModel.setSrcLst(getCASAAccountList(acctListOperationResponse.getAcctList(), acctListOperationResponse));

	bmbPayload.setPayData(responseModel);

    }

    private List<CasaAccountJSONModel> getCASAAccountList(List<? extends CustomerAccountDTO> accounts, ResponseContext response) {

	List<CasaAccountJSONModel> casaAccountList = new ArrayList<CasaAccountJSONModel>();

	if (accounts != null) {

	    for (int i = 0; i < accounts.size(); i++) {

		CASAAccountDTO account = (CASAAccountDTO) accounts.get(i);
		CasaAccountJSONModel accountJSONModel = new CasaAccountJSONModel(account);

		// CHECK added masking
		accountJSONModel.setActNo(getCorrelationId(account.getAccountNumber(), response));


		// masking
		/*String maskedActNo = getMaskedAccountNumber(null,account.getAccountNumber());
		accountJSONModel.setMkdActNo(maskedActNo);*/


		 if(branchCodeCountryList.contains(BMBContextHolder.getContext().getBusinessId())){
			accountJSONModel.setMkdActNo(getMaskedAccountNumber(account.getBranchCode(), account.getAccountNumber()));
		    }
		    else {
			accountJSONModel.setMkdActNo(getMaskedAccountNumber(null, account.getAccountNumber()));
		    }


		casaAccountList.add(accountJSONModel);

	    }
	}
	return casaAccountList;

    }

    protected void postProcess(BankDraftDetailsJSONResponseModel responseModel, ResponseContext... responses) {

    }

    public ListValueResServiceImpl getListValueResServiceImpl() {
	return listValueResServiceImpl;
    }

    public void setListValueResServiceImpl(ListValueResServiceImpl listValueResServiceImpl) {
	this.listValueResServiceImpl = listValueResServiceImpl;
    }

    protected String getLabel(Context responseCtx, String key, String group) {

	String label = "";
	ListValueResServiceRequest listReq = new ListValueResServiceRequest();
	listReq.setContext(responseCtx);
	listReq.setGroup(group);
	listReq.setListValueKey(key);
	ListValueResServiceResponse listResp = listValueResServiceImpl.getListValueRes(listReq);
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

    protected List<String> getSysParamListById(Context context, String paramId, String activityId) {
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
    protected String getSysParamValue(Context context, String paramId, String activityId) {

	String paramValue = "";

	SystemParameterDTO systemParameterDTO = new SystemParameterDTO();

	systemParameterDTO.setBusinessId(context.getBusinessId());
	systemParameterDTO.setSystemId(context.getSystemId());
	systemParameterDTO.setActivityId(activityId);
	systemParameterDTO.setParameterId(paramId);

	SystemParameterServiceRequest systemParameterServiceRequest = new SystemParameterServiceRequest();
	systemParameterServiceRequest.setSystemParameterDTO(systemParameterDTO);

	SystemParameterServiceResponse response = getSystemParameterService().getSystemParameter(systemParameterServiceRequest);

	if (response != null && response.getSystemParameterDTO() != null) {
	    paramValue = response.getSystemParameterDTO().getParameterValue();
	}

	return paramValue;
    }

}