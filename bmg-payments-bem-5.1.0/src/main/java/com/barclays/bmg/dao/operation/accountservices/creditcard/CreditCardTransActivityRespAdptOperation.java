package com.barclays.bmg.dao.operation.accountservices.creditcard;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.barclays.bem.BEMServiceHeader.BEMResHeader;
import com.barclays.bem.BEMServiceHeader.Error;
import com.barclays.bem.RetrieveCreditcardAcctTransactionActivity.RetrieveCreditcardAccountTransactionActivityResponse;
import com.barclays.bmg.constants.AccountErrorCodeConstant;
import com.barclays.bmg.constants.AccountServiceResponseCodeConstant;
import com.barclays.bmg.constants.ErrorCodeConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dao.operation.accountservices.AbstractResAdptOperationAcct;
import com.barclays.bmg.dto.CreditCardActivityDTO;
import com.barclays.bmg.dto.CreditCardStmtAccountInfoDTO;
import com.barclays.bmg.dto.CreditCardStmtBalanceInfoDTO;
import com.barclays.bmg.dto.CreditCardTransactionHistoryListDTO;
import com.barclays.bmg.exception.BMBDataAccessException;
import com.barclays.bmg.mapper.CreditCardActivityMapper;
import com.barclays.bmg.service.accountdetails.request.CreditCardAccountActivityServiceRequest;
import com.barclays.bmg.service.accountdetails.response.CreditCardTransActivityServiceResponse;
import com.barclays.bmg.utils.ConvertUtils;

public class CreditCardTransActivityRespAdptOperation extends AbstractResAdptOperationAcct {

    public CreditCardTransActivityServiceResponse adaptResponseForCreditCardTransActivity(WorkContext workContext, Object obj) throws Exception {

	CreditCardTransActivityServiceResponse returnCCTransActServiceResp = new CreditCardTransActivityServiceResponse();

	DAOContext daoContext = (DAOContext) workContext;
	Object[] args = daoContext.getArguments();
	CreditCardAccountActivityServiceRequest ccTransActivityServiceReq = (CreditCardAccountActivityServiceRequest) args[0];
	//First Vision Changes
	Context context = ccTransActivityServiceReq.getContext();

	RetrieveCreditcardAccountTransactionActivityResponse retrieveCCTransActivityResponse = (RetrieveCreditcardAccountTransactionActivityResponse) obj;

	String respCode = checkServiceResponseHeader(retrieveCCTransActivityResponse.getResponseHeader());

	if (respCode.equals(ErrorCodeConstant.SUCCESS_CODE)) {

	    CreditCardTransactionHistoryListDTO ccTransactionHistoryListDTO = new CreditCardTransactionHistoryListDTO();

	    List<CreditCardActivityDTO> rstObj = new ArrayList<CreditCardActivityDTO>();

	    if (retrieveCCTransActivityResponse.getCreditCardTransactionList() != null) {

		CreditCardActivityMapper ccActivityMapper = new CreditCardActivityMapper();

		rstObj = ccActivityMapper.mapCollection(retrieveCCTransActivityResponse.getCreditCardTransactionList().getTransactionActivityInfo());
	    }

	    ccTransactionHistoryListDTO.setCreditCardActivityList(getUpdatedTransactionActivityList(rstObj, context));

	    if (ccTransActivityServiceReq.isStatementTrxFlag()) {
		CreditCardStmtAccountInfoDTO accountInfo = new CreditCardStmtAccountInfoDTO();
		if (retrieveCCTransActivityResponse.getCreditCardTransactionList() != null) {
		    accountInfo.setTotalCreditLimit(ConvertUtils.convertAmount(retrieveCCTransActivityResponse.getCreditCardTransactionList()
			    .getCreditLimitAmount()));
		    accountInfo.setMinPaymentDue(ConvertUtils.convertPositiveAmount(retrieveCCTransActivityResponse.getCreditCardTransactionList()
			    .getMinimumDueAmount()));
		    accountInfo.setPaymentDue(ConvertUtils.convertAmount(retrieveCCTransActivityResponse.getCreditCardTransactionList()
			    .getClosingBalanceAmount()));
		    accountInfo.setPaymentDueDate(ConvertUtils.convertDate(retrieveCCTransActivityResponse.getCreditCardTransactionList()
			    .getDueDate()));
		}
		ccTransactionHistoryListDTO.setAccountInfo(accountInfo);

		CreditCardStmtBalanceInfoDTO balanceInfo = new CreditCardStmtBalanceInfoDTO();

		if (retrieveCCTransActivityResponse.getCreditCardTransactionList() != null) {
		    // Account Balance
		    // BigDecimal accountBalance =
		    // BigDecimal.valueOf(response.getCreditCardTransactionList().getClosingBalanceAmount().doubleValue());
		    if (null != retrieveCCTransActivityResponse.getCreditCardTransactionList().getClosingBalanceAmount()) {
			if (retrieveCCTransActivityResponse.getCreditCardTransactionList().getClosingBalanceAmount() > 0) {
			    balanceInfo.setAccountBalancePositiveFlag(true);
			}
		    }
		    balanceInfo.setAccountBalance(ConvertUtils.convertPositiveAmount(retrieveCCTransActivityResponse.getCreditCardTransactionList()
			    .getClosingBalanceAmount()));
		    
		    // PreviousBalanceCRChange
		    if (null != retrieveCCTransActivityResponse.getCreditCardTransactionList().getOpeningBalanceAmount()) {
			if (retrieveCCTransActivityResponse.getCreditCardTransactionList().getOpeningBalanceAmount() > 0) {
			    balanceInfo.setPreviousBalanceFlag(true);
			}
		    }
		    // PreviousBalanceCRChange
		    balanceInfo.setPrevBalance(ConvertUtils.convertPositiveAmount(retrieveCCTransActivityResponse.getCreditCardTransactionList()
			    .getOpeningBalanceAmount()));
		    
		    BigDecimal otherDebit = new BigDecimal(0.0);
		    if (null != retrieveCCTransActivityResponse.getCreditCardTransactionList().getOtherCreditDebitAmount()) {
			otherDebit = new BigDecimal(retrieveCCTransActivityResponse.getCreditCardTransactionList().getOtherCreditDebitAmount());
		    }
		    balanceInfo.setOtherDrOrCr(otherDebit);
		    
		}
		getUpdatedBalanceInfo(ccTransactionHistoryListDTO.getCreditCardActivityList(),balanceInfo,context);
		ccTransactionHistoryListDTO.setBalanceInfo(balanceInfo);

		
	    }

	    returnCCTransActServiceResp.setCreditCardTransactionHistoryListDTO(ccTransactionHistoryListDTO);
	    returnCCTransActServiceResp.setSuccess(true);
	} else {
	    returnCCTransActServiceResp.setSuccess(false);
	}

	returnCCTransActServiceResp.setResCde(respCode);

	return returnCCTransActServiceResp;

    }

    /*
     * checking for error response.
     */
    private String checkServiceResponseHeader(BEMResHeader resHeader) throws Exception {

	String returnCode = "";

	if (resHeader.getServiceResStatus() != null) {
	    String resCode = resHeader.getServiceResStatus().getServiceResCode();
	    Error[] errorList = resHeader.getErrorList();

	    if (AccountErrorCodeConstant.SUCCESS_CODE.equals(resCode)) {
		returnCode = resCode;
	    }

	    else if (errorList != null) {
		for (com.barclays.bem.BEMServiceHeader.Error error : errorList) {
		    if (error.getErrorCode().equals(AccountErrorCodeConstant.CC_ACCOUNT_NO_NOT_EXIST)) {
			returnCode = AccountServiceResponseCodeConstant.ACCOUNT_ACTIVITY_INVALID_ACCOUNT_NO;
		    } else if (error.getErrorCode().equals(AccountErrorCodeConstant.CC_ACCOUNT_NO_NOT_EMPTY_PRIME)) {
			returnCode = AccountServiceResponseCodeConstant.ACCOUNT_ACTIVITY_INVALID_ACCOUNT_NO;
		    } else if (error.getErrorCode().equals(AccountErrorCodeConstant.CC_INVALID_STMT_DATE)) {
			returnCode = AccountServiceResponseCodeConstant.ACCOUNT_ACTIVITY_NO_TRANX_FOUND;
		    } else if (error.getErrorCode().equals(AccountErrorCodeConstant.CC_TXN_ACTIVITY_EXCEPTION)) {
			returnCode = AccountServiceResponseCodeConstant.ACCOUNT_ACTIVITY_NO_STMT_TRANX_FOUND;
		    } else {
			throw new BMBDataAccessException(error.getErrorCode(), error.getErrorDesc());
		    }
		}
	    }
	}

	return returnCode;
    }
    
	public void getUpdatedBalanceInfo(List<CreditCardActivityDTO> creditcardactivitylist,
			CreditCardStmtBalanceInfoDTO balanceInfo, Context requestContext) {

		// List<CreditCardActivityDTO> creditcardactivitylist =
		// activityList.get(0).getCreditCardActivityList();
		BigDecimal totalCashWithdrawn = new BigDecimal(0);
		BigDecimal totalPurchase = new BigDecimal(0);
		BigDecimal feesAndCharges = new BigDecimal(0);
		BigDecimal totalAmount = new BigDecimal(0);
		List<String> transactionlogModTotalAmount = new ArrayList<String>();
		List<String> reversalLogModuleTotalAmount = new ArrayList<String>();
		List<String> transactionlogModTotPurchase = new ArrayList<String>();
		List<String> reversalLogModuleTotPurchase = new ArrayList<String>();
		List<String> cashPlanLogModuleAdd = new ArrayList<String>();
		List<String> cashPlanLogModuleSub = new ArrayList<String>();
		List<String> feesLogModuleAdd = new ArrayList<String>();
		List<String> feesPlanLogModuleSub = new ArrayList<String>();

		String TOT_AMOUNT_LOGIC_MOD = getSystemParameterValueById(requestContext, "TOT_AMOUNT_LOGIC_MOD");
		String TOT_AMOUNT_LOGIC_MOD_REV = getSystemParameterValueById(requestContext, "TOT_AMOUNT_LOGIC_MOD_REV");
		String TOT_CASH_LOGIC_MOD = getSystemParameterValueById(requestContext, "TOT_CASH_LOGIC_MOD");
		String TOT_CASH_LOGIC_MOD_REV = getSystemParameterValueById(requestContext, "TOT_CASH_LOGIC_MOD_REV");
		String TOT_PURCH_LOGIC_MOD = getSystemParameterValueById(requestContext, "TOT_PURCH_LOGIC_MOD");
		String TOT_PURCH_LOGIC_MOD_REV = getSystemParameterValueById(requestContext, "TOT_PURCH_LOGIC_MOD_REV");
		String FEES_LOGIC_MOD = getSystemParameterValueById(requestContext, "FEES_LOGIC_MOD");
		String FEES_LOGIC_MOD_REV = getSystemParameterValueById(requestContext, "FEES_LOGIC_MOD_REV");

		if (TOT_AMOUNT_LOGIC_MOD != null) {

			transactionlogModTotalAmount = Arrays.asList(TOT_AMOUNT_LOGIC_MOD.split(","));
		}
		if (TOT_AMOUNT_LOGIC_MOD_REV != null) {
			reversalLogModuleTotalAmount = Arrays.asList(TOT_AMOUNT_LOGIC_MOD_REV.split(","));
		}
		if (TOT_PURCH_LOGIC_MOD != null) {
			transactionlogModTotPurchase = Arrays.asList(TOT_PURCH_LOGIC_MOD.split(","));
		}
		if (TOT_PURCH_LOGIC_MOD_REV != null) {
			reversalLogModuleTotPurchase = Arrays.asList(TOT_PURCH_LOGIC_MOD_REV.split(","));
		}
		if (TOT_CASH_LOGIC_MOD != null) {
			cashPlanLogModuleAdd = Arrays.asList(TOT_CASH_LOGIC_MOD.split(","));
		}
		if (TOT_CASH_LOGIC_MOD_REV != null) {
			cashPlanLogModuleSub = Arrays.asList(TOT_CASH_LOGIC_MOD_REV.split(","));
		}
		if (FEES_LOGIC_MOD != null) {
			feesLogModuleAdd = Arrays.asList(FEES_LOGIC_MOD.split(","));
		}
		if (FEES_LOGIC_MOD_REV != null) {
			feesPlanLogModuleSub = Arrays.asList(FEES_LOGIC_MOD_REV.split(","));
		}

		for (CreditCardActivityDTO ccDTO : creditcardactivitylist) {

			// Total Amount
			if (transactionlogModTotalAmount != null && transactionlogModTotalAmount.contains(ccDTO.getLogicModule())) {
				totalAmount = totalAmount.add(ccDTO.getTransactionAmount());

			} else if (reversalLogModuleTotalAmount != null
					&& reversalLogModuleTotalAmount.contains(ccDTO.getLogicModule())) {
				totalAmount = totalAmount.subtract(ccDTO.getTransactionAmount());

			}
			// Total Purchase
			if (transactionlogModTotPurchase != null && transactionlogModTotPurchase.contains(ccDTO.getLogicModule())) {
				totalPurchase = totalPurchase.add(ccDTO.getTransactionAmount());

			} else if (reversalLogModuleTotPurchase != null
					&& reversalLogModuleTotPurchase.contains(ccDTO.getLogicModule())) {
				totalPurchase = totalPurchase.subtract(ccDTO.getTransactionAmount());

			}
			// Cash Withdrawn
			if (cashPlanLogModuleSub != null && cashPlanLogModuleAdd.contains(ccDTO.getLogicModule())) {
				totalCashWithdrawn = totalCashWithdrawn.add(ccDTO.getTransactionAmount());


			} else if (cashPlanLogModuleSub != null && cashPlanLogModuleSub.contains(ccDTO.getLogicModule())) {
				totalCashWithdrawn = totalCashWithdrawn.subtract(ccDTO.getTransactionAmount());

			}

			// Fees Calculation
			if (feesLogModuleAdd != null && feesLogModuleAdd.contains(ccDTO.getLogicModule())) {
				feesAndCharges = feesAndCharges.add(ccDTO.getTransactionAmount());

			} else if (feesPlanLogModuleSub != null && feesPlanLogModuleSub.contains(ccDTO.getLogicModule())) {
				feesAndCharges = feesAndCharges.subtract(ccDTO.getTransactionAmount());

			}

		}

		balanceInfo.setFeeAndCharge(feesAndCharges);
		balanceInfo.setTotalCashWithdrawn(totalCashWithdrawn);
		balanceInfo.setTotalPurchase(totalPurchase);
		balanceInfo.setPaymentReceived(totalAmount);

	}

}