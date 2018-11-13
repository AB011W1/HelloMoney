package com.barclays.ussd.utils;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;

public interface SystemPreferenceValidator {
    void validate(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException, USSDNonBlockingException;
}
