/**
 * TDRatesInquiryServiceImpl.java
 */
package com.barclays.ussd.common.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.bean.TDRatesDO;
import com.barclays.ussd.services.dao.impl.ITDRatesInquiryDAO;

/**
 * @author BTCI
 *
 */
public class TDRatesInquiryServiceImpl implements ITDRatesInquiryService{

	/**
	 * tdRatestDAOImpl
	 */
	@Autowired
	private ITDRatesInquiryDAO tdRatestDAOImpl;

	public  ITDRatesInquiryDAO getTdRatestDAOImpl() {
		return tdRatestDAOImpl;
	}

	public void setTdRatestDAOImpl(ITDRatesInquiryDAO tdRatestDAOImpl) {
		this.tdRatestDAOImpl = tdRatestDAOImpl;
	}

	/* (non-Javadoc)
	 * @see com.barclays.ussd.common.services.ITDRatesInquiryService#getTDRatesData(java.lang.String)
	 */
	public List<TDRatesDO> getTDRatesData(String countryCd) {
		List<TDRatesDO> tdRatesDOTmp = tdRatestDAOImpl.getTDRates();
		List<TDRatesDO> result = new ArrayList<TDRatesDO>();
		if(tdRatesDOTmp != null && ! tdRatesDOTmp.isEmpty()){
			for(TDRatesDO ele : tdRatesDOTmp){
				if(countryCd.equalsIgnoreCase(ele.getCountryCd())){
					result.add(ele);
				}
			}
		}

		return result;
	}

}
