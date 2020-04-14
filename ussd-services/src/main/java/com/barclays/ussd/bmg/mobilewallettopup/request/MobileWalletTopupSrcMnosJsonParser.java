package com.barclays.ussd.bmg.mobilewallettopup.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.ScreenSequenceCustomizer;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.jsonparsers.MobileWalletTopupMnosJsonParser;
import com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup.MobileWalletData;
import com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup.MobileWalletPayData;
import com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup.MobileWalletProvider;
import com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup.SrcAccount;

public class MobileWalletTopupSrcMnosJsonParser implements BmgBaseJsonParser, ScreenSequenceCustomizer
{

	private static final Logger LOGGER = Logger.getLogger(MobileWalletTopupMnosJsonParser.class);

	public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
		MenuItemDTO menuDTO = new MenuItemDTO();
		ObjectMapper mapper = new ObjectMapper();

		try {
			MobileWalletData mobileWalletData = mapper.readValue(responseBuilderParamsDTO.getJsonString(), MobileWalletData.class);
			if (mobileWalletData != null) {
				if (mobileWalletData.getPayHdr() != null
						&& USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(mobileWalletData.getPayHdr().getResCde())) {
					Collections.sort(mobileWalletData.getPayData().getSrcLst(), new MobileWalletCustomerAccountComparator());
					setOutputInSession(responseBuilderParamsDTO, mobileWalletData);
				} else if (mobileWalletData.getPayHdr() != null) {
					LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
					throw new USSDNonBlockingException(mobileWalletData.getPayHdr().getResCde());
				} else {
					LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
					throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
				}
			}
		} catch (JsonParseException e) {
			LOGGER.error("JsonParseException : ", e);
			throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
		} catch (JsonMappingException e) {
			LOGGER.error("JsonParseException : ", e);
			throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
		} catch (Exception e) {
			LOGGER.error("Exception : ", e);
			if (e instanceof USSDNonBlockingException) {
				throw new USSDNonBlockingException(((USSDNonBlockingException) e).getErrorCode());
			} else {
				throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
			}
		}
		setNextScreenSequenceNumber(menuDTO);
		return menuDTO;
	}

	/**
	 * @param responseBuilderParamsDTO
	 * @param mobileProviderData
	 */
	private void setOutputInSession(ResponseBuilderParamsDTO responseBuilderParamsDTO, MobileWalletData mobileWalletData) {
		MobileWalletPayData mobileWalletPayData = mobileWalletData.getPayData();
		USSDSessionManagement ussdSessionMgmt=responseBuilderParamsDTO.getUssdSessionMgmt();
		if (mobileWalletPayData != null) {
			List<MobileWalletProvider> mnoList = (List<MobileWalletProvider>) mobileWalletPayData.getPrvderLst();
			if(ussdSessionMgmt.getBusinessId().equals("GHBRB") && ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranId().equals(USSDConstants.GH_FREE_DIAL_USSD_TRAN_ID)){
				mnoList=getMNOMobileMoneyList(mnoList);
			}
			//TZNBC Menu Optimization
			Map<String, Object> txSessions = new HashMap<String, Object>(5);
			if(responseBuilderParamsDTO.getUssdSessionMgmt().getBusinessId().equals("TZNBC")){
				mnoList=getSortedTznbcMNOList(mnoList);
			}
			txSessions.put(USSDInputParamsEnum.MOBILE_WALLET_MNOS_LST.getTranId(), mnoList);
			txSessions.put(USSDInputParamsEnum.MOBILE_WALLET_FROM_ACCOUNT.getTranId(), mobileWalletPayData.getSrcLst());
			ussdSessionMgmt.setTxSessions(txSessions);
		}
	}

	private List<MobileWalletProvider> getSortedTznbcMNOList(List<MobileWalletProvider> mnoList) {
		List<MobileWalletProvider> mwalletBillerList = new ArrayList<MobileWalletProvider>(mnoList);
		List<MobileWalletProvider> sortedBillerList=new ArrayList<MobileWalletProvider>();

		for (MobileWalletProvider mobileWalletProvider : mwalletBillerList) {
		    if(mobileWalletProvider.getBillerId().startsWith("AMCASHIN")){
			sortedBillerList.add(mobileWalletProvider);
			break;
		    }
		}

		for (MobileWalletProvider mobileWalletProvider : mwalletBillerList) {
		    if(mobileWalletProvider.getBillerId().startsWith("VMCASHIN")){
			sortedBillerList.add(mobileWalletProvider);
			break;
		    }
		}
		for (MobileWalletProvider mobileWalletProvider : mwalletBillerList) {
		    if(mobileWalletProvider.getBillerId().startsWith("TPCASHIN")){
			sortedBillerList.add(mobileWalletProvider);
			break;
		    }
		}
		for (MobileWalletProvider mobileWalletProvider : mwalletBillerList) {
		    if(mobileWalletProvider.getBillerId().startsWith("EZCASHIN")){
			sortedBillerList.add(mobileWalletProvider);
			break;
		    }
		}

		mwalletBillerList = removeAll(mwalletBillerList,sortedBillerList);
		sortedBillerList.addAll(mwalletBillerList);
		return sortedBillerList;

	    }
	private List<MobileWalletProvider> removeAll(List<MobileWalletProvider> collection, List<MobileWalletProvider> remove)
    {
    	List<MobileWalletProvider> list = new ArrayList<MobileWalletProvider>();
    	for (MobileWalletProvider mobileWalletProvider : collection) {
    		if (!remove.contains(mobileWalletProvider)) {
    	          list.add(mobileWalletProvider);
    	        }
		}
      return list;
    }

  
	@Override
	public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo());
	}

	public int getCustomNextScreen(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
		int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo();
		Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
		List<MobileWalletProvider> mnoList = (List<MobileWalletProvider>) ussdSessionMgmt.getTxSessions().get(
				USSDInputParamsEnum.MOBILE_WALLET_MNOS_LST.getTranId());
		
		//TZNBC Menu Optimization-redirected to Bene Management
		String tranDataId=ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId();
		if(null!=tranDataId && tranDataId.equalsIgnoreCase("ussd0.8.2.1") && ussdSessionMgmt.getBusinessId().equalsIgnoreCase("TZNBC"))
			seqNo = USSDSequenceNumberEnum.SEQUNCE_NUMBER_FOURTYFOUR.getSequenceNo();
				
		else if (mnoList != null && mnoList.size() == 1 && ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranId().equals(USSDConstants.GH_FREE_DIAL_USSD_TRAN_ID)){
			userInputMap = userInputMap == null ? new HashMap<String, String>() : userInputMap;
			userInputMap.put(USSDInputParamsEnum.MOBILE_WALLET_MNOS_LST.getParamName(), USSDConstants.DEFAULT_OPTION_SELECTION);
			ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(userInputMap);
			seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_FIVE.getSequenceNo();
		}
		
		return seqNo;
	}

	private  List<MobileWalletProvider> getMNOMobileMoneyList(List<MobileWalletProvider> mnoList){

		List<MobileWalletProvider> mwalletBillerList = new ArrayList<MobileWalletProvider>(mnoList);
		List<MobileWalletProvider> sortedBillerList=new ArrayList<MobileWalletProvider>();

		for (MobileWalletProvider mobileWalletProvider : mwalletBillerList) {
			if(mobileWalletProvider.getBillerId().equalsIgnoreCase(USSDConstants.FREE_DIAL_MWALLET_BILLER)){
				sortedBillerList.add(mobileWalletProvider);
				break;
			}
		}
		return sortedBillerList;
	}

}

class MobileWalletCustomerAccountComparator implements Comparator<SrcAccount>, Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public int compare(final SrcAccount accountDetail1, final SrcAccount accountDetail2) {
		int retVal = 0;
		if (StringUtils.equalsIgnoreCase(USSDConstants.PRIMARY_INDICATOR_YES, accountDetail1.getPriInd())) {
			retVal = -1;
		} else if (StringUtils.equalsIgnoreCase(USSDConstants.PRIMARY_INDICATOR_YES, accountDetail2.getPriInd())) {
			retVal = 1;
		} else {
			retVal = 0;
		}
		return retVal;
	}
	

  }

