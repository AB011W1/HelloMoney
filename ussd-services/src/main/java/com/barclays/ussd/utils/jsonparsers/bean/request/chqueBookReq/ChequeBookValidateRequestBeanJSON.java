package com.barclays.ussd.utils.jsonparsers.bean.request.chqueBookReq;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;



/**
 * JSOn Resp {"payData":{"txnRefNo":"13596222882226","bkSize":"25","bkTyp":null,"brnCde":null,"brnNam":null,"srcAct":{"brnCde":null,"brnNam":null,"typ":"CASA","actHlds":"","curBal":{"amt":"791,832.98","curr":"MUR"},"avblBal":{"amt":"791,832.98","curr":"MUR"},"amtOnHld":{"amt":"","curr":"MUR"},"unclFnd":{"amt":"","curr":"MUR"},"odLmt":{"amt":"","curr":"MUR"},"minBal":{"amt":"","curr":"MUR"},"wdBal":{"amt":"","curr":"MUR"},"ibanNo":null,"desc":"PREMIER LIFE CURRENT AA","actNo":null,"curr":"MUR","mkdActNo":"xxxxxx9762"}},"payHdr":{"resMsg":"","resCde":"00000","resId":"RES0902","serVer":"2.0","expTrace":null}}
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChequeBookValidateRequestBeanJSON {

	
	@JsonProperty
	private String txnRefNo;
	
	@JsonProperty
	private String bkSize;
	
	@JsonProperty
	private String bkTyp;
	
	@JsonProperty
	private String brnCde;
	
	@JsonProperty
	private String brnNam;
	
	@JsonProperty
	private String desc;
	
	@JsonProperty
	private String actNo; 
	
	@JsonProperty
	private String curr;
	
	@JsonProperty
	private String mkdActNo;
	
	@JsonProperty
	private ChequeBookSrcAccountRequestBeanJSON srcAct;

	/**
	 * @return the txnRefNo
	 */
	public String getTxnRefNo() {
		return txnRefNo;
	}

	/**
	 * @param txnRefNo the txnRefNo to set
	 */
	public void setTxnRefNo(String txnRefNo) {
		this.txnRefNo = txnRefNo;
	}

	/**
	 * @return the bkSize
	 */
	public String getBkSize() {
		return bkSize;
	}

	/**
	 * @param bkSize the bkSize to set
	 */
	public void setBkSize(String bkSize) {
		this.bkSize = bkSize;
	}

	/**
	 * @return the bkTyp
	 */
	public String getBkTyp() {
		return bkTyp;
	}

	/**
	 * @param bkTyp the bkTyp to set
	 */
	public void setBkTyp(String bkTyp) {
		this.bkTyp = bkTyp;
	}

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
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the actNo
	 */
	public String getActNo() {
		return actNo;
	}

	/**
	 * @param actNo the actNo to set
	 */
	public void setActNo(String actNo) {
		this.actNo = actNo;
	}

	/**
	 * @return the curr
	 */
	public String getCurr() {
		return curr;
	}

	/**
	 * @param curr the curr to set
	 */
	public void setCurr(String curr) {
		this.curr = curr;
	}

	/**
	 * @return the mkdActNo
	 */
	public String getMkdActNo() {
		return mkdActNo;
	}

	/**
	 * @param mkdActNo the mkdActNo to set
	 */
	public void setMkdActNo(String mkdActNo) {
		this.mkdActNo = mkdActNo;
	}

	/**
	 * @return the srcAct
	 */
	public ChequeBookSrcAccountRequestBeanJSON getSrcAct() {
		return srcAct;
	}

	/**
	 * @param srcAct the srcAct to set
	 */
	public void setSrcAct(ChequeBookSrcAccountRequestBeanJSON srcAct) {
		this.srcAct = srcAct;
	}

	
	
	
	
}
