package com.barclays.bmg.service.product.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dao.EntitlementDAO;
import com.barclays.bmg.dao.SystemParameterDAO;
import com.barclays.bmg.dao.product.ProductDAO;
import com.barclays.bmg.dao.product.ProductEligibilityDAO;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.CreditCardDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.EntitlementDTO;
import com.barclays.bmg.dto.InsuranceAccountDTO;
import com.barclays.bmg.dto.InvestmentAccountDTO;
import com.barclays.bmg.dto.LoanAccountDTO;
import com.barclays.bmg.dto.Product;
import com.barclays.bmg.dto.ProductDTO;
import com.barclays.bmg.dto.ProductEligibilityDTO;
import com.barclays.bmg.dto.SystemParameterDTO;
import com.barclays.bmg.dto.TdAccountDTO;
import com.barclays.bmg.service.product.ProductEligibilityService;
import com.barclays.bmg.service.product.request.ProductEligibilityServiceRequest;
import com.barclays.bmg.service.product.request.ProductServiceRequest;
import com.barclays.bmg.service.product.response.EntitlementServiceResponse;
import com.barclays.bmg.service.product.response.ProductEligibilityListServiceResponse;
import com.barclays.bmg.service.product.response.ProductListServiceResponse;
import com.barclays.bmg.service.product.response.ProductServiceResponse;
import com.barclays.bmg.service.request.EntitlementServiceRequest;
import com.barclays.bmg.service.request.SystemParameterServiceRequest;
import com.barclays.bmg.service.response.SystemParameterServiceResponse;

public class ProductEligibilityServiceImpl implements ProductEligibilityService {

    private ProductEligibilityDAO productEligibilityDAO;
    private ProductDAO productDAO;
    private SystemParameterDAO systemParameterDAO;
    private EntitlementDAO entitlementDAO;
    private static final String INC = "INC";
    private static final String EXC = "EXC";

    public ProductEligibilityListServiceResponse getProductEligibilityByActivityId(ProductEligibilityServiceRequest request) {

	return productEligibilityDAO.getProductEligibilityByActivityId(request);

    }

    public ProductEligibilityListServiceResponse getProductEligibilityByProductIndicator(ProductEligibilityServiceRequest request) {

	return productEligibilityDAO.getProductEligibilityByProductIndicator(request);

    }

    public ProductEligibilityDAO getProductEligibilityDAO() {
	return productEligibilityDAO;
    }

    public void setProductEligibilityDAO(ProductEligibilityDAO productEligibilityDAO) {
	this.productEligibilityDAO = productEligibilityDAO;
    }

    /*
     * This method is used to check product eligibility for TD interest rates for UAE in View TD Interest Rates Module
     */
    @Override
    public boolean isProductEligible(ProductEligibilityServiceRequest request) {

	ProductEligibilityServiceRequest incProdEligSerReq = new ProductEligibilityServiceRequest();

	incProdEligSerReq.setContext(request.getContext());
	incProdEligSerReq.setIncOrExc(INC);
	incProdEligSerReq.setProductCode(request.getProductCode());
	incProdEligSerReq.setProductCategory(request.getProductCategory());

	ProductEligibilityListServiceResponse incResponse = productEligibilityDAO.getProductEligibilityByIncOrExc(incProdEligSerReq);

	List<ProductEligibilityDTO> includeList = incResponse.getProductEligibilityList();

	if (includeList != null && includeList.size() == 1) {
	    // there is configuration in database for include.
	    return true;
	}

	// if there is no according record or there are more than one record for
	// INCLUDE specific product code, then go on.

	// Check EXC Eligible For Specific Product Code
	ProductEligibilityServiceRequest excProdEligSerReq = new ProductEligibilityServiceRequest();

	excProdEligSerReq.setContext(request.getContext());
	excProdEligSerReq.setProductCode(request.getProductCode());
	excProdEligSerReq.setIncOrExc(EXC);
	excProdEligSerReq.setProductCategory(request.getProductCategory());

	ProductEligibilityListServiceResponse excResponse = productEligibilityDAO.getProductEligibilityByIncOrExc(excProdEligSerReq);

	List<ProductEligibilityDTO> excludeList = excResponse.getProductEligibilityList();

	if (excludeList != null && excludeList.size() == 1) {
	    // there is configuration in database for exclude.
	    return false;
	}
	// if there is no according record or there are more than one record for
	// EXCLUDE specific product code, then go on.

	// Check INC For All Product
	ProductEligibilityServiceRequest incAllProdEligSerReq = new ProductEligibilityServiceRequest();

	incAllProdEligSerReq.setContext(request.getContext());
	incAllProdEligSerReq.setIncOrExc(INC);
	incAllProdEligSerReq.setProductCategory(request.getProductCategory());

	ProductEligibilityListServiceResponse incAllResponse = productEligibilityDAO.getProductEligibilityByIncOrExc(incAllProdEligSerReq);

	List<ProductEligibilityDTO> includeAllList = incAllResponse.getProductEligibilityList();

	if (includeAllList != null) {
	    for (ProductEligibilityDTO child : includeAllList) {

		if (child.getProductCode() != null) {
		    // ignore product code.
		    continue;
		}

		// there is productCode=null record.
		return true;

	    }
	}
	// There is no productCode=null record for INCLUDE, then go on.

	// Check EXC For All Product
	ProductEligibilityServiceRequest excAllProdEligSerReq = new ProductEligibilityServiceRequest();

	excAllProdEligSerReq.setContext(request.getContext());
	excAllProdEligSerReq.setIncOrExc(EXC);
	excAllProdEligSerReq.setProductCategory(request.getProductCategory());

	ProductEligibilityListServiceResponse excAllResponse = productEligibilityDAO.getProductEligibilityByIncOrExc(excAllProdEligSerReq);

	List<ProductEligibilityDTO> excludeAllList = excAllResponse.getProductEligibilityList();

	if (excludeAllList != null) {
	    for (ProductEligibilityDTO child : excludeAllList) {

		if (child.getProductCode() != null) {
		    // ignore product code.
		    continue;
		}

		// there is productCode=null record.
		return false;

	    }
	}
	// There is no productCode=null record for EXCLUDE, then go on.

	// If both have include and exclude for specific product code , exclude
	// will be ignored
	if (includeAllList != null && includeAllList.size() > 0) {
	    // there are more than one specific-productCode record for INCLUDE.
	    // eg, there are two productCode=69 records for INCLUDE.
	    return false;
	} else {
	    // Ignore more than one specific-productCode record for EXCLUDE.
	    // if no configuration, then return true.
	    return true;
	}
    }

    @SuppressWarnings("unused")
    private List<Product> getProductList(List<ProductEligibilityDTO> peList, RequestContext request) {

	List<Product> returnProductList = new ArrayList<Product>();

	for (ProductEligibilityDTO each : peList) {

	    Product product = new Product();

	    product.setProductCode(each.getProductCode());
	    product.setProductCategory(each.getProductCategory());

	    product.setProductName(getProductName(each.getProductCode(), request));

	    ProductServiceRequest productServiceRequest = new ProductServiceRequest();
	    productServiceRequest.setContext(request.getContext());
	    productServiceRequest.setProductGroup(each.getProductCategory());
	    productServiceRequest.setProductCode(each.getProductCode());

	    ProductListServiceResponse productServiceResponse = productDAO.getProductsByProductGroup(productServiceRequest);

	    List<ProductDTO> productList = productServiceResponse.getProductList();

	    if (productList.size() > 0) {
		ProductDTO productDTO = productList.get(0);

		product.setCurrency(productDTO.getCurrencyCode());
		product.setInterestCompoundingFrequency(productDTO.getInterestCompoundingFrequency());
		product.setInterestPayoutFrequency(productDTO.getInterestPayoutFrequency());

	    }
	    returnProductList.add(product);
	}

	return returnProductList;
    }

    private String getProductName(String productCode, RequestContext request) {

	String updatedproductCode = productCode;
	ProductServiceRequest productServiceRequest = new ProductServiceRequest();
	productServiceRequest.setProductCode(updatedproductCode);
	productServiceRequest.setContext(request.getContext());
	ProductListServiceResponse prodServiceResponse = productDAO.getProductsByProductCode(productServiceRequest);

	List<ProductDTO> resultList = prodServiceResponse.getProductList();

	if (resultList != null && resultList.size() > 1) {
	    updatedproductCode = resultList.get(0).getProductDesc();
	}

	return updatedproductCode;

    }

    /**
     * Retrieve eligible accounts
     * 
     * @param request
     * @return
     */
    public ProductEligibilityListServiceResponse getEligibleAccounts(ProductEligibilityServiceRequest request) {

	ProductEligibilityListServiceResponse prodEligibilityServiceResponse = productEligibilityDAO.getProductEligibilityByActivityId(request);

	prodEligibilityServiceResponse.setContext(request.getContext());

	// filter account on product category

	List<CustomerAccountDTO> filterActs = filterAccounts(request.getAccountList(), prodEligibilityServiceResponse);

	Set<CustomerAccountDTO> eligibleActs = new HashSet<CustomerAccountDTO>();

	// Check inc/exc and entitlement

	for (CustomerAccountDTO account : filterActs) {
	    if (isAccountEligible(account, request)) {

		String drOrCr = request.getDrOrCr();
		if ("CR".equals(drOrCr)) {
		    eligibleActs.add(account);
		} else if (isEntitled(account, request)) {
		    eligibleActs.add(account);
		}

	    }
	}

	// Make list from set fix for defect# 653
	List<CustomerAccountDTO> eligibleActsList = new ArrayList<CustomerAccountDTO>();

	// Set total asset/debt
	BigDecimal totalAsset = new BigDecimal(0);
	BigDecimal totalDebt = new BigDecimal(0);

	for (CustomerAccountDTO account : eligibleActs) {
	    setAccountAssetDebt(account, request.getContext());
	    eligibleActsList.add(account);
	    totalAsset = totalAsset.add(account.getAsset());
	    totalDebt = totalDebt.add(account.getDebt());

	}

	// Add product Description to accounts
	addProdDescToAccount(eligibleActsList, request.getContext());

	prodEligibilityServiceResponse.setAccountList(eligibleActsList);
	prodEligibilityServiceResponse.setTotalAsset(totalAsset);
	prodEligibilityServiceResponse.setTotalDebt(totalDebt);
	prodEligibilityServiceResponse.setTotalNetWorth(totalAsset.add(totalDebt.negate()));

	return prodEligibilityServiceResponse;
    }

    /*
     * private void addRequiredValueToAccountList(List<CustomerAccountDTO> filterAccountList, Context context){
     * 
     * for(CustomerAccountDTO each : filterAccountList){
     * 
     * 
     * ProductServiceRequest productServiceRequest = new ProductServiceRequest(); productServiceRequest.setContext(context);
     * productServiceRequest.setProductCode(each.getProductCode());
     * 
     * ProductListServiceResponse productServiceResponse = productDAO.getProductsByProductCode(productServiceRequest);
     * 
     * List<ProductDTO> productList = productServiceResponse.getProductList(); if(productList.size()>0){ ProductDTO productDTO = productList.get(0);
     * // each.setProductName(productDTO.getProductDesc()); each.setDesc(productDTO.getProductDesc());
     * 
     * } } }
     */

    private void addProdDescToAccount(List<CustomerAccountDTO> filterAccountList, Context context) {

	if (filterAccountList != null && !filterAccountList.isEmpty()) {
	    List<String> prodCodeList = new ArrayList<String>();
	    for (CustomerAccountDTO each : filterAccountList) {
		prodCodeList.add(each.getProductCode());
	    }

	    ProductServiceRequest productServiceRequest = new ProductServiceRequest();
	    productServiceRequest.setContext(context);
	    productServiceRequest.setProdCodeList(prodCodeList);

	    ProductListServiceResponse productServiceResponse = productDAO.getProductDescByProductCodes(productServiceRequest);

	    Map<String, String> prodCodeDescMap = productServiceResponse.getProdCodeDescMap();

	    for (CustomerAccountDTO each : filterAccountList) {
		String desc = prodCodeDescMap.get(each.getProductCode()) != null ? prodCodeDescMap.get(each.getProductCode()) : each.getProductCode();
		each.setDesc(desc);

	    }
	}

    }

    /**
     * @param account
     * @param request
     * @return
     */
    public boolean isEntitled(CustomerAccountDTO account, ProductEligibilityServiceRequest request) {

	return isEntitled(account, request, true);
    }

    /**
     * @param account
     * @param request
     * @param primaryCardRequiredFlag
     * @return
     */
    public boolean isEntitled(CustomerAccountDTO account, ProductEligibilityServiceRequest request, boolean primaryCardRequiredFlag) {

	boolean isEntitled = true;
	if (account != null && account instanceof InvestmentAccountDTO) {
	    isEntitled = true;
	}

	EntitlementServiceRequest entitlementServiceRequest = new EntitlementServiceRequest();
	entitlementServiceRequest.setContext(request.getContext());

	EntitlementServiceResponse entitlementServiceResponse = entitlementDAO.retrieveEntitlement(entitlementServiceRequest);

	EntitlementDTO entitlementDTO = entitlementServiceResponse.getEntitlementDTO();

	if (entitlementDTO != null) {
	    if (account instanceof CreditCardAccountDTO) {
		String cardHolderType = entitlementDTO.getCardHolderType();
		if (cardHolderType != null) {
		    CreditCardAccountDTO tempAccount = (CreditCardAccountDTO) account;
		    if (primaryCardRequiredFlag) {
			return true;

		    } else { // sometimes primary card is not required, i.e.
			// registration, forgot XXXX
			if ("P".equals(cardHolderType)) {
			    // primary card only
			    if (tempAccount.getPrimary() != null) {
				// tempAccount.setSupplementaries(null);
				isEntitled = true;
			    } else {
				isEntitled = false;
			    }
			} else if ("S".equals(cardHolderType)) {
			    // supplementary card only
			    if (tempAccount.getSupplementaries() != null && tempAccount.getSupplementaries().size() > 0) {
				isEntitled = true;
			    } else {
				isEntitled = false;
			    }
			} else if ("B".equals(cardHolderType)) {
			    // both
			    if (tempAccount.getPrimary() != null
				    || (tempAccount.getSupplementaries() != null && tempAccount.getSupplementaries().size() > 0)) {
				isEntitled = true;
			    } else {
				isEntitled = false;
			    }
			}
		    }
		} else {
		    isEntitled = false;
		}
	    } else {
		String relationship = entitlementDTO.getRelationship();
		if (!StringUtils.isEmpty(relationship)) {
		    String[] relationArray = relationship.split(",");
		    // "1,5"
		    String relationCode = account.getRelationshipCode();
		    if (StringUtils.isEmpty(relationCode)) {
			return false;
		    }
		    String[] relationCodeArray = relationCode.split(",");

		    for (String relation : relationArray) {
			for (String code : relationCodeArray) {
			    if (relation.trim().equals(code.trim())) {
				isEntitled = false;
				break;
			    }
			}

		    }

		}
	    }

	}

	return isEntitled;
    }

    /**
     * @param actsList
     * @param prodEligibilityServiceResponse
     * @return
     */
    private List<CustomerAccountDTO> filterAccounts(List<? extends CustomerAccountDTO> actsList,
	    ProductEligibilityListServiceResponse prodEligibilityServiceResponse) {

	List<ProductEligibilityDTO> peList = prodEligibilityServiceResponse.getProductEligibilityList();
	List<CustomerAccountDTO> rstActsList = new ArrayList<CustomerAccountDTO>();
	for (ProductEligibilityDTO prdElg : peList) {
	    String prdCat = prdElg.getProductCategory();

	    List<? extends CustomerAccountDTO> tempList = getAccountsByCategory(actsList, prdCat, prodEligibilityServiceResponse.getContext());

	    rstActsList.addAll(tempList);
	}

	return rstActsList;
    }

    public boolean isAccountEligible(CustomerAccountDTO account, ProductEligibilityServiceRequest request) {
	return isAccountEligible(account, request, true);
    }

    /**
     * @param account
     * @param request
     * @return
     */
    private boolean isAccountEligible(CustomerAccountDTO account, ProductEligibilityServiceRequest request, boolean primaryOnly) {

	// boolean isEligible = false;
	if (account != null && account instanceof InvestmentAccountDTO) {
	    return true;
	}

	Context context = request.getContext();

	if (account != null) {
	    if (account instanceof CASAAccountDTO) {

		if (account.getInternetBankingAccessFlag() != null && !account.getInternetBankingAccessFlag()) {
		    return false;
		}
		ProductServiceRequest productServiceRequest = new ProductServiceRequest();
		ProductDTO productDTO = new ProductDTO();
		productDTO.setBusinessID(context.getBusinessId());
		productDTO.setProductCode(account.getProductCode());

		productDTO.setCurrencyCode(account.getCurrency());

		productServiceRequest.setContext(context);
		productServiceRequest.setCurrencyCode(account.getCurrency());
		productServiceRequest.setProductCode(account.getProductCode());

		ProductServiceResponse productServiceResponse = productDAO.getProductsByCurrencyCode(productServiceRequest);

		ProductDTO product = productServiceResponse.getProduct();

		if (product == null || product.getInternetEnabled() == null || !"Y".equalsIgnoreCase(product.getInternetEnabled())) {
		    return false;
		}

	    }

	    // Set Product Category

	    String productCategory = null;
	    if (account instanceof CASAAccountDTO) {
		productCategory = "CASA";
	    } else if (account instanceof TdAccountDTO) {
		productCategory = "TMD";
	    } else if (account instanceof CreditCardAccountDTO) {
		productCategory = "CCD";
	    } else if (account instanceof LoanAccountDTO) {
		productCategory = "LOAN";
	    } else if (account instanceof InsuranceAccountDTO) {
		productCategory = "INS";
	    } else if (account instanceof InvestmentAccountDTO) {
		productCategory = "INV";
	    } else {
		productCategory = account.getProductCategory();
	    }

	    List<ProductEligibilityDTO> incList = null;
	    List<ProductEligibilityDTO> excList = null;

	    ProductEligibilityServiceRequest productEligibilityServiceRequest = new ProductEligibilityServiceRequest();
	    productEligibilityServiceRequest.setProductCategory(productCategory);

	    productEligibilityServiceRequest.setContext(context);
	    // productEligibilityServiceRequest.setIncOrExc("INC");
	    productEligibilityServiceRequest.setProductCode(account.getProductCode());
	    ProductEligibilityListServiceResponse response = productEligibilityDAO.getProductEligibilityByIncOrExc(productEligibilityServiceRequest);
	    List<ProductEligibilityDTO> peList = response.getProductEligibilityList();

	    if (peList != null && !peList.isEmpty()) {
		incList = new ArrayList<ProductEligibilityDTO>();
		excList = new ArrayList<ProductEligibilityDTO>();
		for (ProductEligibilityDTO peDTO : peList) {
		    if (INC.equals(peDTO.getIncOrExc())) {
			incList.add(peDTO);
		    } else if (EXC.equals(peDTO.getIncOrExc())) {
			excList.add(peDTO);
		    }
		}
	    }

	    if (incList != null && incList.size() == 1) {
		ProductEligibilityDTO productEligibility = incList.get(0);
		return incEligibility(productEligibility, account, primaryOnly);
	    }

	    if (excList != null && excList.size() == 1) {
		ProductEligibilityDTO productEligibility = excList.get(0);
		return excEligibility(productEligibility, account, primaryOnly);
	    }

	    // productEligibilityServiceRequest.setIncOrExc("INC");
	    productEligibilityServiceRequest.setProductCode(null);

	    response = productEligibilityDAO.getProductEligibilityByIncOrExc(productEligibilityServiceRequest);
	    peList = response.getProductEligibilityList();

	    if (peList != null && !peList.isEmpty()) {
		incList = new ArrayList<ProductEligibilityDTO>();
		excList = new ArrayList<ProductEligibilityDTO>();
		for (ProductEligibilityDTO peDTO : peList) {
		    if (INC.equals(peDTO.getIncOrExc())) {
			incList.add(peDTO);
		    } else if (EXC.equals(peDTO.getIncOrExc())) {
			excList.add(peDTO);
		    }
		}
	    }

	    if (incList != null) {
		for (ProductEligibilityDTO child : incList) {

		    if (child.getProductCode() != null) {
			continue;
		    }

		    return incEligibility(child, account, primaryOnly);

		}
	    }

	    if (excList != null) {
		for (ProductEligibilityDTO child : excList) {

		    if (child.getProductCode() != null) {
			continue;
		    }

		    return excEligibility(child, account, primaryOnly);

		}
	    }
	    /*
	     * //Check for INC and product code
	     * 
	     * ProductEligibilityServiceRequest productEligibilityServiceRequest = new ProductEligibilityServiceRequest();
	     * productEligibilityServiceRequest .setProductCategory(productCategory);
	     * 
	     * productEligibilityServiceRequest.setContext(context); productEligibilityServiceRequest.setIncOrExc("INC");
	     * productEligibilityServiceRequest .setProductCode(account.getProductCode()); ProductEligibilityListServiceResponse response =
	     * productEligibilityDAO .getProductEligibilityByIncOrExc(productEligibilityServiceRequest ); List<ProductEligibilityDTO> peList =
	     * response.getProductEligibilityList();
	     * 
	     * if (peList != null && peList.size() == 1) { ProductEligibilityDTO productEligibility = peList.get(0); return
	     * incEligibility(productEligibility, account, primaryOnly); }
	     * 
	     * //Check for EXC and product code
	     * 
	     * productEligibilityServiceRequest.setIncOrExc("EXC"); productEligibilityServiceRequest .setProductCode(account.getProductCode());
	     * response = productEligibilityDAO .getProductEligibilityByIncOrExc(productEligibilityServiceRequest ); peList =
	     * response.getProductEligibilityList(); if (peList != null && peList.size() == 1) { ProductEligibilityDTO productEligibility =
	     * peList.get(0); return excEligibility(productEligibility, account, primaryOnly); }
	     * 
	     * 
	     * //Check for all INC
	     * 
	     * productEligibilityServiceRequest.setIncOrExc("INC"); productEligibilityServiceRequest.setProductCode(null);
	     * 
	     * response =productEligibilityDAO.getProductEligibilityByIncOrExc( productEligibilityServiceRequest); peList =
	     * response.getProductEligibilityList(); if (peList != null ) { for (ProductEligibilityDTO child : peList) {
	     * 
	     * if (child.getProductCode() != null) { continue; }
	     * 
	     * return incEligibility(child, account, primaryOnly);
	     * 
	     * } }
	     * 
	     * 
	     * //Check for all EXC
	     * 
	     * productEligibilityServiceRequest.setIncOrExc("EXC"); productEligibilityServiceRequest.setProductCode(null);
	     * 
	     * response =productEligibilityDAO.getProductEligibilityByIncOrExc( productEligibilityServiceRequest); peList =
	     * response.getProductEligibilityList(); if (peList != null ) { for (ProductEligibilityDTO child : peList) {
	     * 
	     * if (child.getProductCode() != null) { continue; }
	     * 
	     * return excEligibility(child, account, primaryOnly);
	     * 
	     * } }
	     */

	}

	return false;
    }

    /**
     * @param productEligibility
     * @param account
     * @return
     */
    private boolean incEligibility(ProductEligibilityDTO productEligibility, CustomerAccountDTO account, boolean primaryOnly) {
	String accountStatus = productEligibility.getAccountStatus();

	if (accountStatus != null) {
	    // accountStatus is not null.
	    String[] accountStatusList = accountStatus.split(",");

	    for (String status : accountStatusList) {
		// account status has higher priority.
		if (status.trim().equalsIgnoreCase(account.getAccountStatus() == null ? null : account.getAccountStatus().trim())) {

		    if (incCardEligibility(productEligibility, account, primaryOnly)) {
			return true;
		    }

		}
	    }
	}

	return false;
    }

    /**
     * return is product eligibility and filter card
     */
    private boolean incCardEligibility(ProductEligibilityDTO productEligibility, CustomerAccountDTO account, boolean primaryOnly) {

	if (account instanceof CreditCardAccountDTO) {
	    String cardStatusCondition = productEligibility.getCardStatus();
	    if (cardStatusCondition != null) {
		// cardStatus is not null.
		CreditCardAccountDTO creditCardAccount = (CreditCardAccountDTO) account;

		String[] cardStatus_db = cardStatusCondition.split(",");

		boolean result = false;
		Arrays.sort(cardStatus_db);

		if (null != creditCardAccount.getPrimary()) {

		    String CCcardStatus = creditCardAccount.getPrimary().getCardStatus();

		    if (StringUtils.isNotBlank(CCcardStatus)) {
			if (Arrays.binarySearch(cardStatus_db, CCcardStatus) >= 0) {
			    result = true;
			} else {
			    if (primaryOnly)
				return false;
			    creditCardAccount.setPrimary(null);
			}
		    }
		}
		// Add Start By Elicer for remove the filter for Supplement Card
		if (!primaryOnly) {
		    List<CreditCardDTO> supplementaries = creditCardAccount.getSupplementaries();
		    List<CreditCardDTO> newSupplementaries = new ArrayList<CreditCardDTO>();
		    if (supplementaries != null) {
			for (CreditCardDTO supplementary : supplementaries) {
			    if (null != supplementary) {

				String CCcardStatus = supplementary.getCardStatus();

				if (StringUtils.isNotBlank(CCcardStatus)) {
				    if (Arrays.binarySearch(cardStatus_db, CCcardStatus) >= 0) {
					result = true;
					newSupplementaries.add(supplementary);
				    }
				}
			    }
			}
			creditCardAccount.setSupplementaries(newSupplementaries);
		    }
		}

		return result;

	    }
	} else {
	    return true;
	}

	return false;
    }

    /**
     * @param productEligibility
     * @param account
     * @return
     */
    private boolean excEligibility(ProductEligibilityDTO productEligibility, CustomerAccountDTO account, boolean primaryOnly) {
	String accountStatus = productEligibility.getAccountStatus();

	if (accountStatus != null) {
	    // accountStatus is not null.
	    String[] accountStatusList = accountStatus.split(",");

	    for (String status : accountStatusList) {
		// account status has higher priority.
		if (status.trim().equalsIgnoreCase(account.getAccountStatus() == null ? null : account.getAccountStatus().trim())) {

		    return false;

		}
	    }

	    // account status has higher priority.
	    return excCardEligibility(productEligibility, account, primaryOnly);
	}

	return true;
    }

    private boolean excCardEligibility(ProductEligibilityDTO productEligibility, CustomerAccountDTO account, boolean primaryOnly) {

	if (account instanceof CreditCardAccountDTO) {
	    String cardStatusCondition = productEligibility.getCardStatus();
	    if (cardStatusCondition != null) {
		// cardStatus is not null.
		CreditCardAccountDTO creditCardAccount = (CreditCardAccountDTO) account;

		String[] cardStatus_db = cardStatusCondition.split(",");

		boolean result = false;
		Arrays.sort(cardStatus_db);

		if (null != creditCardAccount.getPrimary()) {

		    String CCcardStatus = creditCardAccount.getPrimary().getCardStatus();

		    if (StringUtils.isNotBlank(CCcardStatus)) {
			if (Arrays.binarySearch(cardStatus_db, CCcardStatus) < 0) {
			    result = true;
			} else {
			    if (primaryOnly)
				return false;
			    creditCardAccount.setPrimary(null);
			}
		    }
		}
		// remove the filter for Supplement Card
		if (!primaryOnly) {
		    List<CreditCardDTO> supplementaries = creditCardAccount.getSupplementaries();
		    List<CreditCardDTO> newSupplementaries = new ArrayList<CreditCardDTO>();
		    if (supplementaries != null) {
			for (CreditCardDTO supplementary : supplementaries) {
			    if (null != supplementary) {

				String CCcardStatus = supplementary.getCardStatus();

				if (StringUtils.isNotBlank(CCcardStatus)) {

				    if (Arrays.binarySearch(cardStatus_db, CCcardStatus) < 0) {
					result = true;
					newSupplementaries.add(supplementary);
				    }
				}
			    }
			}
			creditCardAccount.setSupplementaries(newSupplementaries);
		    }
		}

		return result;

	    }
	} else {
	    return true;
	}

	return true;
    }

    /**
     * @param actsList
     * @param prdCat
     * @param context
     * @return
     */
    private List<? extends CustomerAccountDTO> getAccountsByCategory(List<? extends CustomerAccountDTO> actsList, String prdCat, Context context) {

	List<? extends CustomerAccountDTO> rstActsList = null;
	if ("CASA".equals(prdCat)) {
	    rstActsList = getCASAAccounts(actsList, context);
	} else if ("LOAN".equals(prdCat)) {
	    rstActsList = getLoanAccounts(actsList, context);
	} else if ("TMD".equals(prdCat)) {
	    rstActsList = getTdAccounts(actsList, context);
	} else if ("CCD".equals(prdCat)) {
	    rstActsList = getCreditCardAccounts(actsList, context);
	} else if ("INS".equals(prdCat)) {
	    rstActsList = getInsuranceAccounts(actsList, context);
	} else if ("INV".equals(prdCat)) {
	    rstActsList = getInvestmentAccounts(actsList, context);
	}

	return rstActsList;
    }

    /**
     * @param actsList
     * @param context
     * @return
     */
    private List<CASAAccountDTO> getCASAAccounts(List<? extends CustomerAccountDTO> actsList, Context context) {

	List<CASAAccountDTO> rstList = new ArrayList<CASAAccountDTO>();
	for (CustomerAccountDTO account : actsList) {
	    if (account instanceof CASAAccountDTO) {
		CASAAccountDTO tempAccount = (CASAAccountDTO) account;
		Boolean internetFlag = retrieveInternetFlag(account, context);
		if (internetFlag != null && internetFlag) {

		    rstList.add(tempAccount);
		}
	    }
	}

	return rstList;
    }

    /**
     * @param actsList
     * @param context
     * @return
     */
    private List<LoanAccountDTO> getLoanAccounts(List<? extends CustomerAccountDTO> actsList, Context context) {

	List<LoanAccountDTO> rstList = new ArrayList<LoanAccountDTO>();
	for (CustomerAccountDTO account : actsList) {
	    if (account instanceof LoanAccountDTO) {
		LoanAccountDTO tempAccount = (LoanAccountDTO) account;
		rstList.add(tempAccount);

	    }
	}

	return rstList;
    }

    /**
     * @param actsList
     * @param context
     * @return
     */
    private List<TdAccountDTO> getTdAccounts(List<? extends CustomerAccountDTO> actsList, Context context) {

	List<TdAccountDTO> rstList = new ArrayList<TdAccountDTO>();
	for (CustomerAccountDTO account : actsList) {
	    if (account instanceof TdAccountDTO) {
		TdAccountDTO tempAccount = (TdAccountDTO) account;
		rstList.add(tempAccount);

	    }
	}

	return rstList;
    }

    /**
     * @param actsList
     * @param context
     * @return
     */
    private List<CreditCardAccountDTO> getCreditCardAccounts(List<? extends CustomerAccountDTO> actsList, Context context) {

	List<CreditCardAccountDTO> rstList = new ArrayList<CreditCardAccountDTO>();
	for (CustomerAccountDTO account : actsList) {
	    if (account instanceof CreditCardAccountDTO) {
		CreditCardAccountDTO tempAccount = (CreditCardAccountDTO) account;
		rstList.add(tempAccount);

	    }
	}

	return rstList;
    }

    /**
     * @param actsList
     * @param context
     * @return
     */
    private List<InvestmentAccountDTO> getInvestmentAccounts(List<? extends CustomerAccountDTO> actsList, Context context) {

	List<InvestmentAccountDTO> rstList = new ArrayList<InvestmentAccountDTO>();
	for (CustomerAccountDTO account : actsList) {
	    if (account instanceof InvestmentAccountDTO) {
		InvestmentAccountDTO tempAccount = (InvestmentAccountDTO) account;
		rstList.add(tempAccount);

	    }
	}

	return rstList;
    }

    /**
     * @param actsList
     * @param context
     * @return
     */
    private List<InsuranceAccountDTO> getInsuranceAccounts(List<? extends CustomerAccountDTO> actsList, Context context) {

	List<InsuranceAccountDTO> rstList = new ArrayList<InsuranceAccountDTO>();
	for (CustomerAccountDTO account : actsList) {
	    if (account instanceof InsuranceAccountDTO) {
		InsuranceAccountDTO tempAccount = (InsuranceAccountDTO) account;
		rstList.add(tempAccount);

	    }
	}

	return rstList;
    }

    /**
     * @param account
     * @param context
     * @return
     */
    private Boolean retrieveInternetFlag(CustomerAccountDTO account, Context context) {
	Boolean internetFlag = account.getInternetBankingAccessFlag();

	Boolean internetSupportFlag = getSystemParameterBooleanValueById(context, "INTERNET_SUPPORT_FLAG_ON_ACCOUNT", "COMMON");

	if (internetSupportFlag) {
	    if (internetFlag == null) {
		account.setInternetBankingAccessFlag(false);
	    } else {
		internetFlag = retrieveInternetAccessFlag(context, account.getProductCode(), account.getCurrency());
		account.setInternetBankingAccessFlag(internetFlag);
	    }

	} else {
	    if (internetFlag == null) {
		internetFlag = retrieveInternetAccessFlag(context, account.getProductCode(), account.getCurrency());

		account.setInternetBankingAccessFlag(internetFlag);

	    }
	}

	internetFlag = account.getInternetBankingAccessFlag();

	return internetFlag;
    }

    /**
     * @param context
     * @param paramId
     * @param activityId
     * @return
     */
    private Boolean getSystemParameterBooleanValueById(Context context, String paramId, String activityId) {

	Boolean result = false;
	SystemParameterServiceRequest systemParameterServiceRequest = new SystemParameterServiceRequest();
	systemParameterServiceRequest.setContext(context);

	SystemParameterDTO systemParameterDTO = new SystemParameterDTO();
	systemParameterDTO.setActivityId(activityId);
	systemParameterDTO.setBusinessId(context.getBusinessId());
	systemParameterDTO.setSystemId(context.getSystemId());
	systemParameterDTO.setParameterId(paramId);

	systemParameterServiceRequest.setSystemParameterDTO(systemParameterDTO);

	SystemParameterServiceResponse systemParameterServiceResponse = systemParameterDAO.getSystemParameter(systemParameterServiceRequest);

	systemParameterDTO = systemParameterServiceResponse.getSystemParameterDTO();

	if (systemParameterDTO != null && systemParameterDTO.getParameterValue() != null) {
	    String paramVal = systemParameterDTO.getParameterValue();
	    result = "Y".equals(paramVal);

	}

	return result;
    }

    /**
     * @param context
     * @param prdCde
     * @param curr
     * @return
     */
    private Boolean retrieveInternetAccessFlag(Context context, String prdCde, String curr) {
	Boolean result = false;

	ProductServiceRequest productServiceRequest = new ProductServiceRequest();

	productServiceRequest.setContext(context);
	productServiceRequest.setProductCode(prdCde);
	productServiceRequest.setCurrencyCode(curr);

	ProductServiceResponse productServiceResponse = productDAO.getProductsByCurrencyCode(productServiceRequest);

	ProductDTO product = productServiceResponse.getProduct();

	if (product != null && product.getInternetEnabled() != null) {
	    result = "Y".equals(product.getInternetEnabled());
	}

	return result;

    }

    /**
     * Set account asset and debt according to the account type TODO Method description, including pre/post conditions and invariants.
     * 
     * @param sscContext
     * @param account
     */
    protected void setAccountAssetDebt(CustomerAccountDTO account, Context context) {
	if (account instanceof CASAAccountDTO) {
	    CASAAccountDTO tempAccount = (CASAAccountDTO) account;
	    setAssetDebt(context, tempAccount, tempAccount.getAvailableBalance(), BigDecimal.valueOf(0));
	} else if (account instanceof TdAccountDTO) {
	    TdAccountDTO tempAccount = (TdAccountDTO) account;
	    setAssetDebt(context, tempAccount, tempAccount.getCurrentBalance(), BigDecimal.valueOf(0));
	} else if (account instanceof LoanAccountDTO) {
	    LoanAccountDTO tempAccount = (LoanAccountDTO) account;
	    setAssetDebt(context, tempAccount, BigDecimal.valueOf(0), tempAccount.getLoanAmount());
	} else if (account instanceof CreditCardAccountDTO) {
	    CreditCardAccountDTO tempAccount = (CreditCardAccountDTO) account;
	    setAssetDebt(context, tempAccount, BigDecimal.valueOf(0), tempAccount.getCurrentBalance());
	} else if (account instanceof InsuranceAccountDTO) {
	    InsuranceAccountDTO tempAccount = (InsuranceAccountDTO) account;
	    setAssetDebt(context, tempAccount, tempAccount.getSumAssured(), BigDecimal.valueOf(0));
	} else if (account instanceof InvestmentAccountDTO) {
	    InvestmentAccountDTO tempAccount = (InvestmentAccountDTO) account;
	    setAssetDebt(context, tempAccount, tempAccount.getCurrentMarketValue(), BigDecimal.valueOf(0));
	}

    }

    protected void setAssetDebt(Context context, CustomerAccountDTO account, BigDecimal asset, BigDecimal debt) {
	BigDecimal updatedasset = asset;
	BigDecimal updateddebt = debt;
	Map<String, BigDecimal> rateMap = context.getCurrencyConversionMap();
	BigDecimal rate = BigDecimal.ONE;

	if (rateMap != null) {
	    if (rateMap.containsKey(account.getCurrency())) {
		rate = rateMap.get(account.getCurrency());
	    }
	}

	updatedasset = updatedasset.setScale(2, BigDecimal.ROUND_HALF_UP);
	updateddebt = updateddebt.setScale(2, BigDecimal.ROUND_HALF_UP);

	account.setAsset(updatedasset.multiply(rate).abs());
	account.setDebt(updateddebt.multiply(rate).abs());
    }

    public ProductDAO getProductDAO() {
	return productDAO;
    }

    public void setProductDAO(ProductDAO productDAO) {
	this.productDAO = productDAO;
    }

    public SystemParameterDAO getSystemParameterDAO() {
	return systemParameterDAO;
    }

    public void setSystemParameterDAO(SystemParameterDAO systemParameterDAO) {
	this.systemParameterDAO = systemParameterDAO;
    }

    public EntitlementDAO getEntitlementDAO() {
	return entitlementDAO;
    }

    public void setEntitlementDAO(EntitlementDAO entitlementDAO) {
	this.entitlementDAO = entitlementDAO;
    }

}
