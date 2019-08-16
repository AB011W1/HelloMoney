package com.barclays.bmg.dao.operation.accountservices.creditcard;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.barclays.bem.BEMServiceHeader.BEMResHeader;
import com.barclays.bem.BEMServiceHeader.Error;
import com.barclays.bem.RetrieveCreditcardAcctTransactionActivity.RetrieveCreditcardAccountTransactionActivityResponse;
import com.barclays.bmg.constants.AccountErrorCodeConstant;
import com.barclays.bmg.constants.AccountServiceResponseCodeConstant;
import com.barclays.bmg.constants.ErrorCodeConstant;
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

	RetrieveCreditcardAccountTransactionActivityResponse retrieveCCTransActivityResponse = (RetrieveCreditcardAccountTransactionActivityResponse) obj;

	String respCode = checkServiceResponseHeader(retrieveCCTransActivityResponse.getResponseHeader());

	if (respCode.equals(ErrorCodeConstant.SUCCESS_CODE)) {

	    CreditCardTransactionHistoryListDTO ccTransactionHistoryListDTO = new CreditCardTransactionHistoryListDTO();

	    List<CreditCardActivityDTO> rstObj = new ArrayList<CreditCardActivityDTO>();

	    if (retrieveCCTransActivityResponse.getCreditCardTransactionList() != null) {

		CreditCardActivityMapper ccActivityMapper = new CreditCardActivityMapper();

		rstObj = ccActivityMapper.mapCollection(retrieveCCTransActivityResponse.getCreditCardTransactionList().getTransactionActivityInfo());
	    }

	    ccTransactionHistoryListDTO.setCreditCardActivityList(rstObj);

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
		    balanceInfo.setFeeAndCharge(ConvertUtils.convertPositiveAmount(retrieveCCTransActivityResponse.getCreditCardTransactionList()
			    .getCCAccountFeesandCharges()));
		    balanceInfo.setPaymentReceived(ConvertUtils.convertAmount(retrieveCCTransActivityResponse.getCreditCardTransactionList()
			    .getCCAccountPaymentsReceived()));

		    // PreviousBalanceCRChange
		    if (null != retrieveCCTransActivityResponse.getCreditCardTransactionList().getOpeningBalanceAmount()) {
			if (retrieveCCTransActivityResponse.getCreditCardTransactionList().getOpeningBalanceAmount() > 0) {
			    balanceInfo.setPreviousBalanceFlag(true);
			}
		    }
		    // PreviousBalanceCRChange
		    balanceInfo.setPrevBalance(ConvertUtils.convertPositiveAmount(retrieveCCTransActivityResponse.getCreditCardTransactionList()
			    .getOpeningBalanceAmount()));
		    // balanceInfo.setPrevBalance(new BigDecimal(1234567890.45));
		    balanceInfo.setTotalCashWithdrawn(ConvertUtils.convertPositiveAmount(retrieveCCTransActivityResponse
			    .getCreditCardTransactionList().getCCAccountTotalCashWithdrawn()));
		    BigDecimal otherDebit = new BigDecimal(0.0);
		    if (null != retrieveCCTransActivityResponse.getCreditCardTransactionList().getOtherCreditDebitAmount()) {
			otherDebit = new BigDecimal(retrieveCCTransActivityResponse.getCreditCardTransactionList().getOtherCreditDebitAmount());
		    }
		    balanceInfo.setOtherDrOrCr(otherDebit);
		    balanceInfo.setTotalPurchase(ConvertUtils.convertPositiveAmount(retrieveCCTransActivityResponse.getCreditCardTransactionList()
			    .getCCAccountTotalPurchases()));
		}
		ccTransactionHistoryListDTO.setBalanceInfo(balanceInfo);

		/*
		 * CreditCardStmtPointsInfoDTO rewardsInfo = new CreditCardStmtPointsInfoDTO();
		 * if(retrieveCCTransActivityResponse.getCreditCardTransactionList() != null &&
		 * retrieveCCTransActivityResponse.getCreditCardTransactionList().getCCAccountRewards() != null) { //BEM5X Initially we received
		 * following values as int now gettting as double.
		 * rewardsInfo.setNewBalance(retrieveCCTransActivityResponse.getCreditCardTransactionList
		 * ().getCCAccountRewards().getNewBalance().intValue());
		 * rewardsInfo.setPointsEarned(retrieveCCTransActivityResponse.getCreditCardTransactionList
		 * ().getCCAccountRewards().getPointsEarned().intValue());
		 * rewardsInfo.setPointsRedeemed(retrieveCCTransActivityResponse.getCreditCardTransactionList
		 * ().getCCAccountRewards().getPointsRedeemed().intValue());
		 * rewardsInfo.setPrevBalance(retrieveCCTransActivityResponse.getCreditCardTransactionList
		 * ().getCCAccountRewards().getPreviousBalance().intValue());
		 * 
		 * } ccTransactionHistoryListDTO.setRewardInfo(rewardsInfo);
		 */

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

}