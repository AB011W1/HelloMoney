/**
 * IBillersLstService.java
 */
package com.barclays.ussd.common.services;

import java.util.List;

import com.barclays.ussd.bean.BillersListDO;
import com.barclays.ussd.exception.USSDNonBlockingException;

/**
 * @author BTCI
 *
 */
public interface IBillersLstService {
    /**
     * @param countryCd
     * @return List<BillersListDO>
     */
    public List<BillersListDO> getBillersList(String countryCd, String mobileNumber,String businessId) throws USSDNonBlockingException;

    /**
     * @param billerId
     * @param countryCd
     * @return String
     */
    public String getBillerName(String billerId, String countryCd, String mobileNumber,String businessId) throws USSDNonBlockingException;

    /**
     * @param index
     * @param countryCd
     * @return String
     */
    public BillersListDO getBillerId(int index, String countryCd,String mobileNumber,String businessId) throws USSDNonBlockingException;

    public BillersListDO getBillerInfo(String countryCd, String billerId, String mobileNumber,String businessId) throws USSDNonBlockingException;

}
