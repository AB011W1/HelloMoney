package com.barclays.bmg.service.impl;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import com.barclays.bmg.constants.ActivityIdConstantBean;
import com.barclays.bmg.constants.ErrorCodeConstant;
import com.barclays.bmg.constants.FundTransferConstants;
import com.barclays.bmg.constants.FundTransferResponseCodeConstants;
import com.barclays.bmg.constants.SystemIdConstants;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dao.TransactionLimitDAO;
import com.barclays.bmg.dto.ListValueCacheDTO;
import com.barclays.bmg.service.MessageResourceService;
import com.barclays.bmg.service.TransactionLimitService;
import com.barclays.bmg.service.product.ListValueResService;
import com.barclays.bmg.service.product.request.ListValueResServiceRequest;
import com.barclays.bmg.service.product.response.ListValueResByGroupServiceResponse;
import com.barclays.bmg.service.request.MessageResourceServiceRequest;
import com.barclays.bmg.service.request.TransactionLimitServiceRequest;
import com.barclays.bmg.service.request.UpgradeTransactionLimitServiceRequest;
import com.barclays.bmg.service.response.MessageResourceServiceResponse;
import com.barclays.bmg.service.response.TransactionLimitServiceResponse;
import com.barclays.bmg.service.response.UpgradeTransactionLimitServiceResponse;
import com.barclays.bmg.utils.BMGFormatUtility;

public class TransactionLimitServiceImpl implements TransactionLimitService {

    private TransactionLimitDAO transactionLimitDAO;
    private MessageResourceService messageResourceService;
    private ListValueResService listValueResService;
    private final static String SYS_PARAM_BP_GROUP = "SYS_PARAM_BP";
    private final static String SYS_PARAM_FTOWN = "SYS_PARAM_FTOWN";
    private final static String SYS_PARAM_FTOTH = "SYS_PARAM_FTOTH";
    private final static String SYS_PARAM_DFT = "SYS_PARAM_DFT";
    private final static String SYS_PARAM_MWALLET = "SYS_PARAM_MWALLET";
    private final static String SYS_PARAM_TOPUP = "SYS_PARAM_TOPUP";
    private final static String SYS_PARAM_CS = "SYS_PARAM_CS";
    private final static String SYS_PARAM_CCOWN = "SYS_PARAM_CCOWN";
    private final static String SYS_PARAM_GHIPPS = "SYS_PARAM_GHIPPS";

    @Override
    public TransactionLimitServiceResponse checkTransactionLimit(TransactionLimitServiceRequest request) {

	TransactionLimitServiceResponse response = new TransactionLimitServiceResponse();
	Double amtInLCY = request.getAmountInLCY().doubleValue();

	UpgradeTransactionLimitServiceRequest upgradeTransactionLimitServiceRequest = new UpgradeTransactionLimitServiceRequest();
	upgradeTransactionLimitServiceRequest.setContext(request.getContext());
	upgradeTransactionLimitServiceRequest.setTxnDate(new Date());
	int txnCount = transactionLimitDAO.getTransactionCountForUB(upgradeTransactionLimitServiceRequest);

	if (ActivityIdConstantBean.BILL_PAYMENT_ONETIME_ACTIVITY_ID.equals(request.getContext().getActivityId())
		|| ActivityIdConstantBean.BILL_PAYMENT_PAYEE_ACTIVITY_ID.equals(request.getContext().getActivityId()))
	    response = getBPTransactionLimitForUB(request);
	else if (ActivityIdConstantBean.FUND_TRANSFER_OWN_ACTIVITY_ID.equals(request.getContext().getActivityId()))
	    response = getFTOwnTransactionLimitForUB(request);
	else if (ActivityIdConstantBean.FUND_TRANSFER_INTERNAL_PAYEE_ACTIVITY_ID.equals(request.getContext().getActivityId()))
	    response = getFTOtherTransactionLimitForUB(request);
	else if (ActivityIdConstantBean.FUND_TRANSFER_INTERNAL_ONETIME_ACTIVITY_ID.equals(request.getContext().getActivityId()))
	    response = getFTOtherTransactionLimitForUB(request);
	else if (ActivityIdConstantBean.FUND_TRANSFER_EXTERNAL_PAYEE_ACTIVITY_ID.equals(request.getContext().getActivityId()))
		response = getDFTTransactionLimitForUB(request);
	else if (ActivityIdConstantBean.MOBILE_TOPUP_ONETIME_ACTIVITY_ID.equals(request.getContext().getActivityId()))
	    response = getMobileTopUpTransactionLimitForUB(request);
	else if (ActivityIdConstantBean.MOBILE_WALLET_ONETIME_ACTIVITY_ID.equals(request.getContext().getActivityId()))
	    response = getMWalletTransactionLimitForUB(request);// !!!!!!!!!! Check Activity Ids for Air time Top Up and M Wallet
	else if (ActivityIdConstantBean.PMT_FT_CASHSEND_ONETIME.equals(request.getContext().getActivityId())) // UMESH ADDED
	    response = getCashSendTransactionLimitForUB(request);
	else if (ActivityIdConstantBean.FUND_TRANSFER_CARD_PAYMENT_ONETIME_ACTIVITY_ID.equals(request.getContext().getActivityId())) // UMESH ADDED
	    response = geCCDTransactionLimitForUB(request);
	else if (ActivityIdConstantBean.GHIPPS_FUND_TRANSFER_OTHER_BANK_ACCOUNTS.equals(request.getContext().getActivityId())) // GHIPPS ADDED
		response = getGHIPPSTransactionLimitForUB(request);
	response.setSuccess(true);

	if (ActivityIdConstantBean.FUND_TRANSFER_CARD_PAYMENT_ONETIME_ACTIVITY_ID.equals(request.getContext().getActivityId())) {

	    handleCreditCardTransactionLimit(request, response, amtInLCY, txnCount);

	} else {
	    if (response.getTransactionMaxLimitUB() != null && response.getTransactionMinLimitUB() != null
		    && (amtInLCY > response.getTransactionMaxLimitUB().doubleValue() || amtInLCY < response.getTransactionMinLimitUB().doubleValue())) {
		response.setSuccess(false);
		response.setResMsg(MessageFormat.format(getErrorMessagefromDB(request, FundTransferResponseCodeConstants.TXN_LIMIT_RANGE_UB), request
			.getContext().getLocalCurrency(), BMGFormatUtility.getFormattedAmount(response.getTransactionLimit())));
		response.setResCde(FundTransferResponseCodeConstants.TXN_LIMIT_RANGE_UB);
		response.setErrTyp(ErrorCodeConstant.RECOVERABLE_ERROR);
	    }
	    if (response.isSuccess() && response.getAValidDailyLimit() != null && amtInLCY > response.getAValidDailyLimit().doubleValue()) {
		response.setSuccess(false);
		response.setResMsg(MessageFormat.format(getErrorMessagefromDB(request, FundTransferResponseCodeConstants.TXN_DAILY_LIMIT_COM),
			request.getContext().getLocalCurrency(), BMGFormatUtility.getFormattedAmount(response.getAValidDailyLimit())));
		response.setResCde(FundTransferResponseCodeConstants.TXN_DAILY_LIMIT_COM);
		response.setErrTyp(ErrorCodeConstant.RECOVERABLE_ERROR);
	    }

	    if (response.isSuccess() && Integer.valueOf(response.getMaxTxns()) != null && txnCount >= response.getMaxTxns()) {
		response.setSuccess(false);
		response.setResMsg(MessageFormat.format(getErrorMessagefromDB(request, FundTransferResponseCodeConstants.TXN_LIMIT_COUNT), "", ""));
		response.setResCde(FundTransferResponseCodeConstants.TXN_LIMIT_COUNT);
		response.setErrTyp(ErrorCodeConstant.RECOVERABLE_ERROR);
	    }
	}

	return response;
    }

    private void handleCreditCardTransactionLimit(TransactionLimitServiceRequest request, TransactionLimitServiceResponse response, Double amtInLCY,
	    int txnCount) {
	if (response.getTransactionMaxLimitUB() != null && response.getTransactionMinLimitUB() != null
		&& (amtInLCY > response.getTransactionMaxLimitUB().doubleValue() || amtInLCY < response.getTransactionMinLimitUB().doubleValue())) {
	    response.setSuccess(false);
	    response.setResMsg(MessageFormat.format(getErrorMessagefromDB(request, FundTransferResponseCodeConstants.CC_TXN_LIMIT_RANGE_UB), request
		    .getContext().getLocalCurrency(), BMGFormatUtility.getFormattedAmount(response.getTransactionLimit())));
	    response.setResCde(FundTransferResponseCodeConstants.TXN_LIMIT_RANGE_UB);
	    response.setErrTyp(ErrorCodeConstant.RECOVERABLE_ERROR);
	}
	if (response.isSuccess() && response.getAValidDailyLimit() != null && amtInLCY > response.getAValidDailyLimit().doubleValue()) {
	    response.setSuccess(false);
	    response.setResMsg(MessageFormat.format(getErrorMessagefromDB(request, FundTransferResponseCodeConstants.CC_TXN_DAILY_LIMIT_COM), request
		    .getContext().getLocalCurrency(), BMGFormatUtility.getFormattedAmount(response.getAValidDailyLimit())));
	    response.setResCde(FundTransferResponseCodeConstants.CC_TXN_DAILY_LIMIT_COM);
	    response.setErrTyp(ErrorCodeConstant.RECOVERABLE_ERROR);
	}

	if (response.isSuccess() && Integer.valueOf(response.getMaxTxns()) != null && txnCount >= response.getMaxTxns()) {
	    response.setSuccess(false);
	    response.setResMsg(MessageFormat.format(getErrorMessagefromDB(request, FundTransferResponseCodeConstants.CC_TXN_LIMIT_COUNT), "", ""));
	    response.setResCde(FundTransferResponseCodeConstants.TXN_LIMIT_COUNT);
	    response.setErrTyp(ErrorCodeConstant.RECOVERABLE_ERROR);
	}
    }

    private TransactionLimitServiceResponse getCashSendTransactionLimitForUB(TransactionLimitServiceRequest request) {

	TransactionLimitServiceResponse response = new TransactionLimitServiceResponse();
	ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
	listValueResServiceRequest.setContext(request.getContext());
	// listValueResServiceRequest.getContext().setSystemId(SystemIdConstants.UB);//Mocked !!!!!!!!!!!!!!!!!
	listValueResServiceRequest.setGroup(SYS_PARAM_CS);
	ListValueResByGroupServiceResponse listResp = listValueResService.getListValueByGroup(listValueResServiceRequest);

	List<ListValueCacheDTO> listValuesDTOList = null;
	if (listResp != null) {
	    listValuesDTOList = listResp.getListValueCahceDTO();
	}

	if (listValuesDTOList != null && !listValuesDTOList.isEmpty()) {

	    for (ListValueCacheDTO valueresDTO : listValuesDTOList) {

		if (valueresDTO.getKey() != null && SystemParameterConstant.CS_MAX_AMT.equals(valueresDTO.getKey())) {

		    response.setTransactionMaxLimitUB(new BigDecimal(valueresDTO.getLabel()));

		} else if (valueresDTO.getKey() != null && SystemParameterConstant.CS_MIN_AMT.equals(valueresDTO.getKey())) {

		    response.setTransactionMinLimitUB(new BigDecimal(valueresDTO.getLabel()));

		} else if (valueresDTO.getKey() != null && SystemParameterConstant.CS_MAX_DAYAMT.equals(valueresDTO.getKey())) {

		    BigDecimal avlblDailyLimit = transactionLimitDAO.getTransactionAvlblDailyLimitForUB(request, new BigDecimal(valueresDTO
			    .getLabel()));
		    response.setAValidDailyLimit(avlblDailyLimit);

		} else if (valueresDTO.getKey() != null && SystemParameterConstant.CS_MAX_TXS.equals(valueresDTO.getKey())) {
		    response.setMaxTxns(Integer.valueOf(valueresDTO.getLabel()));
		}
	    }
	}

	return response;

    }

    private TransactionLimitServiceResponse geCCDTransactionLimitForUB(TransactionLimitServiceRequest request) {

	TransactionLimitServiceResponse response = new TransactionLimitServiceResponse();
	ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
	listValueResServiceRequest.setContext(request.getContext());
	// listValueResServiceRequest.getContext().setSystemId(SystemIdConstants.UB);//Mocked !!!!!!!!!!!!!!!!!
	listValueResServiceRequest.setGroup(SYS_PARAM_CCOWN);
	ListValueResByGroupServiceResponse listResp = listValueResService.getListValueByGroup(listValueResServiceRequest);

	List<ListValueCacheDTO> listValuesDTOList = null;
	if (listResp != null) {
	    listValuesDTOList = listResp.getListValueCahceDTO();
	}

	if (listValuesDTOList != null && !listValuesDTOList.isEmpty()) {

	    for (ListValueCacheDTO valueresDTO : listValuesDTOList) {

		if (valueresDTO.getKey() != null && SystemParameterConstant.CCOWN_MAX_AMT.equals(valueresDTO.getKey())) {

		    response.setTransactionMaxLimitUB(new BigDecimal(valueresDTO.getLabel()));

		} else if (valueresDTO.getKey() != null && SystemParameterConstant.CCOWN_MIN_AMT.equals(valueresDTO.getKey())) {

		    response.setTransactionMinLimitUB(new BigDecimal(valueresDTO.getLabel()));

		} else if (valueresDTO.getKey() != null && SystemParameterConstant.CCOWN_MAX_DAYAMT.equals(valueresDTO.getKey())) {

		    BigDecimal avlblDailyLimit = transactionLimitDAO.getTransactionAvlblDailyLimitForUB(request, new BigDecimal(valueresDTO
			    .getLabel()));
		    response.setAValidDailyLimit(avlblDailyLimit);

		} else if (valueresDTO.getKey() != null && SystemParameterConstant.CCOWN_MAX_TXS.equals(valueresDTO.getKey())) {
		    response.setMaxTxns(Integer.valueOf(valueresDTO.getLabel()));
		}
	    }
	}

	return response;

    }

    /**
     * Get Error Message from Data base.
     *
     * @param request
     * @param errorKey
     * @return
     */
    private String getErrorMessagefromDB(RequestContext request, String errorKey) {
	MessageResourceServiceRequest messageRequest = new MessageResourceServiceRequest();
	messageRequest.setContext(request.getContext());
	messageRequest.setMessageKey(errorKey);

	MessageResourceServiceResponse messageResponse = messageResourceService.getMessageDescByKey(messageRequest);
	return messageResponse.getMessageDesc();
    }

    public TransactionLimitServiceResponse getTransactionLimit(TransactionLimitServiceRequest request) {
	return transactionLimitDAO.getTransactionLimit(request);
    }

    public TransactionLimitServiceResponse getBPTransactionLimitForUB(TransactionLimitServiceRequest request) {
	TransactionLimitServiceResponse response = new TransactionLimitServiceResponse();
	ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
	listValueResServiceRequest.setContext(request.getContext());
	// listValueResServiceRequest.getContext().setSystemId(SystemIdConstants.UB); //Mocked !!!!!!!!!!!!!!!!!
	listValueResServiceRequest.setGroup(SYS_PARAM_BP_GROUP);
	ListValueResByGroupServiceResponse listResp = listValueResService.getListValueByGroup(listValueResServiceRequest);

	List<ListValueCacheDTO> listValuesDTOList = null;
	if (listResp != null) {
	    listValuesDTOList = listResp.getListValueCahceDTO();
	}

	if (listValuesDTOList != null && !listValuesDTOList.isEmpty()) {

	    for (ListValueCacheDTO valueresDTO : listValuesDTOList) {

		if (valueresDTO.getKey() != null && SystemParameterConstant.BILLPAY_MAX_AMT.equals(valueresDTO.getKey())) {

		    response.setTransactionMaxLimitUB(new BigDecimal(valueresDTO.getLabel()));

		} else if (valueresDTO.getKey() != null && SystemParameterConstant.BILLPAY_MIN_AMT.equals(valueresDTO.getKey())) {

		    response.setTransactionMinLimitUB(new BigDecimal(valueresDTO.getLabel()));

		} else if (valueresDTO.getKey() != null && SystemParameterConstant.BILLPAY_MAX_DAYAMT.equals(valueresDTO.getKey())) {

		    BigDecimal avlblDailyLimit = transactionLimitDAO.getTransactionAvlblDailyLimitForUB(request, new BigDecimal(valueresDTO
			    .getLabel()));
		    response.setAValidDailyLimit(avlblDailyLimit);

		} else if (valueresDTO.getKey() != null && SystemParameterConstant.BILLPAY_MAX_TXS.equals(valueresDTO.getKey())) {
		    response.setMaxTxns(Integer.valueOf(valueresDTO.getLabel()));
		}
	    }
	}

	return response;
    }

    public TransactionLimitServiceResponse getFTOwnTransactionLimitForUB(TransactionLimitServiceRequest request) {
	TransactionLimitServiceResponse response = new TransactionLimitServiceResponse();
	ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
	listValueResServiceRequest.setContext(request.getContext());
	// listValueResServiceRequest.getContext().setSystemId(SystemIdConstants.UB);//Mocked !!!!!!!!!!!!!!!!!
	listValueResServiceRequest.setGroup(SYS_PARAM_FTOWN);
	ListValueResByGroupServiceResponse listResp = listValueResService.getListValueByGroup(listValueResServiceRequest);

	List<ListValueCacheDTO> listValuesDTOList = null;
	if (listResp != null) {
	    listValuesDTOList = listResp.getListValueCahceDTO();
	}

	if (listValuesDTOList != null && !listValuesDTOList.isEmpty()) {

	    for (ListValueCacheDTO valueresDTO : listValuesDTOList) {

		if (valueresDTO.getKey() != null && SystemParameterConstant.FTOWN_MAX_AMT.equals(valueresDTO.getKey())) {

		    response.setTransactionMaxLimitUB(new BigDecimal(valueresDTO.getLabel()));

		} else if (valueresDTO.getKey() != null && SystemParameterConstant.FTOWN_MIN_AMT.equals(valueresDTO.getKey())) {

		    response.setTransactionMinLimitUB(new BigDecimal(valueresDTO.getLabel()));

		} else if (valueresDTO.getKey() != null && SystemParameterConstant.FTOWN_MAX_DAYAMT.equals(valueresDTO.getKey())) {

		    BigDecimal avlblDailyLimit = transactionLimitDAO.getTransactionAvlblDailyLimitForUB(request, new BigDecimal(valueresDTO
			    .getLabel()));
		    response.setAValidDailyLimit(avlblDailyLimit);

		} else if (valueresDTO.getKey() != null && SystemParameterConstant.FTOWN_MAX_TXS.equals(valueresDTO.getKey())) {
		    response.setMaxTxns(Integer.valueOf(valueresDTO.getLabel()));
		}
	    }
	}

	return response;
    }

    public TransactionLimitServiceResponse getFTOtherTransactionLimitForUB(TransactionLimitServiceRequest request) {
	TransactionLimitServiceResponse response = new TransactionLimitServiceResponse();
	ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
	listValueResServiceRequest.setContext(request.getContext());
	// listValueResServiceRequest.getContext().setSystemId(SystemIdConstants.UB);//Mocked !!!!!!!!!!!!!!!!!
	listValueResServiceRequest.setGroup(SYS_PARAM_FTOTH);
	ListValueResByGroupServiceResponse listResp = listValueResService.getListValueByGroup(listValueResServiceRequest);

	List<ListValueCacheDTO> listValuesDTOList = null;
	if (listResp != null) {
	    listValuesDTOList = listResp.getListValueCahceDTO();
	}

	if (listValuesDTOList != null && !listValuesDTOList.isEmpty()) {

	    for (ListValueCacheDTO valueresDTO : listValuesDTOList) {

		if (valueresDTO.getKey() != null && SystemParameterConstant.FTOTH_MAX_AMT.equals(valueresDTO.getKey())) {
		    response.setTransactionMaxLimitUB(new BigDecimal(valueresDTO.getLabel()));
		} else if (valueresDTO.getKey() != null && SystemParameterConstant.FTOTH_MIN_AMT.equals(valueresDTO.getKey())) {
		    response.setTransactionMinLimitUB(new BigDecimal(valueresDTO.getLabel()));
		} else if (valueresDTO.getKey() != null && SystemParameterConstant.FTOTH_MAX_TXS.equals(valueresDTO.getKey())) {
		    response.setMaxTxns(Integer.valueOf(valueresDTO.getLabel()));
		} else if (valueresDTO.getKey() != null && SystemParameterConstant.FTOTH_MAX_DAYAMT.equals(valueresDTO.getKey())) {
		    BigDecimal avlblDailyLimit = transactionLimitDAO.getTransactionAvlblDailyLimitForUB(request, new BigDecimal(valueresDTO
			    .getLabel()));
		    response.setAValidDailyLimit(avlblDailyLimit);
		}
	    }
	}

	return response;
    }

    public TransactionLimitServiceResponse getDFTTransactionLimitForUB(TransactionLimitServiceRequest request) {
	TransactionLimitServiceResponse response = new TransactionLimitServiceResponse();
	ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
	listValueResServiceRequest.setContext(request.getContext());
	// listValueResServiceRequest.getContext().setSystemId(SystemIdConstants.UB);//Mocked !!!!!!!!!!!!!!!!!
	listValueResServiceRequest.setGroup(SYS_PARAM_DFT);
	ListValueResByGroupServiceResponse listResp = listValueResService.getListValueByGroup(listValueResServiceRequest);

	List<ListValueCacheDTO> listValuesDTOList = null;
	if (listResp != null) {
	    listValuesDTOList = listResp.getListValueCahceDTO();
	}

	if (listValuesDTOList != null && !listValuesDTOList.isEmpty()) {

	    for (ListValueCacheDTO valueresDTO : listValuesDTOList) {

		if (valueresDTO.getKey() != null && SystemParameterConstant.FTDFT_MAX_AMT.equals(valueresDTO.getKey())) {

		    response.setTransactionMaxLimitUB(new BigDecimal(valueresDTO.getLabel()));

		} else if (valueresDTO.getKey() != null && SystemParameterConstant.FTDFT_MIN_AMT.equals(valueresDTO.getKey())) {

		    response.setTransactionMinLimitUB(new BigDecimal(valueresDTO.getLabel()));

		} else if (valueresDTO.getKey() != null && SystemParameterConstant.FTDFT_MAX_TXS.equals(valueresDTO.getKey())) {
		    response.setMaxTxns(Integer.valueOf(valueresDTO.getLabel()));
		} else if (valueresDTO.getKey() != null && SystemParameterConstant.FTDFT_MAX_DAYAMT.equals(valueresDTO.getKey())) {
		    BigDecimal avlblDailyLimit = transactionLimitDAO.getTransactionAvlblDailyLimitForUB(request, new BigDecimal(valueresDTO
			    .getLabel()));
		    response.setAValidDailyLimit(avlblDailyLimit);
		}
	    }
	}

	return response;
    }

    public TransactionLimitServiceResponse getMobileTopUpTransactionLimitForUB(TransactionLimitServiceRequest request) {
	TransactionLimitServiceResponse response = new TransactionLimitServiceResponse();
	ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
	listValueResServiceRequest.setContext(request.getContext());
	// listValueResServiceRequest.getContext().setSystemId(SystemIdConstants.UB);//Mocked !!!!!!!!!!!!!!!!!
	listValueResServiceRequest.setGroup(SYS_PARAM_TOPUP);
	ListValueResByGroupServiceResponse listResp = listValueResService.getListValueByGroup(listValueResServiceRequest);

	List<ListValueCacheDTO> listValuesDTOList = null;
	if (listResp != null) {
	    listValuesDTOList = listResp.getListValueCahceDTO();
	}

	if (listValuesDTOList != null && !listValuesDTOList.isEmpty()) {

	    for (ListValueCacheDTO valueresDTO : listValuesDTOList) {

		if (valueresDTO.getKey() != null && SystemParameterConstant.MAX_TOPUP_AMT.equals(valueresDTO.getKey())) {

		    response.setTransactionMaxLimitUB(new BigDecimal(valueresDTO.getLabel()));

		} else if (valueresDTO.getKey() != null && SystemParameterConstant.MIN_TOPUP_AMT.equals(valueresDTO.getKey())) {

		    response.setTransactionMinLimitUB(new BigDecimal(valueresDTO.getLabel()));

		} else if (valueresDTO.getKey() != null && SystemParameterConstant.TOPUP_MAX_DAYAMT.equals(valueresDTO.getKey())) {

		    BigDecimal avlblDailyLimit = transactionLimitDAO.getTransactionAvlblDailyLimitForUB(request, new BigDecimal(valueresDTO
			    .getLabel()));
		    response.setAValidDailyLimit(avlblDailyLimit);

		} else if (valueresDTO.getKey() != null && SystemParameterConstant.TOPUP_MAX_TXS.equals(valueresDTO.getKey())) {
		    response.setMaxTxns(Integer.valueOf(valueresDTO.getLabel()));
		}

	    }
	}

	return response;
    }

    public TransactionLimitServiceResponse getMWalletTransactionLimitForUB(TransactionLimitServiceRequest request) {
	TransactionLimitServiceResponse response = new TransactionLimitServiceResponse();
	ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
	listValueResServiceRequest.setContext(request.getContext());
	// listValueResServiceRequest.getContext().setSystemId(SystemIdConstants.UB);//Mocked !!!!!!!!!!!!!!!!!
	listValueResServiceRequest.setGroup(SYS_PARAM_MWALLET);
	ListValueResByGroupServiceResponse listResp = listValueResService.getListValueByGroup(listValueResServiceRequest);

	List<ListValueCacheDTO> listValuesDTOList = null;
	if (listResp != null) {
	    listValuesDTOList = listResp.getListValueCahceDTO();
	}

	if (listValuesDTOList != null && !listValuesDTOList.isEmpty()) {

	    for (ListValueCacheDTO valueresDTO : listValuesDTOList) {
		if (valueresDTO.getKey() != null && SystemParameterConstant.MWALLET_MAX_AMT.equals(valueresDTO.getKey())) {
		    response.setTransactionMaxLimitUB(new BigDecimal(valueresDTO.getLabel()));
		} else if (valueresDTO.getKey() != null && SystemParameterConstant.MWALLET_MIN_AMT.equals(valueresDTO.getKey())) {
		    response.setTransactionMinLimitUB(new BigDecimal(valueresDTO.getLabel()));
		} else if (valueresDTO.getKey() != null && SystemParameterConstant.MWALLET_MAX_DAYAMT.equals(valueresDTO.getKey())) {
		    BigDecimal avlblDailyLimit = transactionLimitDAO.getTransactionAvlblDailyLimitForUB(request, new BigDecimal(valueresDTO
			    .getLabel()));
		    response.setAValidDailyLimit(avlblDailyLimit);
		} else if (valueresDTO.getKey() != null && SystemParameterConstant.MWALLET_MAX_TXS.equals(valueresDTO.getKey())) {
		    response.setMaxTxns(Integer.valueOf(valueresDTO.getLabel()));
		}
	    }
	}

	return response;
    }

    public TransactionLimitDAO getTransactionLimitDAO() {
	return transactionLimitDAO;
    }

    public void setTransactionLimitDAO(TransactionLimitDAO transactionLimitDAO) {
	this.transactionLimitDAO = transactionLimitDAO;
    }

    public MessageResourceService getMessageResourceService() {
	return messageResourceService;
    }

    public void setMessageResourceService(MessageResourceService messageResourceService) {
	this.messageResourceService = messageResourceService;
    }

    public void setListValueResService(ListValueResService listValueResService) {
	this.listValueResService = listValueResService;
    }

    @Override
    public UpgradeTransactionLimitServiceResponse upgradeTransactionLimit(UpgradeTransactionLimitServiceRequest request) {
	String systemId = request.getContext().getSystemId();
	// String systemId=SystemIdConstants.UB; //!!!!!!!!! mocked

	if (SystemIdConstants.UB.equals(systemId))
	    return transactionLimitDAO.upgradeTransactionLimitForUB(request);
	else
	    return transactionLimitDAO.upgradeTransactionLimit(request);

    }

    public TransactionLimitServiceResponse getGHIPPSTransactionLimitForUB(TransactionLimitServiceRequest request) {
		TransactionLimitServiceResponse response = new TransactionLimitServiceResponse();
		ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
		listValueResServiceRequest.setContext(request.getContext());
		listValueResServiceRequest.setGroup(SYS_PARAM_GHIPPS);
		ListValueResByGroupServiceResponse listResp = listValueResService.getListValueByGroup(listValueResServiceRequest);

		List<ListValueCacheDTO> listValuesDTOList = null;
		if (listResp != null) {
		    listValuesDTOList = listResp.getListValueCahceDTO();
		}

		if (listValuesDTOList != null && !listValuesDTOList.isEmpty()) {

		    for (ListValueCacheDTO valueresDTO : listValuesDTOList) {

			if (valueresDTO.getKey() != null && SystemParameterConstant.FTRT_MAX_AMT.equals(valueresDTO.getKey())) {

			    response.setTransactionMaxLimitUB(new BigDecimal(valueresDTO.getLabel()));

			} else if (valueresDTO.getKey() != null && SystemParameterConstant.FTRT_MIN_AMT.equals(valueresDTO.getKey())) {

			    response.setTransactionMinLimitUB(new BigDecimal(valueresDTO.getLabel()));

			} else if (valueresDTO.getKey() != null && SystemParameterConstant.FTRT_MAX_TXS.equals(valueresDTO.getKey())) {
			    response.setMaxTxns(Integer.valueOf(valueresDTO.getLabel()));
			} else if (valueresDTO.getKey() != null && SystemParameterConstant.FTRT_MAX_DAYAMT.equals(valueresDTO.getKey())) {
			    BigDecimal avlblDailyLimit = transactionLimitDAO.getTransactionAvlblDailyLimitForUB(request, new BigDecimal(valueresDTO
				    .getLabel()));
			    response.setAValidDailyLimit(avlblDailyLimit);
			}
		    }
		}

		return response;
    }
}
