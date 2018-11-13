package com.barclays.bmg.sort;

import java.io.Serializable;
import java.util.Comparator;

import com.barclays.bmg.dto.CustomerAccountDTO;

public class CASAAccountDetailsComparator implements Comparator<CustomerAccountDTO>, Serializable {

    /**
	 *
	 */
    private static final long serialVersionUID = 6739083274721147150L;

    @Override
    public int compare(CustomerAccountDTO obj1, CustomerAccountDTO obj2) {

	int compareInt = 0;

	if (obj1 != null && obj2 != null) {

	    String accNowithBranchCode1 = "";
	    String accNowithBranchCode2 = "";
	    int accountNo1 = 0;
	    int accountNo2 = 0;

	    if (obj1.getBranchCode() != null) {
		accNowithBranchCode1 = obj1.getBranchCode() + "" + obj1.getAccountNumber();
	    } else {
		accNowithBranchCode1 = obj1.getAccountNumber();
	    }

	    try {
		accountNo1 = Integer.parseInt(accNowithBranchCode1);
	    } catch (Exception e) {

	    }

	    if (obj2.getBranchCode() != null) {
		accNowithBranchCode2 = obj2.getBranchCode() + "" + obj2.getAccountNumber();
	    }
	    try {
		accountNo2 = Integer.parseInt(accNowithBranchCode2);
	    } catch (Exception e) {
	    }

	    if (accountNo1 != 0 && accountNo2 != 0) {
		compareInt = (accountNo1 - accountNo2);
	    }
	}

	return compareInt;

    }

}
