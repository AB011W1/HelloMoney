package com.barclays.ussd.bmg.mobilewallettopup.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.dto.SystemParameterDTO;
import com.barclays.bmg.service.SystemParameterService;
import com.barclays.bmg.service.request.SystemParameterServiceRequest;
import com.barclays.bmg.service.response.SystemParameterServiceResponse;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.Beneficiery;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.Payee;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.ReferenceNumber;

public class MobileWalletConfirmSavedBeneficiaryRequestBuilder implements BmgBaseRequestBuilder {
	@Autowired
	SystemParameterService systemParameterService;

	@Override
	public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {

		USSDBaseRequest request = new USSDBaseRequest();
		Map<String, String> requestParamMap = new HashMap<String, String>();
		Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
		List<Beneficiery> beneList = (List<Beneficiery>) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions()
		.get(USSDInputParamsEnum.MOBILE_WALLET_PAYEE_LIST.getTranId());
		Payee payee = (Payee)requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(USSDInputParamsEnum.MOBILE_WALLET_BENF_DTlS.getTranId());
		ReferenceNumber refNo = payee.getRefNo();

		String billerId="", mnoId="",mobileNo="";
		if(beneList!=null && beneList.size() != 0){

			billerId = beneList.get(Integer.parseInt(userInputMap.get(USSDInputParamsEnum.MOBILE_WALLET_PAYEE_LIST.getParamName())) - 1).getBillerId();
			mobileNo = refNo.getPhNo();
			if(mobileNo.startsWith("0") && mobileNo.length()>9)
			{
				mobileNo=("233")+mobileNo.substring(1, mobileNo.length());
			}
			else
			{
				mobileNo=("233")+mobileNo;
			}
			SystemParameterDTO systemParameterDTO = new SystemParameterDTO();
			systemParameterDTO.setBusinessId(BMBContextHolder.getContext().getBusinessId());
			systemParameterDTO.setSystemId("UB");
			systemParameterDTO.setParameterId(billerId);

			SystemParameterServiceRequest systemParameterServiceRequest = new SystemParameterServiceRequest();
			systemParameterServiceRequest.setSystemParameterDTO(systemParameterDTO);

			SystemParameterServiceResponse response = systemParameterService.getStatusParameter(systemParameterServiceRequest);
			if(response!=null && response.getSystemParameterDTO()!=null && response.getSystemParameterDTO().getParameterValue()!=null)
				mnoId = response.getSystemParameterDTO().getParameterValue();
			requestParamMap.put(USSDInputParamsEnum.SELFREG_BRANCH.getParamName(), mnoId);
		}

		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
		requestParamMap.put(USSDInputParamsEnum.SELFREG_ACCOUNT.getParamName(), mobileNo);
		requestParamMap.put(USSDInputParamsEnum.SELFREG_MOBILE.getParamName(), mobileNo);
		request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
		request.setRequestParamMap(requestParamMap);
		return request;

	}

}
