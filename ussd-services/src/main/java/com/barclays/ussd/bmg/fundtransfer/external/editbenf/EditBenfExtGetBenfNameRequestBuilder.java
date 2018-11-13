package com.barclays.ussd.bmg.fundtransfer.external.editbenf;

import java.util.Map;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;

public class EditBenfExtGetBenfNameRequestBuilder implements BmgBaseRequestBuilder {
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {
	Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
	userInputMap.put(USSDInputParamsEnum.EDIT_BENF_EXT_ACC_NO.getParamName(), requestBuilderParamsDTO.getUserInput());
	userInputMap.put(USSDInputParamsEnum.EDIT_BENF_EXT_PAYEE_TYPE.getParamName(), USSDConstants.EXTERNAL_PAYEE_TYPE);

	return null;
    }
}
