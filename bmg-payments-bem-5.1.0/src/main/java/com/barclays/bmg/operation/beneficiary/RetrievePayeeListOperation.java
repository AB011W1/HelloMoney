package com.barclays.bmg.operation.beneficiary;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.barclays.bmg.audit.annotation.AuditSupport;
import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.constants.BeneficiaryResponseCodeConstants;
import com.barclays.bmg.constants.BillPaymentResponseCodeConstants;
import com.barclays.bmg.constants.FundTransferConstants;
import com.barclays.bmg.constants.SystemIdConstants;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.CategorizedPayeeListDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.ListValueCacheDTO;
import com.barclays.bmg.operation.payments.BMBPaymentsOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.RetrievePayeeListOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrievePayeeListOperationResponse;
import com.barclays.bmg.service.product.ListValueResService;
import com.barclays.bmg.service.product.request.ListValueResServiceRequest;
import com.barclays.bmg.service.product.response.ListValueResByGroupServiceResponse;
import com.barclays.bmg.service.response.TransactionLimitServiceResponse;

/**
 * @author e20027734 This Operation will return payee List. Specified to the pay Grp. For New payee types. Follow the steps.
 *
 *         Set the Pay Group as Payee Type --- and Txn Type against it in the map.
 */
public class RetrievePayeeListOperation extends BMBPaymentsOperation {
    // private final static String PAYEE_TYPE_GROUP = "FT_FACADE_DEST";
    private ListValueResService listValueResService;
    private String PAYEE_TYPE_GROUP;
    public Map<String, String> payGroupTxnType;
    private final static String INTERNAL = "INTERNAL";
    private static final String BARCLAY_CARD = "BCD";
    private final static String EXTERNAL = "EXTERNAL";
    private final static String SYS_PARAM_BNF = "SYS_PARAM_BNF";
    private Integer viewBenfLimit = 0;
    private static final String AIR_TIME = "AT";
    private static final String M_WALLET = "WT";
    private static final String GHIPPSPAY = "GHIPPSPAY";

    @AuditSupport(auditType = AuditConstant.AUDIT_TYPE_TRANSACTION, activityState = AuditConstant.SRC_COM_SSC, activityId = "PMT_PAYEE_LIST", serviceDescription = "SD_RETRIEVE_IND_BENE_LIST", stepId = "1", activityType = "auditPayeeList")
    public RetrievePayeeListOperationResponse retrievePayeeList(RetrievePayeeListOperationRequest request) {
	RetrievePayeeListOperationResponse response = new RetrievePayeeListOperationResponse();
	Context context = request.getContext();
	response.setContext(context);
	loadParameters(request.getContext(), ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);

	TransactionLimitServiceResponse transactionLimitServiceResponse = getListOfBenfToView(context);

	String payGrp = request.getPayGrp();
	if (payGrp != null && (payGrp.equals(FundTransferConstants.PAYEE_TYPE_INTERNAL) || payGrp.equals(EXTERNAL))) {

	    viewBenfLimit = transactionLimitServiceResponse.getMaxBenfLimit();
	} else {
	    viewBenfLimit = transactionLimitServiceResponse.getMaxBillerBenfLimit();
	}

	List<? extends CustomerAccountDTO> allAccounts = getAllAccounts(request, response);

	if (allAccounts != null && !allAccounts.isEmpty()) {
	    /*
	     * List<? extends CustomerAccountDTO> sourceAccts = getSourceAccountsList( allAccounts, request);
	     */
	    List<? extends CustomerAccountDTO> sourceAccts = getAllAccountsByStatus(request, allAccounts);

	    List<? extends CustomerAccountDTO> finalList = filterAccountsByStatus(request, sourceAccts);
	    if (finalList != null && !finalList.isEmpty()) {
		List<ListValueCacheDTO> payeeTypeGroup = getPayeeTypeGroup(request);

		//CR82
		if(payGrp != null &&(payGrp.equalsIgnoreCase(AIR_TIME)|| payGrp.equalsIgnoreCase(M_WALLET))){
			payeeTypeGroup = getPayeeTypeGroupAtWt(request);
		}

		List<BeneficiaryDTO> beneficiaryList = retrievePayeeList(request, response);

		if (beneficiaryList != null && !beneficiaryList.isEmpty()) {
		    List<CategorizedPayeeListDTO> cateList = getCategorizedPayeeList(beneficiaryList, payeeTypeGroup);
		    List<CategorizedPayeeListDTO> payGrpSpcList = getListForSpecificPayGrp(cateList, request.getPayGrp());
		    if (payGrpSpcList != null && !payGrpSpcList.isEmpty()) {

			response.setCategorizedPayeeList(payGrpSpcList);

		    } else {
			response.setSuccess(false);
			response.setResCde(BillPaymentResponseCodeConstants.NO_PAYEES_REGISTERED);
			setResponseForActivityID(request.getContext().getActivityId(), response);
		    }
		} else {
		    response.setSuccess(false);
		    response.setResCde(BeneficiaryResponseCodeConstants.NO_PAYEES_REGISTERED);
		    setResponseForActivityID(request.getContext().getActivityId(), response);

		}
	    } else {
		response.setSuccess(false);
		response.setResCde(BillPaymentResponseCodeConstants.NO_ACCOUNTS_FOR_SUMMARY);
	    }
	} else {
	    response.setSuccess(false);
	    response.setResCde(BillPaymentResponseCodeConstants.NO_ACCOUNTS_FOR_SUMMARY);
	}

	if (!response.isSuccess()) {
	    getMessage(response);
	}
	return response;
    }

    private void setResponseForActivityID(String activityId, RetrievePayeeListOperationResponse response) {
	if (activityId.equals(ActivityConstant.BILL_PAYMENT_ACTIVITY_ID)) {
	    response.setResCde(BillPaymentResponseCodeConstants.NO_PAYEES_REGISTERED);
	} else if ((activityId.equals(ActivityConstant.FUND_TRANSFER_INTERNAL_PAYEE_ACTIVITY_ID))
		|| (activityId.equals(ActivityConstant.FUND_TRANSFER__EXTERNAL_PAYEE_ACTIVITY_ID))) {
	    response.setResCde(BeneficiaryResponseCodeConstants.NO_PAYEES_REGISTERED);
	}

    }

    /**
     * @param categorizedList
     * @param payGrp
     * @return Get Payee List for Specific group
     */
    private List<CategorizedPayeeListDTO> getListForSpecificPayGrp(List<CategorizedPayeeListDTO> categorizedList, String payGrp) {
	List<CategorizedPayeeListDTO> returnList = new ArrayList<CategorizedPayeeListDTO>();
	if (StringUtils.isNotEmpty(payGrp)) {
	    String txnTyp = payGroupTxnType.get(payGrp);
	    for (CategorizedPayeeListDTO categorizedPayeeListDTO : categorizedList) {
		if (categorizedPayeeListDTO.getTransactionFacadeRoutineType() != null
			&& categorizedPayeeListDTO.getTransactionFacadeRoutineType().equals(txnTyp)) {
		    returnList.add(categorizedPayeeListDTO);
		}
	    }
	} else {
	    for (CategorizedPayeeListDTO categorizedPayeeListDTO : categorizedList) {
		returnList.add(categorizedPayeeListDTO);

	    }
	}
	return returnList;
    }

    /**
     * Get categorized payee list.
     *
     * @param activePayeeList
     * @param payeeTypeGroup
     * @return
     */
    private List<CategorizedPayeeListDTO> getCategorizedPayeeList(List<BeneficiaryDTO> activePayeeList, List<ListValueCacheDTO> payeeTypeGroup) {
	List<CategorizedPayeeListDTO> categorizedList = new ArrayList<CategorizedPayeeListDTO>();
	if (payeeTypeGroup != null) {
	    for (ListValueCacheDTO payeeType : payeeTypeGroup) {
		String key = payeeType.getKey();
		List<BeneficiaryDTO> payeeList = null;
		payeeList = getPayeeListByPayeeType(key, activePayeeList);
		if (payeeList != null && !payeeList.isEmpty()) {
		    CategorizedPayeeListDTO categorizedPayeeListDTO = new CategorizedPayeeListDTO();
		    categorizedPayeeListDTO.setPayeeCategory(payeeType.getLabel());
		    categorizedPayeeListDTO.setPayeeList(payeeList);
		    categorizedPayeeListDTO.setTransactionFacadeRoutineType(payGroupTxnType.get(key));
		    categorizedList.add(categorizedPayeeListDTO);
		}
	    }
	}
	return categorizedList;
    }

    /**
     * Get Payee type list eligible for particular country,
     *
     * @param retreivePayeeListOperationRequest
     * @return
     */
    private List<ListValueCacheDTO> getPayeeTypeGroup(RequestContext request) {
	ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
	listValueResServiceRequest.setContext(request.getContext());
	listValueResServiceRequest.setGroup(PAYEE_TYPE_GROUP);
	ListValueResByGroupServiceResponse listValueResByGroupServiceResponse = listValueResService.getListValueByGroup(listValueResServiceRequest);
	return listValueResByGroupServiceResponse.getListValueCahceDTO();
    }

    //CR82
    private List<ListValueCacheDTO> getPayeeTypeGroupAtWt(RetrievePayeeListOperationRequest request) {
	ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
	listValueResServiceRequest.setContext(request.getContext());
	listValueResServiceRequest.setGroup(request.getPayGrp()+"_FACADE_DEST");
	ListValueResByGroupServiceResponse listValueResByGroupServiceResponse = listValueResService.getListValueByGroup(listValueResServiceRequest);
	return listValueResByGroupServiceResponse.getListValueCahceDTO();
    }

    private List<BeneficiaryDTO> getPayeeListByPayeeType(String payeeType, List<BeneficiaryDTO> activePayeeList) {
	String updatedpayeeType = payeeType;
	if (INTERNAL.equals(updatedpayeeType)) {
	    updatedpayeeType = "IAC";
	}
	if (EXTERNAL.equals(updatedpayeeType)) {
	    updatedpayeeType = "DAC";
	}
	if (BARCLAY_CARD.equals(updatedpayeeType)) {
	    updatedpayeeType = payGroupTxnType.get(updatedpayeeType);
	}
	if (GHIPPSPAY.equals(updatedpayeeType)) {
	    updatedpayeeType = FundTransferConstants.TXN_TYPE_FUND_TRANSFER_GHIPPS;
	}
	List<BeneficiaryDTO> returnList = new ArrayList<BeneficiaryDTO>();
	if (activePayeeList != null && !activePayeeList.isEmpty()) {

	    for (BeneficiaryDTO beneficiary : activePayeeList) {
		if (updatedpayeeType.equals(beneficiary.getPayeeTypeCode())) {

		    returnList.add(beneficiary);

		}
	    }

	}

	List<BeneficiaryDTO> viewPayeeList = new ArrayList<BeneficiaryDTO>();

	/**
	 * preparing View benf list based on system parameter configured
	 */
	if (returnList != null && !returnList.isEmpty()) {

	    if (viewBenfLimit != null && viewBenfLimit > 0 && viewBenfLimit <= returnList.size()) {

		for (int payeeListIndex = 0; payeeListIndex < viewBenfLimit; payeeListIndex++) {

		    viewPayeeList.add(returnList.get(payeeListIndex));
		}

	    } else if (viewBenfLimit != null && viewBenfLimit > 0 && viewBenfLimit > returnList.size()) {

		viewPayeeList.addAll(returnList);
	    }
	    /*
	     * else{ response.setSuccess(false); response.setResCde(BeneficiaryResponseCodeConstants .BENEFICIARY_VIEW_LIMIT_COUNT); return response;
	     *
	     * }
	     */
	    /**
			 *
			 */
	}

	return viewPayeeList;
    }

    private TransactionLimitServiceResponse getListOfBenfToView(Context context) {

	String systemId = context.getSystemId();
	TransactionLimitServiceResponse txnLimitServiceResponse = new TransactionLimitServiceResponse();

	/*
	 * Check for Transaction Limit
	 *
	 * Skipped !!..as system pref parameter validation for max dft & oth benf is not required any more.
	 */
	if (SystemIdConstants.UB.equals(systemId)) {

	    ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
	    listValueResServiceRequest.setContext(context);
	    listValueResServiceRequest.setGroup(SYS_PARAM_BNF);
	    // listValueResServiceRequest.getContext().setSystemId(systemId); //
	    // Mocked !!!!!!!!!!!!!!!!!
	    ListValueResByGroupServiceResponse listResp = listValueResService.getListValueByGroup(listValueResServiceRequest);

	    List<ListValueCacheDTO> listValuesDTOList = null;
	    if (listResp != null) {
		listValuesDTOList = listResp.getListValueCahceDTO();
	    }

	    if (listValuesDTOList != null && !listValuesDTOList.isEmpty()) {

		// set the transaction limit on response based on system
		// preferences

		for (ListValueCacheDTO valueresDTO : listValuesDTOList) {

		    if (valueresDTO.getKey() != null && SystemParameterConstant.VIEW_MAX_BILLER_SIZE.equals(valueresDTO.getKey())) {

			txnLimitServiceResponse.setMaxBillerBenfLimit(Integer.valueOf(valueresDTO.getLabel()));
		    }

		    else if (valueresDTO.getKey() != null && SystemParameterConstant.VIEW_MAX_BENFCR.equals(valueresDTO.getKey())) {

			txnLimitServiceResponse.setMaxBenfLimit(Integer.valueOf(valueresDTO.getLabel()));
		    }

		}
	    }

	}
	return txnLimitServiceResponse;

    }

    @Override
    public ListValueResService getListValueResService() {
	return listValueResService;
    }

    @Override
    public void setListValueResService(ListValueResService listValueResService) {
	this.listValueResService = listValueResService;
    }

    public Map<String, String> getPayGroupTxnType() {
	return payGroupTxnType;
    }

    public void setPayGroupTxnType(Map<String, String> payGroupTxnType) {
	this.payGroupTxnType = payGroupTxnType;
    }

    public String getPAYEE_TYPE_GROUP() {
	return PAYEE_TYPE_GROUP;
    }

    public void setPAYEE_TYPE_GROUP(String payee_type_group) {
	PAYEE_TYPE_GROUP = payee_type_group;
    }

}
