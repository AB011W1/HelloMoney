package com.barclays.bmg.service.bankdraft;

import com.barclays.bmg.service.request.bankdraft.PurchaseBankDraftServiceRequest;
import com.barclays.bmg.service.response.bankdraft.PurchaseBankDraftServiceResponse;

public interface PurchaseBankDraftService {

    public PurchaseBankDraftServiceResponse purchaseBankDraft(PurchaseBankDraftServiceRequest purchaseBankDraftServiceRequest);
}
