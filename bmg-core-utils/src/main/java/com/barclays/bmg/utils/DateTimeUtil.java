/*
 * Copyright (c) 2009 Barclays Bank Plc, All Rights Reserved.
 *
 * This code is confidential to Barclays Bank Plc and shall not be disclosed
 * outside the Bank without the prior written permission of the Director of
 * CIO
 *
 * In the event that such disclosure is permitted the code shall not be copied
 * or distributed other than on a need-to-know basis and any recipients may be
 * required to sign a confidentiality undertaking in favour of Barclays Bank
 * Plc.
 */

package com.barclays.bmg.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.barclays.bmg.context.Context;

/* *************************** Revision History *********************************
 * Version        Author          Date                            Description
 * 0.1            Elicer Zheng        Aug 26, 2009                  Initial version
 *
 *
 ********************************************************************************/

/**
 * TODO Class/Interface description.
 * @author Elicer Zheng
 */
public class DateTimeUtil {
    /**
     * Default private Constructor.
     */
    private static final int TIME_DIFFERENCE = -30;

    //private static final String SV_DATE_FORMAT_EXCEPTION = "SV00016";

    private DateTimeUtil() {

    }

    /**
     * This method was used to get the Date of One month before.
     * @return Date
     */
    public static Date getOneMonthBefore() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, TIME_DIFFERENCE);
        return cal.getTime();

    }

    /**
     * Get n month before or later.
     * @param date Date
     * @param monthNumber int
     * @return Date
     */
    public static Date getDateWithMonthDifference(Date date, int monthNumber) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, monthNumber);
        return cal.getTime();
    }

    /**
     * Get n days before or later.
     * @param date Date
     * @param dayNumber int
     * @return Date
     */
    public static Date getDateWithDaysDifference(Date date, int dayNumber) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, dayNumber);
        return cal.getTime();
    }

    /**
     * Get n days and n month before or later.
     * @param date Date
     * @param dayNumber int
     * @param monthNumber int
     * @return Date
     */
    public static Date getDateWithDaysAndMonthDifference(Date date, int dayNumber, int monthNumber)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, dayNumber);
        cal.add(Calendar.MONTH, monthNumber);
        return cal.getTime();
    }

    /**
     * @param dateStr String
     * @param stringType String(dd/MM/yyyy) (dd-MM-yyy)
     * @return date Date
     */
    public static Date getDateFromStr(final String dateStr, final String stringType) {
        Date date = null;
        try {
            DateFormat formatter;
            formatter = new SimpleDateFormat(stringType);
            date = formatter.parse(dateStr);

        } catch (ParseException e) {
//            throw new CCSSystemException(SV_DATE_FORMAT_EXCEPTION,
//                    "Date format parse exception!", e);
        	
        }

        return date;

    }

    /**
     * @param date Date
     * @param stringType String (dd/MM/yyyy) (dd-MM-yyy)
     * @return dateStr String
     */
    public static String getStringFromDate(final Date date, final String stringType) {
        String dateStr;
        DateFormat formatter = new SimpleDateFormat(stringType);
        dateStr = formatter.format(date);
        return dateStr;

    }

    public static Calendar getCurrentBusinessCalendar(Context context)
    {
		Calendar cal = Calendar.getInstance();
		if(context.getTimezoneOffset()!=null){
			int offset = cal.get(Calendar.ZONE_OFFSET) / 3600000 + cal.get(Calendar.DST_OFFSET) / 3600000;
			Double bos = Double.valueOf(Double.valueOf(context.getTimezoneOffset()) * 60);
			cal.add(Calendar.MINUTE, (-offset * 60 + bos.intValue()));
		}
		return cal;
	}
    public static XMLGregorianCalendar getXMLGregorianCalendar(Context context)
    {
    	XMLGregorianCalendar xgc = null;
    	try
    	{
    		Calendar cal = getCurrentBusinessCalendar(context);
	    	DatatypeFactory dtf = DatatypeFactory.newInstance();
			xgc = dtf.newXMLGregorianCalendar();
			xgc.setYear(cal.get(Calendar.YEAR));
			xgc.setDay(cal.get(Calendar.DAY_OF_MONTH));
			xgc.setMonth(cal.get(Calendar.MONTH)+ 1);
			xgc.setHour(cal.get(Calendar.HOUR_OF_DAY));
			xgc.setMinute(cal.get(Calendar.MINUTE));
			xgc.setSecond(cal.get(Calendar.SECOND));
			xgc.setMillisecond(cal.get(Calendar.MILLISECOND));
    	}
		catch(Exception e)
		{}
		return xgc;
    }
    
	public static String getDayMonthYearFromDate(final Date date, final String param) {
		String dateStr;
		DateFormat formatter = new SimpleDateFormat("dd-MM-yy");
		dateStr = formatter.format(date);
		String[] dateParam = dateStr.split("-");
		if ("DD".equalsIgnoreCase(param)) {
			return dateParam[0];
		} else if ("MM".equalsIgnoreCase(param)) {
			return dateParam[1];
		} else if ("YY".equalsIgnoreCase(param)) {
			return dateParam[2];
		}
		return dateStr;

	}
}
