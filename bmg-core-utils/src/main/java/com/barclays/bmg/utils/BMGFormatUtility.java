package com.barclays.bmg.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.barclays.bmg.json.model.AmountJSONModel;

public class BMGFormatUtility {

	private static final String DEFAULT_LONG_DATE = "dd/MM/yyyy HH:mm:ss";
	private static final String DEFAULT_SHORT_DATE = "dd/MM/yyyy";
	private static final String DEFAULT_NUMBER_FORMAT_PATTERN = "#,##0.00";
	private static final String NUMBER_FORMAT_PATTERN_4DECIMAL = "#,##0.0000";

	public static final String CASA_ACTIVITY_SEARCH_CURRENT_PERIOD_DATERANGE = "CASA_activitySearchCurrentPeriodDateRange";
	public static final String CASA_ACTIVITY_SEARCH_MAX_SELECT_DATERANGE = "CASA_activitySearchMaxSelectDateRange";
	public static final String CASA_DETAIL_ACTIVITY_ID = "ACT_ACCOUNT_DETAIL_CASA_DETAIL";
	public static final int DEFAULT_CURRENT_PERIOD_DATERAMGE = 1;

	public static final String getFormattedAmount(BigDecimal amount) {
		if (amount == null) {
			return "";
		}

		String numberFormatPattern = DEFAULT_NUMBER_FORMAT_PATTERN;

		String result = null;

		// replace format with , and .
		char thousandChar = numberFormatPattern.charAt("#".length());
		char pointChar = numberFormatPattern.charAt("#,##0".length());

		String header = numberFormatPattern.substring(0, 2);
		String tail = numberFormatPattern.substring(2);

		header = header.replace(thousandChar, ',');
		tail = tail.replace(pointChar, '.');

		String realFormat = header + tail;

		DecimalFormat df = new DecimalFormat(realFormat);
		result = df.format(amount.doubleValue());

		tail = result.substring(result.indexOf('.') + 1);
		header = result.substring(0, result.length() - tail.length() - 1);

		result = header.replace(',', thousandChar) + pointChar + tail;

		return result;

	}

	public static final String getFormattedAmount4Decimal(BigDecimal amount) {
		if (amount == null) {
			return "";
		}

		String numberFormatPattern = NUMBER_FORMAT_PATTERN_4DECIMAL;

		String result = null;

		// replace format with , and .
		char thousandChar = numberFormatPattern.charAt("#".length());
		char pointChar = numberFormatPattern.charAt("#,##0".length());

		String header = numberFormatPattern.substring(0, 2);
		String tail = numberFormatPattern.substring(2);

		header = header.replace(thousandChar, ',');
		tail = tail.replace(pointChar, '.');

		String realFormat = header + tail;

		DecimalFormat df = new DecimalFormat(realFormat);
		result = df.format(amount.doubleValue());

		tail = result.substring(result.indexOf('.') + 1);
		header = result.substring(0, result.length() - tail.length() - 1);

		result = header.replace(',', thousandChar) + pointChar + tail;

		return result;

	}

	public static final BigDecimal getFormattedAmountAsBigDecimal(
			BigDecimal amount) {
		if (amount == null) {
			return amount;
		}

		String numberFormatPattern = DEFAULT_NUMBER_FORMAT_PATTERN;

		String result = null;

		// replace format with , and .
		char thousandChar = numberFormatPattern.charAt("#".length());
		char pointChar = numberFormatPattern.charAt("#,##0".length());

		String header = numberFormatPattern.substring(0, 2);
		String tail = numberFormatPattern.substring(2);

		header = header.replace(thousandChar, ',');
		tail = tail.replace(pointChar, '.');

		String realFormat = header + tail;

		DecimalFormat df = new DecimalFormat(realFormat);
		result = df.format(amount.doubleValue());

		return new BigDecimal(result);

	}

	public static final String getShortDate(Date date) {
		if (date == null) {

			return null;
		}

		SimpleDateFormat smf = new SimpleDateFormat(DEFAULT_SHORT_DATE);

		return smf.format(date);

	}

	public static String getLongDate(Date date) {
		if (date == null) {

			return null;
		}

		SimpleDateFormat smf = new SimpleDateFormat(DEFAULT_LONG_DATE);

		return smf.format(date);

	}

	public static AmountJSONModel getJSONAmount(String curr, String amt) {

		AmountJSONModel jsonAmt = new AmountJSONModel();

		jsonAmt.setAmt(amt);
		jsonAmt.setCurr(curr);

		return jsonAmt;
	}

	public static String removeSpaceInBetween(String str) {
		String updatedstr = str;
		if (!StringUtils.isEmpty(updatedstr)) {
			updatedstr = updatedstr.replaceAll("\\s+", " ").trim();
		} else {
			updatedstr = "";
		}
		return updatedstr;
	}
}
