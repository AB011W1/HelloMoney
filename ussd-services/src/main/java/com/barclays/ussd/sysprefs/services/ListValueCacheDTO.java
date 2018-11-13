package com.barclays.ussd.sysprefs.services;




/* *************************** Revision History ***************************
 * Version        Author          Date                         Description
 * 0.1           Tony Ni        Aug 6, 2009                  Initial version
 *
 *
 **************************************************************************/

/**
 * TODO Class/Interface description.
 * @author Tony Ni
 */
public class ListValueCacheDTO extends AbstractBaseDTO {

    /** TODO Comment for <code>serialVersionUID</code>. */

    private static final long serialVersionUID = 9316619569194436L;

    private String key;

    private String label;

    private String filterKey1;

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        // return "ListValueCacheDTO[key="+key+",label="+label+"]";
        return new StringBuilder(32).append("ListValueCacheDTO[key=").append(key).append(",label=")
                .append(label).append("]").toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + ((label == null) ? 0 : label.hashCode());
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
        ListValueCacheDTO other = (ListValueCacheDTO)obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        if (label == null) {
            if (other.label != null) {
                return false;
            }
        } else if (!label.equals(other.label)) {
            return false;
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
