package com.barclays.ussd.utils.jsonparsers.bean.fdrates;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FDApplySourceAccount {

    @JsonProperty
    private String actHlds;

    @JsonProperty
    private Amount curBal;

    @JsonProperty
    private Amount avblBal;

    @JsonProperty
    private Amount amtOnHld;

    @JsonProperty
    private Amount unclFnd;

    @JsonProperty
    private Amount odLmt;

    @JsonProperty
    private Amount minBal;

    @JsonProperty
    private Amount wdBal;

    @JsonProperty
    private String ibanNo;

    @JsonProperty
    private String brnCde;

    @JsonProperty
    private String brnNam;

    @JsonProperty
    private String typ;

    @JsonProperty
    private String mkdActNo;

    @JsonProperty
    private String actNo;

    @JsonProperty
    private String desc;

    @JsonProperty
    private String curr;

    @JsonProperty
    private String priInd;

    /**
     * @return the actHlds
     */
    public String getActHlds() {
	return actHlds;
    }

    /**
     * @param actHlds
     *            the actHlds to set
     */
    public void setActHlds(String actHlds) {
	this.actHlds = actHlds;
    }

    /**
     * @return the curBal
     */
    public Amount getCurBal() {
	return curBal;
    }

    /**
     * @param curBal
     *            the curBal to set
     */
    public void setCurBal(Amount curBal) {
	this.curBal = curBal;
    }

    /**
     * @return the avblBal
     */
    public Amount getAvblBal() {
	return avblBal;
    }

    /**
     * @param avblBal
     *            the avblBal to set
     */
    public void setAvblBal(Amount avblBal) {
	this.avblBal = avblBal;
    }

    /**
     * @return the amtOnHld
     */
    public Amount getAmtOnHld() {
	return amtOnHld;
    }

    /**
     * @param amtOnHld
     *            the amtOnHld to set
     */
    public void setAmtOnHld(Amount amtOnHld) {
	this.amtOnHld = amtOnHld;
    }

    /**
     * @return the unclFnd
     */
    public Amount getUnclFnd() {
	return unclFnd;
    }

    /**
     * @param unclFnd
     *            the unclFnd to set
     */
    public void setUnclFnd(Amount unclFnd) {
	this.unclFnd = unclFnd;
    }

    /**
     * @return the odLmt
     */
    public Amount getOdLmt() {
	return odLmt;
    }

    /**
     * @param odLmt
     *            the odLmt to set
     */
    public void setOdLmt(Amount odLmt) {
	this.odLmt = odLmt;
    }

    /**
     * @return the minBal
     */
    public Amount getMinBal() {
	return minBal;
    }

    /**
     * @param minBal
     *            the minBal to set
     */
    public void setMinBal(Amount minBal) {
	this.minBal = minBal;
    }

    /**
     * @return the wdBal
     */
    public Amount getWdBal() {
	return wdBal;
    }

    /**
     * @param wdBal
     *            the wdBal to set
     */
    public void setWdBal(Amount wdBal) {
	this.wdBal = wdBal;
    }

    /**
     * @return the ibanNo
     */
    public String getIbanNo() {
	return ibanNo;
    }

    /**
     * @param ibanNo
     *            the ibanNo to set
     */
    public void setIbanNo(String ibanNo) {
	this.ibanNo = ibanNo;
    }

    /**
     * @return the brnCde
     */
    public String getBrnCde() {
	return brnCde;
    }

    /**
     * @param brnCde
     *            the brnCde to set
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
     * @param brnNam
     *            the brnNam to set
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
     * @param typ
     *            the typ to set
     */
    public void setTyp(String typ) {
	this.typ = typ;
    }

    /**
     * @return the mkdActNo
     */
    public String getMkdActNo() {
	return mkdActNo;
    }

    /**
     * @param mkdActNo
     *            the mkdActNo to set
     */
    public void setMkdActNo(String mkdActNo) {
	this.mkdActNo = mkdActNo;
    }

    /**
     * @return the actNo
     */
    public String getActNo() {
	return actNo;
    }

    /**
     * @param actNo
     *            the actNo to set
     */
    public void setActNo(String actNo) {
	this.actNo = actNo;
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

    /**
     * @return the curr
     */
    public String getCurr() {
	return curr;
    }

    /**
     * @param curr
     *            the curr to set
     */
    public void setCurr(String curr) {
	this.curr = curr;
    }

    /**
     * @return the priInd
     */
    public String getPriInd() {
	return priInd;
    }

    /**
     * @param priInd
     *            the priInd to set
     */
    public void setPriInd(String priInd) {
	this.priInd = priInd;
    }
}
