package com.barclays.ussd.utils.jsonparsers.hellomoney.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.ussd.auth.bean.CasaAccount;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.auth.bean.UserProfile;
import com.barclays.ussd.bean.LocaleDTO;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.UssdMenuBuilder;
import com.barclays.ussd.utils.jsonparsers.bean.balanceEnq.AvilableBalance;
import com.barclays.ussd.utils.jsonparsers.bean.balanceEnq.CurrentBalance;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.PayHdr;
import com.barclays.ussd.utils.jsonparsers.bean.login.AuthUserData;
import com.barclays.ussd.utils.jsonparsers.bean.login.AuthenticateUserPayData;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerProfile;

public class HelloMoneyDemoJsonParser implements BmgBaseJsonParser {
    @Autowired
    private UssdMenuBuilder ussdMenuBuilder;

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDBlockingException {
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();

	AuthUserData userAuthObj = createMockUserAuthObject(ussdSessionMgmt);
	ussdSessionMgmt.setUserAuthObj(userAuthObj);
	ussdSessionMgmt.setDemoMode(true);
	throw new USSDBlockingException(USSDExceptions.USSD_DEMO_MODE.getBmgCode());
    }

    private AuthUserData createMockUserAuthObject(USSDSessionManagement ussdSessionMgmt) {
	LocaleDTO defaultLocaleDTO = ussdMenuBuilder.getDefaultLocaleDTO(ussdSessionMgmt.getCountryCode(), ussdSessionMgmt.getBusinessId());
	String localCurrency = BMBContextHolder.getContext().getLocalCurrency();

	AuthUserData userAuthObj = new AuthUserData();

	PayHdr payHdr = new PayHdr();
	payHdr.setResCde("00000");
	payHdr.setResId("RES0201");

	AuthenticateUserPayData payData = new AuthenticateUserPayData();
	CustomerProfile custProf = new CustomerProfile();
	custProf.setPrefLang(defaultLocaleDTO.getLanguage());
	custProf.setUsrSta("Active");
	custProf.setPinSta("Active");

	List<CustomerMobileRegAcct> custActs = new ArrayList<CustomerMobileRegAcct>(2);
	CustomerMobileRegAcct acct1 = new CustomerMobileRegAcct();
	acct1.setAccStatus("Active");
	acct1.setActNo("7014922");
	acct1.setCurr(localCurrency);
	AvilableBalance availableBal = new AvilableBalance();
	availableBal.setCurr(localCurrency);
	availableBal.setAmt("693.20");
	acct1.setAvblBal(availableBal);
	CurrentBalance currentBal = new CurrentBalance();
	currentBal.setAmt("4822.00");
	currentBal.setCurr(localCurrency);
	acct1.setCurBal(currentBal);
	acct1.setMkdActNo("XXXXXX4922");
	acct1.setPriInd("Y");

	CustomerMobileRegAcct acct2 = new CustomerMobileRegAcct();
	acct2.setAccStatus("Active");
	acct2.setActNo("7014822");
	acct2.setCurr(localCurrency);
	AvilableBalance availableBal1 = new AvilableBalance();
	availableBal1.setCurr("TZS");
	availableBal1.setAmt("4822.20");
	acct2.setAvblBal(availableBal1);
	CurrentBalance currentBal1 = new CurrentBalance();
	currentBal1.setAmt("7799.00");
	currentBal1.setCurr(localCurrency);
	acct2.setCurBal(currentBal1);
	acct2.setMkdActNo("XXXXXX4822");
	acct2.setPriInd("N");

	custActs.add(acct1);
	custActs.add(acct2);

	payData.setCustProf(custProf);
	payData.setCustActs(custActs);

	userAuthObj.setPayHdr(payHdr);
	userAuthObj.setPayData(payData);
	UserProfile userProfile = ussdSessionMgmt.getUserProfile();
	if (userProfile == null) {
	    userProfile = new UserProfile();
	}
	List<CasaAccount> casaAccountList = new ArrayList<CasaAccount>();
	CasaAccount casaAcct = new CasaAccount();
	casaAcct.setAccountNumber("7017179");
	casaAcct.setMaskedAccountNumber("XXXXXX7179");
	casaAcct.setPrimaryIndicator("Y");

	casaAccountList.add(casaAcct);
	userProfile.setCasaAccountList(casaAccountList);
	ussdSessionMgmt.setUserProfile(userProfile);

	return userAuthObj;
    }

    /**
     * @param ussdMenuBuilder
     *            the ussdMenuBuilder to set
     */
    public void setUssdMenuBuilder(UssdMenuBuilder ussdMenuBuilder) {
	this.ussdMenuBuilder = ussdMenuBuilder;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_ENDS.getSequenceNo());
    }
}
