package com.barclays.bmg.operation.accountservices;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.operation.BMBCommonOperation;
import com.barclays.bmg.operation.accountdetails.response.CASAAccountTransactionOperation;
import com.barclays.bmg.operation.accountdetails.response.CASAAccountTransactionService;
import com.barclays.bmg.operation.accountdetails.response.TransactionActivityOperation;
import com.barclays.bmg.operation.accountdetails.response.TransactionActivityService;
import com.barclays.bmg.service.accounts.RetrevCASAAcctTranActivityService;
import com.barclays.bmg.service.accounts.request.RetrevCASAAcctTranActivityOperationRequest;
import com.barclays.bmg.service.accounts.request.RetrevCASAAcctTranActivityServiceRequest;
import com.barclays.bmg.service.accounts.response.RetrevCASAAcctTranActivityOperationResponse;
import com.barclays.bmg.service.accounts.response.RetrevCASAAcctTranActivityServiceResponse;

public class RetrevCASAAcctTranActivityListOperation extends BMBCommonOperation {
	RetrevCASAAcctTranActivityService retrevCASAAcctTranActivityService;

	public RetrevCASAAcctTranActivityService getRetrevCASAAcctTranActivityService() {
		return retrevCASAAcctTranActivityService;
	}

	public void setRetrevCASAAcctTranActivityService(
			RetrevCASAAcctTranActivityService retrevCASAAcctTranActivityService) {
		this.retrevCASAAcctTranActivityService = retrevCASAAcctTranActivityService;
	}

	public RetrevCASAAcctTranActivityOperationResponse retrevCasaAcctTranActivity(
			RetrevCASAAcctTranActivityOperationRequest request) {
		// TODO Auto-generated method stub
		RetrevCASAAcctTranActivityServiceRequest retrevCASAAcctTranActivityServiceRequest = new RetrevCASAAcctTranActivityServiceRequest();
		retrevCASAAcctTranActivityServiceRequest
				.setAccountNumber(request.getAccountNumber());
		retrevCASAAcctTranActivityServiceRequest
				.setBranchCode(request.getBranchCode());
		retrevCASAAcctTranActivityServiceRequest
				.setLockRequired(request.getLockRequired());
		retrevCASAAcctTranActivityServiceRequest
				.setTransactionStatus(request.getTransactionStatus());
		retrevCASAAcctTranActivityServiceRequest
		.setActionCode(request.getActionCode());
		retrevCASAAcctTranActivityServiceRequest.setTranTypeCode(request.getTranTypeCode());
		retrevCASAAcctTranActivityServiceRequest
				.setTrxReferenceNumber(request.getTrxReferenceNumber());
		retrevCASAAcctTranActivityServiceRequest.setUserID(request.getUserID());
		retrevCASAAcctTranActivityServiceRequest.setContext(request.getContext());
		loadParameters(retrevCASAAcctTranActivityServiceRequest.getContext(),
				ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);
		retrevCASAAcctTranActivityServiceRequest
				.setContext(request.getContext());

		RetrevCASAAcctTranActivityServiceResponse retrevCASAAcctTranActivityServiceResponse = retrevCASAAcctTranActivityService
				.retrevCasaAcctTranActivity(retrevCASAAcctTranActivityServiceRequest);

		RetrevCASAAcctTranActivityOperationResponse retrevCASAAcctTranActivityOperationResponse = new RetrevCASAAcctTranActivityOperationResponse();

		if (retrevCASAAcctTranActivityServiceResponse != null) {

			if (retrevCASAAcctTranActivityServiceResponse.isSuccess()) {
				List<CASAAccountTransactionService> casaAccountTransactionService = retrevCASAAcctTranActivityServiceResponse.getCasaAccountTransactionList();

				List<CASAAccountTransactionOperation> casaAccountTransactionOperationlst = new LinkedList<CASAAccountTransactionOperation>();

				if (casaAccountTransactionService != null && casaAccountTransactionService.size() > 0) {
					for (CASAAccountTransactionService casaAcctTranSer : casaAccountTransactionService) {
						CASAAccountTransactionOperation cops = new CASAAccountTransactionOperation();
						TransactionActivityOperation transactionActivityOperation = new TransactionActivityOperation();
						TransactionActivityService transactionActivityService = casaAcctTranSer.getTransactionActivity();

						transactionActivityOperation.setAmount(transactionActivityService.getAmount());
						transactionActivityOperation.setAuthLebel(transactionActivityService.getAuthLebel());
						transactionActivityOperation.setAuthorizedBy(transactionActivityService.getAuthorizedBy());
						transactionActivityOperation.setAuthorizedDateTime(transactionActivityService.getAuthorizedBy());
						transactionActivityOperation.setBeneficiaryORBillerName(transactionActivityService.getBeneficiaryORBillerName());
						transactionActivityOperation.setCustomerFullName(transactionActivityService.getCustomerFullName());
						transactionActivityOperation.setDateTime(transactionActivityService.getDateTime());
						transactionActivityOperation
								.setDebitAccountBranch(transactionActivityService.getDebitAccountBranch());
						transactionActivityOperation.setDebitAccountCurrency(transactionActivityService.getDebitAccountCurrency());
						transactionActivityOperation.setDebitAccountNumber(transactionActivityService.getDebitAccountNumber());
						transactionActivityOperation.setDebitAccountType(transactionActivityService.getDebitAccountType());
						transactionActivityOperation.setDebitAmount(transactionActivityService.getDebitAmount());
						transactionActivityOperation.setDecision(transactionActivityService.getDecision());
						transactionActivityOperation.setFundsTransferType(transactionActivityService.getFundsTransferType());
						transactionActivityOperation.setInitiatedBy(transactionActivityService.getInitiatedBy());
						transactionActivityOperation.setInitiatedDateTime(transactionActivityService.getInitiatedDateTime());
						transactionActivityOperation.setToAccount(transactionActivityService.getToAccount());
						transactionActivityOperation.setTotalRecords(transactionActivityService.getTotalRecords());
						transactionActivityOperation.setTransactionRefnbr(transactionActivityService.getTransactionRefnbr());
						transactionActivityOperation.setTransactionRemarks(transactionActivityService.getTransactionRemarks());
						transactionActivityOperation.setTransactionStatus(transactionActivityService.getTransactionStatus());
						transactionActivityOperation.setTransactionType(transactionActivityService.getTransactionType());
						transactionActivityOperation.setTxnBeneficiaryName(transactionActivityService.getTxnBeneficiaryName());
						transactionActivityOperation.setBeneficiaryAcctOrMblno(transactionActivityService.getBeneficiaryAcctOrMblno());
						transactionActivityOperation.setCorporateUserAuthDetailsType(transactionActivityService.getCorporateUserAuthDetailsType());

						cops.setTransactionActivity(transactionActivityOperation);
						casaAccountTransactionOperationlst.add(cops);
					}
				}
				List<CASAAccountTransactionOperation> filteredList=getFilteredList(casaAccountTransactionOperationlst,request.getTranTypeCode());
				retrevCASAAcctTranActivityOperationResponse
						.setCasaAccountTransactionList(filteredList);
			}
		}
		return retrevCASAAcctTranActivityOperationResponse;
	}

	public List<CASAAccountTransactionOperation> getFilteredList(List<CASAAccountTransactionOperation> tranList, String tranTypeCode){
		List<CASAAccountTransactionOperation> filteredList=new ArrayList();
		for(CASAAccountTransactionOperation tran:tranList){
			if(tran.getTransactionActivity().getTransactionType().equals(tranTypeCode))
				filteredList.add(tran);
		}
		return filteredList;
	}
}
