package com.barclays.ussd.utils.jsonparsers.bean.regbiller;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegBillerPayData
{
	@JsonProperty
	private List<BillerDTO> billerDTOList;

	public List<BillerDTO> getBillerDTOList() {
		return billerDTOList;
	}

	public void setBillerDTOList(List<BillerDTO> billerDTOList) {
		this.billerDTOList = billerDTOList;
	}
}
