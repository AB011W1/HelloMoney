package com.barclays.bmg.cashsend.operation;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.bmg.audit.annotation.AuditSupport;
import com.barclays.bmg.cashsend.operation.request.CashSendOneTimeExecuteOperationRequest;
import com.barclays.bmg.cashsend.operation.response.CashSendOneTimeExecuteOperationResponse;
import com.barclays.bmg.cashsend.service.CashSendOneTimeExecuteService;
import com.barclays.bmg.cashsend.service.request.CashSendOneTimeExecuteServiceRequest;
import com.barclays.bmg.cashsend.service.response.CashSendOneTimeExecuteServiceResponse;
import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.constants.SMSConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.helper.DBParamFetchHelper;
import com.barclays.bmg.helper.DisasterRecoveryHelper;
import com.barclays.bmg.helper.J2CAuthenticationDataUtil;
import com.barclays.bmg.operation.payments.BMBPaymentsOperation;
import com.barclays.bmg.service.product.request.ListValueResServiceRequest;
import com.barclays.bmg.service.product.response.ListValueResByGroupServiceResponse;
import com.barclays.bmg.service.request.dto.EncryptionConstants;
import com.barclays.bmg.ussd.auth.service.request.SMSDetailsServiceRequest;
import com.barclays.bmg.ussd.service.SMSDetailsService;
import com.barclays.ussd.utils.CrypterUtil;

public class CashSendOneTimeExecuteOperation extends BMBPaymentsOperation {

    private static final Logger LOGGER = Logger.getLogger(CashSendOneTimeExecuteOperation.class);

    private CashSendOneTimeExecuteService cashSendOneTimeExecuteService;
    @Autowired
    private J2CAuthenticationDataUtil j2cAuthenticationDataUtil;

    private static final String DR = "DR_";

    private SMSDetailsService smsDetailsService;

    public SMSDetailsService getSmsDetailsService() {
	return smsDetailsService;
    }

    public void setSmsDetailsService(SMSDetailsService smsDetailsService) {
	this.smsDetailsService = smsDetailsService;
    }

    @AuditSupport(auditType = AuditConstant.AUDIT_TYPE_TRANSACTION, activityState = AuditConstant.SRC_COM_SSC, serviceDescription = "SD_CASH_SEND", stepId = "3", activityType = "auditCashSend")
    public CashSendOneTimeExecuteOperationResponse executeCashSendOperation(CashSendOneTimeExecuteOperationRequest request) {

	CashSendOneTimeExecuteOperationResponse cashSendExecuteOperationResponse = new CashSendOneTimeExecuteOperationResponse();

	CashSendOneTimeExecuteServiceRequest cashSendExecuteServiceRequest = new CashSendOneTimeExecuteServiceRequest();

	Context context = request.getContext();

	cashSendExecuteServiceRequest.setContext(context);

	cashSendExecuteServiceRequest.setCashSendRequestDTO(request.getCashSendRequestDTO());

	// Change below code accordingly
	loadParameters(context, context.getActivityId(), ActivityConstant.COMMON_ID);

	CashSendOneTimeExecuteServiceResponse cashSendExecuteServiceResponse = cashSendOneTimeExecuteService
		.executeCashSend(cashSendExecuteServiceRequest);

	// Change below code accordingly get voucher ID from response

	cashSendExecuteOperationResponse.setSuccess(cashSendExecuteServiceResponse.isSuccess());
	cashSendExecuteOperationResponse.setTxnDt(cashSendExecuteServiceResponse.getTxnDt());
	cashSendExecuteOperationResponse.setTxnRefNo(cashSendExecuteServiceResponse.getTxnRefNo());
	cashSendExecuteOperationResponse.setVoucherId(cashSendExecuteServiceResponse.getSendCashResInfo().getVoucherId());

	cashSendExecuteOperationResponse.setResCde(cashSendExecuteServiceResponse.getResCde());
	cashSendExecuteOperationResponse.setResMsg(cashSendExecuteServiceResponse.getResMsg());

	// what to send in txn message
	// cashSendExecuteOperationResponse.setTxnMsg(cashSendExecuteServiceResponse.getTxnMsg());
	// System.out.println(chequeBookExecuteServiceResponse.getTxnRefNo());
	if (cashSendExecuteServiceResponse != null && cashSendExecuteServiceResponse.isSuccess()) {
	    upgradeTransactionLimit(cashSendExecuteServiceRequest, new BigDecimal(cashSendExecuteServiceRequest.getCashSendRequestDTO().getTxnAmt()));
	}
	return cashSendExecuteOperationResponse;
    }

    public CashSendOneTimeExecuteOperationResponse encryptCashSendPin(CashSendOneTimeExecuteOperationRequest request) {
	CashSendOneTimeExecuteOperationResponse cashSendExecuteOperationResponse = new CashSendOneTimeExecuteOperationResponse();

	CashSendOneTimeExecuteServiceRequest cashSendExecuteServiceRequest = new CashSendOneTimeExecuteServiceRequest();

	Context context = request.getContext();
	cashSendExecuteServiceRequest.setContext(context);

	loadParameters(context, context.getActivityId(), ActivityConstant.COMMON_ID);

	cashSendExecuteServiceRequest.setContext(context);
	cashSendExecuteServiceRequest.setCashSendRequestDTO(request.getCashSendRequestDTO());
	cashSendExecuteServiceRequest.setBcagProperties(fetchBCAGProperties(context));

	CashSendOneTimeExecuteServiceResponse cashSendExecuteServiceResponse = cashSendOneTimeExecuteService
		.encryptCashSendPin(cashSendExecuteServiceRequest);
	if (cashSendExecuteServiceResponse != null)
	    cashSendExecuteOperationResponse.setPin(cashSendExecuteServiceResponse.getPin());
	return cashSendExecuteOperationResponse;
    }

    private Properties fetchBCAGProperties(Context context) {
	Properties bcagProperties = new Properties();

	bcagProperties.put(EncryptionConstants.CASHSENDKEYSTORE, DBParamFetchHelper.getSysParamValue(EncryptionConstants.CASHSENDKEYSTORE));
	bcagProperties.put(EncryptionConstants.CASHSENDKEYSTOREKEY, DBParamFetchHelper.getSysParamValue(EncryptionConstants.CASHSENDKEYSTOREKEY));
	bcagProperties.put(EncryptionConstants.CASHSENDENCRYPTLOGIC, DBParamFetchHelper.getSysParamValue(EncryptionConstants.CASHSENDENCRYPTLOGIC));

	// Read Encrypeted UserName and Password from DB
	String hexEncryptedUserName = DBParamFetchHelper.getSysParamValue(EncryptionConstants.CASHSENDUSERNAME);
	String hexEncryptedUserKey = DBParamFetchHelper.getSysParamValue(EncryptionConstants.CASHSENDUSERKEY);
	// Decrypt UserName,Password using CrypterUtil.
	bcagProperties.put(EncryptionConstants.CASHSENDUSERNAME, decrypt(hexEncryptedUserName));
	bcagProperties.put(EncryptionConstants.CASHSENDUSERKEY, decrypt(hexEncryptedUserKey));

	// CHANGE THIS TO ENCRYPT/DECRYPT == ENDS

	/*
	 * THIS VARIABLE IS DEPENDENT ON JVM ARGUEMENT[BCAG.CurrenEnv]. IF IT IS LIVE, IT WILL GET THE LIST OF SERVERS FOR LIVE BASED ON
	 * BCAGSERVERLIST, ELSE, IT WILL TAKE THE SERVER LIST BASED ON DR_BCAGSERVERLIST
	 */
	bcagProperties.put(EncryptionConstants.CASHSENDSERVERS, getServerList(context));

	return bcagProperties;
    }

    private String getServerList(Context context) {
	Map<String, Object> contextMap = context.getContextMap();
	String serverString;

	if (DisasterRecoveryHelper.BCAG_DR_FLAG) {
	    serverString = (String) contextMap.get(DR + EncryptionConstants.CASHSENDSERVERS);
	    LOGGER.debug("BCAG.CurrenEnv=DR | Server list: " + serverString);
	} else {
	    serverString = (String) contextMap.get(EncryptionConstants.CASHSENDSERVERS);
	    LOGGER.debug("BCAG.CurrenEnv=LIVE | Server list: " + serverString);
	}

	return serverString;
    }

    private String decrypt(String hexencoded) {

	byte[] byteArrayToDecrypt = null;
	byte[] decrypt = null;
	try {
	    byteArrayToDecrypt = Hex.decodeHex(hexencoded.toCharArray());
	    // Read IV and KEY from WAS and Decrypt it using CrypterUtil
	    decrypt = CrypterUtil.decrypt(j2cAuthenticationDataUtil.getIV().getBytes(), j2cAuthenticationDataUtil.getKEY().getBytes(),
	     byteArrayToDecrypt);
	    // TODO Remove HardCoding
	    //decrypt = CrypterUtil.decrypt("770A8A65DA156D24".getBytes(), "990A8A65DA156D24".getBytes(), byteArrayToDecrypt);

	} catch (Exception e) {
	    LOGGER.error("decrypt exception occured  : " + e);
	    // throw e;
	}
	return new String(decrypt);

    }

    public void sendSMSSuccessAcknowledgment(CashSendOneTimeExecuteOperationRequest request, CashSendOneTimeExecuteOperationResponse response) {

	SMSDetailsServiceRequest smsRequest = new SMSDetailsServiceRequest();
	Context context = request.getContext();

	loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);
	smsRequest.setContext(context);

	smsRequest.setParentResponse(response);
	smsRequest.setParentRequest(request);
	smsRequest.setEvent(getSMSEvent(request, SMSConstants.SMS_CS_SUCCESS));
	smsRequest.setPriority(getSMSPriority(request, SMSConstants.SMS_CS_SUCCESS_PRIORITY));
	smsRequest.setDynamicFields(getDynamicFields(request, SMSConstants.SMS_CS_FIELD_SUCCESS));

	smsDetailsService.getSMSDetails(smsRequest);
    }

    public void sendSMSFailAcknowledgment(CashSendOneTimeExecuteOperationRequest request, CashSendOneTimeExecuteOperationResponse response) {

	SMSDetailsServiceRequest smsRequest = new SMSDetailsServiceRequest();
	Context context = request.getContext();

	loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);
	smsRequest.setContext(context);

	smsRequest.setParentResponse(response);
	smsRequest.setParentRequest(request);

	smsRequest.setEvent(getSMSEvent(request, SMSConstants.SMS_CS_FAIL));
	smsRequest.setPriority(getSMSPriority(request, SMSConstants.SMS_CS_FAIL_PRIORITY));
	smsRequest.setDynamicFields(getDynamicFields(request, SMSConstants.SMS_CS_FIELD_FAIL));

	smsDetailsService.getSMSDetails(smsRequest);
    }

    private String getSMSEvent(CashSendOneTimeExecuteOperationRequest request, String smsKey) {
	ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
	listValueResServiceRequest.setContext(request.getContext());
	listValueResServiceRequest.setListValueKey(smsKey);
	ListValueResByGroupServiceResponse listValueResByGroupServiceResponse = listValueResService.getListValueByKey(listValueResServiceRequest);
	String event = listValueResByGroupServiceResponse.getListValueCahceDTO().get(0).getLabel();
	return event;
    }

    private String getSMSPriority(CashSendOneTimeExecuteOperationRequest request, String smsKey) {
	ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
	listValueResServiceRequest.setContext(request.getContext());
	listValueResServiceRequest.setListValueKey(smsKey);
	ListValueResByGroupServiceResponse listValueResByGroupServiceResponse = listValueResService.getListValueByKey(listValueResServiceRequest);
	String priority = listValueResByGroupServiceResponse.getListValueCahceDTO().get(0).getLabel();
	return priority;

    }

    private String getDynamicFields(CashSendOneTimeExecuteOperationRequest request, String smsKey) {
	ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
	listValueResServiceRequest.setContext(request.getContext());
	listValueResServiceRequest.setListValueKey(smsKey);
	ListValueResByGroupServiceResponse listValueResByGroupServiceResponse = listValueResService.getListValueByKey(listValueResServiceRequest);
	String event = listValueResByGroupServiceResponse.getListValueCahceDTO().get(0).getLabel();
	return event;
    }

    public CashSendOneTimeExecuteService getCashSendOneTimeExecuteService() {
	return cashSendOneTimeExecuteService;
    }

    public void setCashSendOneTimeExecuteService(CashSendOneTimeExecuteService cashSendOneTimeExecuteService) {
	this.cashSendOneTimeExecuteService = cashSendOneTimeExecuteService;
    }

    public J2CAuthenticationDataUtil getJ2cAuthenticationDataUtil() {
	return j2cAuthenticationDataUtil;
    }

    public void setJ2cAuthenticationDataUtil(J2CAuthenticationDataUtil authenticationDataUtil) {
	j2cAuthenticationDataUtil = authenticationDataUtil;
    }

}
