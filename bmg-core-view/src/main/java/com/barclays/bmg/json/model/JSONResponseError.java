package com.barclays.bmg.json.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.constants.ErrorCodeConstant;

public class JSONResponseError implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -1933441654222526600L;
	public static final String RECOVERABLE_ERROR = "1002";
	public static final String UNRECOVERABLE_ERROR = "1001";
	public static final String WARNING = "1100";
	public static final String INFO = "1110";

	public static final String RELOGIN_NEEDED_ERROR = "1003";
	public static String DISPLAY_OTP = "2000";
	public static String DISPLAY_SQA = "2001";

	private List<String> messageList;
	private String errType;

	public JSONResponseError() {
		super();
		// default values
		this.messageList = new ArrayList<String>();
		this.errType = "";

	}

	public List<String> getMessageList() {
		return messageList;
	}

	public void setMessageList(List<String> messageList) {
		this.messageList = messageList;
	}

	public void addMessage(String msg) {
		messageList.add(msg);
	}

	public String getErrType() {
		return errType;
	}

/*	public void setErrType(String errType) {
		this.errType = errType;
	}*/

	public void setJSONErrorType(String errorType){
		if(ErrorCodeConstant.UNRECOVERABLE_ERROR.equals(errorType)){
			errType = UNRECOVERABLE_ERROR;
		}else if(ErrorCodeConstant.RECOVERABLE_ERROR.equals(errorType)){
			errType = RECOVERABLE_ERROR;
		}else if(ErrorCodeConstant.WARNING.equals(errorType)){
			errType = WARNING;
		}else if(ErrorCodeConstant.INFO.equals(errorType)){
			errType = INFO;
		}else if(ErrorCodeConstant.RELOGIN_NEEDED_ERROR.equals(errorType)){
			errType = RELOGIN_NEEDED_ERROR;
		}else{
			errType = UNRECOVERABLE_ERROR;
		}
	}
}
