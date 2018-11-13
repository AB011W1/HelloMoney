package com.barclays.bmg.json.model.builder.billpayment;

import java.util.List;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BillerDTO;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.json.model.AmountJSONModel;
import com.barclays.bmg.json.model.CasaAccountJSONModel;
import com.barclays.bmg.json.model.billpayment.BillerJSONModel;
import com.barclays.bmg.json.model.billpayment.OneTimeBillPayFormSubmitJSONModel;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.billpayment.OneTimeBillPayOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrieveAcctListOperationResponse;
import com.barclays.bmg.utils.BMGFormatUtility;
import com.barclays.bmg.utils.DateTimeUtil;


public class OneTimeBillPayInitJSONBldr extends
		BMBMultipleResponseJSONBuilder implements BMBJSONBuilder {

		protected void populatePayLoad(BMBPayload bmbPayload,
				ResponseContext... responseContexts) {
		Context context=null;
		RetrieveAcctListOperationResponse retrieveAcctListOperationResponse = (RetrieveAcctListOperationResponse)responseContexts[0];
		//TransactionLimitOperationResponse transactionLimitOperationResponse = (TransactionLimitOperationResponse) responseContexts[1];
		OneTimeBillPayOperationResponse oneTimeBillPayOperationResponse= (OneTimeBillPayOperationResponse) responseContexts[2];

		OneTimeBillPayFormSubmitJSONModel oneTimeBillPayFormSubmitJSONModel = new OneTimeBillPayFormSubmitJSONModel();

		if (retrieveAcctListOperationResponse != null) {

			context=retrieveAcctListOperationResponse.getContext();

			List<? extends CustomerAccountDTO> accountList = retrieveAcctListOperationResponse
					.getAcctList();
			for (Object acct : accountList) {
				CustomerAccountDTO customerAccountDTO = (CustomerAccountDTO) acct;


/*				 * if (customerAccountDTO instanceof CreditCardAccountDTO ||
				 * customerAccountDTO instanceof CASAAccountDTO) {
*/

					if (customerAccountDTO instanceof CreditCardAccountDTO) {
						CasaAccountJSONModel accountJSONModel = new CasaAccountJSONModel();
						accountJSONModel.setDesc(customerAccountDTO
								.getDesc());
						accountJSONModel.setActNo(getCorrelationId(
								customerAccountDTO.getAccountNumber(),
								retrieveAcctListOperationResponse));
						accountJSONModel
								.setMkdActNo(getMaskedCardNumber(((CreditCardAccountDTO) customerAccountDTO)
										.getPrimary().getCardNumber()));
						accountJSONModel.setCurr(customerAccountDTO
								.getCurrency());

						AmountJSONModel amountJSONModel=new AmountJSONModel();
						amountJSONModel.setAmt(getPositiveCurrentBalance(customerAccountDTO
								.getCurrentBalance()));
						amountJSONModel.setCurr(customerAccountDTO
								.getCurrency());

						accountJSONModel.setAvblBal(amountJSONModel);
						oneTimeBillPayFormSubmitJSONModel.add(accountJSONModel);
					} else {
						CasaAccountJSONModel accountJSONModel = new CasaAccountJSONModel();

						accountJSONModel.setTyp(customerAccountDTO
								.getDesc());
						accountJSONModel.setActNo(getCorrelationId(
								customerAccountDTO.getAccountNumber(),retrieveAcctListOperationResponse));
						// Set Masked account number.
						/*accountJSONModel.setMkdActNo(getMaskedAccountNumber(customerAccountDTO
								.getBranchCode(), customerAccountDTO.getAccountNumber()));*/

						 if(branchCodeCountryList.contains(BMBContextHolder.getContext().getBusinessId())){
							accountJSONModel.setMkdActNo(getMaskedAccountNumber(customerAccountDTO.getBranchCode(), customerAccountDTO.getAccountNumber()));
						    }
						    else {
							accountJSONModel.setMkdActNo(getMaskedAccountNumber(null, customerAccountDTO.getAccountNumber()));
						    }

						accountJSONModel.setCurr(customerAccountDTO.getCurrency());
						AmountJSONModel amountJSONModel=new AmountJSONModel();
						amountJSONModel.setAmt(getPositiveCurrentBalance(customerAccountDTO
								.getCurrentBalance()));
						amountJSONModel.setCurr(customerAccountDTO
								.getCurrency());
						accountJSONModel.setAvblBal(amountJSONModel);
						accountJSONModel.setPriInd(customerAccountDTO.getPriInd());
						oneTimeBillPayFormSubmitJSONModel.add(accountJSONModel);

					}








				// }
			}


			}


		BillerDTO billerDTO=oneTimeBillPayOperationResponse.getBillerDTO();
		BillerJSONModel billerJSONModel=new BillerJSONModel();
		billerJSONModel.setBillerType(billerDTO.getBillerCategoryName());
		billerJSONModel.setBillerName(billerDTO.getBillerName());
		oneTimeBillPayFormSubmitJSONModel.setBillerInfo(billerJSONModel);

		/*		PaymentTransferJSONBean paymentTransferJSONBean=null;
		BeneficiaryJSONModel benficiBean = null;
		if (retrieveAcctListOperationResponse != null) {

			context=retrieveAcctListOperationResponse.getContext();
			paymentTransferJSONBean = new PaymentTransferJSONBean();

			List<? extends CustomerAccountDTO> accountList = retrieveAcctListOperationResponse
					.getAcctList();
			for (Object acct : accountList) {
				CustomerAccountDTO customerAccountDTO = (CustomerAccountDTO) acct;


				 * if (customerAccountDTO instanceof CreditCardAccountDTO ||
				 * customerAccountDTO instanceof CASAAccountDTO) {

				if (customerAccountDTO instanceof CreditCardAccountDTO) {
					AccountJSONBean accountJSONBean = new AccountJSONBean();
					accountJSONBean.setProductType(customerAccountDTO
							.getProductCode());
					accountJSONBean.setAcctNo(getCorrelationId(
							customerAccountDTO.getAccountNumber(),
							retrieveAcctListOperationResponse));
					accountJSONBean
							.setMaskedAcctNo(getMaskedCardNumber(((CreditCardAccountDTO) customerAccountDTO)
									.getPrimary().getCardNumber()));
					accountJSONBean.setCurrency(customerAccountDTO
							.getCurrency());
					accountJSONBean
							.setAvailableBalance(getPositiveCurrentBalance(customerAccountDTO
									.getCurrentBalance()));
					paymentTransferJSONBean.add(accountJSONBean);
				} else {
					AccountJSONBean accountJSONBean = new AccountJSONBean();
					accountJSONBean
							.setProductType(customerAccountDTO.getDesc());
					accountJSONBean.setAcctNo(getCorrelationId(
							customerAccountDTO.getAccountNumber(),
							retrieveAcctListOperationResponse));
					accountJSONBean.setMaskedAcctNo(getMaskedAccountNumber(
							customerAccountDTO.getBranchCode(),
							customerAccountDTO.getAccountNumber()));
					accountJSONBean.setCurrency(customerAccountDTO
							.getCurrency());
					accountJSONBean.setAvailableBalance(BMGFormatUtility
							.getFormattedAmount(customerAccountDTO
									.getAvailableBalance()));
					paymentTransferJSONBean.add(accountJSONBean);

				}

				// }
			}


			}

			AmountJSONModel txnLimit = null;
			benficiBean = new BeneficiaryJSONModel();
			BillerDTO billerDTO=oneTimeBillPayOperationResponse.getBillerDTO();
			// BillerType
			benficiBean.setBilrType(billerDTO.getBillerCategoryId());
			benficiBean.setBilrName(billerDTO.getBillerName());


			if (transactionLimitOperationResponse.getAValidDailyLimit() != null) {
				txnLimit = new AmountJSONModel();
				txnLimit
						.setAmount(BMGFormatUtility
								.getFormattedAmount(transactionLimitOperationResponse
										.getAValidDailyLimit()));
				txnLimit.setCurrency(transactionLimitOperationResponse
						.getContext().getLocalCurrency());

				// TransactionLimit
				benficiBean.setTransactionLmt(txnLimit.getAmount());
				benficiBean.setAvailDlyTxnLmt(txnLimit.getAmount());
				// Currency
				benficiBean.setTrnCurr(txnLimit.getCurrency());
			}


			benficiBean.setBilrId(billerDTO.getBillerId());
			paymentTransferJSONBean.setPayeeModel(benficiBean);
			responseModel = paymentTransferJSONBean;*/

		if(context != null) {
			oneTimeBillPayFormSubmitJSONModel.setTxnRefNo(context.getOrgRefNo());
		}
		oneTimeBillPayFormSubmitJSONModel.setTxnDate(BMGFormatUtility.getLongDate(DateTimeUtil.getCurrentBusinessCalendar(oneTimeBillPayOperationResponse.getContext()).getTime()));

		bmbPayload.setPayData(oneTimeBillPayFormSubmitJSONModel);
	}

	protected BMBPayloadHeader createHeader(ResponseContext response) {

		BMBPayloadHeader header = new BMBPayloadHeader();
		if (response != null && response.isSuccess()) {
			header.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
			header.setResMsg("");
		} else  if(response != null){
			header.setResCde(response.getResCde());
			header.setResMsg(response.getResMsg());
		}
		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
		header.setResId(ResponseIdConstants.ONE_TIME_BILL_PAY_INIT);

		return header;
	}


	@Override
	public Object createMultipleJSONResponse(
			ResponseContext... responseContexts) {
		BMBPayloadHeader bmbPayloadHeader = null;
		BMBPayload bmbPayload = null;
		for (ResponseContext response : responseContexts) {
			if (response != null && !response.isSuccess()) {
				bmbPayloadHeader = createHeader(response,
						ResponseIdConstants.ONE_TIME_BILL_PAY_INIT);
				break;
			}
		}

		if (bmbPayloadHeader != null) {
			bmbPayload = new BMBPayload(bmbPayloadHeader);
		} else {
			ResponseContext response = responseContexts[0];
			bmbPayload = new BMBPayload(createHeader(response,
					ResponseIdConstants.ONE_TIME_BILL_PAY_INIT));
			populatePayLoad(bmbPayload, responseContexts);
		}

		return bmbPayload;
	}



}
