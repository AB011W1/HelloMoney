package com.barclays.ussd.sysprefs.services;

public class ListValueResDTO extends AbstractBaseDTO {

    /**
     *
     */
    private static final long serialVersionUID = 2212819827890050297L;

    private Long listId;
    private String languageKey;
    private String listValue;
    private Long listOrder;
    private String listKey;
    private String filterKey1;

    public Long getListId() {
	return listId;
    }

    public void setListId(Long listId) {
	this.listId = listId;
    }

    public String getLanguageKey() {
	return languageKey;
    }

    public void setLanguageKey(String languageKey) {
	this.languageKey = languageKey;
    }

    public String getListValue() {
	return listValue;
    }

    public void setListValue(String listValue) {
	this.listValue = listValue;
    }

    public Long getListOrder() {
	return listOrder;
    }

    public void setListOrder(Long listOrder) {
	this.listOrder = listOrder;
    }

    public void setListKey(String listKey) {
	this.listKey = listKey;
    }

    public String getListKey() {
	return listKey;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((languageKey == null) ? 0 : languageKey.hashCode());
	result = prime * result + ((listId == null) ? 0 : listId.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	ListValueResDTO other = (ListValueResDTO) obj;
	if (languageKey == null) {
	    if (other.languageKey != null) {
		return false;
	    }

	} else {
	    if (!languageKey.equals(other.languageKey)) {
		return false;
	    }
	}
	if (listId == null) {
	    if (other.listId != null) {
		return false;
	    }
	} else {
	    if (!listId.equals(other.listId)) {
		return false;
	    }
	}
	return true;
    }

    public String getFilterKey1() {
	return filterKey1;
    }

    public void setFilterKey1(String filterKey1) {
	this.filterKey1 = filterKey1;
    }

}
