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
package com.barclays.bmg.ussd.service;

import com.barclays.bmg.ussd.auth.service.request.ChallengeQuestionAndPositionServiceRequest;
import com.barclays.bmg.ussd.auth.service.response.ChallengeQuestionAndPositionServiceResponse;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>ChallengeQuestionAndPositionService.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 21, 2013</b> </br>
 * ******************************************************
 * 
 * @author E20043104
 * 
 */
public interface ChallengeQuestionAndPositionService {

    // public ChallengeQuestionAndPositionServiceResponse getCountOfQuesToBeAsked(ChallengeQuestionAndPositionServiceRequest serviceRequest);

    // public ChallengeQuestionAndPositionServiceResponse getChallengeQuestions(ChallengeQuestionAndPositionServiceRequest serviceRequest);

    /**
     * This contract getChallengeQuesPos has the purpose to get Challenge question positions.
     * 
     * @param ChallengeQuestionAndPositionServiceRequest
     * @return ChallengeQuestionAndPositionServiceResponse
     */
    public ChallengeQuestionAndPositionServiceResponse getChallengePositions(ChallengeQuestionAndPositionServiceRequest serviceRequest);

}