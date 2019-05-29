package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.ScreenSequenceCustomizer;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup.MobileWalletProvider;

public class MobileWalletNewBeneficiarySrcMnosJsonParser implements BmgBaseJsonParser,ScreenSequenceCustomizer {
	@Resource(name = "branchCodeCountryList")
    private List<String> branchCodeCountryList;
    private static final Logger LOGGER = Logger.getLogger(MobileWalletNewBeneficiarySrcMnosJsonParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuDTO = null;
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	try {
	    List<MobileWalletProvider> mnoList = (List<MobileWalletProvider>) ussdSessionMgmt.getTxSessions().get(
		    USSDInputParamsEnum.MOBILE_WALLET_MNOS_LST.getTranId());
	    ussdSessionMgmt.getTxSessions().put(USSDInputParamsEnum.MOBILE_WALLET_NEW_BENE_SRC_MON.getTranId(), mnoList);
	    if (mnoList != null && !mnoList.isEmpty()) {
			//Code changes to implement CR#51
			if(responseBuilderParamsDTO.getUssdSessionMgmt().getBusinessId().equals("KEBRB")){
				mnoList=getSortedMNOList(mnoList);
				ussdSessionMgmt.getTxSessions().put(USSDInputParamsEnum.MOBILE_WALLET_NEW_BENE_SRC_MON.getTranId(),mnoList);
			}
			//Code changes to implement TZNBC MNO Sequence
			if(responseBuilderParamsDTO.getUssdSessionMgmt().getBusinessId().equals("TZNBC")){
				mnoList=getSortedTznbcMNOList(mnoList);
				ussdSessionMgmt.getTxSessions().put(USSDInputParamsEnum.MOBILE_WALLET_NEW_BENE_SRC_MON.getTranId(),mnoList);
			}
	    }
	    //if(!(billerId.equalsIgnoreCase("MTNMM-4")) && !(billerId.equalsIgnoreCase("MTNZMBANKTOWALLET-2"))){
	    if(null != mnoList){
	    for(int i=0;i<mnoList.size();i++){
	    	if(mnoList.get(i).getBillerId().equalsIgnoreCase("MTNMM-4") || mnoList.get(i).getBillerId().equalsIgnoreCase("MTNZMBANKTOWALLET-2")){
	    		mnoList.remove(i);
	    	}
	    }
	    }
	    menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, mnoList, "");
	} catch (Exception e) {
	    LOGGER.error("Exception : ", e);
/*	    if (e instanceof USSDNonBlockingException) {
		throw new USSDNonBlockingException(((USSDNonBlockingException) e).getErrorCode());
	    } else {*/
		throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	   // }
	}
	return menuDTO;
    }

    /**
     * @param responseBuilderParamsDTO
     * @param mobileWalletPayData
     * @param warningMsg
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, List<MobileWalletProvider> mnoList, String warningMsg) {
	MenuItemDTO menuItemDTO = new MenuItemDTO();

	int index = 1;
	StringBuilder pageBody = new StringBuilder();

	if (mnoList != null && !mnoList.isEmpty()) {
	    for (MobileWalletProvider mno : mnoList) {
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append(index++);
		pageBody.append(USSDConstants.DOT_SEPERATOR);
		pageBody.append(mno.getBillerName());
	    }
	}
	menuItemDTO.setPageBody(pageBody.toString());
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageFooter(menuItemDTO.getPageFooter() + warningMsg);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setPaginationType(PaginationEnum.LISTED);
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);

	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {

	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_SIXTEEN.getSequenceNo());
    }


    @Override
    public int getCustomNextScreen(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
	int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_SIXTEEN.getSequenceNo();
	//CR#47
	/*if (!branchCodeCountryList.contains(businessId)) {
	    seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo();
	}*/
	return seqNo;
    }


    private  List<MobileWalletProvider> getSortedMNOList(List<MobileWalletProvider> mnoList){

    	List<MobileWalletProvider> mwalletBillerList = new ArrayList<MobileWalletProvider>(mnoList);
		List<MobileWalletProvider> sortedBillerList=new ArrayList<MobileWalletProvider>();

		for (MobileWalletProvider mobileWalletProvider : mwalletBillerList) {
			if(mobileWalletProvider.getBillerId().startsWith("MPESA")){
				sortedBillerList.add(mobileWalletProvider);
				break;
			}
		}

		for (MobileWalletProvider mobileWalletProvider : mwalletBillerList) {
			if(mobileWalletProvider.getBillerId().startsWith("AIRTELMONEY")){
				sortedBillerList.add(mobileWalletProvider);
				break;
			}
		}

		mwalletBillerList = removeAll(mwalletBillerList,sortedBillerList);
		sortedBillerList.addAll(mwalletBillerList);
		return sortedBillerList;

    }

    //Changes done for TZNBC MNO list sequence
    private  List<MobileWalletProvider> getSortedTznbcMNOList(List<MobileWalletProvider> mnoList){

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



}
