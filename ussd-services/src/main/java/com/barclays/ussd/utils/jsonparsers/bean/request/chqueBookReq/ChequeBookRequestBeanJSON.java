package com.barclays.ussd.utils.jsonparsers.bean.request.chqueBookReq;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChequeBookRequestBeanJSON {

	@JsonProperty
	private List<AccountChequBookDetails> srcLst;

	@JsonProperty
	private List<ChequeBookSizeList> bkSizeLst;

	@JsonProperty
	private List<ChequeBookTypeList> bkTypLst;





	public List<AccountChequBookDetails> getSrcLst() {
		return srcLst;
	}


	public void setSrcLst(List<AccountChequBookDetails> srcLst) {
		this.srcLst = srcLst;
	}


	public List<ChequeBookSizeList> getBkSizeLst() {
		return bkSizeLst;
	}


	public void setBkSizeLst(List<ChequeBookSizeList> bkSizeLst) {
		this.bkSizeLst = bkSizeLst;
	}



	/**
	 * @return the bkTypLst
	 */
	public List<ChequeBookTypeList> getBkTypLst() {
		return bkTypLst;
	}


	/**
	 * @param bkTypLst the bkTypLst to set
	 */
	public void setBkTypLst(List<ChequeBookTypeList> bkTypLst) {
		this.bkTypLst = bkTypLst;
	}


}
