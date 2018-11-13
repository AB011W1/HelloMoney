package com.barclays.bmg.helper;

import java.util.HashMap;
import java.util.Map;

import javax.resource.spi.security.PasswordCredential;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;

import org.apache.log4j.Logger;

import com.ibm.wsspi.security.auth.callback.WSMappingCallbackHandlerFactory;

public class J2CAuthenticationDataUtil {
    private static final Logger LOGGER = Logger.getLogger(J2CAuthenticationDataUtil.class);
    private Map<String, String> j2cDataMap = new HashMap<String, String>();
    private static final String IV_ALIAS = "SHMI";
    private static final String KEY_ALIAS = "SHMK";

    public void init() throws Exception {
	LOGGER.debug(":: START init()");
	// Read IV Alias
	j2cDataMap.put(IV_ALIAS, getJ2CData(IV_ALIAS));
	// Read Key Alias
	j2cDataMap.put(KEY_ALIAS, getJ2CData(KEY_ALIAS));
	LOGGER.debug(":: END init()");
    }

    public String getJ2CData(String j2cAlias) throws Exception {
	PasswordCredential passwordCredential = null;
	String user = null;
	String password = null;
	try {
	    // ----------WAS 6 change -------------
	    Map map = new HashMap();
	    WSMappingCallbackHandlerFactory factory = WSMappingCallbackHandlerFactory.getInstance();
	    map.put(com.ibm.wsspi.security.auth.callback.Constants.MAPPING_ALIAS, j2cAlias);
	    CallbackHandler cbh = factory.getCallbackHandler(map, null);
	    LoginContext lc = new LoginContext("DefaultPrincipalMapping", cbh);
	    lc.login();
	    javax.security.auth.Subject subject = lc.getSubject();
	    java.util.Set credentials = subject.getPrivateCredentials();

	    passwordCredential = (PasswordCredential) credentials.iterator().next();
	    user = passwordCredential.getUserName();
	    password = new String(passwordCredential.getPassword());

	    if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("::  Inside getJ2CData method :: " + j2cAlias + " user =" + user);
		LOGGER.debug("::  Inside getJ2CData method :: " + j2cAlias + " password =" + password);
	    }
		System.out.println("::  Inside getJ2CData method :: " + j2cAlias + " user =" + user);
		System.out.println("::  Inside getJ2CData method :: " + j2cAlias + " password =" + password);
	} catch (Exception e) {
	    LOGGER.error("Unable to get credentials for j2calias ", e);
	}
	return password;

    }

    public String getIV() {
	return j2cDataMap.get(IV_ALIAS);
    }

    public String getKEY() {
	return j2cDataMap.get(KEY_ALIAS);
    }

}
