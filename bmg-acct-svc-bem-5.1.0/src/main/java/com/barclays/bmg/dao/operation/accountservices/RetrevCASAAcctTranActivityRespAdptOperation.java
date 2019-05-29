package com.barclays.bmg.dao.operation.accountservices;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.barclays.bem.BEMServiceHeader.BEMResHeader;
import com.barclays.bem.RetrieveCASAAcctTransactionActivity.CASAAccountTransactionList;
import com.barclays.bem.RetrieveCASAAcctTransactionActivity.RetrieveCASAAccountTransactionActivityResponse;
import com.barclays.bem.TransactionHistory.TransactionHistory;
import com.barclays.bem.UserAuditDetails.CorporateUserAuthDetailsType;
import com.barclays.bmg.operation.accountdetails.response.CorporateUserAuthDetailsTypeBMG;
import com.barclays.bmg.constants.ErrorCodeConstant;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.operation.accountdetails.response.CASAAccountTransactionService;
import com.barclays.bmg.operation.accountdetails.response.TransactionActivityService;
import com.barclays.bmg.operation.accountservices.AbstractResAdptOperation;
import com.barclays.bmg.service.accounts.response.RetrevCASAAcctTranActivityServiceResponse;

public class RetrevCASAAcctTranActivityRespAdptOperation extends
		AbstractResAdptOperation {
	private static final Logger LOGGER = Logger
			.getLogger(RetrevCASAAcctTranActivityRespAdptOperation.class);

	public RetrevCASAAcctTranActivityServiceResponse adaptResponse(
			WorkContext workContext, Object obj) {
		RetrevCASAAcctTranActivityServiceResponse rcasatsresponse = new RetrevCASAAcctTranActivityServiceResponse();
		RetrieveCASAAccountTransactionActivityResponse activityResponse = (RetrieveCASAAccountTransactionActivityResponse) obj;
		checkRespHeader(activityResponse.getResponseHeader(), rcasatsresponse);

		if (rcasatsresponse.isSuccess()) {
			if (activityResponse.getCASAAccountTransactionList() != null
					&& activityResponse.getCASAAccountTransactionList().getTransactionActivity().length != 0) {
				CASAAccountTransactionList casaAccountTransactionList = activityResponse.getCASAAccountTransactionList();
				List<CASAAccountTransactionService> list = populateCustomerDetailsList(casaAccountTransactionList.getTransactionActivity());
				if (!list.isEmpty()
						&& list.get(0).getTransactionActivity() != null) {
					rcasatsresponse.setCasaAccountTransactionList(list);
					rcasatsresponse.setSuccess(true);
				} else {
					rcasatsresponse.setResCde("BEMRECMOB");
					rcasatsresponse.setSuccess(false);
				}

			} else {
				rcasatsresponse.setResMsg(activityResponse.getResponseHeader()
						.getServiceResStatus().getServiceResDesc());
				rcasatsresponse.setResCde("BEMRECMOB");
				rcasatsresponse.setSuccess(false);
			}

		} else {
			for (com.barclays.bem.BEMServiceHeader.Error error : activityResponse
					.getResponseHeader().getErrorList()) {
				rcasatsresponse.setResMsg(activityResponse.getResponseHeader()
						.getServiceResStatus().getServiceResDesc());
				rcasatsresponse.setResCde("BEM" + error.getErrorCode());
				rcasatsresponse.setSuccess(false);
			}
		}

		return rcasatsresponse;
	}

	private List<CASAAccountTransactionService> populateCustomerDetailsList(
			TransactionHistory[] transactionHistory) {
		if (LOGGER.isDebugEnabled()) {
			if (transactionHistory != null)
				LOGGER.debug(" Entry accountInformationArray size"
						+ transactionHistory.length);
		}

		List<CASAAccountTransactionService> casaAcctTranService = new LinkedList<CASAAccountTransactionService>();

		List<TransactionHistory> tranHistory = new ArrayList<TransactionHistory>();
		if (transactionHistory != null)
			tranHistory = Arrays.asList(transactionHistory);

		for (TransactionHistory details : tranHistory) {
			CASAAccountTransactionService casaAcctTranSer=new CASAAccountTransactionService();
			TransactionActivityService transActivityService=new TransactionActivityService();
			CorporateUserAuthDetailsTypeBMG dtype=null;
			List<CorporateUserAuthDetailsTypeBMG> corporateUserAuthDetailsType=new ArrayList<CorporateUserAuthDetailsTypeBMG>();

			if(details.getTransactionCurrencyAmount()!=null)
			transActivityService.setAmount(details.getTransactionCurrencyAmount().toString());
			if(details.getUserAuditDetails()!=null){
				transActivityService.setAuthLebel(details.getUserAuditDetails().getAuditType());
			//
				CorporateUserAuthDetailsType[] authdetails= details.getUserAuditDetails().getCorporateUserAuthDetails();
				if(authdetails!=null){
				for(CorporateUserAuthDetailsType ad:authdetails){
					dtype=new CorporateUserAuthDetailsTypeBMG();
					dtype.setAuthorizedBy(ad.getAuthorisedBy());
					dtype.setDecision(ad.getDecision());
					dtype.setRemarks(ad.getRemarks());
					corporateUserAuthDetailsType.add(dtype);
				}
				transActivityService.setCorporateUserAuthDetailsType(corporateUserAuthDetailsType);
				}
			}
			//
			//
			/*if(details.getUserAuditDetails().getCorporateUserAuthDetails()!=null)
				dtype= details.getUserAuditDetails().getCorporateUserAuthDetails()[0];
			transActivityService.setInitiatedBy(details.getUserAuditDetails().getEventByUserId());
			}
			if(dtype!=null){
			transActivityService.setAuthorizedBy(dtype.getAuthorisedBy());
			if(dtype.getAuthorisedDateTime()!=null)
				transActivityService.setAuthorizedDateTime(dtype.getAuthorisedDateTime().toString());
			transActivityService.setAuthorizerName(dtype.getAuthorizerName());
			transActivityService.setDecision(dtype.getDecision());
			transActivityService.setRemarks(dtype.getRemarks());
			}
			//
*/
			if(details.getBeneficiary()!=null){
			transActivityService.setBeneficiaryORBillerName(details.getBeneficiary().getBeneficiaryName());
			transActivityService.setTxnBeneficiaryName(details.getBeneficiary().getBeneficiaryName());
			if(details.getBeneficiary().getBeneficiaryAccountInfo()!=null)
				transActivityService.setBeneficiaryAcctOrMblno(details.getBeneficiary().getBeneficiaryAccountInfo().getAccountNumber());
			}
			transActivityService.setToAccount(details.getAccountNumber());
			transActivityService.setCustomerFullName(details.getNameOnProbative());
			Calendar datetime=details.getTransactionDateTime();
			Calendar dt=datetime.getInstance();
			int year=dt.get(Calendar.YEAR);
			int month=dt.get(Calendar.MONTH+1 );
			int day=dt.get(Calendar.DAY_OF_MONTH);
			transActivityService.setDateTime(day+"/"+month+"/"+year);
			if(details.getTransactionBranch()!=null)
				transActivityService.setDebitAccountBranch(details.getTransactionBranch().getBranchCode());
			transActivityService.setDebitAccountCurrency(details.getTransactionCurrencyCode());
			transActivityService.setDebitAccountNumber(details.getAccountNumber());
			transActivityService.setDebitAccountType(details.getStatementTxnDesc1());
			if(details.getDebitAmount()!=null)
				transActivityService.setDebitAmount(Math.round(details.getDebitAmount())+"");
			transActivityService.setFundsTransferType(details.getTransactionTypeCode());
			transActivityService.setTransactionRefnbr(details.getTransactionReferenceNumber());
			transActivityService.setTransactionRemarks(details.getStatementTxnDesc1());
			transActivityService.setTransactionStatus(details.getTransactionLiteral());
			transActivityService.setTransactionType(details.getTransactionTypeCode());

			casaAcctTranSer.setTransactionActivity(transActivityService);
			casaAcctTranService.add(casaAcctTranSer);
		}
		return casaAcctTranService;
	}

	private void checkRespHeader(BEMResHeader resHeader,
			ResponseContext response) {

		String resCode = resHeader.getServiceResStatus().getServiceResCode();
		if (ErrorCodeConstant.BUSINESS_ERROR.equals(resCode)) {
			if (resHeader.getErrorList() != null
					&& resHeader.getErrorList().length > 0) {

				for (com.barclays.bem.BEMServiceHeader.Error error : resHeader
						.getErrorList()) {

					response.setResMsg(error.getErrorDesc());
					response.setResCde("BEM" + error.getErrorCode());
					response.setSuccess(false);
				}
			}
		}
		if (response.isSuccess()) {
			response.setSuccess(super.checkResponseHeader(resHeader));
		}
	}
}
