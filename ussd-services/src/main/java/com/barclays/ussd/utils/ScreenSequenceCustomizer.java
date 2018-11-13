package com.barclays.ussd.utils;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.exception.USSDBlockingException;

public interface ScreenSequenceCustomizer {
    int getCustomNextScreen(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException;
}
