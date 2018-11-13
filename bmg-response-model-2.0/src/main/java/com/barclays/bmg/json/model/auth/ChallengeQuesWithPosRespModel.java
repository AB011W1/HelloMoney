/**
 * Challenge Question with position response model
 */
package com.barclays.bmg.json.model.auth;

import com.barclays.bmg.json.response.BMBPayloadData;

/**
 * @author BTCI
 *
 */
public class ChallengeQuesWithPosRespModel extends BMBPayloadData{

	private static final long serialVersionUID = 8049767021313343805L;
	private String challengeId;
	private String questionPos1;
	private String questionPos2;
	private String questionText;
	/**
	 * @return the challengeId
	 */
	public String getChallengeId() {
		return challengeId;
	}
	/**
	 * @param challengeId the challengeId to set
	 */
	public void setChallengeId(String challengeId) {
		this.challengeId = challengeId;
	}
	/**
	 * @return the questionPos1
	 */
	public String getQuestionPos1() {
		return questionPos1;
	}
	/**
	 * @param questionPos1 the questionPos1 to set
	 */
	public void setQuestionPos1(String questionPos1) {
		this.questionPos1 = questionPos1;
	}
	/**
	 * @return the questionPos2
	 */
	public String getQuestionPos2() {
		return questionPos2;
	}
	/**
	 * @param questionPos2 the questionPos2 to set
	 */
	public void setQuestionPos2(String questionPos2) {
		this.questionPos2 = questionPos2;
	}
	/**
	 * @return the questionText
	 */
	public String getQuestionText() {
		return questionText;
	}
	/**
	 * @param questionText the questionText to set
	 */
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}




}
