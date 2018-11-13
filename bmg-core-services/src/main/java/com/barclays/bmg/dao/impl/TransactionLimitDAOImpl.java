package com.barclays.bmg.dao.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.barclays.bmg.dao.TransactionLimitDAO;
import com.barclays.bmg.dto.TransactionLimitDTO;
import com.barclays.bmg.service.request.TransactionLimitServiceRequest;
import com.barclays.bmg.service.request.UpgradeTransactionLimitServiceRequest;
import com.barclays.bmg.service.response.TransactionLimitServiceResponse;
import com.barclays.bmg.service.response.UpgradeTransactionLimitServiceResponse;

public class TransactionLimitDAOImpl extends BaseDAOImpl implements TransactionLimitDAO {

    private static final String BUSINESS_ID = "businessId";
    private static final String SYSTEM_ID = "systemId";
    private static final String ACTIVITY_ID = "activityId";
    private static final String AMOUNT = "amount";
    private static final String TXN_LIMIT_FIND_GLOBAL = "findGlobalTxnLimit";
    private static final String TXN_LIMIT_FIND_INDIVIDUAL = "findIndividualTxnLimit";
    private static final String TXN_LIMIT_FIND_DAILY_USED = "findUsedTxnDailyLimit";
    private static final String CUSTOMER_ID = "customerId";
    private static final String TRAN_DATE = "transactionDate";
    private static final String TRAN_LIMIT_TYPE = "transactionLimitType";
    private static final String TXN_LIMIT_TYPE_FIND = "findTxnLimitType";
    private static final String TXN_LIMIT_FIND_TOTAL_COUNT = "findTxnLimitTotalCount";
    private static final String TXN_LIMIT_INSERT_TOTAL = "insertTxnLimitTotal";
    private static final String TXN_LIMIT_FIND_TOTAL_COUNT_TODAY = "findTxnLimitTotalCountToday";
    private static final String TXN_LIMIT_UPDATE_TOTAL = "updateTxnLimitTotal";
    private static final String TXN_LIMIT_UPDATE_TOTAL_TODAY = "updateTxnLimitTotalToday";
    private static final String TXN_LIMIT_FIND_DAILY_USED_UB = "findUsedTxnDailyLimitUB";
    private static final String TXN_LIMIT_FIND_TOTAL_COUNT_UB = "findTxnLimitTotalCountUB";
    private static final String TXN_LIMIT_INSERT_TOTAL_UB = "insertTxnLimitTotalUB";
    private static final String TXN_LIMIT_FIND_TOTAL_COUNT_TODAY_UB = "findTxnLimitTotalCountTodayUB";
    private static final String TXN_LIMIT_UPDATE_TOTAL_UB = "updateTxnLimitTotalUB";
    private static final String TXN_LIMIT_UPDATE_TOTAL_TODAY_UB = "updateTxnLimitTotalTodayUB";
    private static final String TXN_COUNT = "txnCount";
    private static final String TXN_LIMIT_FIND_TXN_COUNT_UB = "findTxnLimitTxnCountUB";

    @Override
    public TransactionLimitServiceResponse getTransactionLimit(TransactionLimitServiceRequest request) {

	TransactionLimitDTO transactionLimitDTO = getDefinedTransactionLimit(request);

	TransactionLimitServiceResponse response = null;

	if (transactionLimitDTO != null) {

	    response = new TransactionLimitServiceResponse();
	    response.setThresholdLimit(transactionLimitDTO.getThresholdLimit());
	    response.setTransactionLimit(transactionLimitDTO.getTransactionLimit());
	    BigDecimal dailyLimit = transactionLimitDTO.getDailyLimit();
	    // Todo: need to set actual transaction date
	    transactionLimitDTO.setTransactionDate(Calendar.getInstance().getTime());
	    if (transactionLimitDTO.getTransactionDate() != null) {
		response.setAValidDailyLimit(getAValidDailyLimit(request, transactionLimitDTO.getTransactionDate(), transactionLimitDTO
			.getTransactionLimitType(), dailyLimit));
	    } else {
		response.setAValidDailyLimit(dailyLimit);
	    }
	}

	return response;
    }

    @Override
    public BigDecimal getTransactionAvlblDailyLimitForUB(TransactionLimitServiceRequest request, BigDecimal dailyAmount) {

	TransactionLimitDTO transactionLimitDTO = new TransactionLimitDTO();
	// TransactionLimitServiceResponse response = null;
	BigDecimal avlblDailyLimit = null;
	BigDecimal dailyLimit = dailyAmount;
	UpgradeTransactionLimitServiceRequest req = new UpgradeTransactionLimitServiceRequest();
	req.setContext(request.getContext());
	transactionLimitDTO.setTransactionLimitType(getTransactionLimitType(req));
	// Todo: need to set actual transaction date
	transactionLimitDTO.setTransactionDate(Calendar.getInstance().getTime());
	if (transactionLimitDTO.getTransactionDate() != null) {
	    avlblDailyLimit = getAValidDailyLimitForUB(request, transactionLimitDTO.getTransactionDate(), transactionLimitDTO
		    .getTransactionLimitType(), dailyLimit);
	} else {
	    avlblDailyLimit = dailyLimit;
	}

	return avlblDailyLimit;
    }

    private TransactionLimitDTO getDefinedTransactionLimit(TransactionLimitServiceRequest request) {

	TransactionLimitDTO individualLimit = getDefinedIndividualTransactionLimit(request);
	TransactionLimitDTO globalLimit = getDefinedGlobalTransactionLimit(request);

	if (individualLimit == null) {
	    individualLimit = globalLimit;
	} else if (globalLimit == null) {
	    individualLimit.setThresholdLimit(getDefaultValue());
	    if (individualLimit.getTransactionLimit() == null) {
		individualLimit.setTransactionLimit(getDefaultValue());
	    }
	    if (individualLimit.getDailyLimit() == null) {
		individualLimit.setDailyLimit(getDefaultValue());
	    }
	} else {
	    individualLimit.setThresholdLimit(globalLimit.getThresholdLimit());

	    if (individualLimit.getTransactionLimit() == null) {
		individualLimit.setTransactionLimit(globalLimit.getTransactionLimit());
	    }
	    if (individualLimit.getDailyLimit() == null) {
		individualLimit.setDailyLimit(globalLimit.getDailyLimit());
	    }
	}

	return individualLimit;
    }

    private TransactionLimitDTO getDefinedGlobalTransactionLimit(TransactionLimitServiceRequest request) {

	Map<String, String> parameterMap = new HashMap<String, String>();
	parameterMap.put(BUSINESS_ID, request.getContext().getBusinessId());
	parameterMap.put(SYSTEM_ID, request.getContext().getSystemId());
	parameterMap.put(ACTIVITY_ID, request.getContext().getActivityId());
	return (TransactionLimitDTO) this.queryForObject(TXN_LIMIT_FIND_GLOBAL, parameterMap);
    }

    private TransactionLimitDTO getDefinedIndividualTransactionLimit(TransactionLimitServiceRequest request) {
	Map<String, String> parameterMap = new HashMap<String, String>();
	parameterMap.put(BUSINESS_ID, request.getContext().getBusinessId());
	parameterMap.put(SYSTEM_ID, request.getContext().getSystemId());
	parameterMap.put(ACTIVITY_ID, request.getContext().getActivityId());
	parameterMap.put(CUSTOMER_ID, request.getContext().getCustomerId());

	return (TransactionLimitDTO) this.queryForObject(TXN_LIMIT_FIND_INDIVIDUAL, parameterMap);
    }

    private BigDecimal getDefaultValue() {
	return BigDecimal.valueOf(0.0);
    }

    private BigDecimal getAValidDailyLimit(TransactionLimitServiceRequest request, Date tranDate, String tranType, BigDecimal dailyLimit) {

	BigDecimal usedAmount = getUsedDailyTransactionAmount(request, tranDate, tranType);
	return BigDecimal.valueOf(dailyLimit.doubleValue() - usedAmount.doubleValue());
    }

    private BigDecimal getAValidDailyLimitForUB(TransactionLimitServiceRequest request, Date tranDate, String tranType, BigDecimal dailyLimit) {

	BigDecimal usedAmount = getUsedDailyTransactionAmountForUB(request, tranDate, tranType);
	return BigDecimal.valueOf(dailyLimit.doubleValue() - usedAmount.doubleValue());
    }

    private BigDecimal getUsedDailyTransactionAmount(TransactionLimitServiceRequest request, Date tranDate, String tranType) {

	Map<String, Object> parameterMap = new HashMap<String, Object>();
	parameterMap.put(BUSINESS_ID, request.getContext().getBusinessId());
	parameterMap.put(SYSTEM_ID, request.getContext().getSystemId());
	parameterMap.put(CUSTOMER_ID, request.getContext().getCustomerId());
	// CHECK DATE needs to be passed.
	parameterMap.put(TRAN_DATE, tranDate);
	parameterMap.put(TRAN_LIMIT_TYPE, tranType);
	BigDecimal usedAmount = (BigDecimal) this.queryForObject(TXN_LIMIT_FIND_DAILY_USED, parameterMap);
	if (usedAmount == null) {
	    usedAmount = BigDecimal.valueOf(0);
	}
	return usedAmount;
    }

    @Override
    public UpgradeTransactionLimitServiceResponse upgradeTransactionLimit(UpgradeTransactionLimitServiceRequest request) {

	String tranLimitType = getTransactionLimitType(request);
	UpgradeTransactionLimitServiceResponse response = new UpgradeTransactionLimitServiceResponse();
	if (tranLimitType == null || tranLimitType.length() == 0) {
	    response.setSuccess(false);
	} else {
	    Integer dailyTotalCount = getDailyTotalCount(request, tranLimitType);
	    if (Integer.valueOf(0).equals(dailyTotalCount)) {
		insertTxnLimitTotal(request, tranLimitType);
	    } else {
		Integer dailyTotalCountToday = getDailyTotalCountToday(request, tranLimitType);
		if (Integer.valueOf(0).equals(dailyTotalCountToday)) {
		    updateTxnLimitTotal(request, tranLimitType);
		} else {
		    updateTxnLimitTotalToday(request, tranLimitType);
		}
	    }
	}
	return response;
    }

    private String getTransactionLimitType(UpgradeTransactionLimitServiceRequest request) {
	Map<String, String> parameterMap = new HashMap<String, String>();
	parameterMap.put(BUSINESS_ID, request.getContext().getBusinessId());
	parameterMap.put(SYSTEM_ID, request.getContext().getSystemId());
	parameterMap.put(ACTIVITY_ID, request.getContext().getActivityId());

	String tranLimitType = (String) this.queryForObject(TXN_LIMIT_TYPE_FIND, parameterMap);
	return tranLimitType;
    }

    private Integer getDailyTotalCount(UpgradeTransactionLimitServiceRequest request, String tranLimitType) {
	Map<String, String> parameterMap = new HashMap<String, String>();
	parameterMap.put(BUSINESS_ID, request.getContext().getBusinessId());
	parameterMap.put(SYSTEM_ID, request.getContext().getSystemId());
	parameterMap.put(CUSTOMER_ID, request.getContext().getCustomerId());
	parameterMap.put(TRAN_LIMIT_TYPE, tranLimitType);

	Integer dailyTotalCount = (Integer) this.queryForObject(TXN_LIMIT_FIND_TOTAL_COUNT, parameterMap);
	return dailyTotalCount;
    }

    private void insertTxnLimitTotal(UpgradeTransactionLimitServiceRequest request, String tranLimitType) {
	Map<String, Object> parameterMap = new HashMap<String, Object>();
	parameterMap.put(BUSINESS_ID, request.getContext().getBusinessId());
	parameterMap.put(SYSTEM_ID, request.getContext().getSystemId());
	parameterMap.put(CUSTOMER_ID, request.getContext().getCustomerId());
	parameterMap.put(TRAN_DATE, request.getTxnDate());
	BigDecimal amount = request.getAmtInLCY().getAmount().setScale(2, RoundingMode.HALF_UP);
	parameterMap.put(AMOUNT, amount);
	parameterMap.put(TRAN_LIMIT_TYPE, tranLimitType);
	this.insert(TXN_LIMIT_INSERT_TOTAL, parameterMap);

    }

    private Integer getDailyTotalCountToday(UpgradeTransactionLimitServiceRequest request, String tranLimitType) {
	Map<String, Object> parameterMap = new HashMap<String, Object>();
	parameterMap.put(BUSINESS_ID, request.getContext().getBusinessId());
	parameterMap.put(SYSTEM_ID, request.getContext().getSystemId());
	parameterMap.put(CUSTOMER_ID, request.getContext().getCustomerId());
	parameterMap.put(TRAN_DATE, request.getTxnDate());
	parameterMap.put(TRAN_LIMIT_TYPE, tranLimitType);

	Integer dailyTotalCountToday = (Integer) this.queryForObject(TXN_LIMIT_FIND_TOTAL_COUNT_TODAY, parameterMap);
	return dailyTotalCountToday;
    }

    private void updateTxnLimitTotal(UpgradeTransactionLimitServiceRequest request, String tranLimitType) {
	Map<String, Object> parameterMap = new HashMap<String, Object>();
	parameterMap.put(BUSINESS_ID, request.getContext().getBusinessId());
	parameterMap.put(SYSTEM_ID, request.getContext().getSystemId());
	parameterMap.put(CUSTOMER_ID, request.getContext().getCustomerId());
	parameterMap.put(TRAN_DATE, request.getTxnDate());
	parameterMap.put(TRAN_LIMIT_TYPE, tranLimitType);
	BigDecimal amount = request.getAmtInLCY().getAmount().setScale(2, RoundingMode.HALF_UP);
	parameterMap.put(AMOUNT, amount);
	this.update(TXN_LIMIT_UPDATE_TOTAL, parameterMap);
    }

    private void updateTxnLimitTotalToday(UpgradeTransactionLimitServiceRequest request, String tranLimitType) {
	Map<String, Object> parameterMap = new HashMap<String, Object>();
	parameterMap.put(BUSINESS_ID, request.getContext().getBusinessId());
	parameterMap.put(SYSTEM_ID, request.getContext().getSystemId());
	parameterMap.put(CUSTOMER_ID, request.getContext().getCustomerId());
	parameterMap.put(TRAN_DATE, request.getTxnDate());
	parameterMap.put(TRAN_LIMIT_TYPE, tranLimitType);
	BigDecimal amount = request.getAmtInLCY().getAmount().setScale(2, RoundingMode.HALF_UP);
	parameterMap.put(AMOUNT, amount);
	this.update(TXN_LIMIT_UPDATE_TOTAL_TODAY, parameterMap);
    }

    /*
     * For UB System Id getting transaction limit data
     */

    private BigDecimal getUsedDailyTransactionAmountForUB(TransactionLimitServiceRequest request, Date tranDate, String tranType) {

	Map<String, Object> parameterMap = new HashMap<String, Object>();
	parameterMap.put(BUSINESS_ID, request.getContext().getBusinessId());
	parameterMap.put(SYSTEM_ID, request.getContext().getSystemId());
	parameterMap.put(CUSTOMER_ID, request.getContext().getCustomerId());
	// CHECK DATE needs to be passed.
	parameterMap.put(TRAN_DATE, tranDate);
	parameterMap.put(TRAN_LIMIT_TYPE, tranType);
	BigDecimal usedAmount = (BigDecimal) this.queryForObject(TXN_LIMIT_FIND_DAILY_USED_UB, parameterMap);
	if (usedAmount == null) {
	    usedAmount = BigDecimal.valueOf(0);
	}
	return usedAmount;
    }

    @Override
    public UpgradeTransactionLimitServiceResponse upgradeTransactionLimitForUB(UpgradeTransactionLimitServiceRequest request) {

	String tranLimitType = getTransactionLimitType(request);
	UpgradeTransactionLimitServiceResponse response = new UpgradeTransactionLimitServiceResponse();
	if (tranLimitType == null || tranLimitType.length() == 0) {
	    response.setSuccess(false);
	} else {
	    Integer dailyTotalCount = getDailyTotalCountUB(request, tranLimitType);
	    if (Integer.valueOf(0).equals(dailyTotalCount)) {
		insertTxnLimitTotalUB(request, tranLimitType);
	    } else {
		Integer dailyTotalCountToday = getDailyTotalCountTodayUB(request, tranLimitType);
		if (Integer.valueOf(0).equals(dailyTotalCountToday)) {
		    updateTxnLimitTotalUB(request, tranLimitType);
		} else {
		    updateTxnLimitTotalTodayUB(request, tranLimitType);
		}
	    }
	}
	return response;
    }

    private Integer getDailyTotalCountUB(UpgradeTransactionLimitServiceRequest request, String tranLimitType) {
	Map<String, String> parameterMap = new HashMap<String, String>();
	parameterMap.put(BUSINESS_ID, request.getContext().getBusinessId());
	parameterMap.put(SYSTEM_ID, request.getContext().getSystemId());
	parameterMap.put(CUSTOMER_ID, request.getContext().getCustomerId());
	parameterMap.put(TRAN_LIMIT_TYPE, tranLimitType);

	Integer dailyTotalCount = (Integer) this.queryForObject(TXN_LIMIT_FIND_TOTAL_COUNT_UB, parameterMap);
	return dailyTotalCount;
    }

    private void insertTxnLimitTotalUB(UpgradeTransactionLimitServiceRequest request, String tranLimitType) {
	Map<String, Object> parameterMap = new HashMap<String, Object>();
	parameterMap.put(BUSINESS_ID, request.getContext().getBusinessId());
	parameterMap.put(SYSTEM_ID, request.getContext().getSystemId());
	parameterMap.put(CUSTOMER_ID, request.getContext().getCustomerId());
	parameterMap.put(TRAN_DATE, request.getTxnDate());
	BigDecimal amount = request.getAmtInLCY().getAmount().setScale(2, RoundingMode.HALF_UP);
	parameterMap.put(AMOUNT, amount);
	parameterMap.put(TRAN_LIMIT_TYPE, tranLimitType);
	parameterMap.put(TXN_COUNT, 1);

	this.insert(TXN_LIMIT_INSERT_TOTAL_UB, parameterMap);

    }

    private Integer getDailyTotalCountTodayUB(UpgradeTransactionLimitServiceRequest request, String tranLimitType) {
	Map<String, Object> parameterMap = new HashMap<String, Object>();
	parameterMap.put(BUSINESS_ID, request.getContext().getBusinessId());
	parameterMap.put(SYSTEM_ID, request.getContext().getSystemId());
	parameterMap.put(CUSTOMER_ID, request.getContext().getCustomerId());
	parameterMap.put(TRAN_DATE, request.getTxnDate());
	parameterMap.put(TRAN_LIMIT_TYPE, tranLimitType);

	Integer dailyTotalCountToday = (Integer) this.queryForObject(TXN_LIMIT_FIND_TOTAL_COUNT_TODAY_UB, parameterMap);
	return dailyTotalCountToday;
    }

    private void updateTxnLimitTotalUB(UpgradeTransactionLimitServiceRequest request, String tranLimitType) {
	Map<String, Object> parameterMap = new HashMap<String, Object>();
	parameterMap.put(BUSINESS_ID, request.getContext().getBusinessId());
	parameterMap.put(SYSTEM_ID, request.getContext().getSystemId());
	parameterMap.put(CUSTOMER_ID, request.getContext().getCustomerId());
	parameterMap.put(TRAN_DATE, request.getTxnDate());
	parameterMap.put(TRAN_LIMIT_TYPE, tranLimitType);
	BigDecimal amount = request.getAmtInLCY().getAmount().setScale(2, RoundingMode.HALF_UP);
	parameterMap.put(AMOUNT, amount);
	this.update(TXN_LIMIT_UPDATE_TOTAL_UB, parameterMap);
    }

    private void updateTxnLimitTotalTodayUB(UpgradeTransactionLimitServiceRequest request, String tranLimitType) {
	Map<String, Object> parameterMap = new HashMap<String, Object>();
	parameterMap.put(BUSINESS_ID, request.getContext().getBusinessId());
	parameterMap.put(SYSTEM_ID, request.getContext().getSystemId());
	parameterMap.put(CUSTOMER_ID, request.getContext().getCustomerId());
	parameterMap.put(TRAN_DATE, request.getTxnDate());
	parameterMap.put(TRAN_LIMIT_TYPE, tranLimitType);
	BigDecimal amount = request.getAmtInLCY().getAmount().setScale(2, RoundingMode.HALF_UP);
	parameterMap.put(AMOUNT, amount);
	this.update(TXN_LIMIT_UPDATE_TOTAL_TODAY_UB, parameterMap);
    }

    @Override
    public Integer getTransactionCountForUB(UpgradeTransactionLimitServiceRequest request) {

	String tranLimitType = getTransactionLimitType(request);

	Map<String, Object> parameterMap = new HashMap<String, Object>();
	parameterMap.put(BUSINESS_ID, request.getContext().getBusinessId());
	parameterMap.put(SYSTEM_ID, request.getContext().getSystemId());
	parameterMap.put(CUSTOMER_ID, request.getContext().getCustomerId());
	// CHECK DATE needs to be passed.
	parameterMap.put(TRAN_DATE, request.getTxnDate());
	parameterMap.put(TRAN_LIMIT_TYPE, tranLimitType);
	Integer txnCount = (Integer) this.queryForObject(TXN_LIMIT_FIND_TXN_COUNT_UB, parameterMap);
	if (txnCount == null) {
	    txnCount = 0;
	}
	return txnCount;
    }

}
