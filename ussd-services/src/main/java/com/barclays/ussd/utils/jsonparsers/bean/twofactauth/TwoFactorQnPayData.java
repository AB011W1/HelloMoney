/**
 * FromAcntLst.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.twofactauth;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.bean.TwoFactorQuestion;

/**
 * @author BTCI
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TwoFactorQnPayData {
    @JsonProperty
    private List<TwoFactorQuestion> questionWithPositions;

    public List<TwoFactorQuestion> getQuestionWithPositions() {
	return questionWithPositions;
    }

    public void setQuestionWithPositions(List<TwoFactorQuestion> questionWithPositions) {
	this.questionWithPositions = questionWithPositions;
    }
}
