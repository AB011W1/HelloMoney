package com.barclays.ussd.bean;

/**
 * The Class TwoFactorQuestion.
 */
public class TwoFactorQuestion {

    /** The challenge id. */
    private String challengeId;

    /** The ques id. */
    private String quesId;

    /** The ans pos1. */
    private String ansPos1;

    /** The ans pos2. */
    private String ansPos2;

    /**
     * Gets the challenge id.
     * 
     * @return the challenge id
     */
    public String getChallengeId() {
	return challengeId;
    }

    /**
     * Sets the challenge id.
     * 
     * @param challengeId
     *            the new challenge id
     */
    public void setChallengeId(String challengeId) {
	this.challengeId = challengeId;
    }

    /**
     * Gets the ques id.
     * 
     * @return the ques id
     */
    public String getQuesId() {
	return quesId;
    }

    /**
     * Sets the ques id.
     * 
     * @param quesId
     *            the new ques id
     */
    public void setQuesId(String quesId) {
	this.quesId = quesId;
    }

    /**
     * Gets the ans pos1.
     * 
     * @return the ans pos1
     */
    public String getAnsPos1() {
	return ansPos1;
    }

    /**
     * Sets the ans pos1.
     * 
     * @param ansPos1
     *            the new ans pos1
     */
    public void setAnsPos1(String ansPos1) {
	this.ansPos1 = ansPos1;
    }

    /**
     * Gets the ans pos2.
     * 
     * @return the ans pos2
     */
    public String getAnsPos2() {
	return ansPos2;
    }

    /**
     * Sets the ans pos2.
     * 
     * @param ansPos2
     *            the new ans pos2
     */
    public void setAnsPos2(String ansPos2) {
	this.ansPos2 = ansPos2;
    }

}
