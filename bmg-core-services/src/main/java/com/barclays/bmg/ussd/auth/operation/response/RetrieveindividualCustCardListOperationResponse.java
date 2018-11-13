package com.barclays.bmg.ussd.auth.operation.response;

import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.DebitCardDTO;

public class RetrieveindividualCustCardListOperationResponse extends
		ResponseContext {
	   List<DebitCardDTO> DebitCardDTOList;
	   public List<DebitCardDTO> getDebitCardDTOList() {
	   	return DebitCardDTOList;
	   }
	   public void setDebitCardDTOList(List<DebitCardDTO> debitCardDTOList) {
	   	DebitCardDTOList = debitCardDTOList;
	   }
}
