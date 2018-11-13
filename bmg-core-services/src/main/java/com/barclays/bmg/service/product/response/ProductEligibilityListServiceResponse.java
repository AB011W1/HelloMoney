package com.barclays.bmg.service.product.response;

import java.math.BigDecimal;
import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.Product;
import com.barclays.bmg.dto.ProductEligibilityDTO;

public class ProductEligibilityListServiceResponse extends ResponseContext {

    /**
	 *
	 */
    private static final long serialVersionUID = 4196303698660235960L;
    private List<ProductEligibilityDTO> productEligibilityList;
    private List<Product> productList;
    private List<? extends CustomerAccountDTO> accountList;
    private BigDecimal totalAsset = new BigDecimal(0);
    private BigDecimal totalDebt = new BigDecimal(0);
    private BigDecimal totalNetWorth = new BigDecimal(0);

    public List<? extends CustomerAccountDTO> getAccountList() {
	return accountList;
    }

    public void setAccountList(List<? extends CustomerAccountDTO> accountList) {
	this.accountList = accountList;
    }

    public List<Product> getProductList() {
	return productList;
    }

    public void setProductList(List<Product> productList) {
	this.productList = productList;
    }

    public List<ProductEligibilityDTO> getProductEligibilityList() {
	return productEligibilityList;
    }

    public void setProductEligibilityList(List<ProductEligibilityDTO> productEligibilityList) {
	this.productEligibilityList = productEligibilityList;
    }

    public BigDecimal getTotalAsset() {
	return totalAsset;
    }

    public void setTotalAsset(BigDecimal totalAsset) {
	this.totalAsset = totalAsset;
    }

    public BigDecimal getTotalDebt() {
	return totalDebt;
    }

    public void setTotalDebt(BigDecimal totalDebt) {
	this.totalDebt = totalDebt;
    }

    public BigDecimal getTotalNetWorth() {
	return totalNetWorth;
    }

    public void setTotalNetWorth(BigDecimal totalNetWorth) {
	this.totalNetWorth = totalNetWorth;
    }

}
