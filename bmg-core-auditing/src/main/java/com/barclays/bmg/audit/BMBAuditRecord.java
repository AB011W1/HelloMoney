package com.barclays.bmg.audit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.barclays.bmg.audit.annotation.AuditSupport;
import com.barclays.bmg.audit.builder.BMGTransactionAuditBuilder;
import com.barclays.bmg.audit.dto.FieldDataDTO;
import com.barclays.bmg.audit.dto.ScreenDataDTO;
import com.barclays.bmg.audit.dto.TransactionAuditDTO;
import com.barclays.bmg.audit.service.AuditService;
import com.barclays.bmg.audit.service.request.AuditServiceRequest;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.constants.BMGConstants;
import com.barclays.bmg.constants.ErrorCodeConstant;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.exception.BMBDataAccessException;
import com.barclays.bmg.service.MessageResourceService;
import com.barclays.bmg.service.request.MessageResourceServiceRequest;
import com.barclays.bmg.service.response.MessageResourceServiceResponse;

@Aspect
public class BMBAuditRecord implements ApplicationContextAware {

    AuditService auditService;
    MessageResourceService messageResourceService;
    BMGTransactionAuditBuilder auditBuilder;
    private ApplicationContext applicationContext;

    private static final Logger LOGGER = Logger.getLogger(BMBAuditRecord.class);

    private static final String SCREEN_ELEM = "screen";
    private static final String FIELD_ELEM = "field";

    private static final String BAIND_BEFR = "B";
    private static final String BAIND_AFTR = "A";

    @Around("@annotation(auditSupport)")
    public Object recordAuditLog(ProceedingJoinPoint joinpoint, AuditSupport auditSupport) throws Throwable {

	Object result = null;
	String auditType = auditSupport.auditType();

	if (AuditConstant.AUDIT_TYPE_TRANSACTION.equals(auditType)) {
	    auditBuilder = (BMGTransactionAuditBuilder) applicationContext.getBean(auditSupport.activityType());

	    if (auditBuilder != null) {
		result = auditTransaction(joinpoint, auditSupport);
	    }
	} else {
	    try {
		result = joinpoint.proceed();
	    } catch (Throwable e) {
		if (e instanceof BMBDataAccessException) {
		    throw (BMBDataAccessException) e;
		} else {
		    throw e;
		}
	    }
	}
	return result;
    }

    /*
     * log transaction audit
     */
    private Object auditTransaction(ProceedingJoinPoint joinpoint, AuditSupport auditSupport) throws Throwable {
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Start : auditTransaction() Activity Type :- " + auditSupport.activityType());
	}
	Object result = null;
	Object args[] = joinpoint.getArgs();
	AuditServiceRequest auditFromRequest = new AuditServiceRequest();

	try {
	    auditFromRequest = logAuditTransactionRequest(args, auditSupport, result);
	} catch (Exception e) {
	    LOGGER.error("Error in audit Transaction from logAuditTransactionRequest ", e);
	}

	try {
	    result = joinpoint.proceed();
	    logAuditTransactionResponse(args, auditSupport, result);
	} catch (Throwable e) {
	    if (e instanceof BMBDataAccessException) {
		auditFailureResponse(args, auditSupport, ((BMBDataAccessException) e).getErrorCode(), auditFromRequest, result);
		throw (BMBDataAccessException) e;
	    } else {
		LOGGER.error("Error in audit Transaction from logAuditTransactionResponse ", e);
	    }
	}
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("End : auditTransaction() Activity Type :- " + auditSupport.activityType());
	}
	return result;
    }

    private void auditFailureResponse(Object[] args, AuditSupport auditSupport, String errorCode, AuditServiceRequest auditServiceRequest,
	    Object result) {
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Start : auditFailureResponse() Activity Type :- " + auditSupport.activityType());
	}
	TransactionAuditDTO transactionAuditDTO = new TransactionAuditDTO();

	ArrayList<String> inprogressErrorCodeList = (ArrayList<String>) applicationContext.getBean("inprogressErrorCodeList");

	if (auditServiceRequest != null) {
	    transactionAuditDTO = auditServiceRequest.getTransactionAuditDTO();
	}

	String message = null;
	String tempErrorCode = null;

	if (args != null) {
	    setRequestContext(args, auditServiceRequest);

	    if (inprogressErrorCodeList.contains(errorCode)) {
		transactionAuditDTO.setTransactionStatus(AuditConstant.TXN_STATUS_INPROGRESS);
	    } else {
		transactionAuditDTO.setTransactionStatus(AuditConstant.TXN_STATUS_FAILURE);
	    }

	    if (errorCode != null) {
		if (!errorCode.isEmpty() && errorCode.contains("BMB")) {
		    message = getMessage(auditServiceRequest, errorCode);
		} else {
		    tempErrorCode = ErrorCodeConstant.BEM_MESSAGE_PREFIX + errorCode;
		    message = getMessage(auditServiceRequest, tempErrorCode);
		}

	    } else {
		tempErrorCode = "BMB99999";
		message = getMessage(auditServiceRequest, tempErrorCode);
	    }

	    ScreenDataDTO screenData = new ScreenDataDTO();

	    FieldDataDTO field16 = new FieldDataDTO();

	    field16.setFieldId(AuditConstant.FAILURE_REASON);
	    field16.setValue(message);
	    screenData.addField(field16);

	    FieldDataDTO field17 = new FieldDataDTO();
	    field17.setFieldId(AuditConstant.ERRCD);
	    if (tempErrorCode != null)
		field17.setValue(tempErrorCode);
	    else
		field17.setValue("");
	    screenData.addField(field17);

	    try {
		String step = auditSupport.stepId();
		if (step != null) {
		    if (!step.isEmpty()) {
			Integer stepId = Integer.parseInt(step);
			transactionAuditDTO.setStepId(stepId);
		    }
		}
	    } catch (Exception e) {
	    }

	    transactionAuditDTO.setSessionId(BMBContextHolder.getContext().getSessionId());
	    auditServiceRequest.setActivityId(auditSupport.activityId());
	    auditServiceRequest.getTransactionAuditDTO().setBaInd(BAIND_AFTR);

	    Map<String, String> bmgAttribs = new HashMap<String, String>();
	    bmgAttribs.put(AuditConstant.FAILURE_REASON, message);
	    bmgAttribs.put(AuditConstant.ERRCD, tempErrorCode);
	    this.addAdditionalFields(transactionAuditDTO, bmgAttribs);

	    auditServiceRequest.setTransactionAuditDTO(transactionAuditDTO);
	    auditService.auditTransaction(auditServiceRequest);
	}
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("End : auditFailureResponse() Activity Type :- " + auditSupport.activityType());
	}
    }

    private void setRequestContext(Object[] args, AuditServiceRequest auditServiceRequest) {
	int leng = args.length;
	RequestContext request;

	if (leng > 1)
	    request = (RequestContext) args[1];
	else
	    request = (RequestContext) args[0];

	auditServiceRequest.setContext(request.getContext());
    }

    protected String buildData(ScreenDataDTO screenData) {
	StringBuffer buffer = new StringBuffer();
	if (screenData != null) {
	    buffer.append("<");
	    buffer.append(SCREEN_ELEM);
	    buffer.append(" id=\"");
	    buffer.append(screenData.getScreenId());
	    buffer.append("\">");
	    for (FieldDataDTO field : screenData.getFieldList()) {
		if (field.getValue() != null) {
		    buffer.append("<");
		    buffer.append(FIELD_ELEM);
		    buffer.append(" id=\"");
		    buffer.append(field.getFieldId());
		    buffer.append("\" value=\"");
		    buffer.append(field.getValue());
		    buffer.append("\"/>");
		}
	    }
	    buffer.append("</");
	    buffer.append(SCREEN_ELEM);
	    buffer.append(">");
	}

	return buffer.toString();
    }

    private String getMessage(AuditServiceRequest auditServiceRequest, String errorCode) {
	String paramValue = null;

	MessageResourceServiceRequest messageRequest = new MessageResourceServiceRequest();
	messageRequest.setContext(auditServiceRequest.getContext());
	messageRequest.setMessageKey(errorCode);

	MessageResourceServiceResponse response = messageResourceService.getMessageDescByKey(messageRequest);

	if (response != null && response.getMessageDesc() != null) {
	    paramValue = response.getMessageDesc();
	}
	return paramValue;

    }

    public AuditServiceRequest logAuditTransactionRequest(Object args[], AuditSupport auditSupport, Object result) {
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Start : logAuditTransactionRequest() Activity Type :- " + auditSupport.activityType());
	}
	AuditServiceRequest auditServiceRequest = new AuditServiceRequest();
	if (args != null) {
	    setRequestContext(args, auditServiceRequest);
	    auditServiceRequest.setTransactionAuditDTO(auditBuilder.buildFromRequest(args, result));
	    prepareRequestTransaction(auditServiceRequest, auditSupport, result);
	    auditService.auditTransaction(auditServiceRequest);
	}
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("End : logAuditTransactionRequest() Activity Type :- " + auditSupport.activityType());
	}
	return auditServiceRequest;
    }

    public void prepareRequestTransaction(AuditServiceRequest auditServiceRequest, AuditSupport auditSupport, Object result) {
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Start : prepareRequestTransaction() Activity Type :- " + auditSupport.activityType());
	}
	TransactionAuditDTO transactionAuditDTO = auditServiceRequest.getTransactionAuditDTO();
	transactionAuditDTO.setBaInd(BAIND_BEFR);
	prepareTransaction(auditServiceRequest, auditSupport, result);
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("End : prepareRequestTransaction() Activity Type :- " + auditSupport.activityType());
	}
    }

    public void logAuditTransactionResponse(Object args[], AuditSupport auditSupport, Object result) {
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Start : logAuditTransactionResponse() Activity Type :- " + auditSupport.activityType());
	}
	AuditServiceRequest auditServiceRequest = new AuditServiceRequest();
	if (args != null) {
	    setRequestContext(args, auditServiceRequest);
	    auditServiceRequest.setTransactionAuditDTO(auditBuilder.buildFromResponse(args, result));
	    prepareResponseTransaction(auditServiceRequest, auditSupport, result);
	    auditService.auditTransaction(auditServiceRequest);
	}
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("End : logAuditTransactionResponse() Activity Type :- " + auditSupport.activityType());
	}
    }

    public void prepareResponseTransaction(AuditServiceRequest auditServiceRequest, AuditSupport auditSupport, Object result) {
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Start : prepareResponseTransaction() Activity Type :- " + auditSupport.activityType());
	}
	TransactionAuditDTO transactionAuditDTO = auditServiceRequest.getTransactionAuditDTO();
	transactionAuditDTO.setBaInd(BAIND_AFTR);
	prepareTransaction(auditServiceRequest, auditSupport, result);
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("End : prepareResponseTransaction() Activity Type :- " + auditSupport.activityType());
	}

    }

    public void prepareTransaction(AuditServiceRequest auditServiceRequest, AuditSupport auditSupport, Object result) {
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Start : prepareTransaction() Activity Type :- " + auditSupport.activityType());
	}
	TransactionAuditDTO transactionAuditDTO = auditServiceRequest.getTransactionAuditDTO();
	try {
	    String step = auditSupport.stepId();
	    if (step != null) {
		if (!step.isEmpty()) {
		    Integer stepId = Integer.parseInt(step);
		    transactionAuditDTO.setStepId(stepId);
		}
	    }
	} catch (Exception e) {
	}

	transactionAuditDTO.setSessionId(BMBContextHolder.getContext().getSessionId());
	auditServiceRequest.setActivityId(auditSupport.activityId());

	// add BMG attributes
	Map<String, String> bmgAttribs = new HashMap<String, String>(5);
	bmgAttribs.put(BMGConstants.USER_NAME, auditServiceRequest.getContext().getFullName());
	this.addAdditionalFields(transactionAuditDTO, bmgAttribs);
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("End : prepareTransaction() Activity Type :- " + auditSupport.activityType());
	}
    }

    public String retreiveServiceDesc(AuditServiceRequest auditServiceRequest, AuditSupport auditSupport) {

	String paramValue = AuditConstant.WHITESPACE;
	MessageResourceServiceRequest messageRequest = new MessageResourceServiceRequest();
	messageRequest.setContext(auditServiceRequest.getContext());
	messageRequest.setMessageKey(auditSupport.serviceDescription());

	MessageResourceServiceResponse response = messageResourceService.getMessageDescByKey(messageRequest);

	if (response != null && response.getMessageDesc() != null) {
	    paramValue = response.getMessageDesc();
	}
	return paramValue;
    }

    /**
     * TODO Method description, including pre/post conditions and invariants.
     * 
     * @param fieldValue
     * @param fieldName
     * @return
     */
    private String buildFieldElement(String fieldName, String fieldValue) {
	StringBuilder buffer = new StringBuilder();
	buffer.append("<field id=\"").append(fieldName).append("\" value=\"").append(fieldValue).append("\"/>");
	return buffer.toString();
    }

    /**
     * TODO Method description, including pre/post conditions and invariants.
     * 
     * @param txnAudit
     * @param additionElement
     */
    private void addAditionalElement(TransactionAuditDTO txnAudit, String additionElement) {
	String reqres = txnAudit.getReqRes();
	StringBuilder buffer = new StringBuilder();
	if (!StringUtils.isEmpty(reqres)) {
	    buffer.append(reqres.substring(0, reqres.indexOf(">") + 1)).append(additionElement).append(reqres.substring(reqres.indexOf(">") + 1));
	} else {
	    buffer.append("<screen id=\"DEFAULT_SCREEN_ID\">").append(additionElement).append("</screen>");
	}
	txnAudit.setReqRes(buffer.toString());
    }

    private void addAdditionalFields(TransactionAuditDTO txnAudit, Map<String, String> additionalFileds) {
	for (Iterator<Map.Entry<String, String>> iterator = additionalFileds.entrySet().iterator(); iterator.hasNext();) {
	    Map.Entry<String, String> entry = iterator.next();
	    String key = entry.getKey();
	    String value = entry.getValue();

	    addAditionalElement(txnAudit, buildFieldElement(key, value));
	}
    }

    public AuditService getAuditService() {
	return auditService;
    }

    public void setAuditService(AuditService auditService) {
	this.auditService = auditService;
    }

    public BMGTransactionAuditBuilder getAuditBuilder() {
	return auditBuilder;
    }

    public void setAuditBuilder(BMGTransactionAuditBuilder auditBuilder) {
	this.auditBuilder = auditBuilder;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	this.applicationContext = applicationContext;

    }

    public MessageResourceService getMessageResourceService() {
	return messageResourceService;
    }

    public void setMessageResourceService(MessageResourceService messsaResourceService) {
	this.messageResourceService = messsaResourceService;
    }
}