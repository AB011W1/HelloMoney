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
package com.barclays.bmg.json.model.builder.auth;

import com.barclays.bmg.dto.QuesWithPosDTO;
import com.barclays.bmg.json.response.BMBPayloadData;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>ChallengeQuestionAndPositionJSONResponseModel.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 21, 2013</b>
 * </br> ***********************************************************
 *
 * @author E20043104
 *
 */
public class ChallengeQuestionAndPositionJSONResponseModel extends BMBPayloadData {


	private static final long serialVersionUID = -2203138724921005787L;
	private QuesWithPosDTO[] questionWithPositions;

    public QuesWithPosDTO[] getQuestionWithPositions() {
	return questionWithPositions;
    }

    public void setQuestionWithPositions(QuesWithPosDTO[] questionWithPositions) {
	this.questionWithPositions = questionWithPositions;
    }

}
