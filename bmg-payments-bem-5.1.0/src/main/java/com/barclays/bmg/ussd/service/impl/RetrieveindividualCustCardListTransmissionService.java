package com.barclays.bmg.ussd.service.impl;

import java.rmi.RemoteException;

import com.barclays.bem.FinancialTransactionCardManagment.FinancialTransactionCardManagment_PortType;
import com.barclays.bem.HMCustomerManagement.HelloMoneyCustomerManagement_PortType;
import com.barclays.bem.RetrieveIndividualCustBeneficiaryList.RetrieveIndividualCustomerBeneficiaryListRequest;
import com.barclays.bem.RetrieveIndividualCustBeneficiaryList.RetrieveIndividualCustomerBeneficiaryListResponse;
import com.barclays.bem.RetrieveIndividualCustCardList.RetrieveIndividualCustCardListRequest;
import com.barclays.bem.RetrieveIndividualCustCardList.RetrieveIndividualCustCardListResponse;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.dao.service.impl.AbstractTransmissionService;
import com.barclays.bmg.exception.BMBDataAccessException;
import com.barclays.bmg.service.request.RetrieveindividualCustCardListServiceRequest;

public class RetrieveindividualCustCardListTransmissionService extends
		AbstractTransmissionService {
	private FinancialTransactionCardManagment_PortType remoteService;
	//TODO
	//private RetrieveindividualCustCardList_PortType remoteService;
	@Override
	public Object sendAndReceive(Object object) throws Exception {
		// TODO Auto-generated method stub
		RetrieveIndividualCustCardListRequest request =
			(RetrieveIndividualCustCardListRequest) object;
		RetrieveIndividualCustCardListResponse response = null;
try{
	response = remoteService.retrieveIndividualCustCardList(request);
}catch (RemoteException e) {
	
	throw new BMBDataAccessException(ResponseCodeConstants.BEM_SERVICE_EXCEPTION);
}
return response;


	}
	public FinancialTransactionCardManagment_PortType getRemoteService() {
		return remoteService;
	}
	public void setRemoteService(FinancialTransactionCardManagment_PortType remoteService) {
		this.remoteService = remoteService;
		}
}



