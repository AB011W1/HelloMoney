package com.barclays.bmg.audit.dao.impl;

import java.util.Calendar;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;

import com.barclays.bmg.audit.dao.AuditDAO;
import com.barclays.bmg.audit.dto.TransactionAuditDTO;
import com.barclays.bmg.audit.service.request.AuditServiceRequest;
import com.barclays.bmg.context.Context;
import com.barclays.mcfe.audit.jms.JMSSender;
import com.barclays.mcfe.audit.service.AuditBody;
import com.barclays.mcfe.audit.service.AuditHeader;
import com.barclays.mcfe.audit.service.AuditMCFE;
import com.barclays.mcfe.audit.service.Mcfe;
import com.barclays.mcfe.audit.service.TransactionAudit;

public class JMSAuditDAO implements AuditDAO {

    private JMSSender jmsSender;
    private StringBuilder makeHeader;
    private StringBuilder makeBody;

    private static final Logger LOGGER = Logger.getLogger(JMSAuditDAO.class);

    @Override
    public void auditTransaction(AuditServiceRequest request) {
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Start : JMSAuditDAO.auditTransaction() Activity Type :- " + request.getActivityId());
	}
	AuditMCFE auditMcfe = new AuditMCFE();
	try {
	    Mcfe mcfe = new Mcfe();
	    AuditHeader header = makeAuditHeader(request);
	    mcfe.setHeader(header);
	    AuditBody body = makeAuditBody(request);
	    mcfe.setBody(body);
	    auditMcfe.setMCFE(mcfe);

	    if (jmsSender != null) {
		jmsSender.send(auditMcfe);
	    }

	} catch (Exception e) {
	    LOGGER.error("Error : JMSAuditDAO.auditTransaction() Activity Type :- ", e);
	}
	LOGGER.debug("End : JMSAuditDAO.auditTransaction() Activity Type :- " + request.getActivityId());
    }

    private AuditHeader makeAuditHeader(AuditServiceRequest request) {
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Start : JMSAuditDAO.makeAuditHeader() Activity Type :- " + request.getActivityId());
	}
	AuditHeader auditHeader = new AuditHeader();
	Context context = request.getContext();
	makeHeader = new StringBuilder();
	String activityId = request.getActivityId();
	if (activityId == null || "".equals(activityId)) {
	    activityId = context.getActivityId();
	}
	auditHeader.setActivityId(activityId);
	makeHeader.append("activityId : ");
	makeHeader.append(auditHeader.getActivityId());

	auditHeader.setBusinessId(context.getBusinessId());
	makeHeader.append("\tBusinessId : ");
	makeHeader.append(auditHeader.getBusinessId());

	auditHeader.setCustomerCIF(context.getCustomerId());
	makeHeader.append("\tCustomerCd : ");
	makeHeader.append(auditHeader.getCustomerCIF());

	auditHeader.setSystemId(context.getSystemId());
	makeHeader.append("\tSystemId : ");
	makeHeader.append(auditHeader.getSystemId());

	auditHeader.setUserID(context.getLoginId());
	makeHeader.append("\tUserId : ");
	makeHeader.append(auditHeader.getUserID());

	auditHeader.setCustomerFname(context.getFullName() == null ? "" : context.getFullName());
	makeHeader.append("\tCustomerFname : ");
	makeHeader.append(auditHeader.getCustomerFname());
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("End : JMSAuditDAO.makeAuditHeader() Activity Type :- " + request.getActivityId());
	}
	return auditHeader;
    }

    @Override
    public String toString() {
	return "\n Header : " + makeHeader.toString() + "\nBody : " + makeBody.toString();
    }

    private AuditBody makeAuditBody(AuditServiceRequest request) {
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Start : JMSAuditDAO.makeAuditBody() Activity Type :- " + request.getActivityId());
	}

	AuditBody auditBody = new AuditBody();
	TransactionAudit audit = new TransactionAudit();
	TransactionAuditDTO dto = request.getTransactionAuditDTO();
	Context context = request.getContext();

	makeBody = new StringBuilder();

	audit.setBackEndId(dto.getBackEndId());
	audit.setBackEndRefNo(dto.getBackEndRefNo());
	audit.setBaseCurrencyAmount(dto.getBaseCurrencyAmount());
	audit.setFromAccount(dto.getFromAccount());

	makeBody.append("FromAccount : ");
	makeBody.append(dto.getFromAccount());

	audit.setFromAccountCurrency(dto.getFromAccountCurrency());

	audit.setFxRate(dto.getFxRate());
	audit.setToAccount(dto.getToAccount());
	makeBody.append("\tToAccount : ");
	makeBody.append(dto.getToAccount());

	audit.setToAccountCurrency(dto.getToAccountCurrency());
	audit.setTransactionAmount(dto.getTransactionAmount());
	makeBody.append("\tTransactionAmount : ");
	makeBody.append(dto.getTransactionAmount() + "\t " + dto.getTransactionCurrency());

	audit.setTransactionCurrency(dto.getTransactionCurrency());

	makeBody.append("Context : ");
	makeBody.append(context.toString());

	audit.setHttpSessionId(dto.getSessionId());
	audit.setCreditcardNo(dto.getCreditCardNo());
	audit.setAuditType("T");
	audit.setRemoteIPAddress(dto.getIpAddress());
	audit.setStepId(dto.getStepId());
	audit.setBeforeAfterInd(dto.getBaInd());
	audit.setTransactionRefNo(context.getActivityRefNo() == null ? context.getOrgRefNo() : context.getActivityRefNo());
	dto.setTransactionRefNo(context.getActivityRefNo() == null ? context.getOrgRefNo() : context.getActivityRefNo());
	makeBody.append("\tTransactionRefNo : ");
	makeBody.append(context.getActivityRefNo() == null ? context.getOrgRefNo() : context.getActivityRefNo());

	audit.setTransactionStatus(dto.getTransactionStatus());
	makeBody.append("\tStatus : ");
	makeBody.append(dto.getTransactionStatus());

	makeBody.append("\tTransactionDateTime : ");
	makeBody.append(dto.getTransactionDateTime());

	audit.setTransactionDateTime(getDateTime());

	audit.setReqRes(dto.getReqRes());
	makeBody.append("\tReqRes : ");
	makeBody.append(dto.getReqRes());

	audit.setActivityDateTime(Calendar.getInstance());

	auditBody.setTransaction(audit);
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("End : JMSAuditDAO.makeAuditBody() Activity Type :- " + request.getActivityId());
	}
	return auditBody;
    }

    private XMLGregorianCalendar getDateTime() {
	javax.xml.datatype.XMLGregorianCalendar xcal = null;
	try {
	    xcal = javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar(new java.util.GregorianCalendar());
	} catch (javax.xml.datatype.DatatypeConfigurationException e) {
	    LOGGER.error("Error in getDateTime", e);
	}

	return xcal;
    }

    public JMSSender getJmsSender() {
	return jmsSender;
    }

    public void setJmsSender(JMSSender jmsSender) {
	this.jmsSender = jmsSender;
    }
}
