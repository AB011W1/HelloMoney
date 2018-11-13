package com.barclays.ussd.utils.jsonparsers.bean.login;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SelfRegistrationQuestion {

	@JsonProperty
	private String quesId;

	@JsonProperty
	private String challengeId;

	@JsonProperty
	private String ansPos1;

	@JsonProperty
	private String ansPos2;

	public String getQuesId() {
		return quesId;
	}

	public void setQuesId(String quesId) {
		this.quesId = quesId;
	}

	public String getChallengeId() {
		return challengeId;
	}

	public void setChallengeId(String challengeId) {
		this.challengeId = challengeId;
	}

	public String getAnsPos1() {
		return ansPos1;
	}

	public void setAnsPos1(String ansPos1) {
		this.ansPos1 = ansPos1;
	}

	public String getAnsPos2() {
		return ansPos2;
	}

	public void setAnsPos2(String ansPos2) {
		this.ansPos2 = ansPos2;
	}

}
