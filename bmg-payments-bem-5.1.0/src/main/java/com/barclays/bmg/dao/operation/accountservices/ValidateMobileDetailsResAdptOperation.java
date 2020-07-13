package com.barclays.bmg.dao.operation.accountservices;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bem.BEMServiceHeader.BEMResHeader;
import com.barclays.bem.IndividualName.IndividualName;
import com.barclays.bem.RetrieveMobileDetails.CustomerAccountBasic;
import com.barclays.bem.RetrieveMobileDetails.CustomerBasic;

import com.barclays.bem.RetrieveMobileDetails.RetrieveMobileDetailsResponse;
import com.barclays.bmg.constants.BillPaymentResponseCodeConstants;
import com.barclays.bmg.constants.ErrorCodeConstant;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ServiceErrorCodeConstant;
import com.barclays.bmg.constants.ServiceIdConstants;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.exception.BMBDataAccessException;
import com.barclays.bmg.service.request.MWalletValidateServiceRequest;
import com.barclays.bmg.service.request.RetrieveAllCustAcctServiceRequest;
import com.barclays.bmg.service.response.MWalletValidateServiceResopnse;
import com.barclays.bmg.service.response.RetrieveAllCustAcctServiceResponse;

public class ValidateMobileDetailsResAdptOperation {
	
	//TODO Service request and response
		public MWalletValidateServiceResopnse adaptResponse(WorkContext workContext, Object obj) throws Exception {
			CustomerAccountBasic custAccntBasic = null;
			CustomerBasic custBasic = null;
			IndividualName name = null;
			MWalletValidateServiceResopnse response = new MWalletValidateServiceResopnse();
			//RetrieveAllCustAcctServiceResponse allAccountServiceResponse = new RetrieveAllCustAcctServiceResponse();
			DAOContext daoContext = (DAOContext) workContext;
			Object[] args = daoContext.getArguments();

			MWalletValidateServiceRequest allAccountServiceRequest = (MWalletValidateServiceRequest) args[0];
			

			RetrieveMobileDetailsResponse bemResponse = (RetrieveMobileDetailsResponse) obj;
			
			//TODO code for response check 
			if(bemResponse != null)
			custAccntBasic = bemResponse.getCustomerAccountBasic();
			
			//TODO to apply null checks
			if(custAccntBasic != null) {
			custBasic = custAccntBasic.getCustomerBasic();			
			}
			
			if(custBasic !=  null) {
			name = custBasic.getCustomerName();
			}
			
			if(name != null) {
			response.setPayeeName(name.getFullName());
			}
			
			response=checkResHeader(bemResponse.getResponseHeader(), response);
			return response;
			
		}
		
		
		private MWalletValidateServiceResopnse checkResHeader(BEMResHeader resHeader,MWalletValidateServiceResopnse response) {
			String resCode = null;
			if(null != resHeader && null != resHeader.getServiceResStatus())
				resCode = resHeader.getServiceResStatus().getServiceResCode();
			
			if (ResponseCodeConstants.SUCCESS_RES_CODE.equals(resCode)) {
				response.setSuccess(true);	
				
		}

						
			if(ErrorCodeConstant.BUSINESS_ERROR.equals(resCode) && resHeader.getErrorList() != null && resHeader.getErrorList().length > 0){
				for (com.barclays.bem.BEMServiceHeader.Error error : resHeader
						.getErrorList()) {
					if("500".equals(error.getPPErrorCode())){
						response.setSuccess(false);
						response.setResCde(error.getPPErrorCode());
						response.setResMsg(error.getPPErrorDesc());
//						BMBDataAccessException dae = new BMBDataAccessException(error.getPPErrorCode(), error.getPPErrorDesc(), resHeader
//								.getServiceContext().getConsumerUniqueRefNo());
//							
//							throw dae;
						
					}
				}
			}

			return response;
		}

}
