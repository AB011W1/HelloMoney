/**
 *
 */
package com.barclays.ussd.services.dao.impl;

import java.util.List;

import com.barclays.ussd.bean.BillersListDO;
import com.barclays.ussd.exception.USSDNonBlockingException;

/**
 * @author E20040496
 *
 */
public interface IBillersLstDAO {
    public List<BillersListDO> getBillersList(String countryCd, String mobileNumber,String businessId) throws USSDNonBlockingException;

    public BillersListDO getBillerInfo(String countryCd, String billerId, String mobileNumber,String businessId) throws USSDNonBlockingException;

}
