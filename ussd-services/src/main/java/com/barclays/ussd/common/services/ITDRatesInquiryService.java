/**
 * ITDRatesInquiryService.java
 */
package com.barclays.ussd.common.services;

import java.util.List;

import com.barclays.ussd.bean.TDRatesDO;

/**
 * @author BTCI
 *
 */
public interface ITDRatesInquiryService {

	/**
	 * @param countryCd
	 * @return List<TDRatesDO>
	 */
	public List<TDRatesDO> getTDRatesData(String countryCd);

}
