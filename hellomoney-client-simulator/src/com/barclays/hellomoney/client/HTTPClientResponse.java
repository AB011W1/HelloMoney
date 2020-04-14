package com.barclays.hellomoney.client;

public class HTTPClientResponse {

    String responseBody;

    /**
     * @param responseBody
     */
    public HTTPClientResponse(String responseBody) {
	this.responseBody = responseBody;
    }

    /**
     * @return the responseBody
     */
    public final String getResponseBody() {
	return responseBody;
    }

    /**
     * @param responseBody
     *            the responseBody to set
     */
    public final void setResponseBody(String responseBody) {
	this.responseBody = responseBody;
    }

}
