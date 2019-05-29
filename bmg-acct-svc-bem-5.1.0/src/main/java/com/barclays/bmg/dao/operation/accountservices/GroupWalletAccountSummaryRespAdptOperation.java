package com.barclays.bmg.dao.operation.accountservices;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.barclays.bem.AccountAdditionalDetails.AccountAdditionalDetails;
import com.barclays.bem.BEMServiceHeader.BEMResHeader;
import com.barclays.bem.RetrieveNonPersonalCustAccountList.AccountInformation;
import com.barclays.bem.RetrieveNonPersonalCustAccountList.NonPersonalCustAcctList;
import com.barclays.bem.RetrieveNonPersonalCustAccountList.RetrieveNonPersonalCustAccountListResponse;
import com.barclays.bmg.constants.ErrorCodeConstant;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.operation.accountdetails.response.AccountAdditionalInfoSer;
import com.barclays.bmg.operation.accountdetails.response.AccountSer;
import com.barclays.bmg.service.accounts.response.AllGroupWalletAccountServiceResponse;

public class GroupWalletAccountSummaryRespAdptOperation extends
		AbstractResAdptOperation {
	private static final Logger LOGGER = Logger
			.getLogger(GroupWalletAccountSummaryRespAdptOperation.class);

	public AllGroupWalletAccountServiceResponse adaptResponse(
			WorkContext workContext, Object obj) {
		AllGroupWalletAccountServiceResponse agwsresponse = new AllGroupWalletAccountServiceResponse();
		RetrieveNonPersonalCustAccountListResponse nonPersonalResponse = (RetrieveNonPersonalCustAccountListResponse) obj;
		checkRespHeader(nonPersonalResponse.getResponseHeader(), agwsresponse);

		if (agwsresponse.isSuccess()) {
			if (nonPersonalResponse.getNonPersonalCustAcctList() != null
					&& nonPersonalResponse.getNonPersonalCustAcctList()
							.getAccounts().length != 0) {
				NonPersonalCustAcctList nonPersonalCustAcctList = nonPersonalResponse
						.getNonPersonalCustAcctList();
				List<AccountSer> list = populateCustomerDetailsList(nonPersonalCustAcctList
						.getAccounts());
				if (!list.isEmpty()
						&& list.get(0).getAccountAdditionalInfo() != null) {
					agwsresponse.setNonPersonalCustAcctList(list);
					agwsresponse.setSuccess(true);
				} else {
					agwsresponse.setResCde("BEMRECMOB");
					agwsresponse.setSuccess(false);
				}

			} else {
				agwsresponse.setResMsg(nonPersonalResponse.getResponseHeader()
						.getServiceResStatus().getServiceResDesc());
				agwsresponse.setResCde("BEMRECMOB");
				agwsresponse.setSuccess(false);
			}

		} else {
			for (com.barclays.bem.BEMServiceHeader.Error error : nonPersonalResponse
					.getResponseHeader().getErrorList()) {
				agwsresponse.setResMsg(nonPersonalResponse.getResponseHeader()
						.getServiceResStatus().getServiceResDesc());
				agwsresponse.setResCde("BEM" + error.getErrorCode());
				agwsresponse.setSuccess(false);
			}
		}

		return agwsresponse;
	}

	private List<AccountSer> populateCustomerDetailsList(
			AccountInformation[] accountInformation) {
		if (LOGGER.isDebugEnabled()) {
			if (accountInformation != null)
				LOGGER.debug(" Entry accountInformationArray size"
						+ accountInformation.length);
		}

		List<AccountSer> nonPersonalCustAcctList = new LinkedList<AccountSer>();
		List<AccountInformation> accInf = new ArrayList<AccountInformation>();
		if (null != accountInformation)
			accInf = Arrays.asList(accountInformation);

		for (AccountInformation details : accInf) {
			AccountAdditionalDetails aad = details.getAccountAdditionalInfo();
			AccountSer accountSer = new AccountSer();
			AccountAdditionalInfoSer accountAdditionalInfoSer = new AccountAdditionalInfoSer();
			accountAdditionalInfoSer.setAccountId(aad.getAccountNumber());
			accountAdditionalInfoSer.setAccountTitle(aad.getAccountTitle());
			accountAdditionalInfoSer.setAuthLevel(aad.getAccountLevel());
			accountAdditionalInfoSer.setAvailableBalance(aad
					.getAccountCurrentBalance().getAccountCCYBalance()
					.toString());
			accountAdditionalInfoSer.setBranchCode(aad.getDomicileBranch()
					.getBranchCode());
			accountAdditionalInfoSer.setBranchName(aad.getDomicileBranch()
					.getBranchName());
			accountAdditionalInfoSer.setCurrencyCode(aad
					.getAccountCurrencyCode());
			accountAdditionalInfoSer.setCurrencyShortName(aad
					.getAccountCurrencyCode());
			accountAdditionalInfoSer.setCurrentBalance(aad
					.getAccountCurrentBalance().getAccountCCYBalance()
					.toString());
			accountAdditionalInfoSer.setCurrentStatus(aad.getAccountStatus());
			accountAdditionalInfoSer.setCustomerId(aad.getCustomerNumber());
			accountAdditionalInfoSer.setCustomerRelationship(aad
					.getJointAccountRelationship(0).getRelationshipTypeCode());
			accountAdditionalInfoSer.setProductCode(details.getAccountDetails()
					.getProductInfo().getProductCode());
			accountAdditionalInfoSer.setProductName(details.getAccountDetails()
					.getProductInfo().getProductName());
			accountSer.setAccountAdditionalInfo(accountAdditionalInfoSer);
			nonPersonalCustAcctList.add(accountSer);
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("NonPersonalCustAcctList size"
					+ nonPersonalCustAcctList.size());
		}

		return nonPersonalCustAcctList;
	}

	private void checkRespHeader(BEMResHeader resHeader,
			ResponseContext response) {

		String resCode = resHeader.getServiceResStatus().getServiceResCode();
		if (ErrorCodeConstant.BUSINESS_ERROR.equals(resCode)) {
			if (resHeader.getErrorList() != null
					&& resHeader.getErrorList().length > 0) {

				for (com.barclays.bem.BEMServiceHeader.Error error : resHeader
						.getErrorList()) {

					response.setResMsg(error.getErrorDesc());
					response.setResCde("BEM" + error.getErrorCode());
					response.setSuccess(false);
				}
			}
		}
		if (response.isSuccess()) {
			response.setSuccess(super.checkResponseHeader(resHeader));
		}
	}
}