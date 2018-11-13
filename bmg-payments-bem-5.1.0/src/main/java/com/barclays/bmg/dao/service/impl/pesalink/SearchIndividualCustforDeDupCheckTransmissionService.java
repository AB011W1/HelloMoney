package com.barclays.bmg.dao.service.impl.pesalink;

import java.rmi.RemoteException;

import com.barclays.bem.AddIndividualCustContactHistory.AddIndividualCustContactHistoryResponse;
import com.barclays.bem.AddProblem.AddProblemRequest;
import com.barclays.bem.AddProblem.AddProblemResponse;
import com.barclays.bem.FinancialTransactionProcessing.FinancialTransactionProcessing_PortType;
import com.barclays.bem.IndividualCustomerManagement.IndividualCustomerManagement_PortType;
import com.barclays.bem.ProblemManagement.ProblemManagement_PortType;
import com.barclays.bem.SearchIndividualCustInformation.SearchIndividualCustomerInformationRequest;
import com.barclays.bem.SearchIndividualCustInformation.SearchIndividualCustomerInformationResponse;
import com.barclays.bem.ViewBillPaymentDetails.ViewBillPaymentDetailsRequest;
import com.barclays.bem.ViewBillPaymentDetails.ViewBillPaymentDetailsResponse;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.dao.service.TransmissionService;
import com.barclays.bmg.exception.BMBDataAccessException;

public class SearchIndividualCustforDeDupCheckTransmissionService  implements TransmissionService {


		private IndividualCustomerManagement_PortType  remoteService;
		@Override
		public Object sendAndReceive(Object object) throws Exception {
			SearchIndividualCustomerInformationRequest request = (SearchIndividualCustomerInformationRequest)object;

			SearchIndividualCustomerInformationResponse response = null;
			try{
				response = remoteService.searchIndividualCustInformation(request);
			}catch (RemoteException e) {
				
				throw new BMBDataAccessException(ResponseCodeConstants.BEM_SERVICE_EXCEPTION);
			}
			return response;

		}
		public IndividualCustomerManagement_PortType getRemoteService() {
			return remoteService;
		}
		public void setRemoteService(IndividualCustomerManagement_PortType remoteService) {
			this.remoteService = remoteService;
		}

}


