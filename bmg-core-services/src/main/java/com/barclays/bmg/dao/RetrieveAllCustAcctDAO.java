/**
 *
 */
package com.barclays.bmg.dao;

import com.barclays.bmg.service.request.RetrieveAllCustAcctServiceRequest;
import com.barclays.bmg.service.response.RetrieveAllCustAcctServiceResponse;

/**
 * @author E20037686
 * 
 */
public interface RetrieveAllCustAcctDAO {
    public RetrieveAllCustAcctServiceResponse retrieveAllCustAccount(RetrieveAllCustAcctServiceRequest request);
}