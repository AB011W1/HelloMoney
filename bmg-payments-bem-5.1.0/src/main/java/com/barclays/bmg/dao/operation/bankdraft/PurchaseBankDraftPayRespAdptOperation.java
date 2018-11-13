package com.barclays.bmg.dao.operation.bankdraft;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.barclays.bem.BEMServiceHeader.ServiceContext;
import com.barclays.bem.FundTransferResponseStatus.FundTransferResponseStatus;
import com.barclays.bem.MakeDomesticDemandDraftByAccount.MakeDomesticDemandDraftByAccountResponse;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dao.operation.payments.BMBRetailTxnRespAdptOperation;
import com.barclays.bmg.service.request.bankdraft.PurchaseBankDraftServiceRequest;
import com.barclays.bmg.service.response.bankdraft.PurchaseBankDraftServiceResponse;

public class PurchaseBankDraftPayRespAdptOperation extends
		BMBRetailTxnRespAdptOperation {

	public PurchaseBankDraftServiceResponse createPurchaseBankDraftServiceResponse(
			WorkContext workContext,
			MakeDomesticDemandDraftByAccountResponse accountResponse) {

		PurchaseBankDraftServiceResponse purchaseBankDraftServiceResponse = new PurchaseBankDraftServiceResponse();

		DAOContext daoContext = (DAOContext) workContext;

		Object[] args = daoContext.getArguments();

		PurchaseBankDraftServiceRequest purchaseBankDraftServiceRequest = (PurchaseBankDraftServiceRequest) args[0];

		boolean isValid = checkResponseHeader(accountResponse
				.getResponseHeader());

		purchaseBankDraftServiceResponse.setSuccess(isValid);
		purchaseBankDraftServiceResponse
				.setContext(purchaseBankDraftServiceRequest.getContext());

		FundTransferResponseStatus respStatus = accountResponse
				.getCASAAccountDomesticDemandDraftResponseStatus();

		ServiceContext serviceContext = accountResponse.getResponseHeader()
				.getServiceContext();

		String originalTrnNo = serviceContext.getOriginalConsumerUniqueRefNo();
		Date originalTrnDate = serviceContext.getServiceDateTime().getTime();

		purchaseBankDraftServiceResponse.setTransactionRefNo(originalTrnNo);

		purchaseBankDraftServiceResponse
				.setBemTransactionRefNo(respStatus
						.getTransactionResponseStatus()
						.getTransactionReferenceNumber());
		purchaseBankDraftServiceResponse.setBemTransactionDate(respStatus
				.getTransactionResponseStatus().getTransactionDateTime()
				.getTime());

		purchaseBankDraftServiceResponse.setTransactionDate(originalTrnDate);

		purchaseBankDraftServiceResponse
				.setBankDraftTransactionDTO(purchaseBankDraftServiceRequest
						.getBankDraftTransactionDTO());

		String resCode = accountResponse.getResponseHeader().getServiceResStatus()
				.getServiceResCode();

		if (StringUtils.isNotEmpty(resCode)
				&& ResponseCodeConstants.SUCCESS_RES_CODE.equals(resCode)) {
			purchaseBankDraftServiceResponse.setTxnMsg(ResponseCodeConstants.SUCCESS_TXN_RESPONSE_MESSAGE);
		} else if (StringUtils.isNotEmpty(resCode)
				&& ResponseCodeConstants.SUBMITTED_TXN_RES_CODE.equals(resCode)) {
			purchaseBankDraftServiceResponse.setTxnMsg(ResponseCodeConstants.SUBMITTED_TXN_RESPONSE_MESSAGE);
		}

		return purchaseBankDraftServiceResponse;
	}

}
