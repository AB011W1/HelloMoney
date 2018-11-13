package com.barclays.bmg.json.model.builder.billpayment;

import java.util.List;

import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.json.model.AmountJSONModel;
import com.barclays.bmg.json.model.billpayment.AccountJSONBean;
import com.barclays.bmg.json.model.billpayment.CCPayeeJSONModel;
import com.barclays.bmg.json.model.billpayment.CreditCardAccountJSONBean;
import com.barclays.bmg.json.model.billpayment.PaymentTransferJSONBean;
import com.barclays.bmg.json.model.billpayment.TransactionInformationRestBean;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.fundtransfer.external.MergeOwnCreditcardInfoOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrieveAcctListOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.TransactionLimitOperationResponse;
import com.barclays.bmg.utils.BMGFormatUtility;


public class RetreiveCCPPayeeInfoJSONBldr extends BMBMultipleResponseJSONBuilder implements BMBJSONBuilder{


	protected void populatePayLoad(	BMBPayload bmbPayload ,ResponseContext... responseContexts) {
		PaymentTransferJSONBean paymentTransferJSONBean = null;
		RetrieveAcctListOperationResponse retrieveAcctListOperationResponse = (RetrieveAcctListOperationResponse) responseContexts[0];
		MergeOwnCreditcardInfoOperationResponse mergeOwnCreditcardInfoOperationResponse = (MergeOwnCreditcardInfoOperationResponse) responseContexts[2];
		TransactionLimitOperationResponse transactionLimitOperationResponse = (TransactionLimitOperationResponse) responseContexts[3];
		if(mergeOwnCreditcardInfoOperationResponse !=null && mergeOwnCreditcardInfoOperationResponse.isSuccess()){
			paymentTransferJSONBean = new PaymentTransferJSONBean();
			List<? extends CustomerAccountDTO> accountList = retrieveAcctListOperationResponse.getAcctList();
			if(accountList!=null){
			for(CustomerAccountDTO account : accountList){

				String currency = account.getCurrency();

				AccountJSONBean accountJSONBean = new AccountJSONBean();
				accountJSONBean.setPrdTyp(account.getDesc());
				accountJSONBean.setActNo(getCorrelationId(account.getAccountNumber(),retrieveAcctListOperationResponse));
				// Set Masked account number.
				/*accountJSONBean.setMkdActNo(getMaskedAccountNumber(account.getBranchCode(),account.getAccountNumber()));*/




				 if(branchCodeCountryList.contains(BMBContextHolder.getContext().getBusinessId())){
				accountJSONBean.setMkdActNo(getMaskedAccountNumber(account.getBranchCode(),account.getAccountNumber()));
								    }
								    else {
									accountJSONBean.setMkdActNo(getMaskedAccountNumber(null,account.getAccountNumber()));
								    }


				accountJSONBean.setCurr(account.getCurrency());
				accountJSONBean.setAvlbBal(BMGFormatUtility.getJSONAmount(currency,BMGFormatUtility.getFormattedAmount(account.getAvailableBalance())));
				paymentTransferJSONBean.add(accountJSONBean);
			}
			}

			BeneficiaryDTO beneficiaryDTO = mergeOwnCreditcardInfoOperationResponse.getBeneficiaryDTO();
			CustomerAccountDTO creditCardDestAcct = null;
			if(beneficiaryDTO!=null)
			{
				creditCardDestAcct = beneficiaryDTO.getDestinationAccount();

				paymentTransferJSONBean.setCurr(creditCardDestAcct.getCurrency());
				CCPayeeJSONModel creditCardModel = getCCPayeeJSONModel(creditCardDestAcct ,transactionLimitOperationResponse);

				paymentTransferJSONBean.setPay(creditCardModel);
			}

		}
		bmbPayload.setPayData(paymentTransferJSONBean);
	}

	private CCPayeeJSONModel getCCPayeeJSONModel(CustomerAccountDTO customerAccountDTO ,TransactionLimitOperationResponse transactionLimitOperationResponse){
		CCPayeeJSONModel ccPayeeJSONModel = new CCPayeeJSONModel();


		if(customerAccountDTO != null && customerAccountDTO instanceof CreditCardAccountDTO){

			CreditCardAccountDTO creditCardDestAcct = (CreditCardAccountDTO) customerAccountDTO;
			CreditCardAccountJSONBean creditAccountJSONBean = new CreditCardAccountJSONBean();
			AmountJSONModel curBal = new AmountJSONModel();
			curBal.setCurr(creditCardDestAcct.getCurrency());
			curBal.setAmt(getPositiveCurrentBalance(creditCardDestAcct.getCurrentBalance()));
			creditAccountJSONBean.setCurBal(curBal);
			creditAccountJSONBean.setCurr(creditCardDestAcct.getCurrency());

			AmountJSONModel minPayAmt = new AmountJSONModel();
			minPayAmt.setCurr(creditCardDestAcct.getCurrency());
			minPayAmt.setAmt(BMGFormatUtility.getFormattedAmount(creditCardDestAcct.getMinPaymentAmount()));
			creditAccountJSONBean.setMinPayAmt(minPayAmt);

			AmountJSONModel outStanBal = new AmountJSONModel();
			outStanBal.setCurr(creditCardDestAcct.getCurrency());
			outStanBal.setAmt(getPositiveCurrentBalance(creditCardDestAcct.getOutstandingBalance()));
			creditAccountJSONBean.setOutStndBal(outStanBal);

			creditAccountJSONBean.setPymntDueDt(creditCardDestAcct
					.getPaymentDueDate());
			//TODO MAsked Account number
			creditAccountJSONBean.setToAcctDisName(getMaskedCardNumber(creditCardDestAcct.getPrimary().getCardNumber()));
			creditAccountJSONBean.setToAcct(creditCardDestAcct
					.getAccountNumber());
			ccPayeeJSONModel.setCreditCardAccountJSONBean(creditAccountJSONBean);

		}

		TransactionInformationRestBean tranRestBean = new TransactionInformationRestBean();
		AmountJSONModel dailyLimit = new AmountJSONModel();
		dailyLimit.setAmt(BMGFormatUtility.getFormattedAmount(transactionLimitOperationResponse.getAValidDailyLimit()));
		dailyLimit.setCurr(transactionLimitOperationResponse.getContext().getLocalCurrency());

		AmountJSONModel txnLimit = new AmountJSONModel();
		txnLimit.setAmt(BMGFormatUtility.getFormattedAmount(transactionLimitOperationResponse.getAValidDailyLimit()));
		txnLimit.setCurr(transactionLimitOperationResponse.getContext().getLocalCurrency());

		tranRestBean.setDlyTrnLmt(dailyLimit);
		tranRestBean.setAvailTrnLmt(txnLimit);
		ccPayeeJSONModel.setTrRestBean(tranRestBean);
		return ccPayeeJSONModel;
	}

	@Override
	public Object createMultipleJSONResponse(
			ResponseContext... responseContexts) {
		BMBPayloadHeader bmbPayloadHeader = null;
		BMBPayload bmbPayload = null;
		for(ResponseContext response: responseContexts){
			if(response!=null && !response.isSuccess()){
				bmbPayloadHeader = createHeader(response, ResponseIdConstants.RETRIEVE_CCP_PAYEE_INFORMATION_RESPONSE_ID);
				break;
			}
		}

		if(bmbPayloadHeader!=null){
			bmbPayload = new BMBPayload(bmbPayloadHeader);
		}else{
			ResponseContext response = responseContexts[0];
			bmbPayload = new BMBPayload(createHeader(response,ResponseIdConstants.RETRIEVE_CCP_PAYEE_INFORMATION_RESPONSE_ID));
			populatePayLoad(bmbPayload, responseContexts);
		}

		return bmbPayload;
	}

}
