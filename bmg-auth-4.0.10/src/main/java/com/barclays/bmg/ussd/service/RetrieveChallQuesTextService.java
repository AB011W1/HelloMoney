/**
 * Get Challenge Question Text Service
 */
package com.barclays.bmg.ussd.service;

/**
 * @author BTCI
 * 
 */
public interface RetrieveChallQuesTextService {

    /**
     * @param questionId
     * @param language
     * @return String
     */
    public String getChallengeQuesText(String questionId, String language);
}
