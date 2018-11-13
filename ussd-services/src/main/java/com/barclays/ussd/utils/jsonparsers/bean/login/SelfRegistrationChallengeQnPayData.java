package com.barclays.ussd.utils.jsonparsers.bean.login;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SelfRegistrationChallengeQnPayData {
    @JsonProperty
    List<SelfRegistrationQuestion> questionWithPositions;



	public List<SelfRegistrationQuestion> getQuestionWithPositions() {
	return questionWithPositions;
    }

    public void setQuestionWithPositions(List<SelfRegistrationQuestion> questionWithPositions) {
	this.questionWithPositions = questionWithPositions;
    }

}
