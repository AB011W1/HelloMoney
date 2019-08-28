package com.barclays.bmg.ussd.dao.adapter;

import java.util.Calendar;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.barclays.BEMReqHeader;
import com.barclays.BankUserContextReq;
import com.barclays.ConsumerContextReq;
import com.barclays.CustomerContextReq;
import com.barclays.ServiceContext;
import com.barclays.bem.BEMBaseDataTypes.ProductProcessorTypeCode;
import com.barclays.bmg.constants.ServiceIdConstants;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.utils.DateTimeUtil;

public class SMSDetailsHeaderAdapter {

    protected static final String AUTHORIZEDMODE_OTP = "OTP";
    private final static String NO = "N";

    public BEMReqHeader buildRequestHeader(WorkContext workContext) {

	BEMReqHeader reqHeader = new BEMReqHeader();

	DAOContext daoContext = (DAOContext) workContext;

	Object[] args = daoContext.getArguments();

	RequestContext request = (RequestContext) args[0];
	Context context = request.getContext();

	reqHeader.setBankUserContext(createBankUserContext(context));
	reqHeader.setConsumerContext(createConsumerContextReq(context));
	reqHeader.setCustomerContext(createCustomerContextReq(context));

	reqHeader.setServiceContext(createServiceContext(context, ServiceIdConstants.SMS_ACKNOWLEDGEMENT));
	return reqHeader;
    }

    private String checkAuthorizedMode(Context context) {
	String authorizedMode = "";
	Map<String, Object> contextMap = context.getContextMap();
	String authMode = (String) contextMap.get(SystemParameterConstant.SECOND_AUTH_TYPE);

	if (StringUtils.isEmpty(authMode)) {
	    authorizedMode = authMode;
	}
	return authorizedMode;
    }

    private BankUserContextReq createBankUserContext(Context context) {

	BankUserContextReq bankUserContextReq = new BankUserContextReq();
	Map<String, Object> contextMap = context.getContextMap();

	bankUserContextReq.setRESP_POPULATE_FLAG(true);
	bankUserContextReq.setStaffType(contextMap.get(SystemParameterConstant.SERVICE_HEADER_STAFF_TYPE).toString());

	if (AUTHORIZEDMODE_OTP.equals(checkAuthorizedMode(context))) {
	    bankUserContextReq.setAuthorizerID(contextMap.get(SystemParameterConstant.SERVICE_HEADER_AUTHORIZER_ID_OTP).toString());
	} else {
	    bankUserContextReq.setAuthorizerID(contextMap.get(SystemParameterConstant.SERVICE_HEADER_AUTHORIZER_ID).toString());
	}
	bankUserContextReq.setAuthorizerID(contextMap.get(SystemParameterConstant.SERVICE_HEADER_AUTHORIZER_ID).toString());
	bankUserContextReq.setStaffID(contextMap.get(SystemParameterConstant.SERVICE_HEADER_STAFF_ID).toString());

	return bankUserContextReq;
    }

    private ConsumerContextReq createConsumerContextReq(Context context) {
	ConsumerContextReq consumerContextReq = new ConsumerContextReq();
	Map<String, Object> contextMap = context.getContextMap();
	consumerContextReq.setBusinessID(context.getBusinessId());
	consumerContextReq.setCountryCode(context.getCountryCode());
	consumerContextReq.setSystemID(context.getSystemId());
	// consumerContextReq.setSystemID("IB");

	consumerContextReq.setBranchCode(contextMap.get(SystemParameterConstant.SERVICE_HEADER_BRANCH_CODE).toString());
	consumerContextReq.setTerminalID(contextMap.get(SystemParameterConstant.SERVICE_HEADER_TERMINAL_ID).toString());
	return consumerContextReq;
    }

    private CustomerContextReq createCustomerContextReq(Context context) {
	CustomerContextReq customerContextReq = new CustomerContextReq();
	Map<String, Object> contextMap = context.getContextMap();
	// Need to know what is Login Name.
	if (context.getLoginId() != null && !"".equals(context.getLoginId().trim())) {
	    customerContextReq.setConsumerAccessID(context.getLoginId());
	} else {
	    customerContextReq.setConsumerAccessID(contextMap.get(SystemParameterConstant.SERVICE_HEADER_CUST_ACCESS_ID).toString());

	}

	customerContextReq.setCorpUserID(contextMap.get(SystemParameterConstant.SERVICE_HEADER_CORP_USER_ID).toString());
	customerContextReq.setCustomerType(contextMap.get(SystemParameterConstant.SERVICE_HEADER_CUST_TYPE).toString());
	customerContextReq.setRESP_POPULATE_FLAG(getBooleanFlag(contextMap, SystemParameterConstant.SERVICE_HEADER_RESP_POPULATE_FLAG));

	// if (context.getScvId() == null) needs to be added if req for
	// setMasterCIFID
	if (context.getCustomerId() == null) {
	    customerContextReq.setMasterCIFID(contextMap.get(SystemParameterConstant.SERVICE_HEADER_MASTER_CIF_ID).toString());
	} else {
	    customerContextReq.setMasterCIFID(context.getCustomerId());
	}

	if (context.getPpMap() != null) {
	    customerContextReq.setCustomerNumber(context.getPpMap().get(ProductProcessorTypeCode._BK));
	}

	return customerContextReq;

    }

    private ServiceContext createServiceContext(Context context, String serviceId) {

	ServiceContext serviceContext = new ServiceContext();
	Map<String, Object> contextMap = context.getContextMap();

	serviceContext.setConsumerUniqueRefNo(context.getActivityRefNo());
	serviceContext.setOriginalConsumerUniqueRefNo(context.getActivityRefNo());

	serviceContext.setServiceDateTime(DateTimeUtil.getCurrentBusinessCalendar(context));
	serviceContext.setServiceID(serviceId);

	serviceContext.setServiceVersionNo(contextMap.get(SystemParameterConstant.SERVICE_HEADER_SERVICE_VER_NO).toString());

	return serviceContext;
    }

    public Calendar getCurrentBusinessCalendar(Context context) {
	Calendar cal = Calendar.getInstance();
	int offset = cal.get(Calendar.ZONE_OFFSET) / 3600000 + cal.get(Calendar.DST_OFFSET) / 3600000;
	Double bos = Double.valueOf(Double.valueOf(context.getTimezoneOffset()) * 60);
	cal.add(Calendar.MINUTE, (-offset * 60 + bos.intValue()));
	return cal;
    }

    private boolean getBooleanFlag(Map<String, Object> contextMap, String key) {
	if (NO.equals(contextMap.get(key))) {
	    return false;
	}
	return true;
    }

    /**
     * This method buildSMSDetailsReqHeader has the purpose to build request header
     * 
     * @param WorkContext
     *            void
     */
    /*
     * public BEMReqHeader buildSMSDetailsReqHeader(WorkContext workContext) { return super.buildRequestHeader(workContext,
     * ServiceIdConstants.SMS_ACKNOWLEDGEMENT);
     * 
     * //return new com.barclays.BEMReqHeader(); }
     */

}
