package com.barclays.bmg.mvc.controller.fundtransfer.external;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.ActivityIdConstantBean;
import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.FundTransferConstants;
import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.Amount;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.BranchLookUpDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.ExternalFundTransferDTO;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.billpayment.ExternalFundTransferCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.formvalidation.FormValidateOperation;
import com.barclays.bmg.operation.fundtransfer.external.ExternalFTFormSubmissionOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.ExternalFTFormSubmissionOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.FormValidateOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.ExternalFTFormSubmissionOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.FormValidateOperationResponse;
import com.barclays.bmg.service.lookup.impl.BranchLookUpServiceImpl;

public class PaymentFormSubmissionController extends BMBAbstractCommandController {

    private ExternalFTFormSubmissionOperation externalFTFormSubmissionOperation;
    private FormValidateOperation formValidateOperation;
    private BMBMultipleResponseJSONBuilder bmbJSONBuilder;
    private String activityId;
    private BranchLookUpServiceImpl branchLookUpService;

    public BranchLookUpServiceImpl getBranchLookUpService() {
		return branchLookUpService;
	}

	public void setBranchLookUpService(BranchLookUpServiceImpl branchLookUpService) {
		this.branchLookUpService = branchLookUpService;
	}

	@Override
    protected String getActivityId(Object command) {

	return activityId;
    }

    @Override
    protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest, HttpServletResponse httpResponse, Object command, BindException errors)
	    throws Exception {

	Context context = createContext(httpRequest);

	context.setActivityId(activityId);

	ExternalFundTransferCommand externalFTCommand = (ExternalFundTransferCommand) command;
	ExternalFundTransferDTO externalFundTransferDTO = (ExternalFundTransferDTO) getFromProcessMap(httpRequest,
		BMGProcessConstants.NON_BARCLAYS_FUND_TRANSFER, FundTransferConstants.FUND_TRANSFER_DTO);
	String businessId=context.getBusinessId();
	if(businessId.equalsIgnoreCase("GHBRB") && externalFTCommand.getTxnType().equals(FundTransferConstants.TXN_TYPE_FUND_TRANSFER_GHIPPS)){
		externalFundTransferDTO.setTxnType(externalFTCommand.getTxnType());
		context.setActivityId(ActivityIdConstantBean.GHIPPS_FUND_TRANSFER_OTHER_BANK_ACCOUNTS);
	}

	String swiftCode= null;
	if(businessId.equals("KEBRB")){
	List<BranchLookUpDTO> swiftDTO= branchLookUpService.getBankSwiftCode(externalFundTransferDTO.getBeneficiaryDTO().getDestinationBankCode(), externalFundTransferDTO.getBeneficiaryDTO().getDestinationBranchCode());

	if(swiftDTO!=null)
		swiftCode=	swiftDTO.get(0).getClearingCode();
	}
	FormValidateOperationRequest formValidateOperationRequest = new FormValidateOperationRequest();
	formValidateOperationRequest.setContext(context);
	makeRequest(formValidateOperationRequest, externalFTCommand, externalFundTransferDTO);
	FormValidateOperationResponse formValidateOperationResponse = formValidateOperation.validateForm(formValidateOperationRequest);

	ExternalFTFormSubmissionOperationRequest externalFTFormSubmissionOperationRequest = new ExternalFTFormSubmissionOperationRequest();

	externalFTFormSubmissionOperationRequest.setContext(context);

	makeRequest(externalFTFormSubmissionOperationRequest, externalFTCommand, externalFundTransferDTO, formValidateOperationResponse);
	if(businessId.equals("KEBRB"))
		externalFTFormSubmissionOperationRequest.getBeneficiaryDTO().setIntermediaryBankSwiftCode(swiftCode);
	ExternalFTFormSubmissionOperationResponse externalFTFormSubmissionOperationResponse = externalFTFormSubmissionOperation
		.validateForm(externalFTFormSubmissionOperationRequest);

	if (externalFTFormSubmissionOperationResponse != null && externalFTFormSubmissionOperationResponse.isSuccess()) {
	    String txnRefNo = externalFTFormSubmissionOperationRequest.getContext().getOrgRefNo();
	    externalFTFormSubmissionOperationResponse.setTxnRefNo(txnRefNo);
	    setResponseInProcessMap(httpRequest, externalFundTransferDTO, externalFTFormSubmissionOperationResponse, formValidateOperationResponse);
	    // clearSession(httpRequest,txnRefNo);
	    // setSessionAttribute(httpRequest, txnRefNo,externalFundTransferDTO);
	    // setSessionAttribute(httpRequest, BillPaymentConstants.TXN_REF_NO, txnRefNo);
	    setIntoProcessMap(httpRequest, BMGProcessConstants.NON_BARCLAYS_FUND_TRANSFER, FundTransferConstants.TXN_REF_NO, txnRefNo);
	    setIntoProcessMap(httpRequest, BMGProcessConstants.NON_BARCLAYS_FUND_TRANSFER, FundTransferConstants.FUND_TRANSFER_DTO,
		    externalFundTransferDTO);
	}
	return (BMBBaseResponseModel) bmbJSONBuilder.createMultipleJSONResponse(externalFTFormSubmissionOperationResponse,
		formValidateOperationResponse);
    }

    /*	*//**
     * @param httprequest
     * @param newTxnRefNo
     */
    /*
     * private void clearSession(HttpServletRequest httprequest, String newTxnRefNo){
     *
     * Map<String,Object> processMap = getProcessMapFromSession(httprequest);
     *
     * String oldTxnRefNo = (String) processMap.get(BillPaymentConstants.TXN_REF_NO); if(!StringUtils.isEmpty(oldTxnRefNo)){
     * removeSessionAttribute(httprequest, oldTxnRefNo); } setIntoProcessMap(httprequest, ProcessMapConstants.NON_BARCLAYS_FUND_TRANSFER,
     * BillPaymentConstants.TXN_REF_NO, newTxnRefNo); //setProcessMapIntoSession(httprequest, BillPaymentConstants.TXN_REF_NO, newTxnRefNo); }
     */

    private void makeRequest(FormValidateOperationRequest request, ExternalFundTransferCommand externalFTCommand,
	    ExternalFundTransferDTO externalFundTransferDTO) {
	request.setFrmAct(externalFundTransferDTO.getSourceAcct());
	Amount txnAmt = new Amount();
	txnAmt.setAmount(new BigDecimal(externalFTCommand.getTxnAmt()));
	txnAmt.setCurrency(externalFTCommand.getCurr());
	request.setTxnAmt(txnAmt);
	request.setTxnType(externalFundTransferDTO.getTxnType());
	externalFundTransferDTO.setTxnNot(externalFTCommand.getTxnNot());
    }

    /**
     * @param request
     * @param externalFTCommand
     * @param externalFundTransferDTO
     */
    private void makeRequest(ExternalFTFormSubmissionOperationRequest request, ExternalFundTransferCommand externalFTCommand,
	    ExternalFundTransferDTO externalFundTransferDTO, FormValidateOperationResponse formValidateOperationResponse) {
	Amount txnAmount = new Amount();
	txnAmount.setAmount(new BigDecimal(externalFTCommand.getTxnAmt()));
	txnAmount.setCurrency(externalFTCommand.getCurr());
	request.setTxnAmt(txnAmount);
	request.setBeneficiaryDTO(externalFundTransferDTO.getBeneficiaryDTO());
	request.setSourceAcct(externalFundTransferDTO.getSourceAcct());
	request.setChargeKey(externalFTCommand.getChDesc());
	request.setRem1(externalFTCommand.getRem1());
	request.setRem2(externalFTCommand.getRem2());
	request.setRem3(externalFTCommand.getRem3());
	request.setTxnNot(externalFTCommand.getTxnNot());
	request.setPayRson(externalFTCommand.getRsonOfPayment());
	request.setPayDtls(externalFTCommand.getPayDtls());
	BeneficiaryDTO bDTO= externalFundTransferDTO.getBeneficiaryDTO();
	if(bDTO!=null){
		String ano= bDTO.getDestinationAccountNumber();
		String accNo = null;
		String businessId=request.getContext().getBusinessId();
		if(ano!=null && businessId!=null && businessId.equals("MZBRB")){
			accNo = ano.substring(8, 19);
			bDTO.setDestinationAccountNumber(accNo);
		}
		bDTO.setNib(ano);
    }
	request.setBeneficiaryDTO(bDTO);
	request.setTxnTyp(externalFundTransferDTO.getTxnType());
	Map<String, String> chDescMap = externalFundTransferDTO.getChargeDesc();
	request.setCharDtls(chDescMap.get(externalFTCommand.getChDesc()));
	request.setAuthReq(formValidateOperationResponse.isScndLvlauthReq());
	request.setFxRateDTO(formValidateOperationResponse.getFxRateDTO());
	request.setAuthType(formValidateOperationResponse.getScndLvlAuthTyp());
	request.setCurr(externalFTCommand.getCurr());
	externalFundTransferDTO.setTxnNot(externalFTCommand.getTxnNot());
    }

    /**
     * @param httprequest
     * @param response
     *
     *            Set payment response in Process Map for next Execution Operation.
     */
    private void setResponseInProcessMap(HttpServletRequest httprequest, ExternalFundTransferDTO fundTransferDTO, ResponseContext... responses) {

	// As there is no process map to carry object setting it in session.
	ExternalFTFormSubmissionOperationResponse externalFTFormSubmissionOperationResponse = (ExternalFTFormSubmissionOperationResponse) responses[0];
	FormValidateOperationResponse formValidateOperationResponse = (FormValidateOperationResponse) responses[1];
	fundTransferDTO.setBeneficiaryDTO(externalFTFormSubmissionOperationResponse.getBeneficiaryDTO());
	fundTransferDTO.setSelChDesc(externalFTFormSubmissionOperationResponse.getChargeKey());
	fundTransferDTO.setSourceAcct(externalFTFormSubmissionOperationResponse.getSourceAcct());
	fundTransferDTO.setTxAmount(externalFTFormSubmissionOperationResponse.getTxnAmt());
	fundTransferDTO.setTxnNot(externalFTFormSubmissionOperationResponse.getTxnNot());
	fundTransferDTO.setFxRateDTO(externalFTFormSubmissionOperationResponse.getFxRateDTO());
	fundTransferDTO.setRem1(externalFTFormSubmissionOperationResponse.getRem1());
	fundTransferDTO.setRem2(externalFTFormSubmissionOperationResponse.getRem2());
	fundTransferDTO.setRem3(externalFTFormSubmissionOperationResponse.getRem3());
	fundTransferDTO.setPayRsonKey(externalFTFormSubmissionOperationResponse.getPayRson());
	fundTransferDTO.setPayRsonValue(externalFTFormSubmissionOperationResponse.getPayRsonValue());
	fundTransferDTO.setPayDtlsKey(externalFTFormSubmissionOperationResponse.getPayDtls());
	fundTransferDTO.setPayDtlsValue(externalFTFormSubmissionOperationResponse.getPayDtlsValue());
	fundTransferDTO.setTxnAmtInLCY(formValidateOperationResponse.getTxnAmtInLCY());
	setIntoProcessMap(httprequest, BMGProcessConstants.NON_BARCLAYS_FUND_TRANSFER, SessionConstant.SESSION_AUTH_REQUIRED,
		getStringValue(externalFTFormSubmissionOperationResponse.isAuthReq()));
	setIntoProcessMap(httprequest, BMGProcessConstants.NON_BARCLAYS_FUND_TRANSFER, SessionConstant.SESSION_AUTH_TYPE,
		externalFTFormSubmissionOperationResponse.getAuthType());
	/*
	 * setProcessMapIntoSession(httprequest,
	 * SessionConstant.SESSION_AUTH_REQUIRED,getStringValue(externalFTFormSubmissionOperationResponse.isAuthReq()));
	 * setProcessMapIntoSession(httprequest, SessionConstant.SESSION_AUTH_TYPE,externalFTFormSubmissionOperationResponse.getAuthType());
	 */// setSmsParams(httprequest,fundTransferDTO);
    }

    private String getStringValue(boolean val) {
	if (val == true) {
	    return "true";
	} else
	    return "false";
    }

    public ExternalFTFormSubmissionOperation getExternalFTFormSubmissionOperation() {
	return externalFTFormSubmissionOperation;
    }

    public void setExternalFTFormSubmissionOperation(ExternalFTFormSubmissionOperation externalFTFormSubmissionOperation) {
	this.externalFTFormSubmissionOperation = externalFTFormSubmissionOperation;
    }

    public BMBMultipleResponseJSONBuilder getBmbJSONBuilder() {
	return bmbJSONBuilder;
    }

    public void setBmbJSONBuilder(BMBMultipleResponseJSONBuilder bmbJSONBuilder) {
	this.bmbJSONBuilder = bmbJSONBuilder;
    }

    public String getActivityId() {
	return activityId;
    }

    public void setActivityId(String activityId) {
	this.activityId = activityId;
    }

    public FormValidateOperation getFormValidateOperation() {
	return formValidateOperation;
    }

    public void setFormValidateOperation(FormValidateOperation formValidateOperation) {
	this.formValidateOperation = formValidateOperation;
    }

}
