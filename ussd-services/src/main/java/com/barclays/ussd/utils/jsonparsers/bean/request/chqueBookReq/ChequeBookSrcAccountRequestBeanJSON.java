package com.barclays.ussd.utils.jsonparsers.bean.request.chqueBookReq;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChequeBookSrcAccountRequestBeanJSON {

	@JsonProperty
	private String brnCde;

	@JsonProperty
	private String brnNam;
	
	@JsonProperty
	private String typ;
	
	@JsonProperty
	private String actHlds;

	/**
	 * @return the brnCde
	 */
	public String getBrnCde() {
		return brnCde;
	}

	/**
	 * @param brnCde the brnCde to set
	 */
	public void setBrnCde(String brnCde) {
		this.brnCde = brnCde;
	}

	/**
	 * @return the brnNam
	 */
	public String getBrnNam() {
		return brnNam;
	}

	/**
	 * @param brnNam the brnNam to set
	 */
	public void setBrnNam(String brnNam) {
		this.brnNam = brnNam;
	}

	/**
	 * @return the typ
	 */
	public String getTyp() {
		return typ;
	}

	/**
	 * @param typ the typ to set
	 */
	public void setTyp(String typ) {
		this.typ = typ;
	}

	/**
	 * @return the actHlds
	 */
	public String getActHlds() {
		return actHlds;
	}

	/**
	 * @param actHlds the actHlds to set
	 */
	public void setActHlds(String actHlds) {
		this.actHlds = actHlds;
	}
	
	
	
}
