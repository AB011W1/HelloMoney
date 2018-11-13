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
 * Package name is com.barclays.bmg.json.model.builder.airtimetopup
 * /
 */
package com.barclays.bmg.json.model.builder.airtimetopup;

import java.math.BigDecimal;
import java.util.List;

import com.barclays.bmg.constants.AccountConstants;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BillerDTO;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.json.model.CasaAccountJSONModel;
import com.barclays.bmg.json.model.CreditCardAccountJSONModel;
import com.barclays.bmg.json.model.billpayment.BillerJSONModel;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadData;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.AirTimeTopUpValidateServiceResopnse;
import com.barclays.bmg.operation.response.fundtransfer.external.FormValidateOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.GetSelectedAccountOperationResponse;
import com.barclays.bmg.service.response.AirTimeTopUpValidateResponse;
import com.barclays.bmg.utils.BMGFormatUtility;

/**
 * ****************** Revision History **********************
 * </br>
 * Project Name  is  <b>bmg-response-model-2.0</b>
 * </br>
 * The file name is  <b>AirTimeTopUpValidateJSONBldr.java</b>
 * </br>
 * Description  is  <b>Initial Version</b>
 * </br>
 * Updated Date  is  <b>May 17, 2013</b>
 * </br>
 * ******************************************************
 *
 * @author e20037686
 * </br>
 * * The Class AirTimeTopUpValidateJSONBldr created using JRE 1.6.0_10
 */
public class AirTimeTopUpValidateJSONBldr extends BMBMultipleResponseJSONBuilder
{

	/**
	 * The Constant named "tmp_i" is created.
	 */
	private final static Integer tmp_i = 0;

	/**
	 * The Constant named "zero_0" is created.
	 */
	public final static BigDecimal zero_0 = new BigDecimal(tmp_i);

	/* (non-Javadoc)
	 * @see com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder#createMultipleJSONResponse(com.barclays.bmg.context.ResponseContext[])
	 */
	@Override
	public Object createMultipleJSONResponse(
			ResponseContext... responseContexts) {
		BMBPayloadHeader bmbPayloadHeader = null;
		BMBPayload bmbPayload = null;
		BMBPayloadData bmbPayloadData = null;
		for(ResponseContext response: responseContexts){
			if(response!=null && !response.isSuccess()){
				bmbPayloadHeader = createHeader(response);
			}
		}
		if(bmbPayloadHeader!=null)
		{
			bmbPayload = new BMBPayload(bmbPayloadHeader);
			bmbPayload.setPayData(bmbPayloadData);
		}else{
			bmbPayload = new BMBPayload(createHeader(responseContexts[0]));
			populatePayLoad(bmbPayload, responseContexts);
		}

		return bmbPayload;
	}

	/**
	 * The method is written for Creates the header.
	 *
	 * @param response the response
	 * @return the BMBPayloadHeader's object
	 */
	protected BMBPayloadHeader createHeader(ResponseContext response) {

		BMBPayloadHeader header = new BMBPayloadHeader();
		if(response != null && response.isSuccess()){
		header.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
		header.setResMsg("");
		}else if(response != null){
			header.setResCde(response.getResCde());
			header.setResMsg(response.getResMsg());
		}
		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
		header.setResId(ResponseIdConstants.AIR_TIME_TOP_UP_JSONBLD_VALD);
		return header;

	}

	/**
	 * The method is written for Populate pay load.
	 *
	 * @param bmbPayload the bmb payload
	 * @param responses the responses
	 */
	protected void populatePayLoad(BMBPayload bmbPayload,
			ResponseContext... responses) {

		AirTimeTopUpValidateJSONModel responseModel = new AirTimeTopUpValidateJSONModel();

		AirTimeTopUpValidateResponse airTimeTopUpValidateResponse = (AirTimeTopUpValidateResponse) responses[0];

		GetSelectedAccountOperationResponse getSelectedAccountOperationResponse =(GetSelectedAccountOperationResponse) responses[1];

		AirTimeTopUpValidateServiceResopnse res = (AirTimeTopUpValidateServiceResopnse) responses[2];

		BillerDTO billerDTO = airTimeTopUpValidateResponse.getBillerDTO();

		BillerJSONModel billerJSONModel = new BillerJSONModel();
		billerJSONModel.setBillerCatId(billerDTO.getBillerCategoryId());
		billerJSONModel.setBillerName(billerDTO.getBillerName());
		billerJSONModel.setBillerRefNo(billerDTO.getBillerAccountNumber());
		billerJSONModel.setBillerCatName(billerDTO.getBillerCategoryName());
		billerJSONModel.setBillerId(billerDTO.getBillerId());
		CustomerAccountDTO customerAccountDTO = getSelectedAccountOperationResponse.getSelectedAcct();
	    CasaAccountJSONModel casaAccountJSONModel = null;


	    if(customerAccountDTO instanceof CreditCardAccountDTO)
	    {
	    	CreditCardAccountDTO account = (CreditCardAccountDTO) customerAccountDTO;
		    CreditCardAccountJSONModel ccActJson = new CreditCardAccountJSONModel(account);
		    ccActJson.setTyp(AccountConstants.CC_ACCOUNTS);
		    ccActJson.setDesc(account.getProductCode());
		    ccActJson.setMkdCrdNo(getMaskedCreditCardNumber(ccActJson.getCrdNo()));
			responseModel.setCreditcardJsonModel(ccActJson);
	    }else
	    {
	    	casaAccountJSONModel = getCASAAccountList(getSelectedAccountOperationResponse.getSelectedAcct(),getSelectedAccountOperationResponse);
			responseModel.setSrcAcct(casaAccountJSONModel);
	    }

		if(casaAccountJSONModel!=null)
		{
			String curr = casaAccountJSONModel.getCurr();
			if(curr!=null)
			{
				String txnAmt = airTimeTopUpValidateResponse.getTxnAmt();
				responseModel.setTxnAmt(BMGFormatUtility.getJSONAmount(curr, BMGFormatUtility.getFormattedAmount(txnAmt==null ? zero_0: new BigDecimal(txnAmt))));
			}
		}
		responseModel.setMblNo(airTimeTopUpValidateResponse.getMblNo());
		responseModel.setPrvder(billerJSONModel);
		responseModel.setTxnRefNo(res.getTxnRefNo());
		bmbPayload.setPayData(responseModel);
	}

	/**
	 * Gets the casa account list.
	 *
	 * @param accounts the accounts
	 * @param response the response
	 * @return the CASAAccountList
	 */
	private CasaAccountJSONModel getCASAAccountList(CustomerAccountDTO accounts, ResponseContext response)
	{
		CasaAccountJSONModel accountJSONModel = null;
		if (accounts != null) {
			CASAAccountDTO account = (CASAAccountDTO) accounts;
			accountJSONModel = new CasaAccountJSONModel(account);
			// CHECK added masking
			/*accountJSONModel.setMkdActNo(getMaskedAccountNumber(account.getBranchCode()
							, account.getAccountNumber()));*/

			 if(branchCodeCountryList.contains(BMBContextHolder.getContext().getBusinessId())){
				accountJSONModel.setMkdActNo(getMaskedAccountNumber(account.getBranchCode(), account.getAccountNumber()));
			    }
			    else {
				accountJSONModel.setMkdActNo(getMaskedAccountNumber(null, account.getAccountNumber()));
			    }
			accountJSONModel.setActNo(getCorrelationId(account.getAccountNumber(), response));
			}
		return accountJSONModel;

	}

	/**
	 * Gets the casa account list.
	 *
	 * @param accounts the accounts
	 * @param response the response
	 * @param branchResponse the branch response
	 * @return the CASAAccountList
	 */
/*	@SuppressWarnings("unused")
	private List<CasaAccountJSONModel> getCASAAccountList(
			List<? extends CustomerAccountDTO> accounts,
			ResponseContext response,
			BranchLookUpOperationResponse branchResponse) {

		List<CasaAccountJSONModel> casaAccountList = new ArrayList<CasaAccountJSONModel>();

		 To populate branch code & branch name in Map
		Map<String, String> brnCdeMap = new HashMap<String, String>();

		if (branchResponse != null && branchResponse.getBranchList() != null
				&& branchResponse.getBranchList().size() > 0) {

			for (int i = 0; i < branchResponse.getBranchList().size(); i++) {

				brnCdeMap.put(branchResponse.getBranchList().get(i)
						.getBranchCode(), branchResponse
						.getBranchList().get(i).getBranchName());
			}
		}
		 ----------

		if (accounts != null) {

			for (int i = 0; i < accounts.size(); i++) {

				CASAAccountDTO account = (CASAAccountDTO) accounts.get(i);
				CasaAccountJSONModel accountJSONModel = new CasaAccountJSONModel(
						account);

				// CHECK added masking
				accountJSONModel.setMkdActNo(getMaskedAccountNumber(account
						.getBranchCode(), account.getAccountNumber()));
				accountJSONModel.setActNo(getCorrelationId(account
						.getAccountNumber(), response));

				if (StringUtils.isNotEmpty(account.getBranchCode().toString())) {

					String acctBrnCdeFormatted = getFormattedBranchCode(account
							.getBranchCode());


					 * To get branch code & branch name from Map & adding into
					 * account json model

					if (!brnCdeMap.isEmpty()) {
						accountJSONModel.setBrnCde(acctBrnCdeFormatted);
						accountJSONModel.setBrnNam(brnCdeMap
								.get(acctBrnCdeFormatted));
					} else {
						accountJSONModel.setBrnCde("");
						accountJSONModel.setBrnNam("");
					}
				}
				casaAccountList.add(accountJSONModel);
			}
		}
		return casaAccountList;
	}*/
}