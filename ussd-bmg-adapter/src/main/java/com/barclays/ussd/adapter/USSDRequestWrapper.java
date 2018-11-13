package com.barclays.ussd.adapter;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class USSDRequestWrapper extends HttpServletRequestWrapper implements Cloneable {
    @Override
    protected Object clone() throws CloneNotSupportedException {
	return super.clone();
    }

    private Map attributeMap = new HashMap<String, Object>();

    public USSDRequestWrapper(HttpServletRequest request) {
	super(request);
    }

    public USSDRequestWrapper(HttpServletRequest request, Map<String, String> queryParamMap) {
	super(request);
	attributeMap = queryParamMap;
    }

    @Override
    public String getParameter(String name) {
	if (super.getParameter(name) == null || super.getParameter(name).trim().length() == 0) {
	    return getUSSDAttribute(name) == null ? null : getUSSDAttribute(name).toString();
	} else {
	    return super.getParameter(name);
	}
    }

    @Override
    public Map getParameterMap() {
	Map parameterMap = new HashMap<String, Object>();
	parameterMap.putAll(attributeMap);
	parameterMap.putAll(super.getParameterMap());
	return parameterMap;
    }

    @Override
    public Enumeration getParameterNames() {

	Vector<String> parameterVector = new Vector<String>();

	Enumeration<String> attributeEnumeration = super.getAttributeNames();

	while (attributeEnumeration != null && attributeEnumeration.hasMoreElements()) {
	    parameterVector.add(attributeEnumeration.nextElement());
	}

	Enumeration<String> parameterEnumeration = super.getParameterNames();
	while (parameterEnumeration != null && parameterEnumeration.hasMoreElements()) {
	    parameterVector.add(parameterEnumeration.nextElement());
	}

	Set s = attributeMap.keySet();

	for (Iterator iterator = s.iterator(); iterator.hasNext();) {
	    String object = (String) iterator.next();
	    parameterVector.add(object);

	}
	return parameterVector.elements();
    }

    @Override
    public String[] getParameterValues(String name) {
	if (super.getParameterValues(name) == null) {
	    return getUSSDAttribute(name) == null ? null : new String[] { getUSSDAttribute(name).toString() };
	} else {
	    return super.getParameterValues(name);
	}

    }

    public Object getUSSDAttribute(String name) {
	return attributeMap.get(name);
    }

    public void setUSSDAttribute(String name, Object o) {
	attributeMap.put(name, o);
	super.setAttribute(name, o);

    }

}
