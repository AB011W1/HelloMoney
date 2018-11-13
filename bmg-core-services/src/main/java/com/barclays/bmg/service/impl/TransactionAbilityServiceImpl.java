package com.barclays.bmg.service.impl;

import java.sql.Timestamp;
import java.util.Calendar;

import com.barclays.bmg.dao.TransactionCutOffDAO;
import com.barclays.bmg.service.TransactionAbilityService;
import com.barclays.bmg.service.request.TransactionAbilityRequest;
import com.barclays.bmg.service.response.TransactionAbilityResponse;

/**
 * @author e20027734 check if Transaction cut off time is elapsed. It will set next business date if elapsed.
 */
public class TransactionAbilityServiceImpl implements TransactionAbilityService {

    private TransactionCutOffDAO transactionCutOffDAO;

    @Override
    public TransactionAbilityResponse checkTransactionAbility(TransactionAbilityRequest request) {

	TransactionAbilityResponse response = transactionCutOffDAO.getTransactionCutOffTime(request);

	if (response != null) {

	    Timestamp cutOffTime = response.getCutOffTime();
	    Calendar c1 = Calendar.getInstance();
	    Calendar c2 = Calendar.getInstance();
	    c2.setTimeInMillis(cutOffTime.getTime());
	    c1.set(Calendar.DATE, c2.get(Calendar.DATE));
	    c1.set(Calendar.MONTH, c2.get(Calendar.MONTH));
	    c1.set(Calendar.YEAR, c2.get(Calendar.YEAR));

	    if (c1.after(c2)) {
		Calendar nextBusinessDate = Calendar.getInstance();
		nextBusinessDate.add(Calendar.DATE, 1);
		response.setNextBusinessDate(nextBusinessDate.getTime());
		response.setTransactionable(false);
	    }
	    response.setTransactionable(true);
	} else {
	    response = new TransactionAbilityResponse();
	}
	return response;
    }

    public TransactionCutOffDAO getTransactionCutOffDAO() {
	return transactionCutOffDAO;
    }

    public void setTransactionCutOffDAO(TransactionCutOffDAO transactionCutOffDAO) {
	this.transactionCutOffDAO = transactionCutOffDAO;
    }

}
