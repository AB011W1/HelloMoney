package com.barclays.bmg.sort;

import java.io.Serializable;
import java.util.Comparator;

import com.barclays.bmg.json.model.fundtransfer.PayeeJSONModel;

public class PayeeJSONModelComparator implements Comparator<PayeeJSONModel>, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 7175106238877198436L;

	@Override
	public int compare(PayeeJSONModel object1, PayeeJSONModel object2) {

		int compareInd = 0;

		if(object1 != null &&  object2 != null){
			String payeeName1 = object1.getDisLbl();
			String payeeName2 = object2.getDisLbl();

			if(payeeName1 != null && payeeName2 != null){
				compareInd = payeeName1.compareToIgnoreCase(payeeName2);
			}
		}

		return compareInd;
	}

}
