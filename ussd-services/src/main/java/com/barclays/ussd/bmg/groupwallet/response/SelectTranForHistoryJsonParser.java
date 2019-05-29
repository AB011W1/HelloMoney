package com.barclays.ussd.bmg.groupwallet.response;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup.MobileWalletProvider;
import com.barclays.ussd.utils.jsonparsers.bean.pesalink.AccountAdditionalInfo;
import com.barclays.ussd.utils.jsonparsers.bean.pesalink.CASAccountTransactionList;
import com.barclays.ussd.utils.jsonparsers.bean.vlpb.TransHist;
import com.barclays.ussd.utils.jsonparsers.bean.vlpb.VLPBBillerList;
import com.barclays.ussd.utils.jsonparsers.bean.vlpb.VLPBBillerLstDet;

public class SelectTranForHistoryJsonParser implements BmgBaseJsonParser {

	@Override
	public MenuItemDTO parseJsonIntoJava(
			ResponseBuilderParamsDTO responseBuilderParamsDTO)
			throws USSDBlockingException, USSDNonBlockingException {
		// TODO Auto-generated method stub

		MenuItemDTO menuDTO = null;
		VLPBBillerList activityDetails=null;
		ObjectMapper mapper=new ObjectMapper();

		try {
			activityDetails = mapper.readValue(responseBuilderParamsDTO.getJsonString(), VLPBBillerList.class);
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
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return menuDTO;
	}

	private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, VLPBBillerLstDet   activityDetailsPaydata) throws ParseException {
		MenuItemDTO menuItemDTO = null;
		StringBuilder pageBody = new StringBuilder();
		if (activityDetailsPaydata != null) {
		menuItemDTO=new MenuItemDTO();
		Map<String,String> userInputMap=responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
		int selectedAcc=Integer.parseInt(userInputMap.get("accNo"));
		List<AccountAdditionalInfo> lstFrmAcnt = (List<AccountAdditionalInfo>) responseBuilderParamsDTO.getUssdSessionMgmt().getCustaccountList();
		int selectedbiller=Integer.parseInt(userInputMap.get("billerId"));
		List <MobileWalletProvider> providers=(List<MobileWalletProvider>)responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get("MWTU001");
		MobileWalletProvider selectedBiller= providers.get(selectedbiller-1);
		AccountAdditionalInfo ainfo=lstFrmAcnt.get(selectedAcc-1);
		List<TransHist>tranList= activityDetailsPaydata.getTransactionHistoryList();
		int count=1;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");

		if(tranList!=null && ainfo!=null ){
			for(TransHist tran:tranList){
				if(ainfo.getAccountAdditionalInfo().getMaskedAccountId().equals(partialMask(tran.getFromAccountInfo().getMkdActNo()))
						&&selectedBiller.getBillerId().equals(tran.getBillerInfo().getBillerId())){
				String billDate =tran.getTransactionDate();
			    String fomattedDate = sf.format(formatter.parse(billDate));
			    pageBody.append(count +". ");
			    pageBody.append(fomattedDate);

			    pageBody.append(" "+ tran.getAmountInfo().getAmt()+" "+tran.getAmountInfo().getCurr()+USSDConstants.NEW_LINE);

				count++;
				}
			}
		}

		USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
		menuItemDTO.setPageBody(pageBody.toString());
		menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
		menuItemDTO.setPaginationType(PaginationEnum.LISTED);
		menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
		setNextScreenSequenceNumber(menuItemDTO);
		}
		return menuItemDTO;
	}

	String partialMask(String text) {
		int unmaskNumber = 4;
    	String updatedtext = text;
    	updatedtext = updatedtext.trim();
        if (updatedtext.length() <= unmaskNumber) {
            return updatedtext;
        }
        StringBuilder masked = new StringBuilder();
        for (int i = 0; i < updatedtext.length() - unmaskNumber; i++) {
            masked.append("x");
        }
        masked.append(updatedtext.substring(updatedtext.length() - unmaskNumber));
        return masked.toString();
    }

	public CASAccountTransactionList getTransactionDetails(List<CASAccountTransactionList> casaTranlist, String transactionNo){
		for(CASAccountTransactionList tran:casaTranlist)
			if(tran.getTransactionActivity().getTransactionRefnbr().equals(transactionNo))
				return tran;
		return null;
	}

	@Override
	public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
		// TODO Auto-generated method stub
		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_THIRTYSEVEN.getSequenceNo());
	}

}