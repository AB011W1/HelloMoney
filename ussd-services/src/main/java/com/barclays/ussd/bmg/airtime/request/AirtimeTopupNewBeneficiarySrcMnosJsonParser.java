package com.barclays.ussd.bmg.airtime.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.jsonparsers.AirtimeInitResponseParser;
import com.barclays.ussd.utils.jsonparsers.bean.airtime.Biller;

public class AirtimeTopupNewBeneficiarySrcMnosJsonParser implements BmgBaseJsonParser {
	private static final Logger LOGGER = Logger.getLogger(AirtimeInitResponseParser.class);

	private static final String EDIT_SELECT_MNO_LABEL = "label.select.edit.beneficiary.youroperator";

    @Override
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuDTO = null;
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	try {
	    List<Biller> mnoList = (List<Biller>) ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.AIRTIME_MNO_LIST.getTranId());
	   /* Map<String, Object> txSessions = ussdSessionMgmt.getTxSessions();*/
	    ussdSessionMgmt.getTxSessions().put(USSDInputParamsEnum.AIRTIME_TOPUP_NEW_BENE_SRC_MON.getTranId(), mnoList);
	    if (mnoList != null && !mnoList.isEmpty()) {
			//Code changes to implement CR#51
			if(responseBuilderParamsDTO.getUssdSessionMgmt().getBusinessId().equals("KEBRB")){
				mnoList=getSortedMNOList(mnoList);
				ussdSessionMgmt.getTxSessions().put(USSDInputParamsEnum.AIRTIME_TOPUP_NEW_BENE_SRC_MON.getTranId(),mnoList);
			}
			//Code changes to implement TZNBC MNO sequence
			if(responseBuilderParamsDTO.getUssdSessionMgmt().getBusinessId().equals("TZNBC")){
				mnoList=getSortedTznbcMNOList(mnoList);
				ussdSessionMgmt.getTxSessions().put(USSDInputParamsEnum.AIRTIME_TOPUP_NEW_BENE_SRC_MON.getTranId(),mnoList);
			}
	    }

	    menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, mnoList);
	    menuDTO.setPaginationType(PaginationEnum.LISTED);
	} catch (Exception e) {
	    LOGGER.error("Exception : ", e);
/*	    if (e instanceof USSDNonBlockingException) {
		throw new USSDNonBlockingException(((USSDNonBlockingException) e).getErrorCode());
	    } else {*/
		throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	    //}
	}
	return menuDTO;
    }

    /**
     * @param responseBuilderParamsDTO
     * @param airtimeInitPayData
     * @param warningMsg
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, List<Biller> mnoList) {
	MenuItemDTO menuItemDTO = new MenuItemDTO();

	int index = 1;
	StringBuilder pageBody = new StringBuilder();

		if (mnoList != null && !mnoList.isEmpty()) {
	    for (Biller mno : mnoList) {
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append(index++);
		pageBody.append(USSDConstants.DOT_SEPERATOR);
		pageBody.append(mno.getBillerName());
	    }
	}
	menuItemDTO.setPageBody(pageBody.toString());
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageFooter(menuItemDTO.getPageFooter());
	USSDSessionManagement ussdSessionManagement = responseBuilderParamsDTO.getUssdSessionMgmt();
	Map<String, String> userInputMap = ussdSessionManagement
	.getUserTransactionDetails().getUserInputMap();
	String paymentTypeInput=	null;
	String transNodeId=ussdSessionManagement.getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId();
	//TZNBC Menu Optimization - To fetch editbene label from Bene Management screen
	if(ussdSessionManagement.getBusinessId().equalsIgnoreCase("TZNBC"))
		paymentTypeInput = userInputMap.get(USSDInputParamsEnum.AIRTIME_TOPUP_BENE_MANAGEMENT.getParamName());
	//Ghana Menu Optimization - To fetch editbene label from Bene Management 
	else if(ussdSessionManagement.getBusinessId().equalsIgnoreCase("GHBRB") && !transNodeId.equals("ussd0.10"))
		paymentTypeInput = userInputMap.get(USSDInputParamsEnum.AIRTIME_TOPUP_MSISDN_TYPE.getParamName());
	else 
		paymentTypeInput=userInputMap.get(USSDInputParamsEnum.AIRTIME_TOPUP_PAYMENT_TYPE.getParamName());
	//Ghana Menu Optimization
	if(null!=paymentTypeInput && ((ussdSessionManagement.getBusinessId().equalsIgnoreCase("TZNBC") && paymentTypeInput.equals("2"))
			  || (ussdSessionManagement.getBusinessId().equalsIgnoreCase("GHBRB") && !transNodeId.equalsIgnoreCase("ussd0.10") && paymentTypeInput.equals("5"))
			  || (ussdSessionManagement.getBusinessId().equalsIgnoreCase("GHBRB") && transNodeId.equalsIgnoreCase("ussd0.10") && paymentTypeInput.equals("4"))
			  || (!ussdSessionManagement.getBusinessId().equalsIgnoreCase("GHBRB") && paymentTypeInput.equals("4")))){
		menuItemDTO.setPageHeader(EDIT_SELECT_MNO_LABEL);
	}else{
		menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	}
	
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_SIXTEEN.getSequenceNo());
    }


    private  List<Biller> getSortedMNOList(List<Biller> mnoList){

    	List<Biller> billerList = new ArrayList<Biller>(mnoList);
		List<Biller> sortedBillerList=new ArrayList<Biller>();

		for (Biller biller : billerList) {
			if(biller.getBillerId().startsWith("SAFCOM")){
				sortedBillerList.add(biller);
				break;
			}
		}

		for (Biller billerProvider : billerList) {
			if(billerProvider.getBillerId().startsWith("AIRTEL")){
				sortedBillerList.add(billerProvider);
				break;
			}
		}

		billerList = removeAll(billerList,sortedBillerList);
		sortedBillerList.addAll(billerList);
		return sortedBillerList;

    }

    //changes for TZNBC MNO sequence
    private  List<Biller> getSortedTznbcMNOList(List<Biller> mnoList){

    	List<Biller> billerList = new ArrayList<Biller>(mnoList);
		List<Biller> sortedBillerList=new ArrayList<Biller>();

		for (Biller biller : billerList) {
			if(biller.getBillerId().startsWith("ATOP")){
				sortedBillerList.add(biller);
				break;
			}
		}

		for (Biller billerProvider : billerList) {
			if(billerProvider.getBillerId().startsWith("VTOP")){
				sortedBillerList.add(billerProvider);
				break;
			}
		}
		for (Biller billerProvider : billerList) {
			if(billerProvider.getBillerId().startsWith("TTOP")){
				sortedBillerList.add(billerProvider);
				break;
			}
		}
		for (Biller billerProvider : billerList) {
			if(billerProvider.getBillerId().startsWith("ZTOP")){
				sortedBillerList.add(billerProvider);
				break;
			}
		}



		billerList = removeAll(billerList,sortedBillerList);
		sortedBillerList.addAll(billerList);
		return sortedBillerList;

    }


    private List<Biller> removeAll(List<Biller> collection, List<Biller> remove)
    {
    	List<Biller> list = new ArrayList<Biller>();
    	for (Biller billerProvider : collection) {
    		if (!remove.contains(billerProvider)) {
    	          list.add(billerProvider);
    	        }
		}
      return list;
    }
}

