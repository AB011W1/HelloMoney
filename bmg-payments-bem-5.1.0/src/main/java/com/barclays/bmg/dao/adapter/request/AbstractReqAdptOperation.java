package com.barclays.bmg.dao.adapter.request;

import java.util.Calendar;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import com.barclays.bem.BEMBaseDataTypes.ProductProcessorTypeCode;
import com.barclays.bem.BEMServiceHeader.BEMReqHeader;
import com.barclays.bem.BEMServiceHeader.BankUserContextReq;
import com.barclays.bem.BEMServiceHeader.ConsumerContextReq;
import com.barclays.bem.BEMServiceHeader.CustomerContextReq;
import com.barclays.bem.BEMServiceHeader.OverrideDetails;
import com.barclays.bem.BEMServiceHeader.ServiceContext;
import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.ActivityIdConstantBean;
import com.barclays.bmg.constants.ServiceIdConstants;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.service.request.BillerServiceRequest;
import com.barclays.bmg.service.request.DomesticFundTransferServiceRequest;
import com.barclays.bmg.service.request.PayBillServiceRequest;
import com.barclays.bmg.utils.DateTimeUtil;
//import com.barclays.ussd.bean.BillersListDO;

public class AbstractReqAdptOperation {
	protected static final String AUTHORIZEDMODE_OTP = "OTP";
	private final static String NO = "N";
	//CR#46
	private final static String STAFF_ID_BIR ="SHM";
	private String SERVICE_VERSION_CASA_CC;

	protected BEMReqHeader buildRequestHeader(WorkContext workContext, String serviceId) {

		BEMReqHeader reqHeader = new BEMReqHeader();

		DAOContext daoContext = (DAOContext) workContext;

		Object[] args = daoContext.getArguments();

		RequestContext request = (RequestContext) args[0];
		Context context = request.getContext();
        //CBP Change
		Map<String, Object> contextMap = context.getContextMap();


		reqHeader.setBankUserContext(createBankUserContext(context));
		reqHeader.setCustomerContext(createCustomerContextReq(context));
		reqHeader.setConsumerContext(createConsumerContextReq(context));


		// Service version no. check for CASA applicable not for CreditCard
		if(serviceId !=null && serviceId.equals("MakeBillPayment")){
			RequestContext serviceRequest = (RequestContext) args[0];
			PayBillServiceRequest payBillServiceRequest = (PayBillServiceRequest)serviceRequest;
			CustomerAccountDTO fromAccount = payBillServiceRequest.getFromAccount();
			if(null != payBillServiceRequest.getBeneficiaryDTO() && null != payBillServiceRequest.getBeneficiaryDTO().getBillerId() 
					&& payBillServiceRequest.getBeneficiaryDTO().getBillerId().endsWith("-8") 
					&& !payBillServiceRequest.getBeneficiaryDTO().getPresentmentFlag()){
				SERVICE_VERSION_CASA_CC = "GePG";
			}else if (fromAccount instanceof CreditCardAccountDTO){
				SERVICE_VERSION_CASA_CC = "CC";
			} else {
				SERVICE_VERSION_CASA_CC = "CASA";
			}
		}else if(serviceId !=null && serviceId.equals("MakeDomesticFundTransfer")){
			RequestContext serviceRequest = (RequestContext) args[0];
			DomesticFundTransferServiceRequest domesticFundServiceRequest = (DomesticFundTransferServiceRequest)serviceRequest;
			CustomerAccountDTO fromAccount = domesticFundServiceRequest.getSourceAcct();
			if (fromAccount instanceof CreditCardAccountDTO){
				SERVICE_VERSION_CASA_CC = "CC";
			}else{
				SERVICE_VERSION_CASA_CC = "CASA";
			}
		} else if(serviceId !=null && serviceId.equals("MakeDomesticFundTransfer")){

		} else if(serviceId !=null && serviceId.equals("SSAMakeBillPayment")) {
		     RequestContext serviceRequest = (RequestContext) args[0];
		     PayBillServiceRequest payBillServiceRequest = (PayBillServiceRequest)serviceRequest;
		     if(null != payBillServiceRequest.getBeneficiaryDTO() && "Probase".equalsIgnoreCase(payBillServiceRequest.getBeneficiaryDTO().getBillAggregatorId())){
		    	 SERVICE_VERSION_CASA_CC = "Probase";
		     }
		}

		reqHeader.setServiceContext(createServiceContext(context, serviceId));

		String opcode = context.getOpCde();
		if(opcode!= null && (opcode.equalsIgnoreCase("OP0978")||opcode.equalsIgnoreCase("OP0981")||opcode.equalsIgnoreCase("OP0983")) && context.getBusinessId().equalsIgnoreCase("KEBRB") ){

			if(context.getActivityId().equalsIgnoreCase("KITS_DEREGISTRATION_LOOKUP"))
			{
				reqHeader.setOverrideList(createOverrideListDeregInfo(context));

			}else
			{
				reqHeader.setOverrideList(createOverrideList(context));
			}

		}
		if(opcode!= null && (opcode.equalsIgnoreCase("OP0979")||opcode.equalsIgnoreCase("OP0982")) && context.getBusinessId().equalsIgnoreCase("KEBRB") ){

			if(context.getActivityId().equalsIgnoreCase("KITS_REGISTRATION"))
			{
				reqHeader.setOverrideList(createOverrideListReg(context));
			}else if(context.getActivityId().equalsIgnoreCase("KITS_DEREGISTRATION"))
			{
				reqHeader.setOverrideList(createOverrideListDeReg(context));
			}

		}
		if(opcode!= null && opcode.equalsIgnoreCase("OP0980") && context.getBusinessId().equalsIgnoreCase("KEBRB")){
			if(context.getActivityId().equalsIgnoreCase("KITS_PTP_BILLPAY"))
			{
				reqHeader.setOverrideList(createOverrideListPTPBillPay(context));

			}else if(context.getActivityId().equalsIgnoreCase("KITS_PTA_BILLPAY"))
			{
				reqHeader.setOverrideList(createOverrideListPTABillPay(context));
			}
		}
		if(opcode!= null && opcode.equalsIgnoreCase("OP0990") && context.getBusinessId().equalsIgnoreCase("GHBRB")){
			reqHeader.setOverrideList(createOverrideListGHIPSSNQ(context));
		}
		if(opcode!= null && opcode.equalsIgnoreCase("OP0991") && context.getBusinessId().equalsIgnoreCase("GHBRB")){
			if(context.getActivityId()!=null && context.getActivityId().equals(ActivityIdConstantBean.GHIPPS_FUND_TRANSFER_OTHER_BANK_ACCOUNTS)&& serviceId.equals(ServiceIdConstants.SERVICE_MAKE_DOMESTIC_FUND_TRANSFER)){
				reqHeader.setOverrideList(createOverrideListGHIPSSFT(context));
			}
		}

		// Override list for Retrieve charge details request - CPB 16/05/2017
		if(opcode!=null && (opcode.equalsIgnoreCase("OP0797") || opcode.equalsIgnoreCase("OP0581") || opcode.equalsIgnoreCase("OP0671") ||
				opcode.equalsIgnoreCase("OP0602") || opcode.equalsIgnoreCase("OP0651") || opcode.equalsIgnoreCase("OP0511") || opcode.equalsIgnoreCase("OP0569"))
				&& contextMap != null && ("Y").equals(contextMap.get(SystemParameterConstant.isCBPFLAG))){
			if(daoContext.getMethodName().equals("retreiveChargeDetails") && (context.getActivityId().equals("PMT_FT_PESALINK") ||
					context.getActivityId().equals("PMT_FT_CS") || context.getActivityId().equals("PMT_BP_MOBILE_WALLET_ONETIME") ||
					context.getActivityId().equals("PMT_BP_BILLPAY_PAYEE") || context.getActivityId().equals("PMT_BP_BILLPAY_ONETIME") ||
					context.getActivityId().equals("PMT_FT_INTERNAL_PAYEE") || context.getActivityId().equals("PMT_FT_INTERNAL_ONETIME"))){
				reqHeader.setOverrideList(createOverrideListCBP(context));

			}
		}


		/*//FundTransfer_To_Own01
		if(opcode!=null && (opcode.equalsIgnoreCase("OP0501") && contextMap.get(SystemParameterConstant.isCBPFLAG).equals("Y") &&  (context.getBusinessId().equalsIgnoreCase("UGBRB") || context.getBusinessId().equalsIgnoreCase("GHBRB")||
				context.getBusinessId().equalsIgnoreCase("ZMBRB") || context.getBusinessId().equalsIgnoreCase("BWBRB")))){
			if(daoContext.getMethodName().equals("retreiveChargeDetails") && context.getActivityId().equals("PMT_FT_OWN")){
				reqHeader.setOverrideList(createOverrideListCBP(context));
			}
		}*/

		if(null != opcode && "OP0603".equalsIgnoreCase(opcode) && "MakeBillPayment".equalsIgnoreCase(serviceId)){
			createOverrideListGePG((PayBillServiceRequest)args[0], reqHeader);
		}

		//GHIPS2- to set override list for SSAMakeBillPayment
		if(null != opcode && "OP0603".equalsIgnoreCase(opcode) && context!=null && context.getBusinessId().equals("GHBRB") &&
				serviceId.equals("SSAMakeBillPayment") && context.getActivityId()!=null && context.getActivityId().equals(ActivityConstant.MOBILE_WALLET_PAYEE_ACTIVITY_ID) &&
				("Y").equals(contextMap.get(SystemParameterConstant.isGHIPS2Flag)) && !("TRUE".equalsIgnoreCase(context.getIsFreeDialUssdFlow()))) {
			reqHeader.setOverrideList(createOverrideListGHIPPS2(context));
		}
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

		//Lead Generation
		String creditCardOpcode = context.getOpCde();

		if(creditCardOpcode!= null){
			//CR#83 Apply Product
			if(creditCardOpcode.equalsIgnoreCase("OP0959") || creditCardOpcode.equalsIgnoreCase("OP0984")){
				bankUserContextReq.setStaffID(STAFF_ID_BIR);
			}
			if(creditCardOpcode.equalsIgnoreCase("OP0799"))
				bankUserContextReq.setStaffID(context.getCustomerId());
		}

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

		String opCode = context.getOpCde();
		ServiceContext serviceContext = new ServiceContext();
		Map<String, Object> contextMap = context.getContextMap();

		serviceContext.setConsumerUniqueRefNo(context.getActivityRefNo());
		serviceContext.setOriginalConsumerUniqueRefNo(context.getActivityRefNo());
		serviceContext.setServiceDateTime(DateTimeUtil.getCurrentBusinessCalendar(context));
		serviceContext.setServiceID(serviceId);

		/* Regulatory Requirement Changes - 23/10/2017 - code rollBacked on 08/12/2017
		 */
		// Version no for MakeBillPayment and MakeDoesticFundTransfer of CBP 30/08/2017
		if(opCode!=null && (opCode.equalsIgnoreCase("OP0570") || opCode.equalsIgnoreCase("OP0603") || opCode.equalsIgnoreCase("OP0511") ||
				opCode.equalsIgnoreCase("OP0502") || opCode.equalsIgnoreCase("OP0980")) && (contextMap !=null && contextMap.get(SystemParameterConstant.isCBPFLAG).equals("Y"))){
			if((serviceId.equals("MakeBillPayment") || serviceId.equals("MakeDomesticFundTransfer")) && (context.getActivityId()!=null &&
					context.getActivityId().equals("KITS_PTA_BILLPAY") || context.getActivityId().equals("KITS_PTP_BILLPAY") ||
					context.getActivityId().equals("PMT_FT_PESALINK") ||
					context.getActivityId().equals("PMT_BP_MOBILE_WALLET_ONETIME") ||
					context.getActivityId().equals("PMT_BP_BILLPAY_PAYEE") || context.getActivityId().equals("PMT_BP_BILLPAY_ONETIME") ||
					context.getActivityId().equals("PMT_FT_INTERNAL_PAYEE") || context.getActivityId().equals("PMT_FT_INTERNAL_ONETIME"))
					&& !SERVICE_VERSION_CASA_CC.equals("CC")){
					serviceContext.setServiceVersionNo(contextMap.get(SystemParameterConstant.SERVICE_HEADER_SERVICE_VER_NO_CBP).toString());
			}
			//Added for KITS and flag added for KITS enable/disable
			//Commented to add CBP in Pesalink
			/*else if(serviceId.equals("MakeBillPayment") && (context.getActivityId().equals("KITS_PTP_BILLPAY") || context.getActivityId().equals("KITS_PTA_BILLPAY"))
					&& contextMap.get("isKITSFLAG").toString().equals("Y")){
				serviceContext.setServiceVersionNo("7.0.0");
			}*/
			else{
				serviceContext.setServiceVersionNo(contextMap.get(SystemParameterConstant.SERVICE_HEADER_SERVICE_VER_NO).toString());
			}
		}else{
			if(null != contextMap && "OP0603".equalsIgnoreCase(opCode) && "MakeBillPayment".equalsIgnoreCase(serviceId) && "GePG".equalsIgnoreCase(SERVICE_VERSION_CASA_CC)){
				serviceContext.setServiceVersionNo(contextMap.get(SystemParameterConstant.SERVICE_HEADER_SERVICE_VERSION_NO_GEPG).toString());

				//Probase
			} else if(null != contextMap && "OP0603".equalsIgnoreCase(opCode) && "SSAMakeBillPayment".equalsIgnoreCase(serviceId) && "Probase".equalsIgnoreCase(SERVICE_VERSION_CASA_CC) && contextMap.get(SystemParameterConstant.isProbaseFlag).equals("Y")){
				serviceContext.setServiceVersionNo(contextMap.get(SystemParameterConstant.SERVICE_HEADER_SERVICE_VER_NO_PROBASE).toString());

				//GHIPS2- to set Service version number for SSAMakeBillPAyment
			} else if(null != contextMap && "OP0603".equalsIgnoreCase(opCode) && context!=null && context.getBusinessId().equals("GHBRB") &&
				serviceId.equals("SSAMakeBillPayment") && context.getActivityId()!=null && context.getActivityId().equals(ActivityConstant.MOBILE_WALLET_PAYEE_ACTIVITY_ID) &&
				("Y").equals(contextMap.get(SystemParameterConstant.isGHIPS2Flag)) && !("TRUE".equalsIgnoreCase(context.getIsFreeDialUssdFlow()))) {
				serviceContext.setServiceVersionNo(contextMap.get(SystemParameterConstant.SERVICE_HEADER_SERVICE_VER_NO_GHIPPS2).toString());

			}else if(null != contextMap){
				serviceContext.setServiceVersionNo(contextMap.get(SystemParameterConstant.SERVICE_HEADER_SERVICE_VER_NO).toString());
			}
		}
		SERVICE_VERSION_CASA_CC = "";
		/*
		// Remove once Regulatory Requirement Changes revoked - 23/10/2017 - code commented on 08/12/2017
		serviceContext.setServiceVersionNo(contextMap.get(SystemParameterConstant.SERVICE_HEADER_SERVICE_VER_NO).toString());
		*/
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

	protected String getParamvalue(WorkContext workContext) {
		DAOContext daoContext = (DAOContext) workContext;
		Object[] args = daoContext.getArguments();
		RequestContext request = (RequestContext) args[0];
		Context context = request.getContext();
		Map<String, Object> contextMap = context.getContextMap();
		String param_value = (String) contextMap.get(SystemParameterConstant.VISIONPLUS_PRIME);
		return param_value;
	}

	protected String getBusinessId(WorkContext workContext) {
		DAOContext daoContext = (DAOContext) workContext;
		Object[] args = daoContext.getArguments();
		RequestContext request = (RequestContext) args[0];
		Context context = request.getContext();
		String busId = context.getBusinessId();
		return busId;
	}
	private OverrideDetails[] createOverrideList(Context context) {

		OverrideDetails[] overrideDetailsArray= new OverrideDetails[1];
		OverrideDetails overrideDetails = new OverrideDetails();
		//Added for KITS registration
		//Flag added for KITS enable/disable
		if(context.getValue("isKITSFLAG").toString().equals("Y"))
		{
			if(context.getActivityId().equals("KITS_REGISTRATION_LOOKUP"))
			{
				overrideDetails.setCode("STA");
				overrideDetails.setDetails("KITS Status Call");
			}
			else
			{
				overrideDetails.setCode("KITS");
				overrideDetails.setDetails("KITS Lookup Call");
			}
		}
		else
		{
			overrideDetails.setCode("KITS");
			overrideDetails.setDetails("KITS Lookup Call");
		}

		overrideDetails.setSource("KITS");
		overrideDetailsArray[0]=overrideDetails;

		return overrideDetailsArray;
	}

	private OverrideDetails[] createOverrideListDeregInfo(Context context) {

		OverrideDetails[] overrideDetailsArray= new OverrideDetails[1];
		OverrideDetails overrideDetails = new OverrideDetails();
		overrideDetails.setCode("DEINFO");
		overrideDetails.setDetails("KITS De Registration Information");
		overrideDetails.setSource("KITS");
		overrideDetailsArray[0]=overrideDetails;

		return overrideDetailsArray;
	}
	private OverrideDetails[] createOverrideListReg(Context context) {

		OverrideDetails[] overrideDetailsArray= new OverrideDetails[1];
		OverrideDetails overrideDetails = new OverrideDetails();
		overrideDetails.setCode("REG");
		overrideDetails.setDetails("KITS Customer Registration");
		overrideDetails.setSource("KITS");
		overrideDetailsArray[0]=overrideDetails;

		return overrideDetailsArray;
	}
	private OverrideDetails[] createOverrideListDeReg(Context context) {

		OverrideDetails[] overrideDetailsArray= new OverrideDetails[1];
		OverrideDetails overrideDetails = new OverrideDetails();
		overrideDetails.setCode("DEL");
		overrideDetails.setDetails("KITS Customer De-registration");
		overrideDetails.setSource("KITS");
		overrideDetailsArray[0]=overrideDetails;

		return overrideDetailsArray;
	}
	private OverrideDetails[] createOverrideListPTABillPay(Context context) {

		OverrideDetails[] overrideDetailsArray= new OverrideDetails[1];
		OverrideDetails overrideDetails = new OverrideDetails();
		overrideDetails.setCode("P2A");
		overrideDetails.setDetails("Kits Outward Transaction");
		overrideDetails.setSource("KITS");
		overrideDetailsArray[0]=overrideDetails;

		return overrideDetailsArray;
	}
	private OverrideDetails[] createOverrideListPTPBillPay(Context context) {

		OverrideDetails[] overrideDetailsArray= new OverrideDetails[1];
		OverrideDetails overrideDetails = new OverrideDetails();
		overrideDetails.setCode("P2P");
		overrideDetails.setDetails("Kits Outward Transaction");
		overrideDetails.setSource("KITS");
		overrideDetailsArray[0]=overrideDetails;

		return overrideDetailsArray;
	}

	private OverrideDetails[] createOverrideListGHIPSSNQ(Context context) {

		OverrideDetails[] overrideDetailsArray= new OverrideDetails[1];
		OverrideDetails overrideDetails = new OverrideDetails();
		overrideDetails.setCode("GHIPSS");
		overrideDetails.setDetails("Name Enquiry");
		overrideDetails.setSource("GHIPSS");
		overrideDetailsArray[0]=overrideDetails;

		return overrideDetailsArray;
	}

	private OverrideDetails[] createOverrideListGHIPSSFT(Context context) {

		OverrideDetails[] overrideDetailsArray= new OverrideDetails[1];
		OverrideDetails overrideDetails = new OverrideDetails();
		overrideDetails.setCode("Payment");
		overrideDetails.setDetails("GHIPSS Payment request");
		overrideDetails.setSource("GHIPSS");
		overrideDetailsArray[0]=overrideDetails;

		return overrideDetailsArray;
	}

	// CBP change 04/05/2017
	private OverrideDetails[] createOverrideListCBP(Context context) {

		OverrideDetails[] overrideDetailsArray= new OverrideDetails[1];
		OverrideDetails overrideDetails = new OverrideDetails();
		overrideDetails.setCode("CBP");
		overrideDetails.setDetails("Core Billing and Pricing");
		overrideDetails.setSource("UB");
		overrideDetailsArray[0]=overrideDetails;

		return overrideDetailsArray;
	}

	//For GePG BillPayment request
	private void createOverrideListGePG(PayBillServiceRequest billServiceRequest, BEMReqHeader reqHeader) {
		if(null !=  billServiceRequest && null != billServiceRequest.getBeneficiaryDTO()&& billServiceRequest.getBeneficiaryDTO().getBillerId().endsWith("-8")){
			OverrideDetails[] overrideDetailsArray= new OverrideDetails[1];
			OverrideDetails overrideDetails = new OverrideDetails();
			if(billServiceRequest.getBeneficiaryDTO().getPresentmentFlag()){
				overrideDetails.setCode("PRESENTMENT");
			} else {
				overrideDetails.setCode("NON-PRESENTMENT");
			}
			overrideDetails.setDetails("For GEPG Payment Service");
			overrideDetails.setSource("GePG");
			overrideDetailsArray[0]=overrideDetails;

			reqHeader.setOverrideList(overrideDetailsArray);
		}
	}
	//GHIPPS2 SSAMakeBillPayment overrideList
	private OverrideDetails[] createOverrideListGHIPPS2(Context context) {
		OverrideDetails[] overrideDetailsArray= new OverrideDetails[1];
		OverrideDetails overrideDetails = new OverrideDetails();
		overrideDetails.setCode("A2W");
		overrideDetails.setDetails("Ghipps2 Account to Wallet");
		overrideDetails.setSource("Account2Phone");
		overrideDetailsArray[0]=overrideDetails;

		return overrideDetailsArray;
	}

}