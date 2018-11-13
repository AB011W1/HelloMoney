package com.barclays.bmg.json.model;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.constants.AccountConstants;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.InsuranceAccountDTO;
import com.barclays.bmg.dto.InvestmentAccountDTO;
import com.barclays.bmg.dto.LoanAccountDTO;
import com.barclays.bmg.dto.TdAccountDTO;
import com.barclays.bmg.json.response.BMBPayloadData;

public class AccountSummaryModelAdapter implements BMGJSONAdapter {

	/**
	 * Entry method
	 */
	public BMBPayloadData adaptToJSONModel(Object obj) {

		// Get the list of all accounts
		List<CustomerAccountDTO> allAccountList = (List<CustomerAccountDTO>) obj;

		// Get the JSON Model
		AccountSummaryJSONModel accountSummaryJSONModel = getResponseModel();

		// Populate the accountsummary model
		accountSummaryJSONModel
				.setCustActs(getCategorisedAccounts(allAccountList));

		return accountSummaryJSONModel;
	}

	/**
	 *
	 * @param accountList
	 * @return
	 */
	protected List<? extends AccountJSONModel> getCategorisedAccounts(
			List<CustomerAccountDTO> accountList) {
		List<AccountJSONModel> returnList = new ArrayList<AccountJSONModel>();

		if (accountList != null && accountList.size() > 0) {
			for (CustomerAccountDTO customerAccount : accountList) {

				// Get the AccountModel object
				AccountJSONModel accountJSONModel = getBean(customerAccount);

				// Set the AccountModel into the list based upon category
				if (accountJSONModel != null) {
					// Set the Branch masked account number
					// accountJSONModel.setMaskedAcctNo(bmgSystemUtility
					// .getDisplayAccount(customerAccount));
					accountJSONModel.setMkdActNo(customerAccount
							.getAccountNumber());
					returnList.add(accountJSONModel);

				}
			}
		}

		return returnList;

	}

	/**
	 * Returns the Appropriate AccountModel
	 *
	 * @param customerAccountDTO
	 * @return
	 */
	protected AccountJSONModel getBean(CustomerAccountDTO customerAccountDTO) {
		AccountJSONModel accountJSONModel = null;
		if (customerAccountDTO instanceof CASAAccountDTO) {
			CASAAccountDTO account = (CASAAccountDTO) customerAccountDTO;
			accountJSONModel = new CasaAccountJSONModel(account);
			accountJSONModel.setTyp(AccountConstants.CASA_ACCOUNTS);
		} else if (customerAccountDTO instanceof TdAccountDTO) {
			TdAccountDTO account = (TdAccountDTO) customerAccountDTO;
			accountJSONModel = new TDAccountJSONModel(account);
			accountJSONModel.setTyp(AccountConstants.TD_ACCOUNTS);
		} else if (customerAccountDTO instanceof LoanAccountDTO) {
			LoanAccountDTO account = (LoanAccountDTO) customerAccountDTO;
			accountJSONModel = new LoanAccountJSONModel(account);
			accountJSONModel.setTyp(AccountConstants.LOAN_ACCOUNTS);
		} else if (customerAccountDTO instanceof CreditCardAccountDTO) {
			CreditCardAccountDTO account = (CreditCardAccountDTO) customerAccountDTO;
			accountJSONModel = new CreditCardAccountJSONModel(account);
			accountJSONModel.setTyp(AccountConstants.CC_ACCOUNTS);
		} else if (customerAccountDTO instanceof InsuranceAccountDTO) {
			InsuranceAccountDTO account = (InsuranceAccountDTO) customerAccountDTO;
			accountJSONModel = new InsuranceAccountJSONModel(account);
			accountJSONModel.setTyp(AccountConstants.INSURE_ACCOUNTS);
		} else if (customerAccountDTO instanceof InvestmentAccountDTO) {
			InvestmentAccountDTO account = (InvestmentAccountDTO) customerAccountDTO;
			accountJSONModel = new InvestmentAccountJSONModel(account);
			accountJSONModel.setTyp(AccountConstants.INVEST_ACCOUNTS);
		}

		return accountJSONModel;
	}

	/**
	 *
	 * @return
	 */
	protected AccountSummaryJSONModel getResponseModel() {

		return new AccountSummaryJSONModel();
	}

}
