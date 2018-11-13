/**
 * TDRatesInquiryDAOImpl.java
 */
package com.barclays.ussd.services.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.barclays.ussd.bean.TDRatesDO;
import com.barclays.ussd.utils.USSDConstants;

/**
 * @author BTCI
 * 
 */
public class TDRatesInquiryDAOImpl extends SqlMapClientDaoSupport implements ITDRatesInquiryDAO {

    /*
     * (non-Javadoc)
     * 
     * @see com.barclays.ussd.services.dao.impl.ITDRatesInquiryDAO#getTDRates()
     */
    @SuppressWarnings("unchecked")
    public List<TDRatesDO> getTDRates() {
	return this.getSqlMapClientTemplate().queryForList(USSDConstants.GET_TD_RATES);
    }

}
