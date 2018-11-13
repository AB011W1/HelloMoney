package com.barclays.bmg.cashsend.service.impl;

import com.barclays.bmg.cashsend.dao.CashSendEncryptionDAO;
import com.barclays.bmg.cashsend.dao.CashSendOneTimeExecuteDAO;
import com.barclays.bmg.cashsend.service.CashSendOneTimeExecuteService;
import com.barclays.bmg.cashsend.service.request.CashSendOneTimeExecuteServiceRequest;
import com.barclays.bmg.cashsend.service.response.CashSendOneTimeExecuteServiceResponse;

public class CashSendOneTimeExecuteServiceImpl implements CashSendOneTimeExecuteService {

    CashSendOneTimeExecuteDAO cashSendOneTimeExecuteDAO;

    CashSendEncryptionDAO cashSendEncryptionDAO;

    public CashSendEncryptionDAO getCashSendEncryptionDAO() {
	return cashSendEncryptionDAO;
    }

    public void setCashSendEncryptionDAO(CashSendEncryptionDAO cashSendEncryptionDAO) {
	this.cashSendEncryptionDAO = cashSendEncryptionDAO;
    }

    // Please verify below code
    @Override
    public CashSendOneTimeExecuteServiceResponse executeCashSend(CashSendOneTimeExecuteServiceRequest request) {

	CashSendOneTimeExecuteServiceResponse response = cashSendOneTimeExecuteDAO.executeCashSend(request);

	return response;
    }

    @Override
    public CashSendOneTimeExecuteServiceResponse encryptCashSendPin(CashSendOneTimeExecuteServiceRequest request) {

	CashSendOneTimeExecuteServiceResponse response = cashSendEncryptionDAO.encryptPin(request);

	return response;
    }

    public CashSendOneTimeExecuteDAO getCashSendOneTimeExecuteDAO() {
	return cashSendOneTimeExecuteDAO;
    }

    public void setCashSendOneTimeExecuteDAO(CashSendOneTimeExecuteDAO cashSendOneTimeExecuteDAO) {
	this.cashSendOneTimeExecuteDAO = cashSendOneTimeExecuteDAO;
    }

}
