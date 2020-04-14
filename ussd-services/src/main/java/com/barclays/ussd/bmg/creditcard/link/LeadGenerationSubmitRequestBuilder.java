package com.barclays.ussd.bmg.creditcard.link;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.dto.LeadGenProductDTO;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.jsonparsers.bean.login.AuthUserData;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;

public class LeadGenerationSubmitRequestBuilder implements BmgBaseRequestBuilder {
	/*
	 * (non-Javadoc)
	 *
	 * @see com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder#getRequestObject(com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO)
	 */
	public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException {
		String prodName="";
		String subProdName="";
		USSDBaseRequest request = new USSDBaseRequest();
		USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
		Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
		if (userInputMap == null) {
			throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getUssdErrorCode());
		}
		AuthUserData userAuthObj = (AuthUserData) ussdSessionMgmt.getUserAuthObj();
		List<CustomerMobileRegAcct> custActs = userAuthObj.getPayData().getCustActs();
		Map<String, String> requestParamMap = new HashMap<String, String>();
		for (CustomerMobileRegAcct acct : custActs) {
			if (USSDConstants.PRIMARY_INDICATOR_YES.equalsIgnoreCase(acct.getPriInd())) {
				requestParamMap.put(USSDConstants.PRIMARY_ACC_NUMBER, acct.getMkdActNo());
			}
		}
		prodName=userInputMap.get(USSDConstants.LEAD_GEN_PRODUCT_NAME);
		subProdName=userInputMap.get(USSDConstants.LEAD_GEN_SUB_PRODUCT_NAME);

		//TZNBC Menu Optimization - to fetch product and sub product for Loans
		String loanSubProduct="";
		String language= requestBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getLanguage();
		List<String> loansList= (List<String>) ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.LEAD_GENERATION_LOANS.getTranId());
		if(null!=loansList) {
			loanSubProduct = loansList.get(Integer.parseInt(userInputMap.get(USSDInputParamsEnum.LEAD_GENERATION_LOANS.getParamName())) - 1);		
			if (null!= loanSubProduct)
				if(null!=language && language.equalsIgnoreCase("EN"))
					prodName="Loans";
				else
					prodName="Mikopo";
				subProdName=loanSubProduct;
		}
		if(subProdName==null){
			subProdName="";
		}
		request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
		request.setOpCde(requestBuilderParamsDTO.getBmgOpCode());
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
		requestParamMap.put(USSDConstants.DATA_TYPE_CREDIT_CARD_NUMBER, userInputMap.get(USSDConstants.DATA_TYPE_CREDIT_CARD_NUMBER));
		requestParamMap.put(USSDInputParamsEnum.ACTIVITY_ID.getParamName(), "LEAD_GENRATION_APPLY_PRODUCT");
		requestParamMap.put(USSDConstants.DATA_TYPE_MOBILE_NUMBER,requestBuilderParamsDTO.getMsisdnNo());
		requestParamMap.put(USSDConstants.LEAD_GEN_PRODUCT_NAME,prodName);
		requestParamMap.put(USSDConstants.LEAD_GEN_SUB_PRODUCT_NAME,subProdName);
		request.setRequestParamMap(requestParamMap);
		return request;

	}

}
