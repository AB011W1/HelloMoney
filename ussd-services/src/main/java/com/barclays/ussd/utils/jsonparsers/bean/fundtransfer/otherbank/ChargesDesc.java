/**
 *
 */
package com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.otherbank;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author E20042299
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChargesDesc {

    @JsonProperty
    private String key;
    @JsonProperty
    private String desc;

    /**
     * @return the key
     */
    public String getKey() {
	return key;
    }

    /**
     * @param key
     *            the key to set
     */
    public void setKey(String key) {
	this.key = key;
    }

    /**
     * @return the desc
     */
    public String getDesc() {
	return desc;
    }

    /**
     * @param desc
     *            the desc to set
     */
    public void setDesc(String desc) {
	this.desc = desc;
    }
}
