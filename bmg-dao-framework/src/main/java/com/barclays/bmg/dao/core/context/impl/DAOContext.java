package com.barclays.bmg.dao.core.context.impl;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import com.barclays.bmg.dao.core.context.WorkContext;

public class DAOContext implements WorkContext {

	private ApplicationContext applicationContext;

	private String methodName;

	private Object[] arguments;

	public void setApplicationContext(ApplicationContext applicationcontext)
			throws BeansException {
		this.applicationContext = applicationcontext;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public Object getBean(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getBeanFromApplicationContext(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setIntoDAOContext(Object key, Object value) {
		// TODO Auto-generated method stub

	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Object[] getArguments() {
		return arguments.clone();
	}

	public void setArguments(Object[] arguments) {
		this.arguments = arguments.clone();
	}



}
