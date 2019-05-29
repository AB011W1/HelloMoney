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
package com.barclays.bmg.mvc.controller.accountdetails;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.CustomerDTO;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.accountdetails.AtAGlanceCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.SessionActivityOperation;
import com.barclays.bmg.operation.accountdetails.RetrieveAllCustAcctOperation;
import com.barclays.bmg.operation.accounts.AccountSummaryOperation;
import com.barclays.bmg.operation.accounts.request.AccountSummaryOperationRequest;
import com.barclays.bmg.operation.accounts.response.AccountSummaryOperationResponse;
import com.barclays.bmg.operation.request.RetrieveAllCustAcctOperationRequest;
import com.barclays.bmg.operation.request.SessionSummaryOperationRequest;
import com.barclays.bmg.operation.response.RetrieveAllCustAcctOperationResponse;
import com.barclays.bmg.utils.BMBCommonUtility;

/**
 * ****************** Revision History ********************** </br> Project Name is <b>bmg-acct-svc-bem-5.1.0</b> </br> The file name is
 * <b>RetrieveAllCustAcctController.java</b> </br> Description is <b>V 1.2</b> </br> Updated Date is <b>June 18, 2013</b> </br>
 * ******************************************************
 *
 * @author e20037686 </br> * The Class RetrieveAllCustAcctController created using JRE 1.6.0_10
 */
public class RetrieveAllCustAcctController extends BMBAbstractCommandController {
    /**
     * The instance variable named "retrieveAllCustAcctOperation" is created.
     */
    private RetrieveAllCustAcctOperation retrieveAllCustAcctOperation;
    private AccountSummaryOperation accountSummaryOperation;

    /**
     * @return the accountSummaryOperation
     */
    public AccountSummaryOperation getAccountSummaryOperation() {
	return accountSummaryOperation;
    }

    /**
	 *
	 */
    public void setAccountSummaryOperation(AccountSummaryOperation accountSummaryOperation) {
	this.accountSummaryOperation = accountSummaryOperation;
    }

    /**
     * The instance variable named "bmbJsonBuilder" is created.
     */
    private BMBJSONBuilder bmbJsonBuilder;
    private SessionActivityOperation sessionActivityOperation;
    private static final String OPCD = "OP0202";
    private static final String OPCDSTR = "opCde";

    /*
     * (non-Javadoc)
     *
     * @see com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController#handle1 (javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.validation.BindException)
     */
    @Override
    protected BMBBaseResponseModel handle1(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)
	    throws Exception {

	AtAGlanceCommand atAGlanceCommand = (AtAGlanceCommand) command;

	String accountType = null;
	if (!StringUtils.isEmpty(atAGlanceCommand.getAccountType())) {
	    accountType = atAGlanceCommand.getAccountType();
	}

	Context context = createContext(request);
	//Added as to remove extra CAS call-Prod issue
	if (OPCD.equals(request.getParameter(OPCDSTR))){
	context.setOpCde(OPCD);
	}
	//Added as to remove extra CAS call-Prod issue End
	//Forgot Pin Change
	if(context.getMobilePhone()==null){
		context.setMobilePhone(context.getLoginId());
	}
	RetrieveAllCustAcctOperationRequest opReq = makeRequest(request, context);
	RetrieveAllCustAcctOperationResponse opRes = retrieveAllCustAcctOperation.retrieveAllCustAccount(opReq);
	List<? extends CustomerAccountDTO> accList = null;
	if (opRes != null) {
	    boolean isSuccess = opRes.isSuccess();
	    if (isSuccess) {
		if (OPCD.equals(request.getParameter(OPCDSTR))) {
		    CustomerDTO custDto = opRes.getCustomer();
		    if (custDto != null && opRes.getContext() != null) {
			setUserMapIntoSession(request, SessionConstant.SESSION_LANGUAGE_ID, custDto.getLanguage());
			setUserMapIntoSession(request, SessionConstant.SESSION_FULL_NAME, custDto.getFullName());
			setUserMapIntoSession(request, SessionConstant.SESSION_CUSTOMER_DTO, custDto);
			setUserMapIntoSession(request, SessionConstant.SESSION_CUSTOMER_ID, custDto.getCustomerID());
			setUserMapIntoSession(request, SessionConstant.SESSION_USER_ID, context.getMobilePhone());
			setUserMapIntoSession(request, SessionConstant.SESSION_PIN_STATUS, custDto.getPinStatus());
			// setUserMapIntoSession(request, SessionConstant.SESSION_LOCAL_CURRENCY, CURR);
			// setUserMapIntoSession(request, SessionConstant.SESSION_USR_STATUS, custDto.getU());
			setUserMapIntoSession(request, SessionConstant.SESSION_PP_MAP, opRes.getContext().getPpMap());

			//Changes for caching of account list & reduce one call to enhance performance
			setUserMapIntoSession(request, SessionConstant.SESSION_ACCOUNT_LIST+context.getSessionId(), opRes.getAccountList());
			accList=opRes.getAccountList();

		    }
		    SessionSummaryOperationRequest seOperationRequest = new SessionSummaryOperationRequest();
		    seOperationRequest.setContext(createContext(request));
		    sessionActivityOperation.persistLoginInformation(seOperationRequest);
		}

		AccountSummaryOperationRequest accountSummaryOperationRequest = makeRequest(request);
		accountSummaryOperationRequest.setAccountType(accountType);
		AccountSummaryOperationResponse acntSmryOpRes = accountSummaryOperation.retrieveAllAccount(accountSummaryOperationRequest);
		if (acntSmryOpRes != null) {
		    opRes.setAccountList(acntSmryOpRes.getAccountList());
		    opRes.setResCde(acntSmryOpRes.getResCde());
		    opRes.setSuccess(acntSmryOpRes.isSuccess());
		    opRes.setResMsg(acntSmryOpRes.getResMsg());
		    opRes.setErrTyp(acntSmryOpRes.getErrTyp());
		}

		List<? extends CustomerAccountDTO> newAccList=opRes.getAccountList();
		if(null != accList){
			for(CustomerAccountDTO dto : accList){
				for(CustomerAccountDTO ndto:newAccList){
					if(dto.getAccountNumber().equals(ndto.getAccountNumber())){
						ndto.setBankCif(dto.getBankCif());
						ndto.setGroupWalletIndicator(dto.getGroupWalletIndicator());
					}
				}
			}
		}

		clearCorrelationIds(request, BMGProcessConstants.ACCOUNTS_PROCESS);
		mapCorrelationIds(opRes.getAccountList(), opReq, request, opRes, BMGProcessConstants.ACCOUNTS_PROCESS);
	    }
	}
	return (BMBBaseResponseModel) bmbJsonBuilder.createJSONResponse(opRes);
    }

    /**
     * The method is written for Make request.
     *
     * @param request
     *            the request
     * @param context
     *            the context
     * @return the RetrieveAllCustAcctOperationRequest's object
     */
    private RetrieveAllCustAcctOperationRequest makeRequest(HttpServletRequest request, Context context) {
	RetrieveAllCustAcctOperationRequest opReq = new RetrieveAllCustAcctOperationRequest();
	context.setActivityId(ActivityConstant.CASA_DETAIL_ACTIVITY_ID);
	opReq.setContext(context);
	return opReq;
    }

    private AccountSummaryOperationRequest makeRequest(HttpServletRequest request) {
	AccountSummaryOperationRequest accountSummaryOperationRequest = new AccountSummaryOperationRequest();
	Context context = createContext(request);
	context.setActivityId(ActivityConstant.ACCOUNT_SUMMARY_ACTIVITY_ID);
	context.setActivityRefNo(BMBCommonUtility.generateRefNo());
	accountSummaryOperationRequest.setContext(context);
	return accountSummaryOperationRequest;
    }

    /**
     * Gets the retrieve all cust acct operation.
     *
     * @return the RetrieveAllCustAcctOperation
     */
    public RetrieveAllCustAcctOperation getRetrieveAllCustAcctOperation() {
	return retrieveAllCustAcctOperation;
    }

    /**
     * Sets values for RetrieveAllCustAcctOperation.
     *
     * @param retrieveAllCustAcctOperation
     *            the retrieve all cust acct operation
     */
    public void setRetrieveAllCustAcctOperation(RetrieveAllCustAcctOperation retrieveAllCustAcctOperation) {
	this.retrieveAllCustAcctOperation = retrieveAllCustAcctOperation;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.barclays.bmg.mvc.controller.auth.BMBAbstractController#getActivityId()
     */

    @Override
    protected String getActivityId(Object command) {
	// TODO Auto-generated method stub
	return null;
    }

    /**
     * Gets the bmb json builder.
     *
     * @return the BmbJsonBuilder
     */
    public BMBJSONBuilder getBmbJsonBuilder() {
	return bmbJsonBuilder;
    }

    /**
     * Sets values for BmbJsonBuilder.
     *
     * @param bmbJsonBuilder
     *            the bmb json builder
     */
    public void setBmbJsonBuilder(BMBJSONBuilder bmbJsonBuilder) {
	this.bmbJsonBuilder = bmbJsonBuilder;
    }

    /**
     * @return the sessionActivityOperation
     */
    public SessionActivityOperation getSessionActivityOperation() {
	return sessionActivityOperation;
    }

    /**
	 *
	 */
    public void setSessionActivityOperation(SessionActivityOperation sessionActivityOperation) {
	this.sessionActivityOperation = sessionActivityOperation;
    }
}