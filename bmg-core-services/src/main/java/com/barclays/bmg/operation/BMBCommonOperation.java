package com.barclays.bmg.operation;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.barclays.bmg.constants.CommonConstants;
import com.barclays.bmg.constants.FundTransferConstants;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.context.BMGContextHolder;
import com.barclays.bmg.context.BMGGlobalContext;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.ListValueCacheDTO;
import com.barclays.bmg.dto.SystemParameterDTO;
import com.barclays.bmg.exception.FeatureBlackoutException;
import com.barclays.bmg.service.MessageResourceService;
import com.barclays.bmg.service.SystemParameterService;
import com.barclays.bmg.service.featureblackout.FeatureBlackoutService;
import com.barclays.bmg.service.featureblackout.request.FeatureBlackoutServiceRequest;
import com.barclays.bmg.service.product.ListValueResService;
import com.barclays.bmg.service.product.request.ListValueResServiceRequest;
import com.barclays.bmg.service.product.response.ListValueResByGroupServiceResponse;
import com.barclays.bmg.service.product.response.ListValueResServiceResponse;
import com.barclays.bmg.service.request.MessageResourceServiceRequest;
import com.barclays.bmg.service.request.SystemParameterServiceRequest;
import com.barclays.bmg.service.response.MessageResourceServiceResponse;
import com.barclays.bmg.service.response.SystemParameterListServiceResponse;
import com.barclays.bmg.service.response.SystemParameterServiceResponse;
import com.barclays.bmg.service.utils.CreditCardValidationUtil;

/**
 * @author E20026338
 *
 */
public class BMBCommonOperation {
    protected SystemParameterService systemParameterService;
    protected MessageResourceService messageResourceService;
    protected FeatureBlackoutService featureBlackoutService;
    protected ListValueResService listValueResService;
    private Map<String, Boolean> currLstFilterMap;
    private String groupIdAsCurr;
    // START BRANCH CODE VALIDATION #UMESH
    protected List<String> branchCodeCountryList;
    protected List<String> inprogressErrorCodeList;

    public List<String> getBranchCodeCountryList() {
	return branchCodeCountryList;
    }

    public void setBranchCodeCountryList(List<String> branchCodeCountryList) {
	this.branchCodeCountryList = branchCodeCountryList;
    }

    public List<String> getInprogressErrorCodeList() {
	return inprogressErrorCodeList;
    }

    public void setInprogressErrorCodeList(List<String> inprogressErrorCodeList) {
	this.inprogressErrorCodeList = inprogressErrorCodeList;
    }

    // END BRANCH CODE VALIDATION #UMESH

    public SystemParameterService getSystemParameterService() {
	return systemParameterService;
    }

    public void setSystemParameterService(SystemParameterService systemParameterService) {
	this.systemParameterService = systemParameterService;
    }

    public MessageResourceService getMessageResourceService() {
	return messageResourceService;
    }

    public void setMessageResourceService(MessageResourceService messageResourceService) {
	this.messageResourceService = messageResourceService;
    }

    public FeatureBlackoutService getFeatureBlackoutService() {
	return featureBlackoutService;
    }

    public void setFeatureBlackoutService(FeatureBlackoutService featureBlackoutService) {
	this.featureBlackoutService = featureBlackoutService;
    }

    public ListValueResService getListValueResService() {
	return listValueResService;
    }

    public void setListValueResService(ListValueResService listValueResService) {
	this.listValueResService = listValueResService;
    }

    public Map<String, Boolean> getCurrLstFilterMap() {
	return currLstFilterMap;
    }

    public void setCurrLstFilterMap(Map<String, Boolean> currLstFilterMap) {
	this.currLstFilterMap = currLstFilterMap;
    }

    public String getGroupIdAsCurr() {
	return groupIdAsCurr;
    }

    public void setGroupIdAsCurr(String groupIdAsCurr) {
	this.groupIdAsCurr = groupIdAsCurr;
    }

    /**
     * @param response
     */
    protected void getMessage(ResponseContext response) {
	MessageResourceServiceRequest messageRequest = new MessageResourceServiceRequest();
	messageRequest.setContext(response.getContext());
	messageRequest.setMessageKey(response.getResCde());

	MessageResourceServiceResponse messageResponse = messageResourceService.getMessageDescByKey(messageRequest);
	response.setResMsg(messageResponse.getMessageDesc());
	response.setErrTyp(messageResponse.getErrTyp());
    }

    /**
     * @param response
     */
    protected void getMessage(ResponseContext response, Object[] args) {
	MessageResourceServiceRequest messageRequest = new MessageResourceServiceRequest();
	messageRequest.setContext(response.getContext());
	messageRequest.setMessageKey(response.getResCde());

	MessageResourceServiceResponse messageResponse = messageResourceService.getMessageDescByKey(messageRequest);
	String fmtMsg = MessageFormat.format(messageResponse.getMessageDesc(), args);
	response.setResMsg(fmtMsg);
	response.setErrTyp(messageResponse.getErrTyp());
    }

    /**
     * Loads system parameters into Context
     *
     * @param context
     * @param activities
     */
    protected void loadParameters(Context context, String... activities) {

	SystemParameterDTO systemParameterDTO = new SystemParameterDTO();

	systemParameterDTO.setBusinessId(context.getBusinessId());
	systemParameterDTO.setSystemId(context.getSystemId());

	SystemParameterServiceRequest systemParameterServiceRequest = new SystemParameterServiceRequest();
	systemParameterServiceRequest.setSystemParameterDTO(systemParameterDTO);

	SystemParameterListServiceResponse systemParameterListServiceResponse;
	List<SystemParameterDTO> systemParameterDTOList;
	Map<String, Object> systemParameterMap = new HashMap<String, Object>();

	for (int i = 0; i < activities.length; i++) {

	    systemParameterServiceRequest.getSystemParameterDTO().setActivityId(activities[i]);
	    systemParameterListServiceResponse = systemParameterService.getSysParamByActivityId(systemParameterServiceRequest);
	    systemParameterDTOList = systemParameterListServiceResponse.getSystemParameterDTOList();

	    for (SystemParameterDTO eachSPDto : systemParameterDTOList) {
		systemParameterMap.put(eachSPDto.getParameterId(), eachSPDto.getParameterValue());
	    }
	}

	// Changes for Tanzania Rel-2 only - SQA Overlapping on OTP against Rel-1

	if (context.getBusinessId().equals("TZBRB") && ("2.0").equals(context.getServiceVersion())) {
	    systemParameterMap.put(SystemParameterConstant.SECOND_AUTH_TYPE_SYSPARAM_KEY, "SQA");
	}
	context.setContextMap(systemParameterMap);

	BMGGlobalContext bmggloContext = new BMGGlobalContext();
	bmggloContext.setActivityId(context.getActivityId());
	bmggloContext.setBusinessId(context.getBusinessId());
	bmggloContext.setSystemId(context.getSystemId());
	bmggloContext.setContextMap(systemParameterMap);

	BMGContextHolder.setLogContext(bmggloContext);

    }

    /**
     * @param request
     * @param activityId
     * @return
     */
    protected String getAuthType(RequestContext request, String activityId) {
	Context context = request.getContext();
	String authType = "NON";
	SystemParameterDTO systemParameterDTO = new SystemParameterDTO();

	systemParameterDTO.setBusinessId(context.getBusinessId());
	systemParameterDTO.setSystemId(context.getSystemId());
	systemParameterDTO.setActivityId(activityId);
	systemParameterDTO.setParameterId(SystemParameterConstant.SECOND_AUTH_TYPE);

	SystemParameterServiceRequest systemParameterServiceRequest = new SystemParameterServiceRequest();
	systemParameterServiceRequest.setSystemParameterDTO(systemParameterDTO);
	SystemParameterServiceResponse response = systemParameterService.getSystemParameter(systemParameterServiceRequest);
	if (response != null && response.getSystemParameterDTO() != null) {
	    authType = response.getSystemParameterDTO().getParameterValue();
	}
	return authType;
    }

    /**
     * @param request
     * @param branchCode
     * @return
     */
    protected String getFormattedBranchCode(RequestContext request, String branchCode) {
	int branchCodeLength = FundTransferConstants.DEFAULT_BRANCH_CODE_LENGTH;
	Map<String, Object> sysMap = request.getContext().getContextMap();
	String length = (String) sysMap.get(FundTransferConstants.BRANCH_CODE_LENGTH);
	if (length != null) {
	    branchCodeLength = Integer.parseInt(length);
	}

	if (branchCode == null) {
	    return "";
	}
	if (branchCode.length() >= branchCodeLength) {
	    return branchCode;
	} else {
	    StringBuilder sb = new StringBuilder();
	    for (int i = branchCode.length(); i < branchCodeLength; i++) {
		sb.append("0");
	    }
	    return sb.append(branchCode).toString();
	}
    }

    /**
     *
     * Get feature blackout for particular activity Id.
     *
     * @param activityId
     */
    protected boolean getFeatureBlackout(String activityId) {
	FeatureBlackoutServiceRequest request = new FeatureBlackoutServiceRequest();
	Context context = new Context();
	context.setBusinessId(BMBContextHolder.getContext().getBusinessId());
	context.setActivityId(activityId);
	context.setSystemId(BMBContextHolder.getContext().getSystemId());
	request.setContext(context);
	boolean blackout = false;
	if (!StringUtils.isEmpty(activityId)) {
	    blackout = featureBlackoutService.checkFeatureBlackout(request);
	}

	/*
	 * if(blackout){ throw new FeatureBlackoutException(); }
	 */
	return blackout;
    }

    /**
     *
     * Get feature blackout for particular activity Id.
     *
     * @param activityId
     */
    protected boolean checkFeatureBlackout(String activityId) {
	FeatureBlackoutServiceRequest request = new FeatureBlackoutServiceRequest();
	Context context = new Context();
	context.setBusinessId(BMBContextHolder.getContext().getBusinessId());
	context.setActivityId(activityId);
	context.setSystemId(BMBContextHolder.getContext().getSystemId());
	request.setContext(context);
	boolean blackout = false;
	if (!StringUtils.isEmpty(activityId)) {
	    blackout = featureBlackoutService.checkFeatureBlackout(request);
	}

	if (blackout) {
	    throw new FeatureBlackoutException();
	}
	return blackout;
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

	SystemParameterServiceResponse response = systemParameterService.getSystemParameter(systemParameterServiceRequest);

	if (response != null && response.getSystemParameterDTO() != null) {
	    paramValue = response.getSystemParameterDTO().getParameterValue();
	}

	return paramValue;
    }

    protected List<String> getSysParamListById(Context context, String paramId, String activityId) {
	String params = getSysParamValue(context, paramId, activityId);

	List<String> valueList = new ArrayList<String>();

	if (params != null) {
	    String[] valueArray = params.split(",");

	    for (String value : valueArray) {
		valueList.add(value);
	    }

	}

	return valueList;
    }

    /**
     * Retrieve records for input group
     *
     * @param context
     * @param group
     * @return
     */
    protected List<ListValueCacheDTO> getListValueByGroup(Context context, String group) {

	ListValueResServiceRequest listReq = new ListValueResServiceRequest();
	listReq.setContext(context);
	listReq.setGroup(group);
	ListValueResByGroupServiceResponse listResp = listValueResService.getListValueByGroup(listReq);
	return listResp.getListValueCahceDTO();
    }

    /**
     * Retrieve record for group and key
     *
     * @param context
     * @param group
     * @param key
     * @return
     */
    protected String getListValueLabel(Context context, String group, String key) {
	ListValueResServiceRequest request = new ListValueResServiceRequest();
	request.setContext(context);
	request.setGroup(group);
	request.setListValueKey(key);
	ListValueResServiceResponse response = listValueResService.getListValueRes(request);

	return response.getListValueLabel();
    }

    protected List<String> getFilteredCurrList(RequestContext request, String paramId, String activityId) {

	Context context = request.getContext();

	List<String> supportcurrLst = getSysParamListById(context, paramId, activityId);

	String businessId = BMBContextHolder.getContext().getBusinessId();

	List<String> finalCurrLstVal = null;

	if (currLstFilterMap.containsKey(businessId) && currLstFilterMap.get(businessId)) {

	    finalCurrLstVal = getFinalCurrList(context, supportcurrLst);

	} else {
	    finalCurrLstVal = supportcurrLst;
	}

	return finalCurrLstVal;

    }

    public List<String> getFinalCurrList(Context context, List<String> supportcurrLst) {

	List<ListValueCacheDTO> currLstValDTO = getListValueByGroup(context, groupIdAsCurr);

	List<String> uniqueCurrLstVal = new ArrayList<String>();

	for (ListValueCacheDTO CurrLst : currLstValDTO) {
	    uniqueCurrLstVal.add(CurrLst.getLabel());
	}

	List<String> finalCurrLstVal = new ArrayList<String>();

	int size = supportcurrLst.size();

	for (int i = 0; i < size; i++) {
	    if ((uniqueCurrLstVal).contains(supportcurrLst.get(i))) {
		finalCurrLstVal.add(supportcurrLst.get(i));
	    }
	}

	return finalCurrLstVal;
    }

    protected List<? extends CustomerAccountDTO> getAllAccountsByStatus(RequestContext request, List<? extends CustomerAccountDTO> lstAccounts) {
	List<CustomerAccountDTO> allFilteredAccounts = new ArrayList<CustomerAccountDTO>();
	Context context = request.getContext();
	Map<String, Object> contextMap = context.getContextMap();
	String localCurr = context.getLocalCurrency();

	String CURRENCY_CODE_USD = contextMap.get(SystemParameterConstant.CURRENCY_CODE_USD).toString();
	String CURRENCY_CODE_EURO = contextMap.get(SystemParameterConstant.CURRENCY_CODE_EURO).toString();
	String CURRENCY_CODE_POUND = contextMap.get(SystemParameterConstant.CURRENCY_CODE_POUND).toString();
	String CURRENCY_CODE_RAND = String.valueOf(contextMap.get(SystemParameterConstant.CURRENCY_CODE_RAND));

	String CASA_ACCOUNT_STATUS_ACTIVE = contextMap.get(SystemParameterConstant.CASA_ACCOUNT_STATUS_ACTIVE) != null? contextMap.get(SystemParameterConstant.CASA_ACCOUNT_STATUS_ACTIVE).toString() : "";
	String CASA_ACCOUNT_STATUS_BLOCKED = contextMap.get(SystemParameterConstant.CASA_ACCOUNT_STATUS_BLOCKED) != null? contextMap.get(SystemParameterConstant.CASA_ACCOUNT_STATUS_BLOCKED).toString() : "";
	String CASA_ACCOUNT_STATUS_SUSPENDED = contextMap.get(SystemParameterConstant.CASA_ACCOUNT_STATUS_SUSPENDED) != null ? contextMap.get(SystemParameterConstant.CASA_ACCOUNT_STATUS_SUSPENDED).toString() : "";
	String CASA_ACCOUNT_STATUS_DORMANT = contextMap.get(SystemParameterConstant.CASA_ACCOUNT_STATUS_DORMANT) != null? contextMap.get(SystemParameterConstant.CASA_ACCOUNT_STATUS_DORMANT).toString() : "";

	if (branchCodeCountryList.contains(BMBContextHolder.getContext().getBusinessId())) {

	    if (lstAccounts != null && !lstAccounts.isEmpty() && !StringUtils.isEmpty(localCurr)) {
		for (CustomerAccountDTO sourceAcct : lstAccounts) {
		    if (sourceAcct instanceof CASAAccountDTO) {
			if (context.getActivityId().equals("ACT_ACCOUNTSERVICE_ACCOUNTSUMMARY")) {
                //Added RAND currency condition for ZWBRB.  Defect#2162
				if(BMBContextHolder.getContext().getBusinessId().equals(CommonConstants.ZWBRB_BUSINESS_ID))
				{          if (sourceAcct.getCurrency().equals(localCurr) || sourceAcct.getCurrency().equals(CURRENCY_CODE_USD)
						   || sourceAcct.getCurrency().equals(CURRENCY_CODE_EURO) || sourceAcct.getCurrency().equals(CURRENCY_CODE_POUND)
						   || sourceAcct.getCurrency().equals(CURRENCY_CODE_RAND)) {
							if (sourceAcct.getAccountStatus().equals(CASA_ACCOUNT_STATUS_ACTIVE)
								|| sourceAcct.getAccountStatus().equals(CASA_ACCOUNT_STATUS_BLOCKED)
								|| sourceAcct.getAccountStatus().equals(CASA_ACCOUNT_STATUS_SUSPENDED)
								|| sourceAcct.getAccountStatus().equals(CASA_ACCOUNT_STATUS_DORMANT))
							    allFilteredAccounts.add(sourceAcct);
						    }
				}else{
						if (sourceAcct.getCurrency().equals(localCurr) || sourceAcct.getCurrency().equals(CURRENCY_CODE_USD)
					       || sourceAcct.getCurrency().equals(CURRENCY_CODE_EURO) || sourceAcct.getCurrency().equals(CURRENCY_CODE_POUND)) {
					    if (sourceAcct.getAccountStatus().equals(CASA_ACCOUNT_STATUS_ACTIVE)
						   || sourceAcct.getAccountStatus().equals(CASA_ACCOUNT_STATUS_BLOCKED)
						   || sourceAcct.getAccountStatus().equals(CASA_ACCOUNT_STATUS_SUSPENDED)
						   || sourceAcct.getAccountStatus().equals(CASA_ACCOUNT_STATUS_DORMANT))
					       allFilteredAccounts.add(sourceAcct);
			    }
			}
			} else if (context.getActivityId().equals("SEC_ONLN_REG")) {
			    if (sourceAcct.getCurrency().equals(localCurr)) {
				if (sourceAcct.getAccountStatus().equals(CASA_ACCOUNT_STATUS_ACTIVE)
					|| sourceAcct.getAccountStatus().equals(CASA_ACCOUNT_STATUS_BLOCKED)
					|| sourceAcct.getAccountStatus().equals(CASA_ACCOUNT_STATUS_SUSPENDED)
					|| sourceAcct.getAccountStatus().equals(CASA_ACCOUNT_STATUS_DORMANT))
				    allFilteredAccounts.add(sourceAcct);
			    }
			} else if (context.getActivityId().equals("ACT_CHK_BOOK_REQUEST")) {
			    if (sourceAcct.getCurrency().equals(localCurr)) {
				if (sourceAcct.getAccountStatus().equals(CASA_ACCOUNT_STATUS_ACTIVE)) {
				    String params = getListValueLabel(context, "SYS_PARAM_CURRENT", "PRODUCT_CODES");
				    if (params != null) {
					String[] valueArray = params.split(",");

					for (String value : valueArray) {
					    if (sourceAcct.getProductCode().equalsIgnoreCase(value)) {
						allFilteredAccounts.add(sourceAcct);
						break;
					    }
					}
				    }
				}
			    }
			} else {
			    if (sourceAcct.getCurrency().equals(localCurr)) {
				if (sourceAcct.getAccountStatus().equals(CASA_ACCOUNT_STATUS_ACTIVE))
				    allFilteredAccounts.add(sourceAcct);
			    }
			}
		    }
		}
	    }
	} else {

	    String[] activeRelshipCdeAry = CASA_ACCOUNT_STATUS_ACTIVE.split(",");
	    String[] blockedCdeAry = CASA_ACCOUNT_STATUS_BLOCKED.split(",");
	    // String[] suspendedCdeAry = CASA_ACCOUNT_STATUS_SUSPENDED.split(",");
	    // String[] dormantCdeAry = CASA_ACCOUNT_STATUS_DORMANT.split(",");

	    if (lstAccounts != null && !lstAccounts.isEmpty() && !StringUtils.isEmpty(localCurr)) {
		for (CustomerAccountDTO sourceAcct : lstAccounts) {
		    if (sourceAcct instanceof CASAAccountDTO) {

			if (context.getActivityId().equals("ACT_ACCOUNTSERVICE_ACCOUNTSUMMARY")) {
			    if (sourceAcct.getCurrency().equals(localCurr) || sourceAcct.getCurrency().equals(CURRENCY_CODE_USD)
				    || sourceAcct.getCurrency().equals(CURRENCY_CODE_EURO) || sourceAcct.getCurrency().equals(CURRENCY_CODE_POUND)
				    || sourceAcct.getCurrency().equals(CURRENCY_CODE_RAND)) {

				if (findStatusInArray(sourceAcct.getAccountStatus(), activeRelshipCdeAry)
					|| findStatusInArray(sourceAcct.getAccountStatus(), blockedCdeAry))
				    allFilteredAccounts.add(sourceAcct);
			    }

			} else if (context.getActivityId().equals("SEC_ONLN_REG")) {
			    if (sourceAcct.getCurrency().equals(localCurr)) {
				if (findStatusInArray(sourceAcct.getAccountStatus(), activeRelshipCdeAry))
				    allFilteredAccounts.add(sourceAcct);
			    }
			} else if (context.getActivityId().equals("PMT_FT_CS")) {
			    if (sourceAcct.getCurrency().equals(localCurr)) {
				if (findStatusInArray(sourceAcct.getAccountStatus(), activeRelshipCdeAry))
				    allFilteredAccounts.add(sourceAcct);
			    }
			} else if (context.getActivityId().equals("ACT_CHK_BOOK_REQUEST")) {
			    if (sourceAcct.getCurrency().equals(localCurr)) {
				if (findStatusInArray(sourceAcct.getAccountStatus(), activeRelshipCdeAry)) {
				    String params = getListValueLabel(context, "SYS_PARAM_CURRENT", "PRODUCT_CODES");
				    if (params != null) {
					String[] valueArray = params.split(",");
					for (String value : valueArray) {
					    if (sourceAcct.getProductCode().equalsIgnoreCase(value)) {
						allFilteredAccounts.add(sourceAcct);
						break;
					    }
					}
				    }
				}
			    }
			} else {
			    if (sourceAcct.getCurrency().equals(localCurr)) {
				if (findStatusInArray(sourceAcct.getAccountStatus(), activeRelshipCdeAry))
				    allFilteredAccounts.add(sourceAcct);
			    }
			}
		    }
		}
	    }

	}
	return allFilteredAccounts;
    }

    protected List<? extends CustomerAccountDTO> filterAccountsByStatus(RequestContext request, List<? extends CustomerAccountDTO> lstAccounts) {
	List<CustomerAccountDTO> allFilteredAccounts = new ArrayList<CustomerAccountDTO>();
	Context context = request.getContext();
	Map<String, Object> contextMap = context.getContextMap();

	String ACCOUNTTYPE_SOLO = (contextMap.get(SystemParameterConstant.ACCOUNTTYPE_SOLO) != null) ? contextMap.get(
		SystemParameterConstant.ACCOUNTTYPE_SOLO).toString() : "";

	String ACCOUNTTYPE_JOINTORFIRST = (contextMap.get(SystemParameterConstant.ACCOUNTTYPE_JOINTORFIRST) != null) ? contextMap.get(
		SystemParameterConstant.ACCOUNTTYPE_JOINTORFIRST).toString() : "";

	String ACCOUNTTYPE_JOINTOROTHER = (contextMap.get(SystemParameterConstant.ACCOUNTTYPE_JOINTOROTHER) != null) ? contextMap.get(
		SystemParameterConstant.ACCOUNTTYPE_JOINTOROTHER).toString() : "";

	String ACCOUNTTYPE_JOINTOR = (contextMap.get(SystemParameterConstant.ACCOUNTTYPE_JOINTOR) != null) ? contextMap.get(
				SystemParameterConstant.ACCOUNTTYPE_JOINTOR).toString() : "";

	String ACCOUNTTYPE_MANYOR = (contextMap.get(SystemParameterConstant.ACCOUNTTYPE_MANYOR) != null) ? contextMap.get(
				SystemParameterConstant.ACCOUNTTYPE_MANYOR).toString() : "";

	String ACCOUNTTYPE_AUS = (contextMap.get(SystemParameterConstant.ACCOUNTTYPE_AUS) != null) ? contextMap.get(
			    SystemParameterConstant.ACCOUNTTYPE_AUS).toString() : "";

		if (branchCodeCountryList.contains(BMBContextHolder
				.getContext().getBusinessId())) {//For EBox/ BRAINS countries: Refer CR #06
			for (CustomerAccountDTO customerAccountDTO : lstAccounts) {
				if (customerAccountDTO instanceof CASAAccountDTO) {
					CASAAccountDTO casaAccount = (CASAAccountDTO) customerAccountDTO;

					/*if (customerAccountDTO.getRelationshipCode().equals(ACCOUNTTYPE_SOLO)
								|| customerAccountDTO.getRelationshipCode().equals(ACCOUNTTYPE_JOINTOR)
								|| customerAccountDTO.getRelationshipCode().equals(ACCOUNTTYPE_MANYOR)) {
							allFilteredAccounts.add(casaAccount);
						}*/

					if(BMBContextHolder.getContext().getBusinessId().equals(CommonConstants.ZMBRB_BUSINESS_ID)){
						if (customerAccountDTO.getRelationshipCode().equals(ACCOUNTTYPE_SOLO)
								|| customerAccountDTO.getRelationshipCode().equals(ACCOUNTTYPE_JOINTOR))
							    allFilteredAccounts.add(casaAccount);
					}else if(BMBContextHolder.getContext().getBusinessId().equals(CommonConstants.ZWBRB_BUSINESS_ID)){// Modified for Defect# 2182 and Defect 2183.
					    if (context.getActivityId().equals("ACT_CHK_BOOK_REQUEST") || context.getActivityId().equals("CUS_ORDER_PAPER_STMT"))
					    {
					    	if (customerAccountDTO.getRelationshipCode().equals(ACCOUNTTYPE_SOLO)) {
								allFilteredAccounts.add(casaAccount);
							}
					    }else {
					    	if (customerAccountDTO.getRelationshipCode().equals(ACCOUNTTYPE_SOLO)

								|| customerAccountDTO.getRelationshipCode().equals(ACCOUNTTYPE_JOINTOR)
								|| customerAccountDTO.getRelationshipCode().equals(ACCOUNTTYPE_MANYOR)) {
							   allFilteredAccounts.add(casaAccount);
						}
					    }
					}else if(BMBContextHolder.getContext().getBusinessId().equals(CommonConstants.BWBRB_BUSINESS_ID)){
						if (customerAccountDTO.getRelationshipCode().equals(ACCOUNTTYPE_SOLO)) {
							  allFilteredAccounts.add(casaAccount);
						}
					}else{
						if (customerAccountDTO.getRelationshipCode().equals(ACCOUNTTYPE_SOLO)
								|| customerAccountDTO.getRelationshipCode().equals(ACCOUNTTYPE_JOINTOR)
								|| customerAccountDTO.getRelationshipCode().equals(ACCOUNTTYPE_MANYOR)) {
							  allFilteredAccounts.add(casaAccount);
						}
					}
				}
				}
		}else{//For FCR Countries: Refer CR #06
			for (CustomerAccountDTO customerAccountDTO : lstAccounts) {
				if (customerAccountDTO instanceof CASAAccountDTO) {
					CASAAccountDTO casaAccount = (CASAAccountDTO) customerAccountDTO;
						if (customerAccountDTO.getRelationshipCode().equals(ACCOUNTTYPE_SOLO)
								|| customerAccountDTO.getRelationshipCode().equals(ACCOUNTTYPE_JOINTORFIRST)
								|| customerAccountDTO.getRelationshipCode().equals(ACCOUNTTYPE_JOINTOROTHER)
								|| customerAccountDTO.getRelationshipCode().equals(ACCOUNTTYPE_AUS)) {
							allFilteredAccounts.add(casaAccount);
						}
				}
			}
		}
	return allFilteredAccounts;
    }

    protected String[] getValueFromContextMap(Map<String, Object> contextMap, String paramName) {
	// Get ParamValueString from Map
	String paramValueStr = contextMap.get(paramName).toString();
	// If not null ,Split ParamValueString using Comma
	return (paramValueStr == null) ? null : paramValueStr.split(",");
    }

    protected List<CustomerAccountDTO> filterCCDForAtAGlance(RequestContext request, List<? extends CustomerAccountDTO> lstAccounts) {
	List<CustomerAccountDTO> allFilteredAccounts = new ArrayList<CustomerAccountDTO>();

	// Get AtAGlance Credit Card Block code
	String[] ccdAtaGalnceBlockCodeAry = getValueFromContextMap(request.getContext().getContextMap(),
		SystemParameterConstant.CREDIT_CARD_ATAGLANCE_BLOCK_CODE);
	// Get AtAGlance Account Block code
	String[] ccdAtaGalnceAccountBlockCodeAry = getValueFromContextMap(request.getContext().getContextMap(),
		SystemParameterConstant.CREDIT_CARD_ATAGLANCE_ACCOUNT_BLOCK_CODE);

	CreditCardAccountDTO creditCardAccountDTO = null;
	if (lstAccounts != null && !lstAccounts.isEmpty()) {
	    for (CustomerAccountDTO sourceAcct : lstAccounts) {
		if (sourceAcct instanceof CreditCardAccountDTO) {
		    creditCardAccountDTO = (CreditCardAccountDTO) sourceAcct;
		    if (CreditCardValidationUtil.validateCCDForAtAGlance(creditCardAccountDTO, ccdAtaGalnceBlockCodeAry,
			    ccdAtaGalnceAccountBlockCodeAry)) {
			allFilteredAccounts.add(creditCardAccountDTO);
		    }

		}
	    }
	}
	return allFilteredAccounts;
    }

    protected List<CustomerAccountDTO> filterCCDForUnbilledStmtTrans(RequestContext request, List<? extends CustomerAccountDTO> lstAccounts) {
	List<CustomerAccountDTO> allFilteredAccounts = new ArrayList<CustomerAccountDTO>();

	// Get Credit Card Statement Block code
	String[] ccdStatementBlockCodeAry = getValueFromContextMap(request.getContext().getContextMap(),
		SystemParameterConstant.CREDIT_CARD_STATEMENT_BLOCK_CODE);
	// Get Credit card Statement Account Block code
	String[] ccdStatementAccountBlockCodeAry = getValueFromContextMap(request.getContext().getContextMap(),
		SystemParameterConstant.CREDIT_CARD_STATEMENT_ACCOUNT_BLOCK_CODE);

	CreditCardAccountDTO creditCardAccountDTO = null;
	if (lstAccounts != null && !lstAccounts.isEmpty()) {
	    for (CustomerAccountDTO sourceAcct : lstAccounts) {
		if (sourceAcct instanceof CreditCardAccountDTO) {
		    creditCardAccountDTO = (CreditCardAccountDTO) sourceAcct;

		    // Unbilled transaction and Statement transaction
		    if (CreditCardValidationUtil.validateCCDForUnbilledStmtTrans(creditCardAccountDTO, ccdStatementBlockCodeAry,
			    ccdStatementAccountBlockCodeAry)) {
			allFilteredAccounts.add(sourceAcct);
		    }
		}
	    }
	}
	return allFilteredAccounts;
    }

    protected List<CustomerAccountDTO> filterCCDForPayment(RequestContext request, List<? extends CustomerAccountDTO> lstAccounts) {
	List<CustomerAccountDTO> allFilteredAccounts = new ArrayList<CustomerAccountDTO>();

	// Get Credit Card Statement Block code
	String[] ccdPaymentBlockCodeAry = getValueFromContextMap(request.getContext().getContextMap(),
		SystemParameterConstant.CREDIT_CARD_PAYMENT_BLOCK_CODE);
	// Get Credit card Statement Account Block code
	String[] ccdPaymentAccountBlockCodeAry = getValueFromContextMap(request.getContext().getContextMap(),
		SystemParameterConstant.CREDIT_CARD_PAYMENT_ACCOUNT_BLOCK_CODE);

	CreditCardAccountDTO creditCardAccountDTO = null;
	if (lstAccounts != null && !lstAccounts.isEmpty()) {
	    for (CustomerAccountDTO sourceAcct : lstAccounts) {
		if (sourceAcct instanceof CreditCardAccountDTO) {
		    creditCardAccountDTO = (CreditCardAccountDTO) sourceAcct;
		    // own Payment and 3rd Party Payment

		    if (CreditCardValidationUtil.validateCCDForPayment(creditCardAccountDTO, ccdPaymentBlockCodeAry, ccdPaymentAccountBlockCodeAry)) {
			allFilteredAccounts.add(sourceAcct);
		    }
		}
	    }
	}
	return allFilteredAccounts;
    }

    private boolean findStatusInArray(String accountStatus, String arry[]) {
	boolean result = false;

	// Allow Space as a active status for credit card
	if (accountStatus.trim().equals("")) {
	    return true;
	}

	if (arry != null && arry.length > 0) {
	    for (int i = 0; i < arry.length; i++) {
		String temp = arry[i];
		if (temp.equalsIgnoreCase(accountStatus)) {
		    result = true;
		    break;
		}
	    }
	}
	return result;
    }

}
