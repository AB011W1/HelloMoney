package com.barclays.ussd.bmg.groupwallet.response;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup.MobileWalletProvider;
import com.barclays.ussd.utils.jsonparsers.bean.pesalink.AccountAdditionalInfo;
import com.barclays.ussd.utils.jsonparsers.bean.pesalink.CASAccountTransactionList;
import com.barclays.ussd.utils.jsonparsers.bean.pesalink.TransactionActivity;
import com.barclays.ussd.utils.jsonparsers.bean.pesalink.TransactionActivityDetails;
import com.barclays.ussd.utils.jsonparsers.bean.pesalink.TransactionActivityDetailsPayData;

public class TranListJsonParser implements BmgBaseJsonParser {

	@Override
	public MenuItemDTO parseJsonIntoJava(
			ResponseBuilderParamsDTO responseBuilderParamsDTO)
			throws USSDBlockingException, USSDNonBlockingException {
		// TODO Auto-generated method stub

		MenuItemDTO menuDTO = null;
		TransactionActivityDetails activityDetails=null;
		ObjectMapper mapper=new ObjectMapper();

		try {
			activityDetails = mapper.readValue(responseBuilderParamsDTO.getJsonString(), TransactionActivityDetails.class);
			if (activityDetails.getPayData() == null ||
					activityDetails.getPayData().getCasaAccountTransactionList() == null ||
					activityDetails.getPayData().getCasaAccountTransactionList().isEmpty()) {
			    throw new USSDNonBlockingException(USSDExceptions.USSD_NO_ELIGIBLE_TRAN.getBmgCode());
			}
			menuDTO=renderMenuOnScreen(responseBuilderParamsDTO,activityDetails.getPayData());
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return menuDTO;
	}

	private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, TransactionActivityDetailsPayData  activityDetailsPaydata) {
		MenuItemDTO menuItemDTO = null;
		StringBuilder pageBody = new StringBuilder();
		USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
		if (activityDetailsPaydata != null) {
		menuItemDTO=new MenuItemDTO();
		int selectedAcc=Integer.parseInt(responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap().get("accno"));
		List<AccountAdditionalInfo> lstFrmAcnt = (List<AccountAdditionalInfo>) responseBuilderParamsDTO.getUssdSessionMgmt().getCustaccountList();

		AccountAdditionalInfo ainfo=lstFrmAcnt.get(selectedAcc-1);
		int authLevel=Integer.parseInt(ainfo.getAccountAdditionalInfo().getAuthLevel());

		ussdSessionMgmt.getUserTransactionDetails().getUserInputMap().put("authLevel", ainfo.getAccountAdditionalInfo().getAuthLevel());
		Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
		Map<String, Object> txSessions = ussdSessionMgmt.getTxSessions();
		List<MobileWalletProvider> mobileWalletProvider = (List<MobileWalletProvider>) txSessions
				.get(USSDInputParamsEnum.MOBILE_WALLET_MNOS_LST.getTranId());
		String selectedMobileWalletProvider = userInputMap
				.get(USSDInputParamsEnum.MOBILE_WALLET_MNOS_LST.getParamName());
		MobileWalletProvider mobileWalletProviderSelected = mobileWalletProvider
				.get(Integer.parseInt(selectedMobileWalletProvider) - 1);

		List<CASAccountTransactionList> casaTranlist=activityDetailsPaydata.getCasaAccountTransactionList();
		HashMap<String,Integer> transactions=new HashMap<String,Integer>();
		for(CASAccountTransactionList transaction:casaTranlist){
			TransactionActivity  tranActivity=transaction.getTransactionActivity();
			String refNumber=tranActivity.getTransactionRefnbr();
			if(!transactions.containsKey(refNumber)
					&& tranActivity.getBeneficiaryORBillerName()!=null
					&& tranActivity.getBeneficiaryORBillerName().equals(mobileWalletProviderSelected.getBillerId()))
				transactions.put(refNumber, 1);
			else{
				if(tranActivity.getBeneficiaryORBillerName()!=null
					&& tranActivity.getBeneficiaryORBillerName().equals(mobileWalletProviderSelected.getBillerId())){
				int count=Integer.parseInt(transactions.get(refNumber).toString());
				count++;
				transactions.put(refNumber, count);
				}
			}
		}
		Set<String> finalList=transactions.keySet();
		int srNo=1;

		for(String tranNo:finalList){
			int count=Integer.parseInt(transactions.get(tranNo).toString());
			if(count<authLevel){
			pageBody.append(USSDConstants.NEW_LINE);
			pageBody.append(srNo);
			pageBody.append(USSDConstants.DOT_SEPERATOR);
			pageBody.append(tranNo);
			srNo++;
			}
		}

		responseBuilderParamsDTO.getUssdSessionMgmt().setTransactionList(casaTranlist);
		responseBuilderParamsDTO.getUssdSessionMgmt().setFinalTransactionList(finalList);
		USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
		menuItemDTO.setPageBody(pageBody.toString());
		menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
		menuItemDTO.setPaginationType(PaginationEnum.LISTED);
		menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
		setNextScreenSequenceNumber(menuItemDTO);
		}
		return menuItemDTO;
	}

	@Override
	public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
		// TODO Auto-generated method stub
		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_THIRTYEIGHT.getSequenceNo());
	}

}