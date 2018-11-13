package com.barclays.ussd.xmlrequest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "BODY")
public class USSDXMLRequestBody {

    private static final String UTF_8 = "UTF-8";
    String action;
    String userInput;
    String extra;
    String amount;

    public String getAmount() {
		return amount;
	}

    @XmlElement(name = "AMOUNT")
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
     * @return the action
     */
    public String getAction() {
	return action;
    }

    /**
     * @param action
     *            the action to set
     */
    @XmlElement(name = "ACTION")
    public void setAction(String action) {
	this.action = action;
    }

    /**
     * @return the userInput
     */
    public String getUserInput() {
	return userInput;
    }

    /**
     * @param userInput
     *            the userInput to set
     */
    @XmlElement(name = "USERINPUT")
    public void setUserInput(String userInput) {
	try {
	    this.userInput = URLDecoder.decode(userInput, UTF_8);
	} catch (UnsupportedEncodingException e) {
	    // TODO Auto-generated catch block

	}
    }

    /**
     * @return the extra
     */
    public String getExtra() {
	return extra;
    }

    /**
     * @param extra
     *            the extra to set
     */
    @XmlElement(name = "EXTRA")
    public void setExtra(String extra) {
	this.extra = extra;
    }

    @Override
    public String toString() {
	String currentInput = userInput;

	if (userInput != null && userInput.length() == 4 && !userInput.equalsIgnoreCase("null")) { currentInput = "XXXX"; }

	return new StringBuilder("action: ").append(action).append("  || input: ").append(currentInput).append("  || extra: ").append(extra)
		.toString();
    }
}
