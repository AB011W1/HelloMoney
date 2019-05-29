package com.barclays.bmg.dao.accountservices.adapter.ssa;

import com.barclays.bem.FundsTransferInfo.FundsTransferInfo;
import com.barclays.bem.IndividualName.IndividualName;
import com.barclays.bem.PaymentandTransferAccount.PaymentandTransferAccount;
import com.barclays.bem.SendCash.SendCashDetails;
import com.barclays.bem.SendCash.SendCashInfo;
import com.barclays.bem.SendCash.SendCashRecipientInfo;
import com.barclays.bem.SendCash.SendCashSenderInfo;
import com.barclays.bem.UserAuditDetails.UserAuditDetails;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dto.CashSendRequestDTO;
import com.barclays.bmg.service.request.ManageFundtransferStatusServiceRequest;

public class ManageFundtransferStatusPayloadAdapter {


	  public FundsTransferInfo[] adaptPayLoad(WorkContext workContext) {
			DAOContext daoContext = (DAOContext) workContext;

			Object[] args = daoContext.getArguments();

			FundsTransferInfo fundsTransferInfo = new FundsTransferInfo();

			ManageFundtransferStatusServiceRequest request = (ManageFundtransferStatusServiceRequest) args[0];

			//fundsTransferInfo.setActionCode(request.getActionCode());
			fundsTransferInfo.setBusinessEntityCustomerNumber(request.getBankCIF());
			fundsTransferInfo.setCustomerNumber(request.getUserId());
			PaymentandTransferAccount debitAccount=new PaymentandTransferAccount();
			debitAccount.setAccountNumber(request.getAccno().trim());

			PaymentandTransferAccount creditAccount=new PaymentandTransferAccount();
			if(request.getAcconoToCredit()!=null)
				creditAccount.setAccountNumber(request.getAcconoToCredit().trim());

			fundsTransferInfo.setDebitAccount(debitAccount);
			fundsTransferInfo.setCreditAccount(creditAccount);
			fundsTransferInfo.setCreditAmount(Double.parseDouble(request.getCreditAmount()));
			fundsTransferInfo.setDebitAmount(Double.parseDouble(request.getDebitAmount()));
			fundsTransferInfo.setUserReferenceNumber("0");
			IndividualName name=new IndividualName();
			name.setFullName(request.getInitiatingCustomerFullName());
			fundsTransferInfo.setInitiatingCustomerName(name);
			fundsTransferInfo.setTrxReferenceNumber(request.getTransactionNumber());
			fundsTransferInfo.setTransactionStatusCode(request.getTransactionStatus());
			fundsTransferInfo.setTrxReferenceNumber(request.getTransactionNumber());
			fundsTransferInfo.setFundsTransferType(request.getFundTransferType());
			fundsTransferInfo.setTransactionStatusMessage(request.getTransactionStatusMessage());
			fundsTransferInfo.setRemarks(request.getRemarks());
			UserAuditDetails userAuditDetails = new UserAuditDetails();
			userAuditDetails.setAccountName(request.getBillerId());
			fundsTransferInfo.setUserAuditDetails(userAuditDetails);

			FundsTransferInfo [] fTInfo = new FundsTransferInfo[]{fundsTransferInfo};
			return fTInfo;
		    }

}
