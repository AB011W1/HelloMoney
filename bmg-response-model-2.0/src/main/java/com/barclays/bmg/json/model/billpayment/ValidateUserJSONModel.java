package com.barclays.bmg.json.model.billpayment;

import java.io.Serializable;

import com.barclays.bmg.json.response.BMBPayloadData;

public class ValidateUserJSONModel extends BMBPayloadData implements Serializable {
	private static final long serialVersionUID = -5436515665377036572L;
    /*private boolean  isUserValid;
    private String validationMessage;
    private String status;*/
    private String prefLang;
    private String usrSta;
	/**
	 * @return the prefLang
	 */
	public String getPrefLang() {
		return prefLang;
	}

	/**
	 * @param prefLang the prefLang to set
	 */
	public void setPrefLang(String prefLang) {
		this.prefLang = prefLang;
	}

	/**
	 * @return the usrSta
	 */
	public String getUsrSta() {
		return usrSta;
	}

	/**
	 * @param usrSta the usrSta to set
	 */
	public void setUsrSta(String usrSta) {
		this.usrSta = usrSta;
	}

	/*public boolean getIsUserValid() {
		return isUserValid;
	}

	public void setIsUserValid(boolean isUserValid) {
		this.isUserValid = isUserValid;
	}

	public String getValidationMessage() {
		return validationMessage;
	}

	public void setValidationMessage(String validationMessage) {
		this.validationMessage = validationMessage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}*/

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public ValidateUserJSONModel() {

	}
	@Override
	public String toString()
	{
//		"isUserValid="+isUserValid+",validationMessage="+validationMessage+" ,status="+status+" ,
		return "prefLang="+prefLang+" ,usrSta="+usrSta;
	}
}