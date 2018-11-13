/**
 *
 */
package com.barclays.bmg.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;



public class DateUtils {


	/** Get the yesterday Date */
	public static Calendar getPreviousDate() {

		Calendar calendar = new GregorianCalendar();

		calendar.setLenient(false);

		//calendar.setTimeInMillis(currntDateLong);

		calendar.add(Calendar.DATE, -1);

		return calendar;
	}

	/** convert long to calendar */
	public static Calendar longToCal(long longDate){
		Calendar calendar = new GregorianCalendar();
		// set lenient false
		calendar.setLenient(false);

		calendar.setTimeInMillis(longDate);

		return calendar;
	}


	/** Get the Last month from the current date which start from the 1st. */
	public static Calendar getLastMonth() {
		Calendar calendar = new GregorianCalendar();
		// set lenient false
		calendar.setLenient(false);
		// add -1 month to current month
		calendar.add(Calendar.MONTH, -1);
		// set DATE to 1, so first date of previous month
		calendar.set(Calendar.DATE, 1);

		return calendar;
	}


	/**
	 *  Check fromDate and ToDate are not same date
	 * @param fromDateLong
	 * @param toDateLong
	 * @return
	 */
	public static boolean isSameDate(Long fromDateLong, Long toDateLong) {

		Calendar fromCal = longToCal(fromDateLong);

		Calendar toCal = longToCal(toDateLong);

		return org.apache.commons.lang.time.DateUtils.isSameDay(fromCal, toCal);

	}


	public static boolean isFutureDate (Long fromDateLong, Long toDateLong) {

		Calendar fromCal = longToCal(fromDateLong);

		Calendar toCal = longToCal(toDateLong);

		return org.apache.commons.lang.time.DateUtils.isSameDay(fromCal, toCal);

	}


	/**
	 * Parse the string Date format to Calendar.
	 * @param strDate
	 * @param dateFormate
	 * @return
	 */
	public static Calendar parseStringToCalendar(String strDate,String dateFormate) {
		Calendar calendar = null;
		try {
			SimpleDateFormat dateFormat = new  SimpleDateFormat(dateFormate);
			Date date  = dateFormat.parse(strDate);
			calendar = new GregorianCalendar();
			calendar.setTime(date);
			return calendar;
		} catch (ParseException e) {
			
		}
		return calendar;
	}


}
