package com.barclays.ussd.bean;

// TODO: Auto-generated Javadoc
/**
 * The Class SkipNodeDTO.
 */
public class SkipNodeDTO {
	
	/** The tran data id. */
	private String tranDataId;
	
	/** The default value. */
	private String defaultValue;

	/**
     * Gets the tran data id.
     * 
     * @return the tran data id
     */
	public String getTranDataId() {
		return tranDataId;
	}
	
	/**
     * Sets the tran data id.
     * 
     * @param tranDataId
     *            the new tran data id
     */
	public void setTranDataId(String tranDataId) {
		this.tranDataId = tranDataId;
	}
	
	/**
     * Gets the default value.
     * 
     * @return the default value
     */
	public String getDefaultValue() {
		return defaultValue;
	}
	
	/**
     * Sets the default value.
     * 
     * @param defaultValue
     *            the new default value
     */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

/*	@Override
    public boolean equals(Object obj)
	{
		SkipNodeDTO skipNode = (SkipNodeDTO) obj;
		if (skipNode.getTranDataId().equals(this.tranDataId))
		{
                return true;
        }
        return false;
    }

	 @Override

	 public int hashCode()
	 {
	        return 1;
	 }*/

}
