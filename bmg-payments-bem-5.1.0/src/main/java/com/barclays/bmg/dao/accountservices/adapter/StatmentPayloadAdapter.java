package com.barclays.bmg.dao.accountservices.adapter;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import com.barclays.bem.Branch.Branch;
import com.barclays.bem.OrderPaperStatement.OrderPaperStatement;
import com.barclays.bmg.constants.StatementRequestConstants;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.request.StatmentExecuteServiceRequest;
import com.barclays.bmg.utils.DateUtils;

public class StatmentPayloadAdapter {

	//public OrderPaperStatementRequest adaptPayLoad(WorkContext workContext){
	public OrderPaperStatement adaptPayLoad(WorkContext workContext){
		DAOContext daoContext = (DAOContext)workContext;

		Object[] args = daoContext.getArguments();

		StatmentExecuteServiceRequest request = (StatmentExecuteServiceRequest)args[0];

		OrderPaperStatement orderPaperStatement = new OrderPaperStatement();

		orderPaperStatement.setAccountNumber(request.getAccountDTO().getAccountNumber());
		Calendar calenderEndDate = DateUtils.parseStringToCalendar(request.getToDate(), StatementRequestConstants.SIMPLE_DATE_FORMAT);
		Calendar calendarstartDate = DateUtils.parseStringToCalendar(request.getFromDate(), StatementRequestConstants.SIMPLE_DATE_FORMAT);

		/*Calendar calenderEndDate = longToCal(Long.valueOf(request.getToDate()));
		Calendar calendarstartDate = longToCal(Long.valueOf(request.getFromDate()));
		*/
		Branch branch = new Branch();
        branch.setBranchCode(request.getAccountDTO().getBranchCode());
		orderPaperStatement.setBranch(branch);
		orderPaperStatement.setStatementStartDate(calendarstartDate);
		orderPaperStatement.setStatementEndDate(calenderEndDate);
		//orderPaperStatement.setReferralRepId(request.getContext().getOrgRefNo());
		orderPaperStatement.setStatementPeriodTypeCode("BK");
        return orderPaperStatement;
	}

	// convert long to calander
	private static Calendar longToCal(long longDate){
		Calendar calendar = new GregorianCalendar();
		// set lenient false
		calendar.setLenient(false);

		calendar.setTimeInMillis(longDate);

		return calendar;
	}

	private static Calendar getLastMonthFromCurrentDate() {
		Calendar aCalendar = new GregorianCalendar(TimeZone.getTimeZone("GST"));
		//aCalendar.set(Calendar.DATE, 1);
		aCalendar.set(Calendar.DATE, 1);
		aCalendar.add(Calendar.MONTH, -2);
		aCalendar.add(Calendar.DAY_OF_MONTH,5);
		return aCalendar;
	}
	private static Calendar getStatDate() {
		Calendar calendarEndDat = new GregorianCalendar(TimeZone.getTimeZone("GST"));
		calendarEndDat.setTimeInMillis(System.currentTimeMillis());
		return calendarEndDat;
	}

/*	private static Calendar setFromDate() {
		Calendar calendarDate=GregorianCalendar.getInstance();
	        calendarDate.clear();
	        calendarDate.set(2012, 12, 1);
	        return calendarDate;
	}*/
	private static Calendar setToDate() {
		Calendar calendarDate=GregorianCalendar.getInstance();
	        calendarDate.clear();
	        calendarDate.set(2013, 1, 1);
	        return calendarDate;
	}

	private Calendar dateToCalendar(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

}