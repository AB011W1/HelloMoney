package com.barclays.bmg.dto;

import com.barclays.bmg.dto.BaseDomainDTO;

public class ScriptResDTO extends BaseDomainDTO {

    /**
	 *
	 */
    private static final long serialVersionUID = 6815120340495964448L;

    private String languageId;

    private String scriptValue;

    private Long seqNo;

    public String getLanguageId() {
	return languageId;
    }

    public void setLanguageId(String languageId) {
	this.languageId = languageId;
    }

    public String getScriptValue() {
	return scriptValue;
    }

    public void setScriptValue(String scriptValue) {
	this.scriptValue = scriptValue;
    }

    public Long getSeqNo() {
	return seqNo;
    }

    public void setSeqNo(Long seqNo) {
	this.seqNo = seqNo;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((languageId == null) ? 0 : languageId.hashCode());

	result = prime * result + ((seqNo == null) ? 0 : seqNo.hashCode());
	return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	ScriptResDTO other = (ScriptResDTO) obj;
	if (languageId == null) {
	    if (other.languageId != null)
		return false;
	} else if (!languageId.equals(other.languageId))
	    return false;

	if (seqNo == null) {
	    if (other.seqNo != null)
		return false;
	} else if (!seqNo.equals(other.seqNo))
	    return false;
	return true;
    }

}
