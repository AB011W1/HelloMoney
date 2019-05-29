package com.barclays.bmg.json.model.builder.pesalink;

import java.util.LinkedList;
import java.util.List;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.json.model.RetrevCASAAcctTranActivityModel;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.accountdetails.response.CASAAccountTransactionOperation;
import com.barclays.bmg.operation.accountdetails.response.TransactionActivityOperation;
import com.barclays.bmg.service.accounts.response.RetrevCASAAcctTranActivityOperationResponse;

public class RetrevCASAAcctTranActivityJsonBldr extends BMBMultipleResponseJSONBuilder implements BMBJSONBuilder  {



	@Override
	public Object createMultipleJSONResponse(
			ResponseContext... responseContexts) {
		// TODO Auto-generated method stub
		BMBPayloadHeader bmbPayloadHeader = null;
		BMBPayload bmbPayload = null;
		for (ResponseContext response : responseContexts) {
		    if (response != null && !response.isSuccess()) {
			bmbPayloadHeader = createHeader(response);
			break;
		    }
		}

		if (bmbPayloadHeader != null) {
		    bmbPayload = new BMBPayload(bmbPayloadHeader);
		} else {
		    bmbPayload = new BMBPayload(createHeader(responseContexts[0]));
		    populatePayLoad(bmbPayload, responseContexts);
		}

		return bmbPayload;

	}

	protected void populatePayLoad(BMBPayload bmbPayload,ResponseContext... responses) {
		RetrevCASAAcctTranActivityOperationResponse response=(RetrevCASAAcctTranActivityOperationResponse)responses[0];
		RetrevCASAAcctTranActivityModel responseModel = null;
		if (response != null && response.isSuccess()) {
			responseModel = new RetrevCASAAcctTranActivityModel();
			List<CASAAccountTransactionOperation> casaAccountTransactionOperationlst = response.getCasaAccountTransactionList();
			List<CASAAccountTransactionOperation> casaAcctTranModelLst = new LinkedList<CASAAccountTransactionOperation>();
			if (casaAccountTransactionOperationlst != null && casaAccountTransactionOperationlst.size() > 0) {
				for (CASAAccountTransactionOperation casaAcctTranSer : casaAccountTransactionOperationlst) {
					CASAAccountTransactionOperation cops = new CASAAccountTransactionOperation();
					TransactionActivityOperation transactionActivityOperation = new TransactionActivityOperation();
					TransactionActivityOperation transactionActivityService = casaAcctTranSer.getTransactionActivity();

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
					casaAcctTranModelLst.add(cops);
				}
			}
			responseModel.setCasaAccountTransactionList(casaAcctTranModelLst);
		}
		bmbPayload.setPayData(responseModel);
	}

	protected BMBPayloadHeader createHeader(
			ResponseContext response) {

		BMBPayloadHeader header = new BMBPayloadHeader();
		if (response != null && response.isSuccess()) {
			header.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
			header.setResMsg("Succes GroupWalllet Transaction Activity Retrival");

		} else if (response != null && !response.isSuccess()) {
			header.setResCde(response.getResCde());
			header.setResMsg(response.getResMsg());
		}
		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);

		return header;
	}

}
