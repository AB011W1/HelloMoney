package com.barclays.bmg.operation.response;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.ValidateUserDTO;

public class ValidateUserOperationResponse extends ResponseContext {
    /**
	 *
	 */
    private static final long serialVersionUID = 3442406259121352533L;
    private ValidateUserDTO validateUserDto;

    public ValidateUserDTO getValidateUserDto() {
	return validateUserDto;
    }

    public void setValidateUserDto(ValidateUserDTO validateUserDto) {
	this.validateUserDto = validateUserDto;
    }

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
