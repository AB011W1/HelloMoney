package com.barclays.bmg.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class IntrateDTO implements Serializable {

    private static final long serialVersionUID = -3441465361343764662L;

    private String businessID;
    private String productCode;
    private Date effectiveDtm;
    private String channel;
    private String custSeg;
    private String ccy;
    // private Integer tenure;
    private Integer tenureMonth;

    private Integer tenureDay;
    private BigDecimal from;
    private BigDecimal to;

    private BigDecimal intrate;
    private BigDecimal intvar;
    private BigDecimal panrate;
    private BigDecimal panvar;
    private String tenureType;
    private String prodDesc;

    /**
     * @return the tenureMonth
     */
    public Integer getTenureMonth() {
	return tenureMonth;
    }

    /**
     * @param tenureMonth
     *            the tenureMonth to set
     */
    public void setTenureMonth(Integer tenureMonth) {
	this.tenureMonth = tenureMonth;
    }

    /**
     * @return the tenureDay
     */
    public Integer getTenureDay() {
	return tenureDay;
    }

    /**
     * @param tenureDay
     *            the tenureDay to set
     */
    public void setTenureDay(Integer tenureDay) {
	this.tenureDay = tenureDay;
    }

    /**
     * @return the businessID
     */
    public String getBusinessID() {
	return businessID;
    }

    /**
     * @param businessID
     *            the businessID to set
     */
    public void setBusinessID(String businessID) {
	this.businessID = businessID;
    }

    /**
     * @return the productCode
     */
    public String getProductCode() {
	return productCode;
    }

    /**
     * @param productCode
     *            the productCode to set
     */
    public void setProductCode(String productCode) {
	this.productCode = productCode;
    }

    /**
     * @return the effectiveDtm
     */
    public Date getEffectiveDtm() {
	if (effectiveDtm != null) {
	    return new Date(effectiveDtm.getTime());
	}
	return null;
    }

    /**
     * @param effectiveDtm
     *            the effectiveDtm to set
     */
    public void setEffectiveDtm(Date effectiveDtm) {
	if (effectiveDtm != null) {
	    this.effectiveDtm = new Date(effectiveDtm.getTime());
	} else {
	    this.effectiveDtm = null;
	}
    }

    /**
     * @return the channel
     */
    public String getChannel() {
	return channel;
    }

    /**
     * @param channel
     *            the channel to set
     */
    public void setChannel(String channel) {
	this.channel = channel;
    }

    /**
     * @return the custSeg
     */
    public String getCustSeg() {
	return custSeg;
    }

    /**
     * @param custSeg
     *            the custSeg to set
     */
    public void setCustSeg(String custSeg) {
	this.custSeg = custSeg;
    }

    /**
     * @return the ccy
     */
    public String getCcy() {
	return ccy;
    }

    /**
     * @param ccy
     *            the ccy to set
     */
    public void setCcy(String ccy) {
	this.ccy = ccy;
    }

    /**
     * @return the tenure
     */
    /*
     * public Integer getTenure() { return this.tenureMonth 30 + this.tenureDay; }
     */
    /**
     * @param tenure
     *            the tenure to set
     */
    /*
     * public void setTenure(Integer tenure) { this.tenure = tenure; }
     */
    /**
     * @return the from
     */
    public BigDecimal getFrom() {
	return from;
    }

    /**
     * @param from
     *            the from to set
     */
    public void setFrom(BigDecimal from) {
	this.from = from;
    }

    /**
     * @return the to
     */
    public BigDecimal getTo() {
	return to;
    }

    /**
     * @param to
     *            the to to set
     */
    public void setTo(BigDecimal to) {
	this.to = to;
    }

    /**
     * @return the intrate
     */
    public BigDecimal getIntrate() {
	return intrate;
    }

    /**
     * @param intrate
     *            the intrate to set
     */
    public void setIntrate(BigDecimal intrate) {
	this.intrate = intrate;
    }

    /**
     * @return the intvar
     */
    public BigDecimal getIntvar() {
	return intvar;
    }

    /**
     * @param intvar
     *            the intvar to set
     */
    public void setIntvar(BigDecimal intvar) {
	this.intvar = intvar;
    }

    /**
     * @return the panrate
     */
    public BigDecimal getPanrate() {
	return panrate;
    }

    /**
     * @param panrate
     *            the panrate to set
     */
    public void setPanrate(BigDecimal panrate) {
	this.panrate = panrate;
    }

    /**
     * @return the panvar
     */
    public BigDecimal getPanvar() {
	return panvar;
    }

    /**
     * @param panvar
     *            the panvar to set
     */
    public void setPanvar(BigDecimal panvar) {
	this.panvar = panvar;
    }

    public String getTenureType() {
	return tenureType;
    }

    public void setTenureType(String tenureType) {
	this.tenureType = tenureType;
    }

    public String getProdDesc() {
	return prodDesc;
    }

    public void setProdDesc(String prodDesc) {
	this.prodDesc = prodDesc;
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append("IntrateDTO[productCode=").append(productCode);
	sb.append(",tenureType=").append(tenureType);
	sb.append(",effectiveDtm=").append(effectiveDtm);
	sb.append(",intrate=").append(intrate);
	sb.append(",tenureMonth=").append(tenureMonth);
	sb.append(",tenureDay=").append(tenureDay);
	sb.append("]");
	return sb.toString();
    }

}
