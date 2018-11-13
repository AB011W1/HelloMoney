package com.barclays.bmg.operation.response;

import com.barclays.bmg.context.ResponseContext;

public class SQAGenerateAuthenticationOperationResponse extends ResponseContext {
    /**
	 *
	 */
    private static final long serialVersionUID = 7744131880731853546L;
    private boolean success;
    private String question;
    private String questionId;

    @Override
    public boolean isSuccess() {
	return success;
    }

    @Override
    public void setSuccess(boolean success) {
	this.success = success;
    }

    public String getQuestion() {
	return question;
    }

    public void setQuestion(String question) {
	this.question = question;
    }

    public String getQuestionId() {
	return questionId;
    }

    public void setQuestionId(String questionId) {
	this.questionId = questionId;
    }

}
