/**
 *
 */
package com.barclays.ussd.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;

import com.barclays.ussd.utils.jsonparsers.bean.vlpb.BillerListVO;

/**
 * @author E20040496
 *
 */
public class DateComparator implements Comparator<BillerListVO> {

	private static final String DATE_FORMATE = "dd/MM/yyyy HH:mm:ss";

	@Override
	public int compare(BillerListVO arg0, BillerListVO arg1) {
		return dateSort(arg1.getBillDate()).compareTo(dateSort(arg0.getBillDate()));

	}

	private Calendar dateSort(String billDate) {
		Calendar cal = Calendar.getInstance();
		try {
			cal.setLenient(true);
			SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMATE);
			cal.setTime(formatter.parse(billDate));
		} catch (ParseException e) {
			
		}
		return cal;
	}
}
