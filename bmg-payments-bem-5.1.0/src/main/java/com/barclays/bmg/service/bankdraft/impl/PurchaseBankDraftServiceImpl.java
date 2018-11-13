package com.barclays.bmg.service.bankdraft.impl;

import com.barclays.bmg.dao.PurchaseBankDraftDAO;
import com.barclays.bmg.service.bankdraft.PurchaseBankDraftService;
import com.barclays.bmg.service.request.bankdraft.PurchaseBankDraftServiceRequest;
import com.barclays.bmg.service.response.bankdraft.PurchaseBankDraftServiceResponse;
import com.barclays.bmg.service.sessionactivity.annotation.SessionActivitySupport;

public class PurchaseBankDraftServiceImpl implements PurchaseBankDraftService {

	private PurchaseBankDraftDAO purchaseBankDraftDAO;

	@Override
	@SessionActivitySupport(activityType="bankDraft")
	public PurchaseBankDraftServiceResponse purchaseBankDraft(
			PurchaseBankDraftServiceRequest purchaseBankDraftServiceRequest) {
		// TODO Auto-generated method stub
		return purchaseBankDraftDAO.purchaseBankDraft(purchaseBankDraftServiceRequest);
	}

	public PurchaseBankDraftDAO getPurchaseBankDraftDAO() {
		return purchaseBankDraftDAO;
	}

	public void setPurchaseBankDraftDAO(PurchaseBankDraftDAO purchaseBankDraftDAO) {
		this.purchaseBankDraftDAO = purchaseBankDraftDAO;
	}



}
