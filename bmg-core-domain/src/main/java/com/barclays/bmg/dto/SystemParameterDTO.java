package com.barclays.bmg.dto;

import java.util.Date;

import com.barclays.bmg.dto.BaseDomainDTO;

public class SystemParameterDTO extends BaseDomainDTO {
    /**
     *
     */
    private static final long serialVersionUID = -1407149423518589602L;

    private String systemId;

    private String businessId;

    private String parameterId;

    private String parameterValue;

    private String parameterDesc;

    private String activityId;

    private Date modifiedTime;

    private String modifyBy;

    private Date authorisedTime;

    private String authorisedBy;

    private String deleteFlag;

    private String featureSet;

    private Integer dataType;

    private String valueOptions;

    private Integer minValue;

    private Integer maxValue;

    private Integer dispOrder;

    private String paramComponentKey;

    private String freeDialParameterMTN;

	private String freeDialParameterAirtel;

    public String getFreeDialParameterMTN() {
		return freeDialParameterMTN;
	}

	public void setFreeDialParameterMTN(String freeDialParameterMTN) {
		this.freeDialParameterMTN = freeDialParameterMTN;
	}

	public String getFreeDialParameterAirtel() {
		return freeDialParameterAirtel;
	}

	public void setFreeDialParameterAirtel(String freeDialParameterAirtel) {
		this.freeDialParameterAirtel = freeDialParameterAirtel;
	}

    public String getParameterId() {
	return parameterId;
    }

    public void setParameterId(String parameterId) {
	this.parameterId = parameterId;
    }

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

    public String getParameterValue() {
	return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
	this.parameterValue = parameterValue;
    }

    public String getParameterDesc() {
	return parameterDesc;
    }

    public void setParameterDesc(String parameterDesc) {
	this.parameterDesc = parameterDesc;
    }

    public Date getModifiedTime() {
	if (modifiedTime != null) {
	    return new Date(modifiedTime.getTime());
	}
	return null;
    }

    public void setModifiedTime(Date modifiedTime) {
	if (modifiedTime != null) {
	    this.modifiedTime = new Date(modifiedTime.getTime());
	} else {
	    this.modifiedTime = null;
	}
    }

    public String getModifyBy() {
	return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
	this.modifyBy = modifyBy;
    }

    public Date getAuthorisedTime() {
	if (authorisedTime != null) {
	    return new Date(authorisedTime.getTime());
	}
	return null;
    }

    public void setAuthorisedTime(Date authorisedTime) {
	if (authorisedTime != null) {
	    this.authorisedTime = new Date(authorisedTime.getTime());
	} else {
	    this.authorisedTime = null;
	}
    }

    public String getAuthorisedBy() {
	return authorisedBy;
    }

    public void setAuthorisedBy(String authorisedBy) {
	this.authorisedBy = authorisedBy;
    }

    public String getDeleteFlag() {
	return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
	this.deleteFlag = deleteFlag;
    }

    public String getFeatureSet() {
	return featureSet;
    }

    public void setFeatureSet(String featureSet) {
	this.featureSet = featureSet;
    }

    public Integer getDataType() {
	return dataType;
    }

    public void setDataType(Integer dataType) {
	this.dataType = dataType;
    }

    public String getValueOptions() {
	return valueOptions;
    }

    public void setValueOptions(String valueOptions) {
	this.valueOptions = valueOptions;
    }

    public Integer getMinValue() {
	return minValue;
    }

    public void setMinValue(Integer minValue) {
	this.minValue = minValue;
    }

    public Integer getMaxValue() {
	return maxValue;
    }

    public void setMaxValue(Integer maxValue) {
	this.maxValue = maxValue;
    }

    public Integer getDispOrder() {
	return dispOrder;
    }

    public void setDispOrder(Integer dispOrder) {
	this.dispOrder = dispOrder;
    }

    /**
     * @param activityId
     *            the activityId to set
     */
    public void setActivityId(String activityId) {
	this.activityId = activityId;
    }

    /**
     * @return the activityId
     */
    public String getActivityId() {
	return activityId;
    }

    /**
     * @param obj
     * @return
     * @see java.lang.Object#equals(java.lang.Object)
     */

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
	SystemParameterDTO other = (SystemParameterDTO) obj;
	if (activityId == null) {
	    if (other.activityId != null) {
		return false;
	    }
	} else if (!activityId.equals(other.activityId)) {
	    return false;
	}
	if (businessId == null) {
	    if (other.businessId != null) {
		return false;
	    }
	} else if (!businessId.equals(other.businessId)) {
	    return false;
	}
	if (parameterId == null) {
	    if (other.parameterId != null) {
		return false;
	    }
	} else if (!parameterId.equals(other.parameterId)) {
	    return false;
	}
	if (systemId == null) {
	    if (other.systemId != null) {
		return false;
	    }
	} else if (!systemId.equals(other.systemId)) {
	    return false;
	}
	return true;
    }

    /**
     * @return
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((activityId == null) ? 0 : activityId.hashCode());
	result = prime * result + ((businessId == null) ? 0 : businessId.hashCode());
	result = prime * result + ((parameterId == null) ? 0 : parameterId.hashCode());
	result = prime * result + ((systemId == null) ? 0 : systemId.hashCode());
	return result;
    }

    /**
     * @param paramComponentKey
     *            the paramComponentKey to set
     */
    public void setParamComponentKey(String paramComponentKey) {
	this.paramComponentKey = paramComponentKey;
    }

    /**
     * @return the paramComponentKey
     */
    public String getParamComponentKey() {
	return paramComponentKey;
    }

}
