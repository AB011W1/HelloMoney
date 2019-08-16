/*
 * Copyright (c) 2013 Barclays Bank Plc, All Rights Reserved.
 *
 * This code is confidential to Barclays Bank Plc and shall not be disclosed
 * outside the Bank without the prior written permission of the Director of
 * CIO
 *
 * In the event that such disclosure is permitted the code shall not be copied
 * or distributed other than on a need-to-know basis and any recipients may be
 * required to sign a confidentiality undertaking in favour of Barclays Bank
 * Plc.
 */
package com.barclays.bmg.ussd.service.impl;

import com.barclays.bmg.ussd.auth.service.request.ChallengeQuestionAndPositionServiceRequest;
import com.barclays.bmg.ussd.auth.service.response.ChallengeQuestionAndPositionServiceResponse;
import com.barclays.bmg.ussd.dao.ChallengeQuestionAndPositionDAO;
import com.barclays.bmg.ussd.service.ChallengeQuestionAndPositionService;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>ChallengeQuestionAndPositionServiceImpl.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 21, 2013</b> </br>
 * ***********************************************************
 * 
 * @author E20043104
 * 
 */
public class ChallengeQuestionAndPositionServiceImpl implements ChallengeQuestionAndPositionService {

    /**
     * The instance variable for challengeQuestionAndPositionDAO of type ChallengeQuestionAndPositionDAO
     */
    private ChallengeQuestionAndPositionDAO challengeQuestionAndPositionDAO;

    /**
     * Getter for ChallengeQuestionAndPositionDAO
     * 
     *@param none
     *@return ChallengeQuestionAndPositionDAO
     */
    public ChallengeQuestionAndPositionDAO getChallengeQuestionAndPositionDAO() {
	return challengeQuestionAndPositionDAO;
    }

    /**
     * Setter for ChallengeQuestionAndPositionDAO
     * 
     * @param ChallengeQuestionAndPositionDAO
     * @return void
     */
    public void setChallengeQuestionAndPositionDAO(ChallengeQuestionAndPositionDAO challengeQuestionAndPositionDAO) {
	this.challengeQuestionAndPositionDAO = challengeQuestionAndPositionDAO;
    }

    /**
     * This implementation method has the purpose to Answer Positions to Challenge Questions
     * 
     * @param ChallengeQuestionAndPositionServiceRequest
     * @return ChallengeQuestionAndPositionServiceResponse
     */
    public ChallengeQuestionAndPositionServiceResponse getChallengePositions(ChallengeQuestionAndPositionServiceRequest serviceRequest) {

	return challengeQuestionAndPositionDAO.getChallengePositions(serviceRequest);

    }

    /*
     * 
     * private static SecureRandom random;
     * 
     * private ChallengeQuesWithPosDAO challengeQuesWithPosDAO;
     * 
     * public ChallengeQuesWithPosDAO getChallengeQuesWithPosDAO() { return challengeQuesWithPosDAO; }
     * 
     * public void setChallengeQuesWithPosDAO( ChallengeQuesWithPosDAO challengeQuesWithPosDAO) { this.challengeQuesWithPosDAO =
     * challengeQuesWithPosDAO; } public ChallengeQuesServiceResponse getCountOfQuesToBeAsked(ChallengeQuesServiceRequest serviceRequest){
     * ChallengeQuesServiceResponse challengeQuesServiceResponse=challengeQuesWithPosDAO.getCountOfQuestions(serviceRequest); return
     * challengeQuesServiceResponse; }
     * 
     * 
     * public ChallengeQuesServiceResponse getChallengeQuestions( ChallengeQuesServiceRequest serviceRequest) {
     * 
     * ChallengeQuesServiceResponse challengeQuesServiceResponse=challengeQuesWithPosDAO.getChallengeQuestions(serviceRequest);
     * 
     * String noOfQuesToBeAsked=serviceRequest.getQuestionCount(); List<String> questionList =challengeQuesServiceResponse.getQuestionIdList();
     * 
     * challengeQuesServiceResponse.setQuestionIdList(selectedQuestions(questionList,noOfQuesToBeAsked));
     * 
     * return challengeQuesServiceResponse; }
     * 
     * private List<String> selectedQuestions(List<String> questionList,String count){
     * 
     * 
     * Set<Integer> set=new LinkedHashSet<Integer>();
     * 
     * if(count!=null){ set=generateRandomDigits(Integer.parseInt(count)); }
     * 
     * List<String> selectedQues=new ArrayList<String>();
     * 
     * Iterator<Integer> iterator=set.iterator(); while(iterator.hasNext()){ int selectedIndex=(Integer)iterator.next();
     * 
     * if(questionList!=null && !questionList.isEmpty()){ selectedQues.add(questionList.get(selectedIndex)); } }
     * 
     * return selectedQues;
     * 
     * }
     * 
     * private static Set<Integer> generateRandomDigits(int count) {
     * 
     * random=BMBCommonUtility.getSecureRandom();
     * 
     * int index=0;
     * 
     * Set<Integer> set=new LinkedHashSet<Integer>();
     * 
     * while(count>0){
     * 
     * index = Math.round((float) (random.nextDouble() 10));
     * 
     * if (index > count) { index = count; }
     * 
     * set.add(index);
     * 
     * if(set.size()==count){ break; }
     * 
     * } return set; }
     */

}
