/* Copyright 2008 Barclays PLC */

/****************************
 * Revision History **************************************************** Version Author Date
 * Description 0.1 Elicer Zheng 2009/02/08 Initial version
 *
 *
 **************************************************************************************************/

package com.barclays.bmg.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.barclays.bmg.type.Currency;
import com.barclays.bmg.type.RateAmount;
import com.barclays.bmg.type.Tenure;

/**
 * @author Scott Li
 * 
 */
public class LoanAccountDTO extends CustomerAccountDTO {
    /** TODO Comment for <code>serialVersionUID</code>. */

    private static final long serialVersionUID = -1685289635070368397L;
    private Date loanStartDate;
    private Date loanEndDate;
    private Date lastDisbursementDate;
    private Date firstInstallmentDate;
    private BigDecimal sanctionAmount;
    private BigDecimal disbursalAmount;
    private Frequent interestRepaymentFreque;
    private Frequent principalRepaymentFreque;
    private BigDecimal principalBalance;
    private BigDecimal otherPrincipalBalance;
    private BigDecimal penaltyAmount;
    private BigDecimal serviceChargeFees;
    private BigDecimal installmentArrears;
    private BigDecimal outstandingLoanAmount;

    private Date nextInstallmentDueDate;
    private BigDecimal nextInstallmentDueAmount;
    private BigDecimal currentInterestPaid;
    private BigDecimal previousInterestPaid;
    private BigDecimal currentPrincipalPaid;
    private BigDecimal previousPrincipalPaid;
    private BigDecimal advanceAmount;

    private String disbursalStatus;
    private Tenure tenure;

    // private String maskAccountNumber;
    // for loanRepayment
    private BigDecimal advancePayment;
    private BigDecimal EMI;
    private BigDecimal dueAmount;
    private String advanceMonth;
    private BigDecimal paymentAmount;

    // TODO After Mapping FEPI
    private String customerType;
    private String customerName;
    private String installmentNo;
    private BigDecimal billingAmount;
    private BigDecimal lateCharge;
    private BigDecimal penaltyInterestRate;
    private String serialNo;
    private BigDecimal loanAmount;
    private Date dueDate;
    private BigDecimal installmentDueAmount;
    private RateAmount interestRate;
    private Date loanMaturityDate;
    private Currency currencyCode;

    // private Frequent frequent;

    private RateAmount penalInterestRate;

    private boolean requestFlg = true;

    /**
     * Default Constructor.
     */
    public LoanAccountDTO() {
	super();

    }

    /**
     * @return the disbursalStatus
     */
    public String getDisbursalStatus() {
	return disbursalStatus;
    }

    /**
     * @param disbursalStatus
     *            the disbursalStatus to set
     */
    public void setDisbursalStatus(String disbursalStatus) {
	this.disbursalStatus = disbursalStatus;
    }

    /**
     * @return the tenure
     */
    public Tenure getTenure() {
	return tenure;
    }

    /**
     * @param tenure
     *            the tenure to set
     */
    public void setTenure(Tenure tenure) {
	this.tenure = tenure;
    }

    /**
     * @return the currencyCode
     */
    public Currency getCurrencyCode() {
	return currencyCode;
    }

    /**
     * @param currencyCode
     *            the currencyCode to set
     */
    public void setCurrencyCode(Currency currencyCode) {
	this.currencyCode = currencyCode;
    }

    /**
     * @return the loanMaturityDate
     */
    public Date getLoanMaturityDate() {
	if (loanMaturityDate != null) {
	    return new Date(loanMaturityDate.getTime());
	}
	return null;
    }

    /**
     * @param loanMaturityDate
     *            the loanMaturityDate to set
     */
    public void setLoanMaturityDate(Date loanMaturityDate) {
	if (loanMaturityDate != null) {
	    this.loanMaturityDate = new Date(loanMaturityDate.getTime());
	} else {
	    this.loanMaturityDate = null;
	}
    }

    /**
     * @return the interestRate
     */
    public RateAmount getInterestRate() {
	return interestRate;
    }

    /**
     * @param interestRate
     *            the interestRate to set
     */
    public void setInterestRate(RateAmount interestRate) {
	this.interestRate = interestRate;
    }

    /**
     * @return the dueDate
     */
    public Date getDueDate() {
	if (dueDate != null) {
	    return new Date(dueDate.getTime());
	}
	return null;
    }

    /**
     * @param dueDate
     *            the dueDate to set
     */
    public void setDueDate(Date dueDate) {
	if (dueDate != null) {
	    this.dueDate = new Date(dueDate.getTime());
	} else {
	    this.dueDate = null;
	}
    }

    /**
     * @return the installmentDueAmount
     */
    public BigDecimal getInstallmentDueAmount() {
	return installmentDueAmount;
    }

    /**
     * @param installmentDueAmount
     *            the installmentDueAmount to set
     */
    public void setInstallmentDueAmount(BigDecimal installmentDueAmount) {
	this.installmentDueAmount = installmentDueAmount;
    }

    /**
     * @return the loanAmount
     */
    public BigDecimal getLoanAmount() {
	return loanAmount;
    }

    /**
     * @param loanAmount
     *            the loanAmount to set
     */
    public void setLoanAmount(BigDecimal loanAmount) {
	this.loanAmount = loanAmount;
    }

    /**
     * @return the serialNo
     */
    public String getSerialNo() {
	return serialNo;
    }

    /**
     * @param serialNo
     *            the serialNo to set
     */
    public void setSerialNo(String serialNo) {
	this.serialNo = serialNo;
    }

    /**
     * @return the penaltyInterestRate
     */
    public BigDecimal getPenaltyInterestRate() {
	return penaltyInterestRate;
    }

    /**
     * @param penaltyInterestRate
     *            the penaltyInterestRate to set
     */
    public void setPenaltyInterestRate(BigDecimal penaltyInterestRate) {
	this.penaltyInterestRate = penaltyInterestRate;
    }

    /**
     * @return the lateCharge
     */
    public BigDecimal getLateCharge() {
	return lateCharge;
    }

    /**
     * @param lateCharge
     *            the lateCharge to set
     */
    public void setLateCharge(BigDecimal lateCharge) {
	this.lateCharge = lateCharge;
    }

    /**
     * @return the billingAmount
     */
    public BigDecimal getBillingAmount() {
	return billingAmount;
    }

    /**
     * @param billingAmount
     *            the billingAmount to set
     */
    public void setBillingAmount(BigDecimal billingAmount) {
	this.billingAmount = billingAmount;
    }

    /**
     * @return the installmentNo
     */
    public String getInstallmentNo() {
	return installmentNo;
    }

    /**
     * @param installmentNo
     *            the installmentNo to set
     */
    public void setInstallmentNo(String installmentNo) {
	this.installmentNo = installmentNo;
    }

    /**
     * @return the customerName
     */
    public String getCustomerName() {
	return customerName;
    }

    /**
     * @param customerName
     *            the customerName to set
     */
    public void setCustomerName(String customerName) {
	this.customerName = customerName;
    }

    /**
     * @return the customerType
     */
    public String getCustomerType() {
	return customerType;
    }

    /**
     * @param customerType
     *            the customerType to set
     */
    public void setCustomerType(String customerType) {
	this.customerType = customerType;
    }

    /**
     * @return the advanceMonth
     */
    public String getAdvanceMonth() {
	return advanceMonth;
    }

    /**
     * @param advanceMonth
     *            the advanceMonth to set
     */
    public void setAdvanceMonth(String advanceMonth) {
	this.advanceMonth = advanceMonth;
    }

    /**
     * @return the advancePayment
     */
    public BigDecimal getAdvancePayment() {
	// TODO: wait for MW supply this value
	this.setAdvancePayment(new BigDecimal(0));
	return advancePayment;
    }

    /**
     * @param advancePayment
     *            the advancePayment to set
     */
    public void setAdvancePayment(BigDecimal advancePayment) {
	this.advancePayment = advancePayment;
    }

    /**
     * @return the loanStartDate
     */
    public Date getLoanStartDate() {
	if (loanStartDate != null) {
	    return new Date(loanStartDate.getTime());
	}
	return null;
    }

    /**
     * @param loanStartDate
     *            the loanStartDate to set
     */
    public void setLoanStartDate(Date loanStartDate) {
	if (loanStartDate != null) {
	    this.loanStartDate = new Date(loanStartDate.getTime());
	} else {
	    this.loanStartDate = null;
	}
    }

    /**
     * @return the loanEndDate
     */
    public Date getLoanEndDate() {
	if (loanEndDate != null) {
	    return new Date(loanEndDate.getTime());
	}
	return null;
    }

    /**
     * @param loanEndDate
     *            the loanEndDate to set
     */
    public void setLoanEndDate(Date loanEndDate) {
	if (loanEndDate != null) {
	    this.loanEndDate = new Date(loanEndDate.getTime());
	} else {
	    this.loanEndDate = null;
	}
    }

    /**
     * @return the lastDisbursementDate
     */
    public Date getLastDisbursementDate() {
	if (lastDisbursementDate != null) {
	    return new Date(lastDisbursementDate.getTime());
	}
	return null;
    }

    /**
     * @param lastDisbursementDate
     *            the lastDisbursementDate to set
     */
    public void setLastDisbursementDate(Date lastDisbursementDate) {
	if (lastDisbursementDate != null) {
	    this.lastDisbursementDate = new Date(lastDisbursementDate.getTime());
	} else {
	    this.lastDisbursementDate = null;
	}
    }

    /**
     * @return the firstInstallmentDate
     */
    public Date getFirstInstallmentDate() {
	if (firstInstallmentDate != null) {
	    return new Date(firstInstallmentDate.getTime());
	}
	return null;
    }

    /**
     * @param firstInstallmentDate
     *            the firstInstallmentDate to set
     */
    public void setFirstInstallmentDate(Date firstInstallmentDate) {
	if (firstInstallmentDate != null) {
	    this.firstInstallmentDate = new Date(firstInstallmentDate.getTime());
	} else {
	    this.firstInstallmentDate = null;
	}
    }

    /**
     * @return the sanctionAmount
     */
    public BigDecimal getSanctionAmount() {
	return sanctionAmount;
    }

    /**
     * @param sanctionAmount
     *            the sanctionAmount to set
     */
    public void setSanctionAmount(BigDecimal sanctionAmount) {
	this.sanctionAmount = sanctionAmount;
    }

    /**
     * @return the disbursalAmount
     */
    public BigDecimal getDisbursalAmount() {
	return disbursalAmount;
    }

    /**
     * @param disbursalAmount
     *            the disbursalAmount to set
     */
    public void setDisbursalAmount(BigDecimal disbursalAmount) {
	this.disbursalAmount = disbursalAmount;
    }

    /**
     * @return the interestRepaymentFreque
     */
    public Frequent getInterestRepaymentFreque() {
	return interestRepaymentFreque;
    }

    /**
     * @param interestRepaymentFreque
     *            the interestRepaymentFreque to set
     */
    public void setInterestRepaymentFreque(Frequent interestRepaymentFreque) {
	this.interestRepaymentFreque = interestRepaymentFreque;
    }

    /**
     * @return the principalRepaymentFreque
     */
    public Frequent getPrincipalRepaymentFreque() {
	return principalRepaymentFreque;
    }

    /**
     * @param principalRepaymentFreque
     *            the principalRepaymentFreque to set
     */
    public void setPrincipalRepaymentFreque(Frequent principalRepaymentFreque) {
	this.principalRepaymentFreque = principalRepaymentFreque;
    }

    /**
     * @return the principalBalance
     */
    public BigDecimal getPrincipalBalance() {
	return principalBalance;
    }

    /**
     * @param principalBalance
     *            the principalBalance to set
     */
    public void setPrincipalBalance(BigDecimal principalBalance) {
	this.principalBalance = principalBalance;
    }

    /**
     * @return the penaltyAmount
     */
    public BigDecimal getPenaltyAmount() {
	return penaltyAmount;
    }

    /**
     * @return the eMI
     */
    public BigDecimal getEMI() {
	return EMI;
    }

    /**
     * @param emi
     *            the eMI to set
     */
    public void setEMI(BigDecimal emi) {
	EMI = emi;
    }

    /**
     * @return the dueAmount
     */
    public BigDecimal getDueAmount() {
	return dueAmount;
    }

    /**
     * @param dueAmount
     *            the dueAmount to set
     */
    public void setDueAmount(BigDecimal dueAmount) {
	this.dueAmount = dueAmount;
    }

    /**
     * @param penaltyAmount
     *            the penaltyAmount to set
     */
    public void setPenaltyAmount(BigDecimal penaltyAmount) {
	this.penaltyAmount = penaltyAmount;
    }

    /**
     * @return the serviceChargeFees
     */
    public BigDecimal getServiceChargeFees() {
	return serviceChargeFees;
    }

    /**
     * @param serviceChargeFees
     *            the serviceChargeFees to set
     */
    public void setServiceChargeFees(BigDecimal serviceChargeFees) {
	this.serviceChargeFees = serviceChargeFees;
    }

    /**
     * @return the installmentArrears
     */
    public BigDecimal getInstallmentArrears() {
	return installmentArrears;
    }

    /**
     * @param installmentArrears
     *            the installmentArrears to set
     */
    public void setInstallmentArrears(BigDecimal installmentArrears) {
	this.installmentArrears = installmentArrears;
    }

    /**
     * @return the outstandingLoanAmount
     */
    public BigDecimal getOutstandingLoanAmount() {
	return outstandingLoanAmount;
    }

    /**
     * @param outstandingLoanAmount
     *            the outstandingLoanAmount to set
     */
    public void setOutstandingLoanAmount(BigDecimal outstandingLoanAmount) {
	this.outstandingLoanAmount = outstandingLoanAmount;
    }

    /**
     * @return the advanceAmount
     */
    public BigDecimal getAdvanceAmount() {

	return this.advanceAmount;
    }

    /**
     * @param advanceAmount
     *            the advanceAmount to set
     */
    public void setAdvanceAmount(BigDecimal advanceAmount) {
	this.advanceAmount = advanceAmount;
    }

    /**
     * @return the nextInstallmentDueDate
     */
    public Date getNextInstallmentDueDate() {
	if (nextInstallmentDueDate != null) {
	    return new Date(nextInstallmentDueDate.getTime());
	}
	return null;
    }

    /**
     * @param nextInstallmentDueDate
     *            the nextInstallmentDueDate to set
     */
    public void setNextInstallmentDueDate(Date nextInstallmentDueDate) {
	if (nextInstallmentDueDate != null) {
	    this.nextInstallmentDueDate = new Date(nextInstallmentDueDate.getTime());
	} else {
	    this.nextInstallmentDueDate = null;
	}
    }

    /**
     * @return the nextInstallmentDueAmount
     */
    public BigDecimal getNextInstallmentDueAmount() {
	return nextInstallmentDueAmount;
    }

    /**
     * @param nextInstallmentDueAmount
     *            the nextInstallmentDueAmount to set
     */
    public void setNextInstallmentDueAmount(BigDecimal nextInstallmentDueAmount) {
	this.nextInstallmentDueAmount = nextInstallmentDueAmount;
    }

    /**
     * @return the currentInterestPaid
     */
    public BigDecimal getCurrentInterestPaid() {
	return currentInterestPaid;
    }

    /**
     * @param currentInterestPaid
     *            the currentInterestPaid to set
     */
    public void setCurrentInterestPaid(BigDecimal currentInterestPaid) {
	this.currentInterestPaid = currentInterestPaid;
    }

    /**
     * @return the previousInterestPaid
     */
    public BigDecimal getPreviousInterestPaid() {
	return previousInterestPaid;
    }

    /**
     * @param previousInterestPaid
     *            the previousInterestPaid to set
     */
    public void setPreviousInterestPaid(BigDecimal previousInterestPaid) {
	this.previousInterestPaid = previousInterestPaid;
    }

    /**
     * @return the currentPrincipalPaid
     */
    public BigDecimal getCurrentPrincipalPaid() {
	return currentPrincipalPaid;
    }

    /**
     * @param currentPrincipalPaid
     *            the currentPrincipalPaid to set
     */
    public void setCurrentPrincipalPaid(BigDecimal currentPrincipalPaid) {
	this.currentPrincipalPaid = currentPrincipalPaid;
    }

    /**
     * @return the previousPrincipalPaid
     */
    public BigDecimal getPreviousPrincipalPaid() {
	return previousPrincipalPaid;
    }

    /**
     * @param previousPrincipalPaid
     *            the previousPrincipalPaid to set
     */
    public void setPreviousPrincipalPaid(BigDecimal previousPrincipalPaid) {
	this.previousPrincipalPaid = previousPrincipalPaid;
    }

    /**
     * @return the pastDue
     */
    public BigDecimal getPaymentAmount() {
	return this.paymentAmount;
    }

    /**
     * @param pastDue
     *            the pastDue to set
     */
    public void setPaymentAmount(BigDecimal pastDue) {
	this.paymentAmount = pastDue;
    }

    public RateAmount getPenalInterestRate() {
	return penalInterestRate;
    }

    public void setPenalInterestRate(RateAmount penalInterestRate) {
	this.penalInterestRate = penalInterestRate;
    }

    public BigDecimal getOtherPrincipalBalance() {
	return otherPrincipalBalance;
    }

    public void setOtherPrincipalBalance(BigDecimal otherPrincipalBalance) {
	this.otherPrincipalBalance = otherPrincipalBalance;
    }

    public boolean isRequestFlg() {
	return requestFlg;
    }

    public void setRequestFlg(boolean requestFlg) {
	this.requestFlg = requestFlg;
    }

    @Override
    public int hashCode() {
	return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
	return super.equals(obj);
    }

}
