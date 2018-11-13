package com.barclays.bmg.dao;

import com.barclays.bmg.service.request.bankdraft.PurchaseBankDraftServiceRequest;
import com.barclays.bmg.service.response.bankdraft.PurchaseBankDraftServiceResponse;

public interface PurchaseBankDraftDAO {

	public PurchaseBankDraftServiceResponse purchaseBankDraft(
			PurchaseBankDraftServiceRequest purchaseBankDraftServiceRequest);

}
