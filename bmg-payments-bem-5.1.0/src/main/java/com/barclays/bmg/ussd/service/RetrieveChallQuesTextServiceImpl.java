/**
 * Retrieve Challenge Question Text
 */
package com.barclays.bmg.ussd.service;

import org.apache.log4j.Logger;

import com.barclays.bmg.ussd.dao.RetrieveChallQuesTextDAO;

/**
 * @author BTCI
 * 
 */
public class RetrieveChallQuesTextServiceImpl implements RetrieveChallQuesTextService {
    private static final Logger logger = Logger.getLogger(RetrieveChallQuesTextServiceImpl.class);
    RetrieveChallQuesTextDAO retrieveChallQuesText;

    /**
     * @return the retrieveChallQuesText
     */
    public RetrieveChallQuesTextDAO getRetrieveChallQuesText() {
	return retrieveChallQuesText;
    }

    /**
     * @param retrieveChallQuesText
     *            the retrieveChallQuesText to set
     */
    public void setRetrieveChallQuesText(RetrieveChallQuesTextDAO retrieveChallQuesText) {
	this.retrieveChallQuesText = retrieveChallQuesText;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.barclays.bmg.ussd.service.RetrieveChallQuesTextService#getChallengeQuesText(java.lang.String, java.lang.String)
     */
    public String getChallengeQuesText(String questionId, String language) {
	logger.info("Enter RetrieveChallQuesTextServiceImpl.getChallengeQuesText");
	String challengeQuesText = retrieveChallQuesText.getChallengeQuesText(questionId, language);
	logger.info("Exit RetrieveChallQuesTextServiceImpl.getChallengeQuesText");
	return challengeQuesText;
    }

}
