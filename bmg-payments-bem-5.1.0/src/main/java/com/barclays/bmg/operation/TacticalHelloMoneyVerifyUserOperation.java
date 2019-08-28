/*
 * Copyright (c) 2013 Barclays Bank Plc, All Rights Reserved.
 *
 * This code is confidential to Barclays Bank Plc and shall not be disclosed
 * outside the Bank without the prior written permission of the Director of
 * CIO
 *
 * In the event that such disclosure is permitted the code shall not be copied
 * or distributed other than on a need-to-know basis and any recipients may be
 * required to sign a confidentiality undertaking in favour of Barclays Bank
 * Plc.
 */
package com.barclays.bmg.operation;

import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.barclays.bmg.audit.annotation.AuditSupport;
import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dao.impl.ThmHttpClientExecutor;
import com.barclays.bmg.helper.DBParamFetchHelper;
import com.barclays.bmg.helper.DisasterRecoveryHelper;
import com.barclays.bmg.operation.request.TacticalHelloMoneyVerifyUserOperationRequest;
import com.barclays.bmg.operation.response.TacticalHelloMoneyVerifyUserOperationResponse;
import com.barclays.bmg.service.TacticalHelloMoneyEncryptPinService;
import com.barclays.bmg.service.TacticalHelloMoneyVerifyUserService;
import com.barclays.bmg.service.request.TacticalHelloMoneyVerifyUserServiceRequest;
import com.barclays.bmg.service.request.dto.EncryptionConstants;
import com.barclays.bmg.service.response.TacticalHelloMoneyVerifyUserServiceResponse;

public class TacticalHelloMoneyVerifyUserOperation extends BMBCommonOperation {
    private static final Logger LOGGER = Logger.getLogger(TacticalHelloMoneyVerifyUserOperation.class);

    private TacticalHelloMoneyVerifyUserService thmLgnVrfyUsrPnService;
    private TacticalHelloMoneyEncryptPinService thmEncryptPinService;
    private static final String DR = "DR_";
    private static final String THM_URL_EP = "TacticalHelloMoney_EndPoint";
    private ThmHttpClientExecutor thmHttpClientExecutor;

    @AuditSupport(auditType = AuditConstant.AUDIT_TYPE_TRANSACTION, activityState = AuditConstant.SRC_COM_EXECUTOR, serviceDescription = "SD_LOGIN", stepId = "1", activityType = "auditLogin")
    public TacticalHelloMoneyVerifyUserOperationResponse verifyUserWithPin(TacticalHelloMoneyVerifyUserOperationRequest request) {
	TacticalHelloMoneyVerifyUserOperationResponse thmLgnVrfyUsrPnOperationResponse = new TacticalHelloMoneyVerifyUserOperationResponse();
	Context context = request.getContext();
	loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID, context.getActivityId());

	// TacticalHelloMoneyEncryptPinServiceRequest thmEncryptPinServiceRequest = new TacticalHelloMoneyEncryptPinServiceRequest();
	// /thmEncryptPinServiceRequest.setContext(context);
	// thmEncryptPinServiceRequest.setPin(request.getCustPIN());
	// /thmEncryptPinServiceRequest.setBcagProperties(fetchBCAGProperties(context));

	// TacticalHelloMoneyEncryptPinServiceResponse encryptThmPinResponse = thmEncryptPinService.encryptThmPin(thmEncryptPinServiceRequest);

	// if (encryptThmPinResponse != null) {
	TacticalHelloMoneyVerifyUserServiceRequest thmLgnVrfyUsrPnServiceRequest = new TacticalHelloMoneyVerifyUserServiceRequest();
	thmLgnVrfyUsrPnServiceRequest.setContext(context);
	thmLgnVrfyUsrPnServiceRequest.setCustMSISDN(request.getCustMSISDN());
	thmLgnVrfyUsrPnServiceRequest.setCustPIN(request.getCustPIN());
	thmLgnVrfyUsrPnServiceRequest.setThmUrl(getTHMUrl(context));

	TacticalHelloMoneyVerifyUserServiceResponse serRes = thmLgnVrfyUsrPnService.verifyUserWithThmPin(thmLgnVrfyUsrPnServiceRequest);

	if (serRes != null) {
	    thmLgnVrfyUsrPnOperationResponse.setContext(serRes.getContext());
	    thmLgnVrfyUsrPnOperationResponse.setResCde(serRes.getResCde());
	    thmLgnVrfyUsrPnOperationResponse.setResMsg(serRes.getResMsg());

	    boolean respSuccessFlg = serRes.isSuccess();
	    if (respSuccessFlg) {
		thmLgnVrfyUsrPnOperationResponse.setSuccess(respSuccessFlg);
	    } else {
		getMessage(thmLgnVrfyUsrPnOperationResponse);
		thmLgnVrfyUsrPnOperationResponse.setSuccess(false);
	    }
	} else {
	    thmLgnVrfyUsrPnOperationResponse.setSuccess(false);
	}

	/*
	 * } else { thmLgnVrfyUsrPnOperationResponse.setSuccess(false); }
	 */

	return thmLgnVrfyUsrPnOperationResponse;
    }

    private Properties fetchBCAGProperties(Context context) {
	Properties bcagProperties = new Properties();

	bcagProperties.put(EncryptionConstants.THMPINENCRYPTKEYSTORE, DBParamFetchHelper.getSysParamValue(EncryptionConstants.THMPINENCRYPTKEYSTORE));
	bcagProperties.put(EncryptionConstants.THMPINENCRYPTKEYSTOREKEY, DBParamFetchHelper
		.getSysParamValue(EncryptionConstants.THMPINENCRYPTKEYSTOREKEY));
	bcagProperties.put(EncryptionConstants.THMPINENCRYPTENCRYPTLOGIC, DBParamFetchHelper
		.getSysParamValue(EncryptionConstants.THMPINENCRYPTENCRYPTLOGIC));

	// CHANGE THIS TO ENCRYPT/DECRYPT == STARTS
	String encryptedUserName = DBParamFetchHelper.getSysParamValue(EncryptionConstants.THMPINENCRYPTUSERNAME);
	String encryptedUserKey = DBParamFetchHelper.getSysParamValue(EncryptionConstants.THMPINENCRYPTUSERKEY);
	bcagProperties.put(EncryptionConstants.THMPINENCRYPTUSERNAME, encryptedUserName);
	bcagProperties.put(EncryptionConstants.THMPINENCRYPTUSERKEY, encryptedUserKey);
	// CHANGE THIS TO ENCRYPT/DECRYPT == ENDS

	/*
	 * THIS VARIABLE IS DEPENDENT ON JVM ARGUEMENT[BCAG.CurrenEnv]. IF IT IS LIVE, IT WILL GET THE LIST OF SERVERS FOR LIVE BASED ON
	 * BCAGSERVERLIST, ELSE, IT WILL TAKE THE SERVER LIST BASED ON DR_BCAGSERVERLIST
	 */
	bcagProperties.put(EncryptionConstants.THMPINENCRYPTSERVERS, getServerList(context));

	return bcagProperties;
    }

    private String getServerList(Context context) {
	Map<String, Object> contextMap = context.getContextMap();
	String serverString;

	if (DisasterRecoveryHelper.BCAG_DR_FLAG) {
	    serverString = (String) contextMap.get(DR + EncryptionConstants.THMPINENCRYPTSERVERS);
	    LOGGER.debug("BCAG.CurrenEnv=DR | Server list: " + serverString);
	} else {
	    serverString = (String) contextMap.get(EncryptionConstants.THMPINENCRYPTSERVERS);
	    LOGGER.debug("BCAG.CurrenEnv=LIVE | Server list: " + serverString);
	}

	return serverString;
    }

    private String getTHMUrl(Context context) {
	Map<String, Object> contextMap = context.getContextMap();
	String url;

	if (DisasterRecoveryHelper.THM_DR_FLAG) {
	    url = (String) contextMap.get(DR + THM_URL_EP);
	    LOGGER.debug("THM.CurrenEnv=DR | URL: " + url);
	} else {
	    url = (String) contextMap.get(THM_URL_EP);
	    if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("THM.CurrenEnv=LIVE | Server list: " + url);
	    }
	}
	return url;
    }

    public TacticalHelloMoneyVerifyUserService getThmLgnVrfyUsrPnService() {
	return thmLgnVrfyUsrPnService;
    }

    public void setThmLgnVrfyUsrPnService(TacticalHelloMoneyVerifyUserService thmLgnVrfyUsrPnService) {
	this.thmLgnVrfyUsrPnService = thmLgnVrfyUsrPnService;
    }

    public TacticalHelloMoneyEncryptPinService getThmEncryptPinService() {
	return thmEncryptPinService;
    }

    public void setThmEncryptPinService(TacticalHelloMoneyEncryptPinService thmEncryptPinService) {
	this.thmEncryptPinService = thmEncryptPinService;
    }

    public ThmHttpClientExecutor getThmHttpClientExecutor() {
	return thmHttpClientExecutor;
    }

    public void setThmHttpClientExecutor(ThmHttpClientExecutor thmHttpClientExecutor) {
	this.thmHttpClientExecutor = thmHttpClientExecutor;
    }

}