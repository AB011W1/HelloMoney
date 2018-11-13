package com.barclays.bmg.json.model.billpayment;

import java.util.List;

import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.json.model.AmountJSONModel;
import com.barclays.bmg.json.model.BMGJSONAdapter;
import com.barclays.bmg.json.response.BMBPayloadData;
import com.barclays.bmg.operation.response.billpayment.RetreiveCCPPayeeInformationOperationResponse;
import com.barclays.bmg.utils.BMGFormatUtility;

public class RetreiveCCPPayeeInformationModelAdapter implements BMGJSONAdapter {

	@Override
	public BMBPayloadData adaptToJSONModel(Object obj) {
		PaymentTransferJSONBean paymentTransferJSONBean = new PaymentTransferJSONBean();
		RetreiveCCPPayeeInformationOperationResponse response = (RetreiveCCPPayeeInformationOperationResponse) obj;


		List<? extends CustomerAccountDTO> accountList = response.getSourceAcctList();

		for(CustomerAccountDTO account : accountList){

			String currency = account.getCurrency();

			AccountJSONBean accountJSONBean = new AccountJSONBean();
			accountJSONBean.setPrdTyp(account.getDesc());
			accountJSONBean.setActNo(account.getAccountNumber());
			// Set Masked account number.
			accountJSONBean.setMkdActNo(account.getAccountNumber());
			accountJSONBean.setCurr(account.getCurrency());
			accountJSONBean.setAvlbBal(BMGFormatUtility.getJSONAmount(currency,BMGFormatUtility.getFormattedAmount(account.getAvailableBalance())));
			paymentTransferJSONBean.add(accountJSONBean);
		}

		CustomerAccountDTO creditCardDestAcct = response.getCreditCardDestAcct();

		paymentTransferJSONBean.setCurr(creditCardDestAcct.getCurrency());
		CCPayeeJSONModel creditCardModel = getCCPayeeJSONModel(response);

		paymentTransferJSONBean.setPay(creditCardModel);
		return paymentTransferJSONBean;
	}

	private CCPayeeJSONModel getCCPayeeJSONModel(RetreiveCCPPayeeInformationOperationResponse response){
		CCPayeeJSONModel ccPayeeJSONModel = new CCPayeeJSONModel();
		CustomerAccountDTO customerAccountDTO = response.getCreditCardDestAcct();

		if(customerAccountDTO != null && customerAccountDTO instanceof CreditCardAccountDTO){

			CreditCardAccountDTO creditCardDestAcct = (CreditCardAccountDTO) customerAccountDTO;
			CreditCardAccountJSONBean creditAccountJSONBean = new CreditCardAccountJSONBean();
			AmountJSONModel curBal = new AmountJSONModel();
			curBal.setCurr(creditCardDestAcct.getCurrency());
			curBal.setAmt(creditCardDestAcct.getCurrentBalance().toString());
			creditAccountJSONBean.setCurBal(curBal);
			creditAccountJSONBean.setCurr(creditCardDestAcct.getCurrency());

			AmountJSONModel minPayAmt = new AmountJSONModel();
			minPayAmt.setCurr(creditCardDestAcct.getCurrency());
			minPayAmt.setAmt(creditCardDestAcct.getMinPaymentAmount().toString());
			creditAccountJSONBean.setMinPayAmt(minPayAmt);

			AmountJSONModel outStanBal = new AmountJSONModel();
			outStanBal.setCurr(creditCardDestAcct.getCurrency());
			outStanBal.setAmt(creditCardDestAcct.getOutstandingBalance().toString());
			creditAccountJSONBean.setOutStndBal(outStanBal);

			creditAccountJSONBean.setPymntDueDt(creditCardDestAcct
					.getPaymentDueDate());
			//TODO MAsked Account number
			creditAccountJSONBean.setToAcctDisName(creditCardDestAcct.getAccountNumber());
			creditAccountJSONBean.setToAcct(creditCardDestAcct
					.getAccountNumber());
			ccPayeeJSONModel.setCreditCardAccountJSONBean(creditAccountJSONBean);

		}

		TransactionInformationRestBean tranRestBean = new TransactionInformationRestBean();
		AmountJSONModel dailyLimit = new AmountJSONModel();
		dailyLimit.setAmt(BMGFormatUtility.getFormattedAmount(response.getAValidDailyLimit()));

		AmountJSONModel txnLimit = new AmountJSONModel();
		txnLimit.setAmt(BMGFormatUtility.getFormattedAmount(response.getTxnLimit()));

		tranRestBean.setDlyTrnLmt(dailyLimit);
		tranRestBean.setAvailTrnLmt(txnLimit);
		ccPayeeJSONModel.setTrRestBean(tranRestBean);
		return ccPayeeJSONModel;
	}

}
