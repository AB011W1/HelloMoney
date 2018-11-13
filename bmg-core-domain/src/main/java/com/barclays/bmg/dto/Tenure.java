package com.barclays.bmg.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tenure implements Serializable {

    private static final long serialVersionUID = 6759944979485518328L;

    private static final String MONTH_LABEL = "Month(s)";

    private static final String DAY_LABEL = "Day(s)";

    private static final String YEAR = "YEAR";

    private static final String MONTH = "MONTH";

    private static final String WEEK = "WEEK";

    private static final String DAY = "DAY";

    private static final String YEAR_TENURE_TYPE = "Y";

    private static final String MONTH_TENURE_TYPE = "M";

    private static final String DAY_TENURE_TYPE = "D";

    private static final String WEEK_TENURE_TYPE = "W";

    private static final int DAY_IN_YEAR = 365;

    private static final int DAY_IN_MONTH = 30;

    private static final int DAY_IN_WEEK = 7;

    private Integer year;

    private Integer month;

    private Integer day;

    private Integer week;

    private String tenureType;

    private boolean isMonthAvailable;

    private boolean isDayAvailable;

    private boolean isWeekAvailable;

    private boolean isYearAvailable;

    /*
     * Added for Support Tenure DropDown
     */

    private String displayLabel;

    private List<BigDecimal> supportedAmountRange;

    private Map<String, BigDecimal> supportedInterestRate;

    private BigDecimal slabFromAmount;

    private BigDecimal interestRate;

    /**
     * @return the slabFromAmount
     */
    public BigDecimal getSlabFromAmount() {
	return slabFromAmount;
    }

    /**
     * @param slabFromAmount
     *            the slabFromAmount to set
     */
    public void setSlabFromAmount(BigDecimal slabFromAmount) {
	this.slabFromAmount = slabFromAmount;
    }

    /**
     * @return the interestRate
     */
    public BigDecimal getInterestRate() {
	return interestRate;
    }

    /**
     * @param interestRate
     *            the interestRate to set
     */
    public void setInterestRate(BigDecimal interestRate) {
	this.interestRate = interestRate;
    }

    /**
     * @return the supportedAmountRange
     */
    public List<BigDecimal> getSupportedAmountRange() {
	if (supportedAmountRange == null) {
	    supportedAmountRange = new ArrayList<BigDecimal>();
	}
	return supportedAmountRange;
    }

    /**
     * @param supportedAmountRange
     *            the supportedAmountRange to set
     */
    public void setSupportedAmountRange(List<BigDecimal> supportedAmountRange) {
	this.supportedAmountRange = supportedAmountRange;
    }

    /**
     * @return the supportedInterestRate
     */
    public Map<String, BigDecimal> getSupportedInterestRate() {
	if (supportedInterestRate == null) {
	    supportedInterestRate = new HashMap<String, BigDecimal>();
	}
	return supportedInterestRate;
    }

    /**
     * @param supportedInterestRate
     *            the supportedInterestRate to set
     */
    public void setSupportedInterestRate(Map<String, BigDecimal> supportedInterestRate) {
	this.supportedInterestRate = supportedInterestRate;
    }

    /**
     * @return the displayLabel
     */
    public String getDisplayLabel() {
	if (this.getDay() == null || this.getDay() == 0) {

	    // return this.getMonth()+ " " + MONTH_LABEL ;
	    return new StringBuilder().append(this.getMonth()).append(" ").append(MONTH_LABEL).toString();
	} else if (this.getMonth() == null || this.getMonth() == 0) {
	    // return this.getDay()+ " " + DAY_LABEL;
	    return new StringBuilder().append(this.getDay()).append(" ").append(DAY_LABEL).toString();
	} else {
	    // return this.getDay() + " " + DAY_LABEL + " " + this.getMonth() +
	    // " " + MONTH_LABEL ;
	    return new StringBuilder().append(this.getDay()).append(" ").append(this.getMonth()).append(" ").append(MONTH_LABEL).toString();
	}
    }

    /**
     * @param displayLabel
     *            the displayLabel to set
     */
    public void setDisplayLabel(String displayLabel) {
	this.displayLabel = displayLabel;
    }

    /**
     * @return the isMonthAvailable
     */
    public boolean isMonthAvailable() {
	return isMonthAvailable;
    }

    /**
     * @param isMonthAvailable
     *            the isMonthAvailable to set
     */
    public void setMonthAvailable(boolean isMonthAvailable) {
	this.isMonthAvailable = isMonthAvailable;
    }

    /**
     * @return the isDayAvailable
     */
    public boolean isDayAvailable() {
	return isDayAvailable;
    }

    /**
     * @param isDayAvailable
     *            the isDayAvailable to set
     */
    public void setDayAvailable(boolean isDayAvailable) {
	this.isDayAvailable = isDayAvailable;
    }

    /**
     * @return the isWeekAvailable
     */
    public boolean isWeekAvailable() {
	return isWeekAvailable;
    }

    /**
     * @param isWeekAvailable
     *            the isWeekAvailable to set
     */
    public void setWeekAvailable(boolean isWeekAvailable) {
	this.isWeekAvailable = isWeekAvailable;
    }

    /**
     * @return the isYearAvailable
     */
    public boolean isYearAvailable() {
	return isYearAvailable;
    }

    /**
     * @param isYearAvailable
     *            the isYearAvailable to set
     */
    public void setYearAvailable(boolean isYearAvailable) {
	this.isYearAvailable = isYearAvailable;
    }

    /**
     * @return year
     */
    public Integer getYear() {
	return year;
    }

    /**
     * @param year
     *            Integer
     */
    public void setYear(Integer year) {
	this.year = year;
    }

    /**
     * @return month
     */
    public Integer getMonth() {
	return month;
    }

    /**
     * @param month
     *            Integer
     */
    public void setMonth(Integer month) {
	this.month = month;
    }

    /**
     * @return day
     */
    public Integer getDay() {
	return day;
    }

    /**
     * @param day
     *            Integer
     */
    public void setDay(Integer day) {
	this.day = day;
    }

    /**
     * @return the week
     */
    public Integer getWeek() {
	return week;
    }

    /**
     * @param week
     *            the week to set
     */
    public void setWeek(Integer week) {
	this.week = week;
    }

    public String getTenureValue() {
	String returnValue = null;
	if (day != null && !day.equals(0)) {
	    returnValue = String.valueOf(day);
	} else if (month != null && !month.equals(0)) {
	    returnValue = String.valueOf(month);
	} else if (week != null && !month.equals(0)) {
	    returnValue = String.valueOf(week);
	} else if (year != null && !year.equals(0)) {
	    returnValue = String.valueOf(year);
	}
	return returnValue;
    }

    public String getTenureValueByMonth() {
	String returnValue = "0";
	if (day != null) {
	    returnValue = "0";
	} else if (month != null) {
	    returnValue = String.valueOf(month);
	} else if (week != null) {
	    returnValue = "0";
	} else if (year != null) {
	    returnValue = String.valueOf(year * 12);
	}
	return returnValue;
    }

    /**
     * @return the tenureType
     */
    public String getTenureType() {
	if (day != null && !day.equals(0)) {
	    this.tenureType = DAY;
	} else if (month != null && !month.equals(0)) {
	    this.tenureType = MONTH;
	} else if (week != null && !week.equals(0)) {
	    this.tenureType = WEEK;
	} else if (year != null && !year.equals(0)) {
	    this.tenureType = YEAR;
	}
	return tenureType;
    }

    /**
     * @param tenureType
     *            the tenureType to set
     */
    public void setTenureType(String tenureType) {
	this.tenureType = tenureType;
    }

    public String getTenureInMonth() {
	Integer retV = 0;
	if (day != null && day > 0) {
	    retV = 1;
	}
	if (month != null) {
	    retV = retV + month;
	}
	return String.valueOf(retV);
    }

    public String getTenureInDay() {
	Integer totalTenureDay = 0;
	if (day != null && day > 0) {
	    totalTenureDay = totalTenureDay + day;
	}
	if (week != null && week > 0) {
	    totalTenureDay = totalTenureDay + week * DAY_IN_WEEK;
	}

	if (month != null && month > 0) {
	    totalTenureDay = totalTenureDay + month * DAY_IN_MONTH;
	}
	if (year != null && year > 0) {
	    totalTenureDay = totalTenureDay + year * DAY_IN_YEAR;
	}

	return String.valueOf(totalTenureDay);
    }

}
