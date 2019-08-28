package com.barclays.bmg.service.accounts.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barclays.bmg.constants.BMGConstants;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.context.BMGContextHolder;
import com.barclays.bmg.context.BMGGlobalContext;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dao.RetrieveAllCustAcctDAO;
import com.barclays.bmg.dao.accounts.AllAccountDAO;
import com.barclays.bmg.dao.product.ListValueResDAO;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.ListValueCacheDTO;
import com.barclays.bmg.service.accounts.AllAccountService;
import com.barclays.bmg.service.accounts.request.AllAccountServiceRequest;
import com.barclays.bmg.service.accounts.response.AllAccountServiceResponse;
import com.barclays.bmg.service.product.impl.ListValueResServiceImpl;
import com.barclays.bmg.service.product.request.ListValueResServiceRequest;
import com.barclays.bmg.service.product.response.ListValueResByGroupServiceResponse;
import com.barclays.bmg.service.product.response.ListValueResServiceResponse;
import com.barclays.bmg.service.request.RetrieveAllCustAcctServiceRequest;
import com.barclays.bmg.service.response.RetrieveAllCustAcctServiceResponse;

public class AllAccountServiceImpl implements AllAccountService  {
    AllAccountDAO allAccountDAO;
    ListValueResDAO listValueResDAO;
    RetrieveAllCustAcctDAO retrieveAllCustAcctDAO;
    private static final int ZERO = 0;

    private List<String> branchCodeCountryList;

    public List<String> getBranchCodeCountryList() {
	return branchCodeCountryList;
    }

    public void setBranchCodeCountryList(List<String> branchCodeCountryList) {
	this.branchCodeCountryList = branchCodeCountryList;
    }

    public AllAccountServiceResponse retrieveAllAccount(AllAccountServiceRequest request) {
	//Changes for caching of account list & reduce one call to enhance performance
    	AllAccountServiceResponse allAccountServiceResponse=new AllAccountServiceResponse();
    	if(request.getContext().getAccountList()==null || request.getContext().getAccountList().isEmpty()){
    	allAccountServiceResponse = retrieveHelloMoneyAccts(request);
    	}else{
    		allAccountServiceResponse.setAccountList(request.getContext().getAccountList());
    		allAccountServiceResponse.setSuccess(true);
    	}
	if (allAccountServiceResponse != null && allAccountServiceResponse.isSuccess()) {
	    List<? extends CustomerAccountDTO> ussdLst = allAccountServiceResponse.getAccountList();

	    if (ussdLst != null && !ussdLst.isEmpty()) {
		allAccountServiceResponse = allAccountDAO.retrieveAllAccount(request);

		if (allAccountServiceResponse != null && allAccountServiceResponse.isSuccess()) {
		    List<? extends CustomerAccountDTO> allLst = allAccountServiceResponse.getAccountList();
		    List<CustomerAccountDTO> consoLst = new ArrayList<CustomerAccountDTO>();


		  /*  ListValueResServiceRequest listValueResServiceRequest=new ListValueResServiceRequest();
		    listValueResServiceRequest.setGroup("LOAN_PRODUCT_CODE");
		    listValueResServiceRequest.setListValueKey("695");
		    ListValueResServiceResponse listValueResServiceResponse=listValueResDAO.findListValueLabel(listValueResServiceRequest);
		    System.out.println(listValueResServiceResponse);*/

		    ListValueResServiceRequest requesttemp = new ListValueResServiceRequest();
			Context context = allAccountServiceResponse.getContext();

			requesttemp.setContext(context);
			requesttemp.setGroup("LOAN_PRODUCT_CODE");
			List<String> productCode=new ArrayList<String>();
			ListValueResByGroupServiceResponse response = listValueResDAO.findListValueResByGroup(requesttemp);
			if (response.getListValueCahceDTO() != null) {
			    for (ListValueCacheDTO listVal : response.getListValueCahceDTO()) {
				 productCode.add(listVal.getKey());

			    }
			}

			Map<String, Object> contextMap = context.getContextMap();
		    if (allLst != null && ussdLst != null) {

			for (CustomerAccountDTO ussdDto : ussdLst) {
			    int ussdBrCd = (ussdDto.getBranchCode() != null ? Integer.parseInt(ussdDto.getBranchCode()) : ZERO);
			    String ussdAcctNo = (ussdDto.getAccountNumber() != null ? ussdDto.getAccountNumber() : BMGConstants.EMPTYSTR);

			    for (CustomerAccountDTO allDto : allLst) {
			    	//for (ListValueCacheDTO listVal : response.getListValueCahceDTO()) {
			    		if((!(productCode.indexOf(allDto.getProductCode())>-1)) && (contextMap!=null && "Y".equals(contextMap.get(SystemParameterConstant.isLoanRepayment)) && "KEBRB".equals(context.getBusinessId())))
			    		{
			    			if (allDto instanceof CASAAccountDTO) {
							    int allBrCd = (allDto.getBranchCode() != null ? Integer.parseInt(allDto.getBranchCode()) : ZERO);
							    String allAcctNo = (allDto.getAccountNumber() != null ? allDto.getAccountNumber() : BMGConstants.EMPTYSTR);

							    if (branchCodeCountryList.contains(BMBContextHolder.getContext().getBusinessId())) {
								if (allBrCd == ussdBrCd && allAcctNo.equals(ussdAcctNo)) {
								    CASAAccountDTO dto = (CASAAccountDTO) allDto;
								    dto.setPriInd(ussdDto.getPriInd());
								    consoLst.add(allDto);
								    break;
								}
							    } else {

								if (allAcctNo.equals(ussdAcctNo)) {
								    CASAAccountDTO dto = (CASAAccountDTO) allDto;
								    dto.setPriInd(ussdDto.getPriInd());
								    consoLst.add(allDto);
								    break;
								}

							    }

							}
			    		}
			    		else if(!("KEBRB".equals(context.getBusinessId())) || ("N".equals(contextMap.get(SystemParameterConstant.isLoanRepayment)) && "KEBRB".equals(context.getBusinessId())))
			    		{

			    			if (allDto instanceof CASAAccountDTO) {
							    int allBrCd = (allDto.getBranchCode() != null ? Integer.parseInt(allDto.getBranchCode()) : ZERO);
							    String allAcctNo = (allDto.getAccountNumber() != null ? allDto.getAccountNumber() : BMGConstants.EMPTYSTR);

							    if (branchCodeCountryList.contains(BMBContextHolder.getContext().getBusinessId())) {
								if (allBrCd == ussdBrCd && allAcctNo.equals(ussdAcctNo)) {
								    CASAAccountDTO dto = (CASAAccountDTO) allDto;
								    dto.setPriInd(ussdDto.getPriInd());
								    consoLst.add(allDto);
								    break;
								}
							    } else {

								if (allAcctNo.equals(ussdAcctNo)) {
								    CASAAccountDTO dto = (CASAAccountDTO) allDto;
								    dto.setPriInd(ussdDto.getPriInd());
								    consoLst.add(allDto);
								    break;
								}

							    }

							}

			    		}
			   // }
			    }
			}
			if (!allLst.isEmpty())
			    allAccountServiceResponse.setAccountList(consoLst);
		    }
		}
	    }
}

	if (allAccountServiceResponse != null)
	    getCurrencyRate(allAccountServiceResponse);
	return allAccountServiceResponse;
    }

    private AllAccountServiceResponse retrieveHelloMoneyAccts(AllAccountServiceRequest request) {
	RetrieveAllCustAcctServiceRequest serReq = new RetrieveAllCustAcctServiceRequest();
	serReq.setContext(request.getContext());
	RetrieveAllCustAcctServiceResponse serRes = retrieveAllCustAcctDAO.retrieveAllCustAccount(serReq);
	AllAccountServiceResponse allAccountServiceResponse = new AllAccountServiceResponse();
	allAccountServiceResponse.setAccountList(serRes.getAccountList());
	allAccountServiceResponse.setContext(serRes.getContext());
	allAccountServiceResponse.setResCde(serRes.getResCde());
	allAccountServiceResponse.setSuccess(serRes.isSuccess());
	allAccountServiceResponse.setResMsg(serRes.getResMsg());
	return allAccountServiceResponse;
    }

    private void getCurrencyRate(AllAccountServiceResponse allAccountServiceResponse) {
	ListValueResServiceRequest request = new ListValueResServiceRequest();
	Context context = allAccountServiceResponse.getContext();

	request.setContext(context);
	request.setGroup("FX_BOOK_RATES");

	ListValueResByGroupServiceResponse response = listValueResDAO.findListValueResByGroup(request);

	Map<String, BigDecimal> currencyConversionMap = context.getCurrencyConversionMap();

	if (currencyConversionMap == null) {
	    currencyConversionMap = new HashMap<String, BigDecimal>();

	}

	currencyConversionMap.put(context.getLocalCurrency(), BigDecimal.valueOf(1));
	context.setCurrencyConversionMap(currencyConversionMap);

	if (response.getListValueCahceDTO() != null) {
	    for (ListValueCacheDTO listVal : response.getListValueCahceDTO()) {
		String curr = listVal.getKey();
		BigDecimal rate = BigDecimal.valueOf(Double.parseDouble(listVal.getFilterKey1()));
		currencyConversionMap.put(curr, rate);
	    }
	}
    }

    public void setAllAccountDAO(AllAccountDAO allAccountDAO) {
	this.allAccountDAO = allAccountDAO;
    }

    public ListValueResDAO getListValueResDAO() {
	return listValueResDAO;
    }

    public void setListValueResDAO(ListValueResDAO listValueResDAO) {
	this.listValueResDAO = listValueResDAO;
    }

    public AllAccountDAO getAllAccountDAO() {
	return allAccountDAO;
    }

    public RetrieveAllCustAcctDAO getRetrieveAllCustAcctDAO() {
	return retrieveAllCustAcctDAO;
    }

    public void setRetrieveAllCustAcctDAO(RetrieveAllCustAcctDAO retrieveAllCustAcctDAO) {
	this.retrieveAllCustAcctDAO = retrieveAllCustAcctDAO;
    }

    @Override
    public AllAccountServiceResponse retrieveAccountsFromCBS(AllAccountServiceRequest request) {
	return allAccountDAO.retrieveAllAccount(request);
    }

    @Override
    public AllAccountServiceResponse retrieveCreditCardList(AllAccountServiceRequest request) {
	return allAccountDAO.retrieveAllAccount(request);
    }
}


