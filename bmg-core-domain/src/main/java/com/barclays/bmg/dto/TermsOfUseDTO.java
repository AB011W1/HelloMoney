package com.barclays.bmg.dto;

public class TermsOfUseDTO extends BaseDomainDTO {
    /**
     *
     */
    private static final long serialVersionUID = -1407149423518589602L;

    private String systemId;

    private String businessId;

    private String customerId;

    private String acceptFlag;

    private String termsOfUseVersion;

    public String getSystemId() {
	return systemId;
    }

    public void setSystemId(String systemId) {
	this.systemId = systemId;
    }

    public String getBusinessId() {
	return businessId;
    }

    public void setBusinessId(String businessId) {
	this.businessId = businessId;
    }

    public String getCustomerId() {
	return customerId;
    }

    public void setCustomerId(String customerId) {
	this.customerId = customerId;
    }

    public String getAcceptFlag() {
	return acceptFlag;
    }

    public void setAcceptFlag(String acceptFlag) {
	this.acceptFlag = acceptFlag;
    }

    public String getTermsOfUseVersion() {
	return termsOfUseVersion;
    }

    public void setTermsOfUseVersion(String termsOfUseVersion) {
	this.termsOfUseVersion = termsOfUseVersion;
    }

    /*
     * public boolean equals(Object obj) { if (this == obj) { return true; } if (obj == null) { return false; } if (getClass() != obj.getClass()) {
     * return false; } SystemParameterDTO other = (SystemParameterDTO)obj; if (activityId == null) { if (other.activityId != null) { return false; } }
     * else if (!activityId.equals(other.activityId)) { return false; } if (businessId == null) { if (other.businessId != null) { return false; } }
     * else if (!businessId.equals(other.businessId)) { return false; } if (parameterId == null) { if (other.parameterId != null) { return false; } }
     * else if (!parameterId.equals(other.parameterId)) { return false; } if (systemId == null) { if (other.systemId != null) { return false; } } else
     * if (!systemId.equals(other.systemId)) { return false; } return true; }
     *//**
     * @return
     * @see java.lang.Object#hashCode()
     */
    /*
     * public int hashCode() { final int prime = 31; int result = 1; result = prime result + ((activityId == null) ? 0 : activityId.hashCode());
     * result = prime result + ((businessId == null) ? 0 : businessId.hashCode()); result = prime result + ((parameterId == null) ? 0 :
     * parameterId.hashCode()); result = prime result + ((systemId == null) ? 0 : systemId.hashCode()); return result; }
     *//**
     * @param paramComponentKey
     *            the paramComponentKey to set
     */
    /*
     * public void setParamComponentKey(String paramComponentKey) { this.paramComponentKey = paramComponentKey; }
     *//**
     * @return the paramComponentKey
     */
    /*
     * public String getParamComponentKey() { return paramComponentKey; }
     */

}
