package com.barclays.bmg.service.response;

import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.DebitCardDTO;

public class RetrieveindividualCustCardListServiceResponse extends
		ResponseContext {
	   /**
	 *
	 */
   private static final long serialVersionUID = -1699114389201116019L;
   List<DebitCardDTO> DebitCardDTOList;
public List<DebitCardDTO> getDebitCardDTOList() {
	return DebitCardDTOList;
}
public void setDebitCardDTOList(List<DebitCardDTO> debitCardDTOList) {
	DebitCardDTOList = debitCardDTOList;
}


}
