package com.barclays.bmg.json.response;

import java.io.Serializable;

import com.barclays.bmg.json.model.JSONResponseError;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;

public  class BaseJSONResponseModel extends BMBBaseResponseModel implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1491966163193024853L;

	private boolean success;
	private JSONResponseError error;
	private JSONResponseCodes succCdes;

	public BaseJSONResponseModel() {
		super();
		// default values
		error = new JSONResponseError();
		success = true;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public JSONResponseError getError() {
		return error;
	}
	public void setError(JSONResponseError error) {
		this.error = error;
	}
	public JSONResponseCodes getSuccCdes() {
		return succCdes;
	}
	public void setSuccCdes(JSONResponseCodes succCdes) {
		this.succCdes = succCdes;
	}

}
