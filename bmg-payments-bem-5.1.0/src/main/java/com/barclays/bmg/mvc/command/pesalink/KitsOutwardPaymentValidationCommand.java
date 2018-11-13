package com.barclays.bmg.mvc.command.pesalink;

public class KitsOutwardPaymentValidationCommand {


    private String refNo;
    private String frActNo;
    private String amt;
    private String curr;
    private String payRsn;
    private String bnkNm;
    private String bnkSrtCd;

    public String getBnkSrtCd() {
          return bnkSrtCd;
    }

    public void setBnkSrtCd(String bnkSrtCd) {
          this.bnkSrtCd = bnkSrtCd;
    }

    public String getRefNo() {
          return refNo;
    }

    public void setRefNo(String refNo) {
          this.refNo = refNo;
    }

    public String getFrActNo() {
          return frActNo;
    }

    public void setFrActNo(String frActNo) {
          this.frActNo = frActNo;
    }

    /**
    * @return the payRsn
    */
    public String getPayRsn() {
          return payRsn;
    }

    /**
    * @param payRsn
    *            the payRsn to set
    */
    public void setPayRsn(String payRsn) {
          this.payRsn = payRsn;
    }

    /**
    * @return the bnkNm
    */
    public String getBnkNm() {
          return bnkNm;
    }

    /**
    * @param bnkNm
    *            the bnkNm to set
    */
    public void setBnkNm(String bnkNm) {
          this.bnkNm = bnkNm;
    }

    /**
    * @param amt the amt to set
    */
    public void setAmt(String amt) {
          this.amt = amt;
    }

    /**
    * @return the amt
    */
    public String getAmt() {
          return amt;
    }

    /**
    * @param curr the curr to set
    */
    public void setCurr(String curr) {
          this.curr = curr;
    }

    /**
    * @return the curr
    */
    public String getCurr() {
          return curr;
    }

}
