/**
 * Retrieve Challenge Question Text
 */
package com.barclays.bmg.ussd.dao;

/**
 * @author BTCI
 * 
 */
public interface RetrieveChallQuesTextDAO {
    /**
     * @param questionId
     * @param language
     * @return String
     */
    public String getChallengeQuesText(String questionId, String language);

}
