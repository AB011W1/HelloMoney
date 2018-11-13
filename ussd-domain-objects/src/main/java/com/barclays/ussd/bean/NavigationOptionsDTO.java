package com.barclays.ussd.bean;

public class NavigationOptionsDTO {
    private String backOption;
    private String homeOption;
    private String scrollUpOption;
    private String scrollDownOption;

    public String getScrollUpOption() {
	return scrollUpOption;
    }

    public void setScrollUpOption(String scrollUpOption) {
	this.scrollUpOption = scrollUpOption;
    }

    public String getScrollDownOption() {
	return scrollDownOption;
    }

    public void setScrollDownOption(String scrollDownOption) {
	this.scrollDownOption = scrollDownOption;
    }

    public String getBackOption() {
	return backOption;
    }

    public void setBackOption(String backOption) {
	this.backOption = backOption;
    }

    public String getHomeOption() {
	return homeOption;
    }

    public void setHomeOption(String homeOption) {
	this.homeOption = homeOption;
    }

}
