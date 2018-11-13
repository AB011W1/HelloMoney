package com.barclays.bmg.dto;

public class QuesWithPosDTO extends BaseDomainDTO {

    public QuesWithPosDTO(String quesId, String ansPos1, String ansPos2, String challengeId) {
	this.quesId = quesId;
	this.ansPos1 = ansPos1;
	this.ansPos2 = ansPos2;
	this.challengeId = challengeId;
    }

    /**
	 *
	 */
    private static final long serialVersionUID = -2288808555032711834L;

    private String quesId;

    private String ansPos1;

    private String ansPos2;

    private String challengeId;

    public String getChallengeId() {
	return challengeId;
    }

    public void setChallengeId(String challengeId) {
	this.challengeId = challengeId;
    }

    public String getQuesId() {
	return quesId;
    }

    public void setQuesId(String quesId) {
	this.quesId = quesId;
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
