/*
 * Copyright (c) 2013 Barclays Bank Plc, All Rights Reserved.
 *
 * This code is confidential to Barclays Bank Plc and shall not be disclosed
 * outside the Bank without the prior written permission of the Director of
 * CIO
 *
 * In the event that such disclosure is permitted the code shall not be copied
 * or distributed other than on a need-to-know basis and any recipients may be
 * required to sign a confidentiality undertaking in favour of Barclays Bank
 * Plc.
 */
/**
 * Package name is com.barclays.bmg.dao.operation.accountservices
 * /
 */
package com.barclays.bmg.dao.operation.accountservices;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.barclays.bem.BEMServiceHeader.BEMResHeader;
import com.barclays.bem.BEMServiceHeader.Error;
import com.barclays.bem.IndividualName.IndividualName;
import com.barclays.bem.RetrieveIndividualCustAcctBasic.AccountInfo_Type;
import com.barclays.bem.RetrieveIndividualCustAcctBasic.CustomerAccountBasic;
import com.barclays.bem.RetrieveIndividualCustAcctBasic.CustomerBasic;
import com.barclays.bem.RetrieveIndividualCustAcctBasic.PINInfo_Type;
import com.barclays.bem.RetrieveIndividualCustAcctBasic.RetrieveIndividualCustAcctBasicResponse;
import com.barclays.bmg.constants.AccountErrorCodeConstant;
import com.barclays.bmg.constants.AccountServiceResponseCodeConstant;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.constants.ErrorCodeConstant;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.CustomerDTO;
import com.barclays.bmg.exception.BMBDataAccessException;
import com.barclays.bmg.service.request.RetrieveAllCustAcctServiceRequest;
import com.barclays.bmg.service.response.RetrieveAllCustAcctServiceResponse;

/**
 * ****************** Revision History ********************** </br> Project Name is <b>bmg-acct-svc-bem-5.1.0</b> </br> The file name is
 * <b>RetrieveAllCustAcctResAdptOperation.java</b> </br> Description is <b>V 1.2</b> </br> Updated Date is <b>June 04, 2013</b> </br>
 * ******************************************************
 *
 * @author e20037686 </br> * The Class RetrieveAllCustAcctResAdptOperation created using JRE 1.6.0_10
 */
public class RetrieveAllCustAcctResAdptOperation {
    public static final String SOURCE_FCR = "FCR";
    public static final String SOURCE_PRIME = "PRIME";
    public static final String SOURCE_TELESTO = "BEM";

    // public static final String CURR = "TZS";

    public RetrieveAllCustAcctServiceResponse adaptResponse(WorkContext workContext, Object obj) throws Exception {
	RetrieveAllCustAcctServiceResponse allAccountServiceResponse = new RetrieveAllCustAcctServiceResponse();
	DAOContext daoContext = (DAOContext) workContext;
	Object[] args = daoContext.getArguments();

	RetrieveAllCustAcctServiceRequest allAccountServiceRequest = (RetrieveAllCustAcctServiceRequest) args[0];
	allAccountServiceResponse.setContext(allAccountServiceRequest.getContext());

	RetrieveIndividualCustAcctBasicResponse customerAccountListResponse = (RetrieveIndividualCustAcctBasicResponse) obj;

	// TODO remove later once service is up from BEM side.
	// customerAccountListResponse = new RetrieveIndividualCustAcctBasicResponse();

	List<CustomerAccountDTO> customerAccountList = new ArrayList<CustomerAccountDTO>();
	String respCode = checkServiceResponseHeader(customerAccountListResponse.getResponseHeader(), allAccountServiceResponse);

	// TODO remove later once service is up from BEM side.
	// respCode = "00000";

	if (respCode.equals(ErrorCodeConstant.SUCCESS_CODE) || respCode.equals(ErrorCodeConstant.PARTIAL_SUCCESS_CODE)) {
	    allAccountServiceResponse.setSuccess(true);
	    respCode = AccountErrorCodeConstant.SUCCESS_CODE;
	    CustomerAccountBasic custAccntBasic = customerAccountListResponse.getCustomerAccountBasic();
	    CustomerDTO custDto = new CustomerDTO();
	    if (custAccntBasic != null) {
		CustomerBasic custBasic = custAccntBasic.getCustomerBasic();
		if (custBasic != null) {
		    if (custBasic.getDateofBirth() != null) {
			custDto.setDOB(custBasic.getDateofBirth().getTime());
		    }
		    String temp = custBasic.getSCVID();
		    custDto.setCustomerID(temp != null ? temp : AuditConstant.WHITESPACE);
		    temp = custBasic.getLanguage();
		    custDto.setLanguage(temp != null ? temp : AuditConstant.WHITESPACE);
		    IndividualName indName = custBasic.getCustomerName();
		    if (indName != null) {
			temp = indName.getFullName();
			custDto.setFullName(temp != null ? temp : AuditConstant.WHITESPACE);
			custDto.setSurname1(indName.getLastName());
			custDto.setSalutation(indName.getSalutationTypeCode());
			custDto.setGivenName(indName.getFirstName());
		    }
		    String userStatus = custBasic.getMobileNumberStatus();
		    custDto.setUserStatusInMCE(userStatus);
		    custDto.setMobilePhone(custBasic.getMobileNumber());
		    //added for limited access flag: CR-35
		    custDto.setCustomerAccessStatusCode(custBasic.getCustomerAccessStatusCode());
		}
		PINInfo_Type pinType = custAccntBasic.getPinInfo();
		if (pinType != null) {
		    custDto.setPinStatus(pinType.getStatus());
		}
		AccountInfo_Type[] acctType = custAccntBasic.getAccountInfo();

		if (acctType != null && acctType.length > 0) {

		    for (AccountInfo_Type type : acctType) {
			CustomerAccountDTO dto = new CASAAccountDTO();
			Double bal = type.getAccountBalance();
			dto.setCurrentBalance(BigDecimal.valueOf(bal != null ? bal : 0));
			dto.setAccountNumber(type.getAccountNumber());
			dto.setBranchCode(type.getBranchCode());
			dto.setPriInd(type.getPrimaryIndicator());
			dto.setBankCif(type.getEntityCustomerId());
			if(type.getAccountIndicators()!=null && type.getAccountIndicators().length>=1)
				dto.setGroupWalletIndicator(type.getAccountIndicators()[0].getValue());
			// dto.setCurrentBookBalanceAmount(type.get)
			// dto.setCurrency(CURR);
			customerAccountList.add(dto);
		    }

		} else {
		    allAccountServiceResponse.setSuccess(false);
		    allAccountServiceResponse.setResCde(AccountServiceResponseCodeConstant.ACT_ACTSUMMARY_NOACTFORSUMMARY);
		    return allAccountServiceResponse;
		}
	    }
	    allAccountServiceResponse.setCustomer(custDto);
	    allAccountServiceResponse.setAccountList(customerAccountList);
	} else {
	    allAccountServiceResponse.setSuccess(false);
	}
	if (!ErrorCodeConstant.PARTIAL_SUCCESS_CODE.equals(respCode)) {
	    allAccountServiceResponse.setResCde(respCode);
	}
	return allAccountServiceResponse;
    }

    private String checkServiceResponseHeader(BEMResHeader resHeader, RetrieveAllCustAcctServiceResponse allAccountServiceResponse) throws Exception {
	String returnCode = "";

	// TODO remove later once service is up from BEM side.
	// resHeader = new BEMResHeader();

	if (resHeader.getServiceResStatus() != null) {
	    String resCode = resHeader.getServiceResStatus().getServiceResCode();

	    Error[] errorList = resHeader.getErrorList();

	    if (AccountErrorCodeConstant.SUCCESS_CODE.equals(resCode) || AccountErrorCodeConstant.PARTIAL_SUCCESS_CODE.equals(resCode)) {
		returnCode = resCode;
	    } else if (errorList != null) {
		for (Error error : errorList) {
		    throw new BMBDataAccessException(error.getErrorCode(), error.getErrorDesc());
		}
	    }
	}
	return returnCode;
    }
}