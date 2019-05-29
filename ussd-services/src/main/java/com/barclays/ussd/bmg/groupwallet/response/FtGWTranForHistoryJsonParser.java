package com.barclays.ussd.bmg.groupwallet.response;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
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
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.jsonparsers.bean.pesalink.AccountAdditionalInfo;
import com.barclays.ussd.utils.jsonparsers.bean.pesalink.CASAccountTransactionList;
import com.barclays.ussd.utils.jsonparsers.bean.vlpb.TransHist;
import com.barclays.ussd.utils.jsonparsers.bean.vlpb.VLPBBillerList;
import com.barclays.ussd.utils.jsonparsers.bean.vlpb.VLPBBillerLstDet;

public class FtGWTranForHistoryJsonParser implements BmgBaseJsonParser {
	private static final Logger LOGGER = Logger.getLogger(FtGWTranForHistoryJsonParser.class);
	private static final String DEFAULT_MINI_STMT_CNT = "20";
	@Override
	public MenuItemDTO parseJsonIntoJava(
			ResponseBuilderParamsDTO responseBuilderParamsDTO)
			throws USSDBlockingException, USSDNonBlockingException {
		String jsonString = responseBuilderParamsDTO.getJsonString();
		ObjectMapper mapper = new ObjectMapper();
		MenuItemDTO menuDTO = null;
		try {
			VLPBBillerList casaDetail = mapper.readValue(jsonString, VLPBBillerList.class);
		    if (casaDetail != null) {
			if (casaDetail.getPayHdr() != null && USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(casaDetail.getPayHdr().getResCde())) {
			    menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, casaDetail.getPayData());
			} else if (casaDetail.getPayHdr() != null) {
			    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
			    throw new USSDNonBlockingException(casaDetail.getPayHdr().getResCde());
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
		return menuDTO;
	}

	private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, VLPBBillerLstDet activityDetailsPaydata )  throws USSDNonBlockingException, ParseException {
		if (activityDetailsPaydata == null || activityDetailsPaydata.getTransactionHistoryList() == null || activityDetailsPaydata.getTransactionHistoryList().isEmpty()) {
		    throw new USSDNonBlockingException(USSDExceptions.USSD_NO_MINI_STMT_DETAILS_AVBL.getBmgCode());
		}

		MenuItemDTO menuItemDTO = null;
		StringBuilder pageBody = new StringBuilder();
		if (activityDetailsPaydata != null) {
		menuItemDTO=new MenuItemDTO();
		Map<String,String> userInputMap=responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
		int selectedAcc=Integer.parseInt(userInputMap.get("accNo"));
		List<AccountAdditionalInfo> lstFrmAcnt = (List<AccountAdditionalInfo>) responseBuilderParamsDTO.getUssdSessionMgmt().getCustaccountList();

		AccountAdditionalInfo ainfo=lstFrmAcnt.get(selectedAcc-1);
		List<TransHist>tranList= activityDetailsPaydata.getTransactionHistoryList();
		int count=1;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");

		if(tranList!=null && ainfo!=null ){
			for(TransHist tran:tranList){
				if(ainfo.getAccountAdditionalInfo().getMaskedAccountId().equals(partialMask(tran.getFromAccountInfo().getMkdActNo()))){
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
		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_ENDS.getSequenceNo());
	}

}