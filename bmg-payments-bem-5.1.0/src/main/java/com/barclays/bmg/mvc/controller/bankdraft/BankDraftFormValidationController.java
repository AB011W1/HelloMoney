package com.barclays.bmg.mvc.controller.bankdraft;

import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.FundTransferConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.Amount;
import com.barclays.bmg.dto.BankDraftTransactionDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.FxRateDTO;
import com.barclays.bmg.json.model.AmountJSONModel;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.bankdraft.BankDraftFormInputCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.formvalidation.FormValidateOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.FormValidateOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.FormValidateOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.GetSelectedAccountOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrievePayeeInfoOperationResponse;
import com.barclays.bmg.utils.BMGFormatUtility;

public class BankDraftFormValidationController extends
		BMBAbstractCommandController {

	private FormValidateOperation formValidateOperation;
	private BMBMultipleResponseJSONBuilder bmbJSONBuilder;
	private String activityId;

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public BMBMultipleResponseJSONBuilder getBmbJSONBuilder() {
		return bmbJSONBuilder;
	}

	public void setBmbJSONBuilder(BMBMultipleResponseJSONBuilder bmbJSONBuilder) {
		this.bmbJSONBuilder = bmbJSONBuilder;
	}

	public FormValidateOperation getFormValidateOperation() {
		return formValidateOperation;
	}

	public void setFormValidateOperation(
			FormValidateOperation formValidateOperation) {
		this.formValidateOperation = formValidateOperation;
	}

	@Override
	protected String getActivityId(Object command) {
		return activityId;
	}

	@Override
	protected BMBBaseResponseModel handle1(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {

		Context context = createContext(request);
		context.setActivityId(getActivityId());

		BankDraftTransactionDTO transactionDTO = (BankDraftTransactionDTO) getFromProcessMap(
				request, BMGProcessConstants.BANK_DRAFT_PROCESS_KEY,
				FundTransferConstants.BANK_DRAFT_DTO);

		BankDraftFormInputCommand bankDraftCommand = (BankDraftFormInputCommand) command;

		// Get Source Account
		CustomerAccountDTO selSourceAcct = transactionDTO.getSourceAcct();
		FormValidateOperationResponse formValidateOperationResponse = null;
		FormValidateOperationRequest formValidateOperationRequest = new FormValidateOperationRequest();
		formValidateOperationRequest.setContext(context);
		formValidateOperationRequest.setFrmAct(selSourceAcct);
		Amount txnAmount = new Amount();
		txnAmount.setAmount(new BigDecimal(bankDraftCommand.getTxnAmt()));
		txnAmount.setCurrency(bankDraftCommand.getCurr());
		formValidateOperationRequest.setTxnAmt(txnAmount);
		formValidateOperationRequest.setTxnType(transactionDTO.getTxnType());

		formValidateOperationResponse = formValidateOperation
				.validateForm(formValidateOperationRequest);

		if (formValidateOperationResponse.isSuccess()) {
			// FIXME

			transactionDTO.setTxnAmt(txnAmount);
			transactionDTO.setTxnNot(bankDraftCommand.getTxnNot());
			transactionDTO.setDeliveryType(bankDraftCommand.getDelTypSel());
			transactionDTO.setDraftType(transactionDTO.getTxnType());
			transactionDTO.setScndLvlauthReq(formValidateOperationResponse
					.isScndLvlauthReq());
			transactionDTO.setScndLvlAuthTyp(formValidateOperationResponse
					.getScndLvlAuthTyp());
			transactionDTO.setFxRateDTO(formValidateOperationResponse.getFxRateDTO());
			transactionDTO.setTxnAmtInLCY(formValidateOperationResponse.getTxnAmtInLCY());
			transactionDTO.setBranchCode(bankDraftCommand.getBrnCde());
			transactionDTO.setBranchName(bankDraftCommand.getBrnNme());
			setIntoProcessMap(request,
					BMGProcessConstants.BANK_DRAFT_PROCESS_KEY,
					FundTransferConstants.BANK_DRAFT_DTO, transactionDTO);

		}

		GetSelectedAccountOperationResponse getSelectedAccountOperationResponse = new GetSelectedAccountOperationResponse();
		getSelectedAccountOperationResponse.setContext(context);
		getSelectedAccountOperationResponse.setSelectedAcct(transactionDTO
				.getSourceAcct());

		RetrievePayeeInfoOperationResponse retrievePayeeInfoOperationResponse = new RetrievePayeeInfoOperationResponse();
		retrievePayeeInfoOperationResponse.setContext(context);
		retrievePayeeInfoOperationResponse.setBeneficiaryDTO(transactionDTO
				.getBeneficiaryDTO());

		AmountJSONModel txnJSONAmt = new AmountJSONModel();
		txnJSONAmt.setAmt(BMGFormatUtility.getFormattedAmount(txnAmount
				.getAmount()));
		txnJSONAmt.setCurr(txnAmount.getCurrency());

		FxRateDTO fxRateDTO = formValidateOperationResponse.getFxRateDTO();

		Map<String, Object> contextMap = context.getContextMap();

		if (fxRateDTO != null) {
			AmountJSONModel eqAmtJSONAmt = new AmountJSONModel();
			eqAmtJSONAmt.setAmt(BMGFormatUtility.getFormattedAmount(fxRateDTO
					.getEquivalentAmount()));
			eqAmtJSONAmt.setCurr(selSourceAcct.getCurrency());
			contextMap.put("fxRate", fxRateDTO.getEffectiveFXRate().toString());
			contextMap.put("eqAmtJSONAmt", eqAmtJSONAmt);
		}

		contextMap.put("txnAmount", txnJSONAmt);
		contextMap.put("delTypSel", bankDraftCommand.getDelTypSel());
		contextMap.put("payAt", bankDraftCommand.getPayAt());
		contextMap.put("brnNam", bankDraftCommand.getBrnNme());
		contextMap.put("brnCde", bankDraftCommand.getBrnCde());
		contextMap.put("payRemarks", bankDraftCommand.getTxnNot());

		BMBPayload bmbPayload = (BMBPayload) bmbJSONBuilder
				.createMultipleJSONResponse(
						getSelectedAccountOperationResponse,
						retrievePayeeInfoOperationResponse,
						formValidateOperationResponse);

		return bmbPayload;
	}

}
