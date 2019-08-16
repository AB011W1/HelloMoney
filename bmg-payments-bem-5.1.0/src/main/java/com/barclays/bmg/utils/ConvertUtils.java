package com.barclays.bmg.utils;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.barclays.bem.JointAccountHoldersRelationship.JointAccountHoldersRelationship;
import com.barclays.bem.Product.Product;
import com.barclays.bem.TenorPeriod.TenorPeriod;
import com.barclays.bem.TransactionHistory.TransactionHistory;
import com.barclays.bmg.type.RateAmount;
import com.barclays.bmg.type.Tenure;

public class ConvertUtils {

    private static final String EMPTY_YEAR = "01/01/0001";
    private static final String EMPTY_YEAR1 = "01/01/1950";
    private static final String EMPTY_YEAR2 = "01/01/1800";

    public static Double convertDouble(BigDecimal sourceAmount) {
	if (sourceAmount != null) {
	    return sourceAmount.doubleValue();
	} else {
	    return null;
	}
    }

    public static BigDecimal convertAmount(Double sourceAmount) {
	if (sourceAmount != null) {
	    return new BigDecimal(sourceAmount);
	} else {
	    return new BigDecimal(0);
	}
    }

    public static BigDecimal convertInterestRateAmount(Double sourceAmount) {
	if (sourceAmount != null) {
	    // sourceAmount = sourceAmount/100;
	    BigDecimal b = BigDecimal.valueOf(sourceAmount);
	    if (b.scale() > 2) {
		BigDecimal interestRate = BigDecimal.valueOf(sourceAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
		return interestRate;
	    }
	    return b;
	} else {
	    return new BigDecimal(0);
	}
    }

    public static BigDecimal convertNAVAmount(Double sourceAmount) {
	if (sourceAmount != null) {
	    // sourceAmount = sourceAmount/100;
	    BigDecimal b = BigDecimal.valueOf(sourceAmount);
	    if (b.scale() > 3) {
		BigDecimal interestRate = BigDecimal.valueOf(sourceAmount).setScale(3, BigDecimal.ROUND_HALF_UP);
		return interestRate;
	    }
	    return b;
	} else {
	    return new BigDecimal(0);
	}
    }

    public static String convertNAVAmountInString(Double sourceAmount) {
	String srcAmnt = "";

	if (sourceAmount != null) {

	    srcAmnt = (BigDecimal.valueOf(sourceAmount).setScale(4, BigDecimal.ROUND_HALF_UP)).toString();

	}
	return srcAmnt;
    }

    public static BigDecimal convertActivityAmount(Double sourceAmount) {
	if (sourceAmount != null) {
	    return new BigDecimal(sourceAmount);
	}
	return null;
    }

    public static BigDecimal convertPositiveAmount(Double sourceAmount) {
	if (sourceAmount != null) {
	    return new BigDecimal(sourceAmount).abs();
	} else {
	    return new BigDecimal(0);
	}
    }

    public static BigDecimal convertIntegerAmount(Integer sourceAmount) {
	if (sourceAmount != null) {
	    return new BigDecimal(sourceAmount);
	} else {
	    return new BigDecimal(0);
	}
    }

    // public static boolean checkAccountStatus(String accountStatus) {
    // if (accountStatus != null) {
    // return false;
    // }
    // if(CustomerAccountDTO.ACTIVE.equals(accountStatus) ||
    // CustomerAccountDTO.DORMANT.equals(accountStatus)){
    // return true;
    // }
    // return false;
    // }
    public static Date convertDate(Calendar calendar) {
	if (calendar == null) {
	    return null;
	} else {
	    String timeString = DateTimeUtil.getStringFromDate(calendar.getTime(), "dd/MM/yyyy");
	    if (timeString.equals(EMPTY_YEAR)) {
		return null;
	    }
	    if (timeString.equals(EMPTY_YEAR1)) {
		return null;
	    }
	    if (timeString.equals(EMPTY_YEAR2)) {
		return null;
	    }
	}

	return calendar.getTime();
    }

    public static String getTrxDesc(TransactionHistory trxHistory) {
	StringBuffer returnStr = new StringBuffer();
	if (trxHistory != null) {
	    // Modified by Tony, add blank after the description
	    if (StringUtils.isNotEmpty(trxHistory.getStatementTxnDesc1())) {
		returnStr.append(trxHistory.getStatementTxnDesc1()).append(" ");
	    }
	    if (StringUtils.isNotEmpty(trxHistory.getStatementTxnDesc2())) {
		returnStr.append(trxHistory.getStatementTxnDesc2()).append(" ");
	    }
	    if (StringUtils.isNotEmpty(trxHistory.getStatementTxnDesc3())) {
		returnStr.append(trxHistory.getStatementTxnDesc3()).append(" ");
	    }
	    if (StringUtils.isNotEmpty(trxHistory.getStatementTxnDesc4())) {
		returnStr.append(trxHistory.getStatementTxnDesc4()).append(" ");
	    }
	    if (StringUtils.isNotEmpty(trxHistory.getStatementTxnDesc5())) {
		returnStr.append(trxHistory.getStatementTxnDesc5());
	    }
	    // Modified End
	}
	return returnStr.toString();
    }

    public static String getRelShipTpeCode(JointAccountHoldersRelationship[] relationShipArray) {
	StringBuffer resShipTypeCode = new StringBuffer();
	if (relationShipArray != null) {
	    for (int i = 0; i < relationShipArray.length; i++) {
		resShipTypeCode.append(relationShipArray[i].getRelationshipTypeCode());
		if (i < (relationShipArray.length - 1)) {
		    resShipTypeCode.append(",");
		}
	    }
	}
	return resShipTypeCode.toString();

    }

    public static Tenure convertTenure(TenorPeriod tenor) {
	Tenure tenure = new Tenure();
	if (tenor != null) {
	    tenure.setDay(tenor.getDays());
	    tenure.setMonth(tenor.getMonths());
	    tenure.setYear(tenor.getYears());
	    tenure.setWeek(tenor.getWeeks());

	}
	return tenure;
    }

    public static Calendar getCalendarByDate(Date date) {
	Calendar cal = Calendar.getInstance();
	if (date != null) {
	    cal.setTime(date);
	    return cal;
	}
	return null;

    }

    public static RateAmount getRateAmount(Double rate) {
	if (rate != null) {
	    return new RateAmount(BigDecimal.valueOf(rate));
	} else {
	    return new RateAmount(BigDecimal.valueOf(0));
	}
    }

    public static String getProductCode(Product product) {
	if (product != null) {
	    return product.getProductCode();
	}

	return null;
    }
}
