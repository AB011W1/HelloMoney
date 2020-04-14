/**
 * BillersLstServiceImpl.java
 */
package com.barclays.ussd.common.services;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.bean.BillersListDO;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.services.dao.impl.IBillersLstDAO;

/**
 * @author BTCI
 *
 */
public class BillersLstServiceImpl implements IBillersLstService {

    @Autowired
    private IBillersLstDAO billersLstDAOImpl;

    /*
     * (non-Javadoc)
     *
     * @see com.barclays.ussd.common.services.IBillersLstService#getBillersList(java.lang.String)
     */
    public List<BillersListDO> getBillersList(String countryCd, String mobileNumber,String businessId) throws USSDNonBlockingException {
    	return billersLstDAOImpl.getBillersList(countryCd,mobileNumber, businessId);
    }

    public IBillersLstDAO getBillersLstDAOImpl() {
    	return billersLstDAOImpl;
    }

    public void setBillersLstDAOImpl(IBillersLstDAO billersLstDAOImpl) {
    	this.billersLstDAOImpl = billersLstDAOImpl;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.barclays.ussd.common.services.IBillersLstService#getBillerName(java.lang.String, java.lang.String)
     */
    public String getBillerName(String billerId, String countryCd, String mobileNumber,String businessId) throws USSDNonBlockingException {
		String billerName = StringUtils.EMPTY;
		List<BillersListDO> lstBillers = billersLstDAOImpl.getBillersList(countryCd,mobileNumber, businessId);
		for (BillersListDO ele : lstBillers) {
		    if (billerId.equalsIgnoreCase(ele.getBillerId())) {
			billerName = ele.getBillerNm();
			break;
		    }
		}
		return billerName;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.barclays.ussd.common.services.IBillersLstService#getBillerId(int, java.lang.String)
     */
    public BillersListDO getBillerId(int index, String countryCd,String mobileNumber,String businessId) throws USSDNonBlockingException {
		List<BillersListDO> lstBillers = billersLstDAOImpl.getBillersList(countryCd,mobileNumber, businessId);
		BillersListDO biller = lstBillers.get(index - 1);
		return biller;
    }

    @Override
    public BillersListDO getBillerInfo(String countryCd, String billerId, String mobileNumber,String businessId) throws USSDNonBlockingException {
    	return billersLstDAOImpl.getBillerInfo(countryCd, billerId,mobileNumber, businessId);
    }
    
    @Override
	public List<BillersListDO> getCategoryList(String countryCd, String mobileNumber, String businessId) throws USSDNonBlockingException {
		return billersLstDAOImpl.getCategoryList(countryCd,mobileNumber, businessId);
	}
	@Override
	public List<BillersListDO> getBillerPerCategory(String mobileNumber, String businessId, String category) throws USSDNonBlockingException {
		return billersLstDAOImpl.getBillerPerCategory(mobileNumber, businessId, category);
	}

}
