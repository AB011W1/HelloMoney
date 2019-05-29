package com.barclays.ussd.xmlrequest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "BODY")
@XmlType(propOrder = { "action", "userInput", "extra", "amount" })
@XmlAccessorType(XmlAccessType.FIELD)
public class USSDXMLReqBody {

    @XmlElement(name = "ACTION")
    String action;

    @XmlElement(name = "USERINPUT")
    String userInput;

    @XmlElement(name = "EXTRA")
    String extra;

    @XmlElement(name = "AMOUNT")
    String amount;

    public String getAmount() {
		return amount;
	}

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
    public void setUserInput(String userInput) {
	this.userInput = userInput;
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
    public void setExtra(String extra) {
	this.extra = extra;
    }

    @Override
    public String toString() {
	return new StringBuilder("action: ").append(action).append(" //userInput: ").append(userInput).append("  //extra: ").append(extra).toString();
    }
}
