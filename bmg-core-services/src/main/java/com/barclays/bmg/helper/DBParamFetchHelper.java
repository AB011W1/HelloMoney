package com.barclays.bmg.helper;

import com.barclays.bmg.context.BMGContextHolder;

public class DBParamFetchHelper {

    public static String getSysParamValue(String key) {
	return String.valueOf(BMGContextHolder.getLogContext().getContextMap().get(key));
    }
}
