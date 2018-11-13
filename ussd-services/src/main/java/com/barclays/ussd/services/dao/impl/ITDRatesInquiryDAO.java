/**
 * ITDRatesInquiryDAO.java
 */
package com.barclays.ussd.services.dao.impl;

import java.util.List;

import com.barclays.ussd.bean.TDRatesDO;

/**
 * @author BTCI
 *
 */
public interface ITDRatesInquiryDAO {
	/**
	 * @return TDRatesDO
	 */
	public List<TDRatesDO> getTDRates();

}
