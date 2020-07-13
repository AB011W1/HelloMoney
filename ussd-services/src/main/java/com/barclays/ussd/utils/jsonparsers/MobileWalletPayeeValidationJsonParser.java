package com.barclays.ussd.utils.jsonparsers;


import java.util.Map;
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
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.ScreenSequenceCustomizer;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup.MobileWalletTxValidate;


public class MobileWalletPayeeValidationJsonParser implements BmgBaseJsonParser,ScreenSequenceCustomizer{

    private static final Logger LOGGER = Logger.getLogger(MobileWalletPayeeValidationJsonParser.class);

    @Override
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	return renderMenuOnScreen(responseBuilderParamsDTO);
    }

    @SuppressWarnings("unchecked")
   	private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
       	MenuItemDTO menuItemDTO = new MenuItemDTO();    				
   				ObjectMapper mapper = new ObjectMapper();
   				MobileWalletTxValidate accList = null;   				
   				
   				if (responseBuilderParamsDTO.getJsonString() != null
   						&& responseBuilderParamsDTO.getJsonString().contains("resCde")) {
   					try {
   						accList = mapper.readValue(responseBuilderParamsDTO.getJsonString(), MobileWalletTxValidate.class);
   						
   						if (accList != null && accList.getPayHdr() != null && accList.getPayHdr().getResCde() != null && accList.getPayHdr().getResCde().equalsIgnoreCase("00000")) {						
   							
   							
   							USSDSessionManagement ussdSession=responseBuilderParamsDTO.getUssdSessionMgmt();
   							Map<String, Object> txSessions = ussdSession.getTxSessions();
   							txSessions.put(responseBuilderParamsDTO.getTranDataId(),accList.getPayData().getPayeeName());
   							ussdSession.setTxSessions(txSessions);
   							responseBuilderParamsDTO.setUssdSessionMgmt(ussdSession);					
   							
   					} else if (accList != null && accList.getPayHdr() != null && accList.getPayHdr().getResCde() != null && accList.getPayHdr().getResCde().equalsIgnoreCase("500")) {
   							
   							throw new USSDNonBlockingException(USSDExceptions.USSD_NO_ACCNT.getBmgCode());
   						}
   						else {
   							throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
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
   				}           
          
   				USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);   				
   				menuItemDTO.setPageFooter(menuItemDTO.getPageFooter());
   				menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
   				menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
   				menuItemDTO.setPaginationType(PaginationEnum.SPACED);
   				setNextScreenSequenceNumber(menuItemDTO);
   				return menuItemDTO;
       }

       @Override
       public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
    	   menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FIVE.getSequenceNo());

       }
       
        @SuppressWarnings("unchecked")
       public int getCustomNextScreen(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
       	int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_FIVE.getSequenceNo();
          		return seqNo;
       }
    

}
