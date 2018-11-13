package com.barclays.bmg.mvc.controller.fundtransfer.own;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.ActivityIdConstantBean;
import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.CommonConstants;
import com.barclays.bmg.constants.FundTransferConstants;
import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.Amount;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.TransactionDTO;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.fundtransfer.own.FundTransferValidateCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.accounts.AccountSummaryOperation;
import com.barclays.bmg.operation.accounts.request.AccountSummaryOperationRequest;
import com.barclays.bmg.operation.accounts.response.AccountSummaryOperationResponse;
import com.barclays.bmg.operation.accountservices.GetSelectedAccountOperation;
import com.barclays.bmg.operation.formvalidation.FormValidateOperation;
import com.barclays.bmg.operation.formvalidation.OwnFundTransferRel1ValidateOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.FormValidateOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.GetSelectedAccountOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.own.OwnFundTransferRel1ValidateOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.FormValidateOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.GetSelectedAccountOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.own.OwnFundTransferRel1ValidateOperationResponse;
import com.barclays.bmg.utils.BMBCommonUtility;

public class OwnFundtransferValidateController extends BMBAbstractCommandController{

	private AccountSummaryOperation accountSummaryOperation;
	private GetSelectedAccountOperation getSelectedAccountOperation;
	private OwnFundTransferRel1ValidateOperation ownFundTransferRel1ValidateOperation;
	private FormValidateOperation formValidateOperation;
	private BMBMultipleResponseJSONBuilder bmbJSONBuilder;
	private boolean crossCurrencyAllowed = true;
	@Override
	protected String getActivityId(Object command) {

		return null;
	}

	@Override
	protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest,
			HttpServletResponse httpResponse, Object command, BindException errors)
			throws Exception {
		FundTransferValidateCommand fTValidateCommand = (FundTransferValidateCommand)command;

		// Get identifier for credit card from USSD Request
		String identifier = null;
		if (!StringUtils.isEmpty(fTValidateCommand.getIdentifier())) {
			identifier = fTValidateCommand.getIdentifier();
		}
		// Get Account Type from USSD Request
		String accountType = null;
		if (!StringUtils.isEmpty(fTValidateCommand.getAccountType())) {
		    accountType = fTValidateCommand.getAccountType();
		}

		// Get AccountID Param from USSD Request
		String activityIDParam = null;
		if (!StringUtils.isEmpty(fTValidateCommand.getActivityId())) {
		    activityIDParam = fTValidateCommand.getActivityId();
		}

		Context context = createContext(httpRequest);
		GetSelectedAccountOperationRequest getSelectedAccountOperationRequest = new GetSelectedAccountOperationRequest();
		AccountSummaryOperationRequest accountSummaryOperationRequest = new AccountSummaryOperationRequest();
		GetSelectedAccountOperationResponse selSourceAcctOpResp = new GetSelectedAccountOperationResponse();
		if("CreditCard".equals(identifier)){
			context.setActivityId(ActivityConstant.CREDIT_CARD_ATAGLANCE_ACTIVITY_ID);
			context.setActivityRefNo(BMBCommonUtility.generateRefNo());
			accountSummaryOperationRequest.setContext(context);
			clearCorrelationIds(httpRequest, BMGProcessConstants.CREDIT_PAYMENT);
			accountSummaryOperationRequest.setActivityIDParam(activityIDParam);
			accountSummaryOperationRequest.setAccountType(accountType);
			AccountSummaryOperationResponse accountSummaryOperationResponse = accountSummaryOperation
				.retrieveCreditCardList(accountSummaryOperationRequest);
			if (accountSummaryOperationResponse.isSuccess()) {
				List<? extends CustomerAccountDTO> acctList = accountSummaryOperationResponse
						.getAccountList();
				if (acctList != null && !acctList.isEmpty()) {
					for (CustomerAccountDTO acct : acctList) {
						if (acct instanceof CreditCardAccountDTO) {
							CreditCardAccountDTO selectedCreditCard = (CreditCardAccountDTO) acct;
							if (fTValidateCommand.getFrActNo().equals(selectedCreditCard.getPrimary().getCardNumber())) {
								selSourceAcctOpResp.setSelectedAcct(selectedCreditCard);

							}
						}

					}
				}
			}

		} else {

		context.setActivityId(ActivityIdConstantBean.FUND_TRANSFER_OWN_ACTIVITY_ID);
		getSelectedAccountOperationRequest.setContext(context);
		//set account map for ecrime purpose
		setEcrimeAccountMapToSession(httpRequest, BMGProcessConstants.OWN_AND_INTERNAL_FUND_TRANSFER);
		// Get Source Account
		getSelectedAccountOperationRequest.setAcctNumber(getAccountNumber(fTValidateCommand.getFrActNo(), httpRequest, BMGProcessConstants.OWN_AND_INTERNAL_FUND_TRANSFER));
		selSourceAcctOpResp = getSelectedAccountOperation.getSelectedSourceAccount(getSelectedAccountOperationRequest);
		}

		// Get Destination account
		context.setActivityId(ActivityIdConstantBean.FUND_TRANSFER_OWN_ACTIVITY_ID);
		getSelectedAccountOperationRequest.setContext(context);
		getSelectedAccountOperationRequest.setAcctNumber(getAccountNumber(fTValidateCommand.getToActNo(), httpRequest, BMGProcessConstants.OWN_AND_INTERNAL_FUND_TRANSFER));
		GetSelectedAccountOperationResponse selDestAcctOpResp = getSelectedAccountOperation.getSelectedDestinationAccount(getSelectedAccountOperationRequest);
		FormValidateOperationResponse formValidateOperationResponse = new FormValidateOperationResponse();
		OwnFundTransferRel1ValidateOperationResponse ownFundTransferRel1ValidateOperationResponse = null;
		// Form validation for Own fund transfer.
		if(selSourceAcctOpResp.isSuccess() && selDestAcctOpResp.isSuccess()){
			   HttpSession session = httpRequest.getSession(false);

		        if (session != null) {
		        	String serVersion = (String)session.getAttribute(SessionConstant.SESSION_BMG_SERVICE_VERSION);
		        	if(serVersion!=null && "1.0".equals(serVersion.trim()) && !isCrossCurrencyAllowed()){
		        		OwnFundTransferRel1ValidateOperationRequest ownFundTransferRel1ValidateOperationRequest = new OwnFundTransferRel1ValidateOperationRequest();
		        		ownFundTransferRel1ValidateOperationRequest.setContext(context);
		        		ownFundTransferRel1ValidateOperationRequest.setSrcAcct(selSourceAcctOpResp.getSelectedAcct());
		        		ownFundTransferRel1ValidateOperationRequest.setDestAcct(selDestAcctOpResp.getSelectedAcct());
		        		ownFundTransferRel1ValidateOperationResponse = ownFundTransferRel1ValidateOperation.validateAccounts(ownFundTransferRel1ValidateOperationRequest);
		        	}
		        }
			FormValidateOperationRequest formValidateOperationRequest = new FormValidateOperationRequest();
			formValidateOperationRequest.setContext(context);
			formValidateOperationRequest.setCreditCardFlag(identifier);
			formValidateOperationRequest.setFrmAct(selSourceAcctOpResp.getSelectedAcct());
			Amount txnAmount = new Amount();
			txnAmount.setAmount(new BigDecimal(fTValidateCommand.getTxnAmt()));
			txnAmount.setCurrency(selDestAcctOpResp.getSelectedAcct().getCurrency());
			formValidateOperationRequest.setTxnAmt(txnAmount);
			formValidateOperationRequest.setTxnType(FundTransferConstants.TXN_TYPE_OWN_FUND_TRANSFER);

			// Changed for Currency validation in Kenya

			if (CommonConstants.KEBRB_BUSINESS_ID.equals(context
					.getBusinessId()) || CommonConstants.AEBRB_BUSINESS_ID.equals(context.getBusinessId())) {

				formValidateOperation.currencyValidation(context,
						selDestAcctOpResp.getSelectedAcct().getCurrency(),
						FundTransferConstants.LIST_VAL_CURR_SUPPORT_OWN_ACC,
						selSourceAcctOpResp.getSelectedAcct().getCurrency(),
						selDestAcctOpResp.getSelectedAcct().getCurrency(),
						formValidateOperationResponse);
			}

			if (formValidateOperationResponse.isSuccess()) {
			formValidateOperationResponse = formValidateOperation.validateForm(formValidateOperationRequest);
			}

			if(formValidateOperationResponse.isSuccess()){
				setResponseInProcessMap(httpRequest, fTValidateCommand,selSourceAcctOpResp,selDestAcctOpResp,formValidateOperationResponse);
			}
		}

		Map<String, Object> contextMap = context.getContextMap();
		contextMap.put("txnNot", fTValidateCommand.getTxnNot());
		contextMap.put("txnDt", fTValidateCommand.getTxnDt());
		contextMap.put("frmAct", fTValidateCommand.getFrActNo());
		contextMap.put("toAct", fTValidateCommand.getToActNo());


		return (BMBBaseResponseModel)bmbJSONBuilder.createMultipleJSONResponse(selSourceAcctOpResp,selDestAcctOpResp,ownFundTransferRel1ValidateOperationResponse,formValidateOperationResponse);


	}

	private void setResponseInProcessMap(HttpServletRequest httpRequest,Object command,ResponseContext... responseContexts){

		GetSelectedAccountOperationResponse selSourceAcctOpResp = (GetSelectedAccountOperationResponse)responseContexts[0];
		GetSelectedAccountOperationResponse selDestAcctOpResp = (GetSelectedAccountOperationResponse)responseContexts[1];
		FormValidateOperationResponse formValidateOperationResponse = (FormValidateOperationResponse) responseContexts[2];
		FundTransferValidateCommand fTValidateCommand = (FundTransferValidateCommand)command;
		TransactionDTO transactionDTO = new TransactionDTO();
		transactionDTO.setTxnType(FundTransferConstants.TXN_TYPE_OWN_FUND_TRANSFER);
		transactionDTO.setSourceAcct(selSourceAcctOpResp.getSelectedAcct());
		CustomerAccountDTO beneAcct = selDestAcctOpResp.getSelectedAcct();
		BeneficiaryDTO beneficiaryDTO = new BeneficiaryDTO();
		beneficiaryDTO.setDestinationAccount(beneAcct);
		beneficiaryDTO.setDestinationAccountNumber(beneAcct.getAccountNumber());
		beneficiaryDTO.setDestinationBranchCode(beneAcct.getBranchCode());
		beneficiaryDTO.setCurrency(beneAcct.getCurrency());
		beneficiaryDTO.setBeneficiaryName(selDestAcctOpResp.getContext().getFullName());
		transactionDTO.setBeneficiaryDTO(beneficiaryDTO);
		transactionDTO.setFxRateDTO(formValidateOperationResponse.getFxRateDTO());
		transactionDTO.setTxnAmt(formValidateOperationResponse.getTxnAmt());
		transactionDTO.setTxnNot(fTValidateCommand.getTxnNot());
		transactionDTO.setScndLvlauthReq(formValidateOperationResponse.isScndLvlauthReq());
		transactionDTO.setScndLvlAuthTyp(formValidateOperationResponse.getScndLvlAuthTyp());
		transactionDTO.setTxnAmtInLCY(formValidateOperationResponse.getTxnAmtInLCY());
		setIntoProcessMap(httpRequest, BMGProcessConstants.OWN_AND_INTERNAL_FUND_TRANSFER, FundTransferConstants.TRANSACTION_DTO, transactionDTO);
		setIntoProcessMap(httpRequest, BMGProcessConstants.OWN_AND_INTERNAL_FUND_TRANSFER, FundTransferConstants.TXN_REF_NO,  formValidateOperationResponse.getContext().getOrgRefNo());

	}

	public GetSelectedAccountOperation getGetSelectedAccountOperation() {
		return getSelectedAccountOperation;
	}

	public void setGetSelectedAccountOperation(
			GetSelectedAccountOperation getSelectedAccountOperation) {
		this.getSelectedAccountOperation = getSelectedAccountOperation;
	}

	public FormValidateOperation getFormValidateOperation() {
		return formValidateOperation;
	}

	public void setFormValidateOperation(FormValidateOperation formValidateOperation) {
		this.formValidateOperation = formValidateOperation;
	}

	public BMBMultipleResponseJSONBuilder getBmbJSONBuilder() {
		return bmbJSONBuilder;
	}

	public void setBmbJSONBuilder(BMBMultipleResponseJSONBuilder bmbJSONBuilder) {
		this.bmbJSONBuilder = bmbJSONBuilder;
	}

	public OwnFundTransferRel1ValidateOperation getOwnFundTransferRel1ValidateOperation() {
		return ownFundTransferRel1ValidateOperation;
	}

	public void setOwnFundTransferRel1ValidateOperation(
			OwnFundTransferRel1ValidateOperation ownFundTransferRel1ValidateOperation) {
		this.ownFundTransferRel1ValidateOperation = ownFundTransferRel1ValidateOperation;
	}

	public boolean isCrossCurrencyAllowed() {
		return crossCurrencyAllowed;
	}

	public void setCrossCurrencyAllowed(boolean crossCurrencyAllowed) {
		this.crossCurrencyAllowed = crossCurrencyAllowed;
	}

	public void setAccountSummaryOperation(AccountSummaryOperation accountSummaryOperation) {
		this.accountSummaryOperation = accountSummaryOperation;
	}

}
