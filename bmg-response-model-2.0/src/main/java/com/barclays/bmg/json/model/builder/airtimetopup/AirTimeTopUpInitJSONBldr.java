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

import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BillerDTO;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.json.model.CasaAccountJSONModel;
import com.barclays.bmg.json.model.billpayment.BillerJSONModel;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.AirTimeTopUpOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrieveAcctListOperationResponse;

/**
 * ****************** Revision History **********************
 * </br>
 * Project Name  is  <b>bmg-response-model-2.0</b>
 * </br>
 * The file name is  <b>AirTimeTopUpInitJSONBldr.java</b>
 * </br>
 * Description  is  <b>Initial Version</b>
 * </br>
 * Updated Date  is  <b>May 17, 2013</b>
 * </br>
 * ******************************************************
 *
 * @author e20037686
 * </br>
 * * The Class AirTimeTopUpInitJSONBldr created using JRE 1.6.0_10
 */
public class AirTimeTopUpInitJSONBldr extends BMBMultipleResponseJSONBuilder {

	/* (non-Javadoc)
	 * @see com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder#createMultipleJSONResponse(com.barclays.bmg.context.ResponseContext[])
	 */
	@Override
	public Object createMultipleJSONResponse(
			ResponseContext... responseContexts) {
		BMBPayloadHeader bmbPayloadHeader = null;
		BMBPayload bmbPayload = null;
		for(ResponseContext response: responseContexts){
			if(response!=null && !response.isSuccess()){
				bmbPayloadHeader = createHeader(response);
				break;
			}
		}

		if(bmbPayloadHeader!=null){
			bmbPayload = new BMBPayload(bmbPayloadHeader);
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
		header.setResId(ResponseIdConstants.AIR_TIME_TOP_UP);
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

		AirTimeTopUpInitJSONModel responseModel = new AirTimeTopUpInitJSONModel();

		RetrieveAcctListOperationResponse acctListOperationResponse = (RetrieveAcctListOperationResponse) responses[0];
		//BranchLookUpOperationResponse branchLookUpOperationResponse = (BranchLookUpOperationResponse) responses[1];

		AirTimeTopUpOperationResponse airTimeTopUpOperationResponse = (AirTimeTopUpOperationResponse) responses[2];

		List<BillerDTO> billerList = airTimeTopUpOperationResponse.getBillerServiceResponse().getBillerList();
		List<BillerJSONModel> billerJSONModelLst = new ArrayList<BillerJSONModel>();
		for (BillerDTO billerDTO : billerList)
		{
			BillerJSONModel billerJSONModel = new BillerJSONModel();
			billerJSONModel.setBillerCatId(billerDTO.getBillerCategoryId());
			billerJSONModel.setBillerName(billerDTO.getBillerName());
			billerJSONModel.setBillerRefNo(billerDTO.getBillerAccountNumber());
			billerJSONModel.setBillerCatName(billerDTO.getBillerCategoryName());
			billerJSONModel.setBillerId(billerDTO.getBillerId());
			billerJSONModelLst.add(billerJSONModel);
		}

		/*responseModel.setSrcLst(getCASAAccountList(acctListOperationResponse
				.getAcctList(), acctListOperationResponse,
				branchLookUpOperationResponse));*/


		responseModel.setSrcLst(getCASAAccountList(acctListOperationResponse.getAcctList(),acctListOperationResponse));
		responseModel.setPrvderLst(billerJSONModelLst);
		bmbPayload.setPayData(responseModel);
	}



	/**
	 * Gets the casa account list.
	 *
	 * @param accounts the accounts
	 * @param response the response
	 * @return the CASAAccountList
	 */
	private List<CasaAccountJSONModel> getCASAAccountList(
			List<? extends CustomerAccountDTO> accounts, ResponseContext response) {

		List<CasaAccountJSONModel> casaAccountList = new ArrayList<CasaAccountJSONModel>();
		if (accounts != null) {
			for (int i = 0; i < accounts.size(); i++) {
				CASAAccountDTO account = (CASAAccountDTO) accounts.get(i);
				CasaAccountJSONModel accountJSONModel = new CasaAccountJSONModel(
						account);
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
				casaAccountList.add(accountJSONModel);

			}
		}
		return casaAccountList;

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

	}
*/

}