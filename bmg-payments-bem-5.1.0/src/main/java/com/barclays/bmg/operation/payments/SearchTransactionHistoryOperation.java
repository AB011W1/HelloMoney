package com.barclays.bmg.operation.payments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.barclays.bmg.audit.annotation.AuditSupport;
import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.constants.SystemIdConstants;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.constants.TransactionHistoryConstants;
import com.barclays.bmg.constants.TransactionHistoryResponseCodeConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.BillerDTO;
import com.barclays.bmg.dto.ListValueCacheDTO;
import com.barclays.bmg.dto.TransactionHistoryDTO;
import com.barclays.bmg.operation.BMBCommonOperation;
import com.barclays.bmg.operation.request.billpayment.SearchTransactionHistoryOperationRequest;
import com.barclays.bmg.operation.response.billpayment.SearchTransactionHistoryOperationResponse;
import com.barclays.bmg.service.BillerService;
import com.barclays.bmg.service.SearchTransactionHistoryService;
import com.barclays.bmg.service.product.request.ListValueResServiceRequest;
import com.barclays.bmg.service.product.response.ListValueResByGroupServiceResponse;
import com.barclays.bmg.service.request.BillerServiceRequest;
import com.barclays.bmg.service.request.SearchTransactionHistoryServiceRequest;
import com.barclays.bmg.service.response.SearchTransactionHistoryServiceResponse;
import com.barclays.bmg.service.response.TransactionLimitServiceResponse;

/**
 * @author BTCI
 *
 *         Operation for Search Transaction History Operation will return transaction history based on transaction type
 */
public class SearchTransactionHistoryOperation extends BMBCommonOperation {

    private SearchTransactionHistoryService searchTransactionHistoryService;
    private final static String SYS_PARAM_BP = "SYS_PARAM_BP";
    private BillerService billerService;

    /**
     * @param searchTransactionHistoryService
     */
    public void setSearchTransactionHistoryService(SearchTransactionHistoryService searchTransactionHistoryService) {
	this.searchTransactionHistoryService = searchTransactionHistoryService;
    }

    /**
     * @param SearchTransactionHistoryOperationRequest
     * @return SearchTransactionHistoryOperationResponse
     */
    @AuditSupport(auditType = AuditConstant.AUDIT_TYPE_ACTIVITY, activityState = AuditConstant.SRC_COM_EXECUTOR, serviceDescription = "SD_SEARCH_TXN_HISTORY", stepId = "1", activityType = "auditSearchTransactionHistory")
    public SearchTransactionHistoryOperationResponse searchTransactionHistory(SearchTransactionHistoryOperationRequest request) {

	Context context = request.getContext();
	loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);
	SearchTransactionHistoryOperationResponse response = new SearchTransactionHistoryOperationResponse();
	response.setContext(context);
	SearchTransactionHistoryServiceRequest searchTransactionHistoryServiceRequest = new SearchTransactionHistoryServiceRequest();
	searchTransactionHistoryServiceRequest.setContext(context);
	searchTransactionHistoryServiceRequest.setTransactionHistoryDTO(request.getTransactionHistoryDTO());
	SearchTransactionHistoryServiceResponse searchTransactionHistoryServiceResponse = searchTransactionHistoryService
		.searchTransactionHistory(searchTransactionHistoryServiceRequest);
	List<TransactionHistoryDTO> transactionHistoryDTOList = searchTransactionHistoryServiceResponse.getTransactionHistoryDTOList();
	List<TransactionHistoryDTO> resultList = null;
	response.setSuccess(searchTransactionHistoryServiceResponse.isSuccess());
	if (!response.isSuccess()) {
	    getMessage(response);

	}

	// check for transaction history list
	if (transactionHistoryDTOList != null && !transactionHistoryDTOList.isEmpty()) {

	    /*
	     * Check for Transaction Limit
	     */
	    // String systemId=request.getContext().getSystemId();
	    String systemId = SystemIdConstants.UB; // !!!!!!!!!! mocked
	    if (SystemIdConstants.UB.equals(systemId)) {

		ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
		listValueResServiceRequest.setContext(context);
		TransactionLimitServiceResponse txnLimitServiceResponse = new TransactionLimitServiceResponse();
		listValueResServiceRequest.setGroup(SYS_PARAM_BP); // TO-DO This group needs to be changed in future if history req for other Txn
		// types
		listValueResServiceRequest.getContext().setSystemId(SystemIdConstants.UB); // Mocked !!!!!!!!!!!!!!!!!
		ListValueResByGroupServiceResponse listResp = listValueResService.getListValueByGroup(listValueResServiceRequest);

		List<ListValueCacheDTO> listValuesDTOList = null;
		if (listResp != null) {
		    listValuesDTOList = listResp.getListValueCahceDTO();
		}

		if (listValuesDTOList != null && !listValuesDTOList.isEmpty()) {

		    // set the transaction limit on response based on system
		    // preferences

		    for (ListValueCacheDTO valueresDTO : listValuesDTOList) {

			if (valueresDTO.getKey() != null && SystemParameterConstant.LAST_PAID_BILL_LIST.equals(valueresDTO.getKey())) {

			    txnLimitServiceResponse.setLastPaidBillRecords(Integer.valueOf(valueresDTO.getLabel()));
			}

		    }
		}

		int lastPaidBillListRecords = txnLimitServiceResponse.getLastPaidBillRecords();
		transactionHistoryDTOList = sortTransactionHistory(transactionHistoryDTOList);
		resultList = new ArrayList<TransactionHistoryDTO>();
		int txnHistCount = 0;
		int transactionHistoryDTOListSize = transactionHistoryDTOList.size();
		if (lastPaidBillListRecords > 0) {
		    BillerServiceRequest billerServiceRequest = new BillerServiceRequest();
		    billerServiceRequest.setContext(context);
		    billerServiceRequest.setShowAllBillers(true);
		    billerServiceRequest.setBillerCategoryId("BP");
		    HashMap<String, String> billerIdandNameMap = new HashMap<String, String>();
		    List<BillerDTO> billerList = billerService.getAllBillerList(billerServiceRequest).getBillerList();
		    for (BillerDTO billerDTO : billerList) {
			billerIdandNameMap.put(billerDTO.getBillerId(), billerDTO.getBillerName());
		    }

		    if (TransactionHistoryConstants.BILL_PAYMENT.equals(transactionHistoryDTOList.get(0).getTransactionType())
			    && transactionHistoryDTOListSize > lastPaidBillListRecords) {
			int successIndex = 0;

			while (successIndex < lastPaidBillListRecords && txnHistCount < transactionHistoryDTOListSize) {
			    TransactionHistoryDTO txHistDTO = transactionHistoryDTOList.get(txnHistCount);
			    if ("SUCCESS".equalsIgnoreCase(txHistDTO.getStatus())) {
				if (billerIdandNameMap.containsKey(txHistDTO.getBillerId())) {
				    txHistDTO.setBillerName(billerIdandNameMap.get(txHistDTO.getBillerId()));
				}
				resultList.add(txHistDTO);
				successIndex++;
			    }
			    txnHistCount++;
			}

		    } else {
			while (txnHistCount < transactionHistoryDTOListSize) {
			    TransactionHistoryDTO txHistDTO = transactionHistoryDTOList.get(txnHistCount);
			    if ("SUCCESS".equalsIgnoreCase(txHistDTO.getStatus())) {
				if (billerIdandNameMap.containsKey(txHistDTO.getBillerId())) {
				    txHistDTO.setBillerName(billerIdandNameMap.get(txHistDTO.getBillerId()));
				}
				resultList.add(txHistDTO);
			    }
			    txnHistCount++;
			}

		    }
		}

		response.setTransactionHistoryDTOList(resultList);

	    } else {
		response.setTransactionHistoryDTOList(transactionHistoryDTOList);
	    }

	} else {
	    response.setSuccess(false);
	    response.setResCde(TransactionHistoryResponseCodeConstants.NO_TXN_HIST_FOUND);
	    return response;
	}

	return response;

    }

    private List<TransactionHistoryDTO> sortTransactionHistory(List<TransactionHistoryDTO> transactionHistory) {
	Collections.sort(transactionHistory, new Comparator<TransactionHistoryDTO>() {
	    public int compare(TransactionHistoryDTO o1, TransactionHistoryDTO o2) {
		if (o2.getTransactionDate() == null || o1.getTransactionDate() == null)
		    return 0;
		return o2.getTransactionDate().compareTo(o1.getTransactionDate());
	    }
	});
	return transactionHistory;
    }

    public BillerService getBillerService() {
	return billerService;
    }

    public void setBillerService(BillerService billerService) {
	this.billerService = billerService;
    }

}
