package com.barclays.bmg.dao.core.context;

public interface WorkContext {

	void setIntoDAOContext(Object key, Object value);

	Object getBean(Object key);

	Object getBeanFromApplicationContext(Object key);
}
