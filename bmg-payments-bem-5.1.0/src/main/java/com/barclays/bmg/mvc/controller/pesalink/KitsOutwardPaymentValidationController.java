
package com.barclays.bmg.mvc.controller.pesalink;


import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;

import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.Amount;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.TransactionDTO;
import com.barclays.bmg.json.model.builder.BMBCommonJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.pesalink.KitsOutwardPaymentValidationCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.accountservices.GetSelectedAccountOperation;
import com.barclays.bmg.operation.formvalidation.BillPayAmountValidationOperation;
import com.barclays.bmg.operation.formvalidation.FormValidateOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.BillPayAmountValidationOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.FormValidateOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.GetSelectedAccountOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.BillPayAmountValidationOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.FormValidateOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.GetSelectedAccountOperationResponse;

public class KitsOutwardPaymentValidationController extends BMBAbstractCommandController{

	                private GetSelectedAccountOperation selectedAccountOperation;
	                private BillPayAmountValidationOperation billPayAmountValidationOperation;
	                private FormValidateOperation formValidateOperation;
	                private BMBJSONBuilder bmbJSONBuilder;


	                @Override
	                protected String getActivityId(Object command) {

	                                return null;
	                }

	                @Override
	                protected BMBBaseResponseModel handle1(HttpServletRequest request,
	                                                HttpServletResponse response, Object command, BindException errors)
	                                                throws Exception {

	                	KitsOutwardPaymentValidationCommand kitsValidateCommand = (KitsOutwardPaymentValidationCommand)command;

	                                String activityId = (String) getFromProcessMap(request,
	                                                                BMGProcessConstants.KITS_PAYMENT,
	                                                                "KITS_BILLPAYMENT_PTA");
	                                String txnType = (String) getFromProcessMap(request,
	                                                                BMGProcessConstants.KITS_PAYMENT,
	                                                                "PTA");

	                                Context context = createContext(request);
	                                context.setActivityId(activityId);

	                                BeneficiaryDTO beneficiaryDTO = new BeneficiaryDTO();
	                                //Updated on 30/10/2016 G01022861
	                                beneficiaryDTO.setBillerCategoryName(BMGProcessConstants.KITS_BILLER_ORGANIGATIONCODE_CATEGORYID);
	                                beneficiaryDTO.setBillerId(BMGProcessConstants.KITS_BILLER_ORGANIGATIONCODE_BILLERID);
	                                //Ended 03/10/2016
	                                //beneficiaryDTO.setNarration(kitsValidateCommand.getPayRsn());
	                                beneficiaryDTO.setBillRefNo(kitsValidateCommand.getRefNo());
	                                beneficiaryDTO.setDestinationBankName(kitsValidateCommand.getBnkNm());
	                               // beneficiaryDTO.setDestinationBankSortCode(kitsValidateCommand.getBnkSrtCd());
	                                beneficiaryDTO.setDestinationAccountNumber(kitsValidateCommand.getRefNo());

	                                GetSelectedAccountOperationRequest getSelectedAccountOperationRequest = new GetSelectedAccountOperationRequest();
	                                getSelectedAccountOperationRequest.setContext(context);

	                                // Get Source Account
	                                getSelectedAccountOperationRequest.setAcctNumber(getAccountNumber(kitsValidateCommand.getFrActNo(), request, BMGProcessConstants.KITS_PAYMENT));
	                                GetSelectedAccountOperationResponse selSourceAcctOpResp = selectedAccountOperation.getSelectedSourceAccount(getSelectedAccountOperationRequest);

	                                Amount txnAmount = new Amount();
	                                txnAmount.setAmount(new BigDecimal(kitsValidateCommand.getAmt()));
	                                if(!StringUtils.isEmpty(kitsValidateCommand.getCurr())){
	                                                txnAmount.setCurrency(kitsValidateCommand.getCurr());
	                                }else{
	                                                txnAmount.setCurrency(context.getLocalCurrency());
	                                }

	                                // Bill Payment validation.
	                                BillPayAmountValidationOperationRequest billPayAmountValidationOperationRequest = new BillPayAmountValidationOperationRequest();
	                                billPayAmountValidationOperationRequest.setContext(context);
	                                billPayAmountValidationOperationRequest.setBeneficiaryDTO(beneficiaryDTO);

	                                billPayAmountValidationOperationRequest.setTxnAmount(txnAmount);
	                                billPayAmountValidationOperationRequest.setTxnType(txnType);
	                                BillPayAmountValidationOperationResponse billAmountValidationOperationResponse =
	                                                                                billPayAmountValidationOperation.validateTxnAmount(billPayAmountValidationOperationRequest);
	                                FormValidateOperationResponse formValidateOperationResponse = null;
	                                // Validate the form.
	                                if(selSourceAcctOpResp.isSuccess()){
	                                                FormValidateOperationRequest formValidateOperationRequest = new FormValidateOperationRequest();
	                                                formValidateOperationRequest.setContext(context);
	                                                formValidateOperationRequest.setFrmAct(selSourceAcctOpResp.getSelectedAcct());
	                                                formValidateOperationRequest.setTxnType(txnType);
	                                                //formValidateOperationRequest.setBeneficiaryDTO(beneficiaryDTO);
	                                                formValidateOperationRequest.setTxnAmt(txnAmount);
	                                                formValidateOperationResponse = formValidateOperation.validateForm(formValidateOperationRequest);
	                                                formValidateOperationResponse.setTxnTyp(txnType);
	                                }

	                                if(checkAllOperationResponses(selSourceAcctOpResp,billAmountValidationOperationResponse,formValidateOperationResponse)){
	                                                setResponseInProcessMap(request,beneficiaryDTO,txnType, selSourceAcctOpResp,formValidateOperationResponse);
	                                }

	                                setTxnTypeInResponses(txnType,selSourceAcctOpResp,billAmountValidationOperationResponse,formValidateOperationResponse);

	                                //return (BMBBaseResponseModel) bmbJSONBuilder.createMultipleJSONResponse(selSourceAcctOpResp,billAmountValidationOperationResponse,formValidateOperationResponse);
	                                return (BMBBaseResponseModel) bmbJSONBuilder.createJSONResponse(formValidateOperationResponse);
	                }


	                private void setResponseInProcessMap(HttpServletRequest httpRequest ,BeneficiaryDTO beneficiaryDTO,String txnType,ResponseContext... responseContexts){

	                                GetSelectedAccountOperationResponse selSourceAcctOpResp = (GetSelectedAccountOperationResponse)responseContexts[0];
	                                FormValidateOperationResponse formValidateOperationResponse = (FormValidateOperationResponse) responseContexts[1];
	                                String txnRefNo = formValidateOperationResponse.getContext().getOrgRefNo();
	                                TransactionDTO transactionDTO=new TransactionDTO();
	                                transactionDTO.setSourceAcct(selSourceAcctOpResp.getSelectedAcct());
//	                            transactionDTO.setFxRateDTO(formValidateOperationResponse.getFxRateDTO());
	                                transactionDTO.setTxnAmt(formValidateOperationResponse.getTxnAmt());
	                                transactionDTO.setTxnType(txnType);
	                                transactionDTO.setScndLvlauthReq(formValidateOperationResponse.isScndLvlauthReq());
	                                transactionDTO.setScndLvlAuthTyp(formValidateOperationResponse.getScndLvlAuthTyp());
	                                transactionDTO.setTxnAmtInLCY(formValidateOperationResponse.getTxnAmtInLCY());
	                                //--- Fix for Kenya UBP Charging
//	                            transactionDTO.setTransFee(formValidateOperationResponse.getTranFee());
	                                transactionDTO.setBeneficiaryDTO(beneficiaryDTO);

	                                setIntoProcessMap(httpRequest, BMGProcessConstants.KITS_PAYMENT, BillPaymentConstants.TRANSACTION_DTO, transactionDTO);
	                                setIntoProcessMap(httpRequest, BMGProcessConstants.KITS_PAYMENT, BillPaymentConstants.TXN_REF_NO,  txnRefNo);
	                }


	                private void setTxnTypeInResponses(String txnType, ResponseContext... responses){
	                                if(responses!=null){
	                                                for(ResponseContext response: responses){
	                                                                if(response!=null){
	                                                                                response.setTxnTyp(txnType);
	                                                                }
	                                                }
	                                }
	                }



	                /**
	                * @param formValidateOperation the formValidateOperation to set
	                */
	                public void setFormValidateOperation(FormValidateOperation formValidateOperation) {
	                                this.formValidateOperation = formValidateOperation;
	                }


	                /**
	                * @return the formValidateOperation
	                */
	                public FormValidateOperation getFormValidateOperation() {
	                                return formValidateOperation;
	                }

	                public GetSelectedAccountOperation getSelectedAccountOperation() {
	                                return selectedAccountOperation;
	                }


	                public void setSelectedAccountOperation(
	                                                GetSelectedAccountOperation selectedAccountOperation) {
	                                this.selectedAccountOperation = selectedAccountOperation;
	                }


	                public BillPayAmountValidationOperation getBillPayAmountValidationOperation() {
	                                return billPayAmountValidationOperation;
	                }

	                public void setBillPayAmountValidationOperation(
	                                                BillPayAmountValidationOperation billPayAmountValidationOperation) {
	                                this.billPayAmountValidationOperation = billPayAmountValidationOperation;
	                }

	                public BMBJSONBuilder getBmbJSONBuilder() {
	                                return bmbJSONBuilder;
	                }

	                public void setBmbJSONBuilder(BMBJSONBuilder bmbJSONBuilder) {
	                                this.bmbJSONBuilder = bmbJSONBuilder;
	                }


	}



