package com.barclays.bmg.operation;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.barclays.bmg.operation.addorgBeneficiary.AddOrgBeneficiaryOperationTest;
import com.barclays.bmg.operation.applyTD.ApplyTDExecuteControllerOperationTest;
import com.barclays.bmg.operation.applyTD.ApplyTDValidationControllerOperationTest;
import com.barclays.bmg.operation.formvalidation.FormValidateOperationTest;
import com.barclays.bmg.operation.formvalidation.OwnFundTransferRel1ValidateOperationTest;
import com.barclays.bmg.operation.formvalidation.TransactionLimitOperationTest;
import com.barclays.bmg.operation.internalFundTransferOTH.InternalNonRegisteredOneTimeFundTransfer_AllOperationsTest;
import com.barclays.bmg.operation.internalFundTransferOTH.InternalNonRegisteredOneTimeFundTransfer_ExecutionOperationsTest;
import com.barclays.bmg.operation.payments.DomesticFundTransferExecuteOperationTest;
import com.barclays.bmg.operation.validateUser.ValidateUserExecuteControllerOperationTest;
import com.barclays.bmg.operation.validation.fundtransfer.own.ValidateAccountListOperationTest;

@RunWith(Suite.class)
@Suite.SuiteClasses( { InternalNonRegisteredOneTimeFundTransfer_ExecutionOperationsTest.class,
		InternalNonRegisteredOneTimeFundTransfer_AllOperationsTest.class,
		ApplyTDValidationControllerOperationTest.class,
		ApplyTDExecuteControllerOperationTest.class,
		//AddOrgBeneficiaryExecuteControllerOperationTest.class,
		AddOrgBeneficiaryOperationTest.class,
		//GetSelectedAccountOperationTest.class,
		//RetrieveAccountListOperationTest.class,
		FormValidateOperationTest.class,
		OwnFundTransferRel1ValidateOperationTest.class,
		TransactionLimitOperationTest.class,
		DomesticFundTransferExecuteOperationTest.class,
		ValidateAccountListOperationTest.class,
		/*RetrievePayeeInfoOperationTest.class,
		FilterUrgentPayeeListOperationTest.class,
		RetrievePayeeListOperationTest.class,
		ExternalFundTransferDataOperationTest.class,*/
		ValidateUserExecuteControllerOperationTest.class})
/*
 * ValidateUserValidationControllerOperationTest.class,ValidateUserInitControllerOperationTest
 * .class,
 */
public final class RunAllTests {
}
