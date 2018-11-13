package com.barclays.bmg.sort;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Comparator;

import com.barclays.bmg.dto.AccountTrnxDTO;

public class CASATransactionHistoryComparator implements Comparator<AccountTrnxDTO>, Serializable {

    /**
	 *
	 */
    private static final long serialVersionUID = 7260114093247715450L;

    @Override
    public int compare(AccountTrnxDTO obj1, AccountTrnxDTO obj2) {

	int compareInt = 0;

	if (obj1 != null && obj2 != null) {

	    Calendar date1 = obj1.getTransactionDateTime();
	    Calendar date2 = obj2.getTransactionDateTime();

	    if (date1 != null && date2 != null) {
		compareInt = date2.compareTo(date1);

	    }
	}

	return compareInt;

    }

}
