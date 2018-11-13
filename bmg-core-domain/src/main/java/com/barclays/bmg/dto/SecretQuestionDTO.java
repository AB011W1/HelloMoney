package com.barclays.bmg.dto;

public class SecretQuestionDTO extends BaseDomainDTO {

    private static final long serialVersionUID = -6801378137430491743L;

    private String questionId;
    private String messageValue;

    public String getMessageValue() {
	return messageValue;
    }

    public void setMessageValue(String messageValue) {
	this.messageValue = messageValue;
    }

    /**
     * @return the questionId
     */
    public String getQuestionId() {
	return questionId;
    }

    /**
     * @param questionId
     *            the questionId to set
     */
    public void setQuestionId(String questionId) {
	this.questionId = questionId;
    }

}
