package com.barclays.bmg.sort;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

import com.barclays.bmg.dto.AccountActivityDTO;

public class CASATransactionActivityComparator implements Comparator<AccountActivityDTO>, Serializable {

    /**
	 *
	 */
    private static final long serialVersionUID = 7260114093247715450L;

    @Override
    public int compare(AccountActivityDTO obj1, AccountActivityDTO obj2) {

	int compareInt = 0;

	if (obj1 != null && obj2 != null) {

	    Date date1 = obj1.getTransactionDate();
	    Date date2 = obj2.getTransactionDate();

	    if (date1 != null && date2 != null) {
		compareInt = date2.compareTo(date1);

	    }
	}

	return compareInt;

    }

}
