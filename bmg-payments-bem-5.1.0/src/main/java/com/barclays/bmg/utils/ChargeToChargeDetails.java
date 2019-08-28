package com.barclays.bmg.utils;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bem.ChargeDetails.ChargeDetails;
import com.barclays.bem.TransactionAccount.TransactionAccount;
import com.barclays.bem.TransactionFxRate.TransactionFxRate;
import com.barclays.bmg.dto.Charge;

public class ChargeToChargeDetails {

    public static ChargeDetails[] convertToChargeDetails(List<Charge> charges) {
	List<ChargeDetails> cs = new ArrayList<ChargeDetails>();
	if (charges != null) {
	    for (Charge charge : charges) {
		ChargeDetails c = new ChargeDetails();
		c.setChargeTypeCode(charge.getChargeTypeCode());
		c.setChargeAmount(charge.getChargeAmount().getAmount().doubleValue());
		c.setChargeAmountCurrencyCode(charge.getChargeAmount().getCurrency());

		c.setChargeWaiverFlag(charge.isWaiverFlag());
		TransactionFxRate fx = new TransactionFxRate();

		c.setTransactionChargeFxRate(fx);
		fx.setEffectiveFXRate(charge.getEffectiveFXRate().doubleValue());
		fx.setLCYToTargetFXRate(charge.getLCYToTargetFXRate().doubleValue());
		fx.setSourceToLCYFXRate(charge.getSourceToLCYFXRate().doubleValue());
		TransactionAccount account = new TransactionAccount();
		c.setChargeAccount(account);
		account.setAccountCurrencyCode(charge.getDebitAccountCurrency());
		account.setAccountNumber(charge.getDebitAccountNumber());
		c.setLCYChargeAmount(charge.getChargeAmountInLCY().getAmount().doubleValue());
		c.setIsChargeUpdated(charge.isUpdated());
		cs.add(c);
	    }
	}
	return cs.toArray(new ChargeDetails[0]);

    }

}
