package com.barclays.bmg.dto;

import java.util.List;

/**
 * @author e20027734
 * 
 */
public class CategorizedPayeeListDTO extends BaseDomainDTO {

    /**
	 *
	 */
    private static final long serialVersionUID = -3060799708456631284L;
    private String payeeCategory;
    private List<BeneficiaryDTO> payeeList;
    private List<? extends CustomerAccountDTO> destAccountList;
    private String transactionFacadeRoutineType;

    public String getPayeeCategory() {
	return payeeCategory;
    }

    public void setPayeeCategory(String payeeCategory) {
	this.payeeCategory = payeeCategory;
    }

    public List<BeneficiaryDTO> getPayeeList() {
	return payeeList;
    }

    public void setPayeeList(List<BeneficiaryDTO> payeeList) {
	this.payeeList = payeeList;
    }

    public String getTransactionFacadeRoutineType() {
	return transactionFacadeRoutineType;
    }

    public void setTransactionFacadeRoutineType(String transactionFacadeRoutineType) {
	this.transactionFacadeRoutineType = transactionFacadeRoutineType;
    }

    public List<? extends CustomerAccountDTO> getDestAccountList() {
	return destAccountList;
    }

    public void setDestAccountList(List<? extends CustomerAccountDTO> destAccountList) {
	this.destAccountList = destAccountList;
    }

}
