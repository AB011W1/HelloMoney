package com.barclays.bmg.operation.request;

import com.barclays.bmg.context.RequestContext;

public class SQAAuthenticationOperationRequest extends RequestContext {

    private String sqa;
    private String questionId;

    public String getSqa() {
	return sqa;
    }

    public void setSqa(String sqa) {
	this.sqa = sqa;
    }

    public String getQuestionId() {
	return questionId;
    }

    public void setQuestionId(String questionId) {
	this.questionId = questionId;
    }

}
