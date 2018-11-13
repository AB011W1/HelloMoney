package com.barclays.bmg.service.request;

import com.barclays.bmg.context.RequestContext;

public class InqueryBillServiceRequest extends RequestContext {

    private String BillRefNo1;
    private String BillRefNo2;
    private String topUpService;
    private String billerId;
    private boolean mobileProvider;

    public String getBillRefNo1() {
	return BillRefNo1;
    }

    public void setBillRefNo1(String billRefNo1) {
	BillRefNo1 = billRefNo1;
    }

    public String getBillRefNo2() {
	return BillRefNo2;
    }

    public void setBillRefNo2(String billRefNo2) {
	BillRefNo2 = billRefNo2;
    }

    public String getTopUpService() {
	return topUpService;
    }

    public void setTopUpService(String topUpService) {
	this.topUpService = topUpService;
    }

    public String getBillerId() {
	return billerId;
    }

    public void setBillerId(String billerId) {
	this.billerId = billerId;
    }

    public boolean isMobileProvider() {
	return mobileProvider;
    }

    public void setMobileProvider(boolean mobileProvider) {
	this.mobileProvider = mobileProvider;
    }

}
