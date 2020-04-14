/**
 *
 */
package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.barclays.bmg.constants.BillPaymentConstants;
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
import com.barclays.ussd.utils.jsonparsers.bean.airtime.Biller;

/**
 * @author BTCI
 *
 */
public class AirtimeInitResponseParser implements BmgBaseJsonParser, ScreenSequenceCustomizer {
    private static final Logger LOGGER = Logger.getLogger(AirtimeInitResponseParser.class);

    @Override
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuDTO = null;
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	try {
	    List<Biller> mnoList = (List<Biller>) ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.AIRTIME_MNO_LIST.getTranId());
	    if (mnoList != null && !mnoList.isEmpty()) {
			//Code changes to implement CR#51
			if(responseBuilderParamsDTO.getUssdSessionMgmt().getBusinessId().equals("KEBRB")){
				mnoList=getSortedMNOList(mnoList);
				ussdSessionMgmt.getTxSessions().put(USSDInputParamsEnum.AIRTIME_MNO_LIST.getTranId(),mnoList);
			}
			//Code changes to implement TZNBC MNO sequence
			if(responseBuilderParamsDTO.getUssdSessionMgmt().getBusinessId().equals("TZNBC")){
				mnoList=getSortedTznbcMNOList(mnoList);
				ussdSessionMgmt.getTxSessions().put(USSDInputParamsEnum.AIRTIME_MNO_LIST.getTranId(),mnoList);
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
	   // }
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
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_EIGHT.getSequenceNo());
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

  //TZNBC Menu Optimization
    @Override
    public int getCustomNextScreen(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
    	
    	int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_EIGHT.getSequenceNo();
    	String tranDataId=ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId();
    	if(null!=tranDataId && tranDataId.equalsIgnoreCase("ussd0.3.2") && ussdSessionMgmt.getBusinessId().equalsIgnoreCase("TZNBC")) {
    		Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
			userInputMap.put(BillPaymentConstants.AT_MW_SAVED_BENEF,BillPaymentConstants.AT_MW_SAVED_BENEF);
    		seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_THIRTEEN.getSequenceNo();
    	}
		return seqNo;
    }

}
